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
 * @author lex
 * @version $Id: FeedbackTypeEnum.java, v 0.1 2020年01月02日 下午1:31 lex Exp $
 */
@Getter
@AllArgsConstructor
public enum FeedbackTypeEnum {
    UNREASONABLE_RULE(FeedbackStatusEnum.INVALID, "规则不合理"), MALFORM_RULE(FeedbackStatusEnum.INVALID,
            "规则校验异常"), FAULT_INJECT(FeedbackStatusEnum.EXACTLY, "演练导致"), EXCEPTION(
                    FeedbackStatusEnum.EXACTLY,
                    "异常事件"), REAL_FAULT(FeedbackStatusEnum.EXACTLY, "故障事件"),;

    private final FeedbackStatusEnum category;
    private final String desc;

    /**
     * Get by string code.
     *
     * @param code
     * @return
     */
    public static FeedbackTypeEnum getByCode(String code) {
        for (FeedbackTypeEnum c : FeedbackTypeEnum.values()) {
            if (c.name().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

}
