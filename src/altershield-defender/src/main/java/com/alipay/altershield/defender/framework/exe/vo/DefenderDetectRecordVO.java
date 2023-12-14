/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.vo;


import java.util.Date;

/**
 * 防御规则列表页查看执行记录视图
 *
 * @author haoxuan
 * @version DefenderDetectVO.java, v 0.1 2022年11月29日 7:27 下午 haoxuan
 */
public class DefenderDetectRecordVO {

    /**
     * 防御规则执行记录id
     */
    private String detectExeId;

    /**
     * 防御规则id
     */
    private String ruleId;

    /**
     * 防御规则名称
     */
    private String ruleName;

    /**
     * 下游防御规则id
     */
    private String externalId;

    /**
     * 防御校验状态
     */
    private String status;

    /**
     * 防御校验简略信息
     */
    private String msg;

    /**
     * 防御校验结果详情
     */
    private String detectResult;

    /**
     * 防御规则状态
     * @see com.alipay.opscloud.api.defender.enums.DefenderRuleStatusEnum
     */
    private String ruleStatus;

    /**
     * 计划执行时间
     */
    private Date gmtPlan;

    /**
     * 执行开始时间
     */
    private Date gmtStart;

    /**
     * 执行完成时间
     */
    private Date gmtFinish;

    /**
     * 反馈状态
     */
    private String feedbackStatus;

    /**
     * 反馈原因
     */
    private String feedbackReason;

    /**
     * 最后反馈人
     */
    private String feedbackOperator;

    /**
     * 关联的变更工单id
     */
    private String changeOrderId;

    /**
     * 关联的变更节点id
     */
    private String nodeId;

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
     * Getter method for property <tt>ruleName</tt>.
     *
     * @return property value of ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Setter method for property <tt>ruleName</tt>.
     *
     * @param ruleName value to be assigned to property ruleName
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * Getter method for property <tt>detectResult</tt>.
     *
     * @return property value of detectResult
     */
    public String getDetectResult() {
        return detectResult;
    }

    /**
     * Setter method for property <tt>detectResult</tt>.
     *
     * @param detectResult value to be assigned to property detectResult
     */
    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    /**
     * Getter method for property <tt>ruleStatus</tt>.
     *
     * @return property value of ruleStatus
     */
    public String getRuleStatus() {
        return ruleStatus;
    }

    /**
     * Setter method for property <tt>ruleStatus</tt>.
     *
     * @param ruleStatus value to be assigned to property ruleStatus
     */
    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    /**
     * Getter method for property <tt>gmtPlan</tt>.
     *
     * @return property value of gmtPlan
     */
    public Date getGmtPlan() {
        return gmtPlan;
    }

    /**
     * Setter method for property <tt>gmtPlan</tt>.
     *
     * @param gmtPlan value to be assigned to property gmtPlan
     */
    public void setGmtPlan(Date gmtPlan) {
        this.gmtPlan = gmtPlan;
    }

    /**
     * Getter method for property <tt>gmtStart</tt>.
     *
     * @return property value of gmtStart
     */
    public Date getGmtStart() {
        return gmtStart;
    }

    /**
     * Setter method for property <tt>gmtStart</tt>.
     *
     * @param gmtStart value to be assigned to property gmtStart
     */
    public void setGmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
    }

    /**
     * Getter method for property <tt>gmtFinish</tt>.
     *
     * @return property value of gmtFinish
     */
    public Date getGmtFinish() {
        return gmtFinish;
    }

    /**
     * Setter method for property <tt>gmtFinish</tt>.
     *
     * @param gmtFinish value to be assigned to property gmtFinish
     */
    public void setGmtFinish(Date gmtFinish) {
        this.gmtFinish = gmtFinish;
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

    /**
     * Getter method for property <tt>feedbackReason</tt>.
     *
     * @return property value of feedbackReason
     */
    public String getFeedbackReason() {
        return feedbackReason;
    }

    /**
     * Setter method for property <tt>feedbackReason</tt>.
     *
     * @param feedbackReason value to be assigned to property feedbackReason
     */
    public void setFeedbackReason(String feedbackReason) {
        this.feedbackReason = feedbackReason;
    }

    /**
     * Getter method for property <tt>feedbackOperator</tt>.
     *
     * @return property value of feedbackOperator
     */
    public String getFeedbackOperator() {
        return feedbackOperator;
    }

    /**
     * Setter method for property <tt>feedbackOperator</tt>.
     *
     * @param feedbackOperator value to be assigned to property feedbackOperator
     */
    public void setFeedbackOperator(String feedbackOperator) {
        this.feedbackOperator = feedbackOperator;
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
}