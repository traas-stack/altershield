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
package com.alipay.altershield.framework.sdk.http;

import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.sdk.util.ClientLoggers;
import org.slf4j.Logger;

import java.util.function.Function;

/**
 * The type Base http ops cloud facade.
 *
 * @author yuanji
 * @version : BaseHttpOpsCloudFacade.java, v 0.1 2022年05月10日 11:49 上午 yuanji Exp $
 */
public abstract class BaseHttpAlterShieldFacade {

    private HttpAlterShieldClient httpAlterShieldClient;

    /**
     * The constant logger.
     */
    protected static final Logger logger = ClientLoggers.LOGGER;

    /**
     * Sets http ops cloud client.
     *
     * @param httpAlterShieldClient the http ops cloud client
     */
    public void setHttpAlterShieldClient(HttpAlterShieldClient httpAlterShieldClient) {
        this.httpAlterShieldClient = httpAlterShieldClient;
    }

    /**
     * Post request r.
     *
     * @param <R> the type parameter
     * @param <T> the type parameter
     * @param action the action
     * @param request the request
     * @param function the function
     * @return the r
     */
    protected <R, T> R postRequest(String action, T request, Function<String, R> function) {
        String requestJson = JSONUtil.toJSONString(request, false);
        AlterShieldLoggerManager.log("debug", logger, "start post request ", action);
        AlterShieldLoggerManager.log("debug", logger, requestJson);
        final String response = httpAlterShieldClient.post(action, requestJson);
        AlterShieldLoggerManager.log("debug", logger, "get post result");
        AlterShieldLoggerManager.log("debug", logger, response);
        return function.apply(response);

    }

}
