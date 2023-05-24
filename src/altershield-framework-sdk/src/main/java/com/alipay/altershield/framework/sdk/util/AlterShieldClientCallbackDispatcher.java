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
package com.alipay.altershield.framework.sdk.util;

import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.SignUtil;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.callback.ChangeCheckCallback;
import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeActionCheckCallbackRequest;
import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeCheckCallbackWrapperRequest;
import com.alipay.altershield.framework.core.change.facade.callback.result.ChangeCheckCallbackResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于http的client的 回调分发
 *
 * @author yuanji
 * @version : OpsCloudClientCallbackDispatcher.java, v 0.1 2022年03月10日 11:33 上午 yuanji Exp $
 */
public class AlterShieldClientCallbackDispatcher {

    private static final Logger logger = ClientLoggers.LOGGER;

    /**
     * 回调类实现
     */
    private ChangeCheckCallback changeCheckCallback;
    /**
     * 变更平台token
     */
    private String token;

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sets ops cloud change check callback.
     *
     * @param changeCheckCallback the ops cloud change check callback
     */
    public void setChangeCheckCallback(
            ChangeCheckCallback changeCheckCallback) {
        this.changeCheckCallback = changeCheckCallback;
    }

    /**
     * Dispatch.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the io exception
     */
    public void dispatch(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        AlterShieldLoggerManager.log("debug", logger, "start dispatch change check callback");
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new IllegalArgumentException(
                    "http request mus be post method, actual is " + request.getMethod());
        }
        String json = getRequestPostStr(request);
        AlterShieldLoggerManager.log("debug", logger, "callback data :", json);

        ChangeCheckCallbackResult result;

        result = validate(request, json);
        if (result == null) {
            try {
                ChangeCheckCallbackWrapperRequest opsCloudChangeCheckCallbackWrapperRequest =
                        JSONUtil.parseJSONToObj(json,
                                ChangeCheckCallbackWrapperRequest.class);
                result = doDispatch(opsCloudChangeCheckCallbackWrapperRequest);
            } catch (AlterShieldInternalException e) {
                result = new ChangeCheckCallbackResult();
                result.setMsg(e.getMessage());
                result.setRetry(false);
                result.setSuccess(false);
                result.setSysErrorCode(e.getErrorCode().getCode());
            }
        }
        writeResponse(response, result);
    }

    /**
     * 对请求的签名做校验
     *
     * @param request the request
     * @param content the content
     * @return ops cloud change check callback result
     */
    public ChangeCheckCallbackResult validate(HttpServletRequest request, String content) {
        AlterShieldLoggerManager.log("info", logger, "start validate callback request");
        String platform = request.getHeader(HttpAlterShieldClient.H_PLATFORM);
        String signResult = request.getHeader(HttpAlterShieldClient.H_PLATFORM_SIGN);
        String tString = request.getHeader(HttpAlterShieldClient.H_PLATFORM_TIMESTAMP);
        String token = getToken(platform);
        if (StringUtils.isBlank(platform) || StringUtils.isBlank(signResult)
                || StringUtils.isBlank(tString) || StringUtils.isBlank(token)) {
            return new ChangeCheckCallbackResult(false, false,
                    "authentication request fail");
        }
        // TODO 后期 增加重放校验
        long t = Long.parseLong(tString);
        AlterShieldLoggerManager.log("info", logger, "get token from platform" + platform, token);

        try {
            boolean result = SignUtil.verifySign(t, content, signResult, token);
            if (!result) {
                String msg = buildMsg(platform, signResult, t, content);
                AlterShieldLoggerManager.log("warn", logger, msg);
                return new ChangeCheckCallbackResult(false, false,
                        "authentication request fail");
            }
        } catch (Exception e) {
            String msg = buildMsg(platform, signResult, t, content);
            AlterShieldLoggerManager.log("error", logger, e, msg);
            return new ChangeCheckCallbackResult(false, false,
                    "authentication request fail " + e.getMessage());
        }
        AlterShieldLoggerManager.log("info", logger, "validate callback request success");
        return null;
    }

    private String buildMsg(String platform, String signResult, long t, String content) {
        return String.format(
                "check authentication fail, platform=%s, signResult=%s, t =%d, content=%s",
                platform, signResult, t, content);
    }

    /**
     * Write response.
     *
     * @param response the response
     * @param opsCloudChangeCheckCallbackResult the ops cloud change check callback result
     * @throws IOException the io exception
     */
    protected void writeResponse(HttpServletResponse response,
            ChangeCheckCallbackResult opsCloudChangeCheckCallbackResult)
            throws IOException {
        response.setCharacterEncoding(HttpAlterShieldClient.CHAR_SET);
        response.setContentType(HttpAlterShieldClient.CONTENT_TYPE);
        response.getWriter()
                .print(JSONUtil.toJsonStringWithClass(opsCloudChangeCheckCallbackResult));
        response.getWriter().flush();
    }

    /**
     * Gets request post str.
     *
     * @param request the request
     * @return the request post str
     * @throws IOException the io exception
     */
    protected String getRequestPostStr(HttpServletRequest request) throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = HttpAlterShieldClient.CHAR_SET;
        }
        return new String(buffer, charEncoding);
    }

    /**
     * Get request post bytes byte [ ].
     *
     * @param request the request
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    protected byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * Do dispatch ops cloud change check callback result.
     *
     * @param wrapperRequest the wrapper request
     * @return the ops cloud change check callback result
     */
    protected ChangeCheckCallbackResult doDispatch(
            ChangeCheckCallbackWrapperRequest wrapperRequest) {
        switch (wrapperRequest.getChangeCheckType()) {
            case CHANGE_ORDER:
                return changeCheckCallback
                        .orderChangeCheckCallback(wrapperRequest.getCallbackRequest());
            case CHANGE_BATCH:
                return changeCheckCallback
                        .batchChangeCheckCallback(wrapperRequest.getCallbackRequest());
            case CHANGE_ACTION:
                return changeCheckCallback.actionChangeCheckCallback(
                        (ChangeActionCheckCallbackRequest) wrapperRequest
                                .getCallbackRequest());
            default:
                AlterShieldLoggerManager.log("error", logger,
                        "invalid change check type:" + wrapperRequest);
                throw new AlterShieldInternalException(
                        AlterShieldInternalErrorCode.INVALID_CHANGE_CHECK_ERROR,
                        "invalid change check type:" + wrapperRequest.getChangeCheckType());
        }
    }

    /**
     * 根据平台获取签名。如果是多平台，需要业务实现获取token的逻辑
     *
     * @param platform the platform
     * @return token
     */
    protected String getToken(String platform) {
        return token;
    }

    /**
     * Init.
     */
    public void init() {
        if (changeCheckCallback == null) {
            throw new NullPointerException("OpsCloudChangeCheckCallback is null");
        }
    }
}
