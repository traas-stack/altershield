/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.request;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.spi.defender.model.request.ChangeBaseInfo;
import com.alipay.altershield.spi.defender.model.request.ChangeExecuteInfo;
import com.alipay.altershield.spi.defender.model.request.ChangeInfluenceInfo;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Defender detection request structure
 *
 * @author yhaoxuan
 * @version DefenderDetectRequest.java, v 0.1 2022年08月08日 4:16 下午 yhaoxuan
 */
public class DefenderDetectRequest {

    /**
     * Change order id
     */
    @NotNull
    private String changeOrderId;

    /**
     * Change scenario identification (change work order dimension, used to identify a type of change scenario)
     */
    @NotNull
    private String changeSceneKey;

    /**
     * Change step tyoe
     */
    private MetaChangeStepTypeEnum stepTypeEnum;

    /**
     * Change node id (node id of the current change batch)
     */
    @NotNull
    private String nodeId;

    /**
     * Change node identification (change batch dimension, used to identify a type of change action)
     */
    @NotNull
    private String changeKey;

    /**
     * Is it an emergency change
     */
    private boolean emergency;

    /**
     * Defense stage
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
     * List of change tag ids related with this change
     */
    @NotNull
    private Set<String> changeTagIds;

    /**
     * Getter method for property <tt>stepTypeEnum</tt>.
     *
     * @return property value of stepTypeEnum
     */
    public MetaChangeStepTypeEnum getStepTypeEnum() {
        return stepTypeEnum;
    }

    /**
     * Setter method for property <tt>stepTypeEnum</tt>.
     *
     * @param stepTypeEnum value to be assigned to property stepTypeEnum
     */
    public void setStepTypeEnum(MetaChangeStepTypeEnum stepTypeEnum) {
        this.stepTypeEnum = stepTypeEnum;
    }

    /**
     * 校验超时时间
     */
    private Long timeout;

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
     * Getter method for property <tt>emergency</tt>.
     *
     * @return property value of emergency
     */
    public boolean isEmergency() {
        return emergency;
    }

    /**
     * Setter method for property <tt>emergency</tt>.
     *
     * @param emergency value to be assigned to property emergency
     */
    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
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
     * Getter method for property <tt>changeTagIds</tt>.
     *
     * @return property value of changeTagIds
     */
    public Set<String> getChangeTagIds() {
        return changeTagIds;
    }

    /**
     * Setter method for property <tt>changeTagIds</tt>.
     *
     * @param changeTagIds value to be assigned to property changeTagIds
     */
    public void setChangeTagIds(Set<String> changeTagIds) {
        this.changeTagIds = changeTagIds;
    }

    /**
     * Getter method for property <tt>timeout</tt>.
     *
     * @return property value of timeout
     */
    public Long getTimeout() {
        return timeout;
    }

    /**
     * Setter method for property <tt>timeout</tt>.
     *
     * @param timeout value to be assigned to property timeout
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}