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

import com.alipay.altershield.framework.core.change.facade.request.ChangeEventRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.SubmitChangeEventResult;
import com.alipay.altershield.framework.sdk.change.client.ChangeInnerClient;
import com.alipay.altershield.framework.sdk.change.client.ChangeNotifyClient;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The type Ops cloud change notify client.
 *
 * @author yuanji
 * @version : OpsCloudChangeNotifyClientImpl.java, v 0.1 2022年03月07日 5:14 下午 yuanji Exp $
 */
@Data
public class ChangeNotifyClientImpl implements ChangeNotifyClient {

    private ChangeInnerClient changeInnerClient;

    private ExecutorService executorService;

    private int threadSize = 5;

    private int threadPoolSize = 100;

    @Override
    public AlterShieldResult<SubmitChangeEventResult> submitChangeEvent(
            ChangeEventRequest request) {
        return changeInnerClient.submitChangeEvent(request);
    }

    @Override
    public void asyncSubmitChangeEvent(ChangeEventRequest request,
            Consumer<AlterShieldResult<SubmitChangeEventResult>> consumer) {

        Assert.notNull(executorService, "executor service is null, init it ,try later");
        executorService.submit(() -> {
            AlterShieldResult<SubmitChangeEventResult> opsCloudResult =
                    submitChangeEvent(request);
            if (consumer != null) {
                consumer.accept(opsCloudResult);
            }
        });
    }


    /**
     * Init.
     */
    public synchronized void init() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(threadSize, threadSize, 1, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(threadPoolSize),
                    r -> new Thread(r, "opscloud-change-notify-client-thread"));
        }
    }

    /**
     * Destroy.
     */
    public synchronized void destroy() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

}
