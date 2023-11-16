/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender;

import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;

/**
 * synchronous defender detect task
 *
 * @author yhaoxuan
 * @version DefenderSyncDetectPlugin.java, v 0.1 2022年09月19日 7:45 下午 yhaoxuan
 */
public interface DefenderSyncDetectPlugin {

    /**
     * synchronous invoke the detect task
     *
     * @param request entity of detect request
     * @return result of detect task
     */
    DefenderDetectPluginResult detect(DefenderDetectPluginRequest request);
}