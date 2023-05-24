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
package com.alipay.altershield.framework.sdk.change.facade.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.core.change.facade.HeartbeatCheck;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lex
 * @version $Id: HttpHeartbeatCheckClient.java, v 0.1 2019年10月18日 上午10:54 lex Exp $
 */
@Data
public class HttpHeartbeatCheckClientImpl implements HeartbeatCheck {

    private HttpAlterShieldClient httpAlterShieldClient;
    public void setHttpOpsCloudClient(HttpAlterShieldClient httpAlterShieldClient) {
        this.httpAlterShieldClient = httpAlterShieldClient;
    }

    /**
     *
     * @return
     */
    @Override
    public AlterShieldResult<Map<String, String>> doCheck(String platform) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("platform", platform);
        final String response =
                httpAlterShieldClient.get(AlterShieldApiConstant.heartbeatConfigUri, uriVariables);
        return JSON.parseObject(response,
                new TypeReference<AlterShieldResult<Map<String, String>>>() {});
    }

    @Override
    public AlterShieldResult<Map<String, String>> doCheck() {
        throw new UnsupportedOperationException("config() not supported.");
    }

}
