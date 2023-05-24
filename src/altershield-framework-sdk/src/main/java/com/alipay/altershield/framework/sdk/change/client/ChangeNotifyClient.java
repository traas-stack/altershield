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

import com.alipay.altershield.framework.core.change.facade.request.ChangeEventRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.SubmitChangeEventResult;

import java.util.function.Consumer;

/**
 * 变更通知客户端 仅同步变更内容到变更核心。
 *
 * @author yuanji
 * @version : OpsCloudChangeNotifyClient.java, v 0.1 2022年03月07日 4:57 下午 yuanji Exp $
 */
public interface ChangeNotifyClient {

    /**
     * 发送变更通知事件
     *
     * @param request
     * @return
     */
    AlterShieldResult<SubmitChangeEventResult> submitChangeEvent(ChangeEventRequest request);

    /**
     * 异步提交变更事件 需要业务方确保参数正确
     *
     * @param request
     * @param consumer 如果不为null，则回调这个类
     */
    void asyncSubmitChangeEvent(ChangeEventRequest request,
            Consumer<AlterShieldResult<SubmitChangeEventResult>> consumer);

}
