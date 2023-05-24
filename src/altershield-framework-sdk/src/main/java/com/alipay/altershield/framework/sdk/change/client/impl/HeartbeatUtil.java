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
import com.alipay.altershield.framework.core.change.facade.HeartbeatCheck;
import com.alipay.altershield.framework.core.change.facade.request.ApiSwitchRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum;
import com.alipay.altershield.framework.sdk.util.AsteriskMatcher;
import com.alipay.altershield.framework.sdk.util.ClientLoggers;
import org.slf4j.Logger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liushengdong
 * @version $Id: HeartbeatBase.java, v 0.1 2018年12月20日 4:15 PM liushengdong Exp $
 */
public class HeartbeatUtil {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = ClientLoggers.LOGGER;

    private volatile Cache cache = null;

    /**
     * 跨洲调用异步化配置，确保不为null
     */
    private volatile AsyncInvokeConf asyncInvokeConf = new AsyncInvokeConf();

    private volatile boolean isRouteOk = true;

    /**
     * 心跳总开关，在opscloud没有部署的地方，可以一键关闭心跳，让所有开关都返回false，避免周期性的轮询错误
     */
    private volatile boolean isHeartBeatOn = true;

    /**
     * 是否抛出client端抓住的各种异常，比如超时，路由错误等。
     */
    private volatile boolean throwClientError = false;

    private final LimitTimeConf limitTimeConf = new LimitTimeConf();

    /***/
    public static final int maxFailedCount = 10;

    private static class Cache {
        private Set<String> switchOffPlatformsSet = new HashSet<String>(0);
        private Set<String> switchOffChangeSceneKeysSet = new HashSet<String>(0);
        private Set<String> switchOffChangeSceneKeysSetOfCurrPlatform = new HashSet<String>(0);
        private AsteriskMatcher whiteListChangeSceneKeys = new AsteriskMatcher(null);
        private AsteriskMatcher emergencyListChangeSceneKeys = new AsteriskMatcher(null);
        private boolean switchChgSrvCheck = false;

        /**
         * client开关,白名单模式 true:白名单模式,默认没有配置摆明单的平台.都无法访问. false:默认没有配置黑名单的用户,都可以访问
         */
        private boolean whiteListModel = true;

        /**
         * Getter method for property <tt>switchOffPlatformsSet</tt>.
         *
         * @return property value of switchOffPlatformsSet
         */
        Set<String> getSwitchOffPlatformsSet() {
            return switchOffPlatformsSet;
        }

        /**
         * Getter method for property <tt>switchOffServiceKeysSet</tt>.
         *
         * @return property value of switchOffServiceKeysSet
         */
        Set<String> getSwitchOffChangeSceneKeysSet() {
            return switchOffChangeSceneKeysSet;
        }

        /**
         * Getter method for property <tt>switchOffServiceKeysSetOfCurrPlatform</tt>.
         *
         * @return property value of switchOffServiceKeysSetOfCurrPlatform
         */
        Set<String> getSwitchOffChangeSceneKeysSetOfCurrPlatform() {
            return switchOffChangeSceneKeysSetOfCurrPlatform;
        }

        /**
         * Setter method for property <tt>switchOffPlatformsSet</tt>.
         *
         * @param switchOffPlatformsSet value to be assigned to property switchOffPlatformsSet
         */
        void setSwitchOffPlatformsSet(Set<String> switchOffPlatformsSet) {
            this.switchOffPlatformsSet = switchOffPlatformsSet;
        }

        /**
         * Setter method for property <tt>switchOffServiceKeysSet</tt>.
         *
         * @param switchOffChangeSceneKeysSet value to be assigned to property
         *        switchOffServiceKeysSet
         */
        void setSwitchOffChangeSceneKeysSet(Set<String> switchOffChangeSceneKeysSet) {
            this.switchOffChangeSceneKeysSet = switchOffChangeSceneKeysSet;
        }

        /**
         * Setter method for property <tt>switchOffServiceKeysSetOfCurrPlatform</tt>.
         *
         * @param switchOffChangeSceneKeysSetOfCurrPlatform value to be assigned to property
         *        switchOffServiceKeysSetOfCurrPlatform
         */
        void setSwitchOffChangeSceneKeysSetOfCurrPlatform(
                Set<String> switchOffChangeSceneKeysSetOfCurrPlatform) {
            this.switchOffChangeSceneKeysSetOfCurrPlatform =
                    switchOffChangeSceneKeysSetOfCurrPlatform;
        }

        /**
         * Getter method for property <tt>switchChgSrvCheck</tt>.
         *
         * @return property value of switchChgSrvCheck
         */
        boolean isSwitchChgSrvCheck() {
            return switchChgSrvCheck;
        }

        /**
         * Setter method for property <tt>switchChgSrvCheck</tt>.
         *
         * @param switchChgSrvCheck value to be assigned to property switchChgSrvCheck
         */
        void setSwitchChgSrvCheck(boolean switchChgSrvCheck) {
            this.switchChgSrvCheck = switchChgSrvCheck;
        }

        /**
         * Getter method for property <tt>whiteListServiceKeys</tt>.
         *
         * @return property value of whiteListServiceKeys
         */
        AsteriskMatcher getWhiteListChangeSceneKeys() {
            return whiteListChangeSceneKeys;
        }

        /**
         * Setter method for property <tt>whiteListServiceKeys</tt>.
         *
         * @param whiteListChangeSceneKeys value to be assigned to property whiteListServiceKeys
         */
        void setWhiteListChangeSceneKeys(AsteriskMatcher whiteListChangeSceneKeys) {
            this.whiteListChangeSceneKeys = whiteListChangeSceneKeys;
        }

        /**
         * Getter method for property <tt>emergencyListServiceKeys</tt>.
         *
         * @return property value of emergencyListServiceKeys
         */
        public AsteriskMatcher getEmergencyListChangeSceneKeys() {
            return emergencyListChangeSceneKeys;
        }

        /**
         * Setter method for property <tt>emergencyListServiceKeys</tt>.
         *
         * @param emergencyListChangeSceneKeys value to be assigned to property
         *        emergencyListServiceKeys
         */
        public void setEmergencyListChangeSceneKeys(AsteriskMatcher emergencyListChangeSceneKeys) {
            this.emergencyListChangeSceneKeys = emergencyListChangeSceneKeys;
        }

        /**
         * Getter method for property <tt>whiteListModel</tt>.
         *
         * @return property value of whiteListModel
         */
        boolean isWhiteListModel() {
            return whiteListModel;
        }

        /**
         * Setter method for property <tt>whiteListModel</tt>.
         *
         * @param whiteListModel value to be assigned to property whiteListModel
         */
        void setWhiteListModel(boolean whiteListModel) {
            this.whiteListModel = whiteListModel;
        }

        @Override
        public String toString() {
            return "Cache{" + "switchOffPlatformsSet=" + switchOffPlatformsSet
                    + ", switchOffChangeSceneKeysSet=" + switchOffChangeSceneKeysSet
                    + ", switchOffChangeSceneKeysSetOfCurrPlatform="
                    + switchOffChangeSceneKeysSetOfCurrPlatform + ", whiteListChangeSceneKeys="
                    + whiteListChangeSceneKeys + ", emergencyListChangeSceneKeys="
                    + emergencyListChangeSceneKeys + ", switchChgSrvCheck=" + switchChgSrvCheck
                    + ", whiteListModel=" + whiteListModel + '}';
        }
    }

    private HeartbeatCheck heartbeatCheck;
    private ScheduledThreadPoolExecutor changeSceneCheckScheduler =
            new ScheduledThreadPoolExecutor(1);
    private ScheduledFuture<?> future = null;

    private AtomicInteger failCount = new AtomicInteger(0);

    private int delay = 5000;
    private final String platform;

    public HeartbeatUtil(String platform) {
        this.platform = platform;
    }

    public synchronized void destroy() {
        if (future != null) {
            if (!future.isCancelled()) {
                future.cancel(true);
            }
        }
    }

    /**
     *
     */
    public synchronized void init() {
        if (future != null) {
            return;
        }
        // 每5秒检测一次,服务启动之后才开始check,不然会opscloudClientExtension.getHeartbeatCheck()return null.
        future = changeSceneCheckScheduler.scheduleWithFixedDelay(() -> {
            // 心跳总开关检查
            if (!isHeartBeatOn) {
                return;
            }

            Map<String, String> config = executeWithRetry(() -> {
                if (heartbeatCheck == null) {
                    LOGGER.warn("heartbeatUtil, client extension's heartbeat is null");
                    return null;
                } else {
                    try {
                        AlterShieldResult<Map<String, String>> ret = heartbeatCheck.doCheck(platform);
                        if (ret.getResultCode() == AlterShieldResultCodeEnum.PERMISSION_DENIED
                                .getCode()) {
                            LOGGER.error(
                                    "heartbeatUtil, client authentication fail," + ret.getMsg());
                            return null;
                        }
                        isRouteOk = true;
                        return ret.getDomain();
                    } catch (Exception e) {
                        LOGGER.error("heartbeatUtil exception", e);
                        throw e;
                    }
                }
            }, 4);
            boolean print = isPrint();
            if (print) {
                String message = "HeartbeatCheck config: " + config;
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(message);
                }
            }

            if (config != null) {
                Cache cacheTemp = null;
                try {
                    cacheTemp = new Cache();
                    cacheTemp.setSwitchOffChangeSceneKeysSet(getSet(config,
                            HeartbeatCheck.OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS));
                    cacheTemp.setWhiteListModel(true);

                    // 如果没有配置,默认返回true
                    String value = config.get(HeartbeatCheck.OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK);
                    cacheTemp.setSwitchChgSrvCheck(getBool(value, true));

                    String switchConfJsnStr =
                            config.get(HeartbeatCheck.OPSCLOUD_CLIENT_SWITCH_CONF);
                    try {
                        JSONObject switchConfJsn = JSON.parseObject(switchConfJsnStr);
                        if (switchConfJsn != null) {
                            String switchOffServiceKeys =
                                    switchConfJsn.getString("switchOffChangeSceneKeys");
                            Set<String> switchOffServiceKeysSetOfCurrPlatform =
                                    CommonUtil.splitAsSet(switchOffServiceKeys, ",", true);
                            cacheTemp.setSwitchOffChangeSceneKeysSetOfCurrPlatform(
                                    switchOffServiceKeysSetOfCurrPlatform);

                            String whiteListServiceKeys =
                                    switchConfJsn.getString("whiteListChangeSceneKeys");
                            Set<String> whiteListServiceKeysSetOfCurrPlatform =
                                    CommonUtil.splitAsSet(whiteListServiceKeys, ",", true);
                            AsteriskMatcher asteriskMatcher =
                                    new AsteriskMatcher(whiteListServiceKeysSetOfCurrPlatform);
                            cacheTemp.setWhiteListChangeSceneKeys(asteriskMatcher);

                            String emergencyListServiceKeys =
                                    switchConfJsn.getString("emergencyListChangeSceneKeys");
                            if (CommonUtil.isNotBlank(emergencyListServiceKeys)) {
                                Set<String> emergencyListServiceKeysSetOfCurrPlatform =
                                        CommonUtil.splitAsSet(emergencyListServiceKeys, ",", true);
                                AsteriskMatcher emersteriskMatcher = new AsteriskMatcher(
                                        emergencyListServiceKeysSetOfCurrPlatform);
                                cacheTemp.setEmergencyListChangeSceneKeys(emersteriskMatcher);
                            }

                            throwClientError = switchConfJsn.getBooleanValue("throwClientError");
                        }
                    } catch (Exception e) {
                        LOGGER.warn("CLIENT_SWITCH_CONF parse failed. value: " + switchConfJsnStr,
                                e);
                    }

                    // 获取当前平台的限流配置
                    String limitTimesConfJsnStr =
                            config.get(HeartbeatCheck.OPSCLOUD_LIMIT_TIMES_CONF);
                    try {
                        // 这里不每次new一个,是防止计数器被清空
                        limitTimeConf.init(limitTimesConfJsnStr);
                    } catch (Exception e) {
                        LOGGER.warn("LIMIT_TIMES_CONF parse failed. value: " + limitTimesConfJsnStr,
                                e);
                    }

                } catch (Exception e) {
                    LOGGER.warn("INIT HeartbeatUtil cache failed.", e);
                }

                cache = cacheTemp;
                if (print) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("HeartbeatCheck cache:" + cache);
                        LOGGER.info("HeartbeatCheck limitTimeConf:" + limitTimeConf);
                    }
                }
            }

        }, delay, delay, TimeUnit.SECONDS);
    }

    private long prePrintTime = 0;

    /**
     * 5分钟输出一次日志
     *
     * @return
     */
    private boolean isPrint() {
        boolean isPrint = System.currentTimeMillis() > prePrintTime + 300000;
        if (isPrint) {
            prePrintTime = System.currentTimeMillis();
        }
        return isPrint;
    }

    /**
     * 富客户端通过这个状态判断该环境有没有搭建opscloud
     *
     * @return true:路由正常 false:路由失败
     */
    public boolean chngRouteIsOk() {
        return isHeartBeatOn && isRouteOk;
    }

    /**
     * 获取当前心跳总开关的状态
     *
     * @return property value of isHeartBeatOn
     */
    public boolean isHeartBeatOn() {
        return isHeartBeatOn;
    }

    /**
     * 设置当前心跳总开关的状态
     *
     * @param heartBeatOn value to be assigned to property isHeartBeatOn
     */
    public void setHeartBeatOn(boolean heartBeatOn) {
        isHeartBeatOn = heartBeatOn;
    }

    /**
     * Getter method for property <tt>throwClientError</tt>.
     *
     * @return property value of throwClientError
     */
    public boolean isThrowClientError() {
        return throwClientError;
    }

    /**
     * 纯管控服务,是否开启
     *
     * @return true:开启 false:关闭(条件:失败次数超过阈值(opscloud服务不可用);配置本身就是false;)
     */
    public boolean chngCheckIsEnable() {
        // 如果无法路由到opscloud(该环境肯定没有搭建opscloud)则直接返回false
        if (!chngRouteIsOk()) {
            return false;
        }

        // 失败次数超过10次.返回false(不允许调用);失败次数<=10次,rpc失败返回true
        if (isFailed()) {
            return false;
        }
        // 失败次数 <= 10次
        else {
            // 获取配置失败,失败次数少于10次.开关开启(容错处理,失败次数很少的情况下,允许调用)
            if (cache == null) {
                return true;
            }

            // 如果没有配置,默认返回true
            return cache.isSwitchChgSrvCheck();
        }
    }

    private boolean getBool(String value, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * @return /失败次数超过10次,rpc又失败.返回false;失败次数<=10次,rpc失败返回true
     */
    boolean isFailed() {
        return failCount.get() > maxFailedCount;
    }

    /**
     * 判断changeSceneKey是否是应急专用的变更服务。
     *
     * @param changeSceneKey
     * @return
     */
    public boolean isEmergencyChangeSceneKey(String changeSceneKey) {
        if (CommonUtil.isNotBlank(changeSceneKey) && cache != null) {
            return cache.getEmergencyListChangeSceneKeys().match(changeSceneKey);
        }
        return false;
    }

    /**
     * 是否可以调用opscloud
     *
     * @param req
     * @return true:可以调用opscloud.false:不调用opscloud
     */
    public boolean isAlterShieldEnable(ApiSwitchRequest req) {
        // 如果无法路由到opscloud(该环境肯定没有搭建opscloud)则直接返回false
        if (!chngRouteIsOk()) {
            return false;
        }

        // 失败次数超过10次,rpc又失败.返回false;失败次数<=10次,rpc失败返回true
        if (isFailed()) {
            return false;
        }
        // 失败次数 <= 10次
        else {
            boolean isSwitchOn = isSwitchOn(req);
            // 开关允许后,开始限流
            if (isSwitchOn) {
                // @lsd 开关限流

                boolean isPass = limitTimeConf.addCountAndCheckIsPass(req.getChangeSceneKey());
                if (!isPass) {
                    LOGGER.warn("isOpscloudEnable:false Limit traffic(限制流量)."
                            + req.getChangeSceneKey());
                }
                return isPass;
            }
            return isSwitchOn;
        }
    }

    private boolean isSwitchOn(ApiSwitchRequest req) {
        // 获取配置失败,失败次数少于10次.开关开启(容错处理,失败次数很少的情况下,允许调用)
        if (cache == null) {
            return true;
        }

        // 全局匹配,优先级最高 > 当前平台独立配置
        // 按照平台维度关闭
        boolean off = cache.getSwitchOffPlatformsSet().contains(req.getPlatform())
                || cache.getSwitchOffPlatformsSet().contains("*");
        // if enable.按照接口维度关闭
        if (!off) {
            off = cache.getSwitchOffChangeSceneKeysSet().contains(req.getChangeSceneKey())
                    || cache.getSwitchOffChangeSceneKeysSet().contains("*");
        }

        // if enable.获取当前平台独立的配置
        if (!off) {
            return isEnableOfCurrPlatformConf(req);
        }
        return !off;
    }

    private boolean isEnableOfCurrPlatformConf(ApiSwitchRequest req) {
        // 匹配白名单,通过
        if (cache.getWhiteListChangeSceneKeys().match(req.getChangeSceneKey())) {
            return true;
        }

        if (cache.isWhiteListModel()) {
            return false;
        }

        // 按照serviceKey维度关闭
        if (cache.getSwitchOffChangeSceneKeysSetOfCurrPlatform().contains(req.getChangeSceneKey())
                || cache.getSwitchOffChangeSceneKeysSetOfCurrPlatform().contains("*")) {
            return false;
        }

        return true;
    }

    /**
     * 是否使用本地化trace。
     *
     * @return
     */
    public boolean useLocalTrace() {
        return asyncInvokeConf != null && asyncInvokeConf.isPlatformAsync()
                && (nullOrTrue(asyncInvokeConf.getGenTraceLocal()));
    }

    /**
     * 是否使用异步的前置校验。
     *
     * @return
     */
    public boolean isPrecheckAsync() {
        return asyncInvokeConf != null && asyncInvokeConf.isPlatformAsync()
                && (nullOrTrue(asyncInvokeConf.getPrecheckAsync()));
    }

    /**
     * 是否使用异步的后置校验。
     *
     * @return
     */
    public boolean isPostcheckAsync() {
        return asyncInvokeConf != null && asyncInvokeConf.isPlatformAsync()
                && (nullOrTrue(asyncInvokeConf.getFinishCheckAsync()));
    }

    /**
     * Getter method for property <tt>asyncInvokeConf</tt>.
     *
     * @return property value of asyncInvokeConf
     */
    public AsyncInvokeConf getAsyncInvokeConf() {
        return asyncInvokeConf;
    }

    /**
     * @param v
     * @return
     */
    public static boolean nullOrTrue(Boolean v) {
        return v == null || v.booleanValue();
    }

    /**
     * @param config
     * @param key
     * @return
     */
    public Set<String> getSet(Map<String, String> config, String key) {
        String switchOffs = config.get(key);
        return CommonUtil.splitAsSet(switchOffs, ",", true);
    }

    /**
     * @param config
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBool(Map<String, String> config, String key, boolean defaultValue) {
        String value = config.get(key);
        if (CommonUtil.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    private <T> T executeWithRetry(Callable<T> func, int retryTime) {
        retryTime = retryTime <= 0 ? 1 : retryTime;
        Throwable t = null;
        int i = 0;
        for (; i < retryTime; ++i) {
            try {
                T rst = func.call();
                // 成功一次就清零
                failCount.set(0);
                return rst;
            } catch (Throwable e) {
                t = e;

                // 失败一次+1
                failCount.incrementAndGet();
            }
        }
        LOGGER.warn("executeWithRetry, retryTime:" + i + " failCount:" + failCount.get(), t);
        return null;
    }

    /**
     * Setter method for property <tt>delay</tt>.
     *
     * @param delay value to be assigned to property delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setHeartbeatCheck(HeartbeatCheck heartbeatCheck) {
        this.heartbeatCheck = heartbeatCheck;
    }
}
