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

import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitAndStartRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipStartCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeExecOrderSubmitAndStartResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;
import com.alipay.altershield.framework.core.change.facade.result.FinishChangeExecOrderResult;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.framework.sdk.change.client.ChangeInnerClient;
import com.alipay.altershield.framework.sdk.change.client.SimpleChangeClient;
import lombok.Data;

/**
 *
 * @author yuanji
 * @version : OpsCloudSimpleChangeClientImpl.java, v 0.1 2022年02月18日 11:03 上午 yuanji Exp $
 */
@Data
public class SimpleChangeClientImpl implements SimpleChangeClient {

    private ChangeInnerClient changeInnerClient;


    @Override
    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> submitChangeStartNotify(
            ChangeExecOrderSubmitAndStartRequest request) {
        return changeInnerClient.submitChangeStartNotify(request);
    }

    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(
            ChangeSkipStartCheckRequest request) {
        ChangeSkipCheckRequest changeSkipCheckRequest =
                new ChangeSkipCheckRequest();
        changeSkipCheckRequest.setFeedbackVOList(request.getFeedbackVOList());
        changeSkipCheckRequest.setNodeId(request.getNodeId());
        changeSkipCheckRequest.setDefenseStage(DefenseStageEnum.PRE);
        changeSkipCheckRequest.setBizExecOrderId(request.getBizExecOrderId());
        changeSkipCheckRequest.setTenantCode(request.getTenantCode());
        changeSkipCheckRequest.setPlatform(request.getPlatform());
        changeSkipCheckRequest.setChangeSceneKey(request.getChangeSceneKey());
        return changeInnerClient.skipChangeCheck(changeSkipCheckRequest);
    }

    @Override
    public AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request) {
        return changeInnerClient.finishChangeExecOrder(request);
    }
}
