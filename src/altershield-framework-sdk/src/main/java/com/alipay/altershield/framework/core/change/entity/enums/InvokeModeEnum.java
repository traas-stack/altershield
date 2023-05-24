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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  变更服务调用方式
 * @author yuefan.wyf
 * @version $Id: InvokeModeEnum.java, v 0.1 2019年01月24日 下午8:16 yuefan.wyf Exp $
 */
@AllArgsConstructor
@Getter
public enum InvokeModeEnum {

    /** 同步 */
    SYNC("SYNC"),
    /** 异步 */
    ASYNC("ASYNC"),
    /** 正逆向同步*/
    ALL_SYNC("ALL_SYNC"),
    /** 正逆向异步步 */
    ALL_ASYNC("ALL_ASYNC"),
    /** 正向同步、逆向异步*/
    FORWARD_SYNC_REVERSE_ASYNC("FORWARD_SYNC_REVERSE_ASYNC"),
    /** 正向异步、逆向同步*/
    FORWARD_ASYNC_REVERSE_SYNC("FORWARD_ASYNC_REVERSE_SYNC");

    private final String type;
    /**
     * @param type
     * @return
     */
    public static InvokeModeEnum getByType(String type) {
        for (InvokeModeEnum e : InvokeModeEnum
                .values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}