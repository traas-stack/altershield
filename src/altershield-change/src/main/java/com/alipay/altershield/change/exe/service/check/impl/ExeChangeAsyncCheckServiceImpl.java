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

import com.alipay.altershield.change.util.EntityUtil;
import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.altershield.change.exe.service.check.ChangeNodeService;
import com.alipay.altershield.change.exe.service.check.ExeChangeAsyncCheckService;
import com.alipay.altershield.change.exe.service.check.model.ChangeNodeCreateModel;
import com.alipay.altershield.change.exe.service.check.model.ChangeStartNotifyRequestModel;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckProgressResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeNotifySubmitResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeContext;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

public class ExeChangeAsyncCheckServiceImpl implements ExeChangeAsyncCheckService {
    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ExeChangeOrderRepository exeChangeOrderRepository;

    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ChangeNodeService changeNodeService;

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    @Value("${altershield.framework.server.common.cloudId}")
    private String cloudId;

    @Autowired
    private ExeNodeStateMachineManager exeNodeStateMachineManager;

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> startPreCheck(ChangeStartNotifyRequestModel requestModel) {
        return ServiceProcessTemplate.wrapTryCatch(() ->
                transactionTemplate.execute(status -> {
                    ChangeStartNotifyRequest request = requestModel.getChangeStartNotifyRequest();
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                            request.getChangeSceneKey());
                    if (metaChangeSceneEntity == null) {
                        return AlterShieldResult.illegalArgument("change scene not found, changeSceneKey=" + request.getChangeSceneKey());
                    }
                    ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderRepository.getChangeOrder(request.getPlatform(),
                            request.getBizExecOrderId());
                    if (exeChangeOrderEntity == null) {
                        return AlterShieldResult.illegalArgument(
                                "change order  not found, platform=" + request.getPlatform() + ", bizExecOrderId=" + request
                                        .getBizExecOrderId());
                    }
                    ExeNodeEntity exeNodeEntity = createExeNode(requestModel, request, exeChangeOrderEntity, metaChangeSceneEntity);
                    ChangeNotifySubmitResult changeNotifySubmitResult = new ChangeNotifySubmitResult();
                    changeNotifySubmitResult.setNodeId(exeNodeEntity.getNodeExeId());
                    changeNotifySubmitResult.setUrl(EntityUtil.buildNodeDetailUrl(exeNodeEntity));
                    return new AlterShieldResult<>(changeNotifySubmitResult);
                }));
    }

    private ExeNodeEntity createExeNode(ChangeStartNotifyRequestModel requestModel, ChangeStartNotifyRequest request,
                                        ExeChangeOrderEntity exeChangeOrderEntity,
                                        MetaChangeSceneEntity metaChangeSceneEntity) {
        String nodeId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_EXE_NODE, request.getTrace().getTraceId());
        ExeNodeContext context = new ExeNodeContext();
        context.setReturnRuleWhenCheckFail(requestModel.getChangeStartNotifyRequest().isReturnRuleWhenCheckFail());
        ChangeNodeCreateModel model = new ChangeNodeCreateModel(nodeId, cloudId, exeChangeOrderEntity.getOrderId(),
                requestModel.getMetaChangeStepEntity(), context, request, metaChangeSceneEntity,
                exeChangeOrderEntity.getChangeScenarioCode());
        return changeNodeService.createExeNodeEntity(model);
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> starPostCheck(ChangeFinishNotifyRequest request) {

        return doProcess(request, (exeNodeStateMachine, entity, metaChangeSceneEntity) -> {
            exeNodeStateMachine.submitNodePostStartCheck(entity, request, metaChangeSceneEntity);
            ChangeNotifySubmitResult changeNotifySubmitResult = new ChangeNotifySubmitResult(entity.getNodeExeId(), true);
            changeNotifySubmitResult.setUrl(EntityUtil.buildNodeDetailUrl(entity));
            return new AlterShieldResult<>(changeNotifySubmitResult);
        });
    }

    @Override
    public AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(ChangeRetrieveCheckRequest request) {
        return doProcess(request,
                (exeNodeStateMachine, entity, metaChangeSceneEntity) -> exeNodeStateMachine.retrieveCheckResult(entity,
                        request.getDefenseStage(), false));
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeCheck(ChangeResubmitStartNotifyRequest request) {

        return doProcess(request, (exeNodeStateMachine, entity, metaChangeSceneEntity) -> {
            exeNodeStateMachine.submitNodePreStartCheck(entity, metaChangeSceneEntity);
            ChangeNotifySubmitResult changeNotifySubmitResult = new ChangeNotifySubmitResult(entity.getNodeExeId(), false);
            changeNotifySubmitResult.setUrl(EntityUtil.buildNodeDetailUrl(entity));
            return new AlterShieldResult<>(changeNotifySubmitResult);
        });

    }

    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipCheckRequest request) {
        return doProcess(request,
                (exeNodeStateMachine, entity, metaChangeSceneEntity) -> exeNodeStateMachine.setIgnoreNodeCheck(entity,
                        request.getDefenseStage(),
                        request));
    }

    private <R> AlterShieldResult<R> doProcess(ChangeCheckRequest request, TransferStateHandler<R> transferStateHandler) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {

            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                    request.getChangeSceneKey());
            if (metaChangeSceneEntity == null) {
                return AlterShieldResult.illegalArgument("change scene not found, changeSceneKey=" + request.getChangeSceneKey());
            }
            ExeNodeEntity exeNodeEntity = exeChangeNodeRepository.lockNodeById(request.getNodeId());
            if (exeNodeEntity == null) {
                return AlterShieldResult.notSupport("node not found, nodeId:" + request.getNodeId());
            }
            ExeNodeStateMachine exeNodeStateMachine = exeNodeStateMachineManager.getExeNodeStateMachine(exeNodeEntity.getStatus());

            return transactionTemplate.execute(status -> transferStateHandler.doTransfer(exeNodeStateMachine, exeNodeEntity, metaChangeSceneEntity));
        });

    }

    private interface TransferStateHandler<R> {
        /**
         * Do transfer ops cloud result.
         *
         * @param exeNodeStateMachine   the exe node state machine
         * @param entity                the entity
         * @param metaChangeSceneEntity the meta change scene entity
         * @return the ops cloud result
         */
        AlterShieldResult<R> doTransfer(ExeNodeStateMachine exeNodeStateMachine, ExeNodeEntity entity,
                                        MetaChangeSceneEntity metaChangeSceneEntity);
    }
}
