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

/**
 * 防御规则审批人角色
 *
 * @author wb-wsh563471
 * @version $Id: RiskDefenseApproveRoleEnum.java, v 0.1 2020年04月26日 11:14 wb-wsh563471 Exp $
 */
public enum RiskDefenseApproveRoleEnum {
    /** Affects application owner */
    APP_OWNER("APP_OWNER", "影响应用OWNER"),
    /** Impact Application Owner Supervisor */
    APP_OWNER_SUPERIOR("APP_OWNER_SUPERIOR", "影响应用OWNER主管"),
    /** Rule Owner */
    DEFENSE_OWNER("DEFENSE_OWNER", "规则OWNER"),
    /** Designated person */
    APPOINT("APPOINT", "指定的人"),
    /** Submitter Supervisor */
    SUPERIOR("SUPERIOR", "提交人主管");

    private final String type;
    private final String msg;

    private RiskDefenseApproveRoleEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    /**
     * @return property value of {@link #type}
     */
    public String getType() {
        return type;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param type
     * @return
     */
    public static RiskDefenseApproveRoleEnum getByType(String type) {
        for (RiskDefenseApproveRoleEnum e : RiskDefenseApproveRoleEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
