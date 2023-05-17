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
package com.alipay.altershield.framework.core.change.facade.callback;

import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeActionCheckCallbackRequest;
import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeCheckCallbackRequest;
import com.alipay.altershield.framework.core.change.facade.callback.result.ChangeCheckCallbackResult;

/**
 * 变更防御结果回调接口 变更方可以根据接入情况，选择实现相关的方法 业务侧需要处理重复调用的情况
 *
 * @author shuo.qius
 * @version Apr 3, 2019
 */
public interface ChangeCheckCallback {

    /**
     * 工单级别前置校验callback
     *
     * @param request
     * @return
     */
    ChangeCheckCallbackResult orderChangeCheckCallback(ChangeCheckCallbackRequest request);

    /**
     * 批次级别校验callback
     *
     * @param request
     * @return
     */
    ChangeCheckCallbackResult batchChangeCheckCallback(ChangeCheckCallbackRequest request);

    /**
     * 变更动作级别校验callback
     *
     * @param request
     * @return
     */
    ChangeCheckCallbackResult actionChangeCheckCallback(ChangeActionCheckCallbackRequest request);

}
