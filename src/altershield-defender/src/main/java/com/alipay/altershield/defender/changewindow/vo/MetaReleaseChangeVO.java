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
///**
// * Alipay.com Inc.
// * Copyright (c) 2004-2019 All Rights Reserved.
// */
//package com.alipay.altershield.defender.changewindow.vo;
//
//
//
//import com.alipay.altershield.defender.changewindow.enums.ReleaseChangeStatusEnum;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * 破网申请列表展示
// * @author zhijian.leixiao
// * @version 1.0: MetaReleaseChangeVO, v 0.1 2019-08-01 20:09 lx207087 Exp $
// */
//public class MetaReleaseChangeVO {
//    /**
//     * 破网申请id
//     */
//    private String id;
//    /**
//     * 破网申请名称
//     */
//    private String name;
//    /**
//     * 破网申请操作人
//     */
//    private String operator;
//    /**
//     * 破网申请操作人
//     */
//    private String operatorView;
//    /**
//     * 对应封网计划ID
//     */
//    private String blockChangeId;
//    /**
//     * 对应封网计划名称
//     */
//    private String blockChangeName;
//    /**
//     * 破网开始时间
//     */
//    private Date   startTime;
//    /**
//     * 破网结束时间
//     */
//    private Date   endTime;
//    /**
//     * 破网申请状态
//     */
//    private ReleaseChangeStatusEnum status;
//
//    /**
//     * gmt create
//     */
//    private Date gmtCreate;
//
//    /**
//     * gmt modified
//     */
//    private Date gmtModified;
//
//    /**
//     * 业务影响
//     */
//    private String bizInfluence;
//
//    /**
//     * 原因
//     */
//    private String reason;
//
//    /**
//     * 破网架构域范围
//     */
//    private List<ArchDomainTreeNode> archDomainRange;
//    /**
//     * 破网业务域范围
//     */
//    private List<String>             busGroupRange;
//
//    /**
//     * 破网原因类型
//     */
//    private String reasonType;
//
//    /**
//     * 变更平台的变更类型
//     */
//    private List<String> targetTypes;
//
//    /**
//     * Getter method for property <tt>id</tt>.
//     *
//     * @return property value of id
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param id value to be assigned to property id
//     */
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    /**
//     * Getter method for property <tt>name</tt>.
//     *
//     * @return property value of name
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param name value to be assigned to property name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * Getter method for property <tt>operator</tt>.
//     *
//     * @return property value of operator
//     */
//    public String getOperator() {
//        return operator;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param operator value to be assigned to property operator
//     */
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
//
//    /**
//     * Getter method for property <tt>blockChangeId</tt>.
//     *
//     * @return property value of blockChangeId
//     */
//    public String getBlockChangeId() {
//        return blockChangeId;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param blockChangeId value to be assigned to property blockChangeId
//     */
//    public void setBlockChangeId(String blockChangeId) {
//        this.blockChangeId = blockChangeId;
//    }
//
//    /**
//     * Getter method for property <tt>blockChangeName</tt>.
//     *
//     * @return property value of blockChangeName
//     */
//    public String getBlockChangeName() {
//        return blockChangeName;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param blockChangeName value to be assigned to property blockChangeName
//     */
//    public void setBlockChangeName(String blockChangeName) {
//        this.blockChangeName = blockChangeName;
//    }
//
//    /**
//     * Getter method for property <tt>startTime</tt>.
//     *
//     * @return property value of startTime
//     */
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param startTime value to be assigned to property startTime
//     */
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    /**
//     * Getter method for property <tt>endTime</tt>.
//     *
//     * @return property value of endTime
//     */
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param endTime value to be assigned to property endTime
//     */
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }
//
//    /**
//     * Getter method for property <tt>status</tt>.
//     *
//     * @return property value of status
//     */
//    public ReleaseChangeStatusEnum getStatus() {
//        return status;
//    }
//
//    /**
//     * Setter method for property <tt>counterType</tt>.
//     *
//     * @param status value to be assigned to property status
//     */
//    public void setStatus(ReleaseChangeStatusEnum status) {
//        this.status = status;
//    }
//
//    /**
//     * Getter method for property <tt>operatorView</tt>.
//     *
//     * @return property value of operatorView
//     */
//    public String getOperatorView() {
//        return operatorView;
//    }
//
//    /**
//     * Setter method for property <tt>operatorView</tt>.
//     *
//     * @param operatorView value to be assigned to property operatorView
//     */
//    public void setOperatorView(String operatorView) {
//        this.operatorView = operatorView;
//    }
//
//    /**
//     * Gets get gmt create.
//     *
//     * @return the get gmt create
//     */
//    public Date getGmtCreate() {
//        return gmtCreate;
//    }
//
//    /**
//     * Sets set gmt create.
//     *
//     * @param gmtCreate the gmt create
//     */
//    public void setGmtCreate(Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    /**
//     * Gets get gmt modified.
//     *
//     * @return the get gmt modified
//     */
//    public Date getGmtModified() {
//        return gmtModified;
//    }
//
//    /**
//     * Sets set gmt modified.
//     *
//     * @param gmtModified the gmt modified
//     */
//    public void setGmtModified(Date gmtModified) {
//        this.gmtModified = gmtModified;
//    }
//
//    /**
//     * Gets get biz influence.
//     *
//     * @return the get biz influence
//     */
//    public String getBizInfluence() {
//        return bizInfluence;
//    }
//
//    /**
//     * Sets set biz influence.
//     *
//     * @param bizInfluence the biz influence
//     */
//    public void setBizInfluence(String bizInfluence) {
//        this.bizInfluence = bizInfluence;
//    }
//
//    /**
//     * Gets get reason.
//     *
//     * @return the get reason
//     */
//    public String getReason() {
//        return reason;
//    }
//
//    /**
//     * Sets set reason.
//     *
//     * @param reason the reason
//     */
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//
//    /**
//     * Gets get arch domain range.
//     *
//     * @return the get arch domain range
//     */
//    public List<ArchDomainTreeNode> getArchDomainRange() {
//        return archDomainRange;
//    }
//
//    /**
//     * Sets set arch domain range.
//     *
//     * @param archDomainRange the arch domain range
//     */
//    public void setArchDomainRange(List<ArchDomainTreeNode> archDomainRange) {
//        this.archDomainRange = archDomainRange;
//    }
//
//    /**
//     * Gets get bus group range.
//     *
//     * @return the get bus group range
//     */
//    public List<String> getBusGroupRange() {
//        return busGroupRange;
//    }
//
//    /**
//     * Sets set bus group range.
//     *
//     * @param busGroupRange the bus group range
//     */
//    public void setBusGroupRange(List<String> busGroupRange) {
//        this.busGroupRange = busGroupRange;
//    }
//
//    /**
//     * Getter method for property <tt>reasonType</tt>.
//     *
//     * @return property value of reasonType
//     */
//    public String getReasonType() {
//        return reasonType;
//    }
//
//    /**
//     * Setter method for property <tt>reasonType</tt>.
//     *
//     * @param reasonType value to be assigned to property reasonType
//     */
//    public void setReasonType(String reasonType) {
//        this.reasonType = reasonType;
//    }
//
//    /**
//     * Getter method for property <tt>targetTypes</tt>.
//     *
//     * @return property value of targetTypes
//     */
//    public List<String> getTargetTypes() {
//        return targetTypes;
//    }
//
//    /**
//     * Setter method for property <tt>targetTypes</tt>.
//     *
//     * @param targetTypes value to be assigned to property targetTypes
//     */
//    public void setTargetTypes(List<String> targetTypes) {
//        this.targetTypes = targetTypes;
//    }
//}
