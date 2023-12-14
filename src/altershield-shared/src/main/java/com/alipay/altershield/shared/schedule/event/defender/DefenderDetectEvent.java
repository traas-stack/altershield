/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.schedule.event.defender;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Single defense rule detection task event
 *
 * @author yhaoxuan
 * @version DefenderDetectEvent.java, v 0.1 2022年08月30日 2:49 下午 yhaoxuan
 */
@SchedulerEvent("DefenderDetectEvent")
public class DefenderDetectEvent extends AlterShieldSchedulerEvent {

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
     * Defense plugin key
     */
    @NotNull
    private String pluginKey;

    /**
     * Defense plugin entry class
     */
    @NotNull
    private String mainClass;

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
     * Defense rule id
     */
    @NotNull
    private String ruleId;

    /**
     * Downstream rule id
     */
    private String externalId;

    /**
     * pre or post
     */
    @NotNull
    private DefenseStageEnum stage;

    /**
     * The start time of this change node
     */
    @NotNull
    private Date changeStartTime;

    /**
     * The end time of this change node
     */
    private Date changeFinishTime;

    /**
     * Whether to detect change order dimensions
     */
    private boolean orderPhase;

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
     * Getter method for property <tt>changeStartTime</tt>.
     *
     * @return property value of changeStartTime
     */
    public Date getChangeStartTime() {
        return changeStartTime;
    }

    /**
     * Setter method for property <tt>changeStartTime</tt>.
     *
     * @param changeStartTime value to be assigned to property changeStartTime
     */
    public void setChangeStartTime(Date changeStartTime) {
        this.changeStartTime = changeStartTime;
    }

    /**
     * Getter method for property <tt>changeFinishTime</tt>.
     *
     * @return property value of changeFinishTime
     */
    public Date getChangeFinishTime() {
        return changeFinishTime;
    }

    /**
     * Setter method for property <tt>changeFinishTime</tt>.
     *
     * @param changeFinishTime value to be assigned to property changeFinishTime
     */
    public void setChangeFinishTime(Date changeFinishTime) {
        this.changeFinishTime = changeFinishTime;
    }

    /**
     * Getter method for property <tt>orderPhase</tt>.
     *
     * @return property value of orderPhase
     */
    public boolean isOrderPhase() {
        return orderPhase;
    }

    /**
     * Setter method for property <tt>orderPhase</tt>.
     *
     * @param orderPhase value to be assigned to property orderPhase
     */
    public void setOrderPhase(boolean orderPhase) {
        this.orderPhase = orderPhase;
    }
}