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
package com.alipay.altershield.change.exe.service.check.model;

import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderStartNotifyRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitAndStartRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeStartNotifyRequest;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeContext;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * The type Ops cloud change node create model.
 *
 * @author yuanji
 * @version : OpsCloudChangeNodeCreateModel.java, v 0.1 2022年04月08日 3:51 下午 yuanji Exp $
 */
@ToString
@Getter
@Setter
public class ChangeNodeCreateModel {

    /**
     * 来源cloudId
     */
    private String fromCloudId;

    private String orderId;

    private String nodeExeId;

    private MetaChangeStepEntity changeStepEntity;

    private MetaChangeStepTypeEnum changeStepType;

    private String executor;

    /**
     * 变更场景标识
     */
    private String changeSceneKey;

    private Date startTime;

    private boolean emergency;

    // TODO 老变更核心有关
    private ExeNodeContext contextRef;

    private OpsChngTrace trace;

    private  ChangeStartNotifyRequest request;

    private String dispatchGroup;

    private ExeNodeStateEnum status;

    private Map<String, ?> extensionInfo;

    private MetaChangeSceneGenerationEnum generation;

    private ChangeScenarioEnum changeScenarioCode;

    private String paramJsn;

    private ChangeTarget[] changeTargets;

    private Set<String> changeApps;

    private Set<String> changePhases;

    private String tenantCode;

    /**
     * Instantiates a new Ops cloud change node create model.
     *
     * @param nodeExeId             the node exe id
     * @param fromCloudId           the from cloud id
     * @param orderId               the order id
     * @param changeStepEntity      the change step type
     * @param exeNodeContext        the exe node context
     * @param request               the request
     * @param metaChangeSceneEntity the meta change scene entity
     * @param changeScenarioCode    the change scenario code
     */
    public ChangeNodeCreateModel(String nodeExeId, String fromCloudId, String orderId,
                                 MetaChangeStepEntity changeStepEntity,
                                 ExeNodeContext exeNodeContext,
                                 ChangeStartNotifyRequest request,
                                 MetaChangeSceneEntity metaChangeSceneEntity,
                                 ChangeScenarioEnum changeScenarioCode) {
        this.nodeExeId = nodeExeId;
        this.fromCloudId = fromCloudId;
        this.orderId = orderId;
        this.changeStepEntity = changeStepEntity;
        this.changeStepType = changeStepEntity.getStepType();
        this.contextRef = exeNodeContext;
        this.executor = request.getExecutor();
        this.changeSceneKey = request.getChangeSceneKey();
        this.startTime = buildStartTime(request.getStartTime());
        this.extensionInfo = request.getExtensionInfo();
        this.generation = metaChangeSceneEntity.getGeneration();
        this.request = request;
        this.changeScenarioCode = changeScenarioCode;
        this.emergency = request.getEmergency() == Boolean.TRUE || emergencyOrder();
        this.tenantCode = request.getTenantCode();
    }

    /**
     * Instantiates a new Ops cloud change node create model.
     *
     * @param nodeExeId             the node exe id
     * @param fromCloudId           the from cloud id
     * @param orderId               the order id
     * @param changeStepType        the change step type
     * @param exeNodeContext        the exe node context
     * @param request               the request
     * @param metaChangeSceneEntity the meta change scene entity
     * @param changeScenarioCode    the change scenario code
     */
    public ChangeNodeCreateModel(String nodeExeId, String fromCloudId, String orderId,
                                 MetaChangeStepTypeEnum changeStepType,
                                 ExeNodeContext exeNodeContext,
                                 ChangeExecOrderSubmitAndStartRequest request,
                                 MetaChangeSceneEntity metaChangeSceneEntity,
                                 ChangeScenarioEnum changeScenarioCode) {
        this.nodeExeId = nodeExeId;
        this.fromCloudId = fromCloudId;
        this.orderId = orderId;
        this.changeStepType = changeStepType;
        this.contextRef = exeNodeContext;
        this.executor = request.getExecutor();
        this.changeSceneKey = request.getChangeSceneKey();
        this.startTime = buildStartTime(request.getStartTime());
        this.changeScenarioCode = changeScenarioCode;
        this.paramJsn = request.getChangeParamJson();
        this.changeTargets = request.getChangeTargets();
        this.changePhases = request.getChangePhases();

        if (Objects.isNull(request.getExtensionInfo())) {
            this.extensionInfo = new HashMap<>();
        } else {
            this.extensionInfo = request.getExtensionInfo();
        }

        this.emergency = changeScenarioCode == ChangeScenarioEnum.EMERGE;
        this.request = new ChangeExecOrderStartNotifyRequest();
        this.request.setTrace(request.getTrace());
        this.generation = metaChangeSceneEntity.getGeneration();
        this.changeApps = request.getChangeApps();
    }


    /**
     * Emergency request boolean.
     *
     * @return the boolean
     */
    public boolean emergencyOrder()
    {
        return ChangeScenarioEnum.EMERGE == changeScenarioCode;
    }


    private static Date buildStartTime(Date startTime)
    {
        return startTime == null ? new Date() : startTime;
    }


}