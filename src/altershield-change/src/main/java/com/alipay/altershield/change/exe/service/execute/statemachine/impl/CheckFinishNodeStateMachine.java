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

import com.alipay.altershield.change.exe.service.check.ChangeSyncCheckService;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.service.ExeChangeNodeService;
import com.alipay.altershield.shared.schedule.event.change.NodeCheckCallbackEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
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
    protected AlterShieldSchedulerEventPublisher opsCloudSchedulerEventPublisher;


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
            AlterShieldLoggerManager.log("warn", logger, "check.exception", "meta ChangeStep is null", entity.getChangeKey());
            return;
        }
        checkCallback(metaChangeStepEntity, defenseStageEnum, entity);
    }
    @Override
    public AlterShieldResult<ChangeSkipCheckResult> setIgnoreNodeCheck(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, ChangeSkipCheckRequest request) {
        if (defenseStageEnum == DefenseStageEnum.PRE) {
            entity.setStatus(ExeNodeStateEnum.PRE_AOP_IGNORE);
        } else {
            entity.setStatus(ExeNodeStateEnum.POST_AOP_IGNORE);
        }

        AlterShieldResult<ChangeSkipCheckResult> opsCloudResult = exeDefenderDetectService.skipChangeCheck(request);
        if(opsCloudResult.isSuccess() && opsCloudResult.getDomain() != null)
        {
            exeChangeNodeRepository.updateNodeStatus(entity);
        }
        return opsCloudResult;
    }

    @Override
    public AlterShieldResult<ChangeCheckProgressResult> retrieveCheckResult(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum,
                                                                            boolean returnDetails) {

        if(checkFinishSuccess())
        {
            ChangeCheckVerdict verdict = exeChangeNodeService.queryChangeVerdict(entity,defenseStageEnum, returnDetails, checkFinishSuccess());
            ChangeCheckProgressResult opsCloudChangeCheckProgressResult =  new ChangeCheckProgressResult(true, verdict,
                    AlterShieldConstant.ALTER_SHIELD_DEFENSE_CHECK_DETAIL_URL + entity.getNodeExeId());

            return new AlterShieldResult<>(opsCloudChangeCheckProgressResult);
        }
        else
        {
            ChangeCheckVerdict verdict = new ChangeCheckVerdict();
            verdict.setVerdict(ChangeCheckVerdictEnum.PASS.getVerdict());
            verdict.setAllFinish(true);
            verdict.setMsg("check timeout or fail, paas all");
            return new AlterShieldResult<>(new ChangeCheckProgressResult(true, verdict,
                    AlterShieldConstant.ALTER_SHIELD_DEFENSE_CHECK_DETAIL_URL + entity.getNodeExeId()));
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
        String changeSceneKey = metaChangeStepEntity.getChangeSceneKey();
        if (StringUtils.isNotBlank(changeSceneKey)) {
            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeSceneKey);
            if (metaChangeSceneEntity != null) {
                if (metaChangeSceneEntity.getCallbackConfigEntity() != null && !CollectionUtils.isEmpty(
                        metaChangeSceneEntity.getCallbackConfigEntity().getCallbackConfig())) {
                    NodeCheckCallbackEvent opsCloudNodeCheckCallbackEvent = new NodeCheckCallbackEvent();
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