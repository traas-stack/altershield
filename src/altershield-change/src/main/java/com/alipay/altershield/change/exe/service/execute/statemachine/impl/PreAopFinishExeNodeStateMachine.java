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
import com.alipay.altershield.framework.core.change.facade.request.ChangeFinishNotifyRequest;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.*;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.schedule.event.change.ChangeNodeCheckStartEvent;
import com.alipay.altershield.util.MiscUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * PreAop结束状态，可以重试，也可以到后置，也可以回调完成
 *
 * @author yuanji
 * @version : PreAopFinishExeNodeStateMachine.java, v 0.1 2022年10月21日 11:21 yuanji Exp $
 */
@Component
public class PreAopFinishExeNodeStateMachine extends CheckFinishNodeStateMachine {

    /**
     * Instantiates a new Pre aop finish exe node state machine.
     */
    public PreAopFinishExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.PRE_AOP_FINISH;
    }

    /**
     * 设置为执行状态
     * @param entity the entity
     */
    @Override
    public void setChangeExecuting(ExeNodeEntity entity) {
        entity.setStatus(ExeNodeStateEnum.EXECUTE_DOING);
        exeChangeNodeRepository.updateNodeStatus(entity);
    }

    @Override
    public ChangeCheckVerdict setNodeSyncPreStartCheck(long checkTimeOut, ExeChangeOrderEntity changeOrder, ExeNodeEntity entity,
                                                       MetaChangeSceneEntity metaChangeSceneEntity) {
        return changeSyncCheckService.syncCheck(checkTimeOut, changeOrder, entity, metaChangeSceneEntity);
    }

    @Override
    public void submitNodePreStartCheck(ExeNodeEntity entity, MetaChangeSceneEntity metaChangeSceneEntity) {
        entity.setStatus(ExeNodeStateEnum.PRE_AOP_SUBMIT);
        entity.setMsg("重新提交前置检查");
        entity.setExeNodeCheckInfo(new ExeNodeCheckInfo(System.currentTimeMillis()));
        exeChangeNodeRepository.update(entity);
        publishChangeNodeCheckStartEvent(entity, DefenseStageEnum.PRE, metaChangeSceneEntity, false);
    }

    @Override
    public void submitNodePostStartCheck(ExeNodeEntity exeNodeEntity, ChangeFinishNotifyRequest request,
                                         MetaChangeSceneEntity metaChangeSceneEntity) {
        exeNodeEntity.setFinishTime(request.getFinishTime());
        exeNodeEntity.setMsg(request.getMsg());
        if (StringUtils.isNotBlank(request.getServiceResult())) {
            exeNodeEntity.getRstRef().write(request.getServiceResult());
        }
        exeNodeEntity.tagSpiSuccess(request.isSuccess());
        exeNodeEntity.setStatus(ExeNodeStateEnum.POST_AOP_SUBMIT);
        ExeNodeCheckInfo exeNodeCheckInfo = exeNodeEntity.getExeNodeCheckInfo();
        exeNodeCheckInfo.markStart(DefenseStageEnum.POST);
        exeNodeEntity.setExeNodeCheckInfo(exeNodeCheckInfo);
        //merge target
        boolean influenceEffectiveTarget = mergeEffectiveTargetLocations(exeNodeEntity, request);
        //merge 扩展信息
//        boolean influenceExtensionInfo = mergeExtentInfo(exeNodeEntity, request);
//        boolean influenceInfoChanged = influenceEffectiveTarget || influenceExtensionInfo;
        exeChangeNodeRepository.update(exeNodeEntity);
        publishChangeNodeCheckStartEvent(exeNodeEntity, DefenseStageEnum.POST, metaChangeSceneEntity, false);
    }

    private boolean mergeEffectiveTargetLocations(ExeNodeEntity exeNodeEntity, ChangeFinishNotifyRequest request) {
        if (!CollectionUtils.isEmpty(request.getEffectiveTargetLocations())) {
            switch (exeNodeEntity.getChangeStepType()) {
                case STEP_GRAY_BATCH:
                    ExeBatchNodeEntity exeBatchNodeEntity = (ExeBatchNodeEntity) exeNodeEntity;
                    ExeChangeBatchEffectiveInfoEntity exeChangeBatchEffectiveInfoEntity = exeBatchNodeEntity.getChangeEffectiveInfoRef()
                            .readObject();
                    if (exeChangeBatchEffectiveInfoEntity != null) {
                        exeChangeBatchEffectiveInfoEntity.setEffectiveLocations(request.getEffectiveTargetLocations());
                    }
                    return true;
                case STEP_AFTER_GRAY_FINISH:
                case STEP_ATOMIC_ACTION:
                case STEP_GRAY_BATCH_ACTION:
                case STEP_BEFORE_GRAY_START:
                    ExeActionNodeEntity exeActionNodeEntity = (ExeActionNodeEntity) exeNodeEntity;
                    ExeChangeEffectiveInfoEntity exeChangeEffectiveInfoEntity = exeActionNodeEntity.getChangeEffectiveInfoRef()
                            .readObject();
                    if (exeChangeEffectiveInfoEntity != null) {
                        exeChangeEffectiveInfoEntity.setEffectiveLocations(request.getEffectiveTargetLocations());
                    }
                    return true;
            }
        }
        return false;
    }

//    private boolean mergeExtentInfo(ExeNodeEntity exeNodeEntity, ChangeFinishNotifyRequest request) {
//
//        if (!CollectionUtils.isEmpty(request.getExtensionInfo())) {
//            Map<String, Object> extension =  MiscUtil.mergeExtension(exeNodeEntity.getSearchExtRef().readObject(), request.getExtensionInfo());
//            exeNodeEntity.getSearchExtRef().write(extension);
//            return true;
//        }
//        return false;
//
//    }

    /**
     * @param entity
     * @param metaChangeSceneEntity
     * @param influenceChanged
     */
    private void publishChangeNodeCheckStartEvent(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum,
                                                  MetaChangeSceneEntity metaChangeSceneEntity, boolean influenceChanged) {
        if (entity != null && metaChangeSceneEntity != null) {
            ChangeNodeCheckStartEvent event = new ChangeNodeCheckStartEvent();
            event.setChangeKey(entity.getChangeKey());
            event.setChangeSceneKey(entity.getChangeSceneKey());
            event.setDefenseStage(defenseStageEnum);
            event.setEmergency(entity.isEmergency());
            event.setGeneration(metaChangeSceneEntity.getGeneration());
            event.setInfluenceChanged(influenceChanged);
            event.setExeNodeId(entity.getNodeExeId());
            // 4.0 发送事件
            opsCloudSchedulerEventPublisher.publish(entity.getNodeExeId(), event);
        } else {
            //TODO 打印日志
        }

    }
}