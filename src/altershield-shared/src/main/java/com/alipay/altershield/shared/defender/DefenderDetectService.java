/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender;

import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.shared.defender.request.DefenderDetectRequest;
import com.alipay.altershield.shared.defender.result.DefenderDetectResult;
import com.alipay.altershield.shared.defender.result.DefenderDetectSubmitResult;

/**
 * The main process service that initiates defender detection
 *
 * @author yhaoxuan
 * @version OpsCloudDefenderService.java, v 0.1 2022年08月08日 4:10 下午 yhaoxuan
 */
public interface DefenderDetectService {

    /**
     * Synchronous execution of change defense detection entry - only available for pre defense stage in G1 generation
     * Note: The defense rules for implementing asynchronous defense SPI are not executed in synchronous defense.
     *
     * @param request Change defense detection request structure
     * @return Change defense detection result structure
     */
    AlterShieldResult<DefenderDetectResult> syncDetect(DefenderDetectRequest request);

    /**
     * Asynchronous execution of change defense detection entry - used by G1 post, G2, G3, G4 generation
     *
     * @param request Change defense verification request structure
     * @return Change defense detection task submission results
     */
    AlterShieldResult<DefenderDetectSubmitResult> asyncDetect(DefenderDetectRequest request);
}