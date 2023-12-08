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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.impl;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.defender.framework.AbstractDefenderService;
import com.alipay.altershield.defender.framework.enums.DefenseRangeTypeEnum;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.defender.framework.task.DefenderSyncDetectTask;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.service.ExeChangeNodeService;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.defender.DefenderDetectService;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.enums.DefenderPluginInvokeTypeEnum;
import com.alipay.altershield.shared.defender.model.ChangeFilter;
import com.alipay.altershield.shared.defender.request.ChangeFilterRequest;
import com.alipay.altershield.shared.defender.request.DefenderDetectRequest;
import com.alipay.altershield.shared.defender.result.DefenderDetectResult;
import com.alipay.altershield.shared.defender.result.DefenderDetectSubmitResult;
import com.alipay.altershield.shared.pluginmarket.PluginMarket;
import com.alipay.altershield.shared.schedule.event.defender.DefenderCheckProcessEvent;
import com.alipay.altershield.shared.schedule.event.defender.DefenderDetectEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.request.ChangeContent;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The main process service that initiates change risk prevention detection
 *
 * @author yhaoxuan
 * @version DefenderDetectServiceImpl.java, v 0.1 2022年08月17日 4:02 下午 yhaoxuan
 */
@Service
public class DefenderDetectServiceImpl extends AbstractDefenderService implements DefenderDetectService {

    @Autowired
    private AlterShieldSchedulerEventPublisher alterShieldSchedulerEventPublisher;

    @Autowired
    private PluginMarket pluginMarket;

    @Autowired
    private ExeChangeNodeService changeNodeService;

    /**
     * Change Defense Master Log
     */
    private static final Logger DEFENDER = Loggers.DEFENDER;

    /**
     * Synchronous execution of change defense detection entry - only available for pre-production in G1 generation
     * Note: The defense rules for implementing asynchronous defense SPI are not executed in synchronous defense.
     *
     * @param request Change defense detection request structure
     * @return Change defense detection result structure
     */
    @Override
    public AlterShieldResult<DefenderDetectResult> syncDetect(DefenderDetectRequest request) {

        // 1.0 fault tolerance
        if (request == null) {
            return AlterShieldResult.illegalArgument("request can`t be null");
        }

        AlterShieldLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                "starting sync detect", request.getChangeOrderId(), request.getNodeId());

        String detectGroupId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_DEFENDER_DETECT_GROUP_ID, request.getNodeId());
        boolean checkPass = true;

        try {
            // 2.0 Get the final list of defense rules to be executed
            Set<MetaDefenderRuleEntity> matchedRules = matchDefenseRule(request);

            if (CollectionUtils.isEmpty(matchedRules)) {
                // Rule list is empty
                AlterShieldLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                        "Defense rule matching list is empty", request.getChangeOrderId(), request.getNodeId());
                return new AlterShieldResult<>(DefenderDetectResult.pass(detectGroupId));
            }

            // 2.1 This method only implements the defense capability of synchronous SPI access, so asynchronous ones must be filtered out.
            matchedRules = matchedRules.stream()
                    .filter(r -> DefenderPluginInvokeTypeEnum.SYNC.getType().equals(r.getPluginInvokeType()))
                    .collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(matchedRules)) {
                // Rule list is empty
                AlterShieldLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                        "The defense rules list is empty after filtering", request.getChangeOrderId(), request.getNodeId());
                return new AlterShieldResult<>(DefenderDetectResult.pass(detectGroupId));
            }

            // 3.0 Schedule the execution of defense rules and create a task for each defense rule
            CountDownLatch countDownLatch = new CountDownLatch(matchedRules.size());
            List<FutureTask<DefenderDetectPluginResult>> tasks = new ArrayList<>(matchedRules.size());
            for (MetaDefenderRuleEntity rule : matchedRules) {
                DefenderSyncDetectTask task = new DefenderSyncDetectTask(rule, detectGroupId, countDownLatch, request);
                FutureTask<DefenderDetectPluginResult> futureTask = new FutureTask<>(task);
                submitSyncDetect(futureTask, request, rule);
                tasks.add(futureTask);
            }

            // 4.0 Start detection and update the node status to detection (pre/post)
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckStarted(nodeEntity, request.getDefenseStage(), detectGroupId);

            // 5.0 Determine the detection results
            long timeout = request.getTimeout() == null ? AlterShieldConstant.DEFENDER_SYNC_DETECT_TIMEOUT : request.getTimeout();
            boolean success = countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            if (!success) {
                checkPass = true;
                return new AlterShieldResult<>(DefenderDetectResult.timeout(detectGroupId, "Defense execution timeout is greater than" + timeout + "ms"));
            }

            // 5.1 Get detection results
            for (FutureTask<DefenderDetectPluginResult> task : tasks) {
                DefenderDetectPluginResult result = task.get();
                // 5.1.1 If a block occurs, then block
                if (DefenderStatusEnum.FAIL.equals(result.getStatus())) {
                    checkPass = false;
                    DefenderDetectResult failResult = new DefenderDetectResult();
                    failResult.setAllFinish(true);
                    failResult.setDefensed(true);
                    failResult.setStatus(DefenderStatusEnum.FAIL);
                    failResult.setDetectGroupId(detectGroupId);
                    failResult.setMsg("There is a defense rule that fails the detection");
                    return new AlterShieldResult<>(failResult);
                } else if (DefenderStatusEnum.EXCEPTION.equals(result.getStatus())) {
                    // 5.1.2 If the detection is abnormal, need to look at the rule's exception handling strategy.
                    MetaDefenderRuleEntity targetRule = matchedRules.stream()
                            .filter(r -> r.getId().equalsIgnoreCase(result.getRuleId()))
                            .findFirst().orElse(null);
                    if (targetRule != null && ExceptionStrategyEnum.BLOCK.equals(targetRule.getExceptionStrategy())) {
                        checkPass = false;
                        DefenderDetectResult failResult = new DefenderDetectResult();
                        failResult.setAllFinish(true);
                        failResult.setDefensed(true);
                        failResult.setStatus(DefenderStatusEnum.FAIL);
                        failResult.setDetectGroupId(detectGroupId);
                        failResult.setMsg("There is a defense rule execution exception, and the rule exception handling strategy is to block changes");
                        return new AlterShieldResult<>(failResult);
                    }
                }
            }
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", DEFENDER, "DefenderDetectServiceImpl", "syncDetect", "failed",
                    "do sync detect got an exception", request.getChangeOrderId(), request.getNodeId(), e);
            DefenderDetectResult detectResult = DefenderDetectResult.exception(detectGroupId, "Defense against execution exceptions");
            return new AlterShieldResult<>(detectResult);
        } finally {
            // 6.0 Start detection and update the node status to detection completed (pre/post)
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckFinish(nodeEntity, request.getDefenseStage(), checkPass);
        }

        return new AlterShieldResult<>(DefenderDetectResult.pass(detectGroupId));
    }

    /**
     * Asynchronous execution change defense verification entry - G1 post, G2, G3, G4 use
     *
     * @param request Change defense detection request structure
     * @return Change defense detection result structure
     */
    @Override
    public AlterShieldResult<DefenderDetectSubmitResult> asyncDetect(DefenderDetectRequest request) {
        // 1.0 fault tolerance
        if (request == null) {
            return AlterShieldResult.illegalArgument("request can`t be null");
        }

        AlterShieldLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "asyncDetect",
                "starting async detect", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());

        DefenderDetectSubmitResult result = new DefenderDetectSubmitResult();
        result.setChangeOrderId(request.getChangeOrderId());
        result.setNodeId(request.getNodeId());
        result.setStage(request.getDefenseStage());
        // 1.1 Emergency changes do not perform defense detection
        if (request.isEmergency()) {
            result.setDefensed(false);
            result.setMsg("Emergency changes do not perform defense detection");
            return new AlterShieldResult<>(result);
        }

        try {
            String detectGroupId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_DEFENDER_DETECT_GROUP_ID, request.getNodeId());
            // 2.0 Get the final list of defense rules to be executed
            Set<MetaDefenderRuleEntity> matchedRules = matchDefenseRule(request);
            if (CollectionUtils.isEmpty(matchedRules)) {
                result.setDefensed(false);
                result.setMsg("No defense rules were matched");
                return new AlterShieldResult<>(result);
            }

            // 3.0 Inserting detection records and scheduling events
            List<ExeDefenderDetectEntity> toAddDetects = new ArrayList<>(matchedRules.size());
            // 3.1 A batch of detection corresponds to a detection group id
            for (MetaDefenderRuleEntity rule : matchedRules) {
                // 3.2 Build defense detection record entity
                ExeDefenderDetectEntity detect = createDetectEntity(request.getChangeOrderId(), request.getNodeId(),
                        detectGroupId, request.getDefenseStage(), rule);
                toAddDetects.add(detect);
            }

            // 3.3 Start detection and update the node status to detecting (pre/post)
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckStarted(nodeEntity, request.getDefenseStage(), detectGroupId);

            Boolean transactionRst = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus status) {
                    // 4.0 Create detection records in batches
                    boolean isSuccess = exeDefenderDetectRepository.batchInsert(toAddDetects);
                    if (!isSuccess) {
                        AlterShieldLoggerManager.log("error", DEFENDER, "DefenderDetectServiceImpl", "asyncDetect",
                                "create detect records failed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage(),
                                detectGroupId);
                        status.setRollbackOnly();
                    }

                    // 4.1 Create a defense execution event for each detection record and publish it to the event center for scheduling
                    for (ExeDefenderDetectEntity detect : toAddDetects) {
                        DefenderDetectEvent detectEvent = createDetectEvent(request, detect);
                        alterShieldSchedulerEventPublisher.publish(request.getChangeOrderId(), detectEvent);
                    }

                    // 4.2 Create another defense status polling event to aggregate the overall defense detection results.
                    DefenderCheckProcessEvent checkProcessEvent = createCheckProcessEvent(request.getChangeOrderId(), request.getNodeId(),
                            detectGroupId, request.getDefenseStage(), request.getChangeSceneKey(), request.getStepTypeEnum());
                    alterShieldSchedulerEventPublisher.publish(request.getChangeOrderId(), checkProcessEvent);
                    return true;
                }
            });

            if (transactionRst != null && transactionRst) {
                result.setChangeOrderId(request.getChangeOrderId());
                result.setNodeId(request.getNodeId());
                result.setDetectGroupId(detectGroupId);
                result.setStage(request.getDefenseStage());

                AlterShieldLoggerManager.log("info", Loggers.DEFENDER, "DefenderDetectServiceImpl", "asyncDetect", "success",
                        "submit detect task succeed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());
                return new AlterShieldResult<>(result);
            } else {
                AlterShieldLoggerManager.log("info", Loggers.DEFENDER, "DefenderDetectServiceImpl", "asyncDetect", "fail",
                        "submit detect task failed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());
                return AlterShieldResult.systemError("Failed to submit detection task");
            }

        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", DEFENDER, e, "DefenderDetectServiceImpl", "asyncDetect", "fail",
                    "do async detect got an exception", request.getChangeOrderId(), request.getNodeId(), e);
            return AlterShieldResult.systemError("unknown exception");
        }
    }

    /**
     * Match defense rules
     *
     * @param request Change defense detection request structure
     * @return List of matched defense rules
     */
    private Set<MetaDefenderRuleEntity> matchDefenseRule(DefenderDetectRequest request) {

        Set<String> defenseRangeKeys = new HashSet<>();
        defenseRangeKeys.add(request.getChangeSceneKey());
        // 1.0 Add change Tags
        if (!CollectionUtils.isEmpty(request.getChangeTagIds())) {
            defenseRangeKeys.addAll(request.getChangeTagIds());
        }

        // 2.0 Get a list of defense rules
        AlterShieldLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "get defenseRangeKeys", defenseRangeKeys.size(), JSONUtil.toJSONString(defenseRangeKeys, false), request.getDefenseStage());
        List<MetaDefenderRuleEntity> matchedRules = metaDefenderRuleRepository
                .selectByRangeKeysAndStage(defenseRangeKeys, request.getDefenseStage());
        if (CollectionUtils.isEmpty(matchedRules)){
            AlterShieldLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                    "get defense rule list size", matchedRules.size());
            return Collections.emptySet();
        }

        AlterShieldLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "get defense rule list size", matchedRules.size(), JSONUtil.toJSONString(matchedRules.stream().map(MetaDefenderRuleEntity::getName).collect(Collectors.toList())));

        if (request.getChangeExecuteInfo() == null
                || request.getChangeExecuteInfo().isOrderPhase()) {
            // 3.0 If order phase is true, it means that the detection is at the change order level and rules associated with the batch dimension are filtered out.
            matchedRules = matchedRules.stream()
                    .filter(r -> !DefenseRangeTypeEnum.CHANGE_BATCH.equals(r.getDefenseRangeType()))
                    .collect(Collectors.toList());
        } else {
            // 3.1 If order phase is false, it means batch-level detection, and rules associated with the work order dimension are filtered out.
            matchedRules = matchedRules.stream()
                    .filter(r -> !DefenseRangeTypeEnum.CHANGE_SCENE.equals(r.getDefenseRangeType()))
                    .collect(Collectors.toList());
        }

        // 4.0 Execute filter conditions for each defense rule
        List<MetaDefenderRuleEntity> matchResult = new ArrayList<>();
        for (MetaDefenderRuleEntity rule : matchedRules) {
            // 4.1 Fault tolerance, if change Filter is empty, default matched
            if (rule.getChangeFilter() == null || rule.getChangeFilter().readObject() == null) {
                AlterShieldLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                        "the change filter is empty, default matched", request.getNodeId(), rule.getId(), rule.getName());
                matchResult.add(rule);
                continue;
            }

            // 4.3 Perform matching of actual filter criteria
            ChangeFilter changeFilter = rule.getChangeFilter().readObject();
            boolean isMatch = changeFilter.isMatch(buildChangeFilterRequest(request));

            if (isMatch) {
                matchResult.add(rule);
            }
        }

        AlterShieldLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "matched rule list size", matchResult.size());
        return new HashSet<>(matchResult);
    }

    /**
     * Construct a defense rule filtering request structure
     *
     * @param req Change defense detection request structure
     * @return Build results
     */
    private ChangeFilterRequest buildChangeFilterRequest(DefenderDetectRequest req) {
        // The nested classes in req are all annotated with Not Null, so there is no need to judge null.
        ChangeFilterRequest request = new ChangeFilterRequest();
        request.setSceneCodes(Collections.singleton(IdUtil.getChangeScenarioEnum(req.getNodeId()).getCode()));

        if (!Objects.isNull(req.getChangeBaseInfo())) {
            request.setOperator(req.getChangeBaseInfo().getExecutor());
            request.setSourcePlatform(req.getChangeBaseInfo().getPlatform());
            request.setChangeTile(req.getChangeBaseInfo().getChangeTitle());
            request.setChangeParam(req.getChangeBaseInfo().getChangeParamJSON());

            // Change content information
            ChangeContent changeContent = req.getChangeBaseInfo().getChangeContent();
            if (!Objects.isNull(changeContent) && !CollectionUtils.isEmpty(changeContent.getChangeContentInstance())) {
                request.setChangeTargetTypes(Stream.of(changeContent.getChangeContentType()).collect(Collectors.toSet()));
                request.setChangeTargetInstances(new HashSet<>(changeContent.getChangeContentInstance()));
            }
        }


        // Batch information assembly
        if (!Objects.isNull(req.getChangeExecuteInfo())) {
            request.setChangePhase(req.getChangeExecuteInfo().getChangePhase());
            request.setChangePhaseBatchNo(req.getChangeExecuteInfo().getBatchNo());
            request.setLastBatch(req.getChangeExecuteInfo().isLastBatch());
            request.setLastBatchInPhase(req.getChangeExecuteInfo().isLastBatchInPhase());
            request.setTotalBatchNum(req.getChangeExecuteInfo().getTotalBatchNum());
            request.setTotalBatchNumInPhase(req.getChangeExecuteInfo().getTotalBatchInPhase());
        }

        // Influence information
        if (!Objects.isNull(req.getChangeInfluenceInfo())) {
            request.setChangeApps(req.getChangeInfluenceInfo().getApps());
            request.setEnvs(req.getChangeInfluenceInfo().getEnvs());
            request.setIdcs(req.getChangeInfluenceInfo().getPhysicDeployCells());
            request.setLogicalCells(req.getChangeInfluenceInfo().getLogicDeployCells());
            request.setHosts(req.getChangeInfluenceInfo().getHosts());
            request.setTenants(req.getChangeInfluenceInfo().getTenants());
        }
        return request;
    }

    /**
     * Create a change defense detection event entity
     *
     * @param request Defense detection request structure
     * @param detect Check record entity
     * @return Event entity
     */
    private DefenderDetectEvent createDetectEvent(DefenderDetectRequest request, ExeDefenderDetectEntity detect) {
        DefenderDetectEvent event = new DefenderDetectEvent();
        event.setChangeSceneKey(request.getChangeSceneKey());
        event.setChangeKey(request.getChangeKey());
        event.setPluginKey(detect.getPluginKey());
        event.setMainClass(detect.getMainClass());

        event.setChangeOrderId(request.getChangeOrderId());
        event.setNodeId(request.getNodeId());
        event.setDetectGroupId(detect.getDetectGroupId());
        event.setDetectExeId(detect.getDetectExeId());
        event.setRuleId(detect.getRuleId());
        event.setExternalId(detect.getExternalId());

        event.setStage(detect.getStage());
        event.setChangeStartTime(request.getChangeExecuteInfo().getChangeStartTime());
        event.setChangeFinishTime(request.getChangeExecuteInfo().getChangeFinishTime());
        event.setOrderPhase(request.getChangeExecuteInfo().isOrderPhase());
        return event;
    }

    /**
     * Create a change prevention result check event entity
     *
     * @param changeOrderId Change order id
     * @param nodeId Change node id
     * @param detectGroupId Detection group id
     * @param stage Pre or post
     * @return Event entity
     */
    private DefenderCheckProcessEvent createCheckProcessEvent(String changeOrderId, String nodeId,
                                                              String detectGroupId, DefenseStageEnum stage,
                                                              String changeSceneKey, MetaChangeStepTypeEnum changeStepTypeEnum) {
        DefenderCheckProcessEvent event = new DefenderCheckProcessEvent();
        event.setChangeOrderId(changeOrderId);
        event.setNodeId(nodeId);
        event.setDetectGroupId(detectGroupId);
        event.setStage(stage);
        event.setChangeSceneKey(changeSceneKey);
        event.setChangeStepType(changeStepTypeEnum);

        return event;
    }
}