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
public class ExeChangeOrderDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String orderId;

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
     *   业务工单id
     *
     *
     * @mbg.generated
     */
    private String bizExecOrderId;

    /**
     * Database Column Remarks:
     *   场景key
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   变更平台名
     *
     *
     * @mbg.generated
     */
    private String platform;

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
     *   分区位;提供给node使用
     *
     *
     * @mbg.generated
     */
    private String uid;

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
     *   变更标题
     *
     *
     * @mbg.generated
     */
    private String changeTitle;

    /**
     * Database Column Remarks:
     *   变更地址
     *
     *
     * @mbg.generated
     */
    private String changeUrl;

    /**
     * Database Column Remarks:
     *   变更情景码
     *
     *
     * @mbg.generated
     */
    private String changeScenarioCode;

    /**
     * Database Column Remarks:
     *   变更单状态
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   变更执行单创建人
     *
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * Database Column Remarks:
     *   变更执行单执行人
     *
     *
     * @mbg.generated
     */
    private String executor;

    /**
     * Database Column Remarks:
     *   变更阶段（环境），多个用逗号隔开
     *
     *
     * @mbg.generated
     */
    private String changePhases;

    /**
     * Database Column Remarks:
     *   变更消息
     *
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * Database Column Remarks:
     *   可信租户
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
     *   来源工单id
     *
     *
     * @mbg.generated
     */
    private String referId;

    /**
     * Database Column Remarks:
     *   来源租户
     *
     *
     * @mbg.generated
     */
    private String fromTenantCode;

    /**
     * Database Column Remarks:
     *   变更计划开始时间
     *
     *
     * @mbg.generated
     */
    private Date planStartTime;

    /**
     * Database Column Remarks:
     *   变更实际执行时间
     *
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * Database Column Remarks:
     *   上下文扩展信息
     *
     *
     * @mbg.generated
     */
    private String contextRef;

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
     *   变更单调度组
     *
     *
     * @mbg.generated
     */
    private String dispatchGroup;

    /**
     * Database Column Remarks:
     *   变更参数
     *
     *
     * @mbg.generated
     */
    private String paramRef;

    /**
     * Database Column Remarks:
     *   影响的应用
     *
     *
     * @mbg.generated
     */
    private String changeAppsRef;

    /**
     * Database Column Remarks:
     *   变更内容对象
     *
     *
     * @mbg.generated
     */
    private String changeContentRef;

    /**
     * Database Column Remarks:
     *   变更父工单信息。json格式
     *
     *
     * @mbg.generated
     */
    private String parentOrderInfoRef;

    /**
     * Database Column Remarks:
     *   变更目标对象
     *
     *
     * @mbg.generated
     */
    private String changeTargetRef;

    /**
     * Database Column Remarks:
     *   变更结果json
     *
     *
     * @mbg.generated
     */
    private String rstRef;

    /**
     *
     * @return the value of altershield_exe_change_order.order_id
     *
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId the value for altershield_exe_change_order.order_id
     *
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_exe_change_order.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_exe_change_order.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.biz_exec_order_id
     *
     * @mbg.generated
     */
    public String getBizExecOrderId() {
        return bizExecOrderId;
    }

    /**
     *
     * @param bizExecOrderId the value for altershield_exe_change_order.biz_exec_order_id
     *
     * @mbg.generated
     */
    public void setBizExecOrderId(String bizExecOrderId) {
        this.bizExecOrderId = bizExecOrderId;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_scene_key
     *
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     *
     * @param changeSceneKey the value for altershield_exe_change_order.change_scene_key
     *
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.platform
     *
     * @mbg.generated
     */
    public String getPlatform() {
        return platform;
    }

    /**
     *
     * @param platform the value for altershield_exe_change_order.platform
     *
     * @mbg.generated
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.trace_id
     *
     * @mbg.generated
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     *
     * @param traceId the value for altershield_exe_change_order.trace_id
     *
     * @mbg.generated
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.uid
     *
     * @mbg.generated
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid the value for altershield_exe_change_order.uid
     *
     * @mbg.generated
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.coord
     *
     * @mbg.generated
     */
    public String getCoord() {
        return coord;
    }

    /**
     *
     * @param coord the value for altershield_exe_change_order.coord
     *
     * @mbg.generated
     */
    public void setCoord(String coord) {
        this.coord = coord;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_title
     *
     * @mbg.generated
     */
    public String getChangeTitle() {
        return changeTitle;
    }

    /**
     *
     * @param changeTitle the value for altershield_exe_change_order.change_title
     *
     * @mbg.generated
     */
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_url
     *
     * @mbg.generated
     */
    public String getChangeUrl() {
        return changeUrl;
    }

    /**
     *
     * @param changeUrl the value for altershield_exe_change_order.change_url
     *
     * @mbg.generated
     */
    public void setChangeUrl(String changeUrl) {
        this.changeUrl = changeUrl;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_scenario_code
     *
     * @mbg.generated
     */
    public String getChangeScenarioCode() {
        return changeScenarioCode;
    }

    /**
     *
     * @param changeScenarioCode the value for altershield_exe_change_order.change_scenario_code
     *
     * @mbg.generated
     */
    public void setChangeScenarioCode(String changeScenarioCode) {
        this.changeScenarioCode = changeScenarioCode;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_exe_change_order.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.creator
     *
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @param creator the value for altershield_exe_change_order.creator
     *
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.executor
     *
     * @mbg.generated
     */
    public String getExecutor() {
        return executor;
    }

    /**
     *
     * @param executor the value for altershield_exe_change_order.executor
     *
     * @mbg.generated
     */
    public void setExecutor(String executor) {
        this.executor = executor;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_phases
     *
     * @mbg.generated
     */
    public String getChangePhases() {
        return changePhases;
    }

    /**
     *
     * @param changePhases the value for altershield_exe_change_order.change_phases
     *
     * @mbg.generated
     */
    public void setChangePhases(String changePhases) {
        this.changePhases = changePhases;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.msg
     *
     * @mbg.generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg the value for altershield_exe_change_order.msg
     *
     * @mbg.generated
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.tenant_code
     *
     * @mbg.generated
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     *
     * @param tenantCode the value for altershield_exe_change_order.tenant_code
     *
     * @mbg.generated
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.from_cloud_id
     *
     * @mbg.generated
     */
    public String getFromCloudId() {
        return fromCloudId;
    }

    /**
     *
     * @param fromCloudId the value for altershield_exe_change_order.from_cloud_id
     *
     * @mbg.generated
     */
    public void setFromCloudId(String fromCloudId) {
        this.fromCloudId = fromCloudId;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.refer_id
     *
     * @mbg.generated
     */
    public String getReferId() {
        return referId;
    }

    /**
     *
     * @param referId the value for altershield_exe_change_order.refer_id
     *
     * @mbg.generated
     */
    public void setReferId(String referId) {
        this.referId = referId;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.from_tenant_code
     *
     * @mbg.generated
     */
    public String getFromTenantCode() {
        return fromTenantCode;
    }

    /**
     *
     * @param fromTenantCode the value for altershield_exe_change_order.from_tenant_code
     *
     * @mbg.generated
     */
    public void setFromTenantCode(String fromTenantCode) {
        this.fromTenantCode = fromTenantCode;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.plan_start_time
     *
     * @mbg.generated
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     *
     * @param planStartTime the value for altershield_exe_change_order.plan_start_time
     *
     * @mbg.generated
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.start_time
     *
     * @mbg.generated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime the value for altershield_exe_change_order.start_time
     *
     * @mbg.generated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.context_ref
     *
     * @mbg.generated
     */
    public String getContextRef() {
        return contextRef;
    }

    /**
     *
     * @param contextRef the value for altershield_exe_change_order.context_ref
     *
     * @mbg.generated
     */
    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.finish_time
     *
     * @mbg.generated
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     *
     * @param finishTime the value for altershield_exe_change_order.finish_time
     *
     * @mbg.generated
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.dispatch_group
     *
     * @mbg.generated
     */
    public String getDispatchGroup() {
        return dispatchGroup;
    }

    /**
     *
     * @param dispatchGroup the value for altershield_exe_change_order.dispatch_group
     *
     * @mbg.generated
     */
    public void setDispatchGroup(String dispatchGroup) {
        this.dispatchGroup = dispatchGroup;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.param_ref
     *
     * @mbg.generated
     */
    public String getParamRef() {
        return paramRef;
    }

    /**
     *
     * @param paramRef the value for altershield_exe_change_order.param_ref
     *
     * @mbg.generated
     */
    public void setParamRef(String paramRef) {
        this.paramRef = paramRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_apps_ref
     *
     * @mbg.generated
     */
    public String getChangeAppsRef() {
        return changeAppsRef;
    }

    /**
     *
     * @param changeAppsRef the value for altershield_exe_change_order.change_apps_ref
     *
     * @mbg.generated
     */
    public void setChangeAppsRef(String changeAppsRef) {
        this.changeAppsRef = changeAppsRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_content_ref
     *
     * @mbg.generated
     */
    public String getChangeContentRef() {
        return changeContentRef;
    }

    /**
     *
     * @param changeContentRef the value for altershield_exe_change_order.change_content_ref
     *
     * @mbg.generated
     */
    public void setChangeContentRef(String changeContentRef) {
        this.changeContentRef = changeContentRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.parent_order_info_ref
     *
     * @mbg.generated
     */
    public String getParentOrderInfoRef() {
        return parentOrderInfoRef;
    }

    /**
     *
     * @param parentOrderInfoRef the value for altershield_exe_change_order.parent_order_info_ref
     *
     * @mbg.generated
     */
    public void setParentOrderInfoRef(String parentOrderInfoRef) {
        this.parentOrderInfoRef = parentOrderInfoRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.change_target_ref
     *
     * @mbg.generated
     */
    public String getChangeTargetRef() {
        return changeTargetRef;
    }

    /**
     *
     * @param changeTargetRef the value for altershield_exe_change_order.change_target_ref
     *
     * @mbg.generated
     */
    public void setChangeTargetRef(String changeTargetRef) {
        this.changeTargetRef = changeTargetRef;
    }

    /**
     *
     * @return the value of altershield_exe_change_order.rst_ref
     *
     * @mbg.generated
     */
    public String getRstRef() {
        return rstRef;
    }

    /**
     *
     * @param rstRef the value for altershield_exe_change_order.rst_ref
     *
     * @mbg.generated
     */
    public void setRstRef(String rstRef) {
        this.rstRef = rstRef;
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
        sb.append(", orderId=").append(orderId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", bizExecOrderId=").append(bizExecOrderId);
        sb.append(", changeSceneKey=").append(changeSceneKey);
        sb.append(", platform=").append(platform);
        sb.append(", traceId=").append(traceId);
        sb.append(", uid=").append(uid);
        sb.append(", coord=").append(coord);
        sb.append(", changeTitle=").append(changeTitle);
        sb.append(", changeUrl=").append(changeUrl);
        sb.append(", changeScenarioCode=").append(changeScenarioCode);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", executor=").append(executor);
        sb.append(", changePhases=").append(changePhases);
        sb.append(", msg=").append(msg);
        sb.append(", tenantCode=").append(tenantCode);
        sb.append(", fromCloudId=").append(fromCloudId);
        sb.append(", referId=").append(referId);
        sb.append(", fromTenantCode=").append(fromTenantCode);
        sb.append(", planStartTime=").append(planStartTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", contextRef=").append(contextRef);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", dispatchGroup=").append(dispatchGroup);
        sb.append(", paramRef=").append(paramRef);
        sb.append(", changeAppsRef=").append(changeAppsRef);
        sb.append(", changeContentRef=").append(changeContentRef);
        sb.append(", parentOrderInfoRef=").append(parentOrderInfoRef);
        sb.append(", changeTargetRef=").append(changeTargetRef);
        sb.append(", rstRef=").append(rstRef);
        sb.append("]");
        return sb.toString();
    }
}