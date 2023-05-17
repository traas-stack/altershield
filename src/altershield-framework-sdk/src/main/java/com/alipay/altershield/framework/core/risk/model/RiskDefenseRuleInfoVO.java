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

import lombok.Data;

import java.util.Date;

@Data
public class RiskDefenseRuleInfoVO {
    /**
     * Name of risk defense rule
     */
    private String ruleName;

    /**
     * Description of risk defense rule
     */
    private String description;

    /**
     * Identifier of control platform to which the risk defense rule belongs: inspect.
     */
    private String controlKey;

    /**
     * Name of control platform to which the risk defense rule belongs: inspection platform
     */
    private String controlName;

    /**
     * Risk level
     */
    private String riskLevel;
    /**
     * Owner who configured the rule, domain account
     */
    private String owner;
    /**
     * Verification stage: pre- / post-stage
     */
    private String stage;
    /**
     * Rule parameter
     */
    private String ruleParam;

    /**
     * Last update time of rule
     */
    private Date gmtModified;

}
