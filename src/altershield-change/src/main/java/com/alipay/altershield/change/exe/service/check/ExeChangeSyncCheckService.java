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

import com.alipay.altershield.framework.core.change.facade.request.ChangeEventRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitAndStartRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeExecOrderSubmitAndStartResult;
import com.alipay.altershield.framework.core.change.facade.result.SubmitChangeEventResult;

public interface ExeChangeSyncCheckService {

    /**
     * Start check service ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult<ChangeExecOrderSubmitAndStartResult> startCheckService(ChangeExecOrderSubmitAndStartRequest request);

    /**
     * 变更通知服务
     *
     * @param request the request
     * @return ops cloud result
     */
    AlterShieldResult<SubmitChangeEventResult> startNotifyService(ChangeEventRequest request);

    /**
     * Change finish ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    AlterShieldResult changeFinish(ChangeExecOrderFinishRequest request);

}