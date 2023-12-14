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
package com.alipay.altershield.configuration;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.httpclient.DefaultHttpInvoker;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClientImpl;
import com.alipay.altershield.framework.common.httpclient.spi.HttpInvoker;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlterShieldCoreConfig {

    private static final Logger logger = Loggers.DEFAULT;


    /**
     * Create http invoker http invoker.
     *
     * @return the http invoker
     */
    @Bean(initMethod = "init")
    public HttpInvoker createHttpInvoker()
    {
        return  new DefaultHttpInvoker();
    }

    /**
     * Create http ops cloud client http ops cloud client.
     *
     * @param httpInvoker the http invoker
     * @return the http ops cloud client
     */
    @Bean
    public HttpAlterShieldClient createHttpAlterShieldClient(HttpInvoker httpInvoker)
    {
        logger.info("start create http client");
        HttpAlterShieldClientImpl httpAlterShieldClient = new HttpAlterShieldClientImpl();
        httpAlterShieldClient.setHttpInvoker(httpInvoker);
        logger.info("create http client success");
        return httpAlterShieldClient;
    }
}