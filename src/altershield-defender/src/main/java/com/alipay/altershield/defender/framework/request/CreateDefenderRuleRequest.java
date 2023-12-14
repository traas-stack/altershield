/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.defender.framework.enums.DefenseRangeTypeEnum;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.enums.DefenderIgnoreTypeEnum;
import com.alipay.altershield.shared.defender.model.ChangeFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Create a defense rule request structure
 *
 * @author yhaoxuan
 * @version CreateDefenderRuleRequest.java, v 0.1 2022年08月30日 8:03 下午 yhaoxuan
 */
@ApiModel(value = "Create a defense rule request structure")
public class CreateDefenderRuleRequest {

    @Autowired
    private KeyValueStorage singleKeyValueStorage;

    /**
     *   rule name
     */
    @ApiModelProperty(value = "防御规则名称")
    @NotNull
    private String name;

    /**
     *   Handling suggestion
     */
    @ApiModelProperty(value = "Handling suggestion")
    @NotNull
    private String suggestion;

    /**
     *   rule owner
     */
    @ApiModelProperty(value = "rule owner")
    @NotNull
    private List<String> owners;

    /**
     *   plugin key
     */
    @ApiModelProperty(value = "plugin key")
    @NotNull
    private String pluginKey;

    /**
     *   main class of the plugin
     */
    @ApiModelProperty(value = "main class of the plugin")
    @NotNull
    private String mainClass;

    /**
     *   Handling strategy when detecting got an exception
     *
     * @see ExceptionStrategyEnum
     */
    @ApiModelProperty(value = "Handling strategy when detecting got an exception")
    @NotNull
    private String exceptionStrategy;

    /**
     *   Defense range type
     *
     * @see DefenseRangeTypeEnum
     */
    @ApiModelProperty(value = "Defense range type")
    @NotNull
    private String defenseRangeType;

    /**
     *   Defense range key
     */
    @ApiModelProperty(value = "Defense range key")
    @NotNull
    private String defenseRangeKey;

    /**
     *   down streams rule id
     */
    @ApiModelProperty(value = "down streams rule id")
    private String externalId;

    /**
     *   pre or post
     *
     * @see DefenseStageEnum
     */
    @ApiModelProperty(value = "pre or post")
    @NotNull
    private String stage;

    /**
     *   delay second
     */
    @ApiModelProperty(value = "delay second")
    private int delaySecond;

    /**
     *   max detect second
     */
    @ApiModelProperty(value = "max detect second")
    private int maxDetectSecond;

    /**
     *   tenant
     */
    @ApiModelProperty(value = "tenant")
    private String tenant;

    /**
     *   SYNC, ASYNC_POLLING，ASYNC_CALLBACK
     */
    @ApiModelProperty(value = "SYNC, ASYNC_POLLING，ASYNC_CALLBACK")
    private String pluginInvokeType;

    /**
     *   ignore type
     */
    @ApiModelProperty(value = "ignore type")
    private DefenderIgnoreTypeEnum ignoreTag;

    /**
     *   rule arguments
     */
    @ApiModelProperty(value = "rule arguments")
    private String argRef;

    /**
     *   change filter
     */
    @ApiModelProperty(value = "change filter")
    private ChangeFilter changeFilter;

    /**
     * 转换为防御规则实体
     *
     * @return 防御规则实体
     */
    public MetaDefenderRuleEntity convert2Entity() {
        MetaDefenderRuleEntity rule = new MetaDefenderRuleEntity(singleKeyValueStorage);
        rule.setName(this.getName());
        rule.setSuggestion(this.getSuggestion());
        rule.setOwner(StringUtils.join(this.getOwners(), ","));
        rule.setPluginKey(this.getPluginKey());
        rule.setMainClass(this.getMainClass());
        if (StringUtils.isNotBlank(this.getExceptionStrategy())) {
            rule.setExceptionStrategy(ExceptionStrategyEnum.getByStrategy(this.getExceptionStrategy()));
        }
        if (StringUtils.isNotBlank(this.getDefenseRangeType())) {
            rule.setDefenseRangeType(DefenseRangeTypeEnum.getByType(this.getDefenseRangeType()));
        }
        if (StringUtils.isNotBlank(this.getStage())) {
            rule.setStage(DefenseStageEnum.getByStage(this.getStage()));
        }
        rule.setDefenseRangeKey(this.getDefenseRangeKey());
        rule.setExternalId(this.getExternalId());
        rule.setTenant(this.getTenant());
        rule.setDelaySecond(this.getDelaySecond());
        rule.setMaxDetectSecond(this.getMaxDetectSecond());
        rule.setIgnoreTag(this.getIgnoreTag() == null ? DefenderIgnoreTypeEnum.IGNORE : this.getIgnoreTag());
        rule.setPluginInvokeType(this.getPluginInvokeType());
        rule.getArgRef().write(this.getArgRef());
        rule.getChangeFilter().write(this.getChangeFilter());
        return rule;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>suggestion</tt>.
     *
     * @return property value of suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * Setter method for property <tt>suggestion</tt>.
     *
     * @param suggestion value to be assigned to property suggestion
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * Getter method for property <tt>owners</tt>.
     *
     * @return property value of owners
     */
    public List<String> getOwners() {
        return owners;
    }

    /**
     * Setter method for property <tt>owners</tt>.
     *
     * @param owners value to be assigned to property owners
     */
    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    /**
     * Getter method for property <tt>pluginKey</tt>.
     *
     * @return property value of pluginKey
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     * Setter method for property <tt>pluginKey</tt>.
     *
     * @param pluginKey value to be assigned to property pluginKey
     */
    public void setPluginKey(String pluginKey) {
        this.pluginKey = pluginKey;
    }

    /**
     * Getter method for property <tt>mainClass</tt>.
     *
     * @return property value of mainClass
     */
    public String getMainClass() {
        return mainClass;
    }

    /**
     * Setter method for property <tt>mainClass</tt>.
     *
     * @param mainClass value to be assigned to property mainClass
     */
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Getter method for property <tt>exceptionStrategy</tt>.
     *
     * @return property value of exceptionStrategy
     */
    public String getExceptionStrategy() {
        return exceptionStrategy;
    }

    /**
     * Setter method for property <tt>exceptionStrategy</tt>.
     *
     * @param exceptionStrategy value to be assigned to property exceptionStrategy
     */
    public void setExceptionStrategy(String exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    /**
     * Getter method for property <tt>defenseRangeType</tt>.
     *
     * @return property value of defenseRangeType
     */
    public String getDefenseRangeType() {
        return defenseRangeType;
    }

    /**
     * Setter method for property <tt>defenseRangeType</tt>.
     *
     * @param defenseRangeType value to be assigned to property defenseRangeType
     */
    public void setDefenseRangeType(String defenseRangeType) {
        this.defenseRangeType = defenseRangeType;
    }

    /**
     * Getter method for property <tt>defenseRangeKey</tt>.
     *
     * @return property value of defenseRangeKey
     */
    public String getDefenseRangeKey() {
        return defenseRangeKey;
    }

    /**
     * Setter method for property <tt>defenseRangeKey</tt>.
     *
     * @param defenseRangeKey value to be assigned to property defenseRangeKey
     */
    public void setDefenseRangeKey(String defenseRangeKey) {
        this.defenseRangeKey = defenseRangeKey;
    }

    /**
     * Getter method for property <tt>externalId</tt>.
     *
     * @return property value of externalId
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Setter method for property <tt>externalId</tt>.
     *
     * @param externalId value to be assigned to property externalId
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Getter method for property <tt>stage</tt>.
     *
     * @return property value of stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Setter method for property <tt>stage</tt>.
     *
     * @param stage value to be assigned to property stage
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     * Getter method for property <tt>delaySecond</tt>.
     *
     * @return property value of delaySecond
     */
    public int getDelaySecond() {
        return delaySecond;
    }

    /**
     * Setter method for property <tt>delaySecond</tt>.
     *
     * @param delaySecond value to be assigned to property delaySecond
     */
    public void setDelaySecond(int delaySecond) {
        this.delaySecond = delaySecond;
    }

    /**
     * Getter method for property <tt>tenant</tt>.
     *
     * @return property value of tenant
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Setter method for property <tt>tenant</tt>.
     *
     * @param tenant value to be assigned to property tenant
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * Getter method for property <tt>maxDetectSecond</tt>.
     *
     * @return property value of maxDetectSecond
     */
    public int getMaxDetectSecond() {
        return maxDetectSecond;
    }

    /**
     * Setter method for property <tt>maxDetectSecond</tt>.
     *
     * @param maxDetectSecond value to be assigned to property maxDetectSecond
     */
    public void setMaxDetectSecond(int maxDetectSecond) {
        this.maxDetectSecond = maxDetectSecond;
    }

    /**
     * Getter method for property <tt>pluginInvokeType</tt>.
     *
     * @return property value of pluginInvokeType
     */
    public String getPluginInvokeType() {
        return pluginInvokeType;
    }

    /**
     * Setter method for property <tt>pluginInvokeType</tt>.
     *
     * @param pluginInvokeType value to be assigned to property pluginInvokeType
     */
    public void setPluginInvokeType(String pluginInvokeType) {
        this.pluginInvokeType = pluginInvokeType;
    }

    /**
     * Getter method for property <tt>ignoreTag</tt>.
     *
     * @return property value of ignoreTag
     */
    public DefenderIgnoreTypeEnum getIgnoreTag() {
        return ignoreTag;
    }

    /**
     * Setter method for property <tt>ignoreTag</tt>.
     *
     * @param ignoreTag value to be assigned to property ignoreTag
     */
    public void setIgnoreTag(DefenderIgnoreTypeEnum ignoreTag) {
        this.ignoreTag = ignoreTag;
    }

    /**
     * Getter method for property <tt>argRef</tt>.
     *
     * @return property value of argRef
     */
    public String getArgRef() {
        return argRef;
    }

    /**
     * Setter method for property <tt>argRef</tt>.
     *
     * @param argRef value to be assigned to property argRef
     */
    public void setArgRef(String argRef) {
        this.argRef = argRef;
    }

    /**
     * Getter method for property <tt>changeFilter</tt>.
     *
     * @return property value of changeFilter
     */
    public ChangeFilter getChangeFilter() {
        return changeFilter;
    }

    /**
     * Setter method for property <tt>changeFilter</tt>.
     *
     * @param changeFilter value to be assigned to property changeFilter
     */
    public void setChangeFilter(ChangeFilter changeFilter) {
        this.changeFilter = changeFilter;
    }
}