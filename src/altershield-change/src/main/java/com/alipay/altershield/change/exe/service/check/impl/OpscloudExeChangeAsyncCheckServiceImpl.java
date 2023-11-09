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

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeContext;
import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.opscloud.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.opscloud.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.opscloud.change.exe.service.check.ChangeNodeService;
import com.alipay.opscloud.change.exe.service.check.OpscloudExeChangeAsyncCheckService;
import com.alipay.opscloud.change.exe.service.check.model.OpsCloudChangeNodeCreateModel;
import com.alipay.opscloud.change.exe.service.check.model.OpsCloudChangeStartNotifyRequestModel;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.opscloud.change.util.EntityUtil;
import com.alipay.opscloud.common.id.IdGenerator;
import com.alipay.opscloud.common.service.ServiceProcessTemplate;
import com.alipay.opscloud.framework.core.change.facade.request.*;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeCheckProgressResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeNotifySubmitResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.tools.common.id.IdBizCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

public class OpscloudExeChangeAsyncCheckServiceImpl implements OpscloudExeChangeAsyncCheckService {
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

    @Value("${opscloud.framework.server.common.cloudId}")
    private String cloudId;

    @Autowired
    private ExeNodeStateMachineManager exeNodeStateMachineManager;

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> startPreCheck(OpsCloudChangeStartNotifyRequestModel requestModel) {

        return ServiceProcessTemplate.wrapTryCatch(() ->
                transactionTemplate.execute(status -> {
                    OpsCloudChangeStartNotifyRequest request = requestModel.getOpsCloudChangeStartNotifyRequest();
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                            request.getChangeSceneKey());
                    if (metaChangeSceneEntity == null) {
                        return OpsCloudResult.illegalArgument("change scene not found, changeSceneKey=" + request.getChangeSceneKey());
                    }
                    ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderRepository.getChangeOrder(request.getPlatform(),
                            request.getBizExecOrderId());
                    if (exeChangeOrderEntity == null) {
                        return OpsCloudResult.illegalArgument(
                                "change order  not found, platform=" + request.getPlatform() + ", bizExecOrderId=" + request
                                        .getBizExecOrderId());
                    }
                    ExeNodeEntity exeNodeEntity = createExeNode(requestModel, request, exeChangeOrderEntity, metaChangeSceneEntity);
                    OpsCloudChangeNotifySubmitResult opsCloudChangeNotifySubmitResult = new OpsCloudChangeNotifySubmitResult();
                    opsCloudChangeNotifySubmitResult.setNodeId(exeNodeEntity.getNodeExeId());
                    opsCloudChangeNotifySubmitResult.setUrl(EntityUtil.buildDetailUrl(exeNodeEntity));
                    return new OpsCloudResult<>(opsCloudChangeNotifySubmitResult);
                }));
    }

    private ExeNodeEntity createExeNode(OpsCloudChangeStartNotifyRequestModel requestModel, OpsCloudChangeStartNotifyRequest request,
                                        ExeChangeOrderEntity exeChangeOrderEntity,
                                        MetaChangeSceneEntity metaChangeSceneEntity) {
        String nodeId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_EXE_NODE, request.getTrace().getTraceId());
        ExeNodeContext context = new ExeNodeContext();
        context.setReturnRuleWhenCheckFail(requestModel.getOpsCloudChangeStartNotifyRequest().isReturnRuleWhenCheckFail());
        OpsCloudChangeNodeCreateModel model = new OpsCloudChangeNodeCreateModel(nodeId, cloudId, exeChangeOrderEntity.getOrderId(),
                requestModel.getMetaChangeStepEntity(), context, request, metaChangeSceneEntity,
                exeChangeOrderEntity.getChangeScenarioCode());
        return changeNodeService.createExeNodeEntity(model);
    }

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> starPostCheck(OpsCloudChangeFinishNotifyRequest request) {

        return doProcess(request, (exeNodeStateMachine, entity, metaChangeSceneEntity) -> {
            exeNodeStateMachine.submitNodePostStartCheck(entity, request, metaChangeSceneEntity);
            OpsCloudChangeNotifySubmitResult opsCloudChangeNotifySubmitResult = new OpsCloudChangeNotifySubmitResult(entity.getNodeExeId(), true);
            opsCloudChangeNotifySubmitResult.setUrl(EntityUtil.buildDetailUrl(entity));
            return new OpsCloudResult<>(opsCloudChangeNotifySubmitResult);
        });
    }

    @Override
    public OpsCloudResult<OpsCloudChangeCheckProgressResult> retrieveChangeCheckResult(OpsCloudChangeRetrieveCheckRequest request) {
        return doProcess(request,
                (exeNodeStateMachine, entity, metaChangeSceneEntity) -> exeNodeStateMachine.retrieveCheckResult(entity,
                        request.getDefenseStage(), false));
    }

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> resubmitChangeCheck(OpsCloudChangeResumbitStartNotifyRequest request) {

        return doProcess(request, (exeNodeStateMachine, entity, metaChangeSceneEntity) -> {
            exeNodeStateMachine.submitNodePreStartCheck(entity, metaChangeSceneEntity);
            OpsCloudChangeNotifySubmitResult opsCloudChangeNotifySubmitResult = new OpsCloudChangeNotifySubmitResult(entity.getNodeExeId(), false);
            opsCloudChangeNotifySubmitResult.setUrl(EntityUtil.buildDetailUrl(entity));
            return new OpsCloudResult<>(opsCloudChangeNotifySubmitResult);
        });

    }

    @Override
    public OpsCloudResult<OpsCloudChangeSkipCheckResult> skipChangeCheck(OpsCloudChangeSkipCheckRequest request) {
        return doProcess(request,
                (exeNodeStateMachine, entity, metaChangeSceneEntity) -> exeNodeStateMachine.setIgnoreNodeCheck(entity,
                        request.getDefenseStage(),
                        request));
    }

    private <R> OpsCloudResult<R> doProcess(OpsCloudChangeCheckRequest request, TransferStateHandler<R> transferStateHandler) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {

            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(
                    request.getChangeSceneKey());
            if (metaChangeSceneEntity == null) {
                return OpsCloudResult.illegalArgument("change scene not found, changeSceneKey=" + request.getChangeSceneKey());
            }
            ExeNodeEntity exeNodeEntity = exeChangeNodeRepository.lockNodeById(request.getNodeId());
            if (exeNodeEntity == null) {
                return OpsCloudResult.notSupport("node not found, nodeId:" + request.getNodeId());
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
        OpsCloudResult<R> doTransfer(ExeNodeStateMachine exeNodeStateMachine, ExeNodeEntity entity,
                                     MetaChangeSceneEntity metaChangeSceneEntity);
    }
}
