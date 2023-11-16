/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.result;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;

/**
 * Asynchronous defender detect task submission results
 *
 * @author yhaoxuan
 * @version DefenderDetectSumbitResult.java, v 0.1 2022年09月15日 7:58 下午 yhaoxuan
 */
public class DefenderDetectSubmitResult {

    /**
     * Whether defense logic executed this time
     */
    private boolean defensed = true;

    /**
     * Reason for not defending
     */
    private String msg;

    /**
     * Change order id
     */
    private String changeOrderId;

    /**
     * Change node id
     */
    private String nodeId;

    /**
     * Detect group id
     */
    private String detectGroupId;

    /**
     * Defense stage
     */
    private DefenseStageEnum stage;

    /**
     * Getter method for property <tt>defensed</tt>.
     *
     * @return property value of defensed
     */
    public boolean isDefensed() {
        return defensed;
    }

    /**
     * Setter method for property <tt>defensed</tt>.
     *
     * @param defensed value to be assigned to property defensed
     */
    public void setDefensed(boolean defensed) {
        this.defensed = defensed;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     *
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
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

    @Override
    public String toString() {
        return "DefenderDetectSubmitResult{" +
                "defensed=" + defensed +
                ", msg='" + msg + '\'' +
                ", changeOrderId='" + changeOrderId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", detectGroupId='" + detectGroupId + '\'' +
                ", stage=" + stage +
                '}';
    }
}