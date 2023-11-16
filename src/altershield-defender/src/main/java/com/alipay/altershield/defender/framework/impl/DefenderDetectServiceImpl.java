/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.service.ExeChangeNodeService;
import com.alipay.opscloud.api.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.opscloud.api.defender.DefenderDetectService;
import com.alipay.opscloud.api.defender.entity.ExeDefenderDetectEntity;
import com.alipay.opscloud.api.defender.enums.DefenderPluginInvokeTypeEnum;
import com.alipay.opscloud.api.defender.model.ChangeFilter;
import com.alipay.opscloud.api.defender.request.ChangeFilterRequest;
import com.alipay.opscloud.api.defender.request.DefenderDetectRequest;
import com.alipay.opscloud.api.defender.result.DefenderDetectResult;
import com.alipay.opscloud.api.defender.result.DefenderDetectSubmitResult;
import com.alipay.opscloud.api.pluginmarket.PluginMarket;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderCheckProcessEvent;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderDetectEvent;
import com.alipay.opscloud.api.scheduler.event.publish.OpsCloudSchedulerEventPublisher;
import com.alipay.opscloud.defender.AbstractDefenderService;
import com.alipay.opscloud.defender.enums.DefenseRangeTypeEnum;
import com.alipay.opscloud.defender.enums.ExceptionStrategyEnum;
import com.alipay.opscloud.defender.meta.entity.MetaDefenderRuleEntity;
import com.alipay.opscloud.defender.task.DefenderSyncDetectTask;
import com.alipay.opscloud.framework.common.util.JSONUtil;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.opscloud.spi.defender.model.request.ChangeContent;
import com.alipay.opscloud.spi.defender.model.result.DefenderDetectPluginResult;
import com.alipay.opscloud.spi.influence.model.enums.ExtensionKey;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.id.IdBizCodeEnum;
import com.alipay.opscloud.tools.common.id.IdUtil;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 发起变更风险防御校验的主流程服务
 *
 * @author haoxuan
 * @version DefenderDetectServiceImpl.java, v 0.1 2022年08月17日 4:02 下午 haoxuan
 */
@Service
public class DefenderDetectServiceImpl extends AbstractDefenderService implements DefenderDetectService {

    @Autowired
    private OpsCloudSchedulerEventPublisher schedulerEventPublisher;

    @Autowired
    private PluginMarket pluginMarket;

    @Autowired
    private ExeChangeNodeService changeNodeService;

    /**
     * 变更防御主日志
     */
    private static final Logger DEFENDER = Loggers.DEFENDER;

    /**
     * 一级架构域key标识
     */
    private static final String MAIN_ARCH_DOMAIN = "mainDomains";

    /**
     * 二级架构域key标识
     */
    private static final String SUB_ARCH_DOMAIN = "subDomains";

    /**
     * 同步执行变更防御校验入口 - 仅提供G1代际的前置使用
     * 注：同步防御中不执行实现异步防御SPI的防御规则，产品层已做拦截，此处做容错
     *
     * @param request 变更防御校验请求结构体
     * @return 变更防御校验结果结构体
     */
    @Override
    public OpsCloudResult<DefenderDetectResult> syncDetect(DefenderDetectRequest request) {

        // 1.0 容错
        if (request == null) {
            return OpsCloudResult.illegalArgument("request can`t be null");
        }

        // whiteList check
        if (!whiteListCheck(request.getChangeSceneKey())) {
            OpsCloudLoggerManager.log("info", DEFENDER, "白名单校验不通过", request.getChangeSceneKey());
            DefenderDetectResult result = new DefenderDetectResult();
            result.setDefensed(false);
            result.setMsg("白名单校验不通过");
            return OpsCloudResult.succeed("白名单校验不通过", result);
        }

        OpsCloudLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                "starting sync detect", request.getChangeOrderId(), request.getNodeId());

        String detectGroupId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_DEFENDER_DETECT_GROUP_ID, request.getNodeId());
        boolean checkPass = true;

        try {
            // 2.0 获取最终要执行的防御规则列表
            Set<MetaDefenderRuleEntity> matchedRules = matchDefenseRule(request);

            if (CollectionUtils.isEmpty(matchedRules)) {
                // 规则列表为空
                OpsCloudLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                        "防御规则匹配列表为空", request.getChangeOrderId(), request.getNodeId());
                return new OpsCloudResult<>(DefenderDetectResult.pass(detectGroupId));
            }

            // 2.1 本方法只执行同步SPI接入的防御能力，所以要把异步的过滤掉
            matchedRules = matchedRules.stream()
                    .filter(r -> DefenderPluginInvokeTypeEnum.SYNC.getType().equals(r.getPluginInvokeType()))
                    .collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(matchedRules)) {
                // 规则列表为空
                OpsCloudLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "syncDetect",
                        "防御规则过滤后列表为空", request.getChangeOrderId(), request.getNodeId());
                return new OpsCloudResult<>(DefenderDetectResult.pass(detectGroupId));
            }

            // 3.0 调度防御规则执行，为每一个防御规则创建一个task
            CountDownLatch countDownLatch = new CountDownLatch(matchedRules.size());
            List<FutureTask<DefenderDetectPluginResult>> tasks = new ArrayList<>(matchedRules.size());
            for (MetaDefenderRuleEntity rule : matchedRules) {
                DefenderSyncDetectTask task = new DefenderSyncDetectTask(rule, detectGroupId, countDownLatch, request);
                FutureTask<DefenderDetectPluginResult> futureTask = new FutureTask<>(task);
                submitSyncDetect(futureTask, request, rule);
                tasks.add(futureTask);
            }

            // 4.0 开始校验，更新node状态为校验中（前/后置）
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckStarted(nodeEntity, request.getDefenseStage(), detectGroupId);

            // 5.0 判断校验结果
            long timeout = request.getTimeout() == null ? OpsCloudConstant.DEFENDER_SYNC_DETECT_TIMEOUT : request.getTimeout();
            boolean success = countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            if (!success) {
                checkPass = true;
                return new OpsCloudResult<>(DefenderDetectResult.timeout(detectGroupId, "防御执行超时，已大于" + timeout + "ms"));
            }

            // 5.1 获取校验结果
            for (FutureTask<DefenderDetectPluginResult> task : tasks) {
                DefenderDetectPluginResult result = task.get();
                // 5.1.1 出现一个阻断，则阻断
                if (DefenderStatusEnum.FAIL.equals(result.getStatus())) {
                    checkPass = false;
                    DefenderDetectResult failResult = new DefenderDetectResult();
                    failResult.setAllFinish(true);
                    failResult.setDefensed(true);
                    failResult.setStatus(DefenderStatusEnum.FAIL);
                    failResult.setDetectGroupId(detectGroupId);
                    failResult.setMsg("存在防御规则校验不通过");
                    return new OpsCloudResult<>(failResult);
                } else if (DefenderStatusEnum.EXCEPTION.equals(result.getStatus())) {
                    // 5.1.2 如果校验异常，需要看下规则的异常处理策略
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
                        failResult.setMsg("存在防御规则执行异常，且该规则异常处理策略为阻断变更");
                        return new OpsCloudResult<>(failResult);
                    }
                }
            }
        } catch (Exception e) {
            OpsCloudLoggerManager.log("error", DEFENDER, "DefenderDetectServiceImpl", "syncDetect", "failed",
                    "do sync detect got an exception", request.getChangeOrderId(), request.getNodeId(), e);
            DefenderDetectResult detectResult = DefenderDetectResult.exception(detectGroupId, "防御执行异常");
            return new OpsCloudResult<>(detectResult);
        } finally {
            // 6.0 开始校验，更新node状态为校验完成（前/后置）
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckFinish(nodeEntity, request.getDefenseStage(), checkPass);
        }

        return new OpsCloudResult<>(DefenderDetectResult.pass(detectGroupId));
    }

    /**
     * 异步执行变更防御校验入口 - G1后置、G2、G3、G4代际使用
     *
     * @param request 变更防御校验请求结构体
     * @return 变更防御校验结果结构体
     */
    @Override
    public OpsCloudResult<DefenderDetectSubmitResult> asyncDetect(DefenderDetectRequest request) {
        // 1.0 容错
        if (request == null) {
            return OpsCloudResult.illegalArgument("request can`t be null");
        }

        // whiteList check
        if (!whiteListCheck(request.getChangeSceneKey())) {
            OpsCloudLoggerManager.log("info", DEFENDER, "白名单校验不通过", request.getChangeSceneKey());
            DefenderDetectSubmitResult result = new DefenderDetectSubmitResult();
            result.setDefensed(false);
            result.setMsg("白名单校验不通过");
            return OpsCloudResult.succeed("白名单校验不通过", result);
        }

        OpsCloudLoggerManager.log("info", DEFENDER, "DefenderDetectServiceImpl", "asyncDetect",
                "starting async detect", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());

        DefenderDetectSubmitResult result = new DefenderDetectSubmitResult();
        result.setChangeOrderId(request.getChangeOrderId());
        result.setNodeId(request.getNodeId());
        result.setStage(request.getDefenseStage());
        // 1.1 应急变更不做防御
        if (request.isEmergency()) {
            result.setDefensed(false);
            result.setMsg("应急变更不进行防御校验");
            return new OpsCloudResult<>(result);
        }

        try {
            // 2.0 获取最终要执行的防御规则列表
            Set<MetaDefenderRuleEntity> matchedRules = matchDefenseRule(request);
            if (CollectionUtils.isEmpty(matchedRules)) {
                result.setDefensed(false);
                result.setMsg("本次未匹配到防御规则");
                return new OpsCloudResult<>(result);
            }

            // 3.0 插入校验记录与调度事件
            List<ExeDefenderDetectEntity> toAddDetects = new ArrayList<>(matchedRules.size());
            // 3.1 一批校验对应一个detectGroupId
            String detectGroupId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_DEFENDER_DETECT_GROUP_ID, request.getNodeId());
            for (MetaDefenderRuleEntity rule : matchedRules) {
                // 3.2 构建防御校验记录实体
                ExeDefenderDetectEntity detect = createDetectEntity(request.getChangeOrderId(), request.getNodeId(),
                        detectGroupId, request.getDefenseStage(), rule);
                toAddDetects.add(detect);
            }

            // 3.3 开始校验，更新node状态为校验中（前/后置）
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            changeNodeService.setNodeCheckStarted(nodeEntity, request.getDefenseStage(), detectGroupId);

            Boolean transactionRst = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus status) {
                    // 4.0 批量创建校验记录
                    boolean isSuccess = exeDefenderDetectRepository.batchInsert(toAddDetects);
                    if (!isSuccess) {
                        OpsCloudLoggerManager.log("error", DEFENDER, "DefenderDetectServiceImpl", "asyncDetect",
                                "create detect records failed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage(),
                                detectGroupId);
                        status.setRollbackOnly();
                    }

                    // 4.1 为每个校验记录创建一个防御执行事件，publish到事件中心进行调度
                    for (ExeDefenderDetectEntity detect : toAddDetects) {
                        DefenderDetectEvent detectEvent = createDetectEvent(request, detect);
                        schedulerEventPublisher.publish(request.getChangeOrderId(), detectEvent);
                    }

                    // 4.2 再创建一个防御状态轮询的事件，用于聚合整体防御校验结果
                    DefenderCheckProcessEvent checkProcessEvent = createCheckProcessEvent(request.getChangeOrderId(), request.getNodeId(),
                            detectGroupId, request.getDefenseStage(), request.getChangeSceneKey(), request.getStepTypeEnum());
                    schedulerEventPublisher.publish(request.getChangeOrderId(), checkProcessEvent);
                    return true;
                }
            });

            if (transactionRst != null && transactionRst) {
                result.setChangeOrderId(request.getChangeOrderId());
                result.setNodeId(request.getNodeId());
                result.setDetectGroupId(detectGroupId);
                result.setStage(request.getDefenseStage());

                OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "DefenderDetectServiceImpl", "asyncDetect", "success",
                        "submit detect task succeed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());
                return new OpsCloudResult<>(result);
            } else {
                OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "DefenderDetectServiceImpl", "asyncDetect", "fail",
                        "submit detect task failed", request.getChangeOrderId(), request.getNodeId(), request.getDefenseStage());
                return OpsCloudResult.systemError("提交校验任务失败");
            }

        } catch (Exception e) {
            OpsCloudLoggerManager.log("error", DEFENDER, e, "DefenderDetectServiceImpl", "asyncDetect", "fail",
                    "do async detect got an exception", request.getChangeOrderId(), request.getNodeId(), e);
            return OpsCloudResult.systemError("unknown exception");
        }
    }

    /**
     * 匹配防御规则
     *
     * @param request 变更防御校验请求结构体
     * @return 匹配到的防御规则列表
     */
    private Set<MetaDefenderRuleEntity> matchDefenseRule(DefenderDetectRequest request) {

        Set<String> defenseRangeKeys = new HashSet<>();
        defenseRangeKeys.add(request.getChangeSceneKey());
        // 1.0 加入changeTags
        if (!CollectionUtils.isEmpty(request.getChangeTagIds())) {
            defenseRangeKeys.addAll(request.getChangeTagIds());
        }

        // 2.0 获取防御规则列表
        OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "get defenseRangeKeys", defenseRangeKeys.size(), JSONUtil.toJSONString(defenseRangeKeys, false), request.getDefenseStage());
        List<MetaDefenderRuleEntity> matchedRules = metaDefenderRuleRepository
                .selectByRangeKeysAndStage(defenseRangeKeys, request.getDefenseStage());
        if (CollectionUtils.isEmpty(matchedRules)){
            OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                    "get defense rule list size", matchedRules.size());
            return Collections.emptySet();
        }

        OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "get defense rule list size", matchedRules.size(), JSONUtil.toJSONString(matchedRules.stream().map(MetaDefenderRuleEntity::getName).collect(Collectors.toList())));

        if (request.getChangeExecuteInfo() == null
                || request.getChangeExecuteInfo().isOrderPhase()) {
            // 3.0 如果orderPhase为true，说明为工单级别的校验，过滤掉关联到批次维度的规则
            matchedRules = matchedRules.stream()
                    .filter(r -> !DefenseRangeTypeEnum.CHANGE_BATCH.equals(r.getDefenseRangeType()))
                    .collect(Collectors.toList());
        } else {
            // 3.1 如果orderPhase为false，说明为批次级别的校验，过滤掉关联到工单维度的规则
            matchedRules = matchedRules.stream()
                    .filter(r -> !DefenseRangeTypeEnum.CHANGE_SCENE.equals(r.getDefenseRangeType()))
                    .collect(Collectors.toList());
        }

        // 4.0 执行每个防御规则的过滤条件
        List<MetaDefenderRuleEntity> newMatchedRules = new ArrayList<>();
        for (MetaDefenderRuleEntity rule : matchedRules) {
            // 4.1 过滤掉防御能力已关闭的防御规则，此处需要判断是否使用了新系统的插件还是代理到老系统的防御服务
            if (!isEnabledRule(rule)) {
                OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                        "filter disabled rule", request.getNodeId(), rule.getId(), rule.getName());
                continue;
            }

            // 4.2 容错，如果changeFilter为空，默认匹配
            if (rule.getChangeFilter() == null || rule.getChangeFilter().readObject() == null) {
                OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                        "the change filter is empty, default matched", request.getNodeId(), rule.getId(), rule.getName());
                newMatchedRules.add(rule);
                continue;
            }

            // 4.3 执行实际过滤条件的匹配
            ChangeFilter changeFilter = rule.getChangeFilter().readObject();
            boolean isMatch = changeFilter.isMatch(buildChangeFilterRequest(request));

            if (isMatch) {
                // TODO 迭代器不能删数据
//                matchedRules.remove(rule);
                newMatchedRules.add(rule);
            }
        }

        OpsCloudLoggerManager.log("info", DEFENDER, request.getNodeId(), "DefenderDetectServiceImpl", "matchDefenseRule",
                "matched rule list size", newMatchedRules.size());
        return new HashSet<>(newMatchedRules);
    }

    /**
     * 构建防御规则过滤请求结构体
     *
     * @param req 变更防御校验请求结构体
     * @return 构建结果
     */
    private ChangeFilterRequest buildChangeFilterRequest(DefenderDetectRequest req) {
        // req里面的嵌套类，都打了NotNull注解，所以不必判空
        ChangeFilterRequest request = new ChangeFilterRequest();
        request.setSceneCodes(Collections.singleton(IdUtil.getChangeScenarioEnum(req.getNodeId()).getCode()));

        if (!Objects.isNull(req.getChangeBaseInfo())) {
            request.setOperator(req.getChangeBaseInfo().getExecutor());
            request.setSourcePlatform(req.getChangeBaseInfo().getPlatform());
            request.setChangeTile(req.getChangeBaseInfo().getChangeTitle());
            request.setChangeParam(req.getChangeBaseInfo().getChangeParamJSON());

            // 变更内容信息
            ChangeContent changeContent = req.getChangeBaseInfo().getChangeContent();
            if (!Objects.isNull(changeContent) && !CollectionUtils.isEmpty(changeContent.getChangeContentInstance())) {
                request.setChangeTargetTypes(Stream.of(changeContent.getChangeContentType()).collect(Collectors.toSet()));
                request.setChangeTargetInstances(new HashSet<>(changeContent.getChangeContentInstance()));
            }
        }


        // 分批信息组装
        if (!Objects.isNull(req.getChangeExecuteInfo())) {
            request.setChangePhase(req.getChangeExecuteInfo().getChangePhase());
            request.setChangePhaseBatchNo(req.getChangeExecuteInfo().getBatchNo());
            request.setLastBatch(req.getChangeExecuteInfo().isLastBatch());
            request.setLastBatchInPhase(req.getChangeExecuteInfo().isLastBatchInPhase());
            request.setTotalBatchNum(req.getChangeExecuteInfo().getTotalBatchNum());
            request.setTotalBatchNumInPhase(req.getChangeExecuteInfo().getTotalBatchInPhase());
        }

        // 影响面信息
        if (!Objects.isNull(req.getChangeInfluenceInfo())) {
            request.setChangeApps(req.getChangeInfluenceInfo().getApps());
            request.setEnvs(req.getChangeInfluenceInfo().getEnvs());
            request.setIdcs(req.getChangeInfluenceInfo().getPhysicDeployCells());
            request.setLogicalZones(req.getChangeInfluenceInfo().getLogicDeployCells());
            request.setHosts(req.getChangeInfluenceInfo().getHosts());
            request.setTenants(req.getChangeInfluenceInfo().getTenants());

            // 其余影响面信息补充
            Map<String, Object> extInfo = req.getChangeInfluenceInfo().getExtInfo();
            if (!CollectionUtils.isEmpty(extInfo)) {
                request.setOperatorDepId(String.valueOf(extInfo.get(ExtensionKey.DEP_ID.getKey())));
                request.setBizGroupIds(new HashSet<>((Collection<String>) extInfo
                        .getOrDefault(ExtensionKey.BIZ_GROUP_IDS.getKey(), Collections.emptySet())));
                request.setGmCodes((new HashSet<>((Collection<String>) extInfo
                        .getOrDefault(ExtensionKey.GM_CODES, Collections.emptyList()))));
                request.setAffectedServices((new HashSet<>((Collection<String>) extInfo
                        .getOrDefault(ExtensionKey.AFFECTED_SERVICES, Collections.emptyList()))));
                request.setAppHaLevels(new HashSet<>((Collection<String>) extInfo
                        .getOrDefault(ExtensionKey.HA_LEVEL, Collections.emptyList())));
                if (extInfo.containsKey(ExtensionKey.ARCH_DOMAIN)) {
                    JSONObject archObj = (JSONObject) extInfo.get(ExtensionKey.ARCH_DOMAIN);
                    request.setMainArchDomainIds(new HashSet<>(archObj.getJSONArray(MAIN_ARCH_DOMAIN).toJavaList(String.class)));
                    request.setSubArchDomainIds(new HashSet<>(archObj.getJSONArray(SUB_ARCH_DOMAIN).toJavaList(String.class)));
                }
            }
        }
        return request;
    }

    /**
     * 创建变更防御校验事件实体
     *
     * @param request 防御校验请求结构体
     * @param detect 校验记录实体
     * @return 事件实体
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

//        event.setChangeBaseInfo(request.getChangeBaseInfo());
//        event.setChangeExecuteInfo(request.getChangeExecuteInfo());
//        event.setChangeInfluenceInfo(request.getChangeInfluenceInfo());

        return event;
    }

    /**
     * 创建变更防御结果检查事件实体
     *
     * @param changeOrderId 变更工单id
     * @param nodeId 变更节点id
     * @param detectGroupId 校验分组id，对应一次前/后置
     * @param stage 校验阶段 - 前/后置
     * @return 事件实体
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