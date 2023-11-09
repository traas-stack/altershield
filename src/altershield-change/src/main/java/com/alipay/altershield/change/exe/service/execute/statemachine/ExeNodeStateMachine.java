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
///**
// * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
// */
package com.alipay.altershield.change.exe.service.execute.statemachine;

import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.api.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.opscloud.api.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.opscloud.api.defender.ExeDefenderDetectService;
import com.alipay.opscloud.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalErrorCode;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalException;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeFinishNotifyRequest;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeSkipCheckRequest;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeCheckProgressResult;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdict;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * node的状态机
 */
public abstract class ExeNodeStateMachine {

    /**
     * The Meta change scene repository.
     */
    @Autowired
    protected MetaChangeSceneRepository metaChangeSceneRepository;

    /**
     * The Exe change node repository.
     */
    @Autowired
    protected ExeChangeNodeRepository exeChangeNodeRepository;

    /**
     * The Exe state node.
     */
    protected ExeNodeStateEnum exeStateNode;


    @Autowired
    protected ExeDefenderDetectService exeDefenderDetectService;


    /**
     * The constant logger.
     */
    protected static final Logger logger = Loggers.EXE_STATE_MACHINE;

    /**
     * Gets exe state.
     *
     * @return the exe state
     */
    public ExeNodeStateEnum getExeState() {
        return exeStateNode;
    }

    /**
     * Sets meta change scene repository.
     *
     * @param metaChangeSceneRepository the meta change scene repository
     */
    public void setMetaChangeSceneRepository(MetaChangeSceneRepository metaChangeSceneRepository) {
        this.metaChangeSceneRepository = metaChangeSceneRepository;
    }

    /**
     * Sets exe change node repository.
     *
     * @param exeChangeNodeRepository the exe change node repository
     */
    public void setExeChangeNodeRepository(ExeChangeNodeRepository exeChangeNodeRepository) {
        this.exeChangeNodeRepository = exeChangeNodeRepository;
    }

    /**
     * 轮询检查结点状态
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     */
    public void pollingCheckStatus(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum) {
        unsupportedOperation(entity);
    }

    /**
     * 获取检查的结果
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param returnDetails    the return details
     * @return ops cloud result
     */
    public OpsCloudResult<OpsCloudChangeCheckProgressResult> retrieveCheckResult(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum,
                                                                                 boolean returnDetails) {
        return new OpsCloudResult(new OpsCloudChangeCheckProgressResult(false, null,
                OpsCloudConstant.OPSCLOUD_DEFENSE_CHECK_DETAIL_URL + entity.getNodeExeId()));
    }

    /**
     * 设置防御检查开始
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param checkId          the check id
     */
    public void setNodeDefenseStarted(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String checkId) {
        unsupportedOperation(entity);
    }

    /**
     * 设置防御检查结束
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param checkPaas        the check paas
     */
    public void setNodeDefenseFinish(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, Boolean checkPaas) {
        unsupportedOperation(entity);
    }

    /**
     * 设置防御检查失败
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param msg              the msg
     */
    public void setNodeDefenseFail(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, String msg) {
        unsupportedOperation(entity);
    }

    /**
     * 设置结点超时
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     */
    public void setNodeCheckTimeOut(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum) {
        unsupportedOperation(entity);

    }


    /**
     * 设置前置同步检查
     *
     * @param checkTimeOut
     * @param changeOrder changeOrder
     * @param entity the entity
     * @param metaChangeSceneEntity
     */
    public OpsCloudChangeCheckVerdict setNodeSyncPreStartCheck(long checkTimeOut, ExeChangeOrderEntity changeOrder,ExeNodeEntity entity, MetaChangeSceneEntity metaChangeSceneEntity) {
        unsupportedOperation(entity);
        return null;
    }


    /**
     * 提交node检查开始
     *
     * @param entity the entity
     * @param metaChangeSceneEntity
     */
    public void submitNodePreStartCheck(ExeNodeEntity entity, MetaChangeSceneEntity metaChangeSceneEntity) {
        unsupportedOperation(entity);
    }

    /**
     * 提交node检查开始
     *
     * @param entity  the entity
     * @param request the request
     */
    public void submitNodePostStartCheck(ExeNodeEntity entity, OpsCloudChangeFinishNotifyRequest request, MetaChangeSceneEntity metaChangeSceneEntity) {
        unsupportedOperation(entity);
    }

    /**
     * 设置变更执行中
     *
     * @param entity the entity
     */
    public void setChangeExecuting(ExeNodeEntity entity) {
        unsupportedOperation(entity);
    }

    /**
     * 忽略node检查结果
     *
     * @param entity           the entity
     * @param defenseStageEnum the defense stage enum
     * @param request              the request
     */
    public OpsCloudResult<OpsCloudChangeSkipCheckResult> setIgnoreNodeCheck(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum, OpsCloudChangeSkipCheckRequest request) {
        unsupportedOperation(entity);
        return null;
    }

    /**
     * Unsupported operation.
     *
     * @param entity the entity
     */
    protected void unsupportedOperation(ExeNodeEntity entity) {
        throw new OpsCloudInternalException(OpsCloudInternalErrorCode.CHNG_SRV_NOT_SUPPORT, "unsupported operation in " + exeStateNode + ", nodeId=" + entity.getNodeExeId());
    }

}
