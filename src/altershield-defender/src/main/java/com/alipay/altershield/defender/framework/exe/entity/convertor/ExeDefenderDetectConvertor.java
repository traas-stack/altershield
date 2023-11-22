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
package com.alipay.altershield.defender.framework.exe.entity.convertor;

import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.defender.framework.exe.dal.dataobject.ExeDefenderDetectDO;
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
 * Change Defense Detection Record Entity Converter
 *
 * @author yhaoxuan
 * @version ExeDefenderDetectConvertor.java, v 0.1 2022年08月29日 3:28 下午 yhaoxuan
 */
@Component("exeDefenderDetectConvertor")
public class ExeDefenderDetectConvertor extends AbstractDefenderEntityConvertor {

    /**
     * Convert data objects to entity objects
     * <p>
     *
     * @param modelDO Data object
     * @return Entity object
     */
    public ExeDefenderDetectEntity convert2Model(ExeDefenderDetectDO modelDO) {
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
     * Convert entity object to data object
     * <p>
     *
     * @param model Entity object
     * @return Data object
     */
    public ExeDefenderDetectDO convert2DO(ExeDefenderDetectEntity model) {
        if (model == null) {
            return null;
        }

        ExeDefenderDetectDO doModel = new ExeDefenderDetectDO();
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
     * Convert data object list to entity object list
     * <p>
     *
     * @param modelDOList Data object list
     * @return Entity object list
     */
    public List<ExeDefenderDetectEntity> convert2ModelList(List<ExeDefenderDetectDO> modelDOList) {
        if (modelDOList == null) {
            return Collections.emptyList();
        }

        List<ExeDefenderDetectEntity> modelList = new ArrayList<>();

        for (ExeDefenderDetectDO doModel : modelDOList) {
            modelList.add(convert2Model(doModel));
        }

        return modelList;
    }

    /**
     * Convert entity object list to data object list
     * <p>
     *
     * @param modelList Data object list
     * @return Entity object list
     */
    public List<ExeDefenderDetectDO> convert2DOList(List<ExeDefenderDetectEntity> modelList) {
        if (modelList == null) {
            return Collections.emptyList();
        }

        List<ExeDefenderDetectDO> DOList = new ArrayList<>();

        for (ExeDefenderDetectEntity model : modelList) {
            DOList.add(convert2DO(model));
        }

        return DOList;
    }
}