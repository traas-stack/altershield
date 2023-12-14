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
/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.entity;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.framework.core.risk.model.enums.FeedbackStatusEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRef;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;

import java.util.Date;

/**
 * Change defender execution record entity
 *
 * @author yhaoxuan
 * @version ExeDefenderCheckEntity.java, v 0.1 2022年08月17日 3:35 下午 yhaoxuan
 */
public class ExeDefenderDetectEntity extends DefenderBaseEntity {

    /**
     * Defense execution record unique id
     */
    private String detectExeId;

    /**
     * Defense execution record group id - corresponds to a pre/post verification
     */
    private String detectGroupId;

    /**
     * The change work order ID to which the defense execution record belongs
     */
    private String changeOrderId;

    /**
     * The change node id to which the defense execution record belongs
     */
    private String nodeId;

    /**
     * Defense rule id
     */
    private String ruleId;

    /**
     * Downstream platform rule id
     */
    private String externalId;

    /**
     * Defense rule name
     */
    private String ruleName;

    /**
     * Defense rule administrator user ID, separated by commas
     */
    private String ruleOwner;

    /**
     * related plugin key
     */
    private String pluginKey;

    /**
     * related main class of the plugin
     */
    private String mainClass;

    /**
     * detect phase - pre/post
     */
    private DefenseStageEnum stage;

    /**
     * Defense detect status
     */
    private DefenderStatusEnum status;

    /**
     * Whether this defense detection blocks the execution of changes
     */
    private boolean blocked;

    /**
     * The reason why this defense detection blocks the execution of changes
     */
    private String blockMsg;

    /**
     * Brief result information of this defense detection
     */
    private String msg;

    /**
     * Detailed result information of this defense detection
     */
    private KvRef<String> resultRef;

    /**
     * Planned start time
     */
    private Date gmtPlan;

    /**
     * Actual start time
     */
    private Date gmtStart;

    /**
     * Actual completion time
     */
    private Date gmtFinish;

    /**
     * Whether the result of this detection is ignored
     */
    private boolean ignored;

    /**
     * Manual feedback status of defense execution records
     */
    private FeedbackStatusEnum feedbackStatus;

    /**
     * Reasons given for human feedback on defense execution records
     */
    private String feedbackReason;

    /**
     * The feedback person of the defense execution record
     */
    private String feedbackOperator;

    /**
     *规则状态*
     */
    private DefenderRuleStatusEnum ruleStatus;

    /**
     * Is it allowed to ignore
     */
    private boolean allowIgnore = true;

    public ExeDefenderDetectEntity(KeyValueStorage keyValueStorage) {
        super();
        this.resultRef = new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null);
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
     * Getter method for property <tt>ruleOwner</tt>.
     *
     * @return property value of ruleOwner
     */
    public String getRuleOwner() {
        return ruleOwner;
    }

    /**
     * Setter method for property <tt>ruleOwner</tt>.
     *
     * @param ruleOwner value to be assigned to property ruleOwner
     */
    public void setRuleOwner(String ruleOwner) {
        this.ruleOwner = ruleOwner;
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
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public DefenderStatusEnum getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(DefenderStatusEnum status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>blocked</tt>.
     *
     * @return property value of blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Setter method for property <tt>blocked</tt>.
     *
     * @param blocked value to be assigned to property blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Getter method for property <tt>blockMsg</tt>.
     *
     * @return property value of blockMsg
     */
    public String getBlockMsg() {
        return blockMsg;
    }

    /**
     * Setter method for property <tt>blockMsg</tt>.
     *
     * @param blockMsg value to be assigned to property blockMsg
     */
    public void setBlockMsg(String blockMsg) {
        this.blockMsg = blockMsg;
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
     * Getter method for property <tt>resultRef</tt>.
     *
     * @return property value of resultRef
     */
    public KvRef<String> getResultRef() {
        return resultRef;
    }

    /**
     * Setter method for property <tt>resultRef</tt>.
     *
     * @param resultRef value to be assigned to property resultRef
     */
    public void setResultRef(KvRef<String> resultRef) {
        this.resultRef = resultRef;
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
     * Getter method for property <tt>ignored</tt>.
     *
     * @return property value of ignored
     */
    public boolean isIgnored() {
        return ignored;
    }

    /**
     * Setter method for property <tt>ignored</tt>.
     *
     * @param ignored value to be assigned to property ignored
     */
    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    /**
     * Getter method for property <tt>feedbackStatus</tt>.
     *
     * @return property value of feedbackStatus
     */
    public FeedbackStatusEnum getFeedbackStatus() {
        return feedbackStatus;
    }

    /**
     * Setter method for property <tt>feedbackStatus</tt>.
     *
     * @param feedbackStatus value to be assigned to property feedbackStatus
     */
    public void setFeedbackStatus(FeedbackStatusEnum feedbackStatus) {
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
     * Getter method for property <tt>allowIgnore</tt>.
     *
     * @return property value of allowIgnore
     */
    public boolean isAllowIgnore() {
        return allowIgnore;
    }

    /**
     * Setter method for property <tt>allowIgnore</tt>.
     *
     * @param allowIgnore value to be assigned to property allowIgnore
     */
    public void setAllowIgnore(boolean allowIgnore) {
        this.allowIgnore = allowIgnore;
    }

    public DefenderRuleStatusEnum getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(DefenderRuleStatusEnum ruleStatus) {
        this.ruleStatus = ruleStatus;
    }
}