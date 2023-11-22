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
package com.alipay.altershield;

import com.alipay.altershield.listener.LoggingListener;
import com.alipay.altershield.scann.AlterShieldSchedulerEventScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author xiangyue
 * @version : AlterShieldApplication.java, v 0.1 2023-04-27 11:28 xiangyue Exp $$
 */
@EnableOpenApi
@EnableWebMvc
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.alipay.altershield",excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "com.alipay.altershield.framework.sdk.*"))
@AlterShieldSchedulerEventScan(basePackages = "com.alipay.altershield")
public class AlterShieldApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AlterShieldApplication.class);
        LoggingListener loggingListener = new LoggingListener();
        app.addListeners(loggingListener);
        app.run(args);
        app.run(args);
    }
}
