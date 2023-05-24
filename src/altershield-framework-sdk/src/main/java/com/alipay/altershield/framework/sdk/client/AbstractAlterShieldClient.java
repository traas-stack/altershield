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
package com.alipay.altershield.framework.sdk.client;

import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.sdk.util.ClientLoggers;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * The type Abstract ops cloud client.
 *
 * @author yuanji
 * @version : AbstractOpsCloudClient.java, v 0.1 2022年06月10日 17:13 yuanji Exp $
 */
public abstract class AbstractAlterShieldClient {


    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = ClientLoggers.LOGGER;


    /**
     * Do process ops cloud result.
     *
     * @param <R> the type parameter
     * @param <T> the type parameter
     * @param request the request
     * @param supplier the supplier
     * @return the ops cloud result
     */
    protected <R, T> AlterShieldResult<R> doProcess(T request, Supplier<AlterShieldResult<R>> supplier) {
        AlterShieldLoggerManager.log("debug", LOGGER, "start process request", request);

        return supplier.get();
    }
}
