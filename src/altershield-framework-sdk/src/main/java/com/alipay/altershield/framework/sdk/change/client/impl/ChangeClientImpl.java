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

import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.sdk.change.client.ChangeClient;
import com.alipay.altershield.framework.sdk.change.client.ChangeInnerClient;
import lombok.Setter;


/**
 * @author yuanji
 * @version : OpsCloudChangeClientImpl.java, v 0.1 2022年02月16日 10:47 上午 yuanji Exp $
 */
@Setter
public class ChangeClientImpl implements ChangeClient {

    private ChangeInnerClient changeInnerClient;

    @Override
    public AlterShieldResult<SubmitChangeExeOrderResult> submitChangeExecOrder(
            ChangeExecOrderSubmitRequest request) {
        return changeInnerClient.submitChangeExeOrder(request);
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeStartNotify(
            ChangeStartNotifyRequest request) {
        return changeInnerClient.submitChangeStartNotify(request);
    }

    @Override
    public AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(
            ChangeRetrieveCheckRequest request) {
        return changeInnerClient.retrieveChangeCheckResult(request);
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeFinishNotify(
            ChangeFinishNotifyRequest request) {
        return changeInnerClient.submitChangeFinishNotify(request);
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeStartNotify(
            ChangeResubmitStartNotifyRequest request) {
        return changeInnerClient.resubmitChangeStartNotify(request);
    }

    @Override
    public AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request) {
        return changeInnerClient.finishChangeExecOrder(request);
    }

    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(
            ChangeSkipCheckRequest request) {
        return changeInnerClient.skipChangeCheck(request);
    }
}
