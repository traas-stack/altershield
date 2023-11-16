/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.spi.defender.model.request.ChangeBaseInfo;
import com.alipay.altershield.spi.defender.model.request.ChangeExecuteInfo;
import com.alipay.altershield.spi.defender.model.request.ChangeInfluenceInfo;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Defender task execution request structure
 *
 * @author yhaoxuan
 * @version DefenderTaskExecuteRequest.java, v 0.1 2022年08月28日 7:23 下午 yhaoxuan
 */
public class DefenderTaskExecuteRequest {

    /**
     * Change scenario identification (change work order dimension, used to identify a type of change scenario)
     */
    @NotNull
    private String changeSceneKey;

    /**
     * Change node identification (change batch dimension, used to identify a type of change action)
     */
    @NotNull
    private String changeKey;

    /**
     * Change order id
     */
    @NotNull
    private String changeOrderId;

    /**
     * Change node id
     */
    private String nodeId;

    /**
     * Detection group id
     */
    @NotNull
    private String detectGroupId;

    /**
     * Detection execution id
     */
    @NotNull
    private String detectExeId;

    /**
     * Defender rule id
     */
    @NotNull
    private String ruleId;

    /**
     * Defender plugin identification
     */
    @NotNull
    private String pluginKey;

    /**
     * Main class of the defender plugin
     */
    @NotNull
    private String mainClass;

    /**
     * Downstream rule id
     */
    private String externalId;

    /**
     * Change defense verification phase: pre/post
     */
    private DefenseStageEnum defenseStage = DefenseStageEnum.PRE;

    /**
     * Change basic information
     */
    @NotNull
    private ChangeBaseInfo changeBaseInfo;

    /**
     * Change execution information
     */
    @NotNull
    private ChangeExecuteInfo changeExecuteInfo;

    /**
     * Change influence information
     */
    @NotNull
    private ChangeInfluenceInfo changeInfluenceInfo;

    /**
     * Input parameter field, currently only used as storage json for pluginKey, mainClass, controlKey, and externalRuleId
     */
    private String paramJson;

    /**
     * Get target value from parameter json by key
     *
     * @param key the key
     * @param clz target class to convert
     * @param <T> class type
     * @return target value
     */
    public <T> T getParamByKey(String key, Class<T> clz) {
        JSONObject paramJson = JSON.parseObject(this.paramJson);
        if (!Objects.isNull(paramJson) && !paramJson.isEmpty()) {
            return paramJson.getObject(key, clz);
        }

        return null;
    }

    /**
     * Get target value from parameter json by key
     *
     * @param key the key
     * @return target string value
     */
    public String getParamStringByKey(String key) {
        JSONObject paramJson = JSON.parseObject(this.paramJson);
        if (!Objects.isNull(paramJson) && !paramJson.isEmpty()) {
            return paramJson.getObject(key, String.class);
        }

        return "";
    }

    /**
     * Getter method for property <tt>paramJson</tt>.
     *
     * @return property value of paramJson
     */
    public String getParamJson() {
        return paramJson;
    }

    /**
     * Setter method for property <tt>paramJson</tt>.
     *
     * @param paramJson value to be assigned to property paramJson
     */
    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    /**
     * Getter method for property <tt>changeSceneKey</tt>.
     *
     * @return property value of changeSceneKey
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     * Setter method for property <tt>changeSceneKey</tt>.
     *
     * @param changeSceneKey value to be assigned to property changeSceneKey
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     * Getter method for property <tt>changeKey</tt>.
     *
     * @return property value of changeKey
     */
    public String getChangeKey() {
        return changeKey;
    }

    /**
     * Setter method for property <tt>changeKey</tt>.
     *
     * @param changeKey value to be assigned to property changeKey
     */
    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
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
     * Getter method for property <tt>changeOrderId</tt>.
     *
     * @return property value of changeOrderId
     */
    public String getChangeOrderId() {
        return changeOrderId;
    }

    /**
     * Setter method for property <tt>changeOrderId</tt>.
     *
     * @param changeOrderId value to be assigned to property changeOrderId
     */
    public void setChangeOrderId(String changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    /**
     * Getter method for property <tt>nodeId</tt>.
     *
     * @return property value of nodeId
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Setter method for property <tt>nodeId</tt>.
     *
     * @param nodeId value to be assigned to property nodeId
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Getter method for property <tt>detectGroupId</tt>.
     *
     * @return property value of detectGroupId
     */
    public String getDetectGroupId() {
        return detectGroupId;
    }

    /**
     * Setter method for property <tt>detectGroupId</tt>.
     *
     * @param detectGroupId value to be assigned to property detectGroupId
     */
    public void setDetectGroupId(String detectGroupId) {
        this.detectGroupId = detectGroupId;
    }

    /**
     * Getter method for property <tt>detectExeId</tt>.
     *
     * @return property value of detectExeId
     */
    public String getDetectExeId() {
        return detectExeId;
    }

    /**
     * Setter method for property <tt>detectExeId</tt>.
     *
     * @param detectExeId value to be assigned to property detectExeId
     */
    public void setDetectExeId(String detectExeId) {
        this.detectExeId = detectExeId;
    }

    /**
     * Getter method for property <tt>ruleId</tt>.
     *
     * @return property value of ruleId
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * Setter method for property <tt>ruleId</tt>.
     *
     * @param ruleId value to be assigned to property ruleId
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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
     * Getter method for property <tt>defenseStage</tt>.
     *
     * @return property value of defenseStage
     */
    public DefenseStageEnum getDefenseStage() {
        return defenseStage;
    }

    /**
     * Setter method for property <tt>defenseStage</tt>.
     *
     * @param defenseStage value to be assigned to property defenseStage
     */
    public void setDefenseStage(DefenseStageEnum defenseStage) {
        this.defenseStage = defenseStage;
    }

    /**
     * Getter method for property <tt>changeBaseInfo</tt>.
     *
     * @return property value of changeBaseInfo
     */
    public ChangeBaseInfo getChangeBaseInfo() {
        return changeBaseInfo;
    }

    /**
     * Setter method for property <tt>changeBaseInfo</tt>.
     *
     * @param changeBaseInfo value to be assigned to property changeBaseInfo
     */
    public void setChangeBaseInfo(ChangeBaseInfo changeBaseInfo) {
        this.changeBaseInfo = changeBaseInfo;
    }

    /**
     * Getter method for property <tt>changeExecuteInfo</tt>.
     *
     * @return property value of changeExecuteInfo
     */
    public ChangeExecuteInfo getChangeExecuteInfo() {
        return changeExecuteInfo;
    }

    /**
     * Setter method for property <tt>changeExecuteInfo</tt>.
     *
     * @param changeExecuteInfo value to be assigned to property changeExecuteInfo
     */
    public void setChangeExecuteInfo(ChangeExecuteInfo changeExecuteInfo) {
        this.changeExecuteInfo = changeExecuteInfo;
    }

    /**
     * Getter method for property <tt>changeInfluenceInfo</tt>.
     *
     * @return property value of changeInfluenceInfo
     */
    public ChangeInfluenceInfo getChangeInfluenceInfo() {
        return changeInfluenceInfo;
    }

    /**
     * Setter method for property <tt>changeInfluenceInfo</tt>.
     *
     * @param changeInfluenceInfo value to be assigned to property changeInfluenceInfo
     */
    public void setChangeInfluenceInfo(ChangeInfluenceInfo changeInfluenceInfo) {
        this.changeInfluenceInfo = changeInfluenceInfo;
    }

    @Override
    public String toString() {
        return "DefenderTaskExecuteRequest{" +
                "changeSceneKey='" + changeSceneKey + '\'' +
                ", changeKey='" + changeKey + '\'' +
                ", changeOrderId='" + changeOrderId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", detectGroupId='" + detectGroupId + '\'' +
                ", detectExeId='" + detectExeId + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", pluginKey='" + pluginKey + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", externalId='" + externalId + '\'' +
                ", defenseStage=" + defenseStage +
                ", changeBaseInfo=" + changeBaseInfo +
                ", changeExecuteInfo=" + changeExecuteInfo +
                ", changeInfluenceInfo=" + changeInfluenceInfo +
                ", paramJson='" + paramJson + '\'' +
                '}';
    }
}