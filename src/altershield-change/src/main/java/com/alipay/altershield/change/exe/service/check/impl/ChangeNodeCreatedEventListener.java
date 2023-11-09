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
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.altershield.change.exe.service.check.ChangeNodeStatusCheckService;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.schedule.event.change.ChangeNodeCreatedEvent;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventContext;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventListener;
import com.alipay.altershield.shared.schedule.event.result.AlterShieldSchedulerEventExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author yuanji
 * @version : ChangeNodeCheckListener.java, v 0.1 2022年10月19日 17:30 yuanji Exp $
 */
@Component
public class ChangeNodeCreatedEventListener implements AlterShieldSchedulerEventListener<ChangeNodeCreatedEvent> {

    @Autowired
    private ChangeNodeStatusCheckService changeNodeStatusCheckService;

    /**
     * On event ops cloud scheduler event execute result.
     *
     * @param context the context
     * @param event   the event
     * @return the ops cloud scheduler event execute result
     */
    @Override
    public AlterShieldSchedulerEventExecuteResult onEvent(AlterShieldSchedulerEventContext context, ChangeNodeCreatedEvent event) {
        if (event.getGeneration() == MetaChangeSceneGenerationEnum.G0 || event.getGeneration() == MetaChangeSceneGenerationEnum.G1)
        {
                return AlterShieldSchedulerEventExecuteResult.success("not need process");
        }
        changeNodeStatusCheckService.checkNodeStatus(event.getChangeKey(), DefenseStageEnum.PRE, event.getExeNodeId());
        return AlterShieldSchedulerEventExecuteResult.success("start check node status: nodeId=" + event.getExeNodeId());
    }
}