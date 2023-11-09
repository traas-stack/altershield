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
package com.alipay.altershield.change.meta.service.request.converter;

import com.alipay.opscloud.api.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.opscloud.change.meta.dal.dataobject.MetaChangeSceneQueryParam;
import com.alipay.opscloud.change.meta.model.MetaChangeDefenceConfigEntity;
import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.model.effective.*;
import com.alipay.opscloud.change.meta.model.enums.MetaChangeGrayModeTypeEnum;
import com.alipay.opscloud.change.meta.repository.converter.MetaChangeSceneEnumMapper;
import com.alipay.opscloud.change.meta.service.request.QueryChangeSceneRequest;
import com.alipay.opscloud.change.meta.service.request.UpdateDefenceConfigRequest;
import com.alipay.opscloud.change.meta.service.request.UpdateMetaChangeSceneRequest;
import com.alipay.opscloud.framework.core.meta.change.facade.request.*;
import com.alipay.opscloud.framework.core.meta.change.model.enums.MetaChangeSceneGenerationEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Meta change scene request converter.
 *
 * @author yuanji
 * @version : MetaChangeSceneRequestConverter.java, v 0.1 2022年03月17日 5:44 下午 yuanji Exp $
 */
@Mapper(uses = MetaChangeSceneEnumMapper.class)
public abstract class MetaChangeSceneRequestConverter {

    /**
     * The constant INSTANCE.
     */
    public static final MetaChangeSceneRequestConverter INSTANCE = Mappers.getMapper(MetaChangeSceneRequestConverter.class);

    /**
     * Convert to entity meta change scene entity.
     *
     * @param request the request
     * @return the meta change scene entity
     */
    @Mapping(ignore = true, target = "callbackConfigEntity")
    @Mapping(ignore = true, target = "changeEffectiveConfig.changeProgress")
    abstract public MetaChangeSceneEntity convertToEntity(CreateMetaChangeSceneRequest request);

    /**
     * Convert to change step entity list list.
     *
     * @param updateDefenceConfigRequests the update defence config requests
     * @return the list
     */
    public List<MetaChangeStepEntity> convertTOChangeStepEntityList(List<UpdateDefenceConfigRequest> updateDefenceConfigRequests)
    {
        if(updateDefenceConfigRequests == null)
        {
            return null;
        }
        return updateDefenceConfigRequests.stream().map( updateDefenceConfigRequest -> {

            MetaChangeStepEntity metaChangeStepEntity = new MetaChangeStepEntity();
            metaChangeStepEntity.setId(updateDefenceConfigRequest.getId());
            metaChangeStepEntity.setDefenceConfig(convertTOChangeStepEntity(updateDefenceConfigRequest));
            return metaChangeStepEntity;
        }).collect(Collectors.toList());

    }

    /**
     * Convert to change step entity meta change defence config entity.
     *
     * @param updateDefenceConfigRequests the update defence config requests
     * @return the meta change defence config entity
     */
    abstract public MetaChangeDefenceConfigEntity convertTOChangeStepEntity(UpdateDefenceConfigRequest updateDefenceConfigRequests);

    /**
     * 把外部请求转成内部query
     * @param request
     * @return
     */
    abstract public MetaChangeSceneQueryParam convertQueryParam(QueryChangeSceneRequest request);


    /**
     * Covert meta change progress entity.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @param changeProgress        the change progress
     * @param function              the function
     * @return the meta change progress entity
     */
    public MetaChangeProgressEntity covert(MetaChangeSceneEntity metaChangeSceneEntity, CreateMetaChangeProgressRequest changeProgress, Function<String, String> function) {
        if (changeProgress == null) {
            return null;
        }

        MetaChangeProgressEntity entity = new MetaChangeProgressEntity();
        if (changeProgress.getOrderStep() != null) {
            MetaChangeOrderStepEntity orderStepEntity = buildMetaChangeOrderStepEntity(metaChangeSceneEntity, changeProgress.getOrderStep(),function);
            entity.setOrderChangeStep(orderStepEntity);
        }
        if (changeProgress.getBatchStep() != null) {

            MetaChangeBatchStepEntity batchStepEntity = new MetaChangeBatchStepEntity();
            buildStep(metaChangeSceneEntity, changeProgress.getBatchStep(), batchStepEntity, function);
            entity.setBatchStep(batchStepEntity);
        }

        if (changeProgress.getBeforeGrayStartStep() != null) {
            entity.setBeforeGrayStartStep(
                    createMetaChangeAction(metaChangeSceneEntity, changeProgress.getBeforeGrayStartStep(),
                            MetaChangeStepTypeEnum.STEP_BEFORE_GRAY_START, function));
        }

        if (changeProgress.getAfterGrayFinishStep() != null) {
            entity.setAfterGrayFinishStep(
                    createMetaChangeAction(metaChangeSceneEntity, changeProgress.getAfterGrayFinishStep(),
                            MetaChangeStepTypeEnum.STEP_AFTER_GRAY_FINISH, function));
        }

        if (!CollectionUtils.isEmpty(changeProgress.getGrayBatchActionSteps())) {
            List<MetaChangeActionStepEntity> actionEntityList = changeProgress.getGrayBatchActionSteps().stream().map(
                    createMetaChangeStepRequest -> createMetaChangeAction(metaChangeSceneEntity,
                            createMetaChangeStepRequest, MetaChangeStepTypeEnum.STEP_GRAY_BATCH_ACTION, function)).collect(
                    Collectors.toList());
            entity.getBatchStep().setChildChangActionSteps(actionEntityList);
        }

        return entity;
    }

    /**
     * Build meta change order step entity meta change order step entity.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @param changeStepRequest     the change step request
     * @param function              the function
     * @return the meta change order step entity
     */
    public MetaChangeOrderStepEntity buildMetaChangeOrderStepEntity(MetaChangeSceneEntity metaChangeSceneEntity,CreateMetaChangeStepRequest changeStepRequest,
                                                                    Function<String, String> function) {
        MetaChangeOrderStepEntity orderStepEntity = new MetaChangeOrderStepEntity();
        buildStep(metaChangeSceneEntity, changeStepRequest, orderStepEntity, function);
        return orderStepEntity;
    }

    private void buildStep(MetaChangeSceneEntity metaChangeSceneEntity, CreateMetaChangeStepRequest changeStepRequest, MetaChangeStepEntity metaChangeStepEntity,
                           Function<String, String> function) {
        buildBaseStepEntity(metaChangeSceneEntity, metaChangeStepEntity, function);

        convert(changeStepRequest, metaChangeStepEntity);
    }

    private MetaChangeActionStepEntity createMetaChangeAction(MetaChangeSceneEntity metaChangeSceneEntity, CreateActionChangeStepRequest changeStepRequest,
                                                              MetaChangeStepTypeEnum metaChangeStepTypeEnum, Function<String, String> function) {
        MetaChangeActionStepEntity metaChangeActionStepEntity = new MetaChangeActionStepEntity();
        metaChangeActionStepEntity.setStepType(metaChangeStepTypeEnum);
        buildBaseStepEntity(metaChangeSceneEntity, metaChangeActionStepEntity,function);
        convertActionStep(changeStepRequest, metaChangeActionStepEntity);
        return metaChangeActionStepEntity;
    }

    /**
     * Convert.
     *
     * @param changeStepRequest    the change step request
     * @param metaChangeStepEntity the meta change step entity
     */
    @Mapping(target = "changeSceneKey", ignore = true)
    @Mapping(target = "stepType", ignore = true)
    abstract void convert(CreateMetaChangeStepRequest changeStepRequest, @MappingTarget MetaChangeStepEntity metaChangeStepEntity);


    /**
     * Convert.
     *
     * @param changeStepRequest    the change step request
     * @param metaChangeStepEntity the meta change step entity
     */
    @Mapping(target = "changeSceneKey", ignore = true)
    @Mapping(target = "stepType", ignore = true)
    abstract void convertActionStep(CreateActionChangeStepRequest changeStepRequest, @MappingTarget MetaChangeStepEntity metaChangeStepEntity);

    /**
     * Convert meta change defence config entity.
     *
     * @param metaChangeDefenceConfigRequest the meta change defence config request
     * @return the meta change defence config entity
     */
    abstract MetaChangeDefenceConfigEntity convert(MetaChangeDefenceConfigRequest metaChangeDefenceConfigRequest);


    private void buildBaseStepEntity(MetaChangeSceneEntity metaChangeSceneEntity, MetaChangeStepEntity metaChangeStepEntity, Function<String, String> function) {
        metaChangeStepEntity.setChangeSceneKey(metaChangeSceneEntity.getChangeSceneKey());
        if (metaChangeStepEntity.getStepType() == MetaChangeStepTypeEnum.STEP_ORDER) {
            buildStepName(metaChangeSceneEntity, metaChangeStepEntity);
            metaChangeStepEntity.setChangeKey(metaChangeSceneEntity.getChangeSceneKey());
            metaChangeStepEntity.setChangeContentType(metaChangeSceneEntity.getChangeContentType());
            metaChangeStepEntity.setChangeTargetType(metaChangeSceneEntity.getChangeTargetType());
        }
        if (metaChangeStepEntity.getStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH) {
            buildStepName(metaChangeSceneEntity, metaChangeStepEntity);
            metaChangeStepEntity.setChangeContentType(metaChangeSceneEntity.getChangeContentType());
            metaChangeStepEntity.setChangeTargetType(metaChangeSceneEntity.getChangeTargetType());
            metaChangeStepEntity.setChangeKey(
                    metaChangeSceneEntity.getChangeSceneKey() + metaChangeSceneEntity.getChangeEffectiveConfig().getChangeGrayModeType()
                            .getChangKeySuffix());
        }

        //todo 后续开源的时候HISTORYMODE相关的都要移除
        if(Objects.nonNull(metaChangeSceneEntity.getGeneration()) && MetaChangeSceneGenerationEnum.G2.getName().equals(metaChangeSceneEntity.getGeneration().getName()) &&
                Objects.nonNull(metaChangeSceneEntity.getChangeEffectiveConfig()) && Objects.nonNull(metaChangeSceneEntity.getChangeEffectiveConfig().getChangeGrayModeType()) &&
                MetaChangeGrayModeTypeEnum.HISTORYMODE.getName().equals(metaChangeSceneEntity.getChangeEffectiveConfig().getChangeGrayModeType().getName())){
            //先生成id
            metaChangeStepEntity.setId(function.apply(metaChangeStepEntity.getChangeKey()+"HISTORYMODE"));
            return;
        }
        metaChangeStepEntity.setId(function.apply(metaChangeStepEntity.getChangeKey()));

    }

    public static void buildStepName(MetaChangeSceneEntity metaChangeSceneEntity, MetaChangeStepEntity metaChangeStepEntity)
    {
        if (metaChangeStepEntity.getStepType() == MetaChangeStepTypeEnum.STEP_ORDER) {
            metaChangeStepEntity.setName(metaChangeSceneEntity.getName() + "变更工单");
        }
        if (metaChangeStepEntity.getStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH) {
            metaChangeStepEntity.setName(metaChangeSceneEntity.getName() + "变更分批");
        }
    }

    /**
     * Convert to entity meta change scene entity.
     *
     * @param request the request
     * @return the meta change scene entity
     */
    @Mapping(ignore = true, target = "callbackConfigEntity")
    abstract public MetaChangeSceneEntity convertToEntity(UpdateMetaChangeSceneRequest request);

}