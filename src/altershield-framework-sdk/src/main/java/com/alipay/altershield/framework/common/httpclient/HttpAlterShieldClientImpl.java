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
package com.alipay.altershield.framework.common.httpclient;

import com.alipay.altershield.framework.common.httpclient.spi.HttpClientResponse;
import com.alipay.altershield.framework.common.httpclient.spi.HttpInvoker;
import com.alipay.altershield.framework.common.util.SignUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Data
public class HttpAlterShieldClientImpl implements HttpAlterShieldClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpAlterShieldClientImpl.class);
    private String serverAddress;
    private String platform;
    private String platformToken;
    private String proxyAddress;
    private String proxyParamName;
    private HttpInvoker httpInvoker;
    private static final String MIME_TYPE = CONTENT_TYPE + ";" + CHAR_SET;


    /**
     * Post string.
     *
     * @param action  the action
     * @param request the request
     * @return the string
     */
    @Override
    public String post(String action, String request) {
        String url = getServerAddress() + action;
        return post(getPlatform(), getPlatformToken(), url, request);
    }

    /**
     * Post string.
     *
     * @param platform      the platform
     * @param platformToken the platform token
     * @param url           the url
     * @param request       the request
     * @return the string
     */
    @Override
    public String post(String platform, String platformToken, String url, String request) {
        NameValuePair[] headers = buildHeader(platform, platformToken, request);
        return execute(s -> httpInvoker.post(s, headers, request), url);
    }

    /**
     * Get string.
     *
     * @param platform      the platform
     * @param platformToken the platform token
     * @param action        the action
     * @param uriVariables  the uri variables
     * @return the string
     */
    public String get(String platform, String platformToken, String action,
            Map<String, String> uriVariables) {
        String url = buildUrl(action, uriVariables);
        NameValuePair[] headers =
                buildHeader(platform, platformToken, SignUtil.sortMap(uriVariables));
        return execute(s -> httpInvoker.get(s, headers), url);

    }

    private String execute(Function<String, HttpClientResponse> function, String url) {
        long start = System.currentTimeMillis();
        String proxyUrl = buildProxyUrl(url);
        HttpClientResponse response = function.apply(proxyUrl);
        long consume = System.currentTimeMillis() - start;
        logger.info(
                String.format("GET [HTTP %d] %d [%s]", response.getResponseCode(), consume, url));
        if (response.getResponseCode() == HttpStatus.SC_OK) {
            // Unescape html body here one time for all.
            return EscapeUtil.unescapeHtmlEncodedStr(response.getResponseBody());
        } else {
            String msg = String.format("HTTP status %s, response: %s", response.getResponseCode(),
                    response.getResponseBody());
            throw new HttpRpcException(response.getResponseCode(), msg);
        }
    }


    /**
     * Get string.
     *
     * @param action       the action
     * @param uriVariables the uri variables
     * @return the string
     */
    public String get(String action, Map<String, String> uriVariables) {
        String url = getServerAddress() + action;
        return get(getPlatform(), getPlatformToken(), url, uriVariables);
    }

    protected String buildProxyUrl(String url) {
        try {
            if (StringUtils.isNotBlank(proxyAddress) && StringUtils.isNotBlank(proxyParamName)) {
                return proxyAddress + "?" + proxyParamName + "=" + URLEncoder.encode(url, CHAR_SET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return url;
    }


    private String buildUrl(String action, Map<String, String> uriVariables) {
        StringBuilder urlTemplateBuilder = new StringBuilder();
        urlTemplateBuilder.append(action);
        boolean isFirst = true;
        try {
            for (String key : uriVariables.keySet()) {
                urlTemplateBuilder.append(isFirst ? "?" : "&").append(key).append("=")
                        .append(URLEncoder.encode(uriVariables.get(key), CHAR_SET));
                isFirst = false;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return urlTemplateBuilder.toString();
    }

    private NameValuePair[] buildHeader(String platform, String platformToken, String content) {

        List<NameValuePair> nameValuePairs = new ArrayList<>(5);
        nameValuePairs.add(new NameValuePair("Accept", MIME_TYPE));
        nameValuePairs.add(new NameValuePair("Content-Type", MIME_TYPE));
        nameValuePairs.add(new NameValuePair(H_PLATFORM, platform));
        long timestamp = System.currentTimeMillis();
        nameValuePairs.add(new NameValuePair(H_PLATFORM_TIMESTAMP, String.valueOf(timestamp)));

        String signValue;
        try {
            signValue = SignUtil.generateSign(timestamp, content, platformToken);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        nameValuePairs.add(new NameValuePair(H_PLATFORM_SIGN, signValue));
        return nameValuePairs.toArray(new NameValuePair[0]);
    }

    /**
     * Init.
     */
    public void init() {
        Objects.requireNonNull(httpInvoker, "http invoker is null");
    }
}
