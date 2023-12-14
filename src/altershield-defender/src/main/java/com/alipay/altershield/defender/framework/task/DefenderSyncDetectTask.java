/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.task;


import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.DateUtil;
import com.alipay.altershield.defender.framework.AbstractDefenderService;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.request.DefenderDetectRequest;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Synchronize defense execution tasks, each task corresponds to a rule
 *
 * @author yhaoxuan
 * @version DefenderSyncDetectTask.java, v 0.1 2022年12月09日 11:28 上午 yhaoxuan
 */
public class DefenderSyncDetectTask extends AbstractDefenderService implements Callable<DefenderDetectPluginResult> {

    /**
     * The rule entity for the task to be executed
     */
    private MetaDefenderRuleEntity rule;

    /**
     * Detection group id
     */
    private String detectGroupId;

    /**
     * Concurrency counter
     */
    private CountDownLatch countDownLatch;

    /**
     * Defense detection request structure
     */
    private DefenderDetectRequest request;

    public DefenderSyncDetectTask(MetaDefenderRuleEntity rule, String detectGroupId, CountDownLatch countDownLatch,
                                  DefenderDetectRequest request) {
        this.rule = rule;
        this.detectGroupId = detectGroupId;
        this.countDownLatch = countDownLatch;
        this.request = request;
    }

    @Override
    public DefenderDetectPluginResult call() throws Exception {
        AlterShieldLoggerManager.log("info", Loggers.DEFENDER, "DefenderSyncDetectTask", "call", "start to execute rule",
                request.getNodeId(), rule.getId());

        // 1.0 Generate the ID of the detection record
        String detectExeId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_EXE_DEFENDER_DETECT_EXE_ID, detectGroupId);
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setRuleId(rule.getId());
        result.setDetectGroupId(detectGroupId);
        result = executeSyncPlugin(buildDefenderTaskExecuteRequest(request, rule, detectGroupId, detectExeId));

        // 2.0 Create a detection record asynchronously
        DefenderDetectPluginResult finalResult = result;
        defenderThreadPool.execute(() -> {
            ExeDefenderDetectEntity detectRecord = createDetectEntity(request.getChangeOrderId(), request.getNodeId(),
                    detectGroupId, request.getDefenseStage(), rule);
            switch (finalResult.getStatus()) {
                case PASS:
                    detectRecord.setStatus(DefenderStatusEnum.PASS);
                    detectRecord.setMsg("Check passed!");
                    break;
                case FAIL:
                    detectRecord.setStatus(DefenderStatusEnum.FAIL);
                    detectRecord.setMsg("Check failed!");
                    break;
                case TIMEOUT:
                    detectRecord.setStatus(DefenderStatusEnum.TIMEOUT);
                    detectRecord.setMsg("Check timeout!");
                    break;
                case EXCEPTION:
                default:
                    detectRecord.setStatus(DefenderStatusEnum.EXCEPTION);
                    detectRecord.setMsg("Check with an exception!");
            }
            detectRecord.setGmtFinish(DateUtil.getNowDate());
            exeDefenderDetectRepository.insert(detectRecord);
        });

        return result;
    }
}