/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.enums;

/**
 * handing strategies when defender rule exceptions
 *
 * @author haoxuan
 * @version ExceptionStrategyEnum.java, v 0.1 2022年08月25日 9:48 下午 haoxuan
 */
public enum ExceptionStrategyEnum {

    /**
     * block the change order
     */
    BLOCK("BLOCK"),

    /**
     * release the change order
     */
    RELEASE("RELEASE");

    /**
     * handing strategy
     */
    private final String strategy;

    ExceptionStrategyEnum(String strategy) {
        this.strategy = strategy;
    }

    /**
     * Getter method for property <tt>strategy</tt>.
     *
     * @return property value of strategy
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * get enumeration object by string value
     *
     * @param strategy the string value
     * @return target enumeration object
     */
    public static ExceptionStrategyEnum getByStrategy(String strategy) {
        for (ExceptionStrategyEnum e : ExceptionStrategyEnum.values()) {
            if (e.getStrategy().equalsIgnoreCase(strategy)) {
                return e;
            }
        }
        return null;
    }
}