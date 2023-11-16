/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.repository.impl;

import com.alipay.altershield.defender.framework.exe.dal.dataobject.OpsCloudExeDefenderDetectDO;
import com.alipay.altershield.defender.framework.exe.dal.dataobject.OpsCloudExeDefenderDetectParam;
import com.alipay.altershield.defender.framework.exe.dal.mapper.OpsCloudExeDefenderDetectMapper;
import com.alipay.altershield.defender.framework.exe.dal.mapper.OpsCloudManualExeDefenderDetectMapper;
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
    private OpsCloudExeDefenderDetectMapper opsCloudExeDefenderDetectMapper;

    @Autowired
    private OpsCloudManualExeDefenderDetectMapper opsCloudManualExeDefenderDetectMapper;

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
        OpsCloudExeDefenderDetectDO detectDO = opsCloudExeDefenderDetectMapper.selectByPrimaryKey(detectExeId);
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
        OpsCloudExeDefenderDetectDO detectDO = opsCloudExeDefenderDetectMapper.selectByPrimaryKeyForUpdate(detectExeId);
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
        OpsCloudExeDefenderDetectParam param = new OpsCloudExeDefenderDetectParam();
        param.createCriteria().andChangeOrderIdEqualTo(changeOrderId);
        List<OpsCloudExeDefenderDetectDO> detectDOs = opsCloudExeDefenderDetectMapper.selectByParam(param);
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
        List<OpsCloudExeDefenderDetectDO> detectDOs = exeDefenderDetectConvertor.convert2DOList(detectEntities);
        int columns = opsCloudExeDefenderDetectMapper.batchInsert(detectDOs);
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
        OpsCloudExeDefenderDetectDO detectDO = exeDefenderDetectConvertor.convert2DO(detectEntity);
        int column = opsCloudExeDefenderDetectMapper.insert(detectDO);
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
        int columns = opsCloudExeDefenderDetectMapper.updateByPrimaryKeySelective(exeDefenderDetectConvertor.convert2DO(entity));
        return columns > 0;
    }

    @Override
    public List<ExeDefenderDetectEntity> selectByGroupId(String groupId) {
        OpsCloudExeDefenderDetectParam param = new OpsCloudExeDefenderDetectParam();
        param.createCriteria().andDetectGroupIdEqualTo(groupId);
        List<OpsCloudExeDefenderDetectDO> detectDOs = opsCloudExeDefenderDetectMapper.selectByParam(param);
        return exeDefenderDetectConvertor.convert2ModelList(detectDOs);
    }

    @Override
    public boolean batchUpdateStatusByDetectExeIds(List<String> detectExeIds, String ignored) {
        if (CollectionUtils.isEmpty(detectExeIds) || StringUtils.isBlank(ignored)) {
            return false;
        }

        int updateByIds = opsCloudExeDefenderDetectMapper.batchUpdateByIds(detectExeIds, ignored);
        return updateByIds >= detectExeIds.size();
    }

    @Override
    public List<ExeDefenderDetectEntity> selectDetectStatusByNodeId(String nodeId, DefenseStageEnum stage) {
        OpsCloudExeDefenderDetectParam param = new OpsCloudExeDefenderDetectParam();
        OpsCloudExeDefenderDetectParam.Criteria criteria = param.createCriteria();
        criteria.andNodeIdEqualTo(nodeId);
        if (stage != null) {
            criteria.andStageEqualTo(stage.getStage());
        }

        List<OpsCloudExeDefenderDetectDO> detectDOList = opsCloudExeDefenderDetectMapper.selectByParam(param);

        return exeDefenderDetectConvertor.convert2ModelList(detectDOList);
    }
}