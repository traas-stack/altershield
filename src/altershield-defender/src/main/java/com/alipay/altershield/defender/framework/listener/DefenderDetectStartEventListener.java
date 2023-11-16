/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.listener;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.opscloud.api.change.exe.service.ExeChangeNodeService;
import com.alipay.opscloud.api.change.exe.service.ExeChangeOrderQueryService;
import com.alipay.opscloud.api.defender.DefenderDetectService;
import com.alipay.opscloud.api.defender.enums.DefenderVerdictEnum;
import com.alipay.opscloud.api.defender.request.DefenderDetectRequest;
import com.alipay.opscloud.api.defender.result.DefenderDetectSubmitResult;
import com.alipay.opscloud.api.scheduler.enums.OpsCloudScheduleEventResultStatus;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderDetectFinishEvent;
import com.alipay.opscloud.api.scheduler.event.extension.InformationExtensionFinishEvent;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventContext;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventListener;
import com.alipay.opscloud.api.scheduler.event.publish.OpsCloudSchedulerEventPublisher;
import com.alipay.opscloud.api.scheduler.event.result.OpsCloudSchedulerEventExecuteResult;
import com.alipay.opscloud.defender.AbstractDefenderService;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.change.model.OpsCloudChangeContent;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.spi.defender.model.request.ChangeBaseInfo;
import com.alipay.opscloud.spi.defender.model.request.ChangeContent;
import com.alipay.opscloud.spi.defender.model.request.ChangeExecuteInfo;
import com.alipay.opscloud.spi.defender.model.request.ChangeInfluenceInfo;
import com.alipay.opscloud.spi.influence.model.enums.ExtensionKey;
import com.alipay.opscloud.spi.util.MiscUtil;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 变更防御前后置校验开始事件监听器
 *
 * @author haoxuan
 * @version DefenderDetectStartEventListener.java, v 0.1 2022年09月05日 5:38 下午 haoxuan
 */
@Component("defenderDetectStartEventListener")
public class DefenderDetectStartEventListener extends AbstractDefenderService
        implements OpsCloudSchedulerEventListener<InformationExtensionFinishEvent> {

    @Autowired
    private ExeChangeOrderQueryService exeChangeOrderQueryService;

    @Autowired
    private ExeChangeNodeService exeChangeNodeService;

    @Autowired
    private DefenderDetectService defenderDetectService;

    @Autowired
    private OpsCloudSchedulerEventPublisher opsCloudSchedulerEventPublisher;

    @Autowired
    private ExeChangeNodeService changeNodeService;

    @Override
    public OpsCloudSchedulerEventExecuteResult onEvent(OpsCloudSchedulerEventContext context, InformationExtensionFinishEvent event) {
        OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "[DefenderDetectStartEventListener] 防御消费校验开始事件", event);
        
        if (event == null || StringUtils.isBlank(event.getChangeOrderId()) || StringUtils.isBlank(event.getNodeExeId())) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderDetectStartEventListener",
                    "onEvent", "fail", "event or change order id or node id can`t be empty");
            return new OpsCloudSchedulerEventExecuteResult("参数不合法", OpsCloudScheduleEventResultStatus.ABANDON);
        }

        // 1.0 查询工单信息
        ExeChangeOrderEntity changeOrder = exeChangeOrderQueryService.getChangeOrder(event.getChangeOrderId());
        if (changeOrder == null) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderDetectStartEventListener",
                    "onEvent", "fail", "change order does not exist", event.getChangeOrderId());
            return new OpsCloudSchedulerEventExecuteResult("变更工单不存在", OpsCloudScheduleEventResultStatus.ABANDON);
        }

        // 2.0 查询node信息
        ExeNodeEntity node = exeChangeNodeService.getNode(event.getNodeExeId());
        if (node == null) {
            OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderDetectStartEventListener",
                    "onEvent", "fail", "change node does not exist", event.getChangeOrderId());
            return new OpsCloudSchedulerEventExecuteResult("变更节点不存在", OpsCloudScheduleEventResultStatus.ABANDON);
        }

        // 获取changeTags
        Set<String> changeTags = getChangeTags(node);
        OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "[DefenderDetectStartEventListener] 防御拉取标签", changeTags, node.getNodeExeId());

        // 2.0 组装请求基本信息
        DefenderDetectRequest request = new DefenderDetectRequest();
        request.setChangeOrderId(event.getChangeOrderId());
        request.setNodeId(event.getNodeExeId());
        request.setEmergency(event.isEmergency());
        request.setDefenseStage(event.getStage());
        request.setChangeSceneKey(changeOrder.getChangeSceneKey());
        request.setChangeKey(node.getChangeKey());
        request.setChangeTagIds(changeTags);
        request.setStepTypeEnum(event.getChangeStepType());

        // 3.0 组装变更原始信息
        ChangeBaseInfo changeBaseInfo = new ChangeBaseInfo();
        changeBaseInfo.setChangeTitle(changeOrder.getChangeTitle());
        changeBaseInfo.setCreator(changeOrder.getCreator());
        changeBaseInfo.setExecutor(changeOrder.getExecutor());
        changeBaseInfo.setPlatform(changeOrder.getPlatform());
        changeBaseInfo.setChangeParamJSON(changeOrder.getParamRef().readObject());

        // 3.1 组装变更内容信息
        ChangeContent changeContent = new ChangeContent();
        List<OpsCloudChangeContent> contents = changeOrder.getChangeContentRef().readObject();
        if (!CollectionUtils.isEmpty(contents)) {
            // 3.1.2 这一步有点恶心，解析OpsCloudChangeContent的结构，默认使用第一个content的类型，因为一个工单只有一个content类型
            changeContent.setChangeContentType(contents.get(0).getContentType().getTypeName());
            changeContent.setChangeContentInstance(contents.stream().map(OpsCloudChangeContent::getInstanceName)
                    .collect(Collectors.toList()));
        }
        changeBaseInfo.setChangeContent(changeContent);
        request.setChangeBaseInfo(changeBaseInfo);

        // 4.0 组装变更执行信息
        ChangeExecuteInfo changeExecuteInfo = new ChangeExecuteInfo();
        changeExecuteInfo.setChangeStartTime(event.getChangeStartTime());
        if (event.getChangeFinishTime() != null) {
            changeExecuteInfo.setChangeFinishTime(event.getChangeFinishTime());
        }
        changeExecuteInfo.setOrderPhase(event.isOrderPhase());
        request.setChangeExecuteInfo(changeExecuteInfo);

        // 5.0 组装变更影响面信息
        ChangeInfluenceInfo changeInfluenceInfo = new ChangeInfluenceInfo();
        changeInfluenceInfo.setApps(changeOrder.getChangeAppsRef().readObject());
        changeInfluenceInfo.setExtInfo(node.getSearchExtRef().readObject());
        request.setChangeInfluenceInfo(changeInfluenceInfo);

        // 6.0 提交防御校验
        OpsCloudResult<DefenderDetectSubmitResult> result = defenderDetectService.asyncDetect(request);
        // 6.1 提交失败，需要重试
        if (result == null || !result.isSuccess()) {
            return new OpsCloudSchedulerEventExecuteResult("重新提交防御校验任务", OpsCloudScheduleEventResultStatus.RETRY);
        }
        // 6.2 提交成功，未进行防御（未匹配到规则/应急变更）时，直接发布防御校验结束事件
        DefenderDetectSubmitResult submitResult = result.getDomain();
        if (!submitResult.isDefensed()) {
            DefenderDetectFinishEvent finishEvent = buildDefenseFinishEvent(request.getChangeOrderId(), request.getNodeId(),
                    submitResult.isDefensed(), submitResult.getMsg(), "", submitResult.getStage(),
                    DefenderVerdictEnum.PASS, event.getChangeSceneKey(), event.getChangeStepType());
            opsCloudSchedulerEventPublisher.publish(request.getChangeOrderId(), finishEvent);
            // 更新node状态为PASS通过
            ExeNodeEntity nodeEntity = changeNodeService.lockNodeById(request.getNodeId());
            nodeEntity.setMsg(submitResult.getMsg());
            // 此处状态机转换可能出现错误情况，故需要对于异常情况进行处理
            try {
                changeNodeService.setNodeCheckFinish(nodeEntity, request.getDefenseStage(), true);
            } catch (Exception e) {
                // 转态机状转换异常，打印错误日志，事件不重试，需排查问题
                OpsCloudLoggerManager.log("error", Loggers.DEFENDER, "DefenderDetectStartEventListener",
                        "onEvent", "fail", "状态机更新异常: setNodeCheckFinish", nodeEntity.getNodeExeId(), request.getDefenseStage());
            }
        }

        return OpsCloudSchedulerEventExecuteResult.success("防御校验提交成功");
    }

    /**
     * 从node中取标签集合*
     * @param node node
     * @return tagId集合
     */
    private Set<String> getChangeTags(ExeNodeEntity node){
        if (Objects.isNull(node)) {
            return new TreeSet<>();
        }

        return MiscUtil.getSetFromExtension(node.getSearchExtRef().readObject(), ExtensionKey.CHANGE_TAG, true);
    }
}