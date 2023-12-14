/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * Defense rule status enumeration
 *
 * @author yhaoxuan
 * @version DefenderRuleStatusEnum.java, v 0.1 2022年08月25日 9:37 下午 yhaoxuan
 */
public enum DefenderRuleStatusEnum {

    /**
     * disabled
     */
    DISABLED("DISABLED"),

    /**
     * Trial run
     */
    GRAY("GRAY"),

    /**
     * enabled
     */
    ENABLED("ENABLED"),
    ;

    /**
     * status
     */
    private final String status;

    DefenderRuleStatusEnum(String status) {
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
     * Get the corresponding enumeration value based on the string
     *
     * @param status String value corresponding to the status
     * @return Corresponding enumeration value
     */
    public static DefenderRuleStatusEnum getByStatus(String status) {
        for (DefenderRuleStatusEnum e : DefenderRuleStatusEnum.values()) {
            if (e.getStatus().equalsIgnoreCase(status)) {
                return e;
            }
        }
        return null;
    }
}