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

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import org.springframework.stereotype.Component;

/**
 * POST AOP
 *
 * @author yuanji
 * @version : PostAopExeNodeStateMachine.java, v 0.1 2022年10月21日 11:11 yuanji Exp $
 */
@Component
public class PostAopExeNodeStateMachine extends AopExeNodeStateMachine{

    /**
     * Instantiates a new Post aop exe node state machine.
     */
    public PostAopExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.POST_AOP;
    }

    @Override
    public void setNodeDefenseFinish(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {

        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPostCheckPass(checkPaas);
        exeNodeCheckInfo.markFinish(defenseStageEnum);
        entity.setStatus(ExeNodeStateEnum.POST_AOP_FINISH);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }

    @Override
    public void setNodeDefenseFail(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String msg) {
        entity.setStatus(ExeNodeStateEnum.POST_AOP_FAIL);
        entity.getExeNodeCheckInfo().markFinish(defenseStageEnum);
        entity.setMsg(msg);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }
}