/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.impl;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.service.ExeChangeNodeService;
import com.alipay.opscloud.api.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.opscloud.api.defender.DefenderTaskChecker;
import com.alipay.opscloud.api.defender.entity.ExeDefenderDetectEntity;
import com.alipay.opscloud.api.defender.enums.DefenderRuleStatusEnum;
import com.alipay.opscloud.api.defender.enums.DefenderVerdictEnum;
import com.alipay.opscloud.api.defender.result.DefenderTaskResult;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderDetectFinishEvent;
import com.alipay.opscloud.api.scheduler.event.publish.OpsCloudSchedulerEventPublisher;
import com.alipay.opscloud.defender.AbstractDefenderService;
import com.alipay.opscloud.defender.enums.ExceptionStrategyEnum;
import com.alipay.opscloud.defender.meta.entity.MetaDefenderRuleEntity;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 变更防御校验任务批次状态检查器
 *
 * @author haoxuan
 * @version DefenderTaskCheckerImpl.java, v 0.1 2022年08月29日 9:26 下午 haoxuan
 */
@Component("defenderTaskCheckerImpl")
public class DefenderTaskCheckerImpl extends AbstractDefenderService implements DefenderTaskChecker {

    @Autowired
    private OpsCloudSchedulerEventPublisher opsCloudSchedulerEventPublisher;

    @Autowired
    private ExeChangeNodeService changeNodeService;


    /**
     * 检查一次前/后置全部的校验任务的执行状态，并组合最终结果
     *
     * @param changeOrderId 变更工单id
     * @param nodeId 变更节点id
     * @param detectGroupId 校验分组id
     * @return 任务调度结果
     */
    @Override
    public DefenderTaskResult checkDetectProcess(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        // 1、参数校验
        if (StringUtils.isBlank(changeOrderId) || StringUtils.isBlank(detectGroupId)) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskCheckerImpl", "checkDetectProcess",
                    "change order id and detect group id can`t be empty", changeOrderId, detectGroupId);
            return DefenderTaskResult.failWithNoRetry("入参非法");
        }

        // whiteList check
        if (!whiteListCheck(changeSceneKey)) {
            OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "白名单校验不通过", changeSceneKey);
            return DefenderTaskResult.failWithNoRetry("白名单校验不通过");
        }

        // 2、根据groupId取所有匹配到的防御规则
        List<ExeDefenderDetectEntity> detectEntities = exeDefenderDetectRepository.selectByGroupId(detectGroupId);
        if (CollectionUtils.isEmpty(detectEntities)) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderTaskCheckerImpl", "checkDetectProcess",
                    "未查询到防御规则执行记录", changeOrderId, detectGroupId);
            return DefenderTaskResult.failWithRetry("未查询到防御规则执行记录");
        }
        long detectCount = detectEntities.size();

        // 3、过滤掉试运行规则（不过滤未开始的规则是防止在校验过程中关闭该规则）（存在问题：校验过程中改为试运行？采用更新的状态？）
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

        // 4、判断是否有阻断状态且未忽略的规则，有则发布阻断
        List<ExeDefenderDetectEntity> blockRule = detectEntities.stream().filter(e -> e.isBlocked() && !e.isIgnored()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(blockRule)) {
            // 发布校验失败事件
            publishFailEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // 更新node状态为失败
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("任务执行完成");
        }

        // 5、判断是否存在INIT/EXE等状态，存在则RETRY（只要不阻断，并且存在有未执行完的规则就RETRY）
        long reTryCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.INIT.equals(e.getStatus()) || DefenderStatusEnum.EXE.equals(e.getStatus())).count();
        if (reTryCount > 0) {
            return DefenderTaskResult.failWithRetry("存在INIT/EXE状态的规则执行记录");
        }
        // --------------- 以上为非终态情况，处理未阻断或者重试，以下为所有规则均处于终态的校验逻辑 --------------------

        // 6、全部通过/取消+通过=全量规则/取消+通过+异常处理策略为放行=全量规则，则发布成功
        // 6.1 通过数
        long passCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.PASS.equals(e.getStatus())).count();
        // 6.2 取消数
        long cancelCount = detectEntities.stream()
                .filter(e -> DefenderStatusEnum.CANCEL.equals(e.getStatus())).count();
        // 6.3 规则异常且异常处理策略为放行
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
        // 6.4 执行判断
        if ((passCount == detectCount)
                || (passCount + cancelCount == detectCount)
                || (passCount + cancelCount + exceptionReleaseCount == detectCount)) {
            // 发布校验通过事件
            publishPassEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // 更新node状态为成功
            updateNodeStatusFinishPass(nodeId, stage);
            return DefenderTaskResult.succeed("任务执行完成");
        }

        // 7、过滤取消的规则
        detectEntities = detectEntities.stream()
                .filter(e -> !DefenderStatusEnum.CANCEL.equals(e.getStatus()))
                .collect(Collectors.toList());

        // 8、若存在阻断字段为 true且未忽略，则发布阻断
        blockRule = detectEntities.stream().filter(e -> e.isBlocked() && !e.isIgnored()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(blockRule)) {
            // 发布校验失败事件
            publishFailEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
            // 更新node状态为失败
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("任务执行完成");
        }

        // 9、若存在状态为异常/超时且未忽略且异常处理逻辑为阻断的规则，则发布阻断
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
            // 更新node状态为失败
            updateNodeStatusFinishFail(nodeId, stage);
            return DefenderTaskResult.succeed("任务执行完成");
        }

        // 10、其余情况均判定为通过，需更新Node状态
        publishPassEvent(changeOrderId, nodeId, detectGroupId, stage, changeSceneKey, changeStepType);
        // 更新node状态为成功
        updateNodeStatusFinishPass(nodeId, stage);

        return DefenderTaskResult.succeed("任务执行完成");
    }

    /**
     * 发布校验失败事件*
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage 前后置
     */
    private void publishFailEvent(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        eventPublish(changeOrderId, nodeId, detectGroupId, stage, DefenderVerdictEnum.FAIL, changeSceneKey, changeStepType);
    }

    /**
     * 发布校验成功事件*
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage 前后置
     */
    private void publishPassEvent(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {
        eventPublish(changeOrderId, nodeId, detectGroupId, stage, DefenderVerdictEnum.PASS, changeSceneKey, changeStepType);
    }

    /**
     * 事件发布*
     * @param changeOrderId orderId
     * @param nodeId nodeId
     * @param detectGroupId groupId
     * @param stage 前后轴
     * @param verdict 校验结果
     */
    private void eventPublish(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage,
                              DefenderVerdictEnum verdict, String changeSceneKey, MetaChangeStepTypeEnum changeStepType) {

        DefenderDetectFinishEvent event = buildDefenseFinishEvent(changeOrderId, nodeId, detectGroupId,
                stage, verdict,changeSceneKey, changeStepType);

        opsCloudSchedulerEventPublisher.publish(changeOrderId, event);
    }

    /**
     * 更新node状态结束 & 校验通过*
     * @param nodeId nodeId
     * @param stage 前后置
     */
    private void updateNodeStatusFinishPass(String nodeId, DefenseStageEnum stage) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFinish(nodeEntity, stage, true);
    }

    /**
     * 更新node状态结束 & 校验不通过*
     * @param nodeId nodeId
     * @param stage 前后置
     */
    private void updateNodeStatusFinishFail(String nodeId, DefenseStageEnum stage) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFinish(nodeEntity, stage, false);
    }

    /**
     * 更新node状态为失败*
     * @param nodeId nodeId
     * @param stage 前后置
     * @param msg 失败信息
     */
    private void updateNodeStatusFail(String nodeId, DefenseStageEnum stage, String msg) {
        ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(nodeId);
        changeNodeService.setNodeCheckFail(nodeEntity, stage, msg);
    }

}