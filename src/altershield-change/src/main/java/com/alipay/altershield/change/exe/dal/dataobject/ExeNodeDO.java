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
package com.alipay.altershield.change.exe.dal.dataobject;

import java.util.Date;

/**
 *
 * @author xiangyue
 */
public class ExeNodeDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String nodeExeId;

    /**
     * Database Column Remarks:
     *   traceId
     *
     *
     * @mbg.generated
     */
    private String traceId;

    /**
     * Database Column Remarks:
     *   变更单id
     *
     *
     * @mbg.generated
     */
    private String orderId;

    /**
     * Database Column Remarks:
     *   变更坐标
     *
     *
     * @mbg.generated
     */
    private String coord;

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
     *    状态
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   变更场景key标识
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   变更key标识
     *
     *
     * @mbg.generated
     */
    private String changeKey;

    /**
     * Database Column Remarks:
     *   变更类型
     *
     *
     * @mbg.generated
     */
    private String nodeType;

    /**
     * Database Column Remarks:
     *   变更环境
     *
     *
     * @mbg.generated
     */
    private String changePhase;

    /**
     * Database Column Remarks:
     *   记录更新的触发源id
     *
     *
     * @mbg.generated
     */
    private String lastTriggerId;

    /**
     * Database Column Remarks:
     *   操作人
     *
     *
     * @mbg.generated
     */
    private String executor;

    /**
     * Database Column Remarks:
     *   业务扩展字段
     *
     *
     * @mbg.generated
     */
    private String bizExt;

    /**
     * Database Column Remarks:
     *   搜索扩展字段
     *
     *
     * @mbg.generated
     */
    private String searchExtRef;

    /**
     * Database Column Remarks:
     *   变更前置和后置的AOP的checkId
     *
     *
     * @mbg.generated
     */
    private String checkIdInfo;

    /**
     * Database Column Remarks:
     *   变更开始时间
     *
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * Database Column Remarks:
     *   变更结束时间
     *
     *
     * @mbg.generated
     */
    private Date finishTime;

    /**
     * Database Column Remarks:
     *   YN表示是否应急变更
     *
     *
     * @mbg.generated
     */
    private String emergencyFlag;

    /**
     * Database Column Remarks:
     *   上下文扩展字段
     *
     *
     * @mbg.generated
     */
    private String contextRef;

    /**
     * Database Column Remarks:
     *   TLDC架构租户信息
     *
     *
     * @mbg.generated
     */
    private String tenantCode;

    /**
     * Database Column Remarks:
     *   来源云id
     *
     *
     * @mbg.generated
     */
    private String fromCloudId;

    /**
     * Database Column Remarks:
     *   来源id
     *
     *
     * @mbg.generated
     */
    private String referId;

    /**
     * Database Column Remarks:
     *   来源租户信息
     *
     *
     * @mbg.generated
     */
    private String fromTenantCode;

    /**
     * Database Column Remarks:
     *   调度分组
     *
     *
     * @mbg.generated
     */
    private String dispatchGroup;

    /**
     * Database Column Remarks:
     *   入参ref
     *
     *
     * @mbg.generated
     */
    private String paramRef;

    /**
     * Database Column Remarks:
     *   返回结果ref
     *
     *
     * @mbg.generated
     */
    private String rstRef;

    /**
     * Database Column Remarks:
     *   节点执行备注
     *
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * Database Column Remarks:
     *   变更内容
     *
     *
     * @mbg.generated
     */
    private String changeContentRef;

    /**
     * Database Column Remarks:
     *   变更生效信息
     *
     *
     * @mbg.generated
     */
    private String changeEffectiveInfoRef;

    /**
     * Database Column Remarks:
     *   变更目标
     *
     *
     * @mbg.generated
     */
    private String changeTargetRef;

    /**
     *
     * @return the value of altershield_exe_node.node_exe_id
     *
     * @mbg.generated
     */
    public String getNodeExeId() {
        return nodeExeId;
    }

    /**
     *
     * @param nodeExeId the value for altershield_exe_node.node_exe_id
     *
     * @mbg.generated
     */
    public void setNodeExeId(String nodeExeId) {
        this.nodeExeId = nodeExeId;
    }

    /**
     *
     * @return the value of altershield_exe_node.trace_id
     *
     * @mbg.generated
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     *
     * @param traceId the value for altershield_exe_node.trace_id
     *
     * @mbg.generated
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     *
     * @return the value of altershield_exe_node.order_id
     *
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId the value for altershield_exe_node.order_id
     *
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return the value of altershield_exe_node.coord
     *
     * @mbg.generated
     */
    public String getCoord() {
        return coord;
    }

    /**
     *
     * @param coord the value for altershield_exe_node.coord
     *
     * @mbg.generated
     */
    public void setCoord(String coord) {
        this.coord = coord;
    }

    /**
     *
     * @return the value of altershield_exe_node.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_exe_node.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_exe_node.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_exe_node.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_exe_node.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_exe_node.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_scene_key
     *
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     *
     * @param changeSceneKey the value for altershield_exe_node.change_scene_key
     *
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_key
     *
     * @mbg.generated
     */
    public String getChangeKey() {
        return changeKey;
    }

    /**
     *
     * @param changeKey the value for altershield_exe_node.change_key
     *
     * @mbg.generated
     */
    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    /**
     *
     * @return the value of altershield_exe_node.node_type
     *
     * @mbg.generated
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     *
     * @param nodeType the value for altershield_exe_node.node_type
     *
     * @mbg.generated
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_phase
     *
     * @mbg.generated
     */
    public String getChangePhase() {
        return changePhase;
    }

    /**
     *
     * @param changePhase the value for altershield_exe_node.change_phase
     *
     * @mbg.generated
     */
    public void setChangePhase(String changePhase) {
        this.changePhase = changePhase;
    }

    /**
     *
     * @return the value of altershield_exe_node.last_trigger_id
     *
     * @mbg.generated
     */
    public String getLastTriggerId() {
        return lastTriggerId;
    }

    /**
     *
     * @param lastTriggerId the value for altershield_exe_node.last_trigger_id
     *
     * @mbg.generated
     */
    public void setLastTriggerId(String lastTriggerId) {
        this.lastTriggerId = lastTriggerId;
    }

    /**
     *
     * @return the value of altershield_exe_node.executor
     *
     * @mbg.generated
     */
    public String getExecutor() {
        return executor;
    }

    /**
     *
     * @param executor the value for altershield_exe_node.executor
     *
     * @mbg.generated
     */
    public void setExecutor(String executor) {
        this.executor = executor;
    }

    /**
     *
     * @return the value of altershield_exe_node.biz_ext
     *
     * @mbg.generated
     */
    public String getBizExt() {
        return bizExt;
    }

    /**
     *
     * @param bizExt the value for altershield_exe_node.biz_ext
     *
     * @mbg.generated
     */
    public void setBizExt(String bizExt) {
        this.bizExt = bizExt;
    }

    /**
     *
     * @return the value of altershield_exe_node.search_ext_ref
     *
     * @mbg.generated
     */
    public String getSearchExtRef() {
        return searchExtRef;
    }

    /**
     *
     * @param searchExtRef the value for altershield_exe_node.search_ext_ref
     *
     * @mbg.generated
     */
    public void setSearchExtRef(String searchExtRef) {
        this.searchExtRef = searchExtRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.check_id_info
     *
     * @mbg.generated
     */
    public String getCheckIdInfo() {
        return checkIdInfo;
    }

    /**
     *
     * @param checkIdInfo the value for altershield_exe_node.check_id_info
     *
     * @mbg.generated
     */
    public void setCheckIdInfo(String checkIdInfo) {
        this.checkIdInfo = checkIdInfo;
    }

    /**
     *
     * @return the value of altershield_exe_node.start_time
     *
     * @mbg.generated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime the value for altershield_exe_node.start_time
     *
     * @mbg.generated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return the value of altershield_exe_node.finish_time
     *
     * @mbg.generated
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     *
     * @param finishTime the value for altershield_exe_node.finish_time
     *
     * @mbg.generated
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     *
     * @return the value of altershield_exe_node.emergency_flag
     *
     * @mbg.generated
     */
    public String getEmergencyFlag() {
        return emergencyFlag;
    }

    /**
     *
     * @param emergencyFlag the value for altershield_exe_node.emergency_flag
     *
     * @mbg.generated
     */
    public void setEmergencyFlag(String emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    /**
     *
     * @return the value of altershield_exe_node.context_ref
     *
     * @mbg.generated
     */
    public String getContextRef() {
        return contextRef;
    }

    /**
     *
     * @param contextRef the value for altershield_exe_node.context_ref
     *
     * @mbg.generated
     */
    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.tenant_code
     *
     * @mbg.generated
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     *
     * @param tenantCode the value for altershield_exe_node.tenant_code
     *
     * @mbg.generated
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     *
     * @return the value of altershield_exe_node.from_cloud_id
     *
     * @mbg.generated
     */
    public String getFromCloudId() {
        return fromCloudId;
    }

    /**
     *
     * @param fromCloudId the value for altershield_exe_node.from_cloud_id
     *
     * @mbg.generated
     */
    public void setFromCloudId(String fromCloudId) {
        this.fromCloudId = fromCloudId;
    }

    /**
     *
     * @return the value of altershield_exe_node.refer_Id
     *
     * @mbg.generated
     */
    public String getReferId() {
        return referId;
    }

    /**
     *
     * @param referId the value for altershield_exe_node.refer_Id
     *
     * @mbg.generated
     */
    public void setReferId(String referId) {
        this.referId = referId;
    }

    /**
     *
     * @return the value of altershield_exe_node.from_tenant_code
     *
     * @mbg.generated
     */
    public String getFromTenantCode() {
        return fromTenantCode;
    }

    /**
     *
     * @param fromTenantCode the value for altershield_exe_node.from_tenant_code
     *
     * @mbg.generated
     */
    public void setFromTenantCode(String fromTenantCode) {
        this.fromTenantCode = fromTenantCode;
    }

    /**
     *
     * @return the value of altershield_exe_node.dispatch_group
     *
     * @mbg.generated
     */
    public String getDispatchGroup() {
        return dispatchGroup;
    }

    /**
     *
     * @param dispatchGroup the value for altershield_exe_node.dispatch_group
     *
     * @mbg.generated
     */
    public void setDispatchGroup(String dispatchGroup) {
        this.dispatchGroup = dispatchGroup;
    }

    /**
     *
     * @return the value of altershield_exe_node.param_ref
     *
     * @mbg.generated
     */
    public String getParamRef() {
        return paramRef;
    }

    /**
     *
     * @param paramRef the value for altershield_exe_node.param_ref
     *
     * @mbg.generated
     */
    public void setParamRef(String paramRef) {
        this.paramRef = paramRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.rst_ref
     *
     * @mbg.generated
     */
    public String getRstRef() {
        return rstRef;
    }

    /**
     *
     * @param rstRef the value for altershield_exe_node.rst_ref
     *
     * @mbg.generated
     */
    public void setRstRef(String rstRef) {
        this.rstRef = rstRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.msg
     *
     * @mbg.generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg the value for altershield_exe_node.msg
     *
     * @mbg.generated
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_content_ref
     *
     * @mbg.generated
     */
    public String getChangeContentRef() {
        return changeContentRef;
    }

    /**
     *
     * @param changeContentRef the value for altershield_exe_node.change_content_ref
     *
     * @mbg.generated
     */
    public void setChangeContentRef(String changeContentRef) {
        this.changeContentRef = changeContentRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_effective_info_ref
     *
     * @mbg.generated
     */
    public String getChangeEffectiveInfoRef() {
        return changeEffectiveInfoRef;
    }

    /**
     *
     * @param changeEffectiveInfoRef the value for altershield_exe_node.change_effective_info_ref
     *
     * @mbg.generated
     */
    public void setChangeEffectiveInfoRef(String changeEffectiveInfoRef) {
        this.changeEffectiveInfoRef = changeEffectiveInfoRef;
    }

    /**
     *
     * @return the value of altershield_exe_node.change_target_ref
     *
     * @mbg.generated
     */
    public String getChangeTargetRef() {
        return changeTargetRef;
    }

    /**
     *
     * @param changeTargetRef the value for altershield_exe_node.change_target_ref
     *
     * @mbg.generated
     */
    public void setChangeTargetRef(String changeTargetRef) {
        this.changeTargetRef = changeTargetRef;
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
        sb.append(", nodeExeId=").append(nodeExeId);
        sb.append(", traceId=").append(traceId);
        sb.append(", orderId=").append(orderId);
        sb.append(", coord=").append(coord);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", status=").append(status);
        sb.append(", changeSceneKey=").append(changeSceneKey);
        sb.append(", changeKey=").append(changeKey);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", changePhase=").append(changePhase);
        sb.append(", lastTriggerId=").append(lastTriggerId);
        sb.append(", executor=").append(executor);
        sb.append(", bizExt=").append(bizExt);
        sb.append(", searchExtRef=").append(searchExtRef);
        sb.append(", checkIdInfo=").append(checkIdInfo);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", emergencyFlag=").append(emergencyFlag);
        sb.append(", contextRef=").append(contextRef);
        sb.append(", tenantCode=").append(tenantCode);
        sb.append(", fromCloudId=").append(fromCloudId);
        sb.append(", referId=").append(referId);
        sb.append(", fromTenantCode=").append(fromTenantCode);
        sb.append(", dispatchGroup=").append(dispatchGroup);
        sb.append(", paramRef=").append(paramRef);
        sb.append(", rstRef=").append(rstRef);
        sb.append(", msg=").append(msg);
        sb.append(", changeContentRef=").append(changeContentRef);
        sb.append(", changeEffectiveInfoRef=").append(changeEffectiveInfoRef);
        sb.append(", changeTargetRef=").append(changeTargetRef);
        sb.append("]");
        return sb.toString();
    }
}