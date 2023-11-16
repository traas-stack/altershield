/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.listener;

import com.alipay.opscloud.api.defender.DefenderTaskChecker;
import com.alipay.opscloud.api.defender.result.DefenderTaskResult;
import com.alipay.opscloud.api.scheduler.enums.OpsCloudScheduleEventResultStatus;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderCheckProcessEvent;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventContext;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventListener;
import com.alipay.opscloud.api.scheduler.event.result.OpsCloudSchedulerEventExecuteResult;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 变更防御批次校验任务状态检查器
 *
 * @author haoxuan
 * @version DefenderCheckProcessEventListener.java, v 0.1 2022年08月30日 4:01 下午 haoxuan
 */
@Component("defenderCheckProcessEventListener")
public class DefenderCheckProcessEventListener implements OpsCloudSchedulerEventListener<DefenderCheckProcessEvent> {

    @Autowired
    private DefenderTaskChecker defenderTaskChecker;

    /**
     * 接收变更防御批次校验任务状态检查事件，汇总批次所有校验任务的状态
     *
     * @param context
     * @param event
     * @return
     */
    @Override
    public OpsCloudSchedulerEventExecuteResult onEvent(OpsCloudSchedulerEventContext context, DefenderCheckProcessEvent event) {
        OpsCloudLoggerManager.log("info", Loggers.DEFENDER,
                String.format("[DefenderCheckProcessEventListener] 防御消费整合校验规则事件, OrderId=%s, NodeId=%s, stage=%s",
                        event.getChangeOrderId(), event.getNodeId(), event.getStage().getStage()));

        OpsCloudSchedulerEventExecuteResult result = new OpsCloudSchedulerEventExecuteResult();

        DefenderTaskResult executeRst = defenderTaskChecker.checkDetectProcess(event.getChangeOrderId(),
                event.getNodeId(), event.getDetectGroupId(), event.getStage(), event.getChangeSceneKey(), event.getChangeStepType());
        result.setMsg(executeRst.getMsg());
        if (executeRst.isNeedRetry()) {
            result.setStatus(OpsCloudScheduleEventResultStatus.RETRY);
        } else {
            result.setStatus(OpsCloudScheduleEventResultStatus.SUCCESS);
        }
        return result;
    }
}