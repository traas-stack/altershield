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


import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.service.check.ChangeNodeService;
import com.alipay.altershield.change.exe.service.check.ChangeTraceService;
import com.alipay.altershield.change.exe.service.check.ExeChangeOrderService;
import com.alipay.altershield.change.exe.service.check.ExeChangeSyncCheckService;
import com.alipay.altershield.change.exe.service.check.model.ChangeExecOrderSubmitRequestModel;
import com.alipay.altershield.change.exe.service.check.model.ChangeNodeCreateModel;
import com.alipay.altershield.change.exe.service.convert.ExeChangeOrderRequestConverter;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.change.util.EntityUtil;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeContext;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

public class ExeChangeSyncCheckServiceImpl implements ExeChangeSyncCheckService {

    @Autowired
    private ExeChangeOrderService exeChangeOrderService;

    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ChangeTraceService changeTraceService;

    @Autowired
    private ChangeNodeService changeNodeService;

    @Autowired
    private ExeChangeOrderRequestConverter exeChangeOrderRequestConverter;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private IdGenerator idGenerator;

    @Value("${altershield.framework.server.common.cloudId}")
    private String cloudId;

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    @Autowired
    private ExeNodeStateMachineManager exeNodeStateMachineManager;


    /**
     * Start check service ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @Override
    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> startCheckService(
            ChangeExecOrderSubmitAndStartRequest request) {
        return ServiceProcessTemplate.wrapTryCatch(() ->
                transactionTemplate.execute(status -> {
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                            request.getChangeSceneKey());
                    if (metaChangeSceneEntity == null) {
                        return AlterShieldResult.illegalArgument("change scene:" + request.getChangeSceneKey() + "  not found");
                    }
                    ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderService.getChangeOrder(request.getPlatform(),
                            request.getBizExecOrderId());
                    ChangeExecOrderSubmitAndStartResult opsCloudChangeExecOrderSubmitAndStartResult;
                    if (exeChangeOrderEntity == null) {
                        // 创建Order
                        exeChangeOrderEntity = createOrder(request, metaChangeSceneEntity);
                        // 创建Node
                        ExeNodeEntity exeNodeEntity = createExeNode(exeChangeOrderEntity.getOrderId(), request, metaChangeSceneEntity);
                        // 同步前置校验
                        opsCloudChangeExecOrderSubmitAndStartResult = synPreCheck(request.getCheckTimeout(), exeChangeOrderEntity, exeNodeEntity, metaChangeSceneEntity);
                    } else {
                        // 存在Order，则重试同步前置校验
                        opsCloudChangeExecOrderSubmitAndStartResult = redoSynPreCheck(request.getCheckTimeout(), exeChangeOrderEntity, metaChangeSceneEntity);
                    }
                    return AlterShieldResult.succeed("success", opsCloudChangeExecOrderSubmitAndStartResult);
                }));
    }

    private ExeChangeOrderEntity createOrder(ChangeExecOrderSubmitAndStartRequest request, MetaChangeSceneEntity metaChangeSceneEntity) {
        generateOrderTrace(request);
        ChangeExecOrderSubmitRequestModel requestModel = convertToOrderModel(request);
        return exeChangeOrderService.createExeChangeOrder(
                metaChangeSceneEntity, requestModel);
    }

    /**
     * Start notify service ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @Override
    public AlterShieldResult<SubmitChangeEventResult> startNotifyService(ChangeEventRequest request) {

        MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                request.getChangeSceneKey());
        if (metaChangeSceneEntity == null) {
            return AlterShieldResult.illegalArgument("change scene:" + request.getChangeSceneKey() + "  not found");
        }
        ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderService.getChangeOrder(request.getPlatform(),
                request.getBizExecOrderId());
        AlterShieldResult<SubmitChangeEventResult> rs = new AlterShieldResult<>();
        if (exeChangeOrderEntity == null) {
            exeChangeOrderEntity = createOrder(request, metaChangeSceneEntity);
            SubmitChangeEventResult submitChangeEventResult = new SubmitChangeEventResult(
                    exeChangeOrderEntity.getOrderId(), EntityUtil.buildOrderDetailUrl(exeChangeOrderEntity.getOrderId()));
            submitChangeEventResult.setOrderId(exeChangeOrderEntity.getOrderId());
            rs.setDomain(submitChangeEventResult);
            rs.setSuccess(true);
            rs.setMsg("success");
            rs.setResultCode(null);

        } else {
            // 发事件
        }
        return rs;
    }

    private ChangeExecOrderSubmitAndStartResult synPreCheck(long checkTimeOut, ExeChangeOrderEntity exeChangeOrderEntity, ExeNodeEntity exeNodeEntity,
                                                            MetaChangeSceneEntity metaChangeSceneEntity) {
        ExeNodeStateMachine exeNodeStateMachine = exeNodeStateMachineManager.getExeNodeStateMachine(
                exeNodeEntity.getStatus());
        ChangeCheckVerdict opsCloudChangeCheckVerdict = exeNodeStateMachine.setNodeSyncPreStartCheck(checkTimeOut,exeChangeOrderEntity,exeNodeEntity,
                metaChangeSceneEntity);
        ChangeExecOrderSubmitAndStartResult opsCloudChangeExecOrderSubmitAndStartResult
                = new ChangeExecOrderSubmitAndStartResult();
        opsCloudChangeExecOrderSubmitAndStartResult.setVerdict(opsCloudChangeCheckVerdict);
        opsCloudChangeExecOrderSubmitAndStartResult.setOrderId(exeChangeOrderEntity.getOrderId());
        opsCloudChangeExecOrderSubmitAndStartResult.setNodeId(exeNodeEntity.getNodeExeId());
        opsCloudChangeExecOrderSubmitAndStartResult.setUrl(EntityUtil.buildNodeDetailUrl(exeNodeEntity));
        return opsCloudChangeExecOrderSubmitAndStartResult;
    }

    private ChangeExecOrderSubmitAndStartResult redoSynPreCheck(long checkTimeout, ExeChangeOrderEntity exeChangeOrderEntity,
                                                                        MetaChangeSceneEntity metaChangeSceneEntity) {
        ExeNodeEntity exeNodeEntity = exeChangeNodeRepository.getLatestNodeEntity(exeChangeOrderEntity.getOrderId(),
                MetaChangeStepTypeEnum.STEP_ORDER);
        if (exeNodeEntity == null) {
            throw new IllegalArgumentException("invalid request for " + exeChangeOrderEntity.getOrderId());
        }
        if (exeNodeEntity.getContextRef() == null) {
            throw new IllegalArgumentException("invalid request for " + exeChangeOrderEntity.getOrderId() + ", context is null");
        }

        return synPreCheck(checkTimeout, exeChangeOrderEntity, exeNodeEntity,  metaChangeSceneEntity);
    }

    /**
     * Change finish ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @Override
    public AlterShieldResult<FinishChangeExecOrderResult> changeFinish(ChangeExecOrderFinishRequest request) {

        return ServiceProcessTemplate.wrapTryCatch(() ->
                transactionTemplate.execute(status -> {

                    ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderService.getChangeOrder(request.getPlatform(),
                            request.getBizExecOrderId());
                    if (exeChangeOrderEntity == null) {
                        throw new IllegalArgumentException(
                                "changeOrder not found .platform=" + request.getPlatform() + ",execBizOrderId=" + request
                                        .getBizExecOrderId());
                    }
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                            request.getChangeSceneKey());
                    AlterShieldResult opsCloudResult = null;
                    //G1触发后置防御
                    if (metaChangeSceneEntity.getGeneration() == MetaChangeSceneGenerationEnum.G1) {
                        ExeNodeEntity exeNodeEntity = exeChangeNodeRepository.getLatestNodeEntity(exeChangeOrderEntity.getOrderId(),
                                MetaChangeStepTypeEnum.STEP_ORDER);
                        ExeNodeStateMachine exeNodeStateMachine = exeNodeStateMachineManager.getExeNodeStateMachine(
                                exeNodeEntity.getStatus());
                        ChangeFinishNotifyRequest finishNotifyRequest = buildFinishRequest(request);
                        exeNodeStateMachine.submitNodePostStartCheck(exeNodeEntity, finishNotifyRequest, metaChangeSceneEntity);
                    }

                    opsCloudResult = AlterShieldResult.succeed("change finish success", null);
                    return opsCloudResult;
                }));
    }

    private ChangeFinishNotifyRequest buildFinishRequest(ChangeExecOrderFinishRequest request) {
        return exeChangeOrderRequestConverter.covertFinishRequest(request);
    }

    private ExeNodeEntity createExeNode(String orderId, ChangeExecOrderSubmitAndStartRequest request,
                                        MetaChangeSceneEntity metaChangeSceneEntity) {
        String nodeId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_EXE_NODE, request.getTrace().getTraceId());
        ExeNodeContext context = new ExeNodeContext();
        ChangeNodeCreateModel model = new ChangeNodeCreateModel(nodeId, cloudId, orderId, MetaChangeStepTypeEnum.STEP_ORDER,
                context, request, metaChangeSceneEntity,  ChangeScenarioEnum.getByCode(request.getChangeScenarioCode()));
        return changeNodeService.createExeNodeEntity(model);

    }

    private ChangeExecOrderSubmitRequestModel convertToOrderModel(ChangeExecOrderSubmitAndStartRequest request) {
        ChangeExecOrderSubmitRequestModel requestModel = exeChangeOrderRequestConverter.convert(request);
        if (request instanceof ChangeEventRequest) {
            ChangeEventRequest r = (ChangeEventRequest) request;
            Date finishTime = r.getFinishTime();
            if (finishTime == null) {
                finishTime = new Date();
            }
            requestModel.setFinishTime(finishTime);
            requestModel.setStatus(ExeOrderStatusEnum.SUCCESS);
        } else {
            requestModel.setStatus(ExeOrderStatusEnum.RUNNING);
        }
        return requestModel;
    }

    private void generateOrderTrace(ChangeExecOrderRequest request) {
        OpsChngTrace trace = request.getTrace();
        if (trace == null) {
            request.setTrace(changeTraceService
                    .generateTrace(ChangeScenarioEnum.getByCode(request.getChangeScenarioCode()), request.getBizExecOrderId()));
        }
    }
}
