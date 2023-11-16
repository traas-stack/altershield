/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.vo;

/**
 * 破网申请配置节点扩展信息
 * @author wb-wsh563471
 * @version 1.0: ReleaseChangeConfigNode, v 0.1 2019-08-01 15:09 wb-wsh563471 Exp $
 */
public class ReleaseChangeConfigNodeExt {
    /**
     * 变更外部工单id
     */
    private String bizNo;
    /**
     * 变更traceId
     */
    private String traceId;
    /**
     * 变更entireChangeId
     */
    private String entireChangeId;
    /**
     * 变更执行人
     */
    private String operator;

    /**
     * Getter method for property <tt>bizNo</tt>.
     *
     * @return property value of bizNo
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * Setter method for property <tt>bizNo</tt>.
     *
     * @param bizNo value to be assigned to property bizNo
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * Getter method for property <tt>traceId</tt>.
     *
     * @return property value of traceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Setter method for property <tt>traceId</tt>.
     *
     * @param traceId value to be assigned to property traceId
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * Getter method for property <tt>entireChangeId</tt>.
     *
     * @return property value of entireChangeId
     */
    public String getEntireChangeId() {
        return entireChangeId;
    }

    /**
     * Setter method for property <tt>entireChangeId</tt>.
     *
     * @param entireChangeId value to be assigned to property entireChangeId
     */
    public void setEntireChangeId(String entireChangeId) {
        this.entireChangeId = entireChangeId;
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

    @Override
    public String toString() {
        return "ReleaseChangeConfigNodeExt{" +
                "bizNo='" + bizNo + '\'' +
                ", traceId='" + traceId + '\'' +
                ", entireChangeId='" + entireChangeId + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
