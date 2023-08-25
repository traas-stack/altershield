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
package com.alipay.altershiled.schedule.dal.dataobject;

import java.util.Date;

/**
 * @author jinyalong
 * @version : ExeSchedulerEventDO.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public class ExeSchedulerEventDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String eventId;

    /**
     * Database Column Remarks:
     *   创建时间，整个时间第一次创建的时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;
    /**
     *
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * Database Column Remarks:
     *   任务分组
     *
     *
     * @mbg.generated
     */
    private String eventGroup;

    /**
     * Database Column Remarks:
     *   事件类型，用于序列化事件对象
     *
     *
     * @mbg.generated
     */
    private String eventType;

    /**
     * Database Column Remarks:
     *   附加信息
     *
     *
     * @mbg.generated
     */
    private String info;

    /**
     * Database Column Remarks:
     *   重试次数
     *
     *
     * @mbg.generated
     */
    private int retried;

    /**
     * Database Column Remarks:
     *   状态
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   执行结果
     *
     *
     * @mbg.generated
     */
    private String rstMsg;

    /**
     * Database Column Remarks:
     *   计划执行时间
     *
     *
     * @mbg.generated
     */
    private Date gmtPlan;
    /**
     * 事件开始时间，这儿指的是事件源的时间
     */
    private Date gmtEventStart;

    /**
     * Database Column Remarks:
     *   实际执行时间开始时间
     *   只记录最近一次
     *
     *
     * @mbg.generated
     */
    private Date gmtExecuteStart;

    /**
     * 事件执行完成的时间
     */
    private Date gmtExecuteEnd;

    /**
     * 事件结束时间
     */
    private Date gmtEventEnd;

    /**
     * Database Column Remarks:
     *  源id，根据id可以解释事件产生的源
     *
     * @mbg.generated
     */
    private String sourceId;

    /**
     * Database Column Remarks:
     *   对应事件处理的listener的类型
     *
     * @mbg.generated
     */
    private String listenerType;

    /**
     * 任务优先级
     */
    private int priority = 0;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRetried() {
        return retried;
    }

    public void setRetried(int retried) {
        this.retried = retried;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRstMsg() {
        return rstMsg;
    }

    public void setRstMsg(String rstMsg) {
        this.rstMsg = rstMsg;
    }

    public Date getGmtPlan() {
        return gmtPlan;
    }

    public void setGmtPlan(Date gmtPlan) {
        this.gmtPlan = gmtPlan;
    }

    public Date getGmtEventStart() {
        return gmtEventStart;
    }

    public void setGmtEventStart(Date gmtEventStart) {
        this.gmtEventStart = gmtEventStart;
    }

    public Date getGmtExecuteStart() {
        return gmtExecuteStart;
    }

    public void setGmtExecuteStart(Date gmtExecuteStart) {
        this.gmtExecuteStart = gmtExecuteStart;
    }

    public Date getGmtExecuteEnd() {
        return gmtExecuteEnd;
    }

    public void setGmtExecuteEnd(Date gmtExecuteEnd) {
        this.gmtExecuteEnd = gmtExecuteEnd;
    }

    public Date getGmtEventEnd() {
        return gmtEventEnd;
    }

    public void setGmtEventEnd(Date gmtEventEnd) {
        this.gmtEventEnd = gmtEventEnd;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getListenerType() {
        return listenerType;
    }

    public void setListenerType(String listenerType) {
        this.listenerType = listenerType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ExeSchdPointDO{" +
                "eventId='" + eventId + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", uid='" + uid + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", eventType='" + eventType + '\'' +
                ", info='" + info + '\'' +
                ", retried=" + retried +
                ", status='" + status + '\'' +
                ", rstMsg='" + rstMsg + '\'' +
                ", gmtPlan=" + gmtPlan +
                ", gmtEventStart=" + gmtEventStart +
                ", gmtExecuteStart=" + gmtExecuteStart +
                ", gmtExecuteEnd=" + gmtExecuteEnd +
                ", gmtEventEnd=" + gmtEventEnd +
                ", sourceId='" + sourceId + '\'' +
                ", listenerType='" + listenerType + '\'' +
                ", priority=" + priority +
                '}';
    }
}