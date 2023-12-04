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
package com.alipay.altershield.framework.core.change.facade.request;

import com.alipay.altershield.framework.common.validate.StringLength;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * The type Base meta change scene request.
 *
 * @author yuanji
 * @version : BaseMetaChangeSceneRequest.java, v 0.1 2022年03月15日 7:45 下午 yuanji Exp $
 */
public class BaseMetaChangeSceneRequest  {

    /**
     * 租户，在一个云上是唯一标识
     */
    @StringLength(max = 64)
    @NotNull
    private String tenantCode;

    /**
     * 服务租户；多个租户用逗号隔开
     */
    @StringLength(max = 4096)
    @NotNull
    private String serverTenantCode;

    /**
     * 变更场景名
     */
    @NotNull
    @StringLength(max = 256)
    private String name;
    /**
     * 变更场景负责人
     */
    @NotNull
    @StringLength(max = 512)
    private String owner;
    /**
     * 变更场景归属的平台.例如:appName
     */
    @NotNull
    private String platformName;
    /**
     * 变更场景风险等级
     */
    @NotNull
    private String riskInfo;
    /**
     * 变更场景代G
     */
    @NotNull
    private String generation;
    /**
     * 变更范围, SaaS, PaaS,IaaS
     */
    @NotNull
    private String scope;

    /**
     * 变更场景描述
     */
    @StringLength(max = 4096)
    private String description;
    /**
     * 变更内容类型
     */
    @StringLength(max = 128)
    private String changeContentType;

    /**
     * 变更对象类型
     */
    @StringLength(max = 128)
    private String changeTargetType;

    /**
     * 生效载体类型,多个内容用逗号分开
     */
    @StringLength(max = 4096)
    private String  effectiveTargetType;

    /**
     * 场景标签信息
     */
    private Map<String, List<String>> tags;
    /**
     * Gets tenant code.
     *
     * @return the tenant code
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     * Sets tenant code.
     *
     * @param tenantCode the tenant code
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     * Gets server tenant code.
     *
     * @return the server tenant code
     */
    public String getServerTenantCode() {
        return serverTenantCode;
    }

    /**
     * Sets server tenant code.
     *
     * @param serverTenantCode the server tenant code
     */
    public void setServerTenantCode(String serverTenantCode) {
        this.serverTenantCode = serverTenantCode;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets platform name.
     *
     * @return the platform name
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * Sets platform name.
     *
     * @param platformName the platform name
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * Gets risk info.
     *
     * @return the risk info
     */
    public String getRiskInfo() {
        return riskInfo;
    }

    /**
     * Sets risk info.
     *
     * @param riskInfo the risk info
     */
    public void setRiskInfo(String riskInfo) {
        this.riskInfo = riskInfo;
    }

    /**
     * Gets generation.
     *
     * @return the generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * Sets generation.
     *
     * @param generation the generation
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * Gets scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets scope.
     *
     * @param scope the scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets change content type.
     *
     * @return the change content type
     */
    public String getChangeContentType() {
        return changeContentType;
    }

    /**
     * Sets change content type.
     *
     * @param changeContentType the change content type
     */
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    /**
     * Gets change target type.
     *
     * @return the change target type
     */
    public String getChangeTargetType() {
        return changeTargetType;
    }

    /**
     * Sets change target type.
     *
     * @param changeTargetType the change target type
     */
    public void setChangeTargetType(String changeTargetType) {
        this.changeTargetType = changeTargetType;
    }

    /**
     * Gets effective target type.
     *
     * @return the effective target type
     */
    public String getEffectiveTargetType() {
        return effectiveTargetType;
    }

    /**
     * Sets effective target type.
     *
     * @param effectiveTargetType the effective target type
     */
    public void setEffectiveTargetType(String effectiveTargetType) {
        this.effectiveTargetType = effectiveTargetType;
    }

    public Map<String, List<String>> getTags() {
        return tags;
    }

    public void setTags(Map<String, List<String>> tags) {
        this.tags = tags;
    }
}