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
package com.alipay.altershield.framework.core.change.facade.request;

/**
 * 创建change step类 只创建order和batch级别
 *
 * @author yuanji
 * @version : CreateMetaChangeStepRequest.java, v 0.1 2022年03月17日 5:20 下午 yuanji Exp $
 */
public class CreateMetaChangeStepRequest extends BaseMetaChangeStepRequest {

    /**
     * Instantiates a new Create meta change step request.
     *
     * @param enablePreCheck   是否支持前置校验
     * @param preCheckTimeout  前置校验超时时间 单位:ms
     * @param enablePostCheck  是否支持后置校验
     * @param postCheckTimeout 后置校验超时时间 单位:ms
     */
    public CreateMetaChangeStepRequest(boolean enablePreCheck, long preCheckTimeout, boolean enablePostCheck, long postCheckTimeout) {
        super(enablePreCheck, preCheckTimeout, enablePostCheck, postCheckTimeout);
    }

    /**
     * Instantiates a new Create meta change step request.
     */
    public CreateMetaChangeStepRequest() {
    }
}