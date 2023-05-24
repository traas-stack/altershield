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
 * 反馈状态
 *
 * @author yuefan.wyf
 * @version $Id: FeedbackStatusEnum.java, v 0.1 2019年09月04日 上午11:43 yuefan.wyf Exp $
 */
public enum FeedbackStatusEnum {
    /**
     * 误报
     */
    INVALID("INVALID"),
    /**
     * 漏过
     */
    MISSED("MISSED"),
    /**
     * 正常拦截
     */
    EXACTLY("EXACTLY"),
    /**
     * 正常拦截
     */
    EXCEPTION("EXCEPTION"),
    /**
     * 演练导致
     */
    @Deprecated
    DRILL("DRILL");

    private final String type;

    private FeedbackStatusEnum(String type) {
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
    public static FeedbackStatusEnum getByType(String type) {
        for (FeedbackStatusEnum e : FeedbackStatusEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
            if (e.name().equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
