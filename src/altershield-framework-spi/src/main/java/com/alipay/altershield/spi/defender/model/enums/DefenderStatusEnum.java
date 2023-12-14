/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender.model.enums;

/**
 * Change defense detect status
 *
 * @author yhaoxuan
 * @version DefenderStatusEnum.java, v 0.1 2022年08月17日 2:21 下午 yhaoxuan
 */
public enum DefenderStatusEnum {

    /**
     * initialized
     */
    INIT("INIT"),

    /**
     * on executing
     */
    EXE("EXE"),

    /**
     * Executing on asynchronous mode
     */
    ASYNC_CHECK("ASYNC_CHECK"),

    /**
     * May have risks, continue to observe
     */
    SUSPEND("SUSPEND"),

    /**
     * Final state - passed
     */
    PASS("PASS"),

    /**
     * Final state - failed
     */
    FAIL("FAIL"),

    /**
     * Final state - got exception in executing
     */
    EXCEPTION("EXCEPTION"),

    /**
     * Final state - canceled
     */
    CANCEL("CANCEL"),

    /**
     * Final state - timeout
     */
    TIMEOUT("TIMEOUT"),
    ;

    /**
     * detect status
     */
    private final String status;

    DefenderStatusEnum(String status) {
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
    public static DefenderStatusEnum getByStatus(String status) {
        DefenderStatusEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            DefenderStatusEnum e = var1[var3];
            if (e.getStatus().equalsIgnoreCase(status)) {
                return e;
            }
        }

        return null;
    }
}