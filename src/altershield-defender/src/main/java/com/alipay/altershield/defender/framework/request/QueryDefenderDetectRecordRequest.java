/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

import com.alipay.altershield.shared.defender.enums.DefenderStatusDisplayEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

/**
 * Query the execution record request structure of defense rules
 *
 * @author yhaoxuan
 * @version QueryDefenderDetectRecordRequest.java, v 0.1 2022年11月29日 7:56 下午 yhaoxuan
 */
public class QueryDefenderDetectRecordRequest {

    /**
     * rule id
     */
    private String ruleId;

    /**
     * execution status
     */
    private Set<DefenderStatusDisplayEnum> status;

    /**
     * start time
     */
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * end time
     */
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * Whether to query non-feedback data, true only queries non-feedback data
     */
    private boolean filterFeedback;

    public Set<DefenderStatusDisplayEnum> getStatus() {
        return status;
    }

    public void setStatus(Set<DefenderStatusDisplayEnum> status) {
        this.status = status;
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
     * Getter method for property <tt>startTime</tt>.
     *
     * @return property value of startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Setter method for property <tt>startTime</tt>.
     *
     * @param startTime value to be assigned to property startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     *
     * @return property value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter method for property <tt>filterFeedback</tt>.
     *
     * @return property value of filterFeedback
     */
    public boolean isFilterFeedback() {
        return filterFeedback;
    }

    /**
     * Setter method for property <tt>filterFeedback</tt>.
     *
     * @param filterFeedback value to be assigned to property filterFeedback
     */
    public void setFilterFeedback(boolean filterFeedback) {
        this.filterFeedback = filterFeedback;
    }
}