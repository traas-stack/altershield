/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.repository;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;

import java.util.List;

/**
 * @author yhaoxuan
 * @version ExeDefenderDetectRepository.java, v 0.1 2022年08月28日 7:40 下午 yhaoxuan
 */
public interface ExeDefenderDetectRepository {

    /**
     * Obtain records based on the detection execution id (primary key of this table)
     *
     * @param detectExeId primary key id
     * @return Corresponding detection execution record
     */
    ExeDefenderDetectEntity loadByExeId(String detectExeId);

    /**
     * Obtain records based on the detection execution id (primary key of this table) and lock them
     *
     * @param detectExeId primary key id
     * @return Corresponding detection execution record
     */
    ExeDefenderDetectEntity lockByExeId(String detectExeId);

    /**
     * Obtain all detection records in a work order based on the change work order ID
     *
     * @param changeOrderId Change order id
     * @return detection record list
     */
    List<ExeDefenderDetectEntity> selectByChangeOrderId(String changeOrderId);

    /**
     * Create detection records in batches
     *
     * @param detectEntities detection record entity list
     * @return all creations successful
     */
    boolean batchInsert(List<ExeDefenderDetectEntity> detectEntities);

    /**
     * Create detection record
     *
     * @param detectEntity detection record entity
     * @return the creation successful
     */
    boolean insert(ExeDefenderDetectEntity detectEntity);

    /**
     * Update detection record
     *
     * @param entity detection record entity
     * @return Update result
     */
    boolean update(ExeDefenderDetectEntity entity);

    /**
     * Query detection records based on groupId
     *
     * @param groupId detection group id
     * @return detection record list
     */
    List<ExeDefenderDetectEntity> selectByGroupId(String groupId);

    /**
     * Batch update detection records through exe Ids ignore status
     *
     * @param detectExeIds detection id
     * @param ignored Ignore or not
     * @return Update result
     */
    boolean batchUpdateStatusByDetectExeIds(List<String> detectExeIds, String ignored);

    /**
     * Query the execution status of the defense rules matched by the current node based on nodeId and pre/post
     *
     * @param nodeId nodeId
     * @param stage pre or post or null
     * @return detection record list
     */
    List<ExeDefenderDetectEntity> selectDetectStatusByNodeId(String nodeId, DefenseStageEnum stage);
}