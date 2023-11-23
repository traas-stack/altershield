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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.repository.impl;

import com.alipay.altershield.defender.framework.exe.dal.dataobject.ExeDefenderDetectDO;
import com.alipay.altershield.defender.framework.exe.dal.dataobject.ExeDefenderDetectParam;
import com.alipay.altershield.defender.framework.exe.dal.mapper.ExeDefenderDetectMapper;
import com.alipay.altershield.defender.framework.exe.entity.convertor.ExeDefenderDetectConvertor;
import com.alipay.altershield.defender.framework.exe.repository.ExeDefenderDetectRepository;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Defender detection record table storage layer
 *
 * @author yhaoxuan
 * @version ExeDefenderDetectRepositoryImpl.java, v 0.1 2022年08月29日 5:22 下午 yhaoxuan
 */
@Repository
public class ExeDefenderDetectRepositoryImpl implements ExeDefenderDetectRepository {

    @Autowired
    private ExeDefenderDetectMapper exeDefenderDetectMapper;

    @Autowired
    private ExeDefenderDetectConvertor exeDefenderDetectConvertor;

    /**
     * 根据检测执行id（本表主键）获取记录
     *
     * @param detectExeId 主键id
     * @return 对应检测执行记录
     */
    @Override
    public ExeDefenderDetectEntity loadByExeId(String detectExeId) {
        ExeDefenderDetectDO detectDO = exeDefenderDetectMapper.selectByPrimaryKey(detectExeId);
        return exeDefenderDetectConvertor.convert2Model(detectDO);
    }

    /**
     * 根据检测执行id（本表主键）获取记录，并加锁
     *
     * @param detectExeId 主键id
     * @return 对应检测执行记录
     */
    @Override
    public ExeDefenderDetectEntity lockByExeId(String detectExeId) {
        ExeDefenderDetectDO detectDO = exeDefenderDetectMapper.selectByPrimaryKeyForUpdate(detectExeId);
        return exeDefenderDetectConvertor.convert2Model(detectDO);
    }

    /**
     * 根据变更工单id获取一个工单中全部的校验记录
     *
     * @param changeOrderId 变更工单id
     * @return 校验记录列表
     */
    @Override
    public List<ExeDefenderDetectEntity> selectByChangeOrderId(String changeOrderId) {
        ExeDefenderDetectParam param = new ExeDefenderDetectParam();
        param.createCriteria().andChangeOrderIdEqualTo(changeOrderId);
        List<ExeDefenderDetectDO> detectDOs = exeDefenderDetectMapper.selectByParam(param);
        return exeDefenderDetectConvertor.convert2ModelList(detectDOs);
    }

    /**
     * 批量创建校验记录
     *
     * @param detectEntities 校验记录实体列表
     * @return 创建是否全部成功
     */
    @Override
    public boolean batchInsert(List<ExeDefenderDetectEntity> detectEntities) {
        List<ExeDefenderDetectDO> detectDOs = exeDefenderDetectConvertor.convert2DOList(detectEntities);
        int columns = exeDefenderDetectMapper.batchInsert(detectDOs);
        return columns == detectEntities.size();
    }

    /**
     * 创建校验记录
     *
     * @param detectEntity 校验记录实体
     * @return 创建是否成功
     */
    @Override
    public boolean insert(ExeDefenderDetectEntity detectEntity) {
        ExeDefenderDetectDO detectDO = exeDefenderDetectConvertor.convert2DO(detectEntity);
        int column = exeDefenderDetectMapper.insert(detectDO);
        return column > 0;
    }

    /**
     * 更新检测记录
     *
     * @param entity 检测记录实体
     * @return 更新结果
     */
    @Override
    public boolean update(ExeDefenderDetectEntity entity) {
        if (entity == null || StringUtils.isBlank(entity.getDetectExeId())) {
            return false;
        }

        entity.setGmtModified(new Date());
        int columns = exeDefenderDetectMapper.updateByPrimaryKeySelective(exeDefenderDetectConvertor.convert2DO(entity));
        return columns > 0;
    }

    @Override
    public List<ExeDefenderDetectEntity> selectByGroupId(String groupId) {
        ExeDefenderDetectParam param = new ExeDefenderDetectParam();
        param.createCriteria().andDetectGroupIdEqualTo(groupId);
        List<ExeDefenderDetectDO> detectDOs = exeDefenderDetectMapper.selectByParam(param);
        return exeDefenderDetectConvertor.convert2ModelList(detectDOs);
    }

    @Override
    public boolean batchUpdateStatusByDetectExeIds(List<String> detectExeIds, String ignored) {
        if (CollectionUtils.isEmpty(detectExeIds) || StringUtils.isBlank(ignored)) {
            return false;
        }

        int updateByIds = exeDefenderDetectMapper.batchUpdateByIds(detectExeIds, ignored);
        return updateByIds >= detectExeIds.size();
    }

    @Override
    public List<ExeDefenderDetectEntity> selectDetectStatusByNodeId(String nodeId, DefenseStageEnum stage) {
        ExeDefenderDetectParam param = new ExeDefenderDetectParam();
        ExeDefenderDetectParam.Criteria criteria = param.createCriteria();
        criteria.andNodeIdEqualTo(nodeId);
        if (stage != null) {
            criteria.andStageEqualTo(stage.getStage());
        }

        List<ExeDefenderDetectDO> detectDOList = exeDefenderDetectMapper.selectByParam(param);

        return exeDefenderDetectConvertor.convert2ModelList(detectDOList);
    }
}