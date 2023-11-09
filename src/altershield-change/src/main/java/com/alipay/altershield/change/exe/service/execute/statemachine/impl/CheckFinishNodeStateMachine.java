/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.exe.service.execute.statemachine.impl;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.opscloud.api.change.exe.service.ExeChangeNodeService;
import com.alipay.opscloud.api.scheduler.event.change.OpsCloudNodeCheckCallbackEvent;
import com.alipay.opscloud.api.scheduler.event.publish.OpsCloudSchedulerEventPublisher;
import com.alipay.opscloud.change.exe.service.check.ChangeSyncCheckService;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeSkipCheckRequest;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeCheckProgressResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdict;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdictEnum;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.server.OpsCloudRunMode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * 检查状态的状态机 Finish/FAIL/TIMEOUT 可以轮询结果 retrieve结果 重新提交 忽略
 *
 * @author yuanji
 * @version : CheckFinishNodeState.java, v 0.1 2022年10月21日 10:54 yuanji Exp $
 */
public abstract class CheckFinishNodeStateMachine extends ExeNodeStateMachine {

    /**
     * The Ops cloud scheduler event publisher.
     */
    @Autowired
    protected OpsCloudSchedulerEventPublisher opsCloudSchedulerEventPublisher;

    /**
     * The Ops cloud run mode.
     */
    @Autowired
    protected OpsCloudRunMode opsCloudRunMode;

    @Autowired
    private ExeChangeNodeService exeChangeNodeService;

    /**
     * The Change sync check service.
     */
    @Autowired
    protected ChangeSyncCheckService changeSyncCheckService;

    @Override
    public void pollingCheckStatus(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum) {
        MetaChangeStepEntity metaChangeStepEntity = metaChangeSceneRepository.getChangeStepEntity(entity.getChangeKey());
        if (metaChangeStepEntity == null) {
            OpsCloudLoggerManager.log("warn", logger, "check.exception", "meta ChangeStep is null", entity.getChangeKey());
            return;
        }
        checkCallback(metaChangeStepEntity, defenseStageEnum, entity);
    }
    @Override
    public OpsCloudResult<OpsCloudChangeSkipCheckResult> setIgnoreNodeCheck(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, OpsCloudChangeSkipCheckRequest request) {
        if (defenseStageEnum == DefenseStageEnum.PRE) {
            entity.setStatus(ExeNodeStateEnum.PRE_AOP_IGNORE);
        } else {
            entity.setStatus(ExeNodeStateEnum.POST_AOP_IGNORE);
        }

        OpsCloudResult<OpsCloudChangeSkipCheckResult> opsCloudResult = exeDefenderDetectService.skipChangeCheck(request);
        if(opsCloudResult.isSuccess() && opsCloudResult.getDomain() != null)
        {
            exeChangeNodeRepository.updateNodeStatus(entity);
        }
        return opsCloudResult;
    }

    @Override
    public OpsCloudResult<OpsCloudChangeCheckProgressResult> retrieveCheckResult(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum,
                                                                                 boolean returnDetails) {

        if(checkFinishSuccess())
        {
            OpsCloudChangeCheckVerdict verdict = exeChangeNodeService.queryChangeVerdict(entity,defenseStageEnum, returnDetails, checkFinishSuccess());
            OpsCloudChangeCheckProgressResult opsCloudChangeCheckProgressResult =  new OpsCloudChangeCheckProgressResult(true, verdict,
                    OpsCloudConstant.OPSCLOUD_DEFENSE_CHECK_DETAIL_URL + entity.getNodeExeId());

            return new OpsCloudResult(opsCloudChangeCheckProgressResult);
        }
        else
        {
            OpsCloudChangeCheckVerdict verdict = new OpsCloudChangeCheckVerdict();
            verdict.setVerdict(OpsCloudChangeCheckVerdictEnum.PASS.getVerdict());
            verdict.setAllFinish(true);
            verdict.setMsg("check timeout or fail, paas all");
            return new OpsCloudResult(new OpsCloudChangeCheckProgressResult(true, verdict,
                    OpsCloudConstant.OPSCLOUD_DEFENSE_CHECK_DETAIL_URL + entity.getNodeExeId()));
        }
    }

    /**
     * Check callback.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @param defenseStageEnum     the defense stage enum
     * @param entity               the entity
     */
    protected void checkCallback(MetaChangeStepEntity metaChangeStepEntity, DefenseStageEnum defenseStageEnum, ExeNodeEntity entity) {

        //只在独立模式下抛出这个事件 TODO
        if(!OpsCloudRunMode.RUN_MODE_OPSCLOUD.equals(opsCloudRunMode.getRunMode()))
        {
            return;
        }
        String changeSceneKey = metaChangeStepEntity.getChangeSceneKey();
        if (StringUtils.isNotBlank(changeSceneKey)) {
            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeSceneKey);
            if (metaChangeSceneEntity != null) {
                if (metaChangeSceneEntity.getCallbackConfigEntity() != null && !CollectionUtils.isEmpty(
                        metaChangeSceneEntity.getCallbackConfigEntity().getCallbackConfig())) {
                    OpsCloudNodeCheckCallbackEvent opsCloudNodeCheckCallbackEvent = new OpsCloudNodeCheckCallbackEvent();
                    opsCloudNodeCheckCallbackEvent.setNodeExeId(entity.getNodeExeId());
                    opsCloudNodeCheckCallbackEvent.setChangeKey(metaChangeStepEntity.getChangeKey());
                    opsCloudNodeCheckCallbackEvent.setDefenseStage(defenseStageEnum);
                    opsCloudSchedulerEventPublisher.publish(entity.getNodeExeId(), opsCloudNodeCheckCallbackEvent);
                }
            }
        }
    }

    /**
     * 检查是否结束
     *
     * @return boolean
     */
    protected boolean checkFinishSuccess()
    {
        return false;
    }
}