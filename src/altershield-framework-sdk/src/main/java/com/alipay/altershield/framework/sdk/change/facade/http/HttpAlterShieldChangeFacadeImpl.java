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
import com.alipay.altershield.framework.core.change.facade.ChangeFacade;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.sdk.http.BaseHttpAlterShieldFacade;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant.submitChangeEventUri;
import static com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant.*;

/**
 * @author yuanji
 * @version : HttpOpsCloudChangeFacadeImpl.java, v 0.1 2022年02月21日 5:23 下午 yuanji Exp $
 */
@Data
public class HttpAlterShieldChangeFacadeImpl extends BaseHttpAlterShieldFacade
        implements ChangeFacade {


    private Map<String, String> changeStartMapping = new HashMap<>();

    /**
     * 发送变更通知事件
     *
     * @param request
     * @return
     */
    public AlterShieldResult<SubmitChangeEventResult> submitChangeEvent(
            ChangeEventRequest request) {
        return postRequest(submitChangeEventUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<SubmitChangeEventResult>>() {}));

    }

    @Override
    public AlterShieldResult<SubmitChangeExeOrderResult> submitChangeExeOrder(
            ChangeExecOrderSubmitRequest request) {

        return postRequest(submitChangeExcOrderUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<SubmitChangeExeOrderResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> submitChangeStartNotify(
            ChangeExecOrderSubmitAndStartRequest request) {
        return postRequest(submitAndStartChangeExcOrderUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<ChangeExecOrderSubmitAndStartResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeStartNotify(
            ChangeStartNotifyRequest request) {
        return postRequest(changeStartMapping.get(request.getClass().getName()), request,
                s -> JSON.parseObject(s,
                        new TypeReference<AlterShieldResult<ChangeNotifySubmitResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(
            ChangeRetrieveCheckRequest request) {
        return postRequest(retrieveChangeCheckResultUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<ChangeCheckProgressResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeFinishNotify(
            ChangeFinishNotifyRequest request) {
        return postRequest(submitChangeFinishNotifyUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<ChangeNotifySubmitResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeStartNotify(
            ChangeResubmitStartNotifyRequest request) {

        return postRequest(resubmitChangeStartNotifyUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<ChangeNotifySubmitResult>>() {}));
    }

    @Override
    public AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request) {
        return postRequest(finishChangeExecOrderUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<FinishChangeExecOrderResult>>() {}));
    }

    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(
            ChangeSkipCheckRequest request) {
        return postRequest(skipChangeCheckActionUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<ChangeSkipCheckResult>>() {}));
    }



    public void init() {
        changeStartMapping.put(ChangeExecBatchStartNotifyRequest.class.getName(),
                submitChangeExecBatchStartStartNotifyUri);
        changeStartMapping.put(ChangeExecOrderStartNotifyRequest.class.getName(),
                submitChangeExecOrderStartStartNotifyUri);
        changeStartMapping.put(ChangeActionStartNotifyRequest.class.getName(),
                submitChangeActionStartStartNotifyUri);

    }
}
