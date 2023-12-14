/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.defender.framework.exe.dal.dataobject;

import java.util.Date;

/**
 *
 * @author xiangyue
 */
public class ExeDefenderDetectDO {
    /**
     * Database Column Remarks:
     *   The primary key
     *
     *
     * @mbg.generated
     */
    private String detectExeId;

    /**
     * Database Column Remarks:
     *   Create time
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   Last modify time
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   Detection group id
     *
     *
     * @mbg.generated
     */
    private String detectGroupId;

    /**
     * Database Column Remarks:
     *   Related change order id
     *
     *
     * @mbg.generated
     */
    private String changeOrderId;

    /**
     * Database Column Remarks:
     *   Related change node id
     *
     *
     * @mbg.generated
     */
    private String nodeId;

    /**
     * Database Column Remarks:
     *   Related defense rule id
     *
     *
     * @mbg.generated
     */
    private String ruleId;

    /**
     * Database Column Remarks:
     *   Related defense rule name
     *
     *
     * @mbg.generated
     */
    private String ruleName;

    /**
     * Database Column Remarks:
     *   Related defense rule owners
     *
     *
     * @mbg.generated
     */
    private String ruleOwner;

    /**
     * Database Column Remarks:
     *   Defense rule plugin key
     *
     *
     * @mbg.generated
     */
    private String pluginKey;

    /**
     * Database Column Remarks:
     *   Defense plugin main class
     *
     *
     * @mbg.generated
     */
    private String mainClass;

    /**
     * Database Column Remarks:
     *   Downstream platform defense rule id
     *
     *
     * @mbg.generated
     */
    private String externalId;

    /**
     * Database Column Remarks:
     *   Pre or post
     *
     *
     * @mbg.generated
     */
    private String stage;

    /**
     * Database Column Remarks:
     *   Defense detection status
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   This detection whether to block the execution of change
     *
     *
     * @mbg.generated
     */
    private String blocked;

    /**
     * Database Column Remarks:
     *   Reasons for blocking change execution
     *
     *
     * @mbg.generated
     */
    private String blockMsg;

    /**
     * Database Column Remarks:
     *   Brief information about the detection results
     *
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * Database Column Remarks:
     *   Planned start time
     *
     *
     * @mbg.generated
     */
    private Date gmtPlan;

    /**
     * Database Column Remarks:
     *   Actual start time
     *
     *
     * @mbg.generated
     */
    private Date gmtStart;

    /**
     * Database Column Remarks:
     *   Actual completed time
     *
     *
     * @mbg.generated
     */
    private Date gmtFinish;

    /**
     * Database Column Remarks:
     *   Whether the defense detection result is ignored - Y yes; N no
     *
     *
     * @mbg.generated
     */
    private String ignored;

    /**
     * Database Column Remarks:
     *   User feedback status
     *
     *
     * @mbg.generated
     */
    private String feedbackStatus;

    /**
     * Database Column Remarks:
     *   User feedback details reasons
     *
     *
     * @mbg.generated
     */
    private String feedbackReason;

    /**
     * Database Column Remarks:
     *   Feedback person
     *
     *
     * @mbg.generated
     */
    private String feedbackOperator;

    /**
     * Database Column Remarks:
     *   Sub-table: 00~99
     *
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * Database Column Remarks:
     *   Defense rule status: trial run, online, etc.
     *
     *
     * @mbg.generated
     */
    private String ruleStatus;

    /**
     * Database Column Remarks:
     *   Whether the rule allows ignored
     *
     *
     * @mbg.generated
     */
    private String allowIgnore;

    /**
     * Database Column Remarks:
     *   Details of this detection result
     *
     *
     * @mbg.generated
     */
    private String resultRef;

    /**
     *
     * @return the value of altershield_exe_defender_detect.detect_exe_id
     *
     * @mbg.generated
     */
    public String getDetectExeId() {
        return detectExeId;
    }

    /**
     *
     * @param detectExeId the value for altershield_exe_defender_detect.detect_exe_id
     *
     * @mbg.generated
     */
    public void setDetectExeId(String detectExeId) {
        this.detectExeId = detectExeId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_exe_defender_detect.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_exe_defender_detect.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.detect_group_id
     *
     * @mbg.generated
     */
    public String getDetectGroupId() {
        return detectGroupId;
    }

    /**
     *
     * @param detectGroupId the value for altershield_exe_defender_detect.detect_group_id
     *
     * @mbg.generated
     */
    public void setDetectGroupId(String detectGroupId) {
        this.detectGroupId = detectGroupId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.change_order_id
     *
     * @mbg.generated
     */
    public String getChangeOrderId() {
        return changeOrderId;
    }

    /**
     *
     * @param changeOrderId the value for altershield_exe_defender_detect.change_order_id
     *
     * @mbg.generated
     */
    public void setChangeOrderId(String changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.node_id
     *
     * @mbg.generated
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     *
     * @param nodeId the value for altershield_exe_defender_detect.node_id
     *
     * @mbg.generated
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.rule_id
     *
     * @mbg.generated
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     *
     * @param ruleId the value for altershield_exe_defender_detect.rule_id
     *
     * @mbg.generated
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.rule_name
     *
     * @mbg.generated
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     *
     * @param ruleName the value for altershield_exe_defender_detect.rule_name
     *
     * @mbg.generated
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.rule_owner
     *
     * @mbg.generated
     */
    public String getRuleOwner() {
        return ruleOwner;
    }

    /**
     *
     * @param ruleOwner the value for altershield_exe_defender_detect.rule_owner
     *
     * @mbg.generated
     */
    public void setRuleOwner(String ruleOwner) {
        this.ruleOwner = ruleOwner;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.plugin_key
     *
     * @mbg.generated
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     *
     * @param pluginKey the value for altershield_exe_defender_detect.plugin_key
     *
     * @mbg.generated
     */
    public void setPluginKey(String pluginKey) {
        this.pluginKey = pluginKey;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.main_class
     *
     * @mbg.generated
     */
    public String getMainClass() {
        return mainClass;
    }

    /**
     *
     * @param mainClass the value for altershield_exe_defender_detect.main_class
     *
     * @mbg.generated
     */
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.external_id
     *
     * @mbg.generated
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     *
     * @param externalId the value for altershield_exe_defender_detect.external_id
     *
     * @mbg.generated
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.stage
     *
     * @mbg.generated
     */
    public String getStage() {
        return stage;
    }

    /**
     *
     * @param stage the value for altershield_exe_defender_detect.stage
     *
     * @mbg.generated
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_exe_defender_detect.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.blocked
     *
     * @mbg.generated
     */
    public String getBlocked() {
        return blocked;
    }

    /**
     *
     * @param blocked the value for altershield_exe_defender_detect.blocked
     *
     * @mbg.generated
     */
    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.block_msg
     *
     * @mbg.generated
     */
    public String getBlockMsg() {
        return blockMsg;
    }

    /**
     *
     * @param blockMsg the value for altershield_exe_defender_detect.block_msg
     *
     * @mbg.generated
     */
    public void setBlockMsg(String blockMsg) {
        this.blockMsg = blockMsg;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.msg
     *
     * @mbg.generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg the value for altershield_exe_defender_detect.msg
     *
     * @mbg.generated
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.gmt_plan
     *
     * @mbg.generated
     */
    public Date getGmtPlan() {
        return gmtPlan;
    }

    /**
     *
     * @param gmtPlan the value for altershield_exe_defender_detect.gmt_plan
     *
     * @mbg.generated
     */
    public void setGmtPlan(Date gmtPlan) {
        this.gmtPlan = gmtPlan;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.gmt_start
     *
     * @mbg.generated
     */
    public Date getGmtStart() {
        return gmtStart;
    }

    /**
     *
     * @param gmtStart the value for altershield_exe_defender_detect.gmt_start
     *
     * @mbg.generated
     */
    public void setGmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.gmt_finish
     *
     * @mbg.generated
     */
    public Date getGmtFinish() {
        return gmtFinish;
    }

    /**
     *
     * @param gmtFinish the value for altershield_exe_defender_detect.gmt_finish
     *
     * @mbg.generated
     */
    public void setGmtFinish(Date gmtFinish) {
        this.gmtFinish = gmtFinish;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.ignored
     *
     * @mbg.generated
     */
    public String getIgnored() {
        return ignored;
    }

    /**
     *
     * @param ignored the value for altershield_exe_defender_detect.ignored
     *
     * @mbg.generated
     */
    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.feedback_status
     *
     * @mbg.generated
     */
    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    /**
     *
     * @param feedbackStatus the value for altershield_exe_defender_detect.feedback_status
     *
     * @mbg.generated
     */
    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.feedback_reason
     *
     * @mbg.generated
     */
    public String getFeedbackReason() {
        return feedbackReason;
    }

    /**
     *
     * @param feedbackReason the value for altershield_exe_defender_detect.feedback_reason
     *
     * @mbg.generated
     */
    public void setFeedbackReason(String feedbackReason) {
        this.feedbackReason = feedbackReason;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.feedback_operator
     *
     * @mbg.generated
     */
    public String getFeedbackOperator() {
        return feedbackOperator;
    }

    /**
     *
     * @param feedbackOperator the value for altershield_exe_defender_detect.feedback_operator
     *
     * @mbg.generated
     */
    public void setFeedbackOperator(String feedbackOperator) {
        this.feedbackOperator = feedbackOperator;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.uid
     *
     * @mbg.generated
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid the value for altershield_exe_defender_detect.uid
     *
     * @mbg.generated
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.rule_status
     *
     * @mbg.generated
     */
    public String getRuleStatus() {
        return ruleStatus;
    }

    /**
     *
     * @param ruleStatus the value for altershield_exe_defender_detect.rule_status
     *
     * @mbg.generated
     */
    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.allow_ignore
     *
     * @mbg.generated
     */
    public String getAllowIgnore() {
        return allowIgnore;
    }

    /**
     *
     * @param allowIgnore the value for altershield_exe_defender_detect.allow_ignore
     *
     * @mbg.generated
     */
    public void setAllowIgnore(String allowIgnore) {
        this.allowIgnore = allowIgnore;
    }

    /**
     *
     * @return the value of altershield_exe_defender_detect.result_ref
     *
     * @mbg.generated
     */
    public String getResultRef() {
        return resultRef;
    }

    /**
     *
     * @param resultRef the value for altershield_exe_defender_detect.result_ref
     *
     * @mbg.generated
     */
    public void setResultRef(String resultRef) {
        this.resultRef = resultRef;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", detectExeId=").append(detectExeId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", detectGroupId=").append(detectGroupId);
        sb.append(", changeOrderId=").append(changeOrderId);
        sb.append(", nodeId=").append(nodeId);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", ruleName=").append(ruleName);
        sb.append(", ruleOwner=").append(ruleOwner);
        sb.append(", pluginKey=").append(pluginKey);
        sb.append(", mainClass=").append(mainClass);
        sb.append(", externalId=").append(externalId);
        sb.append(", stage=").append(stage);
        sb.append(", status=").append(status);
        sb.append(", blocked=").append(blocked);
        sb.append(", blockMsg=").append(blockMsg);
        sb.append(", msg=").append(msg);
        sb.append(", gmtPlan=").append(gmtPlan);
        sb.append(", gmtStart=").append(gmtStart);
        sb.append(", gmtFinish=").append(gmtFinish);
        sb.append(", ignored=").append(ignored);
        sb.append(", feedbackStatus=").append(feedbackStatus);
        sb.append(", feedbackReason=").append(feedbackReason);
        sb.append(", feedbackOperator=").append(feedbackOperator);
        sb.append(", uid=").append(uid);
        sb.append(", ruleStatus=").append(ruleStatus);
        sb.append(", allowIgnore=").append(allowIgnore);
        sb.append(", resultRef=").append(resultRef);
        sb.append("]");
        return sb.toString();
    }
}