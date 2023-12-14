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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.web.openapi.change.v1.exe;

import com.alipay.altershield.framework.core.change.facade.ChangeFacade;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yuanji
 * @version : ExeChangeController.java, v 0.1 2022年04月11日 2:23 下午 yuanji Exp $
 */
@Controller
@RequestMapping("/openapi/v1/exe/")
public class ExeChangeController {

    @Autowired
    private ChangeFacade changeFacade;

    /**
     * 提交变更事件，适用于G0
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeEventAction, method = RequestMethod.POST)
    public AlterShieldResult<SubmitChangeEventResult> submitChangeEvent(@Validated @RequestBody ChangeEventRequest request) {
        return changeFacade.submitChangeEvent(request);
    }

    /**
     * 提交变更执行单
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeExcOrderAction, method = RequestMethod.POST)
    public AlterShieldResult<SubmitChangeExeOrderResult> submitChangeExeOrder(@Validated @RequestBody ChangeExecOrderSubmitRequest request) {
        return changeFacade.submitChangeExeOrder(request);
    }



    /**
     * 提交变更单并通知变更开始. 使用于G1场景 如果G1场景，则同步返回检查结果
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitAndStartChangeExcOrderAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeExecOrderSubmitAndStartResult> submitChangeStartNotify(@Validated @RequestBody
                                                                                      ChangeExecOrderSubmitAndStartRequest request) {
        return changeFacade.submitChangeStartNotify(request);
    }

    /**
     * 提交阶段变更开始通知.
     * 工单级别
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeExecOrderStartNotifyAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeNotifySubmitResult> submitOrderChangeStartNotify(@Validated @RequestBody
                                                                                            ChangeExecOrderStartNotifyRequest request) {
        return changeFacade.submitChangeStartNotify(request);
    }



    /**
     * 提交阶段变更开始通知.
     * 批次级别
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeExecBatchStartNotifyAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeNotifySubmitResult> submitBatchChangeStartNotify(@Validated @RequestBody
                                                                                            ChangeExecBatchStartNotifyRequest request) {
        return changeFacade.submitChangeStartNotify(request);
    }


    /**
     * 提交阶段变更开始通知.
     * 动作级别
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeActionStartStartNotifyAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeNotifySubmitResult> submitActionChangeStartNotify(@Validated @RequestBody
                                                                                                      ChangeActionStartNotifyRequest request) {
        return changeFacade.submitChangeStartNotify(request);
    }

    /**
     * 获取变更检查结果
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.retrieveChangeCheckResultAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(@Validated @RequestBody
                                                                                               ChangeRetrieveCheckRequest request) {
        return changeFacade.retrieveChangeCheckResult(request);
    }

    /**
     * 提交变更完成通知
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.submitChangeFinishNotifyAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeNotifySubmitResult> submitChangeFinishNotify(@Validated @RequestBody
                                                                                ChangeFinishNotifyRequest request) {
        return changeFacade.submitChangeFinishNotify(request);
    }

    /**
     * 重新提交变更开始通知
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.resubmitChangeStartNotifyAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeStartNotify(@Validated @RequestBody
                                                                                        ChangeResubmitStartNotifyRequest request) {
        return changeFacade.resubmitChangeStartNotify(request);
    }

    /**
     * 结束变更执行单
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.finishChangeExecOrderAction, method = RequestMethod.POST)
    public AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(@Validated @RequestBody ChangeExecOrderFinishRequest request) {
        return changeFacade.finishChangeExecOrder(request);
    }

    /**
     * 忽略检查结果
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.skipChangeCheckAction, method = RequestMethod.POST)
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(@Validated @RequestBody ChangeSkipCheckRequest request) {
        return changeFacade.skipChangeCheck(request);
    }

}