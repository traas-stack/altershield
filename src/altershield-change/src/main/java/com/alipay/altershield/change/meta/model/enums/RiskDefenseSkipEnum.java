/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.meta.model.enums;

/**
 * @author wb-wsh563471
 * @version $Id: RiskDefenseSkipEnum.java, v 0.1 2020年04月28日 19:44 wb-wsh563471 Exp $
 */
public enum RiskDefenseSkipEnum {
    /**
     * 允许忽略
     */
    IGNORE("IGNORE", "允许忽略"),
    /**
     * 审批忽略
     */
    APPROVAL("APPROVAL", "审批忽略"),
    /**
     * 禁止忽略
     */
    NOT_ALLOW("NOT_ALLOW", "禁止忽略");

    private final String type;

    /**
     * 描述
     */
    private final String description;

    private RiskDefenseSkipEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * @return property value of {@link #type}
     */
    public String getType() {
        return type;
    }

    /**
     * Gets get description.
     *
     * @return the get description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param type
     * @return
     */
    public static RiskDefenseSkipEnum getByType(String type) {
        for (RiskDefenseSkipEnum e : RiskDefenseSkipEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}