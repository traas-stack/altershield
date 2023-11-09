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
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.alipay.altershield.change.meta.model.enums;

/**
 *
 * @author wb-wsh563471
 * @version $Id: RiskDefenceTagTypeEnum.java, v 0.1 2020年04月09日 14:36 wb-wsh563471 Exp $
 */
public enum RiskDefenceTagTypeEnum {

    /** 新变更窗口 */
    NEW_CHANGE_WINDOW("NEW_CHANGE_WINDOW"),
    /** 旧变更窗口 */
    OLD_CHANGE_WINDOW("OLD_CHANGE_WINDOW");

    private final String type;

    private RiskDefenceTagTypeEnum(String type) {
        this.type = type;
    }

    /**
     * @return property value of {@link #type}
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     * @return
     */
    public static RiskDefenceTagTypeEnum getByType(String type) {
        for (RiskDefenceTagTypeEnum e : RiskDefenceTagTypeEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}