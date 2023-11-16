/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.framework.core.risk.model.enums;

/**
 * Change defense result feedback status enumeration
 *
 * @author yhaoxuan
 * @version FeedbackStatusEnum.java, v 0.1 2022年07月21日 8:38 下午 yhaoxuan
 */
public enum FeedbackStatusEnum {

    /**
     * False positive
     */
    INVALID("INVALID"),
    /**
     * Missed
     */
    MISSED("MISSED"),
    /**
     * Correct interception
     */
    EXACTLY("EXACTLY"),
    /**
     * Some exception events happened
     */
    EXCEPTION("EXCEPTION"),
    ;

    /**
     * Feedback status
     */
    private final String status;

    FeedbackStatusEnum(String status) {
        this.status = status;
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
     * get enum value by status.
     *
     * @param status the status
     * @return the target enum value
     */
    public static FeedbackStatusEnum getByStatus(String status) {
        for (FeedbackStatusEnum e : FeedbackStatusEnum
                .values()) {
            if (e.getStatus().equalsIgnoreCase(status)) {
                return e;
            }
        }
        return null;
    }
}