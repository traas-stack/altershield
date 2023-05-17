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
package com.alipay.altershield.framework.sdk.change.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.sdk.util.LimitTimes;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author liushengdong
 * @version $Id: LimitTimeConf.java, v 0.1 2019年03月14日 18:14 liushengdong Exp $
 */
public class LimitTimeConf {
    /** -1:代表没有配置 */
    private volatile long defaultValue = -1;

    private final String defaultKey = "default";

    /** Map[serviceKey,限流组件] */
    private ConcurrentHashMap<String, LimitTimes> limitTimesMap = new ConcurrentHashMap<>();

    /**
     *
     * @param json
     */
    public void init(String json) {
        if (CommonUtil.isBlank(json)) {
            return;
        }
        JSONObject limitTimesConfJsn = JSON.parseObject(json);
        if (limitTimesConfJsn != null) {

            Set<String> keySet = limitTimesConfJsn.keySet();

            if (keySet.isEmpty()) {
                return;
            }

            for (String key : keySet) {
                if (defaultKey.equals(key)) {
                    long defaultValue = -1;
                    String value = limitTimesConfJsn.getString(key);
                    if (CommonUtil.isNotBlank(value)) {
                        defaultValue = limitTimesConfJsn.getLongValue(key);
                    }
                    this.setDefault(defaultValue);
                } else {
                    String value = limitTimesConfJsn.getString(key);
                    if (CommonUtil.isNotBlank(value)) {
                        long limit = limitTimesConfJsn.getLongValue(key);
                        this.setServiceKey(key, limit);
                    }
                }
            }

            if (!keySet.contains(defaultKey)) {
                this.setDefault(-1);
            }

            // 现有的serviceKey如果传入是json不包含就直接删除掉
            for (String key : this.limitTimesMap.keySet()) {
                if (!keySet.contains(key)) {
                    limitTimesMap.remove(key);
                }
            }
        }
    }

    /**
     * 在不清理计数的情况下,修改阈值
     *
     * @param serviceKey
     * @param maxCountPerSecond >=0 才是有效设置
     */
    private void setServiceKey(String serviceKey, long maxCountPerSecond) {

        if (maxCountPerSecond >= 0) {
            LimitTimes limitTimes = limitTimesMap.get(serviceKey);

            // 确保serviceKey限流组件被初始化
            if (limitTimes == null) {
                limitTimes = new LimitTimes(maxCountPerSecond);
                LimitTimes limitTimesOld = limitTimesMap.putIfAbsent(serviceKey, limitTimes);
                if (limitTimesOld != null) {
                    limitTimes = limitTimesOld;
                }
            }

            limitTimes.setMaxCountPerSecond(maxCountPerSecond);
        }
    }

    /**
     * 在不清理计数的情况下,修改阈值
     *
     * @param defaultValue -1:代表没有配置
     */
    private void setDefault(long defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param serviceKey
     * @return true:当前serviceKey可用;false:当前serviceKey被限流
     */
    public boolean addCountAndCheckIsPass(String serviceKey) {
        LimitTimes limitTimes = limitTimesMap.get(serviceKey);
        if (limitTimes == null) {

            // 还未初始化,当前serviceKey对应的限流值.立即使用默认值初始化
            if (defaultValue == -1) {
                return true;
            }

            limitTimes = new LimitTimes(defaultValue);
            LimitTimes limitTimesOld = limitTimesMap.putIfAbsent(serviceKey, limitTimes);
            if (limitTimesOld != null) {
                limitTimes = limitTimesOld;
            }
        }

        return limitTimes.addCountAndCheckIsPass();
    }

    /**
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "LimitTimeConf{" + "defaultValue=" + defaultValue + ", limitTimesMap="
                + limitTimesMap + '}';
    }
}
