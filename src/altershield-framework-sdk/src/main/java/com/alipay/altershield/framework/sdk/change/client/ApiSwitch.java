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
package com.alipay.altershield.framework.sdk.change.client;

import com.alipay.altershield.framework.core.change.facade.request.ApiSwitchRequest;
import com.alipay.altershield.framework.core.change.facade.result.ApiSwitchResult;

/**
 *
 * @author yuanji
 * @version : OpsCloudApiSwitch.java, v 0.1 2022年09月06日 15:14 yuanji Exp $
 */
public interface ApiSwitch {

    /**
     * 是否能够调用opscloud服务
     *
     * @param request
     * @return never nullC
     */
    ApiSwitchResult isAlterShieldEnable(ApiSwitchRequest request);

    /**
     * 获取当前心跳总开关的状态
     *
     * @return property value of isHeartBeatOn
     */
    boolean isHeartBeatOn();

    /**
     * 设置当前心跳总开关的状态
     *
     * @param heartBeatOn value to be assigned to property isHeartBeatOn
     */
    void setHeartBeatOn(boolean heartBeatOn);
}
