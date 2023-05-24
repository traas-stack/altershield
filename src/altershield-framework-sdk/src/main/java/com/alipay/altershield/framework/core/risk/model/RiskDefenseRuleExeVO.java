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

import java.util.Date;
import java.util.List;

/**
 * 防御规则执行记录
 *
 * @author yuefan.wyf
 * @version $Id: RiskDefenseRuleExeVO.java, v 0.1 2019年05月31日 上午11:36 yuefan.wyf Exp $
 */
public class RiskDefenseRuleExeVO extends RiskDefenseRuleInfoVO {
    /**
     * Maximum JSON size.
     */
    public static final int MAX_RESULT_JSN_SIZE = 4096;

    public static final String DEFAULT_JSN = " {\"msg\":\"Result JSON Too Large, ignored.\"}";

    /**
     * Execution ID of risk rule
     */
    private String ruleExeId;

    /**
     * Execution status of rule. When equal to SUCCESS, it indicates successful execution. Verdict
     * is only meaningful when execution is successful.
     */
    private String executeStatus;

    /**
     * Start time of rule execution.
     */
    private Date startTime;

    /**
     * Actual execution time of rule. This field is only meaningful when the rule execution is
     * complete; at other times, it is equal to -1.
     */
    private long consume = -1;

    /**
     * Estimated total execution time of rule. Calculated based on historical rule execution time.
     * If not executed before, it is equal to -1.
     */
    private long planConsume = -1;

    /**
     * Handling suggestions for rule execution failure.
     */
    private String suggestion;

    /**
     * Verification result
     */
    private String verdict;

    /**
     * Verification result in JSON format
     */
    private String resultJsn;

    /**
     * URL of rule verification result
     */
    private String url;

    /**
     * Verification message of rule.
     */
    private String msg;

    /**
     * Whether to allow skipping
     */
    private boolean allowIgnore;

    /**
     * Comprehensive judgment of whether to skip, whether execution is successful, whether to block,
     * and whether the rule intercepts the change execution after gray scale.
     */
    private boolean blocked;

    /**
     * Whether it is ignored [that is, skipped]
     */
    private boolean ignored = false;

    /**
     * Type of defense line
     */
    private String defenseType;

    /**
     * Related ID of rule
     */
    private String relationId;

    /**
     * ID of risk defense verification
     */
    private String checkId;

    /**
     * Start execution time
     */
    private Date gmtStart;

    /**
     * End execution time
     */
    private Date gmtFinish;

    /**
     * Planned execution time
     */
    private Date gmtPlan;

    /**
     * Information about the rule administrator
     */
    private List<OwnerInfo> ownerInfos;

    /**
     * Feedback status
     */
    private String feedbackStatus;

    /**
     * Feedback reason type
     */
    private String feedbackReasonType;

    /**
     * Feedback reason
     */
    private String feedbackReason;

}
