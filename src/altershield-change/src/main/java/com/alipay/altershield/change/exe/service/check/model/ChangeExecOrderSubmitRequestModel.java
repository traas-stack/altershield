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
package com.alipay.altershield.change.exe.service.check.model;

import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitRequest;
import com.alipay.altershield.framework.core.change.model.AlterShieldChangeContent;
import com.alipay.altershield.framework.core.change.model.ChangeParentOrderInfo;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
public class ChangeExecOrderSubmitRequestModel {
    /**
     * tldc租户
     */
    private String tenantCode;
    /**
     * 变更场景标识
     */
    private String changeSceneKey;
    /**
     * 变更执行单id。 变更平台侧需要保证唯一性。
     */
    protected String bizExecOrderId;
    /**
     * 变更执行单平台
     */
    private String platform;
    /**
     * 变更执行单标题
     */
    private String changeTitle;
    /**
     * 变更单地址。推荐填写
     * [optional]
     */
    private String changeUrl;
    /**
     * 变更执行单创建人
     */
    private String creator;
    /**
     * 变更情景码 see {@link ChangeScenarioEnum}
     */
    private String                  changeScenarioCode ;
    /**
     * 变更执行单涉及的环境
     * 使用这个对象值 {@link ChangePhaseEnum#getPhase()} }
     */
    private Set<String>             changePhases;
    /**
     * 变更内容实体信息
     */
    private AlterShieldChangeContent[] changeContents;
    /**
     * 变更内容参数。json格式
     */
    @NotNull
    private String changeParamJson;
    /**
     * [optional] 变更影响的应用
     */
    private Set<String> changeApps;
    /**
     * [optional] 变更单父工单信息
     */
    private ChangeParentOrderInfo parentOrderInfo;
    /**
     * [optional] 变更trace。如果不需要单独生成，则保持null，否则由业务侧自己控制trace
     */
    private OpsChngTrace trace;

    /**
     * 变更执行人
     */
    private String executor;

    /**
     * 变更开始时间。如果为null，则使用当前使用
     */
    private Date startTime;

    /**
     * 变更结束时间
     */
    private Date finishTime;

    /**
     * 变更状态
     */
    private ExeOrderStatusEnum status;

    /**
     * Instantiates a new Ops cloud change exec order submit request model.
     */
    public ChangeExecOrderSubmitRequestModel() {
    }

    /**
     * Instantiates a new Ops cloud change exec order submit request model.
     *
     * @param request the request
     */
    public ChangeExecOrderSubmitRequestModel(ChangeExecOrderSubmitRequest request) {

        this.bizExecOrderId = request.getBizExecOrderId();
        this.changeApps = request.getChangeApps();
        this.changeParamJson = request.getChangeParamJson();
        this.changePhases = request.getChangePhases();
        this.changeScenarioCode = request.getChangeScenarioCode();
        this.changeSceneKey = request.getChangeSceneKey();
        this.changeTitle = request.getChangeTitle();
        this.changeUrl = request.getChangeUrl();
        this.changeContents = request.getChangeContents();
        this.parentOrderInfo = request.getParentOrderInfo();
        this.trace = request.getTrace();
        this.platform = request.getPlatform();
        this.creator = request.getCreator();
        this.executor = request.getCreator();
        this.startTime = request.getPlanStartTime();
        this.tenantCode = request.getTenantCode();
        if(startTime == null)
        {
            startTime = new Date();
        }
    }

}