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
package com.alipay.altershield.change.exe.service.check;

import com.alipay.altershield.change.exe.service.check.model.ChangeStartNotifyRequestModel;
import com.alipay.altershield.framework.core.change.facade.request.ChangeFinishNotifyRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeResubmitStartNotifyRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeRetrieveCheckRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckProgressResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeNotifySubmitResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;

public interface ExeChangeAsyncCheckService {

    /**
     * Start pre check ops cloud result.
     *
     * @param requestModel the request model
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeNotifySubmitResult> startPreCheck(ChangeStartNotifyRequestModel requestModel);

    /**
     * Star post check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeNotifySubmitResult> starPostCheck(ChangeFinishNotifyRequest request);

    /**
     * Retrieve change check result ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(ChangeRetrieveCheckRequest request);

    /**
     * Resubmit change check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeCheck(ChangeResubmitStartNotifyRequest request);

    /**
     * Skip change check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipCheckRequest request);
}