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
package com.alipay.altershield.defender.framework;

import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.DateUtil;
import com.alipay.altershield.defender.framework.exe.repository.ExeDefenderDetectRepository;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.defender.framework.meta.repository.MetaDefenderRuleRepository;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.enums.DefenderVerdictEnum;
import com.alipay.altershield.shared.defender.request.DefenderDetectRequest;
import com.alipay.altershield.shared.defender.request.DefenderTaskExecuteRequest;
import com.alipay.altershield.shared.pluginmarket.PluginMarket;
import com.alipay.altershield.shared.schedule.event.defender.DefenderDetectFinishEvent;
import com.alipay.altershield.spi.defender.DefenderAsyncDetectPlugin;
import com.alipay.altershield.spi.defender.DefenderSyncDetectPlugin;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.FutureTask;
import java.util.function.Function;

/**
 * Defender Basic Abstract Class
 *
 * @author yhaoxuan
 * @version AbstractDefenderService.java, v 0.1 2022年08月26日 2:27 下午 yhaoxuan
 */
public abstract class AbstractDefenderService {

    @Autowired
    protected TransactionTemplate            transactionTemplate;
    @Autowired
    protected PluginMarket                   pluginMarket;
    @Autowired
    protected KeyValueStorage                singleKeyValueStorage;
    @Autowired
    protected KeyValueStorage                shardingKeyValueStorage;
    @Autowired
    protected IdGenerator                    idGenerator;
    @Autowired
    protected ThreadPoolTaskExecutor         defenderThreadPool;

    /**
     * Repository layer public dependencies
     */
    @Autowired
    protected MetaDefenderRuleRepository  metaDefenderRuleRepository;
    @Autowired
    protected ExeDefenderDetectRepository exeDefenderDetectRepository;

    /**
     * concurrently executing the synchronous detect task
     *
     * @param task    the task to submit into thread pool
     * @param request entity of detect request
     * @param rule    entity of defender rule
     * @param <T>     type of detect result
     */
    protected <T> void submitSyncDetect(FutureTask<T> task, DefenderDetectRequest request, MetaDefenderRuleEntity rule) {
        defenderThreadPool.execute(() -> {
            try {
                AlterShieldLoggerManager.log("info", Loggers.DEFENDER, "AbstractDefenderService", "submitSyncDetect",
                        "start to submit sync detect", request.getNodeId(), rule.getId());
                task.run();
            } catch (Exception e) {
                AlterShieldLoggerManager.log("info", Loggers.DEFENDER, "AbstractDefenderService", "submitSyncDetect",
                        "failed", "submit sync detect got an exception", request.getNodeId(), rule.getId(), e);
            }
        });
    }

    /**
     * Execution of synchronization type defense plugins
     *
     * @param request Defense detection task request structure
     * @return Plugin detection results
     */
    protected DefenderDetectPluginResult executeSyncPlugin(DefenderTaskExecuteRequest request) {
        Function<DefenderSyncDetectPlugin, DefenderDetectPluginResult> invoker = detectPlugin -> {
            DefenderDetectPluginRequest req = buildDetectPluginRequest(request);
            return detectPlugin.detect(req);
        };
        AlterShieldResult<DefenderDetectPluginResult> pluginResult =
                pluginMarket.executePlugin(request.getPluginKey(), request.getMainClass(), invoker);
        if (pluginResult == null || !pluginResult.checkSuccess()) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "AbstractDefenderService", "executeSyncPlugin",
                    "failed", "invoke plugin failed", request.getNodeId(), request.getRuleId(), request.getPluginKey());
            return DefenderDetectPluginResult.exception("插件触发异常");
        }

        return pluginResult.getDomain();
    }

    /**
     * According to the defense plugin, perform the submission of asynchronous detection tasks
     *
     * @param request Defense detection task request structure
     * @return Plugin detection results
     */
    protected AlterShieldResult<Void> executeSubmitTask(DefenderTaskExecuteRequest request) {
        // 1.0 Assemble task submission function
        Function<DefenderAsyncDetectPlugin, Void> invoker = detectPlugin -> {
            DefenderDetectPluginRequest req = buildDetectPluginRequest(request);
            detectPlugin.submitDetectTask(req);
            return null;
        };

        try {
            // 2.0 Execute plugin
            AlterShieldResult<Void> pluginResult = pluginMarket.executePlugin(request.getPluginKey(),
                    request.getMainClass(), invoker);

            // 2.1 Verify plugin execution results
            if (pluginResult == null || !pluginResult.isSuccess()) {
                AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "AbstractDefenderService", "executeSubmitTask",
                        "plugin execute failed", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId(),
                        request.getRuleId(), request.getPluginKey());
                return AlterShieldResult.systemError("plugin execute failed");
            }

            return pluginResult;
        } catch (Throwable t) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "AbstractDefenderService", "executeSubmitTask",
                    "plugin execute got an exception", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId(),
                    request.getRuleId(), request.getPluginKey(), t);
            return AlterShieldResult.systemError("plugin execute got an exception");
        }
    }

    /**
     * Obtain asynchronous detection results based on the defense plugin
     *
     * @param request Defense detection task request structure
     * @return Plugin detection results
     */
    protected AlterShieldResult<DefenderDetectPluginResult> retrievePluginResult(DefenderTaskExecuteRequest request) {
        // 1.0 Assemble task submission function
        Function<DefenderAsyncDetectPlugin, DefenderDetectPluginResult> invoker = detectPlugin -> {
            DefenderDetectPluginRequest req = buildDetectPluginRequest(request);
            return detectPlugin.retrieveDetectResult(req);
        };

        try {
            // 2.0 Execute plugin
            AlterShieldResult<DefenderDetectPluginResult> pluginResult = pluginMarket.executePlugin(request.getPluginKey(),
                    request.getMainClass(), invoker);

            // 2.1 Verify plugin execution results
            if (pluginResult == null || !pluginResult.isSuccess()) {
                AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "AbstractDefenderService", "retrievePluginResult",
                        "plugin execute failed", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId(),
                        request.getRuleId(), request.getPluginKey());
                return AlterShieldResult.systemError("plugin execute failed");
            }

            return pluginResult;
        } catch (Throwable t) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "AbstractDefenderService", "retrievePluginResult",
                    "plugin execute got an exception", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId(),
                    request.getRuleId(), request.getPluginKey(), t);
            return AlterShieldResult.systemError("plugin execute got an exception");
        }
    }

    /**
     * Row lock update detection record
     *
     * @param detectEntity Detection record entity
     */
    protected void updateDetectInTransaction(ExeDefenderDetectEntity detectEntity) {
        transactionTemplate.execute(s -> {
            exeDefenderDetectRepository.lockByExeId(detectEntity.getDetectExeId());
            return exeDefenderDetectRepository.update(detectEntity);
        });
    }

    /**
     * Create detection record entity
     *
     * @param changeOrderId change order id
     * @param nodeId        change node id
     * @param detectGroupId detection group id
     * @param stage         pre or post
     * @param rule          defense rule entity
     * @return detection record entity
     */
    protected ExeDefenderDetectEntity createDetectEntity(String changeOrderId, String nodeId, String detectGroupId,
                                                         DefenseStageEnum stage, MetaDefenderRuleEntity rule) {
        ExeDefenderDetectEntity detect = new ExeDefenderDetectEntity(shardingKeyValueStorage);
        // 1.0 Various ID information
        detect.setDetectExeId(idGenerator.generateIdByRelatedId(
                IdBizCodeEnum.OPSCLD_EXE_DEFENDER_DETECT_EXE_ID, nodeId));
        detect.setDetectGroupId(detectGroupId);
        detect.setChangeOrderId(changeOrderId);
        detect.setNodeId(nodeId);
        // 2.0 Defense rule information
        detect.setRuleId(rule.getId());
        detect.setExternalId(rule.getExternalId());
        detect.setRuleName(rule.getName());
        detect.setRuleOwner(rule.getOwner());
        detect.setPluginKey(rule.getPluginKey());
        detect.setMainClass(rule.getMainClass());
        // 3.0 Detection information
        detect.setStage(stage);
        detect.setStatus(DefenderStatusEnum.INIT);
        detect.setGmtPlan(DateUtil.getSecondsAfterNow(rule.getDelaySecond()));
        detect.setRuleStatus(rule.getStatus());

        detect.setGmtCreate(DateUtil.getNowDate());
        detect.setGmtModified(DateUtil.getNowDate());

        detect.setGmtStart(detect.getGmtCreate());
        return detect;
    }

    /**
     * Construct a defense task execution request structure
     *
     * @param req           Defense detection request structure
     * @param rule          Defense rule entity
     * @param detectGroupId Detection group id
     * @param detectExeId   Detection execution id
     * @return the request
     */
    protected DefenderTaskExecuteRequest buildDefenderTaskExecuteRequest(DefenderDetectRequest req, MetaDefenderRuleEntity rule,
                                                                         String detectGroupId, String detectExeId) {
        DefenderTaskExecuteRequest request = new DefenderTaskExecuteRequest();
        request.setChangeSceneKey(req.getChangeSceneKey());
        request.setChangeKey(req.getChangeKey());
        request.setPluginKey(rule.getPluginKey());
        request.setMainClass(rule.getMainClass());

        request.setChangeOrderId(req.getChangeOrderId());
        request.setNodeId(req.getNodeId());
        request.setDetectGroupId(detectGroupId);
        request.setDetectExeId(detectExeId);
        request.setRuleId(rule.getId());
        request.setExternalId(rule.getExternalId());

        request.setDefenseStage(req.getDefenseStage());
        request.setChangeBaseInfo(req.getChangeBaseInfo());
        request.setChangeExecuteInfo(req.getChangeExecuteInfo());
        request.setChangeInfluenceInfo(req.getChangeInfluenceInfo());

        return request;
    }

    /**
     * Build defense finish event
     *
     * @param changeOrderId Change order id
     * @param nodeId        Change node id
     * @param detectGroupId Detection group id
     * @param stage         pre or post
     * @param verdict       Detection conclusion
     * @return Defender finish event
     */
    protected DefenderDetectFinishEvent buildDefenseFinishEvent(String changeOrderId, String nodeId, String detectGroupId,
                                                                DefenseStageEnum stage, DefenderVerdictEnum verdict,
                                                                String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        return buildDefenseFinishEvent(changeOrderId, nodeId, true, "", detectGroupId, stage, verdict, changeSceneKey, changeStepType);
    }

    /**
     * Build defense finish event
     *
     * @param changeOrderId Change order id
     * @param nodeId        Change node id
     * @param detectGroupId Detection group id
     * @param stage         pre or post
     * @param verdict       Detection conclusion
     * @return Defender finish event
     */
    protected DefenderDetectFinishEvent buildDefenseFinishEvent(String changeOrderId, String nodeId, boolean defensed, String msg,
                                                                String detectGroupId, DefenseStageEnum stage, DefenderVerdictEnum verdict,
                                                                String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        DefenderDetectFinishEvent event = new DefenderDetectFinishEvent();
        event.setDefensed(defensed);
        event.setMsg(msg);
        event.setChangeOrderId(changeOrderId);
        event.setNodeId(nodeId);
        event.setDetectGroupId(detectGroupId);
        event.setStage(stage);
        event.setVerdict(verdict);
        event.setChangeSceneKey(changeSceneKey);
        event.setChangeStepType(changeStepType);

        return event;
    }

    /**
     * Build the request structure of the detection plug-in
     *
     * @param request defender task execution request structure
     * @return Detect plugin request structure
     */
    private DefenderDetectPluginRequest buildDetectPluginRequest(DefenderTaskExecuteRequest request) {
        DefenderDetectPluginRequest req = new DefenderDetectPluginRequest();
        req.setChangeOrderId(request.getChangeOrderId());
        req.setNodeId(request.getNodeId());
        req.setChangeSceneKey(request.getChangeSceneKey());
        req.setChangeKey(request.getChangeKey());
        req.setDetectGroupId(request.getDetectGroupId());
        req.setDefenseStage(request.getDefenseStage());
        req.setChangeBaseInfo(request.getChangeBaseInfo());
        req.setChangeExecuteInfo(request.getChangeExecuteInfo());
        req.setChangeInfluenceInfo(request.getChangeInfluenceInfo());
        req.setDetectId(request.getDetectExeId());
        if (!StringUtils.isBlank(req.getParamJson())) {
            req.setParamJson(request.getParamJson());
        }

        return req;
    }
}