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

import com.alipay.altershield.common.service.ServiceProcessor;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import org.springframework.stereotype.Component;

/**
 * 超时状态下仅更新结果
 *
 * @author yuanji
 * @version : PostAopTimeoutExeNodeStateMachine.java, v 0.1 2022年10月25日 14:51 yuanji Exp $
 */
@Component
public class PostAopTimeoutExeNodeStateMachine extends PostAopFinishExeNodeStateMachine{

    /**
     * Instantiates a new Post aop timeout exe node state machine.
     */
    public PostAopTimeoutExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.POST_AOP_TIMEOUT;
    }

    @Override
    public void setNodeDefenseFinish(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {

        doProcess(defenseStageEnum, () -> {
            ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
            exeNodeCheckInfo.setPostCheckPass(checkPaas);
            // 允许超时后node更新状态，保证异步化任务的校验结果
            entity.setStatus(ExeNodeStateEnum.POST_AOP_FINISH);
            exeChangeNodeRepository.updateNodeStatus(entity);
            return null;
        });
    }

    @Override
    public void setNodeDefenseFail(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String msg) {
        doProcess(defenseStageEnum, () -> {
            entity.setMsg(msg);
            // 允许超时后的node更新状态，保证异步化任务的校验结果
            entity.setStatus(ExeNodeStateEnum.POST_AOP_FAIL);
            exeChangeNodeRepository.updateNodeStatus(entity);
            return null;
        });
    }
    @Override
    public void setNodeDefenseStarted(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String checkId){
        if(defenseStageEnum == DefenseStageEnum.PRE)
        {
            unsupportedOperation(entity);
        }
        entity.getExeNodeCheckInfo().setPostCheckId(checkId);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }

    private void doProcess(DefenseStageEnum defenseStageEnum, ServiceProcessor<Void> processor)
    {
        if(defenseStageEnum == DefenseStageEnum.PRE)
        {
            AlterShieldLoggerManager.log("warn", logger, "state.transfer.ignore","defense stage is not match:" + defenseStageEnum, getExeState());
            return;
        }
        processor.process();
    }

}