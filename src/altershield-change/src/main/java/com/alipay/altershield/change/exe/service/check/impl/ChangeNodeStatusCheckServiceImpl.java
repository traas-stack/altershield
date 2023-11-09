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
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.scheduler.event.change.OpsCloudNodeCheckPoolingEvent;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventContext;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventListener;
import com.alipay.opscloud.api.scheduler.event.result.OpsCloudSchedulerEventExecuteResult;
import com.alipay.opscloud.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.opscloud.change.exe.service.check.ChangeNodeStatusCheckService;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 这儿是对结点的检查状态判断。只有到这个结点
 *
 * @author yuanji
 * @version : ChangeNodeStatusCheckServiceImpl.java, v 0.1 2022年10月19日 17:44 yuanji Exp $
 */
@Service
public class ChangeNodeStatusCheckServiceImpl implements ChangeNodeStatusCheckService, OpsCloudSchedulerEventListener<OpsCloudNodeCheckPoolingEvent> {

    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ExeNodeStateMachineManager exeNodeStateMachineManager;

    private static final Logger logger = Loggers.SCHEDULE_POINT_DIGEST;

    /**
     * Check node status.
     *
     * @param changeKey        the change key
     * @param defenseStageEnum the defense stage enum
     * @param nodeExeId        the node exe id
     */
    @Override
    public void checkNodeStatus(String changeKey, DefenseStageEnum defenseStageEnum, String nodeExeId) {

        ExeNodeEntity entity = exeChangeNodeRepository.getNodeEntity(nodeExeId);
        if (entity == null) {
            OpsCloudLoggerManager.log("warn", logger, "check.exception", "exeNode is null", nodeExeId);
            return;
        }
        ExeNodeStateMachine exeNodeStateMachine = exeNodeStateMachineManager.getExeNodeStateMachine(entity.getStatus());
        exeNodeStateMachine.pollingCheckStatus(entity,defenseStageEnum);
    }

    /**
     * On event ops cloud scheduler event execute result.
     *
     * @param context the context
     * @param event   the event
     * @return the ops cloud scheduler event execute result
     */
    @Override
    public OpsCloudSchedulerEventExecuteResult onEvent(OpsCloudSchedulerEventContext context, OpsCloudNodeCheckPoolingEvent event) {
        checkNodeStatus(event.getChangeKey(), event.getDefenseStage(), event.getExeNodeId());
        return OpsCloudSchedulerEventExecuteResult.success("check node success");
    }

}