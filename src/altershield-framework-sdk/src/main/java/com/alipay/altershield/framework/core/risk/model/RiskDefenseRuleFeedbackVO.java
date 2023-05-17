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
package com.alipay.altershield.framework.core.risk.model;

import com.alipay.altershield.framework.common.validate.StringLength;
import com.alipay.altershield.framework.common.validate.UserInfoFormat;
import com.alipay.altershield.framework.core.risk.model.enums.FeedbackTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RiskDefenseRuleFeedbackVO {
    /**
     * Execution ID of risk rule, required
     */
    @NotNull
    private String ruleExeId;

    /**
     * Reason for feedback classification, required
     */
    @NotNull
    private FeedbackTypeEnum feedbackTypeEnum;

    /**
     * Operator, required
     */
    @NotNull
    @UserInfoFormat
    private String operator;

    /**
     * Specific reason for skipping, required
     */
    @NotNull
    @StringLength(min = 4, max = 4096)
    private String skipComments;

    /**
     * Whether to ignore the entire trace
     */
    private boolean traceIgnore = false;

}
