/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * Logical relationship enumeration used by matching
 *
 * @author yuefan.wyf
 * @version $Id: LogicalOperatorEnum.java, v 0.1 2019年12月11日 下午10:24 yuefan.wyf Exp $
 */
public enum LogicalOperatorEnum {

    /**
     * and
     */
    AND("AND"),

    /**
     * or
     */
    OR("OR");

    /**
     * operator
     */
    private final String operator;

    private LogicalOperatorEnum(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     *
     * @return property value of operator
     */
    public String getOperator() {
        return operator;
    }
}