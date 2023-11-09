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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.opscloud.change.exe.service.check.ExeChangeAsyncCheckService;
import com.alipay.opscloud.change.exe.service.check.OpscloudExeChangeAsyncCheckService;
import com.alipay.opscloud.change.exe.service.check.model.OpsCloudChangeStartNotifyRequestModel;
import com.alipay.opscloud.framework.core.change.facade.request.*;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeCheckProgressResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeNotifySubmitResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 新变更核心异步纯管控
 *
 * @author yuanji
 * @version : ExeChangeAsyncCheckServiceImpl.java, v 0.1 2022年11月03日 16:37 yuanji Exp $
 */
public class ExeChangeAsyncCheckServiceImpl implements ExeChangeAsyncCheckService {

    @Autowired
    private OpscloudExeChangeAsyncCheckService opscloudExeChangeAsyncCheckService;

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> startPreCheck(OpsCloudChangeStartNotifyRequestModel requestModel) {
        return opscloudExeChangeAsyncCheckService.startPreCheck(requestModel);
    }

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> starPostCheck(OpsCloudChangeFinishNotifyRequest request) {
        return opscloudExeChangeAsyncCheckService.starPostCheck(request);
    }

    @Override
    public OpsCloudResult<OpsCloudChangeCheckProgressResult> retrieveChangeCheckResult(OpsCloudChangeRetrieveCheckRequest request) {
        return opscloudExeChangeAsyncCheckService.retrieveChangeCheckResult(request);
    }

    @Override
    public OpsCloudResult<OpsCloudChangeNotifySubmitResult> resubmitChangeCheck(OpsCloudChangeResumbitStartNotifyRequest request) {
        return opscloudExeChangeAsyncCheckService.resubmitChangeCheck(request);
    }

    @Override
    public OpsCloudResult<OpsCloudChangeSkipCheckResult> skipChangeCheck(OpsCloudChangeSkipCheckRequest request) {
        return opscloudExeChangeAsyncCheckService.skipChangeCheck(request);
    }

}
