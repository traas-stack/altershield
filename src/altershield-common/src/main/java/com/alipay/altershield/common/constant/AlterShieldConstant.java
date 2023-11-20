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

    /**
     * 工单详情页URL
     */
    public static volatile String ALTER_SHIELD_ORDER_DETAIL_URL = "";

    /**
     * 防御执行详情页URL
     */
    public static volatile String ALTER_SHIELD_NODE_DETAIL_URL = "";
    /**
     * kvRef字段是否采用变长模式(仅发布期间兼容用，永远保持true即可)
     */
    public static volatile boolean SWITCH_KV_REF_WRITE_VAR_LEN        = true;
    /**
     * The constant DAL_KV_VALUE_COLUMN_SIZE.
     */
    public static final int     DAL_KV_VALUE_COLUMN_SIZE = 200 * 1024;

    /**
     * 每个参数允许的最大长度
     */
    public static volatile int     LOG_PARAM_MAX_SIZE             = 4096;
    /**
     * 日志脱敏组件总开关
     */
    public static volatile boolean SWITCH_ENABLE_LOG_DE_SENSITIVE = true;
    /**
     * profiler的阀值
     */
    public static volatile long PROFILER_THRESHOLD = 5000;

    /**
     * 配置中 refresh_by_ip_ 后缀的最大值
     */
    public static volatile int REFRESH_BY_IP_MAX = 3;
    /**
     * 客户端配置
     */
    public static volatile boolean OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK         = true;
    /**
     * The constant OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS.
     */
    public static volatile String  OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS = "";
    /**
     * The constant META_CACHE_TIMEOUT_MILLI.
     */
    public static volatile long    META_CACHE_TIMEOUT_MILLI                     = 20 * 1000L;
    /**
     * The constant META_CACHE_INTERVAL_MILLI.
     */
    public static volatile long    META_CACHE_INTERVAL_MILLI                    = 5 * 1000L;
    /**
     * The constant SWITCH_META_CACHE.
     */
    public static volatile boolean SWITCH_META_CACHE                            = true;
    /**
     * 场景key的格式
     */
    public static final String CHANGE_KEY_PATTERN = "^[a-zA-Z][a-zA-Z0-9\\.\\_]*[a-zA-Z0-9]$";
    /**
     * 风险防御校验详情连接
     */
    public static   volatile String OPSCLOUD_DEFENSE_CHECK_DETAIL_URL = "";

    /**
     * 风险防御提交异步check的第一次schdpoint轮询时间
     */
    public static volatile   long   DEFENSE_SUBMIT_CHECK_INTERVAL     = 2;

    /**
     * 同步纯管控获取校验数据超时时间(单位毫秒)
     */
    public static volatile long SYNC_CHNG_CHECK_TIMEOUT = 3000;
    /**
     * G1前置同步打标超时时间
     */
    public static volatile long G1_SYNC_DECISION_TAG_TIMEOUT_MS = 1000;
}
