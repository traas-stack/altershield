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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.common.util;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 一键降级、内存配置相关代码。放在SDK里面方便core和prod层共享核心逻辑，不必重复发明轮胎。
 *
 * @author lex
 * @version $Id: MemoryConfigUtil.java, v 0.1 2020年04月14日 下午3:22 lex Exp $
 */
public class MemoryConfigUtil {

    /**
     * 内存配置总开关，打开后内存配置才生效。默认关闭。
     */
    public static volatile boolean SWITCH_ENABLE_MEMORY_CONFIG = false;

    /**
     * 流量总开关，默认打开。一键降级时关闭。
     */
    public static volatile boolean SWITCH_ENABLE_TRAFFIC = true;

    /**
     * 用来更新本缓存时进行安全校验。可通过DB配置进行覆盖。
     */
    public static volatile String SECURE_TOKEN = "123abc.go";

    /**
     * 内存配置全量缓存在此。当总开关关闭后，清空此配置。
     */
    public static final ConcurrentMap<String, String> memoryConfig = new ConcurrentHashMap<String, String>();

    /**
     * 当前内存配置版本号的Key。
     */
    private static final String KEY_CURRENT_VERSION_TS = "__CURRENT_VERSION_TS";

    /**
     * 开启内存配置总开关。
     *
     * @param timestamp 时间戳
     * @param config 内存配置
     */
    public static void enableMemoryConfig(long timestamp, Map<String, String> config) {
        if (config != null) {
            if (getCurrentTs() > timestamp) {
                // 用来update的版本比较老，不能覆盖。
                for (Map.Entry<String, String> entry : config.entrySet()) {
                    memoryConfig.putIfAbsent(entry.getKey(), entry.getValue());
                }
            } else {
                // 用来update的版本比较新，可以覆盖。
                memoryConfig.putAll(config);
                memoryConfig.put(KEY_CURRENT_VERSION_TS, Long.toString(timestamp));
            }
            ConstantsUtil.fillConstantsClass(memoryConfig, MemoryConfigUtil.class);
        }
        SWITCH_ENABLE_MEMORY_CONFIG = true;
    }

    /**
     *
     * @return the current memory config timestamp of null if memory config disabled.
     */
    public static long getCurrentTs() {
        final String s = memoryConfig.get(KEY_CURRENT_VERSION_TS);
        return s == null ? -1 : Long.parseLong(s);
    }

    /**
     * 关闭内存配置总开关。
     */
    public static void disableMemoryConfig() {
        SWITCH_ENABLE_MEMORY_CONFIG = false;
        SWITCH_ENABLE_TRAFFIC = true;
        memoryConfig.clear();
    }

}