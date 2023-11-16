/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * ignore type when change order blocked by defender rule
 *
 * @author fujinfei
 * @version DefenderIgnoreTypeEnum.java, v 0.1 2022年11月11日 18:05 fujinfei
 */
public enum DefenderIgnoreTypeEnum {

    /**
     * allow others to ignore the result of rule
     */
    IGNORE("IGNORE"),

    /**
     * not allowed
     */
    NOT_ALLOW("NOT_ALLOW"),
    ;

    /**
     * ignore type
     */
    private final String type;

    DefenderIgnoreTypeEnum(String type) {
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
}