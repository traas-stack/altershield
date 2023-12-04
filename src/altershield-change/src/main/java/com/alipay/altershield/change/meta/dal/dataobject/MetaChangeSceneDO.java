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
package com.alipay.altershield.change.meta.dal.dataobject;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author xiangyue
 */
public class MetaChangeSceneDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   租户
     *
     *
     * @mbg.generated
     */
    private String tenantCode;

    /**
     * Database Column Remarks:
     *   变更场景服务的租户 多个用逗号隔开
     *
     *
     * @mbg.generated
     */
    private String serverTenantCode;

    /**
     * Database Column Remarks:
     *   变更场景名字
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   场景key,一个场景的唯一标识
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   场景负责人，多个用逗号隔开
     *
     *
     * @mbg.generated
     */
    private String owner;

    /**
     * Database Column Remarks:
     *   场景代G；G0,G1,G2,G3,G4
     *
     *
     * @mbg.generated
     */
    private String generation;

    /**
     * Database Column Remarks:
     *   风险等级
     *
     *
     * @mbg.generated
     */
    private String riskInfo;

    /**
     * Database Column Remarks:
     *   平台名字，altershield_meta_platform
     *
     *
     * @mbg.generated
     */
    private String platformName;

    /**
     * Database Column Remarks:
     *   变更场景范围。SaaS,PaaS,IaaS
     *
     *
     * @mbg.generated
     */
    private String scope;

    /**
     * Database Column Remarks:
     *   变更场景描述
     *
     *
     * @mbg.generated
     */
    private String description;

    /**
     * Database Column Remarks:
     *   变更生效载体类型;多个用逗号隔开
     *
     *
     * @mbg.generated
     */
    private String effectiveTargetType;

    /**
     * Database Column Remarks:
     *   变更内容类型
     *
     *
     * @mbg.generated
     */
    private String changeContentType;

    /**
     * Database Column Remarks:
     *   变更对象类型
     *
     *
     * @mbg.generated
     */
    private String changeTargetType;

    /**
     * Database Column Remarks:
     *   变更场景状态 0.暂存态 1.发布状态
     *
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   变更生效配置详情
     *
     *
     * @mbg.generated
     */
    private String changeEffectiveConfigJsonRef;

    /**
     * Database Column Remarks:
     *   更回调配置
     *
     *
     * @mbg.generated
     */
    private String callbackConfigJsonRef;

    /**
     * Database Column Remarks:
     *   变更场景标签
     *
     *
     * @mbg.generated
     */
    private String tagsJsonRef;

    private List<MetaChangeStepDO> changeSteps;

    public List<MetaChangeStepDO> getChangeSteps() {
        return changeSteps;
    }

    public void setChangeSteps(List<MetaChangeStepDO> changeSteps) {
        this.changeSteps = changeSteps;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for altershield_meta_change_scene.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_meta_change_scene.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_meta_change_scene.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.tenant_code
     *
     * @mbg.generated
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     *
     * @param tenantCode the value for altershield_meta_change_scene.tenant_code
     *
     * @mbg.generated
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.server_tenant_code
     *
     * @mbg.generated
     */
    public String getServerTenantCode() {
        return serverTenantCode;
    }

    /**
     *
     * @param serverTenantCode the value for altershield_meta_change_scene.server_tenant_code
     *
     * @mbg.generated
     */
    public void setServerTenantCode(String serverTenantCode) {
        this.serverTenantCode = serverTenantCode;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for altershield_meta_change_scene.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.change_scene_key
     *
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     *
     * @param changeSceneKey the value for altershield_meta_change_scene.change_scene_key
     *
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.owner
     *
     * @mbg.generated
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner the value for altershield_meta_change_scene.owner
     *
     * @mbg.generated
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.generation
     *
     * @mbg.generated
     */
    public String getGeneration() {
        return generation;
    }

    /**
     *
     * @param generation the value for altershield_meta_change_scene.generation
     *
     * @mbg.generated
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.risk_info
     *
     * @mbg.generated
     */
    public String getRiskInfo() {
        return riskInfo;
    }

    /**
     *
     * @param riskInfo the value for altershield_meta_change_scene.risk_info
     *
     * @mbg.generated
     */
    public void setRiskInfo(String riskInfo) {
        this.riskInfo = riskInfo;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.platform_name
     *
     * @mbg.generated
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     *
     * @param platformName the value for altershield_meta_change_scene.platform_name
     *
     * @mbg.generated
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.scope
     *
     * @mbg.generated
     */
    public String getScope() {
        return scope;
    }

    /**
     *
     * @param scope the value for altershield_meta_change_scene.scope
     *
     * @mbg.generated
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.description
     *
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description the value for altershield_meta_change_scene.description
     *
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.effective_target_type
     *
     * @mbg.generated
     */
    public String getEffectiveTargetType() {
        return effectiveTargetType;
    }

    /**
     *
     * @param effectiveTargetType the value for altershield_meta_change_scene.effective_target_type
     *
     * @mbg.generated
     */
    public void setEffectiveTargetType(String effectiveTargetType) {
        this.effectiveTargetType = effectiveTargetType;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.change_content_type
     *
     * @mbg.generated
     */
    public String getChangeContentType() {
        return changeContentType;
    }

    /**
     *
     * @param changeContentType the value for altershield_meta_change_scene.change_content_type
     *
     * @mbg.generated
     */
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.change_target_type
     *
     * @mbg.generated
     */
    public String getChangeTargetType() {
        return changeTargetType;
    }

    /**
     *
     * @param changeTargetType the value for altershield_meta_change_scene.change_target_type
     *
     * @mbg.generated
     */
    public void setChangeTargetType(String changeTargetType) {
        this.changeTargetType = changeTargetType;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_meta_change_scene.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.change_effective_config_json_ref
     *
     * @mbg.generated
     */
    public String getChangeEffectiveConfigJsonRef() {
        return changeEffectiveConfigJsonRef;
    }

    /**
     *
     * @param changeEffectiveConfigJsonRef the value for altershield_meta_change_scene.change_effective_config_json_ref
     *
     * @mbg.generated
     */
    public void setChangeEffectiveConfigJsonRef(String changeEffectiveConfigJsonRef) {
        this.changeEffectiveConfigJsonRef = changeEffectiveConfigJsonRef;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.callback_config_json_ref
     *
     * @mbg.generated
     */
    public String getCallbackConfigJsonRef() {
        return callbackConfigJsonRef;
    }

    /**
     *
     * @param callbackConfigJsonRef the value for altershield_meta_change_scene.callback_config_json_ref
     *
     * @mbg.generated
     */
    public void setCallbackConfigJsonRef(String callbackConfigJsonRef) {
        this.callbackConfigJsonRef = callbackConfigJsonRef;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.tags_json_ref
     *
     * @mbg.generated
     */
    public String getTagsJsonRef() {
        return tagsJsonRef;
    }

    /**
     *
     * @param tagsJsonRef the value for altershield_meta_change_scene.tags_json_ref
     *
     * @mbg.generated
     */
    public void setTagsJsonRef(String tagsJsonRef) {
        this.tagsJsonRef = tagsJsonRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof MetaChangeSceneDO)) { return false; }
        if (!super.equals(o)) { return false; }
        MetaChangeSceneDO that = (MetaChangeSceneDO) o;
        return Objects.equals(riskInfo, that.riskInfo) &&
                Objects.equals(changeEffectiveConfigJsonRef, that.changeEffectiveConfigJsonRef) &&
                Objects.equals(description, that.description) &&
                Objects.equals(callbackConfigJsonRef, that.callbackConfigJsonRef) &&
                Objects.equals(scope, that.scope) &&
                Objects.equals(tagsJsonRef, that.tagsJsonRef) &&
                Objects.equals(serverTenantCode, that.serverTenantCode) &&
                Objects.equals(changeTargetType, that.changeTargetType) &&
                Objects.equals(effectiveTargetType, that.effectiveTargetType) &&
                Objects.equals(changeContentType, that.changeContentType) &&
                Objects.equals(changeSteps, that.changeSteps);
    }

    @Override
    public String toString() {
        return "MetaChangeSceneDO{" +
                "riskInfo='" + riskInfo + '\'' +
                ", changeEffectiveConfigJsonRef='" + changeEffectiveConfigJsonRef + '\'' +
                ", description='" + description + '\'' +
                ", callbackConfigJsonRef='" + callbackConfigJsonRef + '\'' +
                ", scope='" + scope + '\'' +
                ", tagsJsonRef='" + tagsJsonRef + '\'' +
                ", serverTntCode='" + serverTenantCode + '\'' +
                ", changeTargetType='" + changeTargetType + '\'' +
                ", effectiveTargetType='" + effectiveTargetType + '\'' +
                ", changeContentType='" + changeContentType + '\'' +
                ", changeSteps=" + changeSteps +
                "} " + super.toString();
    }
}