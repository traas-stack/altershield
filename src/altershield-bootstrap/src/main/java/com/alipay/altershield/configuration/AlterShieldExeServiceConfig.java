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

import com.alipay.opscloud.change.exe.service.check.OpscloudExeChangeAsyncCheckService;
import com.alipay.opscloud.change.exe.service.check.OpscloudExeChangeSyncCheckService;
import com.alipay.opscloud.change.exe.service.check.impl.OpscloudExeChangeAsyncCheckServiceImpl;
import com.alipay.opscloud.change.exe.service.check.impl.OpscloudExeChangeSyncCheckServiceImpl;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zyt
 * @date 2023年2月2日 下午8:52:39
 */
@Configuration
public class AlterShieldExeServiceConfig {

    private static final Logger logger = Loggers.DEFAULT;

    /**
     * Create ops cloud exe change sync check service exe change sync check service.
     *
     * @return the exe change sync check service
     */
    //构建新变更核心同步接口
    @Bean()
    public OpscloudExeChangeSyncCheckService createOpsCloudExeChangeSyncCheckService()
    {
        logger.info("starting create opscloud OpscloudExeChangeSyncCheckService");

        OpscloudExeChangeSyncCheckServiceImpl exeChangeSyncCheckService = new OpscloudExeChangeSyncCheckServiceImpl();
        logger.info("starting create opscloud OpscloudExeChangeSyncCheckService");

        return exeChangeSyncCheckService;
    }

    /**
     * Create ops cloud exe change async check service exe change async check service.
     *
     * @return the exe change async check service
     */
    //构建新变更接口
    @Bean()
    public OpscloudExeChangeAsyncCheckService createOpsCloudExeChangeAsyncCheckService()
    {
        logger.info("starting create opscloud OpscloudExeChangeAsyncCheckService");
        OpscloudExeChangeAsyncCheckServiceImpl exeChangeAsyncCheckService = new OpscloudExeChangeAsyncCheckServiceImpl();
        logger.info("starting create opscloud OpscloudExeChangeAsyncCheckService");

        return exeChangeAsyncCheckService;
    }





}