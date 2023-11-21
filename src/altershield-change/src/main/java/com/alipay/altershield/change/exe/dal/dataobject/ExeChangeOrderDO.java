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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.change.exe.dal.dataobject;

import java.util.Date;

/**
 * The type Exe change order do.
 *
 * @author yuanji
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
     *   变更单标题
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
     *   变更参数
     *
     *
     * @mbg.generated
     */
    private String paramRef;


    /**
     * Database Column Remarks:
     *   返回結果参数
     *
     *
     * @mbg.generated
     */
    private String rstRef;

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
     *   变更阶段
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
     *   影响的应用
     *
     *
     * @mbg.generated
     */
    private String changeAppsRef;

    /**
     * Database Column Remarks:
     *   变更计划时间
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
     *   变更实际结束时间
     *
     *
     * @mbg.generated
     */
    private Date finishTime;

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
     *   变更类标对象
     *
     *
     * @mbg.generated
     */
    private String changeTargetRef;

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
     *   上下文扩展信息
     *
     *
     * @mbg.generated
     */
    private String contextRef;

    /**
     * 父工单信息
     */
    private String parentOrderInfoRef;

    /**
     * Database Column Remarks:
     *   变更单调度的组
     *
     *
     * @mbg.generated
     */
    private String dispatchGroup;

    /**
     * Gets order id.
     *
     * @return the value of opscloud_exe_change_order.order_id
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the value for opscloud_exe_change_order.order_id
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets gmt create.
     *
     * @return the value of opscloud_exe_change_order.gmt_create
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Sets gmt create.
     *
     * @param gmtCreate the value for opscloud_exe_change_order.gmt_create
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Gets gmt modified.
     *
     * @return the value of opscloud_exe_change_order.gmt_modified
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Sets gmt modified.
     *
     * @param gmtModified the value for opscloud_exe_change_order.gmt_modified
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Gets biz exec order id.
     *
     * @return the value of opscloud_exe_change_order.biz_exec_order_id
     * @mbg.generated
     */
    public String getBizExecOrderId() {
        return bizExecOrderId;
    }

    /**
     * Sets biz exec order id.
     *
     * @param bizExecOrderId the value for opscloud_exe_change_order.biz_exec_order_id
     * @mbg.generated
     */
    public void setBizExecOrderId(String bizExecOrderId) {
        this.bizExecOrderId = bizExecOrderId;
    }

    /**
     * Gets change scene key.
     *
     * @return the value of opscloud_exe_change_order.change_scene_key
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     * Sets change scene key.
     *
     * @param changeSceneKey the value for opscloud_exe_change_order.change_scene_key
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     * Gets platform.
     *
     * @return the value of opscloud_exe_change_order.platform
     * @mbg.generated
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets platform.
     *
     * @param platform the value for opscloud_exe_change_order.platform
     * @mbg.generated
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Gets uid.
     *
     * @return the value of opscloud_exe_change_order.uid
     * @mbg.generated
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the value for opscloud_exe_change_order.uid
     * @mbg.generated
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets coord.
     *
     * @return the value of opscloud_exe_change_order.coord
     * @mbg.generated
     */
    public String getCoord() {
        return coord;
    }

    /**
     * Sets coord.
     *
     * @param coord the value for opscloud_exe_change_order.coord
     * @mbg.generated
     */
    public void setCoord(String coord) {
        this.coord = coord;
    }

    /**
     * Gets change title.
     *
     * @return the value of opscloud_exe_change_order.exec_order_title
     * @mbg.generated
     */
    public String getChangeTitle() {
        return changeTitle;
    }

    /**
     * Sets change title.
     *
     * @param changeTitle the value for opscloud_exe_change_order.exec_order_title
     * @mbg.generated
     */
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }

    /**
     * Gets change url.
     *
     * @return the value of opscloud_exe_change_order.exec_order_url
     * @mbg.generated
     */
    public String getChangeUrl() {
        return changeUrl;
    }

    /**
     * Sets change url.
     *
     * @param changeUrl the value for opscloud_exe_change_order.exec_order_url
     * @mbg.generated
     */
    public void setChangeUrl(String changeUrl) {
        this.changeUrl = changeUrl;
    }

    /**
     * Gets param ref.
     *
     * @return the value of opscloud_exe_change_order.param_ref
     * @mbg.generated
     */
    public String getParamRef() {
        return paramRef;
    }

    /**
     * Sets param ref.
     *
     * @param paramRef the value for opscloud_exe_change_order.param_ref
     * @mbg.generated
     */
    public void setParamRef(String paramRef) {
        this.paramRef = paramRef;
    }

    /**
     * Gets change scenario code.
     *
     * @return the value of opscloud_exe_change_order.change_scenario_code
     * @mbg.generated
     */
    public String getChangeScenarioCode() {
        return changeScenarioCode;
    }

    /**
     * Sets change scenario code.
     *
     * @param changeScenarioCode the value for opscloud_exe_change_order.change_scenario_code
     * @mbg.generated
     */
    public void setChangeScenarioCode(String changeScenarioCode) {
        this.changeScenarioCode = changeScenarioCode;
    }

    /**
     * Gets status.
     *
     * @return the value of opscloud_exe_change_order.status
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the value for opscloud_exe_change_order.status
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets creator.
     *
     * @return the value of opscloud_exe_change_order.creator
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the value for opscloud_exe_change_order.creator
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Gets executor.
     *
     * @return the value of opscloud_exe_change_order.executor
     * @mbg.generated
     */
    public String getExecutor() {
        return executor;
    }

    /**
     * Sets executor.
     *
     * @param executor the value for opscloud_exe_change_order.executor
     * @mbg.generated
     */
    public void setExecutor(String executor) {
        this.executor = executor;
    }

    /**
     * Gets change phases.
     *
     * @return the value of opscloud_exe_change_order.change_phase
     * @mbg.generated
     */
    public String getChangePhases() {
        return changePhases;
    }

    /**
     * Sets change phases.
     *
     * @param changePhases the value for opscloud_exe_change_order.change_phase
     * @mbg.generated
     */
    public void setChangePhases(String changePhases) {
        this.changePhases = changePhases;
    }

    /**
     * Gets msg.
     *
     * @return the value of opscloud_exe_change_order.msg
     * @mbg.generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the value for opscloud_exe_change_order.msg
     * @mbg.generated
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets tldc tenant code.
     *
     * @return the value of opscloud_exe_change_order.tldc_tenant_code
     * @mbg.generated
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     * Sets tldc tenant code.
     *
     * @param tenantCode the value for opscloud_exe_change_order.tldc_tenant_code
     * @mbg.generated
     */
    public void setTldcTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     * Gets from cloud id.
     *
     * @return the value of opscloud_exe_change_order.from_cloud_id
     * @mbg.generated
     */
    public String getFromCloudId() {
        return fromCloudId;
    }

    /**
     * Sets from cloud id.
     *
     * @param fromCloudId the value for opscloud_exe_change_order.from_cloud_id
     * @mbg.generated
     */
    public void setFromCloudId(String fromCloudId) {
        this.fromCloudId = fromCloudId;
    }

    /**
     * Gets refer id.
     *
     * @return the value of opscloud_exe_change_order.refer_id
     * @mbg.generated
     */
    public String getReferId() {
        return referId;
    }

    /**
     * Sets refer id.
     *
     * @param referId the value for opscloud_exe_change_order.refer_id
     * @mbg.generated
     */
    public void setReferId(String referId) {
        this.referId = referId;
    }

    /**
     * Gets from tld tenant code.
     *
     * @return the value of opscloud_exe_change_order.from_tld_tenant_code
     * @mbg.generated
     */
    public String getFromTenantCode() {
        return fromTenantCode;
    }

    /**
     * Sets from tld tenant code.
     *
     * @param fromTenantCode the value for opscloud_exe_change_order.from_tld_tenant_code
     * @mbg.generated
     */
    public void setFromTenantCode(String fromTenantCode) {
        this.fromTenantCode = fromTenantCode;
    }

    /**
     * Gets change apps ref.
     *
     * @return the value of opscloud_exe_change_order.change_apps_ref
     * @mbg.generated
     */
    public String getChangeAppsRef() {
        return changeAppsRef;
    }

    /**
     * Sets change apps ref.
     *
     * @param changeAppsRef the value for opscloud_exe_change_order.change_apps_ref
     * @mbg.generated
     */
    public void setChangeAppsRef(String changeAppsRef) {
        this.changeAppsRef = changeAppsRef;
    }

    /**
     * Gets plan start time.
     *
     * @return the value of opscloud_exe_change_order.gmt_plan
     * @mbg.generated
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * Sets plan start time.
     *
     * @param planStartTime the value for opscloud_exe_change_order.gmt_plan
     * @mbg.generated
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets finish time.
     *
     * @return the finish time
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * Sets finish time.
     *
     * @param finishTime the finish time
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * Gets dispatch group.
     *
     * @return the dispatch group
     */
    public String getDispatchGroup() {
        return dispatchGroup;
    }

    /**
     * Sets dispatch group.
     *
     * @param dispatchGroup the dispatch group
     */
    public void setDispatchGroup(String dispatchGroup) {
        this.dispatchGroup = dispatchGroup;
    }

    /**
     * Gets change content ref.
     *
     * @return the value of opscloud_exe_change_order.change_content_ref
     * @mbg.generated
     */
    public String getChangeContentRef() {
        return changeContentRef;
    }

    /**
     * Sets change content ref.
     *
     * @param changeContentRef the value for opscloud_exe_change_order.change_content_ref
     * @mbg.generated
     */
    public void setChangeContentRef(String changeContentRef) {
        this.changeContentRef = changeContentRef;
    }

    /**
     * Gets change target ref.
     *
     * @return the change target ref
     */
    public String getChangeTargetRef() {
        return changeTargetRef;
    }

    /**
     * Sets change target ref.
     *
     * @param changeTargetRef the change target ref
     */
    public void setChangeTargetRef(String changeTargetRef) {
        this.changeTargetRef = changeTargetRef;
    }

    /**
     * Gets trace id.
     *
     * @return the value of opscloud_exe_change_order.trace_id
     * @mbg.generated
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Sets trace id.
     *
     * @param traceId the value for opscloud_exe_change_order.trace_id
     * @mbg.generated
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * Gets context ref.
     *
     * @return the value of opscloud_exe_change_order.context_ref
     * @mbg.generated
     */
    public String getContextRef() {
        return contextRef;
    }

    /**
     * Sets context ref.
     *
     * @param contextRef the value for opscloud_exe_change_order.context_ref
     * @mbg.generated
     */
    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    /**
     * Gets parent order info ref.
     *
     * @return the parent order info ref
     */
    public String getParentOrderInfoRef() {
        return parentOrderInfoRef;
    }

    /**
     * Sets parent order info ref.
     *
     * @param parentOrderInfoRef the parent order info ref
     */
    public void setParentOrderInfoRef(String parentOrderInfoRef) {
        this.parentOrderInfoRef = parentOrderInfoRef;
    }

    public String getRstRef() {
        return rstRef;
    }

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
        sb.append(", uid=").append(uid);
        sb.append(", coord=").append(coord);
        sb.append(", execOrderTitle=").append(changeTitle);
        sb.append(", execOrderUrl=").append(changeUrl);
        sb.append(", paramRef=").append(paramRef);
        sb.append(", rstRef=").append(rstRef);
        sb.append(", changeScenarioCode=").append(changeScenarioCode);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", executor=").append(executor);
        sb.append(", changePhase=").append(changePhases);
        sb.append(", msg=").append(msg);
        sb.append(", tenantCode=").append(tenantCode);
        sb.append(", fromCloudId=").append(fromCloudId);
        sb.append(", referId=").append(referId);
        sb.append(", fromTldTenantCode=").append(fromTenantCode);
        sb.append(", changeAppsRef=").append(changeAppsRef);
        sb.append(", planStartTime=").append(planStartTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", dispatchGroup=").append(dispatchGroup);
        sb.append(", changeContentRef=").append(changeContentRef);
        sb.append(", changeTargetRef=").append(changeTargetRef);
        sb.append(", traceId=").append(traceId);
        sb.append(", contextRef=").append(contextRef);
        sb.append(", parentOrderInfoRef=").append(parentOrderInfoRef);
        sb.append("]");
        return sb.toString();
    }
}