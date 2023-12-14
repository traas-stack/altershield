/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.request;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Defense rule execution batch feedback request structure
 *
 * @author yhaoxuan
 * @version DefenderDetectBatchFeedbackRequest.java, v 0.1 2022年11月29日 8:39 下午 yhaoxuan
 */
public class DefenderDetectBatchFeedbackRequest {

    /**
     * operator
     */
    private String operator;

    /**
     * List of detection record IDs that require feedback
     */
    private List<String> detectExeIds;

    /**
     * feedback status
     *
     * @see com.alipay.altershield.framework.core.risk.model.enums.FeedbackStatusEnum
     */
    private String feedbackStatus;

    /**
     * feedback reason
     */
    private JSONObject feedbackReason;

    /**
     * Getter method for property <tt>feedbackReason</tt>.
     *
     * @return property value of feedbackReason
     */
    public JSONObject getFeedbackReason() {
        return feedbackReason;
    }

    /**
     * Setter method for property <tt>feedbackReason</tt>.
     *
     * @param feedbackReason value to be assigned to property feedbackReason
     */
    public void setFeedbackReason(JSONObject feedbackReason) {
        this.feedbackReason = feedbackReason;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     *
     * @return property value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     *
     * @param operator value to be assigned to property operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>detectExeIds</tt>.
     *
     * @return property value of detectExeIds
     */
    public List<String> getDetectExeIds() {
        return detectExeIds;
    }

    /**
     * Setter method for property <tt>detectExeIds</tt>.
     *
     * @param detectExeIds value to be assigned to property detectExeIds
     */
    public void setDetectExeIds(List<String> detectExeIds) {
        this.detectExeIds = detectExeIds;
    }

    /**
     * Getter method for property <tt>feedbackStatus</tt>.
     *
     * @return property value of feedbackStatus
     */
    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    /**
     * Setter method for property <tt>feedbackStatus</tt>.
     *
     * @param feedbackStatus value to be assigned to property feedbackStatus
     */
    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

}