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
 * Alipay.com Inc. Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield.common.constant;

/**
 * @author xiangyue
 * @version : AlterShieldConstant.java, v 0.1 2023-05-29 15:07 xiangyue Exp $$
 */
public class AlterShieldConstant {
    /**
     * The constant CONST_SEQ_MAX_VALUE.
     */
    public static final long    CONST_SEQ_MAX_VALUE      = 2821109907455L;
    /**
     * 最大重试次数
     */
    public static volatile int  SCHEDULE_POINT_MAX_RETRY                  = 50;
    /**
     * 首次重试间隔，in ms.调度器以秒为单位，这个数字低于1秒无意义
     */
    public static volatile long SCHEDULE_POINT_FIRST_RETRY_INTERVAL       = 1000;
    /**
     * The constant SCHEDULE_POINT_MAX_RETRY_THRESHOLD.
     */
    public static volatile int  SCHEDULE_POINT_MAX_RETRY_THRESHOLD        = 6;
    /**
     * 采用固定间隔重试的初始重试时间间隔，im ms.
     */
    public static volatile long SCHEDULE_POINT_FIXED_RETRY_FIRST_INTERVAL = 5000;
    /**
     * 采用固定间隔重试的最大重试时间间隔，im ms.
     */
    public static volatile long SCHEDULE_POINT_FIXED_RETRY_MAX_INTERVAL   = 600000;
    /**
     * 单次单UID捞数据大小
     */
    public static volatile int SCHEDULE_POINT_SINGLE_UID_LOAD_SIZE = 100;
    /**
     * 切换scheduler的暂停态
     */
    public static volatile boolean SWITCH_SCHEDULER_PAUSE = false;
}
