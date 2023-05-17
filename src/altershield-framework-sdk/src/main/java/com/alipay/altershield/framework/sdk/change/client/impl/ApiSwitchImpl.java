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
package com.alipay.altershield.framework.sdk.change.client.impl;

import com.alipay.altershield.framework.common.util.LogUtil;
import com.alipay.altershield.framework.core.change.facade.request.ApiSwitchRequest;
import com.alipay.altershield.framework.core.change.facade.result.ApiSwitchResult;
import com.alipay.altershield.framework.sdk.change.client.ApiSwitch;

import com.alipay.altershield.framework.sdk.util.ClientLoggers;
import lombok.Data;
import org.slf4j.Logger;
import org.springframework.util.Assert;

/**
 *
 * @author yuanji
 * @version : OpsCloudApiSwitchImpl.java, v 0.1 2022年09月06日 15:20 yuanji Exp $
 */
@Data
public class ApiSwitchImpl implements ApiSwitch {

    private HeartbeatUtil heartbeatUtil;
    private static final Logger LOGGER = ClientLoggers.LOGGER;


    public void init() throws Exception {
        Assert.notNull(heartbeatUtil, "heartbeatUtil is not config");
    }

    @Override
    public ApiSwitchResult isAlterShieldEnable(ApiSwitchRequest request) {
        ApiSwitchResult temp = null;
        try {

            boolean enable = heartbeatUtil.isAlterShieldEnable(request);
            return temp = new ApiSwitchResult(enable);
        } finally {
            LOGGER.info(LogUtil.formatTrace("OpsCloudApiSwitch#isOpscloudEnable", request, temp));
        }
    }

    @Override
    public boolean isHeartBeatOn() {
        return heartbeatUtil.isHeartBeatOn();
    }

    @Override
    public void setHeartBeatOn(boolean heartBeatOn) {
        heartbeatUtil.setHeartBeatOn(heartBeatOn);
    }
}
