/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.result;

/**
 * Defender detect task scheduling results
 *
 * @author yhaoxuan
 * @version DefenderTaskExecuteResult.java, v 0.1 2022年08月30日 3:30 下午 yhaoxuan
 */
public class DefenderTaskResult {

    /**
     * Is task scheduling successful
     */
    private boolean success;

    /**
     * When task failed, need to be retried or not
     */
    private boolean needRetry;

    /**
     * Number of retries
     */
    private long retryCount;

    /**
     * Task scheduling information
     */
    private String msg;

    /**
     * Fail without retrying
     *
     * @param msg fail message
     * @return Task execution result structure
     */
    public static DefenderTaskResult failWithNoRetry(String msg) {
        DefenderTaskResult result = new DefenderTaskResult();
        result.setSuccess(false);
        result.setNeedRetry(false);
        result.setMsg(msg);

        return result;
    }

    /**
     * Failed and needs to retry
     *
     * @param msg fail message
     * @return Task execution result structure
     */
    public static DefenderTaskResult failWithRetry(String msg) {
        DefenderTaskResult result = new DefenderTaskResult();
        result.setSuccess(false);
        result.setNeedRetry(true);
        result.setMsg(msg);

        return result;
    }

    /**
     * Task succeed
     *
     * @param msg execution message
     * @return Task execution result structure
     */
    public static DefenderTaskResult succeed(String msg) {
        DefenderTaskResult result = new DefenderTaskResult();
        result.setSuccess(true);
        result.setNeedRetry(false);
        result.setMsg(msg);

        return result;
    }

    /**
     * Getter method for property <tt>retryCount</tt>.
     *
     * @return property value of retryCount
     */
    public long getRetryCount() {
        return retryCount;
    }

    /**
     * Setter method for property <tt>retryCount</tt>.
     *
     * @param retryCount value to be assigned to property retryCount
     */
    public void setRetryCount(long retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     *
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>needRetry</tt>.
     *
     * @return property value of needRetry
     */
    public boolean isNeedRetry() {
        return needRetry;
    }

    /**
     * Setter method for property <tt>needRetry</tt>.
     *
     * @param needRetry value to be assigned to property needRetry
     */
    public void setNeedRetry(boolean needRetry) {
        this.needRetry = needRetry;
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
}