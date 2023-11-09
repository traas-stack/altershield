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
package com.alipay.altershield.change.exe.repository;


import com.alipay.altershield.shared.change.exe.node.entity.ExeBaseNodeEntity;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;

import java.util.List;

/**
 * @author yuanji
 * @version : ExeChangeNodeRepository.java, v 0.1 2022年03月29日 4:03 下午 yuanji Exp $
 */
public interface ExeChangeNodeRepository {

    /**
     * insert
     *
     * @param entity
     * @return
     */
    boolean insert(ExeNodeEntity entity);

    /**
     * 根据nodeId获取node对象
     *
     * @param nodeExeId
     * @return
     */
    ExeNodeEntity getNodeEntity(String nodeExeId);

    /**
     *  根据nodeId获取node对象
     * @param nodeExeId
     * @return
     */
    ExeNodeEntity lockNodeById(String nodeExeId);

    /**
     * 通过orderId获取node
     *
     * @param orderId
     * @return
     */
    List<ExeBaseNodeEntity> getNodeEntityByOrderIdList(String orderId);

    /**
     * 根据orderId和changeStep 查询nodeEntity
     *
     * @param orderId
     * @param changeStepType
     * @param size
     * @return
     */
    List<ExeNodeEntity> getNodeEntityByOrderIdList(String orderId, MetaChangeStepTypeEnum changeStepType, int size);

    /**
     * 根据orderId和changeStep 查询最近的一个nodeEntity
     *
     * @param orderId
     * @param changeStepType
     * @return
     */
    ExeNodeEntity getLatestNodeEntity(String orderId, MetaChangeStepTypeEnum changeStepType);

    /**
     * 根据orderId查询最近的一个nodeEntity
     *
     * @param orderId
     * @return
     */
    ExeNodeEntity getLatestNodeEntity(String orderId);


    /**
     * 通过traceId获取node
     *
     * @param traceId
     * @return
     */
    List<ExeBaseNodeEntity> getNodeEntityByTraceIdList(String traceId);

    long getNodeEntityCount(String orderId);

    /**
     * 根据node的基本信息
     *
     * @param entity
     */
    void update(ExeNodeEntity entity);

    /**
     * 只更新扩展信息， msg
     * @param exeNodeEntity
     */
    void updateNodeSearchExtInfo(ExeNodeEntity exeNodeEntity);

    /**
     * 只更新checkId, msg信息
     * @param exeNodeEntity
     */
    void updateNodeCheckInfo(ExeNodeEntity exeNodeEntity);

    /**
     * 只更新node的状态信息
     * @param exeNodeEntity
     */
    void updateNodeStatus(ExeNodeEntity exeNodeEntity);


    /**
     * 更新node的状态，msgn, checkId，
     * @param exeNodeEntity
     * @param flushResult 同时更新result
     */
    void updateNodeStatus(ExeNodeEntity exeNodeEntity, boolean flushResult);

    /**
     * 更新node status 要使用老的state
     * @param exeNodeEntity
     * @param oldState
     */
    boolean updateNodeCheckInfoWithOldStatus(ExeNodeEntity exeNodeEntity, List<ExeNodeStateEnum> oldState);

    }