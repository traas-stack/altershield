/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender.model.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Detection plugin request structure
 *
 * @author yhaoxuan
 * @version DefenderDetectPluginRequest.java, v 0.1 2022年08月29日 4:19 下午 yhaoxuan
 */
public class DefenderDetectPluginRequest {

    /**
     * Related change order id
     */
    @NotNull
    private String changeOrderId;

    /**
     * Change node id (node id of the current change batch)
     */
    @NotNull
    private String nodeId;

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
     * The unique ID of this detection, corresponding to one of the pre/post verificationsZ
     */
    private String detectId;

    /**
     * The group id of this detection corresponds to a pre/post position
     */
    @NotNull
    private String detectGroupId;

    /**
     * Change defense detection phase: pre/post
     */
    private DefenseStageEnum defenseStage = DefenseStageEnum.PRE;

    /**
     * Change basic information
     */
    @NotNull
    private ChangeBaseInfo changeBaseInfo;

    /**
     * Change execution phase information
     */
    @NotNull
    private ChangeExecuteInfo changeExecuteInfo;

    /**
     * Change influence information
     */
    @NotNull
    private ChangeInfluenceInfo changeInfluenceInfo;

    /**
     * The input parameter field. (Currently only used as storage json for pluginKey, mainClass, controlKey, and ruleId.)
     */
    private String paramJson;


    public <T> T getParamByKey(String key, Class<T> clz) {
        JSONObject paramJson = JSON.parseObject(this.paramJson);
        if (!Objects.isNull(paramJson) && !paramJson.isEmpty()) {
            return paramJson.getObject(key, clz);
        }

        return null;
    }

    public String getParamStringByKey(String key) {
        JSONObject paramJson = JSON.parseObject(this.paramJson);
        if (!Objects.isNull(paramJson) && !paramJson.isEmpty()) {
            return paramJson.getObject(key, String.class);
        }

        return "";
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
     * Getter method for property <tt>detectId</tt>.
     *
     * @return property value of detectId
     */
    public String getDetectId() {
        return detectId;
    }

    /**
     * Setter method for property <tt>detectId</tt>.
     *
     * @param detectId value to be assigned to property detectId
     */
    public void setDetectId(String detectId) {
        this.detectId = detectId;
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

    @Override
    public String toString() {
        return "DefenderDetectPluginRequest{" +
                "changeOrderId='" + changeOrderId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", changeSceneKey='" + changeSceneKey + '\'' +
                ", changeKey='" + changeKey + '\'' +
                ", detectGroupId='" + detectGroupId + '\'' +
                ", defenseStage=" + defenseStage +
                ", changeBaseInfo=" + changeBaseInfo +
                ", changeExecuteInfo=" + changeExecuteInfo +
                ", changeInfluenceInfo=" + changeInfluenceInfo +
                ", paramJson='" + paramJson + '\'' +
                '}';
    }
}