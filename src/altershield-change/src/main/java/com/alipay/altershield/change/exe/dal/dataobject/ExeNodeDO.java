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
 * The type Exe node do.
 *
 * @author yuanji
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
     *
     *
     * @mbg.generated
     */
    private String uid;

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
     *   记录更新的触发源id
     *
     *
     * @mbg.generated
     */
    private String lastTriggerId;

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
    private String tldcTenantCode;

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
     *   来源TLDC架构租户信息
     *
     *
     * @mbg.generated
     */
    private String fromTldcTenantCode;

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
     *   变更目标
     *
     *
     * @mbg.generated
     */
    private String changeTargetRef;

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
     *   调度分组
     *
     *
     * @mbg.generated
     */
    private String dispatchGroup;

    /**
     * Gets node exe id.
     *
     * @return the value of opscloud_exe_node.node_exe_id
     * @mbg.generated
     */
    public String getNodeExeId() {
        return nodeExeId;
    }

    /**
     * Sets node exe id.
     *
     * @param nodeExeId the value for opscloud_exe_node.node_exe_id
     * @mbg.generated
     */
    public void setNodeExeId(String nodeExeId) {
        this.nodeExeId = nodeExeId;
    }

    /**
     * Gets uid.
     *
     * @return the value of opscloud_exe_node.uid
     * @mbg.generated
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the value for opscloud_exe_node.uid
     * @mbg.generated
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets trace id.
     *
     * @return the value of opscloud_exe_node.trace_id
     * @mbg.generated
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Sets trace id.
     *
     * @param traceId the value for opscloud_exe_node.trace_id
     * @mbg.generated
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * Gets order id.
     *
     * @return the value of opscloud_exe_node.order_id
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the value for opscloud_exe_node.order_id
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets coord.
     *
     * @return the value of opscloud_exe_node.coord
     * @mbg.generated
     */
    public String getCoord() {
        return coord;
    }

    /**
     * Sets coord.
     *
     * @param coord the value for opscloud_exe_node.coord
     * @mbg.generated
     */
    public void setCoord(String coord) {
        this.coord = coord;
    }

    /**
     * Gets gmt create.
     *
     * @return the value of opscloud_exe_node.gmt_create
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Sets gmt create.
     *
     * @param gmtCreate the value for opscloud_exe_node.gmt_create
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Gets gmt modified.
     *
     * @return the value of opscloud_exe_node.gmt_modified
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Sets gmt modified.
     *
     * @param gmtModified the value for opscloud_exe_node.gmt_modified
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Gets status.
     *
     * @return the value of opscloud_exe_node.status
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the value for opscloud_exe_node.status
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets change scene key.
     *
     * @return the value of opscloud_exe_node.change_scene_key
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     * Sets change scene key.
     *
     * @param changeSceneKey the value for opscloud_exe_node.change_scene_key
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     * Gets change key.
     *
     * @return the value of opscloud_exe_node.change_key
     * @mbg.generated
     */
    public String getChangeKey() {
        return changeKey;
    }

    /**
     * Sets change key.
     *
     * @param changeKey the value for opscloud_exe_node.change_key
     * @mbg.generated
     */
    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    /**
     * Gets node type.
     *
     * @return the value of opscloud_exe_node.node_type
     * @mbg.generated
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * Sets node type.
     *
     * @param nodeType the value for opscloud_exe_node.node_type
     * @mbg.generated
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Gets change phase.
     *
     * @return the value of opscloud_exe_node.change_phase
     * @mbg.generated
     */
    public String getChangePhase() {
        return changePhase;
    }

    /**
     * Sets change phase.
     *
     * @param changePhase the value for opscloud_exe_node.change_phase
     * @mbg.generated
     */
    public void setChangePhase(String changePhase) {
        this.changePhase = changePhase;
    }

    /**
     * Gets param ref.
     *
     * @return the value of opscloud_exe_node.param_ref
     * @mbg.generated
     */
    public String getParamRef() {
        return paramRef;
    }

    /**
     * Sets param ref.
     *
     * @param paramRef the value for opscloud_exe_node.param_ref
     * @mbg.generated
     */
    public void setParamRef(String paramRef) {
        this.paramRef = paramRef;
    }

    /**
     * Gets rst ref.
     *
     * @return the value of opscloud_exe_node.rst_ref
     * @mbg.generated
     */
    public String getRstRef() {
        return rstRef;
    }

    /**
     * Sets rst ref.
     *
     * @param rstRef the value for opscloud_exe_node.rst_ref
     * @mbg.generated
     */
    public void setRstRef(String rstRef) {
        this.rstRef = rstRef;
    }

    /**
     * Gets last trigger id.
     *
     * @return the value of opscloud_exe_node.last_trigger_id
     * @mbg.generated
     */
    public String getLastTriggerId() {
        return lastTriggerId;
    }

    /**
     * Sets last trigger id.
     *
     * @param lastTriggerId the value for opscloud_exe_node.last_trigger_id
     * @mbg.generated
     */
    public void setLastTriggerId(String lastTriggerId) {
        this.lastTriggerId = lastTriggerId;
    }

    /**
     * Gets msg.
     *
     * @return the value of opscloud_exe_node.msg
     * @mbg.generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the value for opscloud_exe_node.msg
     * @mbg.generated
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets executor.
     *
     * @return the value of opscloud_exe_node.executor
     * @mbg.generated
     */
    public String getExecutor() {
        return executor;
    }

    /**
     * Sets executor.
     *
     * @param executor the value for opscloud_exe_node.executor
     * @mbg.generated
     */
    public void setExecutor(String executor) {
        this.executor = executor;
    }

    /**
     * Gets biz ext.
     *
     * @return the value of opscloud_exe_node.biz_ext
     * @mbg.generated
     */
    public String getBizExt() {
        return bizExt;
    }

    /**
     * Sets biz ext.
     *
     * @param bizExt the value for opscloud_exe_node.biz_ext
     * @mbg.generated
     */
    public void setBizExt(String bizExt) {
        this.bizExt = bizExt;
    }

    /**
     * Gets search ext ref.
     *
     * @return the value of opscloud_exe_node.search_ext_ref
     * @mbg.generated
     */
    public String getSearchExtRef() {
        return searchExtRef;
    }

    /**
     * Sets search ext ref.
     *
     * @param searchExtRef the value for opscloud_exe_node.search_ext_ref
     * @mbg.generated
     */
    public void setSearchExtRef(String searchExtRef) {
        this.searchExtRef = searchExtRef;
    }

    /**
     * Gets check id info.
     *
     * @return the value of opscloud_exe_node.check_id_info
     * @mbg.generated
     */
    public String getCheckIdInfo() {
        return checkIdInfo;
    }

    /**
     * Sets check id info.
     *
     * @param checkIdInfo the value for opscloud_exe_node.check_id_info
     * @mbg.generated
     */
    public void setCheckIdInfo(String checkIdInfo) {
        this.checkIdInfo = checkIdInfo;
    }

    /**
     * Gets start time.
     *
     * @return the value of opscloud_exe_node.start_time
     * @mbg.generated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the value for opscloud_exe_node.start_time
     * @mbg.generated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets finish time.
     *
     * @return the value of opscloud_exe_node.finish_time
     * @mbg.generated
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * Sets finish time.
     *
     * @param finishTime the value for opscloud_exe_node.finish_time
     * @mbg.generated
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * Gets emergency flag.
     *
     * @return the value of opscloud_exe_node.emergency_flag
     * @mbg.generated
     */
    public String getEmergencyFlag() {
        return emergencyFlag;
    }

    /**
     * Sets emergency flag.
     *
     * @param emergencyFlag the value for opscloud_exe_node.emergency_flag
     * @mbg.generated
     */
    public void setEmergencyFlag(String emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    /**
     * Gets context ref.
     *
     * @return the value of opscloud_exe_node.context_ref
     * @mbg.generated
     */
    public String getContextRef() {
        return contextRef;
    }

    /**
     * Sets context ref.
     *
     * @param contextRef the value for opscloud_exe_node.context_ref
     * @mbg.generated
     */
    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    /**
     * Gets tldc tenant code.
     *
     * @return the value of opscloud_exe_node.tldc_tenant_code
     * @mbg.generated
     */
    public String getTldcTenantCode() {
        return tldcTenantCode;
    }

    /**
     * Sets tldc tenant code.
     *
     * @param tldcTenantCode the value for opscloud_exe_node.tldc_tenant_code
     * @mbg.generated
     */
    public void setTldcTenantCode(String tldcTenantCode) {
        this.tldcTenantCode = tldcTenantCode;
    }

    /**
     * Gets from cloud id.
     *
     * @return the value of opscloud_exe_node.from_cloud_id
     * @mbg.generated
     */
    public String getFromCloudId() {
        return fromCloudId;
    }

    /**
     * Sets from cloud id.
     *
     * @param fromCloudId the value for opscloud_exe_node.from_cloud_id
     * @mbg.generated
     */
    public void setFromCloudId(String fromCloudId) {
        this.fromCloudId = fromCloudId;
    }

    /**
     * Gets refer id.
     *
     * @return the value of opscloud_exe_node.refer_Id
     * @mbg.generated
     */
    public String getReferId() {
        return referId;
    }

    /**
     * Sets refer id.
     *
     * @param referId the value for opscloud_exe_node.refer_Id
     * @mbg.generated
     */
    public void setReferId(String referId) {
        this.referId = referId;
    }

    /**
     * Gets from tldc tenant code.
     *
     * @return the value of opscloud_exe_node.from_tldc_tenant_code
     * @mbg.generated
     */
    public String getFromTldcTenantCode() {
        return fromTldcTenantCode;
    }

    /**
     * Sets from tldc tenant code.
     *
     * @param fromTldcTenantCode the value for opscloud_exe_node.from_tldc_tenant_code
     * @mbg.generated
     */
    public void setFromTldcTenantCode(String fromTldcTenantCode) {
        this.fromTldcTenantCode = fromTldcTenantCode;
    }

    /**
     * Gets change content ref.
     *
     * @return the value of opscloud_exe_node.change_content_ref
     * @mbg.generated
     */
    public String getChangeContentRef() {
        return changeContentRef;
    }

    /**
     * Sets change content ref.
     *
     * @param changeContentRef the value for opscloud_exe_node.change_content_ref
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
     * Gets change effective info ref.
     *
     * @return the value of opscloud_exe_node.change_effective_info_ref
     * @mbg.generated
     */
    public String getChangeEffectiveInfoRef() {
        return changeEffectiveInfoRef;
    }

    /**
     * Sets change effective info ref.
     *
     * @param changeEffectiveInfoRef the value for opscloud_exe_node.change_effective_info_ref
     * @mbg.generated
     */
    public void setChangeEffectiveInfoRef(String changeEffectiveInfoRef) {
        this.changeEffectiveInfoRef = changeEffectiveInfoRef;
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
        sb.append(", uid=").append(uid);
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
        sb.append(", paramRef=").append(paramRef);
        sb.append(", rstRef=").append(rstRef);
        sb.append(", lastTriggerId=").append(lastTriggerId);
        sb.append(", msg=").append(msg);
        sb.append(", executor=").append(executor);
        sb.append(", bizExt=").append(bizExt);
        sb.append(", searchExtRef=").append(searchExtRef);
        sb.append(", checkIdInfo=").append(checkIdInfo);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", emergencyFlag=").append(emergencyFlag);
        sb.append(", contextRef=").append(contextRef);
        sb.append(", tldcTenantCode=").append(tldcTenantCode);
        sb.append(", fromCloudId=").append(fromCloudId);
        sb.append(", referId=").append(referId);
        sb.append(", fromTldcTenantCode=").append(fromTldcTenantCode);
        sb.append(", changeContentRef=").append(changeContentRef);
        sb.append(", changeTargetRef=").append(changeTargetRef);
        sb.append(", changeEffectiveInfoRef=").append(changeEffectiveInfoRef);
        sb.append(", dispatchGroup=").append(dispatchGroup);

        sb.append("]");
        return sb.toString();
    }
}