/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.defender.result.DefenderTaskResult;

/**
 * Defender tasks status checker
 *
 * @author yhaoxuan
 * @version DefenderTaskChecker.java, v 0.1 2022年08月29日 9:18 下午 yhaoxuan
 */
public interface DefenderTaskChecker {

    /**
     * Check the execution status of all detection tasks pre/post once and combine the final results
     *
     * @param changeOrderId change order id
     * @param nodeId change node id
     * @param detectGroupId detect group id
     * @return Task scheduling results
     */
    DefenderTaskResult checkDetectProcess(String changeOrderId, String nodeId, String detectGroupId, DefenseStageEnum stage,
                                          String changeSceneKey, MetaChangeStepTypeEnum changeStepType);
}