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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 风险防御SPI调用方式
 *
 * @author yuefan.wyf
 * @version $Id: RiskInvokeModeEnum.java, v 0.1 2019年06月06日 下午4:09 yuefan.wyf Exp $
 */
@Getter
@AllArgsConstructor
public enum RiskInvokeModeEnum {

    /**
     * 同步 TR
     */
    SYNC_TR("SYNC_TR"),
    /**
     * 异步 TR
     */
    ASYNC_TR("ASYNC_TR"),
    /**
     * 异步 TR轮询
     */
    ASYNC_TR_POLLING("ASYNC_TR_POLLING"),
    /**
     * http
     */
    HTTP("HTTP"),
    /**
     * https
     */
    HTTPS("HTTPS"),
    /**
     * 同步 plugin
     */
    PLUGIN("PLUGIN"),
    /**
     * 异步 plugin
     */
    PLUGIN_ASYNC("PLUGIN_ASYNC"),
    /**
     * 异步 plugin TR轮询
     */
    PLUGIN_ASYNC_POLLING("PLUGIN_ASYNC_POLLING");

    private final String type;

    /**
     * @param type
     * @return
     */
    public static RiskInvokeModeEnum getByType(String type) {
        for (RiskInvokeModeEnum e : RiskInvokeModeEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }

}
