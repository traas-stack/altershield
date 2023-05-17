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
package com.alipay.altershield.framework.core.change.facade.enums;

/**
 * 止血恢复策略
 *
 * @author yuefan.wyf
 * @version : ChangeCureStrategyEnum.java, v 0.1 2020年09月02日 8:33 下午 yuefan.wyf Exp $
 */
public enum ChangeCureStrategyEnum {

    /**
     * 手动跳转URL执行止血恢复
     */
    MANUAL_TO_URL("手动跳转URL执行止血恢复", "MANUAL_TO_URL"),

    /**
     * 手动执行止血恢复
     */
    MANUAL_EXECUTE("手动执行止血恢复", "MANUAL_EXECUTE"),
    /**
     * 系统自动止血恢复
     */
    SYSTEM_AUTO_EXECUTE("系统自动止血恢复", "SYSTEM_AUTO_EXECUTE"),;

    private final String name;

    private final String type;

    ChangeCureStrategyEnum(String name, String type) {
        this.name = name;
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
    public static ChangeCureStrategyEnum getByType(String type) {
        for (ChangeCureStrategyEnum e : ChangeCureStrategyEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
