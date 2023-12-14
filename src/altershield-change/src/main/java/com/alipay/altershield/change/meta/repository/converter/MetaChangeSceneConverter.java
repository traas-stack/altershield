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
package com.alipay.altershield.change.meta.repository.converter;

import com.alipay.altershield.change.meta.dal.dataobject.MetaBaseChangeSceneDO;
import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeSceneDO;
import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeStepDO;
import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeEffectiveConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.*;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeBatchEntity;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneBatchEntity;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The type Meta change scene converter.
 *
 * @author yuanji
 * @version : MetaChangeSceneEntityConverter.java, v 0.1 2022年03月11日 8:15 下午 yuanji Exp $
 */
@Component
public class MetaChangeSceneConverter {

    @Autowired
    private MetaChangeSceneEntityConverter metaChangeSceneEntityConverter;

    @Autowired
    private MetaChangeStepEntityConverter metaChangeStepEntityConverter;

    /**
     * To change scene do meta change scene do.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @return the meta change scene do
     */
    public MetaChangeSceneDO toChangeSceneDO(MetaChangeSceneEntity metaChangeSceneEntity) {
        return metaChangeSceneEntityConverter.covertToDataObject(metaChangeSceneEntity);
    }

    /**
     * To change step do meta change step do.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @return the meta change step do
     */
    public MetaChangeStepDO toChangeStepDO(MetaChangeStepEntity metaChangeStepEntity) {
        return metaChangeStepEntityConverter.convertToDataObject(metaChangeStepEntity);
    }

    /**
     * To change step do list list.
     *
     * @param metaChangeStepEntityList the meta change step entity list
     * @return the list
     */
    public List<MetaChangeStepDO> toChangeStepDOList(List<MetaChangeStepEntity> metaChangeStepEntityList) {
        return metaChangeStepEntityConverter.convertToDataObjectList(metaChangeStepEntityList);
    }

    /**
     * Convert to change scene entity list list.
     *
     * @param metaChangeStepEntityList the meta change step entity list
     * @return the list
     */
    public List<MetaBaseChangeSceneEntity> convertToChangeSceneEntityList(List<MetaBaseChangeSceneDO> metaChangeStepEntityList) {
        return metaChangeSceneEntityConverter.convertToEntityList(metaChangeStepEntityList);
    }

    /**
     * 完整的转换
     *
     * @param changeSceneDO the change scene do
     * @return meta change scene entity
     */
    public MetaChangeSceneEntity convertToFullEntity(MetaChangeSceneDO changeSceneDO) {
        if (changeSceneDO == null) {
            return null;
        }
        MetaChangeSceneEntity entity = metaChangeSceneEntityConverter.covertToEntity(changeSceneDO);
        MetaChangeEffectiveConfigEntity changeConfigEntity = entity.getChangeEffectiveConfig();
        if (!CollectionUtils.isEmpty(changeSceneDO.getChangeSteps())) {
            if (changeConfigEntity == null) {
                changeConfigEntity = new MetaChangeEffectiveConfigEntity();
                entity.setChangeEffectiveConfig(changeConfigEntity);
            }
            MetaChangeProgressEntity progressEntity = buildOpsCloudMetaChangeProgressEntity(changeSceneDO.getChangeSteps());
            changeConfigEntity.setChangeProgress(progressEntity);
        }
        return entity;
    }

    private MetaChangeProgressEntity buildOpsCloudMetaChangeProgressEntity(List<MetaChangeStepDO> changeStepDOList) {
        MetaChangeProgressEntity changeProgressEntity = new MetaChangeProgressEntity();

        MetaChangeBatchStepEntity batchEntity = null;
        for (MetaChangeStepDO metaChangeStepDO : changeStepDOList) {
            MetaChangeStepTypeEnum stepTypeEnum = MetaChangeStepTypeEnum.valueOf(metaChangeStepDO.getStepType());
            if (stepTypeEnum == null) {
                throw new RuntimeException("illegal step type " + metaChangeStepDO);
            }
            switch (stepTypeEnum) {
                case STEP_ORDER:
                    convertToStepEntity(metaChangeStepDO, new MetaChangeOrderStepEntity(),
                            metaChangeStepEntity -> changeProgressEntity.setOrderChangeStep(
                                    (MetaChangeOrderStepEntity) metaChangeStepEntity));
                    break;
                case STEP_BEFORE_GRAY_START:
                    convertToStepEntity(metaChangeStepDO, new MetaChangeActionStepEntity(),
                            metaChangeStepEntity -> changeProgressEntity.setBeforeGrayStartStep(
                                    (MetaChangeActionStepEntity) metaChangeStepEntity));
                    break;
                case STEP_AFTER_GRAY_FINISH:
                    convertToStepEntity(metaChangeStepDO, new MetaChangeActionStepEntity(),
                            metaChangeStepEntity -> changeProgressEntity.setAfterGrayFinishStep(
                                    (MetaChangeActionStepEntity) metaChangeStepEntity));
                    break;
                case STEP_GRAY_BATCH:
                    if (batchEntity == null) {
                        batchEntity = new MetaChangeBatchStepEntity();
                    }
                    metaChangeStepEntityConverter.convertToEntity(metaChangeStepDO, batchEntity);
                    convertToStepEntity(metaChangeStepDO, batchEntity,
                            metaChangeStepEntity -> changeProgressEntity.setBatchStep((MetaChangeBatchStepEntity) metaChangeStepEntity));
                    break;
                case STEP_GRAY_BATCH_ACTION:
                    if (batchEntity == null) {
                        batchEntity = new MetaChangeBatchStepEntity();
                    }

                    List<MetaChangeActionStepEntity> batchActionList = batchEntity.getChildChangActionSteps();
                    if (batchActionList == null) {
                        batchActionList = new ArrayList<>();
                        batchEntity.setChildChangActionSteps(batchActionList);
                    }
                    MetaChangeActionStepEntity batchActionEntity = new MetaChangeActionStepEntity();
                    metaChangeStepEntityConverter.convertToEntity(metaChangeStepDO, batchActionEntity);
                    batchActionList.add(batchActionEntity);
                    break;
                default:
                    break;
            }
        }
        return changeProgressEntity;
    }

    private void convertToStepEntity(MetaChangeStepDO metaChangeStepDO, MetaChangeStepEntity metaChangeStepEntity,
                                     Consumer<MetaChangeStepEntity> consumer) {
        metaChangeStepEntityConverter.convertToEntity(metaChangeStepDO, metaChangeStepEntity);
        consumer.accept(metaChangeStepEntity);
    }

    /**
     * Convert to step entity meta change step entity.
     *
     * @param metaChangeStepDO the meta change step do
     * @return the meta change step entity
     */
    public MetaChangeStepEntity convertToStepEntity(MetaChangeStepDO metaChangeStepDO) {
        if (metaChangeStepDO == null) {
            return null;
        }

        MetaChangeStepTypeEnum stepTypeEnum = MetaChangeStepTypeEnum.valueOf(metaChangeStepDO.getStepType());
        if (stepTypeEnum == null) {
            throw new RuntimeException("illegal step type " + metaChangeStepDO);
        }
        MetaChangeStepEntity metaChangeStepEntity = null;
        switch (stepTypeEnum) {
            case STEP_ORDER:
                metaChangeStepEntity = new MetaChangeOrderStepEntity();
                break;
            case STEP_BEFORE_GRAY_START:
            case STEP_AFTER_GRAY_FINISH:
            case STEP_GRAY_BATCH_ACTION:
                metaChangeStepEntity = new MetaChangeActionStepEntity();
                break;

            case STEP_GRAY_BATCH:
                metaChangeStepEntity = new MetaChangeBatchStepEntity();
                break;
            default:
                break;
        }
        metaChangeStepEntityConverter.convertToEntity(metaChangeStepDO, metaChangeStepEntity);
        return metaChangeStepEntity;
    }
}