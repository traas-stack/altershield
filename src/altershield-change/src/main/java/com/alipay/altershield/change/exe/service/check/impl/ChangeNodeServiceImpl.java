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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.altershield.change.exe.service.check.ChangeNodeService;
import com.alipay.altershield.change.exe.service.check.CoordCounterService;
import com.alipay.altershield.change.exe.service.check.model.ChangeNodeCreateModel;
import com.alipay.altershield.change.exe.service.convert.ChangeStartNotifyRequestConvert;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.service.AlterShieldGroupService;
import com.alipay.altershield.common.util.TraceUtil;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdictEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckRule;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeBaseNodeEntity;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import com.alipay.altershield.shared.change.exe.service.ExeChangeNodeService;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.defender.ExeDefenderDetectService;
import com.alipay.altershield.shared.schedule.event.change.ChangeNodeCreatedEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * The type Change node service.
 *
 * @author yuanji
 * @version : ChangeNodeServiceImpl.java, v 0.1 2022年04月05日 8:38 下午 yuanji Exp $
 */
@Service
public class ChangeNodeServiceImpl implements ChangeNodeService, ExeChangeNodeService {

    @Autowired
    private ExeChangeOrderRepository exeChangeOrderRepository;

    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ChangeStartNotifyRequestConvert changeStartNotifyRequestConvert;
    @Autowired
    private CoordCounterService coordCounterService;

    @Autowired
    private AlterShieldGroupService alterShieldGroupService;

    @Autowired
    private AlterShieldSchedulerEventPublisher alterShieldSchedulerEventPublisher;

    @Autowired
    private ExeNodeStateMachineManager exeNodeStateMachineManager;

    @Autowired
    private ExeDefenderDetectService exeDefenderDetectService;

    private static final Logger logger = Loggers.FACADE_EXE;


    /**
     * Create exe node entity exe node entity.
     *
     * @param model the model
     * @return the exe node entity
     */
    @Override
    public ExeNodeEntity createExeNodeEntity(ChangeNodeCreateModel model) {

        ExeNodeEntity exeNodeEntity = createNode(model);
        AlterShieldLoggerManager.log("info", logger, "create exe node entity", JSONUtil.toJSONString(exeNodeEntity, false));
        sendEvent(exeNodeEntity, model);
        AlterShieldLoggerManager.log("info", logger, "create exe node entity success", model.getNodeExeId());
        return exeNodeEntity;
    }

    private ExeNodeEntity createNode(ChangeNodeCreateModel model)
    {
        OpsChngTrace myTrace = genOpsChngTrace(model.getRequest().getTrace());
        model.setTrace(myTrace);
        ExeNodeEntity exeNodeEntity = convertToEntity(model);
        exeChangeNodeRepository.insert(exeNodeEntity);
        updateOrderStatus(exeNodeEntity, model);
        return exeNodeEntity;
    }

    private void updateOrderStatus(ExeNodeEntity entity, ChangeNodeCreateModel model)
    {
        if(StringUtils.isNotBlank(entity.getOrderId()))
        {
            String msg = "开始进行:%s 变更";

            if(model.getChangeStepEntity() != null)
            {
                msg = String.format(msg, model.getChangeStepEntity().getName());
            }
            else
            {
                msg = String.format(msg, model.getChangeStepType());
            }
            exeChangeOrderRepository.updateOrderInfo(entity.getOrderId(), ExeOrderStatusEnum.RUNNING, msg);
        }
    }

    private void updateOrderMsg(ExeNodeEntity entity)
    {
        if(StringUtils.isNotBlank(entity.getOrderId()))
        {
            exeChangeOrderRepository.updateOrderInfo(entity.getOrderId(), null, entity.getMsg());
        }
    }

    private void sendEvent(ExeNodeEntity exeNodeEntity, ChangeNodeCreateModel opsCloudChangeNodeCreateModel)
    {
        ChangeNodeCreatedEvent event = new ChangeNodeCreatedEvent();
        event.setExeNodeId(exeNodeEntity.getNodeExeId());
        event.setChangeKey(exeNodeEntity.getChangeKey());
        event.setEmergency(exeNodeEntity.isEmergency());
        event.setChangeKey(exeNodeEntity.getChangeKey());
        event.setChangeStepType(exeNodeEntity.getChangeStepType());
        event.setGeneration(opsCloudChangeNodeCreateModel.getGeneration());
        alterShieldSchedulerEventPublisher.publish(exeNodeEntity.getNodeExeId(),event);
    }

    /**
     * Select node entity list.
     *
     * @param orderId            the order id
     * @param metaChangeStepType the meta change step type
     * @param size               the size
     * @return the list
     */
    @Override
    public List<ExeNodeEntity> selectNodeEntity(String orderId, MetaChangeStepTypeEnum metaChangeStepType, int size) {
        return exeChangeNodeRepository.getNodeEntityByOrderIdList(orderId, metaChangeStepType, size);
    }

    private OpsChngTrace genOpsChngTrace(OpsChngTrace parentTrace) {
        int targetSer = (int) coordCounterService.addAndGetByTraceId(parentTrace.getTraceId());
        return TraceUtil.genTraceNodeByService(parentTrace, targetSer);
    }

    private ExeNodeEntity convertToEntity(ChangeNodeCreateModel model) {
        //自动生成trace
        model.setDispatchGroup(alterShieldGroupService.getGroup());
        model.setStatus(ExeNodeStateEnum.PRE_AOP_SUBMIT);
        ExeNodeEntity exeNodeEntity = changeStartNotifyRequestConvert.createExeNodeEntity(model.getChangeStepType());
        changeStartNotifyRequestConvert.convert(model, exeNodeEntity);
        exeNodeEntity.setExeNodeCheckInfo(new ExeNodeCheckInfo(System.currentTimeMillis()));
        exeNodeEntity.setEmergency(model.isEmergency());

        // add 环境
        Set<String> changePhases = model.getChangePhases();
        if (!CollectionUtils.isEmpty(changePhases)) {
            for (String phase : changePhases) {
                ChangePhaseEnum phaseEnum = ChangePhaseEnum.getByPhase(phase);
                if (!Objects.isNull(phaseEnum)) {
                    exeNodeEntity.setChangePhase(phaseEnum);
                }
            }
        }
        if (model.getChangeContents() != null && model.getChangeContents().length > 0) {
            exeNodeEntity.getChangeContentRef().write(new ArrayList<>(Arrays.asList(model.getChangeContents())));
        }

        return exeNodeEntity;
    }

    /**
     * Lock node by id exe node entity.
     *
     * @param nodeId the node id
     * @return the exe node entity
     */
    @Override
    public ExeNodeEntity lockNodeById(String nodeId) {
        ExeNodeEntity entity =  exeChangeNodeRepository.lockNodeById(nodeId);
        mergeEntity(entity);
        return entity;
    }

    /**
     * Gets get node.
     *
     * @param nodeId the node id
     * @return the get node
     */
    @Override
    public ExeNodeEntity getNode(String nodeId) {
        ExeNodeEntity entity = exeChangeNodeRepository.getNodeEntity(nodeId);
        mergeEntity(entity);
        return entity;
    }

    /**
     * Update node search ext info.
     *
     * @param exeNodeEntity the exe node entity
     */
    @Override
    public void updateNodeSearchExtInfo(ExeNodeEntity exeNodeEntity) {
        exeChangeNodeRepository.updateNodeSearchExtInfo(exeNodeEntity);
    }

    /**
     * Update node check info.
     *
     * @param exeNodeEntity the exe node entity
     */
    @Override
    public void updateNodeCheckInfo(ExeNodeEntity exeNodeEntity)
    {
        exeChangeNodeRepository.updateNodeCheckInfo(exeNodeEntity);
        updateOrderMsg(exeNodeEntity);
    }

    /**
     * Sets set node check finish.
     *
     * @param exeNodeEntity    the exe node entity
     * @param defenseStageEnum the defense stage enum
     * @param checkPaas        the check paas
     */
    @Override
    public void setNodeCheckFinish(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {
        AlterShieldLoggerManager.log("info", Loggers.EXE_STATE_MACHINE, "ChangeNodeServiceImpl$setNodeCheckFinish", exeNodeEntity.getNodeExeId(),
                exeNodeEntity, defenseStageEnum, checkPaas);
        ExeNodeStateMachine exeNodeStateMachine =  exeNodeStateMachineManager.getExeNodeStateMachine(exeNodeEntity.getStatus());
        exeNodeStateMachine.setNodeDefenseFinish(exeNodeEntity, defenseStageEnum, checkPaas);
        updateOrderMsg(exeNodeEntity);

    }

    /**
     * Sets set node check fail.
     *
     * @param exeNodeEntity    the exe node entity
     * @param defenseStageEnum the defense stage enum
     * @param msg              the msg
     */
    @Override
    public void setNodeCheckFail(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, String msg) {

        ExeNodeStateMachine exeNodeStateMachine =  exeNodeStateMachineManager.getExeNodeStateMachine(exeNodeEntity.getStatus());
        exeNodeStateMachine.setNodeDefenseFail(exeNodeEntity, defenseStageEnum, msg);
        updateOrderMsg(exeNodeEntity);

    }

    /**
     * Sets set node check started.
     *
     * @param exeNodeEntity    the exe node entity
     * @param defenseStageEnum the defense stage enum
     * @param checkId          the check id
     */
    @Override
    public void setNodeCheckStarted(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, String checkId) {
        ExeNodeStateMachine exeNodeStateMachine =  exeNodeStateMachineManager.getExeNodeStateMachine(exeNodeEntity.getStatus());
        exeNodeStateMachine.setNodeDefenseStarted(exeNodeEntity, defenseStageEnum, checkId);
        updateOrderMsg(exeNodeEntity);
    }

    /**
     * Gets get node entity by order id list.
     *
     * @param orderId the order id
     * @return the get node entity by order id list
     */
    @Override
    public List<ExeBaseNodeEntity> getNodeEntityByOrderIdList(String orderId) {
        return exeChangeNodeRepository.getNodeEntityByOrderIdList(orderId);
    }

    /**
     * Query change verdict ops cloud change check verdict.
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param returnDetails    the return details
     * @param changeSuccess    the change success
     * @return the ops cloud change check verdict
     */
    @Override
    public ChangeCheckVerdict queryChangeVerdict(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum,
                                                 boolean returnDetails, boolean changeSuccess) {
        ChangeCheckVerdict verdict = new ChangeCheckVerdict();
        verdict.setVerdict(ChangeCheckVerdictEnum.PASS.getVerdict());
        verdict.setAllFinish(true);
        if (changeSuccess) {
            final ExeNodeCheckInfo idInfo = entity.getExeNodeCheckInfo();
            boolean isPre = defenseStageEnum == DefenseStageEnum.PRE;
            // 如果不是finish状态，则直接返回通过。否则需要检查结果
            boolean isPass = isPre ? Boolean.TRUE.equals(idInfo.getPreCheckPass()) : Boolean.TRUE.equals(idInfo.getPostCheckPass());
            verdict.setAllFinish(true);
            verdict.setVerdict(
                    isPass ? ChangeCheckVerdictEnum.PASS.getVerdict() : ChangeCheckVerdictEnum.FAIL.getVerdict());
            if (returnDetails) {
                List<ChangeCheckRule> ruleList = exeDefenderDetectService.queryDetectStatusByNodeId(entity.getNodeExeId(),
                        defenseStageEnum);
                verdict.setRules(ruleList);
            }
        }
        return verdict;
    }

    private void mergeEntity(ExeNodeEntity entity)
    {
        if(entity != null)
        {
            switch (entity.getChangeStepType())
            {
                case STEP_ORDER:
                case STEP_GRAY_BATCH:
                    merger(entity);
                    return;

            }
        }
    }

    private void merger(ExeNodeEntity exeNodeEntity)
    {
        ExeChangeOrderEntity orderEntity =  exeChangeOrderRepository.getChangeOrderById(exeNodeEntity.getOrderId());
        if(orderEntity != null)
        {
            if(exeNodeEntity.getChangeTargetRef().readObject() == null)
            {
                exeNodeEntity.getChangeTargetRef().write(exeNodeEntity.getChangeTargetRef().readObject());
            }
            if(exeNodeEntity.getParamRef().readObject() == null)
            {
                exeNodeEntity.getParamRef().write(exeNodeEntity.getParamRef().readObject());
            }
            if(exeNodeEntity.getChangeContentRef().readObject() == null)
            {
                exeNodeEntity.getChangeContentRef().write(exeNodeEntity.getChangeContentRef().readObject());
            }
        }
    }

}