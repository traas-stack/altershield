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

import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.ChangeFacade;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.core.util.UUIDUtil;
import com.alipay.altershield.framework.sdk.change.client.ChangeInnerClient;
import com.alipay.altershield.framework.sdk.client.AbstractAlterShieldClient;
import com.alipay.altershield.framework.sdk.util.ResultCodeUtil;
import lombok.Setter;

import java.util.Objects;

/**
 * @author yuanji
 * @version : OpsCloudChangeFacadeHttpImpl.java, v 0.1 2022年02月21日 5:06 下午 yuanji Exp $
 */
@Setter
public class ChangeInnerClientImpl extends AbstractAlterShieldClient
        implements ChangeInnerClient {

    private ChangeFacade changeFacade;

    private HeartbeatUtil heartbeatUtil;

    /**
     * 是否隐藏服务端错误 true，当服务端报错的时候，会返回成功信息 false，同步返回错误信息，会阻断变更
     */
    private boolean hiddenServerError = true;

    @Override
    public AlterShieldResult<SubmitChangeEventResult> submitChangeEvent(
            ChangeEventRequest request) {
        return process(request,
                new ChangeClientProcessor<SubmitChangeEventResult>() {

                    @Override
                    public String getRequestId() {
                        return request.getBizExecOrderId();
                    }

                    @Override
                    public String getFakeId() {
                        return null;
                    }

                    @Override
                    public SubmitChangeEventResult fakeResult() {
                        return null;
                    }

                    @Override
                    public AlterShieldResult<SubmitChangeEventResult> process() {
                        return changeFacade.submitChangeEvent(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<SubmitChangeExeOrderResult> submitChangeExeOrder(
            ChangeExecOrderSubmitRequest request) {
        return process(request,
                new ChangeClientProcessor<SubmitChangeExeOrderResult>() {

                    @Override
                    public String getRequestId() {
                        return request.getBizExecOrderId();
                    }

                    @Override
                    public String getFakeId() {
                        return null;
                    }

                    @Override
                    public SubmitChangeExeOrderResult fakeResult() {
                        return null;
                    }

                    @Override
                    public AlterShieldResult<SubmitChangeExeOrderResult> process() {
                        return changeFacade.submitChangeExeOrder(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> submitChangeStartNotify(
            ChangeExecOrderSubmitAndStartRequest request) {
        return process(request,
                new ChangeClientProcessor<ChangeExecOrderSubmitAndStartResult>() {

                    @Override
                    public String getRequestId() {
                        return request.getBizExecOrderId();
                    }

                    @Override
                    public String getFakeId() {
                        return null;
                    }

                    @Override
                    public ChangeExecOrderSubmitAndStartResult fakeResult() {
                        return null;
                    }

                    @Override
                    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> process() {
                        return changeFacade.submitChangeStartNotify(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeStartNotify(
            ChangeStartNotifyRequest request) {

        return process(request,
                new ChangeClientProcessor<ChangeNotifySubmitResult>() {
                    @Override
                    public String getRequestId() {
                        return request.getBizExecOrderId();
                    }

                    @Override
                    public String getFakeId() {
                        return null;
                    }

                    @Override
                    public ChangeNotifySubmitResult fakeResult() {
                        String nodeId = UUIDUtil.genFakeId();
                        return new ChangeNotifySubmitResult(nodeId, false);
                    }

                    @Override
                    public AlterShieldResult<ChangeNotifySubmitResult> process() {
                        return changeFacade.submitChangeStartNotify(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(
            ChangeRetrieveCheckRequest request) {
        return process(request,
                new ChangeClientProcessor<ChangeCheckProgressResult>() {
                    @Override
                    public String getRequestId() {
                        return request.getNodeId();
                    }

                    @Override
                    public String getFakeId() {
                        return request.getNodeId();
                    }

                    @Override
                    public ChangeCheckProgressResult fakeResult() {
                        return new ChangeCheckProgressResult(true,
                                new ChangeCheckVerdict(true));
                    }

                    @Override
                    public AlterShieldResult<ChangeCheckProgressResult> process() {
                        return changeFacade.retrieveChangeCheckResult(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeFinishNotify(
            ChangeFinishNotifyRequest request) {
        return process(request,
                new ChangeClientProcessor<ChangeNotifySubmitResult>() {
                    @Override
                    public String getRequestId() {
                        return request.getNodeId();
                    }

                    @Override
                    public String getFakeId() {
                        return request.getNodeId();
                    }

                    @Override
                    public ChangeNotifySubmitResult fakeResult() {
                        return new ChangeNotifySubmitResult(request.getNodeId(), true);
                    }

                    @Override
                    public AlterShieldResult<ChangeNotifySubmitResult> process() {
                        return changeFacade.submitChangeFinishNotify(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeStartNotify(
            ChangeResubmitStartNotifyRequest request) {

        return process(request,
                new ChangeClientProcessor<ChangeNotifySubmitResult>() {

                    @Override
                    public String getRequestId() {
                        return request.getNodeId();
                    }

                    @Override
                    public String getFakeId() {
                        return request.getNodeId();
                    }

                    @Override
                    public ChangeNotifySubmitResult fakeResult() {
                        return new ChangeNotifySubmitResult(request.getNodeId(), true);
                    }

                    @Override
                    public AlterShieldResult<ChangeNotifySubmitResult> process() {
                        return changeFacade.resubmitChangeStartNotify(request);
                    }
                });

    }

    @Override
    public AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request) {
        return process(request,
                new ChangeClientProcessor<FinishChangeExecOrderResult>() {

                    @Override
                    public String getRequestId() {
                        return request.getBizExecOrderId();
                    }

                    @Override
                    public String getFakeId() {
                        return null;
                    }

                    @Override
                    public FinishChangeExecOrderResult fakeResult() {
                        return null;
                    }

                    @Override
                    public AlterShieldResult<FinishChangeExecOrderResult> process() {
                        return changeFacade.finishChangeExecOrder(request);
                    }
                });
    }

    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(
            ChangeSkipCheckRequest request) {

        return process(request, new ChangeClientProcessor<ChangeSkipCheckResult>() {

            @Override
            public String getRequestId() {
                return request.getNodeId();
            }

            @Override
            public String getFakeId() {
                return request.getNodeId();
            }

            @Override
            public ChangeSkipCheckResult fakeResult() {
                return null;
            }

            @Override
            public AlterShieldResult<ChangeSkipCheckResult> process() {
                return changeFacade.skipChangeCheck(request);
            }
        });
    }

    protected <R, T extends ChangeBaseRequest> AlterShieldResult<R> process(T request,
                                                                         ChangeClientProcessor<R> processor) {

        return doProcess(request, () -> {
            String fakeId = processor.getFakeId();
            if (UUIDUtil.isFakeId(fakeId)) {
                AlterShieldLoggerManager.log("info", LOGGER, "request is fake.,return mock result",
                        request);
                return AlterShieldResult.succeed(fakeId + " is a fake.", processor.fakeResult());
            }

            // 是否关闭
            if (!heartbeatUtil.chngCheckIsEnable()) {
                AlterShieldLoggerManager.log("warn", LOGGER, "change check is not work", request);

                String requestId = processor.getRequestId();
                if (Objects.isNull(requestId)) {
                    throw new RuntimeException("illegal request type " + request);
                }
                AlterShieldLoggerManager.log("warn", LOGGER, "start generate error result");

                return generateErrorResult(AlterShieldResultCodeEnum.SUCCESS.getCode(),
                        requestId + " OpsCloud switch is off.", processor);
            }
            return postRequest(request, processor);
        });

    }

    private <R, T extends ChangeBaseRequest> AlterShieldResult<R> postRequest(T request,
            ChangeClientProcessor<R> processor) {
        AlterShieldLoggerManager.log("info", LOGGER, "post request", request);
        try {
            return processor.process();
        } catch (Throwable e) {
            AlterShieldLoggerManager.log("error", LOGGER, e, "submit request error", request);
            return generateErrorResult(e, processor);
        }
    }

    protected <T, R> AlterShieldResult<R> generateErrorResult(Throwable e,
            ChangeClientProcessor<R> processor) {
        final String code = ResultCodeUtil.generateResultCodeByClientException(e).getCode();
        String message = "Client Side Error: " + e.getMessage();
        return generateErrorResult(code, message, processor);
    }

    protected <T, R> AlterShieldResult<R> generateErrorResult(String code, String message,
            ChangeClientProcessor<R> processor) {
        if (isHiddenServerError()) {
            AlterShieldLoggerManager.log("info", LOGGER, "create fake result");
            AlterShieldResult<R> alterShieldResult = new AlterShieldResult<>();
            alterShieldResult.setSuccess(true);
            alterShieldResult.setDomain(processor.fakeResult());
            alterShieldResult.setMsg(message);
            alterShieldResult.setResultCode(code);
            AlterShieldLoggerManager.log("info", LOGGER, "create fake result", alterShieldResult);
            return alterShieldResult;
        }
        AlterShieldLoggerManager.log("info", LOGGER, "create error result");
        return new AlterShieldResult<>(false, code, message);

    }

    public boolean isHiddenServerError() {
        return hiddenServerError;
    }

}
