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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.change.exe.service;

import com.alipay.opscloud.api.change.exe.node.entity.ExeBaseNodeEntity;
import com.alipay.opscloud.api.change.exe.node.entity.ExeNodeEntity;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdict;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;

import java.util.List;

/**
 *
 * @author yuanji
 * @version : ExeChangeNodeQueryService.java, v 0.1 2022年09月06日 11:10 yuanji Exp $
 */
public interface ExeChangeNodeService {

    /**
     * 根据node id 得到entity
     * @param nodeId
     * @return
     */
    ExeNodeEntity lockNodeById(String nodeId);
    /**
     * 根据nodeId获取node信息。可以返回batchNode，orderNode,execActionNode
     * @param nodeId
     * @return
     */
    ExeNodeEntity getNode(String nodeId);

    /**
     * 更新结点的扩展扩展信息,同时更新msg
     * @param exeNodeEntity
     */
    void updateNodeSearchExtInfo(ExeNodeEntity exeNodeEntity);

    /**
     * 更新check信息，同时更新msg
     * @param exeNodeEntity
     */
    void updateNodeCheckInfo(ExeNodeEntity exeNodeEntity);

    /**
     * 更新node检查结束
     * @param exeNodeEntity
     * @param defenseStageEnum
     */
    void setNodeCheckFinish(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, Boolean checkPaas);
    /**
     * 更新node检查开始
     * @param exeNodeEntity
     * @param defenseStageEnum
     * @param  checkId
     */
    void setNodeCheckStarted(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, String checkId);

    /**
     * 更新node检查失败*
     * @param exeNodeEntity
     * @param defenseStageEnum
     * @param msg
     */
    void setNodeCheckFail(ExeNodeEntity exeNodeEntity, DefenseStageEnum defenseStageEnum, String msg);

    /**
     * 查询order下所有node
     *
     * @param orderId
     * @return
     */
    List<ExeBaseNodeEntity> getNodeEntityByOrderIdList(String orderId);

    /**
     * 查询裁决信息
     * @param exeNodeEntity
     * @param defenseStageEnum
     * @param returnDetails
     * @return
     */
    OpsCloudChangeCheckVerdict queryChangeVerdict(ExeNodeEntity exeNodeEntity,DefenseStageEnum defenseStageEnum,boolean returnDetails,boolean changeSuccess);
}