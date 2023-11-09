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
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import org.springframework.stereotype.Component;

/**
 * 前置检查阶段状态
 *
 * @author yuanji
 * @version : PreAopExeNodeState.java, v 0.1 2022年10月21日 10:24 yuanji Exp $
 */
@Component
public class PreAopExeNodeStateMachine extends AopExeNodeStateMachine {

    /**
     * Instantiates a new Pre aop exe node state machine.
     */
    public PreAopExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.PRE_AOP;
    }



    @Override
    public void setNodeDefenseFinish(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {
        if(defenseStageEnum == DefenseStageEnum.POST)
        {
            unsupportedOperation(entity);
        }
        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPreCheckPass(checkPaas);
        exeNodeCheckInfo.markFinish(defenseStageEnum);
        entity.setStatus(ExeNodeStateEnum.PRE_AOP_FINISH);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }

    @Override
    public void setNodeDefenseFail(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String msg) {
        if(defenseStageEnum == DefenseStageEnum.POST)
        {
            unsupportedOperation(entity);
        }
        entity.setStatus(ExeNodeStateEnum.PRE_AOP_FAIL);
        entity.setMsg(msg);
        entity.getExeNodeCheckInfo().markFinish(defenseStageEnum);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }
}