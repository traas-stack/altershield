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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.impl;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.DateUtil;
import com.alipay.altershield.defender.framework.AbstractDefenderService;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.shared.defender.DefenderTaskExecutor;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.enums.DefenderPluginInvokeTypeEnum;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import com.alipay.altershield.shared.defender.request.DefenderTaskExecuteRequest;
import com.alipay.altershield.shared.defender.result.DefenderTaskResult;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Change defense task executor
 *
 * @author yhaoxuan
 * @version DefenderTaskExecutorImpl.java, v 0.1 2022年08月28日 7:17 下午 yhaoxuan
 */
@Component("defenderTaskExecutorImpl")
public class DefenderTaskExecutorImpl extends AbstractDefenderService implements DefenderTaskExecutor {

    private static final Logger logger = Loggers.DEFENDER;

    /**
     * Submit a detection task for a single defense rule
     *
     * @param request Single defense rule task execution request body
     * @return Change defense verification task submission results
     */
    @Override
    public DefenderTaskResult execute(DefenderTaskExecuteRequest request) throws AlterShieldInternalException {
        // 1.0 fault tolerance
        if (request == null) {
            AlterShieldLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                    "request can`t be null");
            return DefenderTaskResult.failWithNoRetry("Request parameters cannot be empty");
        }

        try {
            // 2.0 Get defense rule information
            MetaDefenderRuleEntity rule = metaDefenderRuleRepository.loadByRuleId(request.getRuleId());
            if (rule == null) {
                AlterShieldLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                        "target rule do not exist", "fail", request.getChangeOrderId(), request.getNodeId(),
                        request.getDetectExeId(), request.getRuleId());
                return DefenderTaskResult.failWithNoRetry("Defense rules do not exist");
            }

            // 3.0 Query defense detection records
            ExeDefenderDetectEntity detectEntity = exeDefenderDetectRepository.loadByExeId(request.getDetectExeId());
            if (detectEntity == null) {
                AlterShieldLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                        "detect record do not exist", "fail", request.getChangeOrderId(), request.getNodeId(), request.getDetectExeId());
                return DefenderTaskResult.failWithNoRetry("AlterShieldLoggerManager record does not exist");
            }

            // 2.1 If the user adjusts the effective status of the rule during scheduling, cancel the scheduling. and update the detection record status
            if (DefenderRuleStatusEnum.DISABLED.equals(rule.getStatus())) {
                AlterShieldLoggerManager.log("info", logger, "DefenderTaskExecutorImpl", "execute",
                        "rule had been disabled", "success", request.getChangeOrderId(), request.getNodeId(),
                        request.getDetectExeId(), request.getRuleId());
                detectEntity.setStatus(DefenderStatusEnum.CANCEL);
                detectEntity.setMsg("Defense rules are turned off and detection is cancelled.");
                return DefenderTaskResult.succeed("Defense rules are turned off and detection is not performed.");
            }

            // 4.0 Carry out state machine flow
            return statusTransfer(request, rule, detectEntity);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", logger, "DefenderTaskExecutorImpl", "execute",
                    "execute got an exception", "fail", request.getChangeOrderId(), request.getNodeId(),
                    request.getDetectExeId(), e);
            return DefenderTaskResult.failWithRetry("Unknown exception, try again");
        }
    }

    /**
     * Defense detection task status transfer
     *
     * @param request Defense detection task request body
     * @param rule Defense Rule Entity
     * @param detectEntity Corresponding detection record
     */
    private DefenderTaskResult statusTransfer(DefenderTaskExecuteRequest request, MetaDefenderRuleEntity rule,
                                              ExeDefenderDetectEntity detectEntity) {
        try {
            // 3.0 Processing and transfer based on detection status
            switch (detectEntity.getStatus()) {
                // 3.1 Initial state, first scheduled execution
                case INIT:
                    transferFromInit(request, detectEntity, rule);
                    // Retry event, because it is not a final state, the retry event performs state machine flow
                    AlterShieldLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("INIT -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("INIT finish");
                // 3.2 During execution, the asynchronous switching time has not exceeded
                case EXE:
                    boolean exeFinished = isDetectFinish(request, rule, detectEntity);
                    // 3.2.1 If the detection has not ended, determine whether the maximum blocking time has been exceeded. If so, switch to asynchronous
                    if (!exeFinished) {
                        long duringTime = DateUtil.getDiffFromTwoDates(detectEntity.getGmtStart(), new Date());
                        if (duringTime > AlterShieldConstant.DEFENDER_MAX_BLOCK_OBSERVE_SECOND * 1000) {
                            detectEntity.setStatus(DefenderStatusEnum.ASYNC_CHECK);
                        }
                    }
                    detectEntity.setGmtFinish(DateUtil.getNowDate());
                    updateDetectInTransaction(detectEntity);
                    // Retry event, because it is not a final state, the retry event performs state machine flow
                    AlterShieldLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("EXE -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("EXE finish");
                // 3.3 Already being executed asynchronously, no clear result has been obtained
                case ASYNC_CHECK:
                    boolean asyncCheckFinished = isDetectFinish(request, rule, detectEntity);
                    // 3.3.1 If the detection has not finished, determine whether the maximum defense execution time has been exceeded. If so, update it to timeout.
                    if (!asyncCheckFinished) {
                        long duringTime = DateUtil.getDiffFromTwoDates(detectEntity.getGmtStart(), new Date());
                        if (duringTime > AlterShieldConstant.DEFENDER_MAX_BLOCK_OBSERVE_SECOND * 1000) {
                            detectEntity.setStatus(DefenderStatusEnum.TIMEOUT);
                        }
                    }
                    detectEntity.setGmtFinish(DateUtil.getNowDate());
                    updateDetectInTransaction(detectEntity);
                    // Retry event, because it is not a final state, the retry event performs state machine flow
                    AlterShieldLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("ASYNC_CHECK -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("ASYNC_CHECK finish");
                // 3.4 It was predicted in the previous schedule that there may be risks, and the changes are in a state of being suspended.
                case SUSPEND:
                    isDetectFinish(request, rule, detectEntity);
                    updateDetectInTransaction(detectEntity);
                    // Retry event, because it is not a final state, the retry event performs state machine flow
                    AlterShieldLoggerManager.log("info", logger,
                            detectEntity.getDetectGroupId(), detectEntity.getDetectExeId(),
                            String.format("SUSPEND -> %s", detectEntity.getStatus()));
                    return DefenderTaskResult.failWithRetry("SUSPEND finish");
                // 3.5 The remaining states are in the final state and no further state transfer processing is performed.
                default:
                    break;
            }

            return DefenderTaskResult.succeed("Task execution successful");
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskExecutorImpl", "submitTask",
                    "status transfer got an exception", "fail", request.getChangeOrderId(), request.getNodeId(),
                    request.getDetectExeId(), e);
            return DefenderTaskResult.failWithRetry("Unknown exception, try again");
        }
    }



    /**
     * Processing logic when defense check is in intermediate state (EXE, ASYNC CHECK, SUSPEND)
     *
     * @param request Defense detection task request structure
     * @param rule Defense Rule Entity
     * @param detectEntity Corresponding detection record
     */
    private boolean isDetectFinish(DefenderTaskExecuteRequest request, MetaDefenderRuleEntity rule,
                                   ExeDefenderDetectEntity detectEntity) {
        // 1.0 Execute the plugin and obtain the detection results
        AlterShieldResult<DefenderDetectPluginResult> retrieveRst = retrievePluginResult(request);
        if (retrieveRst == null || !retrieveRst.isSuccess()) {
            // 1.1 If the execution is abnormal, update the detection record to the abnormal status
            detectEntity.setStatus(DefenderStatusEnum.EXCEPTION);
            detectEntity.setGmtFinish(DateUtil.getNowDate());
            updateDetectInTransaction(detectEntity);
            return true;
        }

        // 2.0 If there is a clear final state, update the corresponding detection record
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
     * Processing logic when defense detection is in initial state (INIT)
     *
     * @param request Defense detection task request structure
     * @param detectEntity Corresponding detection record
     */
    private void transferFromInit(DefenderTaskExecuteRequest request, ExeDefenderDetectEntity detectEntity, MetaDefenderRuleEntity rule) {
        if (DefenderPluginInvokeTypeEnum.SYNC.getType().equalsIgnoreCase(rule.getPluginInvokeType())) {
            // 1.0 Synchronous plugin, call it directly, and get the result
            detectEntity.setGmtStart(DateUtil.getNowDate());
            DefenderDetectPluginResult result = executeSyncPlugin(request);
            // 1.1 Update the detection record status based on the detection results
            detectEntity.setStatus(result.getStatus());
            detectEntity.setMsg(result.getMsg());
            detectEntity.getResultRef().write(result.getResultJson());
            detectEntity.setGmtFinish(DateUtil.getNowDate());
        } else {
            // 2.0 Asynchronous plugin, submit detection task
            AlterShieldResult<Void> taskSubmitRst = executeSubmitTask(request);
            if (taskSubmitRst == null || !taskSubmitRst.isSuccess()) {
                // 2.1 If the execution is abnormal, update the detection record to the abnormal status
                detectEntity.setStatus(DefenderStatusEnum.EXCEPTION);
                detectEntity.setGmtStart(new Date());
                detectEntity.setGmtFinish(new Date());
                updateDetectInTransaction(detectEntity);
                return;
            }

            // 2.2 Update the status in the detection record table
            detectEntity.setStatus(DefenderStatusEnum.EXE);
            detectEntity.setGmtStart(new Date());
        }
        updateDetectInTransaction(detectEntity);
    }
}