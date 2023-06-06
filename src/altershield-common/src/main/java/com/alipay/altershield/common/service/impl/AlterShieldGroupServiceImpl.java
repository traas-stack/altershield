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
package com.alipay.altershield.common.service.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.service.AlterShieldGroupService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 默认从启动变量中获取group 如果没有，则从配置中获取， 如果还没有，则返回default;
 *
 * @author yuanji
 * @version : DispatchGroupServiceImpl.java, v 0.1 2022年04月06日 7:47 下午 yuanji Exp $
 */
@Service
public class AlterShieldGroupServiceImpl implements AlterShieldGroupService, InitializingBean {

    @Value("${opscloud.framework.server.common.group.value:#{null}}")
    private              String group = null;
    /**
     * 启动参数中的信息
     */
    public static final String envKey = "opscloud.server.group";

    private static final String DEFAULT_GROUP = "default";

    private static final Logger logger = Loggers.DEFAULT;

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //先看是否有配置
        //先从环境中获取中
        //如果都没有，则使用默认值
        if (StringUtils.isBlank(group)) {
            group = System.getProperty(envKey, DEFAULT_GROUP).toLowerCase();
        }
        logger.info("init opscloud group:{}", group);

    }
}