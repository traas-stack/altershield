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
package com.alipay.altershield.spi.defender.model.request;


import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Change execution information
 *
 * @author yhaoxuan
 * @version ChangeExecuteInfo.java, v 0.1 2022年08月09日 4:53 下午 yhaoxuan
 */
public class ChangeExecuteInfo {

    /**
     * The effective carrier type (server/ECS...)
     */
    @NotNull
    private String effectiveTargetType;

    /**
     * Change the effective carrier list (for example: the type is server, which stores the server’s IP address list)
     */
    @NotNull
    private Set<String> effectiveLocations;

    /**
     * is it currently in the overall change order stage
     */
    private boolean orderPhase;

    /**
     * The environment of the change executed currently.
     *
     * {@link com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum#getPhase()}
     */
    @NotNull
    private String changePhase;

    /**
     * Current batch number
     */
    private int batchNo;

    /**
     * Is it the last batch of this change order
     */
    private boolean lastBatch;

    /**
     * Whether it is the last batch of the current stage
     */
    private boolean isLastBatchInPhase;

    /**
     * Total number of batches in the current stage
     */
    private int totalBatchInPhase;

    /**
     * Total number of batches of change order
     */
    private int totalBatchNum;

    /**
     * Start time of the change batch
     */
    @NotNull
    private Date changeStartTime;

    /**
     * Finish time of the change batch
     */
    private Date changeFinishTime;

    /**
     * extension field
     */
    private Map<String, Object> extInfo;

    /**
     * Getter method for property <tt>effectiveTargetType</tt>.
     *
     * @return property value of effectiveTargetType
     */
    public String getEffectiveTargetType() {
        return effectiveTargetType;
    }

    /**
     * Setter method for property <tt>effectiveTargetType</tt>.
     *
     * @param effectiveTargetType value to be assigned to property effectiveTargetType
     */
    public void setEffectiveTargetType(String effectiveTargetType) {
        this.effectiveTargetType = effectiveTargetType;
    }

    /**
     * Getter method for property <tt>effectiveLocations</tt>.
     *
     * @return property value of effectiveLocations
     */
    public Set<String> getEffectiveLocations() {
        return effectiveLocations;
    }

    /**
     * Setter method for property <tt>effectiveLocations</tt>.
     *
     * @param effectiveLocations value to be assigned to property effectiveLocations
     */
    public void setEffectiveLocations(Set<String> effectiveLocations) {
        this.effectiveLocations = effectiveLocations;
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

    /**
     * Getter method for property <tt>changePhase</tt>.
     *
     * @return property value of changePhase
     */
    public String getChangePhase() {
        return changePhase;
    }

    /**
     * Setter method for property <tt>changePhase</tt>.
     *
     * @param changePhase value to be assigned to property changePhase
     */
    public void setChangePhase(String changePhase) {
        this.changePhase = changePhase;
    }

    /**
     * Getter method for property <tt>batchNo</tt>.
     *
     * @return property value of batchNo
     */
    public int getBatchNo() {
        return batchNo;
    }

    /**
     * Setter method for property <tt>batchNo</tt>.
     *
     * @param batchNo value to be assigned to property batchNo
     */
    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * Getter method for property <tt>lastBatch</tt>.
     *
     * @return property value of lastBatch
     */
    public boolean isLastBatch() {
        return lastBatch;
    }

    /**
     * Setter method for property <tt>lastBatch</tt>.
     *
     * @param lastBatch value to be assigned to property lastBatch
     */
    public void setLastBatch(boolean lastBatch) {
        this.lastBatch = lastBatch;
    }

    /**
     * Getter method for property <tt>isLastBatchInPhase</tt>.
     *
     * @return property value of isLastBatchInPhase
     */
    public boolean isLastBatchInPhase() {
        return isLastBatchInPhase;
    }

    /**
     * Setter method for property <tt>isLastBatchInPhase</tt>.
     *
     * @param lastBatchInPhase value to be assigned to property isLastBatchInPhase
     */
    public void setLastBatchInPhase(boolean lastBatchInPhase) {
        isLastBatchInPhase = lastBatchInPhase;
    }

    /**
     * Getter method for property <tt>totalBatchInPhase</tt>.
     *
     * @return property value of totalBatchInPhase
     */
    public int getTotalBatchInPhase() {
        return totalBatchInPhase;
    }

    /**
     * Setter method for property <tt>totalBatchInPhase</tt>.
     *
     * @param totalBatchInPhase value to be assigned to property totalBatchInPhase
     */
    public void setTotalBatchInPhase(int totalBatchInPhase) {
        this.totalBatchInPhase = totalBatchInPhase;
    }

    /**
     * Getter method for property <tt>totalBatchNum</tt>.
     *
     * @return property value of totalBatchNum
     */
    public int getTotalBatchNum() {
        return totalBatchNum;
    }

    /**
     * Setter method for property <tt>totalBatchNum</tt>.
     *
     * @param totalBatchNum value to be assigned to property totalBatchNum
     */
    public void setTotalBatchNum(int totalBatchNum) {
        this.totalBatchNum = totalBatchNum;
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

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }
}