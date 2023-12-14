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
package com.alipay.altershield.defender.changewindow.dal.dataobject;

import java.util.Date;

/**
 *
 * @author shengzhang
 */
public class MetaReleaseChangeDO {
    /**
     * Database Column Remarks: 主键
     *
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks: 创建时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks: 修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks: 破网申请操作人
     *
     *
     * @mbg.generated
     */
    private String operator;

    /**
     * Database Column Remarks: 破网申请名称
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks: 破网对应的封网计划ID
     *
     *
     * @mbg.generated
     */
    private String blockChangeId;

    /**
     * Database Column Remarks: 破网对应的封网计划名称
     *
     *
     * @mbg.generated
     */
    private String blockChangeName;

    /**
     * Database Column Remarks: 破网开始时间
     *
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * Database Column Remarks: 破网结束时间
     *
     *
     * @mbg.generated
     */
    private Date endTime;

    /**
     * Database Column Remarks: 生效环境
     *
     *
     * @mbg.generated
     */
    private String effectEnvironment;

    /**
     * Database Column Remarks: 破网原因
     *
     *
     * @mbg.generated
     */
    private String reasonRef;

    /**
     * Database Column Remarks: 破网架构域范围
     *
     *
     * @mbg.generated
     */
    private String archdomainRange;

    /**
     * Database Column Remarks: 破网业务域范围
     *
     *
     * @mbg.generated
     */
    private String busgroupRange;

    /**
     * Database Column Remarks: 破网类型
     *
     *
     * @mbg.generated
     */
    private String changeTargetType;

    /**
     * Database Column Remarks: 破网对象配置
     *
     *
     * @mbg.generated
     */
    private String configRef;

    /**
     * Database Column Remarks: 破网申请状态
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks: 审批流Id
     *
     *
     * @mbg.generated
     */
    private String approvalFlowId;

    /**
     * Database Column Remarks: 破网原因类型
     *
     *
     * @mbg.generated
     */
    private String reasonType;

    /**
     * Database Column Remarks: 业务影响
     *
     *
     * @mbg.generated
     */
    private String bizInfluence;

    /**
     * Database Column Remarks: 风险描述和应急预案
     *
     *
     * @mbg.generated
     */
    private String riskDescription;

    /**
     * Database Column Remarks: 站点
     *
     *
     * @mbg.generated
     */
    private String tenant;

    /**
     *
     * @return the value of opscld_meta_releasechange.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for opscld_meta_releasechange.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for opscld_meta_releasechange.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for opscld_meta_releasechange.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.operator
     *
     * @mbg.generated
     */
    public String getOperator() {
        return operator;
    }

    /**
     *
     * @param operator the value for opscld_meta_releasechange.operator
     *
     * @mbg.generated
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for opscld_meta_releasechange.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getBlockChangeId() {
        return blockChangeId;
    }

    public void setBlockChangeId(String blockChangeId) {
        this.blockChangeId = blockChangeId;
    }

    public String getBlockChangeName() {
        return blockChangeName;
    }

    public void setBlockChangeName(String blockChangeName) {
        this.blockChangeName = blockChangeName;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.start_time
     *
     * @mbg.generated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime the value for opscld_meta_releasechange.start_time
     *
     * @mbg.generated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.end_time
     *
     * @mbg.generated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime the value for opscld_meta_releasechange.end_time
     *
     * @mbg.generated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.effect_environment
     *
     * @mbg.generated
     */
    public String getEffectEnvironment() {
        return effectEnvironment;
    }

    /**
     *
     * @param effectEnvironment the value for opscld_meta_releasechange.effect_environment
     *
     * @mbg.generated
     */
    public void setEffectEnvironment(String effectEnvironment) {
        this.effectEnvironment = effectEnvironment;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.reason_ref
     *
     * @mbg.generated
     */
    public String getReasonRef() {
        return reasonRef;
    }

    /**
     *
     * @param reasonRef the value for opscld_meta_releasechange.reason_ref
     *
     * @mbg.generated
     */
    public void setReasonRef(String reasonRef) {
        this.reasonRef = reasonRef;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.archdomain_range
     *
     * @mbg.generated
     */
    public String getArchdomainRange() {
        return archdomainRange;
    }

    /**
     *
     * @param archdomainRange the value for opscld_meta_releasechange.archdomain_range
     *
     * @mbg.generated
     */
    public void setArchdomainRange(String archdomainRange) {
        this.archdomainRange = archdomainRange;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.busgroup_range
     *
     * @mbg.generated
     */
    public String getBusgroupRange() {
        return busgroupRange;
    }

    /**
     *
     * @param busgroupRange the value for opscld_meta_releasechange.busgroup_range
     *
     * @mbg.generated
     */
    public void setBusgroupRange(String busgroupRange) {
        this.busgroupRange = busgroupRange;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.change_target_type
     *
     * @mbg.generated
     */
    public String getChangeTargetType() {
        return changeTargetType;
    }

    /**
     *
     * @param changeTargetType the value for opscld_meta_releasechange.change_target_type
     *
     * @mbg.generated
     */
    public void setChangeTargetType(String changeTargetType) {
        this.changeTargetType = changeTargetType;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.config_ref
     *
     * @mbg.generated
     */
    public String getConfigRef() {
        return configRef;
    }

    /**
     *
     * @param configRef the value for opscld_meta_releasechange.config_ref
     *
     * @mbg.generated
     */
    public void setConfigRef(String configRef) {
        this.configRef = configRef;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for opscld_meta_releasechange.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.approval_flow_id
     *
     * @mbg.generated
     */
    public String getApprovalFlowId() {
        return approvalFlowId;
    }

    /**
     *
     * @param approvalFlowId the value for opscld_meta_releasechange.approval_flow_id
     *
     * @mbg.generated
     */
    public void setApprovalFlowId(String approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.reason_type
     *
     * @mbg.generated
     */
    public String getReasonType() {
        return reasonType;
    }

    /**
     *
     * @param reasonType the value for opscld_meta_releasechange.reason_type
     *
     * @mbg.generated
     */
    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.biz_influence
     *
     * @mbg.generated
     */
    public String getBizInfluence() {
        return bizInfluence;
    }

    /**
     *
     * @param bizInfluence the value for opscld_meta_releasechange.biz_influence
     *
     * @mbg.generated
     */
    public void setBizInfluence(String bizInfluence) {
        this.bizInfluence = bizInfluence;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.risk_description
     *
     * @mbg.generated
     */
    public String getRiskDescription() {
        return riskDescription;
    }

    /**
     *
     * @param riskDescription the value for opscld_meta_releasechange.risk_description
     *
     * @mbg.generated
     */
    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    /**
     *
     * @return the value of opscld_meta_releasechange.tenant
     *
     * @mbg.generated
     */
    public String getTenant() {
        return tenant;
    }

    /**
     *
     * @param tenant the value for opscld_meta_releasechange.tenant
     *
     * @mbg.generated
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", operator=").append(operator);
        sb.append(", name=").append(name);
        sb.append(", blockchangeId=").append(blockChangeId);
        sb.append(", blockchangeName=").append(blockChangeName);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", effectEnvironment=").append(effectEnvironment);
        sb.append(", reasonRef=").append(reasonRef);
        sb.append(", archdomainRange=").append(archdomainRange);
        sb.append(", busgroupRange=").append(busgroupRange);
        sb.append(", changeTargetType=").append(changeTargetType);
        sb.append(", configRef=").append(configRef);
        sb.append(", status=").append(status);
        sb.append(", approvalFlowId=").append(approvalFlowId);
        sb.append(", reasonType=").append(reasonType);
        sb.append(", bizInfluence=").append(bizInfluence);
        sb.append(", riskDescription=").append(riskDescription);
        sb.append(", tenant=").append(tenant);
        sb.append("]");
        return sb.toString();
    }
}
