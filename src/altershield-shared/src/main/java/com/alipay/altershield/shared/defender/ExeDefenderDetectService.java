/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender;

import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckRule;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.request.DefenderDetectBatchFeedbackRequest;

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
     * Batch feedback
     *
     * @param request Batch feedback request structure
     * @return Feedback is successful
     */
    Boolean batchFeedback(DefenderDetectBatchFeedbackRequest request);
}