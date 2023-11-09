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

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.opscloud.api.scheduler.event.change.OpsCloudNodeCheckPoolingEvent;
import com.alipay.opscloud.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.logger.Loggers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AOP阶段的node 状态
 *
 * @author yuanji
 * @version : AopExeNodeState.java, v 0.1 2022年10月21日 10:46 yuanji Exp $
 */
public abstract class AopExeNodeStateMachine extends CheckFinishNodeStateMachine {

    @Override
    public void pollingCheckStatus(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum) {
        MetaChangeStepEntity metaChangeStepEntity = metaChangeSceneRepository.getChangeStepEntity(entity.getChangeKey());
        if (metaChangeStepEntity == null) {
            OpsCloudLoggerManager.log("warn", logger, "check.exception", "meta ChangeStep is null", entity.getChangeKey());
            return;
        }
        checkStatus(metaChangeStepEntity, defenseStageEnum, entity);
    }

    private void checkStatus(MetaChangeStepEntity metaChangeStepEntity, DefenseStageEnum defenseStageEnum, ExeNodeEntity entity) {

        //检查是否超时
        if (isCheckTimeout(entity, defenseStageEnum, metaChangeStepEntity)) {
            List<ExeNodeStateEnum> from = new ArrayList<>();
            from.add(entity.getStatus());
            entity.setStatus(defenseStageEnum == DefenseStageEnum.PRE ? ExeNodeStateEnum.PRE_AOP_TIMEOUT : ExeNodeStateEnum.POST_AOP_TIMEOUT);
            entity.getExeNodeCheckInfo().markFinish(defenseStageEnum);
            entity.setMsg("check defence time out, pass all");

            boolean success = exeChangeNodeRepository.updateNodeCheckInfoWithOldStatus(entity, from);
            //增加日志
            String msg = "current:%s.(%s)-(pollingCheckStatus) -> %s";
            String result = String.format(msg, from, getClass(), entity.getStatus());
            String prefix = "exeState.transfer.success";
            if(!success)
            {
                prefix = "exeState.transfer.fail";
            }
            OpsCloudLoggerManager.log("info", logger, prefix, entity.getNodeExeId(), result);

            checkCallback(metaChangeStepEntity, defenseStageEnum, entity);
        } else {
            //没有超时就继续轮训
            sendPoolingEvent(defenseStageEnum, entity);
        }
    }

    /**
     * Is check timeout boolean.
     *
     * @param entity               the entity
     * @param defenseStageEnum     the defense stage enum
     * @param metaChangeStepEntity the meta change step entity
     * @return the boolean
     */
    protected boolean isCheckTimeout(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, MetaChangeStepEntity metaChangeStepEntity) {
        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        if (exeNodeCheckInfo == null) {
            OpsCloudLoggerManager.log("warn", logger, "check.exception", "checkId is null invalid status.", entity.getNodeExeId());
            return true;
        }
        long startTime;
        long maxTimeout;
        OpsCloudLoggerManager.log("debug", logger, "start.check.timeout",  entity.getNodeExeId(), defenseStageEnum);
        if (defenseStageEnum == DefenseStageEnum.PRE) {
            startTime = exeNodeCheckInfo.getPreCheckStartTime();
            maxTimeout = metaChangeStepEntity.getDefenceConfig().getPreCheckTimeout();
        } else {
            startTime = exeNodeCheckInfo.getPostCheckStartTime();
            maxTimeout = metaChangeStepEntity.getDefenceConfig().getPostCheckTimeout();
        }
        long currentTime = System.currentTimeMillis();
        long cost = currentTime - startTime;
        if(logger.isDebugEnabled())
        {
            String msg = String.format("current:%d, start:%d, cost:%d，maxTimeOut:%d", currentTime, startTime, cost, maxTimeout);
            OpsCloudLoggerManager.log("debug", logger, "check.timeout.timeout",  entity.getNodeExeId(), msg);
        }

        return cost >= maxTimeout;
    }

    private void sendPoolingEvent(DefenseStageEnum defenseStage, ExeNodeEntity entity) {
        OpsCloudNodeCheckPoolingEvent event = new OpsCloudNodeCheckPoolingEvent();
        event.setChangeKey(entity.getChangeKey());
        event.setDefenseStage(defenseStage);
        event.setExeNodeId(entity.getNodeExeId());
        long planTime = System.currentTimeMillis() + OpsCloudConstant.DEFENSE_SUBMIT_CHECK_INTERVAL;
        //设置延时时间
        event.setGmtPlan(new Date(planTime));
        opsCloudSchedulerEventPublisher.publish(entity.getNodeExeId(), event);
        OpsCloudLoggerManager.log("info", logger, "polling check", "resubmit polling event", entity.getNodeExeId(), defenseStage);
    }



    @Override
    public void setNodeDefenseFinish(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {
        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPreCheckPass(checkPaas);
        exeNodeCheckInfo.markFinish(defenseStageEnum);
        if(updateStatus(defenseStageEnum))
        {
            entity.setStatus(defenseStageEnum == DefenseStageEnum.PRE ? ExeNodeStateEnum.PRE_AOP_FINISH : ExeNodeStateEnum.POST_AOP_FINISH);
        }
        OpsCloudLoggerManager.log("info", Loggers.EXE_STATE_MACHINE, "AopExeNodeStateMachine$setNodeDefenseFinish",entity.getNodeExeId(), entity, defenseStageEnum, checkPaas);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }


    @Override
    public void setNodeDefenseFail(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String msg) {
        if(updateStatus(defenseStageEnum))
        {
            entity.setStatus(defenseStageEnum == DefenseStageEnum.PRE ? ExeNodeStateEnum.PRE_AOP_FAIL : ExeNodeStateEnum.POST_AOP_FAIL);
        }
        entity.setMsg(msg);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }

    /**
     * 是否更新状态机
     * @param defenseStageEnum
     * @return
     */
    protected boolean updateStatus(DefenseStageEnum defenseStageEnum)
    {
        return true;
    }
}