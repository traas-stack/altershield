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
package com.alipay.altershield.framework.core.risk.model.check;

import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdictEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ChangeCheckRule implements Serializable {
    private static final long serialVersionUID = -4351062192724206673L;
    /**
     * Execution ID of risk rule
     */
    private String ruleExeId;
    /**
     * Type of defense line
     */
    private String defenseType;

    /**
     * Verification result in Jsn format
     */
    private String resultJsn;

    /**
     * Rule identifier (inspection type, storage inspection rule ID)
     */
    private String externalRuleId;

    /**
     * controlKey
     */
    private String controlKey;

    /**
     * Execution status
     */
    private ChangeCheckStatusEnum status = ChangeCheckStatusEnum.SUCCESS;

    /**
     * Verification status
     */
    private ChangeCheckVerdictEnum verdict = ChangeCheckVerdictEnum.PASS;

    /**
     * Verification message
     */
    private String msg;

    /**
     * Verification rule URL
     */
    private String url;

    /**
     * Associated ID of rule
     */
    private String relationId;

    /**
     * Execution position
     */
    private DefenseStageEnum stage;

    /**
     * Whether it is in gray status
     */
    private boolean grayStatus = true;

    /**
     * Risk level, NO_RISK warning; BLOCK blocking; ROLLBACK direct rollback; RISK_CURE self-healing
     */
    private String riskLevel = "NO_RISK";

    /**
     * Rule name
     */
    private String name;

    /**
     * Rule administrator
     */
    private String owner;

    /**
     * Whether it is allowed to be ignored
     */
    private boolean allowIgnore = true;

    /**
     * Whether it is ignored
     */
    private boolean ignored = false;

    /**
     * Check start time
     */
    private Date gmtStart;
    /**
     * Check end time
     */
    private Date gmtFinish;

}
