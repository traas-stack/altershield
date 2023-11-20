/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.meta.entity;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.defender.framework.enums.DefenseRangeTypeEnum;
import com.alipay.altershield.defender.framework.enums.ExceptionStrategyEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRef;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import com.alipay.altershield.shared.defender.entity.DefenderBaseEntity;
import com.alipay.altershield.shared.defender.enums.DefenderIgnoreTypeEnum;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import com.alipay.altershield.shared.defender.model.ChangeFilter;

import java.util.Objects;

/**
 * defender rule entity
 *
 * @author yhaoxuan
 * @version MetaDefenderRuleEntity.java, v 0.1 2022年08月25日 9:35 下午 yhaoxuan
 */
public class MetaDefenderRuleEntity extends DefenderBaseEntity {

    /**
     * name
     */
    private String name;

    /**
     * handing suggestion when defender rule finish a detection
     */
    private String suggestion;

    /**
     * owners of the defender rule, use commas to separate multiple
     */
    private String owner;

    /**
     * status of the rule
     */
    private DefenderRuleStatusEnum status;

    /**
     * handing strategy when rule got an exception
     */
    private ExceptionStrategyEnum exceptionStrategy;

    /**
     * defense range of the rule
     */
    private DefenseRangeTypeEnum defenseRangeType;

    /**
     * the key of the defense range
     */
    private String defenseRangeKey;

    /**
     * related outer id
     */
    private String externalId;

    /**
     * defense stage
     */
    private DefenseStageEnum stage;

    /**
     * delay second to execute
     */
    private int delaySecond;

    /**
     * max detect second
     */
    private int maxDetectSecond;

    /**
     * tenant
     */
    private String tenant;

    /**
     * related plugin key
     */
    private String pluginKey;

    /**
     * related main class of the plugin
     */
    private String mainClass;

    /**
     * invoke type of the plugin
     *
     * SYNC / ASYNC
     */
    private String pluginInvokeType;

    /**
     * ignore type when change order blocked by defender rule
     */
    private DefenderIgnoreTypeEnum ignoreTag;

    /**
     * execution arguments
     */
    private KvRef<String> argRef;

    /**
     * filter change orders
     */
    private KvRef<ChangeFilter> changeFilter;

    public MetaDefenderRuleEntity(KeyValueStorage keyValueStorage) {
        this.setArgRef(new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null));
        this.setChangeFilter(new KvRef<>(keyValueStorage, KvRefCodec.CHANGE_FILTER, null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        MetaDefenderRuleEntity that = (MetaDefenderRuleEntity) o;
        return delaySecond == that.delaySecond && maxDetectSecond == that.maxDetectSecond && Objects.equals(name, that.name)
                && Objects.equals(suggestion, that.suggestion) && Objects.equals(owner, that.owner) && status == that.status
                && exceptionStrategy == that.exceptionStrategy && defenseRangeType == that.defenseRangeType && Objects.equals(
                defenseRangeKey, that.defenseRangeKey) && Objects.equals(externalId, that.externalId) && stage == that.stage
                && Objects.equals(tenant, that.tenant) && Objects.equals(pluginKey, that.pluginKey)
                && Objects.equals(mainClass, that.mainClass) && Objects.equals(pluginInvokeType, that.pluginInvokeType)
                && ignoreTag == that.ignoreTag && Objects.equals(argRef, that.argRef) && Objects.equals(changeFilter,
                that.changeFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, suggestion, owner, status, exceptionStrategy, defenseRangeType, defenseRangeKey, externalId, stage,
                delaySecond, maxDetectSecond, tenant, pluginKey, mainClass, pluginInvokeType, ignoreTag, argRef, changeFilter);
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
     * Getter method for property <tt>owner</tt>.
     *
     * @return property value of owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Setter method for property <tt>owner</tt>.
     *
     * @param owner value to be assigned to property owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public DefenderRuleStatusEnum getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(DefenderRuleStatusEnum status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>exceptionStrategy</tt>.
     *
     * @return property value of exceptionStrategy
     */
    public ExceptionStrategyEnum getExceptionStrategy() {
        return exceptionStrategy;
    }

    /**
     * Setter method for property <tt>exceptionStrategy</tt>.
     *
     * @param exceptionStrategy value to be assigned to property exceptionStrategy
     */
    public void setExceptionStrategy(ExceptionStrategyEnum exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    /**
     * Getter method for property <tt>defenseRangeType</tt>.
     *
     * @return property value of defenseRangeType
     */
    public DefenseRangeTypeEnum getDefenseRangeType() {
        return defenseRangeType;
    }

    /**
     * Setter method for property <tt>defenseRangeType</tt>.
     *
     * @param defenseRangeType value to be assigned to property defenseRangeType
     */
    public void setDefenseRangeType(DefenseRangeTypeEnum defenseRangeType) {
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
    public DefenseStageEnum getStage() {
        return stage;
    }

    /**
     * Setter method for property <tt>stage</tt>.
     *
     * @param stage value to be assigned to property stage
     */
    public void setStage(DefenseStageEnum stage) {
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
    public KvRef<String> getArgRef() {
        return argRef;
    }

    /**
     * Setter method for property <tt>argRef</tt>.
     *
     * @param argRef value to be assigned to property argRef
     */
    public void setArgRef(KvRef<String> argRef) {
        this.argRef = argRef;
    }

    /**
     * Getter method for property <tt>changeFilter</tt>.
     *
     * @return property value of changeFilter
     */
    public KvRef<ChangeFilter> getChangeFilter() {
        return changeFilter;
    }

    /**
     * Setter method for property <tt>changeFilter</tt>.
     *
     * @param changeFilter value to be assigned to property changeFilter
     */
    public void setChangeFilter(
            KvRef<ChangeFilter> changeFilter) {
        this.changeFilter = changeFilter;
    }

    @Override
    public String toString() {
        return "MetaDefenderRuleEntity{" +
                "name='" + name + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", owner='" + owner + '\'' +
                ", status=" + status +
                ", exceptionStrategy=" + exceptionStrategy +
                ", defenseRangeType=" + defenseRangeType +
                ", defenseRangeKey='" + defenseRangeKey + '\'' +
                ", externalId='" + externalId + '\'' +
                ", stage=" + stage +
                ", delaySecond=" + delaySecond +
                ", maxDetectSecond=" + maxDetectSecond +
                ", tenant='" + tenant + '\'' +
                ", pluginKey='" + pluginKey + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", pluginInvokeType='" + pluginInvokeType + '\'' +
                ", ignoreTag=" + ignoreTag +
                ", argRef=" + argRef +
                ", changeFilter=" + changeFilter +
                '}';
    }
}