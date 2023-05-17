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

import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitAndStartRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipStartCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.*;

/**
 * 简单变更客户端，使用于G0,G1的变更场景
 *
 * @author yuanji
 * @version : OpsCloudSimpleChangeClient.java, v 0.1 2022年02月18日 10:50 上午 yuanji Exp $
 */
public interface SimpleChangeClient {

    /**
     * 提交阶段变更开始通知.并同步返回检查结果 如果返回对象 {@link ChangeNotifySubmitResult#isSkipCheck()} 为true。则不需要检查防御结果
     *
     * @param request
     */
    AlterShieldResult<ChangeExecOrderSubmitAndStartResult> submitChangeStartNotify(
            ChangeExecOrderSubmitAndStartRequest request);


    /**
     * 忽略检查结果
     *
     * @param request
     * @return
     */
    AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipStartCheckRequest request);


    /**
     * 变更单执行
     *
     * @param request
     * @return
     */
    AlterShieldResult<FinishChangeExecOrderResult> finishChangeExecOrder(
            ChangeExecOrderFinishRequest request);


}
