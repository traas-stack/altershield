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
     * Maximum number of retries
     */
    public static volatile int  SCHEDULE_POINT_MAX_RETRY                  = 50;
    /**
     * First retry interval, in ms. Scheduler in seconds, this number is meaningless below 1 second
     */
    public static volatile long SCHEDULE_POINT_FIRST_RETRY_INTERVAL       = 1000;
    /**
     * The constant SCHEDULE_POINT_MAX_RETRY_THRESHOLD.
     */
    public static volatile int  SCHEDULE_POINT_MAX_RETRY_THRESHOLD        = 6;
    /**
     * Initial retry interval using fixed interval retries, im ms.
     */
    public static volatile long SCHEDULE_POINT_FIXED_RETRY_FIRST_INTERVAL = 5000;
    /**
     * Maximum retry interval using fixed interval retries, im ms.
     */
    public static volatile long SCHEDULE_POINT_FIXED_RETRY_MAX_INTERVAL   = 600000;
    /**
     * Data size for single UID retrieval
     */
    public static volatile int SCHEDULE_POINT_SINGLE_UID_LOAD_SIZE = 100;
    /**
     * Switch the scheduler's pause state
     */
    public static volatile boolean SWITCH_SCHEDULER_PAUSE = false;
    /**
     * Change order details page URL
     */
    public static volatile String ALTER_SHIELD_ORDER_DETAIL_URL = "";
    /**
     * Defense execution details page URL
     */
    public static volatile String ALTER_SHIELD_NODE_DETAIL_URL = "";
    /**
     * Whether the kv Ref field adopts variable length mode (only compatible during release, just keep it true forever)
     */
    public static volatile boolean SWITCH_KV_REF_WRITE_VAR_LEN        = true;
    /**
     * The constant DAL_KV_VALUE_COLUMN_SIZE.
     */
    public static final int     DAL_KV_VALUE_COLUMN_SIZE = 200 * 1024;
    /**
     * Maximum length allowed for each parameter
     */
    public static volatile int     LOG_PARAM_MAX_SIZE             = 4096;
    /**
     * Log desensitization component master switch
     */
    public static volatile boolean SWITCH_ENABLE_LOG_DE_SENSITIVE = true;
    /**
     * profiler threshold
     */
    public static volatile long PROFILER_THRESHOLD = 5000;
    /**
     * Maximum value of refresh_by_ip_ suffix in configuration
     */
    public static volatile int REFRESH_BY_IP_MAX = 3;
    /**
     * Client configuration
     */
    public static volatile boolean ALTER_SHIELD_CLIENT_SWITCH_CHG_SRV_CHECK         = true;
    /**
     * The constant ALTER_SHIELD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS.
     */
    public static volatile String  ALTER_SHIELD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS = "";
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
     * Change scene key format
     */
    public static final String CHANGE_KEY_PATTERN = "^[a-zA-Z][a-zA-Z0-9\\.\\_]*[a-zA-Z0-9]$";
    /**
     * Risk defense detection details link
     */
    public static   volatile String ALTER_SHIELD_DEFENSE_CHECK_DETAIL_URL = "";

    /**
     * Risk defense submits the first schdpoint polling time for asynchronous check
     */
    public static volatile   long   DEFENSE_SUBMIT_CHECK_INTERVAL     = 2;

    /**
     * Synchronous pure control timeout for obtaining detection data (unit: milliseconds)
     */
    public static volatile long SYNC_CHNG_CHECK_TIMEOUT = 3000;
    /**
     * G1 pre-synchronous marking timeout time
     */
    public static volatile long G1_SYNC_DECISION_TAG_TIMEOUT_MS = 1000;

    // -------------- Defender Start -----------------
    /**
     * Maximum blocking observation time of defense rules
     */
    public static volatile long DEFENDER_MAX_BLOCK_OBSERVE_SECOND = 900;

    /**
     * Maximum blocking observation time of monitor check
     */
    public static volatile long MONITOR_CHECK_MAX_BLOCK_OBSERVE_SECOND = 600;

    /**
     * Synchronization defense verification timeout, unit: milliseconds
     */
    public static volatile long DEFENDER_SYNC_DETECT_TIMEOUT = 3000;

    /**
     * Inner defense plugin configuration
     */
    public static volatile String DEFENDER_INNER_PLUGINS = "[{\n"
            + "\t\"pluginKey\": \"defender_assert_false_plugin\",\n"
            + "\t\"mainClass\": \"com.alipay.altershield.shared.pluginmarket.innerplugin.defender.AssertFalsePlugin\",\n"
            + "\t\"invokeType\": \"sync\"\n"
            + "}, {\n"
            + "\t\"pluginKey\": \"defender_assert_true_plugin\",\n"
            + "\t\"mainClass\": \"com.alipay.altershield.shared.pluginmarket.innerplugin.defender.AssertTruePlugin\",\n"
            + "\t\"invokeType\": \"sync\"\n"
            + "}, {\n"
            + "\t\"pluginKey\": \"monitor_metric_detection_plugin\",\n"
            + "\t\"mainClass\": \"com.alipay.altershield.shared.pluginmarket.innerplugin.defender.MonitorMetricDetectionPlugin\",\n"
            + "\t\"invokeType\": \"async\"\n"
            + "}]";
    // -------------- Defender Finish -----------------

    // TODO 更换为开源的接入文档地址
    /**
     * Access document address
     */
    public static volatile String ALTER_SHIELD_DOCUMENT_G0 = "https://yuque.antfin-inc.com/opscloud/bt914r/dgcd8z#pTAuq";
    /**
     * The constant ALTER_SHIELD_DOCUMENT_G1.
     */
    public static volatile String ALTER_SHIELD_DOCUMENT_G1 = "https://yuque.antfin-inc.com/opscloud/bt914r/dgcd8z#ziLU0";
    /**
     * The constant ALTER_SHIELD_DOCUMENT_G2.
     */
    public static volatile String ALTER_SHIELD_DOCUMENT_G2 = "https://yuque.antfin-inc.com/opscloud/bt914r/dgcd8z#Ebzy6";

    public static volatile Boolean SKIP_OPEN_API_AUTH = true;

}
