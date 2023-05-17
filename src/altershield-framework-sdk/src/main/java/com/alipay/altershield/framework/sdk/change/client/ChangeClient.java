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
package com.alipay.altershield.framework.sdk.change.client;

import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.facade.result.*;

/**
 *
 * 标准的变更客户端，使用于G2,G3,G4变更场景
 *
 * @author
 * @version : OpsCloudChangeClient.java, v 0.1 2022年02月16日 10:45 上午 yuanji Exp $
 */
public interface ChangeClient {


    /**
     * 提交变更执行单
     *
     * @param request
     * @return
     */
    AlterShieldResult<SubmitChangeExeOrderResult> submitChangeExecOrder(
            ChangeExecOrderSubmitRequest request);

    /**
     * 提交阶段变更开始通知. 变更执行单开始通知 {@link ChangeExecOrderStartNotifyRequest} 变更批次执行开始通知
     * {@link ChangeExecBatchStartNotifyRequest} 变更动作执行开始通知 {@link ChangeActionStartNotifyRequest}
     *
     * @param request
     */
    AlterShieldResult<ChangeNotifySubmitResult> submitChangeStartNotify(
            ChangeStartNotifyRequest request);

    /**
     * 获取变更检查结果 所有阶段都可以使用这个方法获取校验结果
     *
     * @param request
     */
    AlterShieldResult<ChangeCheckProgressResult> retrieveChangeCheckResult(
            ChangeRetrieveCheckRequest request);

    /**
     * 提交变更完成通知
     *
     * @param request
     */
    AlterShieldResult<ChangeNotifySubmitResult> submitChangeFinishNotify(
            ChangeFinishNotifyRequest request);

    /**
     * 重新提交变更开始通知
     *
     * @param request
     */
    AlterShieldResult<ChangeNotifySubmitResult> resubmitChangeStartNotify(
            ChangeResubmitStartNotifyRequest request);

    /**
     * 结束变更执行单
     *
     * @param request
     * @return
     */
    AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request);

    /**
     * 忽略检查结果
     *
     * @param request
     * @return
     */
    AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipCheckRequest request);


}
