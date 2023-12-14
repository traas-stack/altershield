/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.schedule.event.defender;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;

/**
 * A batch of defense detection task status check events
 *
 * @author yhaoxuan
 * @version DefenderCheckProcessEvent.java, v 0.1 2022年08月30日 3:03 下午 yhaoxuan
 */
@SchedulerEvent("DefenderCheckProcessEvent")
public class DefenderCheckProcessEvent extends AlterShieldSchedulerEvent {

    /**
     * Change order id
     */
    private String changeOrderId;

    /**
     * Change node id
     */
    private String nodeId;

    /**
     * Detection group id
     */
    private String detectGroupId;

    /**
     * pre / post
     */
    private DefenseStageEnum stage;

    /**
     * Change scene identifier
     */
    private String changeSceneKey;

    /**
     * Change step type enum
     */
    private MetaChangeStepTypeEnum changeStepType;

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
     * Getter method for property <tt>changeStepType</tt>.
     *
     * @return property value of changeStepType
     */
    public MetaChangeStepTypeEnum getChangeStepType() {
        return changeStepType;
    }

    /**
     * Setter method for property <tt>changeStepType</tt>.
     *
     * @param changeStepType value to be assigned to property changeStepType
     */
    public void setChangeStepType(MetaChangeStepTypeEnum changeStepType) {
        this.changeStepType = changeStepType;
    }
}