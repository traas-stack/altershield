/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender;

import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;

/**
 * asynchronous defender detect plugin spi
 *
 * @author yhaoxuan
 * @version DefenderDetectPlugin.java, v 0.1 2022年08月28日 7:34 下午 yhaoxuan
 */
public interface DefenderAsyncDetectPlugin {

    /**
     * submit detect task
     *
     * @param request entity of the detect request
     * @return result of submit task
     */
    DefenderDetectPluginResult submitDetectTask(DefenderDetectPluginRequest request);

    /**
     * polling to retrieve result of the detect task
     *
     * @param request entity of the detect request
     * @return result of detect task
     */
    DefenderDetectPluginResult retrieveDetectResult(DefenderDetectPluginRequest request);
}