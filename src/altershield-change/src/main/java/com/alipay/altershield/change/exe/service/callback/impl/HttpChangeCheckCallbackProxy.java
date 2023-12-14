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
package com.alipay.altershield.change.exe.service.callback.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alibaba.fastjson.JSON;
import com.alipay.altershield.change.exe.service.callback.ChangeCheckCallbackProxy;
import com.alipay.altershield.change.exe.service.callback.ChangeCheckCallbackProxyModel;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeCheckCallbackWrapperRequest;
import com.alipay.altershield.framework.core.change.facade.callback.result.ChangeCheckCallbackResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpChangeCheckCallbackProxy implements ChangeCheckCallbackProxy {

    @Autowired
    private HttpAlterShieldClient httpAlterShieldClient;

    private static Logger logger = Loggers.OUTER_TASK;

    @Override
    public ChangeCheckCallbackResult doCallback(ChangeCheckCallbackProxyModel model) {
        ChangeCheckCallbackWrapperRequest request = new ChangeCheckCallbackWrapperRequest(model.getCheckType(),
                model.getRequest());
        long start = System.currentTimeMillis();
        AlterShieldLoggerManager.log("info", logger,
                "start do callback to client",  model.getPlatform(), model.getRequest().getChangeSceneKey(), model.getRequest().getNodeId(),model.getUrl());
        try {
            String result = httpAlterShieldClient.post(model.getPlatform(), model.getToken(), model.getUrl(),
                    JSONUtil.toJSONString(request, false));
            long cost = System.currentTimeMillis() - start;
            AlterShieldLoggerManager.log("info", logger, "start do callback client finish.", cost,
                    " platform:" + model.getPlatform() + ",changeSceneKey:" + model.getRequest().getChangeSceneKey() + ", nodeId=" + model
                            .getRequest().getNodeId(), result);
            return JSON.parseObject(result, ChangeCheckCallbackResult.class);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", logger, "post callback error", e,
                    " platform:" + model.getPlatform() + ",changeSceneKey:" + model.getRequest().getChangeSceneKey() + ", nodeId=" + model
                            .getRequest().getNodeId());
            ChangeCheckCallbackResult result = new ChangeCheckCallbackResult();
            result.setSysErrorCode(AlterShieldInternalErrorCode.SPI_INNER_ERROR.getCode());
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            return result;
        }
    }
}