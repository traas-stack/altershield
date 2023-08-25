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
package com.alipay.altershiled.schedule.enums;

/**
 * @author jinyalong
 * @version : SchedulePointStatusEnum.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public enum SchedulePointStatusEnum {
    /**
     * need to execute
     */
    TODO("todo"),
    /**
     * paused because of too much retry failure
     */
    HOLD("hold"),
    /**
     * paused by interface invocation
     */
    PAUSE("pause"),
    /**
     * do param modify so we need to suspend execution
     */
    SUSPEND("suspend"),
    /**
     * no need to execute
     * Prohibit the execution of schd tasks with incorrect states in some concurrent situations.
     * For example, when rolling back, is the forward task still in progress?
     */
    ABANDON("abandon"),
    /** not permitted to be executed */
    CLOSE("close");

    private final String code;

    SchedulePointStatusEnum(String code) {
        this.code = code;
    }

    /**
     * @return property value of {@link #code}
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     * @return
     */
    public static SchedulePointStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (SchedulePointStatusEnum e : SchedulePointStatusEnum.values()) {
            if (code.equalsIgnoreCase(e.getCode())) {
                return e;
            }
        }
        return null;
    }

}
