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
 * @version : Loggers.java, v 0.1 2023_05_29 15:41 xiangyue Exp $$
 */
public interface Loggers {

    /**
     * default
     */
    Logger DEFAULT = LoggerFactory.getLogger("ALTER_SHIELD_FRAMEWORK");

    /**
     * local scheduler
     */
    Logger LOCAL_SCHEDULE = LoggerFactory.getLogger("ALTER_SHIELD_LOCAL_SCHEDULE");

    /**
     * event schedule point
     */
    Logger SCHEDULE_POINT = LoggerFactory.getLogger("ALTER_SHIELD_SCHEDULE_POINT");

    /**
     * digest log of schedule point
     */
    Logger SCHEDULE_POINT_DIGEST = LoggerFactory.getLogger("ALTER_SHIELD_SCHEDULE_POINT_DIGEST");

    /**
     * DAL log
     */
    Logger DAL = LoggerFactory.getLogger("ALTER_SHIELD_DAL");

    /**
     * DAO log
     */
    Logger DAO_DIGEST = LoggerFactory.getLogger("ALTER_SHIELD_DAO_DIGEST");

    /**
     * FACADE execution log
     */
    Logger FACADE_EXE = LoggerFactory.getLogger("ALTER_SHIELD_FACADE_EXE");

    /**
     * 外部服务日志
     */
    Logger SERVICE_INFO = LoggerFactory.getLogger("ALTER_SHIELD_SERVICE_INFO");

    /**
     * 外部操作类调用日志
     */
    Logger SERVICE_OPERATE_DIGEST = LoggerFactory.getLogger("ALTER_SHIELD_SERVICE_OPERATE_DIGEST");

    /**
     * 外部查询类调用日志
     */
    Logger SERVICE_QUERY_DIGEST = LoggerFactory.getLogger("ALTER_SHIELD_SERVICE_QUERY_DIGEST");

    /**
     * 方法性能监测日志
     */
    Logger SERVICE_PROFILER  = LoggerFactory.getLogger("ALTER_SHIELD_SERVICE_PROFILER");

    /**
     * 业务服务方法
     */
    Logger BIZ_SERVICE = LoggerFactory.getLogger("ALTER_SHIELD_BIZ_SERVICE");
    /**
     * CACHE缓存
     */
    Logger META_CACHE            = LoggerFactory.getLogger("ALTER_SHIELD_META_CACHE");
    /**
     * 变更模块
     */
    Logger META_CHANGE            = LoggerFactory.getLogger("ALTER_SHIELD_META_CHANGE");
    /**
     * 状态机
     */
    Logger EXE_STATE_MACHINE            = LoggerFactory.getLogger("ALTER_SHIELD_EXE_STATE_MACHINE");
    /**
     * defender log
     */
    Logger DEFENDER = LoggerFactory.getLogger("ALTER_SHIELD_DEFENDER");
    /**
     * 外部任务调用
     */
    Logger OUTER_TASK = LoggerFactory.getLogger("ALTER_SHIELD_OUTER_TASK");
    /**
     * plugin market log
     */
    Logger PLUGIN_MARKET = LoggerFactory.getLogger("ALTER_SHIELD_PLUGIN_MARKET");
}
