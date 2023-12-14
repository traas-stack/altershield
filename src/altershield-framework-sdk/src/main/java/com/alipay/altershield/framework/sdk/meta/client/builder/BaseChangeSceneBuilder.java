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
package com.alipay.altershield.framework.sdk.meta.client.builder;

import com.alipay.altershield.framework.common.validate.StringLength;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author yuanji
 * @version : BaseChangeSceneBuilder.java, v 0.1 2022年05月26日 4:53 下午 yuanji Exp $
 */
public class BaseChangeSceneBuilder<T extends BaseChangeSceneBuilder> {


    /**
     * 变更场景key，在一个云下唯一标识 场景key只能包含字母，数字，点和下划线。同时只能以字母开头和结尾
     */
    @NotNull
    @Pattern(regexp = "^[a-z][a-z0-9\\.\\_]*[a-z0-9]$")
    @StringLength(max = 200)
    String changeSceneKey;

    @NotNull
    String tenantCode;

    /**
     * 服务租户；多个租户用逗号隔开
     */
    @StringLength(max = 4096)
    String serverTenantCode;

    /**
     * 变更场景名
     */
    @NotNull
    @StringLength(max = 256)
    String name;
    /**
     * 变更场景负责人
     */
    @NotNull
    String owner;
    /**
     * 变更场景归属的平台.例如:appName
     */
    @NotNull
    String platformName;
    /**
     * 变更场景风险等级
     */
    String riskInfo = LOW_INFO_HIGH;

    public static final String RISK_INFO_HIGH = "高风险";
    public static final String MEDIUM_INFO_HIGH = "中风险";
    public static final String LOW_INFO_HIGH = "低风险";

    /**
     * 变更场景代G
     */
    @NotNull
    String generation;
    /**
     * 变更范围, SaaS, PaaS,IaaS
     */
    @NotNull
    String scope;

    /**
     * 变更场景描述
     */
    @StringLength(max = 4096)
    String description;
    /**
     * 变更内容类型
     */
    @StringLength(max = 128)
    String changeContentType;

    /**
     * 变更对象类型
     */
    @StringLength(max = 128)
    String changeTargetType;

    /**
     * 生效载体类型,多个内容用逗号分开
     */
    @StringLength(max = 4096)
    String effectiveTargetType;

    public BaseChangeSceneBuilder() {}

    public BaseChangeSceneBuilder(@NotNull String generation) {
        this.generation = generation;
    }

    public T setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
        return (T) this;
    }

    public T setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
        return (T) this;
    }

    public T setServerTenantCode(String... serverTenantCode) {
        this.serverTenantCode = StringUtils.join(serverTenantCode, ",");
        return (T) this;
    }

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T setOwner(String... owner) {
        this.owner = StringUtils.join(owner, ",");
        return (T) this;
    }

    public T setPlatformName(String platformName) {
        this.platformName = platformName;
        return (T) this;
    }

    /**
     * 设置场景为高风险
     *
     * @return
     */
    public T setHighRiskInfo() {
        this.riskInfo = RISK_INFO_HIGH;
        return (T) this;
    }

    /**
     * 设置场景为中风险
     *
     * @return
     */
    public T setMediumRiskInfo() {
        this.riskInfo = MEDIUM_INFO_HIGH;
        return (T) this;
    }

    /**
     * 设置场景为低风险
     *
     * @return
     */
    public T setLowRiskInfo() {
        this.riskInfo = LOW_INFO_HIGH;
        return (T) this;
    }

    /**
     * 设置场景风险等级
     *
     * @return
     */
    public T setRiskInfo(String riskInfo) {
        this.riskInfo = riskInfo;
        return (T) this;
    }

    /**
     * 设置变更风险为PaaS
     *
     * @return
     */
    public T setPaasScope() {
        this.scope = "Paas";
        return (T) this;
    }

    /**
     * 设置变更风险为SaaS
     *
     * @return
     */
    public T setSaasScope() {
        this.scope = "SaaS";
        return (T) this;
    }

    /**
     * 设置变更风险为IaaS
     *
     * @return
     */
    public T setIaasScope() {
        this.scope = "IaaS";
        return (T) this;
    }

    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    public T setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
        return (T) this;
    }

    public T setChangeTargetType(String changeTargetType) {
        this.changeTargetType = changeTargetType;
        return (T) this;
    }

    public T setEffectiveTargetType(String... effectiveTargetType) {
        this.effectiveTargetType = StringUtils.join(effectiveTargetType, ",");
        return (T) this;
    }

    /**
     * 构建创建场景请求对象
     *
     * @return
     */
    public CreateMetaChangeSceneRequest build() {
        CreateMetaChangeSceneRequest createMetaChangeSceneRequest =
                new CreateMetaChangeSceneRequest();
        createMetaChangeSceneRequest.setChangeSceneKey(changeSceneKey);
        createMetaChangeSceneRequest.setChangeContentType(changeContentType);
        createMetaChangeSceneRequest.setChangeTargetType(changeTargetType);
        createMetaChangeSceneRequest.setDescription(description);
        createMetaChangeSceneRequest.setGeneration(generation);
        createMetaChangeSceneRequest.setName(name);
        createMetaChangeSceneRequest.setPlatformName(platformName);
        createMetaChangeSceneRequest.setRiskInfo(riskInfo);
        createMetaChangeSceneRequest.setScope(scope);
        createMetaChangeSceneRequest.setOwner(owner);
        createMetaChangeSceneRequest.setServerTenantCode(serverTenantCode);
        createMetaChangeSceneRequest.setTenantCode(tenantCode);
        return createMetaChangeSceneRequest;
    }
}
