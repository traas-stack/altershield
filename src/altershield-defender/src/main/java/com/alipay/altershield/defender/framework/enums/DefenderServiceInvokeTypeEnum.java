/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.enums;

/**
 * defender service invoke type enumeration
 *
 * @author yhaoxuan
 * @version DefenderServiceInvokeTypeEnum.java, v 0.1 2022年08月26日 3:44 下午 yhaoxuan
 */
public enum DefenderServiceInvokeTypeEnum {

    /**
     * synchronous
     */
    SYNC("SYNC"),

    /**
     * asynchronous
     */
    ASYNC("ASYNC"),
    ;

    /**
     * invoke type
     */
    private final String type;

    DefenderServiceInvokeTypeEnum(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * get enumeration object by string value
     *
     * @param type the string value
     * @return target enumeration object
     */
    public static DefenderServiceInvokeTypeEnum getByType(String type) {
        for (DefenderServiceInvokeTypeEnum e : DefenderServiceInvokeTypeEnum.values()) {
            if (e.getType().equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}