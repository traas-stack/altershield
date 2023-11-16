/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.pluginmarket.innerplugin.defender;

import com.alipay.altershield.spi.defender.DefenderSyncDetectPlugin;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;

/**
 * Forever True/Forever False Plugin
 *
 * @author yhaoxuan
 * @version AssertPlugin.java, v 0.1 2022年09月19日 6:03 下午 yhaoxuan
 */
public class AssertPlugin implements DefenderSyncDetectPlugin {

    @Override
    public DefenderDetectPluginResult detect(DefenderDetectPluginRequest request) {
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setStatus(DefenderStatusEnum.FAIL);
        result.setMsg("test");
        result.setResultJson("{\"test\": \"helloWord\"}");
        return result;
    }
}