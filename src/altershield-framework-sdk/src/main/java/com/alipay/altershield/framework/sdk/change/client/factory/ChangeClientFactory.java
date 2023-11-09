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
package com.alipay.altershield.framework.sdk.change.client.factory;

import com.alipay.altershield.framework.common.httpclient.DefaultHttpInvoker;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClientImpl;
import com.alipay.altershield.framework.common.httpclient.spi.HttpInvoker;
import com.alipay.altershield.framework.core.change.facade.ChangeFacade;
import com.alipay.altershield.framework.core.change.facade.HeartbeatCheck;
import com.alipay.altershield.framework.core.change.facade.MetaChangeSceneFacade;
import com.alipay.altershield.framework.core.risk.facade.RiskDefenseCheckRuleFacade;
import com.alipay.altershield.framework.sdk.change.client.*;
import com.alipay.altershield.framework.sdk.change.client.impl.*;
import com.alipay.altershield.framework.sdk.change.facade.http.HttpAlterShieldChangeFacadeImpl;
import com.alipay.altershield.framework.sdk.change.facade.http.HttpHeartbeatCheckClientImpl;
import com.alipay.altershield.framework.sdk.http.HttpAlterShieldRiskDefenseCheckRuleFacade;
import com.alipay.altershield.framework.sdk.meta.client.MetaChangeSceneClient;
import com.alipay.altershield.framework.sdk.meta.client.impl.MetaChangeSceneClientImpl;
import com.alipay.altershield.framework.sdk.meta.facade.http.HttpAlterShieldMetaChangeSceneFacadeImpl;
import com.alipay.altershield.framework.sdk.util.ClientLoggers;
import lombok.Data;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * 工厂类
 *
 * @author yuanji
 * @version : OpsCloudChangeClientFactory.java, v 0.1 2022年04月26日 2:20 下午 yuanji Exp $
 */
@Data
public class ChangeClientFactory {

    private HttpAlterShieldClient httpAlterShieldClient;
    private HeartbeatCheck heartbeatCheck;
    private HeartbeatUtil heartbeatUtil;
    private ChangeFacade changeFacade;
    private MetaChangeSceneFacade metaChangeSceneFacade;
    private RiskDefenseCheckRuleFacade riskDefenseCheckRuleFacade;
    private MetaChangeSceneClient metaChangeSceneClient;
    private ApiSwitch apiSwitch;


    /**
     * G2+ 变更client
     */
    private ChangeClient changeClient;
    /**
     * G1 变更client
     */
    private SimpleChangeClient simpleChangeClient;
    /**
     * G0 变更client
     */
    private ChangeNotifyClient changeNotifyClient;
    /**
     * 变更变更服务端地址
     */
    private String serverAddress;
    /**
     * 请求token
     */
    private String platformToken;
    /**
     * 平台名
     */
    private String platform;

    /**
     * 代理服务地址
     */
    private String proxyAddress;
    /**
     * 代理参数名
     */
    private String proxyParamName;

    /**
     * 连接相关配置
     */
    private int connectionTimeout = 6000;
    private int soTimeout = 6000;
    private int maxConnPerHost = 6;
    private int maxTotalConn = 10;
    private int connectionManagerTimeout = 1000;


    /**
     * 是否隐藏服务端错误 默认 true，服务端出错，则默认所有检查请求通过 false，服务端出错，显示告诉调用方系统错误
     */
    private boolean hiddenServerError = true;

    private static final Logger LOGGER = ClientLoggers.LOGGER;

    /**
     * Init.
     */
    public void init() {
        Objects.requireNonNull(platform, "platform is null");
        Objects.requireNonNull(platformToken, "platformToken is null");
        Objects.requireNonNull(serverAddress, "serverAddress is null");

        HttpInvoker httpInvoker = createHttpInvoker();
        httpAlterShieldClient = createHttpAlterShieldClient(httpInvoker);
        heartbeatCheck = createHttpHeartbeatCheckClient(httpAlterShieldClient);
        heartbeatUtil = createHeartBeatUtil(heartbeatCheck);
        changeFacade = createOpsCloudChangeFacade(httpAlterShieldClient);
        riskDefenseCheckRuleFacade =
                createOpsCloudRiskDefenseCheckRuleFacade(httpAlterShieldClient);
        ChangeInnerClient changeInnerClient =
                createChangeInnerClient(heartbeatUtil, changeFacade);

        changeClient = createOpsCloudChangeClient(changeInnerClient);
        simpleChangeClient = createOpsCloudSimpleChangeClient(changeInnerClient);
        changeNotifyClient = createOpsCloudChangeNotifyClient(changeInnerClient);

        metaChangeSceneFacade = createOpsCloudMetaChangeSceneFacade(httpAlterShieldClient);
        metaChangeSceneClient =
                createOpsCloudMetaChangeSceneClient(metaChangeSceneFacade);
        apiSwitch = createOpsCloudApiSwitch(heartbeatUtil);
    }

    /**
     * Destroy.
     */
    public void destroy() {
        if (heartbeatUtil != null) {
            heartbeatUtil.destroy();
        }
    }

    /**
     * Create http invoker http invoker.
     *
     * @return the http invoker
     */
    protected HttpInvoker createHttpInvoker() {
        DefaultHttpInvoker defaultHttpInvoker = new DefaultHttpInvoker();
        defaultHttpInvoker.setConnectionManagerTimeout(connectionManagerTimeout);
        defaultHttpInvoker.setConnectionTimeout(connectionTimeout);
        defaultHttpInvoker.setMaxConnPerHost(maxConnPerHost);
        defaultHttpInvoker.setMaxTotalConn(maxTotalConn);
        defaultHttpInvoker.init();
        return defaultHttpInvoker;
    }

    /**
     * Create http ops cloud client http ops cloud client.
     *
     * @param httpInvoker the http invoker
     * @return the http ops cloud client
     */
    protected HttpAlterShieldClient createHttpAlterShieldClient(HttpInvoker httpInvoker) {
        HttpAlterShieldClientImpl httpAlterShieldClient = new HttpAlterShieldClientImpl();
        httpAlterShieldClient.setHttpInvoker(httpInvoker);
        httpAlterShieldClient.setPlatform(platform);
        httpAlterShieldClient.setServerAddress(serverAddress);
        httpAlterShieldClient.setPlatformToken(platformToken);
        httpAlterShieldClient.setProxyAddress(proxyAddress);
        httpAlterShieldClient.setProxyParamName(proxyParamName);
        httpAlterShieldClient.init();
        return httpAlterShieldClient;
    }

    /**
     * Create http heartbeat check client heartbeat check.
     *
     * @param httpAlterShieldClient the http ops cloud client
     * @return the heartbeat check
     */
    protected HeartbeatCheck createHttpHeartbeatCheckClient(HttpAlterShieldClient httpAlterShieldClient) {
        HttpHeartbeatCheckClientImpl checkClient = new HttpHeartbeatCheckClientImpl();
        checkClient.setHttpOpsCloudClient(httpAlterShieldClient);
        return checkClient;
    }

    /**
     * Create heart beat util heartbeat util.
     *
     * @param heartbeatCheck the heartbeat check
     * @return the heartbeat util
     */
    protected HeartbeatUtil createHeartBeatUtil(HeartbeatCheck heartbeatCheck) {
        HeartbeatUtil heartbeatUtil = new HeartbeatUtil(platform);
        heartbeatUtil.setHeartbeatCheck(heartbeatCheck);
        heartbeatUtil.init();
        return heartbeatUtil;
    }

    /**
     * Create ops cloud change facade ops cloud change facade.
     *
     * @param httpAlterShieldClient the http ops cloud client
     * @return the ops cloud change facade
     */
    protected ChangeFacade createOpsCloudChangeFacade(
            HttpAlterShieldClient httpAlterShieldClient) {
        HttpAlterShieldChangeFacadeImpl httpAlterShieldChangeFacade = new HttpAlterShieldChangeFacadeImpl();
        httpAlterShieldChangeFacade.setHttpAlterShieldClient(httpAlterShieldClient);
        httpAlterShieldChangeFacade.init();
        return httpAlterShieldChangeFacade;
    }

    /**
     * Create ops cloud meta change scene facade ops cloud meta change scene facade.
     *
     * @param httpOpsCloudClient the http ops cloud client
     * @return the ops cloud meta change scene facade
     */
    protected MetaChangeSceneFacade createOpsCloudMetaChangeSceneFacade(
            HttpAlterShieldClient httpOpsCloudClient) {
        HttpAlterShieldMetaChangeSceneFacadeImpl httpAlterShieldChangeFacade =
                new HttpAlterShieldMetaChangeSceneFacadeImpl();
        httpAlterShieldChangeFacade.setHttpAlterShieldClient(httpOpsCloudClient);
        return httpAlterShieldChangeFacade;
    }

    /**
     * Create ops cloud risk defense check rule facade ops cloud risk defense check rule facade.
     *
     * @param httpAlterShieldClient the http ops cloud client
     * @return the ops cloud risk defense check rule facade
     */
    protected RiskDefenseCheckRuleFacade createOpsCloudRiskDefenseCheckRuleFacade(
            HttpAlterShieldClient httpAlterShieldClient) {
        HttpAlterShieldRiskDefenseCheckRuleFacade riskDefenseCheckRuleFacade =
                new HttpAlterShieldRiskDefenseCheckRuleFacade();
        riskDefenseCheckRuleFacade.setHttpAlterShieldClient(httpAlterShieldClient);
        return riskDefenseCheckRuleFacade;
    }

    /**
     * Create ops cloud change inner client ops cloud change inner client.
     *
     * @param heartbeatUtil the heartbeat util
     * @param alterShieldChangeFacade the ops cloud change facade
     * @return the ops cloud change inner client
     */
    protected ChangeInnerClient createChangeInnerClient(HeartbeatUtil heartbeatUtil,
                                                        ChangeFacade alterShieldChangeFacade) {
        ChangeInnerClientImpl innerClient =
                new ChangeInnerClientImpl();
        innerClient.setHeartbeatUtil(heartbeatUtil);
        innerClient.setHiddenServerError(hiddenServerError);
        innerClient.setChangeFacade(alterShieldChangeFacade);
        return innerClient;
    }

    /**
     * Create ops cloud meta change scene client ops cloud meta change scene client.
     *
     * @param opsCloudMetaChangeSceneFacade the ops cloud meta change scene facade
     * @return the ops cloud meta change scene client
     */
    protected MetaChangeSceneClient createOpsCloudMetaChangeSceneClient(
            MetaChangeSceneFacade opsCloudMetaChangeSceneFacade) {
        MetaChangeSceneClientImpl metaChangeSceneClient =
                new MetaChangeSceneClientImpl();
        metaChangeSceneClient
                .setMetaChangeSceneFacade(opsCloudMetaChangeSceneFacade);
        return metaChangeSceneClient;
    }

    /**
     * Create ops cloud change client ops cloud change client.
     *
     * @param changeInnerClient the ops cloud change inner client
     * @return the ops cloud change client
     */
    protected ChangeClient createOpsCloudChangeClient(
            ChangeInnerClient changeInnerClient) {
        ChangeClientImpl changeClient = new ChangeClientImpl();
        changeClient.setChangeInnerClient(changeInnerClient);
        return changeClient;
    }

    /**
     * Create ops cloud simple change client ops cloud simple change client.
     *
     * @param changeInnerClient the ops cloud change inner client
     * @return the ops cloud simple change client
     */
    protected SimpleChangeClient createOpsCloudSimpleChangeClient(
            ChangeInnerClient changeInnerClient) {
        SimpleChangeClientImpl simpleChangeClient =
                new SimpleChangeClientImpl();
        simpleChangeClient.setChangeInnerClient(changeInnerClient);
        return simpleChangeClient;
    }

    /**
     * Create ops cloud change notify client ops cloud change notify client.
     *
     * @param opsCloudChangeInnerClient the ops cloud change inner client
     * @return the ops cloud change notify client
     */
    protected ChangeNotifyClient createOpsCloudChangeNotifyClient(
            ChangeInnerClient opsCloudChangeInnerClient) {
        ChangeNotifyClientImpl opsCloudChangeNotifyClient =
                new ChangeNotifyClientImpl();
        opsCloudChangeNotifyClient.setChangeInnerClient(opsCloudChangeInnerClient);
        return opsCloudChangeNotifyClient;
    }

    /**
     * Create ops cloud api switch ops cloud api switch.
     *
     * @param heartbeatUtil the heartbeat util
     * @return the ops cloud api switch
     */
    protected ApiSwitch createOpsCloudApiSwitch(HeartbeatUtil heartbeatUtil) {
        ApiSwitchImpl opsCloudApiSwitch = new ApiSwitchImpl();
        opsCloudApiSwitch.setHeartbeatUtil(heartbeatUtil);
        return opsCloudApiSwitch;
    }

}
