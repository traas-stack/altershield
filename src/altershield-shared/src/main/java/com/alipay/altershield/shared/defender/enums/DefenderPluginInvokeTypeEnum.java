/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * Change defense rule calling method enumeration
 *
 * @author haoxuan
 * @version DefenderPluginInvokeTypeEnum.java, v 0.1 2022年11月15日 9:06 下午 haoxuan
 */
public enum DefenderPluginInvokeTypeEnum {

    /**
     * Synchronize
     */
    SYNC("SYNC"),

    /**
     * Asynchronous polling
     */
    ASYNC_POLLING("ASYNC_POLLING"),

    /**
     * Asynchronous callback
     */
    ASYNC_CALLBACK("ASYNC_CALLBACK"),
    ;

    private final String type;

    DefenderPluginInvokeTypeEnum(String type) {
        this.type = type;
    }

    /**
     * Get the corresponding enumeration value based on the string
     *
     * @param type String value corresponding to the status
     * @return Corresponding enumeration value
     */
    public static DefenderPluginInvokeTypeEnum getByType(String type) {
        for (DefenderPluginInvokeTypeEnum e : DefenderPluginInvokeTypeEnum.values()) {
            if (e.getType().equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }
}