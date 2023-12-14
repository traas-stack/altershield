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
/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender;

import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckRule;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.request.DefenderDetectBatchFeedbackRequest;
import com.alipay.altershield.shared.defender.result.DefenderDetectResult;

import java.util.List;

/**
 * Provide change matching to rule query service and ignore service
 *
 * @author fujinfei
 * @version DefenderDetectQueryService.java, v 0.1 2022年10月19日 15:51 fujinfei
 */
public interface ExeDefenderDetectService {

    /**
     * Query the list of pre and post rules by change order id
     *
     * @param orderId change order id
     * @return rule list
     */
    List<ExeDefenderDetectEntity> queryDetectInfoByOrderId(String orderId);

    /**
     * Query the list of pre and post rules by node id
     *
     * @param nodeId nodeId
     * @param stageEnum PRE/POST
     * @return rule list
     */
    List<ExeDefenderDetectEntity> queryDetectInfoByNodeId(String nodeId, DefenseStageEnum stageEnum);

    /**
     * Query the execution status of the defense rules by node id and pre or post.
     *
     * @param nodeId nodeId
     * @param stageEnum PRE/POST
     * @return rule list
     */
    List<ChangeCheckRule> queryDetectStatusByNodeId(String nodeId, DefenseStageEnum stageEnum);

    /**
     * Skip change defense checks
     *
     * @param request skip request
     * @return skip result
     */
    AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipCheckRequest request);

    /**
     * Convert 2 verdict result change check verdict.
     *
     * @param defenderDetectResult the defender detect result
     * @param emergency            the emergency
     * @return the change check verdict
     */
    ChangeCheckVerdict convert2VerdictResult(AlterShieldResult<DefenderDetectResult> defenderDetectResult, boolean emergency);
}