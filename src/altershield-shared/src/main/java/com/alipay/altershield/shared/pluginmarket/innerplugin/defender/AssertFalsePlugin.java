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
package com.alipay.altershield.shared.pluginmarket.innerplugin.defender;

import com.alipay.altershield.spi.defender.DefenderSyncDetectPlugin;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;

/**
 *Forever False Plugin
 *
 * @author yhaoxuan
 * @version AssertPlugin.java, v 0.1 2022年09月19日 6:03 下午 yhaoxuan
 */
public class AssertFalsePlugin implements DefenderSyncDetectPlugin {

    @Override
    public DefenderDetectPluginResult detect(DefenderDetectPluginRequest request) {
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setStatus(DefenderStatusEnum.FAIL);
        result.setMsg("altershield assert plugin");
        result.setResultJson("{\"assert\": \"false\"}");
        return result;
    }
}