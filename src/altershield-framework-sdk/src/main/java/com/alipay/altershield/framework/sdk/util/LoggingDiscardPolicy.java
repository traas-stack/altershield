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

import org.slf4j.Logger;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author lex
 * @version $Id: LoggingDiscardPolicy.java, v 0.1 2019年08月06日 下午8:43 lex Exp $
 */
public class LoggingDiscardPolicy implements RejectedExecutionHandler {

    private static final Logger logger = ClientLoggers.LOGGER;

    private String threadPoolName;

    public LoggingDiscardPolicy(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public LoggingDiscardPolicy() {}

    /**
     * 对于线程池打满的错误，仅打印日志就可以了。
     *
     * @param r
     * @param executor
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.error("ThreadPool: " + threadPoolName + " is full, discarded one task.");
    }

    /**
     * Getter method for property <tt>threadPoolName</tt>.
     *
     * @return property value of threadPoolName
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }

    /**
     * Setter method for property <tt>threadPoolName</tt>.
     *
     * @param threadPoolName value to be assigned to property threadPoolName
     */
    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

}
