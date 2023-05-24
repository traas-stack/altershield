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
package com.alipay.altershield.framework.core.change.entity.enums;

/**
 * 变更服务调用权限
 * @author yuefan.wyf
 * @version $Id: PermissionLevelEnum.java, v 0.1 2019年01月24日 下午7:58 yuefan.wyf Exp $
 */
public enum PermissionLevelEnum {

    /** 不允许其他平台访问*/
    PRIVATE("PRIVATE"),
    /** 允许白名单平台访问 */
    PROTECTED("PROTECTED"),
    /** 允许所有平台访问*/
    PUBLIC("PUBLIC");

    private final String type;

    private PermissionLevelEnum(String type) {
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
    public static PermissionLevelEnum getByType(String type) {
        for (PermissionLevelEnum e : PermissionLevelEnum
                .values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}