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
package com.alipay.altershield.framework.common.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AlterShieldLoggerManager {

    /**
     * The constant LEVEL_TRACE.
     */
    public static final int LEVEL_TRACE = 1;
    /**
     * The constant LEVEL_DEBUG.
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * The constant LEVEL_INFO.
     */
    public static final int LEVEL_INFO = 3;
    /**
     * The constant LEVEL_WARN.
     */
    public static final int LEVEL_WARN = 4;
    /**
     * The constant LEVEL_ERROR.
     */
    public static final int LEVEL_ERROR = 5;
    /**
     * The constant LEVEL_FATAL.
     */
    public static final int LEVEL_FATAL = 6;

    private static volatile Map<String, Integer> logMap = new HashMap<String, Integer>();
    static {
        initLogMap(logMap);
    }

    private static void initLogMap(Map<String, Integer> logMap) {
        logMap.put("trace", LEVEL_TRACE);
        logMap.put("debug", LEVEL_DEBUG);
        logMap.put("info", LEVEL_INFO);
        logMap.put("warn", LEVEL_WARN);
        logMap.put("error", LEVEL_ERROR);
        logMap.put("fatal", LEVEL_FATAL);
    }

    /**
     * Is enable boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isEnable(String name) {
        if (name == null) {
            return false;
        }
        return logMap.containsKey(name.toLowerCase());
    }

    /**
     * Reset log level map.
     *
     * @param map the map
     */
    public static void resetLogLevelMap(Map<String, Integer> map) {
        Map<String, Integer> newMap = new HashMap<String, Integer>();
        initLogMap(newMap);
        if (map != null) {
            for (Entry<String, Integer> en : map.entrySet()) {
                String key = en.getKey();
                Integer i = en.getValue();
                if (i != null && i >= LEVEL_TRACE && i <= LEVEL_FATAL) {
                    newMap.put(key, i);
                }
            }
        }
        logMap = newMap;
        StringBuilder sb = new StringBuilder().append("{");
        for (Entry<String, Integer> en : newMap.entrySet()) {
            Integer level = en.getValue();
            String val = null;
            if (level != null) {
                switch (level) {
                    case LEVEL_TRACE:
                        val = "trace";
                        break;
                    case LEVEL_DEBUG:
                        val = "debug";
                        break;
                    case LEVEL_INFO:
                        val = "info";
                        break;
                    case LEVEL_WARN:
                        val = "warn";
                        break;
                    case LEVEL_ERROR:
                        val = "error";
                        break;
                    case LEVEL_FATAL:
                        val = "fatal";
                        break;
                    default:
                        val = String.valueOf(level);
                        break;
                }
            }
            sb.append("\n").append(en.getKey()).append("->").append(val);
        }
        sb.append("}");
        logInternal(LEVEL_WARN, LoggerFactory.getLogger(AlterShieldLoggerManager.class),
                "[logLevelMap] updated: " + sb);
    }

    /**
     *
     * @param logName
     * @return never null, {@link #LEVEL_DEBUG} by default
     */
    private static int getLogLevel(String logName) {
        if (logName == null) {
            logName = "";
        }
        Integer l = logMap.get(logName.toLowerCase());
        if (l == null) {
            return LEVEL_INFO;
        }
        return l;
    }

    /**
     * toString and catch all Throwable
     *
     * @param obj the obj
     * @param log the log
     * @param name the name
     * @return string
     */
    public static String toStringWithoutThrow(Object obj, Logger log, String name) {
        try {
            return String.valueOf(obj);
        } catch (Throwable e) {
            log(name, log, e);
            return null;
        }
    }

    /**
     * Log.
     *
     * @param name the name
     * @param log the log
     * @param t the t
     * @param infos 多个值,输出时会用[]包装
     */
    public static void log(String name, Logger log, Throwable t, Object... infos) {
        logWithTraceIdInternal(getLogLevel(name), log, true, t, infos);
    }

    /**
     * Log.
     *
     * @param name the name
     * @param log the log
     * @param infos 多个值,输出时会用[]包装
     */
    public static void log(String name, Logger log, Object... infos) {
        log(name, log, null, infos);
    }

    /**
     * @param level
     * @param log
     * @param traceId
     * @param t
     * @param infos 多个值,输出时会用[]包装
     */
    private static void logWithTraceIdInternal(int level, Logger log, boolean traceId, Throwable t,
            Object... infos) {
        try {
            if (isEnabled(level, log)) {
                String msg = getLogString(traceId, infos);
                if (t == null) {
                    logInternal(level, log, msg);
                } else {
                    logInternal(level, log, msg, t);
                }
            }
        } catch (Throwable e) {
            log.error("logWithTraceIdInternal exception", e);
        }
    }

    /**
     * @param level
     * @param log
     * @param msg
     * @param t
     */
    private static void logInternal(int level, Logger log, String msg, Throwable t) {
        switch (level) {
            case LEVEL_TRACE:
                if (log.isTraceEnabled()) {
                    log.trace(msg, t);
                }
                break;
            case LEVEL_DEBUG:
                if (log.isDebugEnabled()) {
                    log.debug(msg, t);
                }
                break;
            case LEVEL_INFO:
                if (log.isInfoEnabled()) {
                    log.info(msg, t);
                }
                break;
            case LEVEL_WARN:
                log.warn(msg, t);
                break;
            case LEVEL_ERROR:
                log.error(msg, t);
                break;
            default:
                break;
        }
    }

    /**
     * @param level
     * @param log
     * @param msg
     */
    private static void logInternal(int level, Logger log, String msg) {
        switch (level) {
            case LEVEL_TRACE:
                if (log.isTraceEnabled()) {
                    log.trace(msg);
                }
                break;
            case LEVEL_DEBUG:
                if (log.isDebugEnabled()) {
                    log.debug(msg);
                }
                break;
            case LEVEL_INFO:
                if (log.isInfoEnabled()) {
                    log.info(msg);
                }
                break;
            case LEVEL_WARN:
                if (log.isWarnEnabled()) {
                    log.warn(msg);
                }
                break;
            case LEVEL_ERROR:
                if (log.isErrorEnabled()) {
                    log.error(msg);
                }
                break;

            default:
                break;
        }
    }

    /**
     * @param level
     * @param log
     * @return
     */
    private static boolean isEnabled(int level, Logger log) {
        switch (level) {
            case LEVEL_TRACE:
                return log.isTraceEnabled();
            case LEVEL_DEBUG:
                return log.isDebugEnabled();
            case LEVEL_INFO:
                return log.isInfoEnabled();
            case LEVEL_WARN:
                return log.isWarnEnabled();
            case LEVEL_ERROR:
                return log.isErrorEnabled();
            default:
                return false;
        }
    }

    /**
     * @param trace
     * @param infos
     * @return
     */
    private static String getLogString(boolean trace, Object... infos) {
        trace = trace; // NOPMD
        if (infos == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        sb.append("[thrd_").append(Thread.currentThread().getId()).append(']');

        for (int i = 0; i < infos.length; ++i) {
            sb.append('[');
            sb.append(infos[i]);
            sb.append(']');
        }

        return sb.toString();
    }
}
