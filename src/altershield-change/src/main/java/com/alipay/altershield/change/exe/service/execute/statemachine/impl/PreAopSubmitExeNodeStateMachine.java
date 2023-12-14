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

import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import org.springframework.stereotype.Component;

/**
 * The type Pre aop submit exe node state machine.
 *
 * @author yuanji
 * @version : PreAopSubmitExeNodeStateMachine.java, v 0.1 2022年10月24日 10:57 yuanji Exp $
 */
@Component
public class PreAopSubmitExeNodeStateMachine extends AopExeNodeStateMachine{

    /**
     * Instantiates a new Pre aop submit exe node state machine.
     */
    public PreAopSubmitExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.PRE_AOP_SUBMIT;
    }

    @Override
    public ChangeCheckVerdict setNodeSyncPreStartCheck(long checkTimeOut, ExeChangeOrderEntity changeOrder, ExeNodeEntity entity, MetaChangeSceneEntity metaChangeSceneEntity) {
        if(metaChangeSceneEntity.getGeneration() != MetaChangeSceneGenerationEnum.G1)
        {
            unsupportedOperation(entity);
        }
        ChangeCheckVerdict opsCloudChangeCheckVerdict = changeSyncCheckService.syncCheck(checkTimeOut, changeOrder, entity, metaChangeSceneEntity);
        return opsCloudChangeCheckVerdict;
    }

    @Override
    public void setNodeDefenseStarted(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String checkId) {
        if(defenseStageEnum == DefenseStageEnum.POST)
        {
            unsupportedOperation(entity);
        }
        ExeNodeCheckInfo exeNodeCheckInfo = entity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPreCheckId(checkId);
        exeNodeCheckInfo.markStart(defenseStageEnum);
        entity.setStatus(ExeNodeStateEnum.PRE_AOP);
        exeChangeNodeRepository.updateNodeCheckInfo(entity);
    }

    /**
     * 支持前置提交直接到前置校验结束，兼容小程序云目前前置空跑逻辑
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param checkPaas        the check paas
     */
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
}