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
package com.alipay.altershield.framework.core.risk.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuke.yang
 * @version Dec 25, 2018
 */
@Getter
@AllArgsConstructor
public enum RiskLevelEnum {
    /**
     * no risk
     */
    NO_RISK("NO_RISK"),
    /**
     * extreme high risk
     */
    BLOCK("BLOCK"),
    /**
     * rollback
     */
    ROLLBACK("ROLLBACK"),
    /**
     * Block, generate manual orders/direct failures based on configuration, and send self-healing
     * events to trsbrain
     */
    RISK_CURE("RISK_CURE");

    private final String level;

    /**
     * @param level
     * @return
     */
    public static RiskLevelEnum getByLevel(String level) {
        for (RiskLevelEnum e : RiskLevelEnum.values()) {
            if (e.getLevel().equalsIgnoreCase(level)) {
                return e;
            }
        }
        return null;
    }
}
