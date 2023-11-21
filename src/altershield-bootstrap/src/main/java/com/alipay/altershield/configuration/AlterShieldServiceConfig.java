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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.configuration;

import com.alipay.altershield.change.exe.service.check.ExeChangeAsyncCheckService;
import com.alipay.altershield.change.exe.service.check.ExeChangeSyncCheckService;
import com.alipay.altershield.change.exe.service.check.impl.ExeChangeAsyncCheckServiceImpl;
import com.alipay.altershield.change.exe.service.check.impl.ExeChangeSyncCheckServiceImpl;
import com.alipay.altershield.common.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Ops cloud service config.
 *
 * @author yuanji
 * @version : OpsCloudServiceConfig.java, v 0.1 2022年11月08日 16:47 yuanji Exp $
 */
@Configuration
//@ConditionalOnProperty(name = "opscloud.framework.server.mode", havingValue = OpsCloudRunMode.RUN_MODE_OPSCLOUD)
public class AlterShieldServiceConfig {

    private static final Logger logger = Loggers.DEFAULT;

    /**
     * Create ops cloud exe change sync check service exe change sync check service.
     *
     * @return the exe change sync check service
     */
    //构建新变更核心同步接口
    @Bean()
    public ExeChangeSyncCheckService createExeChangeSyncCheckService() {
        logger.info("starting create opscloud ExeChangeSyncCheckService");

        ExeChangeSyncCheckServiceImpl exeChangeSyncCheckService = new ExeChangeSyncCheckServiceImpl();
        logger.info("starting create opscloud ExeChangeSyncCheckService");

        return exeChangeSyncCheckService;
    }

    /**
     * Create ops cloud exe change async check service exe change async check service.
     *
     * @return the exe change async check service
     */
    //构建新变更接口
    @Bean()
    public ExeChangeAsyncCheckService createExeChangeAsyncCheckService() {
        logger.info("starting create opscloud ExeChangeAsyncCheckService");
        ExeChangeAsyncCheckServiceImpl exeChangeAsyncCheckService = new ExeChangeAsyncCheckServiceImpl();
        logger.info("starting create opscloud ExeChangeAsyncCheckService");

        return exeChangeAsyncCheckService;
    }
}



