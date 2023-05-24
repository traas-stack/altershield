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
package com.alipay.altershield.framework.core.change.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum ChangePhaseEnum implements Serializable {
    /**
     * Development Phase
     */
    DEV_PHASE("dev_phase"),
    /**
     * Simulation Phase
     */
    SIM_PHASE("sim_phase"),
    /**
     * Pre-release Phase
     */
    PRE_PHASE("pre_phase"),
    /**
     * Gray Release Phase
     */
    GRAY_PHASE("gray_phase"),
    /**
     * The First Batch of Production Phase
     */
    PROD_BETA_PHASE("prod_beta_phase"),
    /**
     * Production Phase
     */
    PROD_PHASE("prod_phase");

    private final String phase;

    public static ChangePhaseEnum getByPhase(String phase) {
        for (ChangePhaseEnum e : ChangePhaseEnum.values()) {
            if (e.getPhase().equalsIgnoreCase(phase)) {
                return e;
            }
        }
        return null;
    }
}
