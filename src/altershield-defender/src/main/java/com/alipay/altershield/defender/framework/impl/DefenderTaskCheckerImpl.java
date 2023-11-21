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

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.defender.framework.AbstractDefenderService;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.service.ExeChangeNodeService;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.defender.DefenderTaskChecker;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import com.alipay.altershield.shared.defender.enums.DefenderVerdictEnum;
import com.alipay.altershield.shared.defender.result.DefenderTaskResult;
import com.alipay.altershield.shared.schedule.event.defender.DefenderDetectFinishEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Change Defense Detection Task Batch Status Checker
 *
 * @author yhaoxuan
 * @version DefenderTaskCheckerImpl.java, v 0.1 2022年08月29日 9:26 下午 yhaoxuan
 */
@Component("defenderTaskCheckerImpl")
public class DefenderTaskCheckerImpl extends AbstractDefenderService implements DefenderTaskChecker {

    @Autowired
    private AlterShieldSchedulerEventPublisher alterShieldSchedulerEventPublisher;

    @Autowired
    private ExeChangeNodeService changeNodeService;


    /**
     * Check the execution status of all detection tasks pre/post once and combine the final results
     *
     * @param changeOrderId Change order id
     * @param nodeId Change node id
     * @param detectGroupId Detection group id
     * @return Task result
     */
    @Override
    public DefenderTaskResult checkDetectProcess(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        // 1、Parameter verification
        if (StringUtils.isBlank(changeOrderId) || StringUtils.isBlank(detectGroupId)) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskCheckerImpl", "checkDetectProcess",
                    "change order id and detect group id can`t be empty", changeOrderId, detectGroupId);
            return DefenderTaskResult.failWithNoRetry("Illegal arguments");
        }

        // 2、Get all matched defense rules based on group ID
        List<ExeDefenderDetectEntity> detectEntities = exeDefenderDetectRepository.selectByGroupId(detectGroupId);
        if (CollectionUtils.isEmpty(detectEntities)) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskCheckerImpl", "checkDetectProcess",
                    "No defense rule execution record found", changeOrderId, detectGroupId);
            return DefenderTaskResult.failWithRetry("No defense rule execution record found");
        }
        long detectCount = detectEntities.size();

        // 3、Filter out trial run rules
        List<MetaDefenderRuleEntity> ruleEntities = metaDefenderRuleRepository.selectByRuleIds(detectEntities
                .stream()
                .map(ExeDefenderDetectEntity::getRuleId)
                .collect(Collectors.toSet()));
        Map<String, MetaDefenderRuleEntity> ruleMaps = ruleEntities.stream()
                .filter(e -> !DefenderRuleStatusEnum.GRAY.equals(e.getStatus()))
                .collect(Collectors.toMap(MetaDefenderRuleEntity::getId, e -> e));

        detectEntities = detectEntities.stream()
                .filter(e -> {
                    MetaDefenderRuleEntity rule = ruleMaps.get(e.getRuleId());
                    return !Objects.isNull(rule) && !DefenderRuleStatusEnum.GRAY.equals(rule.getStatus());
                }).collect(Collectors.toList());

        // 4、Determine whether there are rules in blocking status that have not been ignored, and if so, block them.
        List<ExeDefenderDetectEntity> blockRule = detectEntities.stream().filter(e -> e.isBlocked() && !e.isIgnored()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(blockRule)) {
            // Publish detection failure event
            publishFailEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // Update node status to failed
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("Task execution completed");
        }

        // 5、Determine whether there is INIT/EXE and other states, and if so, RETRY (as long as it is not blocked and there are unfinished rules, RETRY)
        long reTryCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.INIT.equals(e.getStatus()) || DefenderStatusEnum.EXE.equals(e.getStatus()))
                .count();
        if (reTryCount > 0) {
            return DefenderTaskResult.failWithRetry("There is a rule execution record in INIT/EXE status");
        }

        // 6、All passed/cancel + pass = full rule/cancel + pass + exception handling policy is release = full rule, then the release is successful
        // 6.1 Number of passes
        long passCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.PASS.equals(e.getStatus())).count();
        // 6.2 Number of cancellations
        long cancelCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.CANCEL.equals(e.getStatus())).count();
        // 6.3 The rule is abnormal and the exception handling strategy is release
        long exceptionReleaseCount = detectEntities.stream()
                .filter(e -> {
                    DefenderStatusEnum defenderStatus = e.getStatus();
                    MetaDefenderRuleEntity rule = ruleMaps.get(e.getRuleId());
                    if (Objects.isNull(rule)) {
                        return false;
                    }
                    return (DefenderStatusEnum.EXCEPTION.equals(defenderStatus) || DefenderStatusEnum.TIMEOUT.equals(defenderStatus))
                            && ExceptionStrategyEnum.RELEASE.equals(rule.getExceptionStrategy());
                }).count();
        // 6.4 execution judgment
        if ((passCount == detectCount)
                || (passCount + cancelCount == detectCount)
                || (passCount + cancelCount + exceptionReleaseCount == detectCount)) {
            // Publish detection pass event
            publishPassEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // Update node status to successful
            updateNodeStatusFinishPass(nodeId, stage);
            return DefenderTaskResult.succeed("Task execution completed");
        }

        // 7、Filter canceled rules
        detectEntities = detectEntities.stream()
                .filter(e -> !DefenderStatusEnum.CANCEL.equals(e.getStatus()))
                .collect(Collectors.toList());

        // 8、If the existing blocking field is true and is not ignored, the blocking is issued.
        blockRule = detectEntities.stream().filter(e -> e.isBlocked() && !e.isIgnored()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(blockRule)) {
            // Publish detection failure event
            publishFailEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // Update node status to failed
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("Task execution completed");
        }

        // 9、If there is a rule whose status is exception/timeout and is not ignored and the exception handling logic is blocking, then block is issued.
        blockRule = detectEntities.stream().filter(e -> {
            DefenderStatusEnum status = e.getStatus();
            MetaDefenderRuleEntity rule = ruleMaps.get(e.getRuleId());
            if (Objects.isNull(rule)) {
                return false;
            }
            return (DefenderStatusEnum.EXCEPTION.equals(status) || DefenderStatusEnum.TIMEOUT.equals(status))
                    && ExceptionStrategyEnum.BLOCK.equals(rule.getExceptionStrategy());
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(blockRule)) {
            publishFailEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // Update node status to failed
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("Task execution completed");
        }

        // 10、The rest of the cases are judged as passed and the Node status needs to be updated.
        publishPassEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
        // Update node status to successful
        updateNodeStatusFinishPass(nodeId, stage);

        return DefenderTaskResult.succeed("Task execution completed");
    }

    /**
     * Publish detection failure event*
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage pre or post
     */
    private void publishFailEvent(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        eventPublish(changeOrderId, nodeId, detectGroupId, stage, DefenderVerdictEnum.FAIL, changeSceneKey, changeStepType);
    }

    /**
     * Publish detection success event
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage pre or post
     */
    private void publishPassEvent(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        eventPublish(changeOrderId, nodeId, detectGroupId, stage, DefenderVerdictEnum.PASS, changeSceneKey, changeStepType);
    }

    /**
     * publish event
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage pre or post
     * @param verdict detection result
     */
    private void eventPublish(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage,
                              DefenderVerdictEnum verdict, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {

        DefenderDetectFinishEvent event = buildDefenseFinishEvent(changeOrderId, nodeId, detectGroupId,
                stage, verdict,changeSceneKey, changeStepType);

        alterShieldSchedulerEventPublisher.publish(changeOrderId, event);
    }

    /**
     * Update node status completed & detection passed
     * @param nodeId nodeId
     * @param stage pre or post
     */
    private void updateNodeStatusFinishPass(String nodeId, DefenseStageEnum stage) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFinish(nodeEntity, stage, true);
    }

    /**
     * End of updating node status & detection failed
     * @param nodeId nodeId
     * @param stage pre or post
     */
    private void updateNodeStatusFinishFail(String nodeId, DefenseStageEnum stage) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFinish(nodeEntity, stage, false);
    }

    /**
     * Update node status to failed
     * @param nodeId nodeId
     * @param stage pre or post
     * @param msg failure message
     */
    private void updateNodeStatusFail(String nodeId, DefenseStageEnum stage, String msg) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFail(nodeEntity, stage, msg);
    }

}