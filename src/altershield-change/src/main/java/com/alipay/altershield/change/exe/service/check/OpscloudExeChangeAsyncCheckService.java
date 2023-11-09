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

import com.alipay.opscloud.change.exe.service.check.model.OpsCloudChangeStartNotifyRequestModel;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeFinishNotifyRequest;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeResumbitStartNotifyRequest;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeRetrieveCheckRequest;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeSkipCheckRequest;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeCheckProgressResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeNotifySubmitResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;

/**
 * @author zyt
 * @date 2023年2月2日 下午8:36:20
 */
public interface OpscloudExeChangeAsyncCheckService {

    /**
     * Start pre check ops cloud result.
     *
     * @param requestModel the request model
     * @return the ops cloud result
     */
    OpsCloudResult<OpsCloudChangeNotifySubmitResult> startPreCheck(OpsCloudChangeStartNotifyRequestModel requestModel);

    /**
     * Star post check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    OpsCloudResult<OpsCloudChangeNotifySubmitResult> starPostCheck(OpsCloudChangeFinishNotifyRequest request);

    /**
     * Retrieve change check result ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    OpsCloudResult<OpsCloudChangeCheckProgressResult> retrieveChangeCheckResult(OpsCloudChangeRetrieveCheckRequest request);

    /**
     * Resubmit change check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    OpsCloudResult<OpsCloudChangeNotifySubmitResult> resubmitChangeCheck(OpsCloudChangeResumbitStartNotifyRequest request);

    /**
     * Skip change check ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    OpsCloudResult<OpsCloudChangeSkipCheckResult> skipChangeCheck(OpsCloudChangeSkipCheckRequest request);
}
