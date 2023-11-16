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
package com.alipay.altershield.common.server;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.NetUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 环境对象
 * @author yuanji
 * @version : OpsCloudEnv.java, v 0.1 2022年04月12日 5:20 下午 yuanji Exp $
 */
@Component
public class AlterShieldEnv implements InitializingBean {

    private static final Logger logger = Loggers.DEFAULT;
    private String localIp;
    private String host;
    @Value("${spring.profiles.active}")
    private String env;
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            localIp = NetUtil.getLocalIp();
            host = NetUtil.getHost(localIp);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("warn", logger, e.toString(), "Env.host failed", host);
        }finally {
            AlterShieldLoggerManager.log("info", logger, "Env.host", host);
        }
    }

    public boolean isPre() {
        return "pre".equalsIgnoreCase(env);
    }

    public boolean isProd() {
        return "prod".equalsIgnoreCase(env);
    }

    public boolean isDev() {
        return "dev".equalsIgnoreCase(env);
    }

    public boolean isStable() {
        return "stable".equalsIgnoreCase(env);
    }

    public boolean isLongTerm() {
        return this.host != null && host.contains("-long-");
    }

    public boolean isGray() {
        return "gray".equalsIgnoreCase(env);
    }
}