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
package com.alipay.altershield.common.service.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.common.backconfig.entity.ConfigEntity;
import com.alipay.altershield.common.backconfig.repository.ConfigRepository;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.server.AlterShieldEnv;
import com.alipay.altershield.common.util.ConstantsUtil;
import com.alipay.altershield.common.util.MemoryConfigUtil;
import com.alipay.altershield.common.util.NetUtil;
import com.alipay.altershield.common.util.RetryUtil;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author liushengdong
 * @version $Id: ConfigRefreshService.java, v 0.1 2018年12月03日 11:38 PM liushengdong Exp $
 */
@Service
public class ConfigRefreshService  {

    private static final Logger logger = Loggers.LOCAL_SCHEDULE;

    /** 单独刷新某一个ip的配置的KEY,对应的值是一个json */
    private static final String REFRESH_BY_IP = "refresh_by_ip";
    private static final String MEMORY_CONFIG_SECURE_TOKEN = "MEMORY_CONFIG_SECURE_TOKEN";


    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private AlterShieldEnv alterShieldEnv;



    /** Map[name,value] */
    private volatile Map<String, String> cache        = new HashMap<>();
    private volatile boolean             isFinishOnce = false;



    @Scheduled(fixedDelay = 10000)
    public void refreshCache() {
       refreshCache("");
    }

    /**
     * @param info
     */
    public void refreshCache(String info) {
        boolean isSucc = refreshCache0(info);
        //只要完成了一次初始化,就直接设置为true
        if (!isFinishOnce && isSucc) {
            isFinishOnce = true;
        }
    }

    /**
     * Getter method for property <tt>isFinishOnce</tt>.
     *
     * @return property value of isFinishOnce
     */
    public boolean isFinishOnce() {
        return isFinishOnce;
    }

    private synchronized boolean refreshCache0(String info) {
        String pre = info + "ConfigRefreshService ";
        AlterShieldLoggerManager.log("info", logger, pre + "start");
        if (MemoryConfigUtil.SWITCH_ENABLE_MEMORY_CONFIG) {
            AlterShieldLoggerManager.log("info", logger, pre + "memory config enabled.",
                    ConstantsUtil.getFieldsStr(MemoryConfigUtil.class));
            Map<String, String> temp = new HashMap<>();
            temp.putAll(cache);
            temp.putAll(MemoryConfigUtil.memoryConfig);
            refreshByIP(temp, REFRESH_BY_IP);
            for (int i = 1; i <= AlterShieldConstant.REFRESH_BY_IP_MAX; i++) {
                refreshByIP(temp, REFRESH_BY_IP + "_" + i);
            }
            cache = temp;
            //初始化常量
            initConstrants();

            return true;
        } else {
            AlterShieldLoggerManager.log("info", logger, pre + "start");
        }

        boolean result = false;
        try {
            Long count = RetryUtil.executeWithRetry(() -> configRepository.selectTotalCount(), 3, AlterShieldInternalErrorCode.DB_ERROR);
            AlterShieldLoggerManager.log("info", logger, pre + "count", count);
            if (count == null) {
                cache = new HashMap<>();
                return result = true;
            }

            List<ConfigEntity> all = new ArrayList<>();

            int pageSize = 300;
            //这里的记录数不可能超过int最大值
            int size = count.intValue();
            for (int i = 0; i < size; i += pageSize) {
                int fromIndex = i;
                int toIndex = i + pageSize;
                toIndex = toIndex <= size ? toIndex : size;

                String msg = String.format("%s selectByPage(%s,%s)", pre, fromIndex, toIndex);
                AlterShieldLoggerManager.log("info", logger, msg);

                int finalToIndex = toIndex;
                List<ConfigEntity> configEntities = RetryUtil.executeWithRetry(
                        () -> configRepository.selectByPage(null, fromIndex, finalToIndex), 3, AlterShieldInternalErrorCode.DB_ERROR);

                AlterShieldLoggerManager.log("info", logger,
                        String.format("%s[returnsize:%s]", msg, configEntities == null ? 0 : configEntities.size()));
                if (configEntities != null && !configEntities.isEmpty()) {
                    all.addAll(configEntities);
                }
            }

            if (all.isEmpty()) {
                cache = new HashMap<>();
            } else {
                Map<String, String> temp = all.stream().collect(Collectors.toMap(ConfigEntity::getName, ConfigEntity::getValue));
                refreshByIP(temp, REFRESH_BY_IP);
                for (int i = 1; i <= AlterShieldConstant.REFRESH_BY_IP_MAX; i++) {
                    refreshByIP(temp, REFRESH_BY_IP + "_" + i);
                }
                cache = temp;
            }

            //初始化常量
            initConstrants();

            return result = true;
        } catch (Throwable t) {
            AlterShieldLoggerManager.log("error", logger, t, pre + "error");
            return result = false;
        } finally {
            AlterShieldLoggerManager.log("info", logger, pre + "end", result);
        }
    }



    private void initConstrants() {
        for (Entry<String, String> entry : cache.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //为空,或者_开头的都不刷新到常量
            if (CommonUtil.isBlank(key) || key.startsWith("_")) {
                continue;
            }
            try {
                ConstantsUtil.fillConstantsClass(key, value, AlterShieldConstant.class);
            } catch (NoSuchFieldException ne) {
                AlterShieldLoggerManager.log("debug", logger, "ConfigRefreshService skip, found no " + key);
            }catch (Throwable t) {
               AlterShieldLoggerManager.log("warn", logger, t, "ConfigRefreshService error");
            }
        }

        // 特殊操作，可以通过DB更新安全token
        if (cache.containsKey(MEMORY_CONFIG_SECURE_TOKEN)) {
            MemoryConfigUtil.SECURE_TOKEN = cache.get(MEMORY_CONFIG_SECURE_TOKEN);
        }

        AlterShieldLoggerManager.log("debug", logger, "ConfigRefreshService.initConstrants",
                ConstantsUtil.getFieldsStr(AlterShieldConstant.class));
    }

    private void refreshByIP(Map<String, String> temp,String name) {
        //将REFRESH_BY_IP数据更新到cache中
        String jsnStr = temp.get(name);
        Map<String, String> updateMap = parseRefreshByIPJsonStr(jsnStr);
        if (!updateMap.isEmpty()) {
            // 只需要打印当前机器命中的REFRESH_BY_IP
            AlterShieldLoggerManager.log("info", logger, "ConfigRefreshService " + name + ":", jsnStr);
        }
        temp.putAll(updateMap);
        //删除多余字段 refresh_by_ip
        temp.remove(name);
    }

    /**
     *
     * @param jsnStr
     * @return ip字段不为空,并且当前机器地址和ip相等,则解析出jsnStr.否则返回空map
     */
    private Map<String,String> parseRefreshByIPJsonStr(String jsnStr) {
        Map<String, String> map = new HashMap<>();
        try {
            if (CommonUtil.isNotBlank(jsnStr)) {
                JSONObject json = JSON.parseObject(jsnStr);
                if (json != null && !json.isEmpty()) {
                    String ip = json.getString("ip");
                    //ip字段不为空,并且当前机器地址和ip相等
                    if (CommonUtil.isNotBlank(ip)
                            && (ip.equals(NetUtil.getLocalIp())
                                || ip.equalsIgnoreCase("pre") && alterShieldEnv.isPre()
                                || ip.equalsIgnoreCase("stable") && alterShieldEnv.isStable()
                                || ip.equalsIgnoreCase("long") && alterShieldEnv.isLongTerm()
                                || ip.equalsIgnoreCase("gray") && alterShieldEnv.isGray())
                    ) {
                        //删除无用字段 ip
                        json.remove("ip");
                        Set<String> keys = json.keySet();
                        for (String key : keys) {
                            map.put(key, json.getString(key));
                        }
                    }
                }
            }

        } catch (Exception e) {
            AlterShieldLoggerManager.log("warn", logger, e, "ConfigRefreshService REFRESH_BY_IP error", jsnStr);
        }
        return map;
    }



    /**
     *
     * @param name
     * @return
     */
    public String getValue(String name) {
        return cache.get(name);
    }

}