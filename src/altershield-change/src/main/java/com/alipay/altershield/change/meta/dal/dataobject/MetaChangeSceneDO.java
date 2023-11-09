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

import java.util.List;

/**
 * @author jinyalong
 */
public class MetaChangeSceneDO extends MetaBaseChangeSceneDO {

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
     *   风险等级
     *
     *
     * @mbg.generated
     */
    private String riskInfo;



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
    public String toString() {
        final StringBuffer sb = new StringBuffer("MetaChangeSceneDO{");
        sb.append("serverTenantCode='").append(serverTenantCode).append('\'');
        sb.append(", riskInfo='").append(riskInfo).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", effectiveTargetType='").append(effectiveTargetType).append('\'');
        sb.append(", changeContentType='").append(changeContentType).append('\'');
        sb.append(", changeTargetType='").append(changeTargetType).append('\'');
        sb.append(", changeEffectiveConfigJsonRef='").append(changeEffectiveConfigJsonRef).append('\'');
        sb.append(", callbackConfigJsonRef='").append(callbackConfigJsonRef).append('\'');
        sb.append(", tagsJsonRef='").append(tagsJsonRef).append('\'');
        sb.append('}');
        sb.append(super.toString());
        return sb.toString();
    }
}