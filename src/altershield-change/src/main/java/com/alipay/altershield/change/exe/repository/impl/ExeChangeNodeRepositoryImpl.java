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
package com.alipay.altershield.change.exe.repository.impl;

import com.alipay.altershield.change.exe.dal.dataobject.ExeNodeDO;
import com.alipay.altershield.change.exe.dal.mapper.ExeNodeMapper;
import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.repository.converter.ExeNodeEntityConverter;
import com.alipay.altershield.common.logger.intercept.AlterShieldLogAnnotation;
import com.alipay.altershield.common.logger.intercept.DetailLogTypeEnum;
import com.alipay.altershield.common.logger.intercept.DigestLogTypeEnum;
import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.shared.change.exe.node.entity.*;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Exe change node repository.
 *
 * @author yuanji
 * @version : ExeChangeNodeRepositoryImpl.java, v 0.1 2022年03月29日 4:09 下午 yuanji Exp $
 */
@Repository
public class ExeChangeNodeRepositoryImpl implements ExeChangeNodeRepository {

    @Autowired
    private ExeNodeMapper exeNodeMapper;
    @Autowired
    private ExeNodeEntityConverter exeNodeEntityConverter;

    @Override
    public boolean insert(ExeNodeEntity entity) {
        Assert.notNull(entity, "entity is null");
        ExeNodeDO exeNodeDO = exeNodeEntityConverter.convertToDataObject(entity);
        return exeNodeMapper.insert(exeNodeDO) == 1;
    }

    @Override
    public ExeNodeEntity getNodeEntity(String nodeExeId) {
        Assert.notNull(nodeExeId, "nodeExeId is null");
        ExeNodeDO exeNodeDO = exeNodeMapper.selectByPrimaryKey(nodeExeId);
        return convert(exeNodeDO);
    }

    /**
     * 使用锁的方式获取entity
     * @param nodeId
     * @return
     */
    public ExeNodeEntity lockNodeById(String nodeId) {
        if (CommonUtil.isBlank(nodeId)) {
            return null;
        }
        ExeNodeDO exeNodeDO = exeNodeMapper.lockById(nodeId);
        return convert(exeNodeDO);
    }

    private ExeNodeEntity convert(ExeNodeDO exeNodeDO) {
        if (exeNodeDO == null) {
            return null;
        }
        ExeNodeEntity entity;
        if (MetaChangeStepTypeEnum.STEP_GRAY_BATCH.name().equalsIgnoreCase(exeNodeDO.getNodeType())) {
            entity = new ExeBatchNodeEntity();
        } else if (MetaChangeStepTypeEnum.STEP_ORDER.name().equalsIgnoreCase(exeNodeDO.getNodeType())) {
            entity = new ExeOrderNodeEntity();
        } else {
            entity = new ExeActionNodeEntity();
        }
        exeNodeEntityConverter.convertToNodeEntity(exeNodeDO, entity);
        return entity;
    }


    @Override
    public List<ExeBaseNodeEntity> getNodeEntityByOrderIdList(String orderId) {
        List<ExeNodeDO> nodeDOList = exeNodeMapper.selectByOrderId(orderId);
        return exeNodeEntityConverter.convertToBaseNodeEntity(nodeDOList);
    }

    @Override

    public List<ExeNodeEntity> getNodeEntityByOrderIdList(String orderId, MetaChangeStepTypeEnum changeStepType,int size) {
        Assert.notNull(orderId, "orderId is null");
        String uid = IdUtil.getUid(orderId);
        String type = null;
        if(changeStepType != null)
        {
            type = changeStepType.name();
        }
        List<ExeNodeDO> nodeDOList = exeNodeMapper.selectByOrderIdAndType(uid, orderId, type, size);
        if (CollectionUtils.isEmpty(nodeDOList)) {
            return Collections.emptyList();
        }
        return nodeDOList.stream().map(exeNodeDO -> convert(exeNodeDO)).collect(Collectors.toList());
    }


    @Override
    @AlterShieldLogAnnotation(serviceDesc = "getLatestNodeEntity", digestLogType = DigestLogTypeEnum.SERVICE_OPERATE_DIGEST,
            detailLogType = DetailLogTypeEnum.SERVICE_INFO, keyArg = 0, keyProps = "orderId")
    public ExeNodeEntity getLatestNodeEntity(String orderId, MetaChangeStepTypeEnum changeStepType) {
        List<ExeNodeEntity> exeNodeEntities = getNodeEntityByOrderIdList(orderId, changeStepType, 1);
        if (CollectionUtils.isEmpty(exeNodeEntities)) {
            return null;
        }
        return exeNodeEntities.get(0);
    }

    @Override
    public ExeNodeEntity getLatestNodeEntity(String orderId) {
        return getLatestNodeEntity(orderId, null);
    }

    @Override
    public List<ExeBaseNodeEntity> getNodeEntityByTraceIdList(String traceId) {
        return null;
    }

    @Override
    public long getNodeEntityCount(String orderId) {
        return 0;
    }

    @Override
    public void update(ExeNodeEntity entity) {
        Assert.notNull(entity, "nodeEntity is null");
        ExeNodeDO exeNodeDO = exeNodeEntityConverter.convertToDataObject(entity);
        exeNodeMapper.updateByPrimaryKey(exeNodeDO);
    }

    @Override
    public void updateNodeSearchExtInfo(ExeNodeEntity exeNodeEntity) {
        ExeNodeDO exeNodeDO = createBaseNodeDO(exeNodeEntity, false);
        exeNodeEntityConverter.flushSearchExtRef(exeNodeEntity, exeNodeDO);
        exeNodeMapper.updateByPrimaryKey(exeNodeDO);

    }



    @Override
    public void updateNodeCheckInfo(ExeNodeEntity exeNodeEntity) {
        ExeNodeDO exeNodeDO  = createBaseNodeDO(exeNodeEntity, true);
        exeNodeEntityConverter.flushCheckInfo(exeNodeEntity, exeNodeDO);
        exeNodeMapper.updateByPrimaryKey(exeNodeDO);
    }


    @Override
    public boolean updateNodeCheckInfoWithOldStatus(ExeNodeEntity exeNodeEntity, List<ExeNodeStateEnum> oldState) {
        ExeNodeDO exeNodeDO  = createBaseNodeDO(exeNodeEntity, true);
        exeNodeEntityConverter.flushCheckInfo(exeNodeEntity, exeNodeDO);
        List<String> oldStates = new ArrayList<>();
        for(ExeNodeStateEnum stateEnum:oldState){
            oldStates.add(stateEnum.name());
        }
        return exeNodeMapper.updateByPrimaryKeyWithOldState(exeNodeDO, oldStates) == 1;
    }


    @Override
    public void updateNodeStatus(ExeNodeEntity exeNodeEntity) {
       updateNodeStatus(exeNodeEntity, false);
    }


    @Override
    public void updateNodeStatus(ExeNodeEntity exeNodeEntity, boolean flushResult) {
        ExeNodeDO exeNodeDO  = createBaseNodeDO(exeNodeEntity, true);
        exeNodeEntityConverter.flushCheckInfo(exeNodeEntity, exeNodeDO);
        exeNodeEntityConverter.flushResult(exeNodeEntity, exeNodeDO);
        exeNodeMapper.updateByPrimaryKey(exeNodeDO);
    }

    private ExeNodeDO createBaseNodeDO(ExeNodeEntity exeNodeEntity, boolean withStatus) {
        ExeNodeDO exeNodeDO  = new ExeNodeDO();

        exeNodeDO.setNodeExeId(exeNodeEntity.getNodeExeId());
        if(withStatus)
        {
            exeNodeDO.setStatus(exeNodeEntity.getStatus().name());
        }
        exeNodeDO.setMsg(exeNodeEntity.getMsg());
        return exeNodeDO;
    }
}