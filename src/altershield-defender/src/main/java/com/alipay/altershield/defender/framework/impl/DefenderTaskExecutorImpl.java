/**
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.impl;

import com.alipay.opscloud.api.defender.DefenderTaskExecutor;
import com.alipay.opscloud.api.defender.entity.ExeDefenderDetectEntity;
import com.alipay.opscloud.api.defender.enums.DefenderPluginInvokeTypeEnum;
import com.alipay.opscloud.api.defender.enums.DefenderRuleStatusEnum;
import com.alipay.opscloud.api.defender.request.DefenderTaskExecuteRequest;
import com.alipay.opscloud.api.defender.result.DefenderTaskResult;
import com.alipay.opscloud.common.ability.entity.MetaAbilityEntity;
import com.alipay.opscloud.common.ability.entity.MetaAbilityPluginEntity;
import com.alipay.opscloud.common.ability.enums.AbilityTypeEnum;
import com.alipay.opscloud.common.ability.repository.MetaAbilityPluginRepository;
import com.alipay.opscloud.common.ability.repository.MetaAbilityRepository;
import com.alipay.opscloud.defender.AbstractDefenderService;
import com.alipay.opscloud.defender.meta.entity.MetaDefenderRuleEntity;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalErrorCode;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalException;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.opscloud.spi.defender.model.result.DefenderDetectPluginResult;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.logger.Loggers;
import com.alipay.opscloud.tools.common.util.CommonUtil;
import com.alipay.opscloud.tools.common.util.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * 变更防御任务执行器
 *
 * @author haoxuan
 * @version DefenderTaskExecutorImpl.java, v 0.1 2022年08月28日 7:17 下午 haoxuan
 */
@Component("defenderTaskExecutorImpl")
public class DefenderTaskExecutorImpl extends AbstractDefenderService implements DefenderTaskExecutor {

    private static final Logger logger = Loggers.DEFENDER;

    @Autowired
    private MetaAbilityRepository metaAbilityRepository;

    @Autowired
    private MetaAbilityPluginRepository metaAbilityPluginRepository;

    /**
     * 提交单个防御规则的校验任务
     *
     * @param request 单个防御规则任务执行请求体
     * @return 变更防御校验任务提交结果
     */
    @Override
    public DefenderTaskResult execute(DefenderTaskExecuteRequest request) throws OpsCloudInternalException {
        // 1.0 容错
        if (request == null) {
            OpsCloudLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                    "request can`t be null");
            return DefenderTaskResult.failWithNoRetry("请求参数不能为空");
        }

        // whiteList check
        if (!whiteListCheck(request.getChangeSceneKey())) {
            OpsCloudLoggerManager.log("info", logger, "白名单校验不通过", request.getChangeSceneKey());
            return DefenderTaskResult.failWithNoRetry("白名单校验不通过");
        }

        try {
            // 2.0 获取防御规则信息
            MetaDefenderRuleEntity rule = metaDefenderRuleRepository.loadByRuleId(request.getRuleId());
            if (rule == null) {
                OpsCloudLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                        "target rule do not exist", "fail", request.getChangeOrderId(), request.getNodeId(),
                        request.getDetectExeId(), request.getRuleId());
                return DefenderTaskResult.failWithNoRetry("防御规则不存在");
            }

            // 3.0 查询防御校验记录
            ExeDefenderDetectEntity detectEntity = exeDefenderDetectRepository.loadByExeId(request.getDetectExeId());
            if (detectEntity == null) {
                OpsCloudLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                        "detect record do not exist", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId());
                return DefenderTaskResult.failWithNoRetry("校验记录不存在");
            }

            // 2.1 如果用户在调度期间调整了规则的生效状态，取消调度。并更新校验记录状态
            if (DefenderRuleStatusEnum.DISABLED.equals(rule.getStatus())) {
                OpsCloudLoggerManager.log("info", logger, "DefenderTaskExecutorImpl", "execute",
                        "rule had been disabled", "success", request.getChangeOrderId(), request.getNodeId(),
                        request.getDetectExeId(), request.getRuleId());
                detectEntity.setStatus(DefenderStatusEnum.CANCEL);
                detectEntity.setMsg("防御规则被关闭，校验取消");
                return DefenderTaskResult.succeed("防御规则已关闭，未执行校验");
            }

            // 4.0 进行状态机流转
            return statusTransfer(request, rule, detectEntity);
        } catch (Exception e) {
            OpsCloudLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                    "execute got an exception", "fail", request.getChangeOrderId(), request.getNodeId(),
                    request.getDetectExeId(), e);
            return DefenderTaskResult.failWithRetry("未知异常，暂重试");
        }
    }

    /**
     * 防御校验任务状态流转
     *
     * @param request 防御校验任务请求体
     * @param rule 防御规则实体
     * @param detectEntity 对应校验记录
     */
    private DefenderTaskResult statusTransfer(DefenderTaskExecuteRequest request, MetaDefenderRuleEntity rule,
                                              ExeDefenderDetectEntity detectEntity) {
        try {
            // 3.0 根据校验状态进行处理及流转
            switch (detectEntity.getStatus()) {
                // 3.1 初始态，第一次调度执行
                case INIT:
                    transferFromInit(request, detectEntity, rule);
                    // 重试事件，因为非终态，重试事件进行状态机流转
                    OpsCloudLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("INIT -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("INIT finish");
                // 3.2 执行中，未超过异步化切换时间
                case EXE:
                    boolean exeFinished = isDetectFinish(request, rule, detectEntity);
                    // 3.2.1 如果校验未结束，判断是否超过最大阻塞时间，如果是，切换异步化
                    if (!exeFinished) {
                        long duringTime = CommonUtil.cost(new Date(), detectEntity.getGmtStart());
                        if (duringTime > (long) OpsCloudConstant.DEFENDER_MAX_BLOCK_OBSERVE_SECOND * 1000) {
                            detectEntity.setStatus(DefenderStatusEnum.ASYNC_CHECK);
                        }
                    }
                    detectEntity.setGmtFinish(DateUtil.getNowDate());
                    updateDetectInTransaction(detectEntity);
                    // 重试事件，因为非终态，重试事件进行状态机流转
                    OpsCloudLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("EXE -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("EXE finish");
                // 3.3 已在异步化执行中，未得到明确结果
                case ASYNC_CHECK:
                    boolean asyncCheckFinished = isDetectFinish(request, rule, detectEntity);
                    // 3.3.1 如果校验未结束，判断是否超过了防御执行的最大时间，如果是，将其更新为超时
                    if (!asyncCheckFinished) {
                        long duringTime = CommonUtil.cost(new Date(), detectEntity.getGmtStart());
                        if (duringTime > (long) OpsCloudConstant.DEFENDER_DETECT_TIMEOUT_SECOND * 1000) {
                            detectEntity.setStatus(DefenderStatusEnum.TIMEOUT);
                        }
                    }
                    detectEntity.setGmtFinish(DateUtil.getNowDate());
                    updateDetectInTransaction(detectEntity);
                    // 重试事件，因为非终态，重试事件进行状态机流转
                    OpsCloudLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("ASYNC_CHECK -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("ASYNC_CHECK finish");
                // 3.4 前一次调度中预判可能存在风险，处于将变更暂缓的状态
                case SUSPEND:
                    isDetectFinish(request, rule, detectEntity);
                    updateDetectInTransaction(detectEntity);
                    // 重试事件，因为非终态，重试事件进行状态机流转
                    OpsCloudLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("SUSPEND -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("SUSPEND finish");
                // 3.5 其余状态均已处于终态，不再做状态流转处理
                default:
                    break;
            }

            return DefenderTaskResult.succeed("任务执行成功");
        } catch (Exception e) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskExecutorImpl", "submitTask",
                    "status transfer got an exception", "fail", request.getChangeOrderId(), request.getNodeId(),
                    request.getDetectExeId(), e);
            return DefenderTaskResult.failWithRetry("未知异常，暂重试");
        }
    }



    /**
     * 当防御校验处于中间态（EXE、ASYNC_CHECK、SUSPEND）时的处理逻辑
     *
     * @param request 防御检测任务请求结构体
     * @param rule 防御规则实体
     * @param detectEntity 对应校验记录
     */
    private boolean isDetectFinish(DefenderTaskExecuteRequest request, MetaDefenderRuleEntity rule,
                                   ExeDefenderDetectEntity detectEntity) {
        // 1.0 执行插件，获取校验结果
        OpsCloudResult<DefenderDetectPluginResult> retrieveRst = retrievePluginResult(request);
        if (retrieveRst == null || !retrieveRst.isSuccess()) {
            // 1.1 如果执行异常，将校验记录更新为异常状态
            detectEntity.setStatus(DefenderStatusEnum.EXCEPTION);
            detectEntity.setGmtFinish(DateUtil.getNowDate());
            updateDetectInTransaction(detectEntity);
            return true;
        }

        // 2.0 如果有明确的终态，更新对应的校验记录
        DefenderDetectPluginResult detectRst = retrieveRst.getDomain();
        if (DefenderStatusEnum.PASS.equals(detectRst.getStatus()) || DefenderStatusEnum.FAIL.equals(detectRst.getStatus())) {
            detectEntity.setStatus(detectRst.getStatus());
            detectEntity.setGmtFinish(DateUtil.getNowDate());
            updateDetectInTransaction(detectEntity);
            return true;
        }

        return false;
    }

    /**
     * 当防御校验处于初始态（INIT）时的处理逻辑
     *
     * @param request 防御检测任务请求结构体
     * @param detectEntity 对应校验记录
     */
    private void transferFromInit(DefenderTaskExecuteRequest request, ExeDefenderDetectEntity detectEntity, MetaDefenderRuleEntity rule) {
        // 1.0 获取插件调用方式
        MetaAbilityEntity plugin = metaAbilityRepository.queryByControlKey(request.getPluginKey());

        // 如果plugin为空，说明插件库没有该插件，或该防御服务为SPI调用
        if (Objects.isNull(plugin)) {
            // 判读插件调用方式：同步 or 异步, 获取插件属性
            String rulePluginInvokeType = rule.getPluginInvokeType();
            DefenderPluginInvokeTypeEnum invokeType = DefenderPluginInvokeTypeEnum.getByType(rulePluginInvokeType);

            // 获取代理插件
            plugin = getProxyPlugin(request, invokeType);

            if (Objects.isNull(plugin)) {
                // 没拿到用于代理的插件
                OpsCloudLoggerManager.log("error", logger, "未查询到代理插件",
                        String.format("pluginKey = %s, ruleId = %s", request.getPluginKey(), request.getRuleId()));
                throw new OpsCloudInternalException(OpsCloudInternalErrorCode.PLUGIN_NOT_FOUND, "未查询到代理插件");
            }
        } else {
            // 覆盖掉规则中 mainClass 脏数据, 获取当前插件最新版本配置数据
            MetaAbilityPluginEntity newestVersionPlugin = metaAbilityPluginRepository.queryNewestByPluginKey(request.getPluginKey());
            if (!Objects.isNull(newestVersionPlugin) && !CollectionUtils.isEmpty(newestVersionPlugin.getMainClassList())) {
                request.setMainClass(new ArrayList<>(newestVersionPlugin.getMainClassList()).get(0));
            }
        }

        if (AbilityTypeEnum.DEFENSE_CHECK_SYNC.equals(plugin.getAbilityType())) {
            // 2.0 同步插件，直接调用，并获取结果
            detectEntity.setGmtStart(DateUtil.getNowDate());
            DefenderDetectPluginResult result = executeSyncPlugin(request);
            // 2.1 根据校验结果，更新校验记录状态
            detectEntity.setStatus(result.getStatus());
            detectEntity.setMsg(result.getMsg());
            detectEntity.getResultRef().write(result.getResultJson());
            detectEntity.setGmtFinish(DateUtil.getNowDate());
        } else {
            // 3.0 异步插件，提交校验任务
            OpsCloudResult<Void> taskSubmitRst = executeSubmitTask(request);
            if (taskSubmitRst == null || !taskSubmitRst.isSuccess()) {
                // 3.1 如果执行异常，将校验记录更新为异常状态
                detectEntity.setStatus(DefenderStatusEnum.EXCEPTION);
                detectEntity.setGmtStart(new Date());
                detectEntity.setGmtFinish(new Date());
                updateDetectInTransaction(detectEntity);
                return;
            }

            // 3.2 更新校验记录表中的状态
            detectEntity.setStatus(DefenderStatusEnum.EXE);
            detectEntity.setGmtStart(new Date());
        }
        updateDetectInTransaction(detectEntity);
    }
}