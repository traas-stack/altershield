/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.listener;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.opscloud.api.change.exe.service.ExeChangeNodeService;
import com.alipay.opscloud.api.change.exe.service.ExeChangeOrderQueryService;
import com.alipay.opscloud.api.defender.DefenderTaskExecutor;
import com.alipay.opscloud.api.defender.request.DefenderTaskExecuteRequest;
import com.alipay.opscloud.api.defender.result.DefenderTaskResult;
import com.alipay.opscloud.api.scheduler.enums.OpsCloudScheduleEventResultStatus;
import com.alipay.opscloud.api.scheduler.event.defender.DefenderDetectEvent;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventContext;
import com.alipay.opscloud.api.scheduler.event.listener.OpsCloudSchedulerEventListener;
import com.alipay.opscloud.api.scheduler.event.result.OpsCloudSchedulerEventExecuteResult;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.change.model.OpsCloudChangeContent;
import com.alipay.opscloud.spi.defender.model.request.ChangeBaseInfo;
import com.alipay.opscloud.spi.defender.model.request.ChangeContent;
import com.alipay.opscloud.spi.defender.model.request.ChangeExecuteInfo;
import com.alipay.opscloud.spi.defender.model.request.ChangeInfluenceInfo;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 变更防御校验事件监听器
 *
 * @author haoxuan
 * @version DefenderDetectEventListener.java, v 0.1 2022年08月30日 3:19 下午 haoxuan
 */
@Component("defenderDetectEventListener")
public class DefenderDetectEventListener implements OpsCloudSchedulerEventListener<DefenderDetectEvent> {

    @Autowired
    private DefenderTaskExecutor defenderTaskExecutor;

    @Autowired
    private ExeChangeNodeService exeChangeNodeService;

    @Autowired
    private ExeChangeOrderQueryService exeChangeOrderQueryService;

    /**
     * 接收变更防御校验事件，开始校验逻辑
     *
     * @param context 事件上下文信息
     * @param event 事件结构体
     * @return 事件处理结果
     */
    @Override
    public OpsCloudSchedulerEventExecuteResult onEvent(OpsCloudSchedulerEventContext context, DefenderDetectEvent event) {
        OpsCloudLoggerManager.log("info", Loggers.DEFENDER, "[DefenderDetectEventListener] 防御消费单个规则校验事件", event);

        OpsCloudSchedulerEventExecuteResult result = new OpsCloudSchedulerEventExecuteResult();

        DefenderTaskExecuteRequest request = buildTaskExecuteRequest(event);
        DefenderTaskResult executeRst = defenderTaskExecutor.execute(request);
        result.setMsg(executeRst.getMsg());
        if (executeRst.isNeedRetry()) {
            result.setStatus(OpsCloudScheduleEventResultStatus.RETRY);
        }
        result.setStatus(OpsCloudScheduleEventResultStatus.SUCCESS);
        return result;
    }

    private DefenderTaskExecuteRequest buildTaskExecuteRequest(DefenderDetectEvent event) {
        DefenderTaskExecuteRequest request = new DefenderTaskExecuteRequest();
        request.setChangeSceneKey(event.getChangeSceneKey());
        request.setChangeKey(event.getChangeKey());
        request.setPluginKey(event.getPluginKey());
        request.setMainClass(event.getMainClass());

        request.setChangeOrderId(event.getChangeOrderId());
        request.setNodeId(event.getNodeId());
        request.setDetectGroupId(event.getDetectGroupId());
        request.setDetectExeId(event.getDetectExeId());
        request.setRuleId(event.getRuleId());
        request.setExternalId(event.getExternalId());
        request.setDefenseStage(event.getStage());

        ExeNodeEntity node = exeChangeNodeService.getNode(event.getNodeId());
        ExeChangeOrderEntity changeOrder = exeChangeOrderQueryService.getChangeOrder(event.getChangeOrderId());
        // 组装变更原始信息
        request.setChangeBaseInfo(buildChangeBaseInfo(changeOrder));
        // 组装变更执行信息
        request.setChangeExecuteInfo(buildChangExecuteInfo(event));
        // 组装变更影响面信息
        request.setChangeInfluenceInfo(buildChangeInfluenceInfo(node, changeOrder));

        return request;
    }

    private ChangeInfluenceInfo buildChangeInfluenceInfo(ExeNodeEntity node, ExeChangeOrderEntity changeOrder) {

        ChangeInfluenceInfo changeInfluenceInfo = new ChangeInfluenceInfo();
        changeInfluenceInfo.setApps(changeOrder.getChangeAppsRef().readObject());
        if (!Objects.isNull(node.getSearchExtRef())) {
            changeInfluenceInfo.setExtInfo(node.getSearchExtRef().readObject());
        }

        return changeInfluenceInfo;
    }

    private ChangeExecuteInfo buildChangExecuteInfo(DefenderDetectEvent event) {

        ChangeExecuteInfo changeExecuteInfo = new ChangeExecuteInfo();
        changeExecuteInfo.setChangeStartTime(event.getChangeStartTime());
        if (event.getChangeFinishTime() != null) {
            changeExecuteInfo.setChangeFinishTime(event.getChangeFinishTime());
        }
        changeExecuteInfo.setOrderPhase(event.isOrderPhase());
        return changeExecuteInfo;
    }

    private ChangeBaseInfo buildChangeBaseInfo(ExeChangeOrderEntity changeOrder) {
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

        return changeBaseInfo;
    }


}