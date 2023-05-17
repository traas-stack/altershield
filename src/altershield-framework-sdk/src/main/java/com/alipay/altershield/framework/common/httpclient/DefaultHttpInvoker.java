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
import lombok.Data;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Data
public class DefaultHttpInvoker implements HttpInvoker {

    private int connectionTimeout = 6000;
    private int soTimeout = 6000;
    private int maxConnPerHost = 6;
    private int maxTotalConn = 10;
    private int connectionManagerTimeout = 1000;
    private HttpClient httpClient;

    private static final Logger logger = LoggerFactory.getLogger(DefaultHttpInvoker.class);

    @Override
    public HttpClientResponse post(String url, NameValuePair[] header, String content) {
        PostMethod postMethod = new PostMethod(url);
        buildHeader(postMethod, header);
        try {
            postMethod.setRequestEntity(new StringRequestEntity(content,
                    HttpAlterShieldClient.CONTENT_TYPE, HttpAlterShieldClient.CHAR_SET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        return execute(postMethod);
    }

    @Override
    public HttpClientResponse get(String url, NameValuePair[] header) {
        HttpMethod httpMethod = new GetMethod(url);
        buildHeader(httpMethod, header);
        return execute(httpMethod);
    }

    private void buildHeader(HttpMethod method, NameValuePair[] header) {
        for (int i = 0; null != header && i < header.length; i++) {
            NameValuePair nameValuePair = header[i];
            method.addRequestHeader(nameValuePair.getName(), nameValuePair.getValue());
        }
    }

    public HttpClientResponse execute(HttpMethod method) {
        HttpClientResponse response = new HttpClientResponse();
        try {
            int code = httpClient.executeMethod(method);
            response.setResponseCode(code);
            if (code == 200) {
                String responseString = readResponses(method);
                response.setResponseBody(responseString);
                return response;
            } else {
                String msg = String.format("HTTP status %s, response: %s",
                        response.getResponseCode(), response.getResponseBody());
                throw new HttpRpcException(response.getResponseCode(), msg);
            }
        } catch (IOException e) {
            throw new HttpRpcException(-1, e.getMessage());
        } finally {
            method.releaseConnection();
        }
    }

    private String readResponses(HttpMethod method) throws IOException {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                        HttpAlterShieldClient.CHAR_SET));

        String tempbf;
        StringBuilder builder = new StringBuilder(100);
        while ((tempbf = br.readLine()) != null) {
            builder.append(tempbf);
        }
        return builder.toString();
    }

    private HttpClient createHttpClient() {
        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
        connectionManagerParams.setConnectionTimeout(connectionTimeout);
        connectionManagerParams.setSoTimeout(soTimeout);
        connectionManagerParams.setMaxTotalConnections(maxTotalConn);
        connectionManagerParams.setDefaultMaxConnectionsPerHost(maxConnPerHost);
        httpConnectionManager.setParams(connectionManagerParams);
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        httpClient.getParams().setConnectionManagerTimeout(connectionManagerTimeout);
        return httpClient;
    }

    public synchronized void init() {
        if (httpClient == null) {
            logger.warn("init default http client invoker");
            this.httpClient = createHttpClient();
        }
    }
}
