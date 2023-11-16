/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.entity.convertor;

import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.defender.framework.exe.dal.dataobject.OpsCloudExeDefenderDetectDO;
import com.alipay.altershield.defender.framework.model.AbstractDefenderEntityConvertor;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.framework.core.risk.model.enums.FeedbackStatusEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 变更防御校验记录实体转换器
 *
 * @author haoxuan
 * @version ExeDefenderDetectConvertor.java, v 0.1 2022年08月29日 3:28 下午 haoxuan
 */
@Component("exeDefenderDetectConvertor")
public class ExeDefenderDetectConvertor extends AbstractDefenderEntityConvertor {

    /**
     * 实体DO对象转换为实体对象
     * <p>
     *
     * @param modelDO DO对象
     * @return 实体对象
     */
    public ExeDefenderDetectEntity convert2Model(OpsCloudExeDefenderDetectDO modelDO) {
        if (modelDO == null) {
            return null;
        }

        ExeDefenderDetectEntity model = new ExeDefenderDetectEntity(shardingKeyValueStorage);
        model.setDetectExeId(modelDO.getDetectExeId());
        model.setGmtCreate(modelDO.getGmtCreate());
        model.setGmtModified(modelDO.getGmtModified());
        model.setDetectGroupId(modelDO.getDetectGroupId());
        model.setChangeOrderId(modelDO.getChangeOrderId());
        model.setNodeId(modelDO.getNodeId());
        model.setRuleId(modelDO.getRuleId());
        model.setRuleName(modelDO.getRuleName());
        model.setRuleOwner(modelDO.getRuleOwner());
        model.setPluginKey(modelDO.getPluginKey());
        model.setMainClass(modelDO.getMainClass());
        if (StringUtils.isNotBlank(modelDO.getStatus())) {
            model.setStatus(DefenderStatusEnum.getByStatus(modelDO.getStatus()));
        }
        model.setBlocked("Y".equalsIgnoreCase(modelDO.getBlocked()));
        model.setBlockMsg(modelDO.getBlockMsg());
        model.setMsg(modelDO.getMsg());
        model.getResultRef().write(modelDO.getResultRef());
        model.setGmtPlan(modelDO.getGmtPlan());
        model.setGmtStart(modelDO.getGmtStart());
        model.setGmtFinish(modelDO.getGmtFinish());
        model.setIgnored("Y".equalsIgnoreCase(modelDO.getIgnored()));
        if (StringUtils.isNotBlank(modelDO.getFeedbackStatus())) {
            model.setFeedbackStatus(FeedbackStatusEnum.getByStatus(modelDO.getFeedbackStatus()));
        }
        model.setFeedbackReason(modelDO.getFeedbackReason());
        model.setFeedbackOperator(modelDO.getFeedbackOperator());
        model.setExternalId(modelDO.getExternalId());
        if (StringUtils.isNotBlank(modelDO.getStage())) {
            model.setStage(DefenseStageEnum.getByStage(modelDO.getStage()));
        }

        model.setAllowIgnore("Y".equalsIgnoreCase(modelDO.getAllowIgnore()));
        return model;
    }

    /**
     * 实体对象转换为DO对象
     * <p>
     *
     * @param model 实体对象模型
     * @return DO对象
     */
    public OpsCloudExeDefenderDetectDO convert2DO(ExeDefenderDetectEntity model) {
        if (model == null) {
            return null;
        }

        OpsCloudExeDefenderDetectDO doModel = new OpsCloudExeDefenderDetectDO();
        doModel.setDetectExeId(model.getDetectExeId());
        doModel.setGmtCreate(model.getGmtCreate());
        doModel.setGmtModified(model.getGmtModified());
        doModel.setDetectGroupId(model.getDetectGroupId());
        doModel.setChangeOrderId(model.getChangeOrderId());
        doModel.setNodeId(model.getNodeId());
        doModel.setRuleId(model.getRuleId());
        doModel.setRuleName(model.getRuleName());
        doModel.setRuleOwner(model.getRuleOwner());
        doModel.setPluginKey(model.getPluginKey());
        doModel.setMainClass(model.getMainClass());
        if (model.getStatus() != null) {
            doModel.setStatus(model.getStatus().getStatus());
        }
        doModel.setBlocked(model.isBlocked() ? "Y" : "N");
        doModel.setBlockMsg(model.getBlockMsg());
        doModel.setMsg(model.getMsg());
        doModel.setResultRef(model.getResultRef().readObject());
        doModel.setGmtPlan(model.getGmtPlan());
        doModel.setGmtStart(model.getGmtStart());
        doModel.setGmtFinish(model.getGmtFinish());
        doModel.setIgnored(model.isIgnored() ? "Y" : "N");
        if (model.getFeedbackStatus() != null) {
            doModel.setFeedbackStatus(model.getFeedbackStatus().getStatus());
        }
        doModel.setFeedbackReason(model.getFeedbackReason());
        doModel.setFeedbackOperator(model.getFeedbackOperator());
        doModel.setExternalId(model.getExternalId());
        if (model.getStage() != null) {
            doModel.setStage(model.getStage().getStage());
        }
        doModel.setUid(IdUtil.getUid(model.getNodeId()));
        doModel.setAllowIgnore(model.isAllowIgnore() ? "Y" : "N");

        return doModel;
    }

    /**
     * 实体DO对象转换为实体对象
     * <p>
     *
     * @param modelDOList DO对象
     * @return 实体对象
     */
    public List<ExeDefenderDetectEntity> convert2ModelList(List<OpsCloudExeDefenderDetectDO> modelDOList) {
        if (modelDOList == null) {
            return Collections.emptyList();
        }

        List<ExeDefenderDetectEntity> modelList = new ArrayList<>();

        for (OpsCloudExeDefenderDetectDO doModel : modelDOList) {
            modelList.add(convert2Model(doModel));
        }

        return modelList;
    }

    /**
     * 实体对象为实体DO对象
     * <p>
     *
     * @param modelList DO对象
     * @return 实体DO对象
     */
    public List<OpsCloudExeDefenderDetectDO> convert2DOList(List<ExeDefenderDetectEntity> modelList) {
        if (modelList == null) {
            return Collections.emptyList();
        }

        List<OpsCloudExeDefenderDetectDO> DOList = new ArrayList<>();

        for (ExeDefenderDetectEntity model : modelList) {
            DOList.add(convert2DO(model));
        }

        return DOList;
    }
}