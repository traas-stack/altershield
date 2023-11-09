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
 * The type Post aop submit exe node state machine.
 *
 * @author yuanji
 * @version : PostAopSubmitExeNodeStateMachine.java, v 0.1 2022年10月24日 10:58 yuanji Exp $
 */
@Component
public class PostAopSubmitExeNodeStateMachine extends AopExeNodeStateMachine{

    /**
     * Instantiates a new Post aop submit exe node state machine.
     */
    public PostAopSubmitExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.POST_AOP_SUBMIT;
    }


    @Override
    public void setNodeDefenseStarted(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String checkId) {

        if(defenseStageEnum == DefenseStageEnum.PRE)
        {
            unsupportedOperation(entity);
        }
        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPostCheckId(checkId);
        exeNodeCheckInfo.markStart(defenseStageEnum);
        entity.setStatus(ExeNodeStateEnum.POST_AOP);
        exeChangeNodeRepository.updateNodeCheckInfo(entity);
    }

    @Override
    protected boolean updateStatus(DefenseStageEnum defenseStageEnum) {
        return defenseStageEnum == DefenseStageEnum.POST;
    }
}