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

import lombok.Getter;

/**
 *
 * @author haoxuan.yhx
 * @version $Id: DefenseAppConfEnum.java, v 0.1 2019年09月03日 下午3:53 haoxuan.yhx Exp $
 */
@Getter
public enum DefenseAppModeEnum {

    CHANGE_APP("CHANGE_APP", "使用变更关联app"),

    CUSTOM_APP("CUSTOM_APP", "自定义app"),

    NO_APP("NO_APP", "不填app，默认使用下游平台app");

    private String mode;

    private String desc;

    DefenseAppModeEnum(String mode, String desc) {
        this.mode = mode;
        this.desc = desc;
    }

    public static DefenseAppModeEnum getByMode(String mode) {
        DefenseAppModeEnum[] var1 = values();
        int var2 = var1.length;
        for (int var3 = 0; var3 < var2; ++var3) {
            DefenseAppModeEnum e = var1[var3];
            if (e.getMode().equalsIgnoreCase(mode)) {
                return e;
            }
        }

        return null;
    }
}
