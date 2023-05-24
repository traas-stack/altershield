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
 *
 * @author haoxuan.yhx
 * @version $Id: DefenseFilterTypeEnum.java, v 0.1 2019年06月18日 下午9:53 haoxuan.yhx Exp $
 */
@Getter
@AllArgsConstructor
public enum DefenseFilterTypeEnum {

    /** 不做匹配 */
    NO_MATCHES("NO_MATCHES"),
    /** 包含一个 */
    MATCHES_ONE("MATCHES_ONE"),
    /** 全部包含 */
    MATCHES_ALL("MATCHES_ALL");

    private final String type;


    /**
     * @param type
     * @return
     */
    public static DefenseFilterTypeEnum getByType(String type) {
        for (DefenseFilterTypeEnum e : DefenseFilterTypeEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
