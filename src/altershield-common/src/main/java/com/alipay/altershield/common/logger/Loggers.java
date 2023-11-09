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
package com.alipay.altershield.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiangyue
 * @version : Loggers.java, v 0.1 2023-05-29 15:41 xiangyue Exp $$
 */
public interface Loggers {
    /**
     * default
     */
    Logger DEFAULT = LoggerFactory.getLogger("ALTERSHIELD-FRAMEWORK");

    /**
     * 变更线程信息
     */
    Logger LOCAL_SCHEDULE = LoggerFactory.getLogger("ALTERSHIELD-LOCAL-SCHEDULE");

    /**
     * point处理线程
     */
    Logger SCHEDULE_POINT = LoggerFactory.getLogger("ALTERSHIELD-SCHEDULE-POINT");
    /**
     *
     */
    Logger SCHEDULE_POINT_DIGEST = LoggerFactory.getLogger("ALTERSHIELD-SCHEDULE-POINT-DIGEST");
    /**
     * DAL日志
     */
    Logger DAL = LoggerFactory.getLogger("ALTERSHIELD-DAL");
    /**
     * DAO日志
     */
    Logger DAO_DIGEST = LoggerFactory.getLogger("ALTERSHIELD_DAO_DIGEST");
}
