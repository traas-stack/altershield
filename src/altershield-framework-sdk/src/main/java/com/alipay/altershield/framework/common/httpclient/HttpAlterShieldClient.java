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

import java.util.Map;

public interface HttpAlterShieldClient {

    String CONTENT_TYPE = "application/json";
    String CHAR_SET = "UTF-8";

    String H_PLATFORM = "X-AlterShield-Platform";
    String H_PLATFORM_TIMESTAMP = "X-AlterShield-Timestamp";
    String H_PLATFORM_SIGN = "X-AlterShield-Sign";

    /**
     * Retrieve data through the POST method.
     *
     * @param platform
     * @param platformToken
     * @param url
     * @param request
     * @return
     */
    String post(String platform, String platformToken, String url, String request);

    /**
     * Retrieve data through the POST method.
     *
     * @param action
     * @param requestContentStr
     * @return
     */
    String post(String action, String requestContentStr);

    /**
     * Retrieve data through the GET method.
     *
     * @param action
     * @param uriVariables
     * @return
     */
    String get(String action, Map<String, String> uriVariables);

    /**
     * Retrieve data through the GET method.
     * @param platform
     * @param platformToken
     * @param action
     * @param uriVariables
     * @return
     */
    String get(String platform, String platformToken, String action,
            Map<String, String> uriVariables);
}
