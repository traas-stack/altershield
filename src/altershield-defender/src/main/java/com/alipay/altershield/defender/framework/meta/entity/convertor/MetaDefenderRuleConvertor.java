/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.meta.entity.convertor;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.common.largefield.ref.KvRef;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.defender.framework.enums.DefenseRangeTypeEnum;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.defender.framework.meta.dal.dataobject.MetaDefenderRuleDO;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.defender.framework.model.AbstractDefenderEntityConvertor;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import com.alipay.altershield.shared.defender.enums.DefenderIgnoreTypeEnum;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import com.alipay.altershield.shared.defender.model.ChangeFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Change Defense Rules Entity Converter
 * 
 * @author yhaoxuan
 * @version MetaDefenderRuleConvertor.java, v 0.1 2022年08月25日 9:42 下午 yhaoxuan
 */
@Component("metaDefenderRuleConvertor")
public class MetaDefenderRuleConvertor extends AbstractDefenderEntityConvertor {

    @Autowired
    private KeyValueStorage singleKeyValueStorage;

    /**
     * 实体DO对象转换为实体对象
     * <p>
     *
     * @param modelDO DO对象
     * @return 实体对象
     */
    public MetaDefenderRuleEntity convert2Model(MetaDefenderRuleDO modelDO) {
        if (modelDO == null) {
            return null;
        }

        MetaDefenderRuleEntity model = new MetaDefenderRuleEntity(singleKeyValueStorage);
        model.setId(modelDO.getId());
        model.setGmtCreate(modelDO.getGmtCreate());
        model.setGmtModified(modelDO.getGmtModified());
        model.setName(modelDO.getName());
        model.setSuggestion(modelDO.getSuggestion());
        model.setOwner(modelDO.getOwner());
        if (StringUtils.isNotBlank(modelDO.getStatus())) {
            model.setStatus(DefenderRuleStatusEnum.getByStatus(modelDO.getStatus()));
        }
        if (StringUtils.isNotBlank(modelDO.getExceptionStrategy())) {
            model.setExceptionStrategy(ExceptionStrategyEnum.getByStrategy(modelDO.getExceptionStrategy()));
        }
        if (StringUtils.isNotBlank(modelDO.getDefenseRangeType())) {
            model.setDefenseRangeType(DefenseRangeTypeEnum.getByType(modelDO.getDefenseRangeType()));
        }
        if (StringUtils.isNotBlank(modelDO.getStage())) {
            model.setStage(DefenseStageEnum.getByStage(modelDO.getStage()));
        }
        model.setDefenseRangeKey(modelDO.getDefenseRangeKey());
        model.setExternalId(modelDO.getExternalId());
        model.setTenant(modelDO.getTenant());
        model.setDelaySecond(modelDO.getDelaySecond());
        model.setPluginKey(modelDO.getPluginKey());
        model.setMainClass(modelDO.getMainClass());

        model.setPluginInvokeType(modelDO.getPluginInvokeType());
        if (StringUtils.isNotBlank(modelDO.getIgnoreTag())) {
            model.setIgnoreTag(Enum.valueOf(DefenderIgnoreTypeEnum.class, modelDO.getIgnoreTag()));
        } else {
            model.setIgnoreTag(DefenderIgnoreTypeEnum.IGNORE);
        }

        model.setMaxDetectSecond(modelDO.getMaxDetectSecond() == null ? 0 : model.getMaxDetectSecond());
        model.getArgRef().write(modelDO.getArgRef());

        try {
            model.getChangeFilter().write(changeFilterConvert(modelDO.getChangeFilterRef()));
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "Deserialization of defense rule`s change filter conditions failed", e);
        }
        return model;
    }

    private ChangeFilter changeFilterConvert(String changeFilterRef) {
        if (StringUtils.isBlank(changeFilterRef)) {
            return null;
        }
        String filter = new KvRef<>(singleKeyValueStorage, KvRefCodec.NOOP, changeFilterRef).readObject();
        return JSONUtil.parseJSONToObj(filter, ChangeFilter.class);
    }

    /**
     * Convert entity object to DO object
     * <p>
     *
     * @param model Entity Object Model
     * @return DO object
     */
    public MetaDefenderRuleDO convert2DO(MetaDefenderRuleEntity model) {
        if (model == null) {
            return null;
        }

        MetaDefenderRuleDO doModel = new MetaDefenderRuleDO();
        doModel.setId(model.getId());
        doModel.setGmtCreate(model.getGmtCreate());
        doModel.setGmtModified(model.getGmtModified());
        doModel.setName(model.getName());
        doModel.setSuggestion(model.getSuggestion());
        doModel.setOwner(model.getOwner());
        if (model.getStatus() != null) {
            doModel.setStatus(model.getStatus().getStatus());
        }
        if (model.getExceptionStrategy() != null) {
            doModel.setExceptionStrategy(model.getExceptionStrategy().getStrategy());
        }
        if (model.getDefenseRangeType() != null) {
            doModel.setDefenseRangeType(model.getDefenseRangeType().getType());
        }
        if (model.getStage() != null) {
            doModel.setStage(model.getStage().getStage());
        }
        doModel.setDefenseRangeKey(model.getDefenseRangeKey());
        doModel.setExternalId(model.getExternalId());
        doModel.setTenant(model.getTenant());
        doModel.setDelaySecond(model.getDelaySecond());
        doModel.setPluginKey(model.getPluginKey());
        doModel.setMainClass(model.getMainClass());

        if (model.getIgnoreTag() != null) {
            doModel.setIgnoreTag(model.getIgnoreTag().getType());
        }
        doModel.setPluginInvokeType(model.getPluginInvokeType());

        doModel.setMaxDetectSecond(model.getMaxDetectSecond());
        doModel.setArgRef(model.getArgRef().flush(model.getId()));

        doModel.setChangeFilterRef(model.getChangeFilter().flush(model.getId()));
        return doModel;
    }

    /**
     * Entity DO objects converted to entity objects
     * <p>
     *
     * @param modelDOList DO object
     * @return entity object
     */
    public List<MetaDefenderRuleEntity> convert2ModelList(List<MetaDefenderRuleDO> modelDOList) {
        if (modelDOList == null) {
            return Collections.emptyList();
        }

        List<MetaDefenderRuleEntity> modelList = new ArrayList<>();

        for (MetaDefenderRuleDO doModel : modelDOList) {
            modelList.add(convert2Model(doModel));
        }

        return modelList;
    }
}