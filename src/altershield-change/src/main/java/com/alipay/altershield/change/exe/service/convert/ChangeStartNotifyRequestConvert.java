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
package com.alipay.altershield.change.exe.service.convert;


import com.alipay.altershield.change.exe.service.check.model.ChangeExecOrderSubmitRequestModel;
import com.alipay.altershield.change.exe.service.check.model.ChangeNodeCreateModel;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.shared.change.exe.node.entity.*;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Objects;

import static com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum.STEP_GRAY_BATCH;
import static com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum.STEP_ORDER;

/**
 * The type Ops cloud change start notify request convert.
 *
 * @author yuanji
 * @version : OpsCloudChangeStartNotifyRequestConvert.java, v 0.1 2022年04月02日 4:25 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring")
public abstract class ChangeStartNotifyRequestConvert extends AbstractEntityConverter {

    /**
     * Convert.
     *
     * @param request the request
     * @param entity  the entity
     */
    @Mapping(target = "changeStepType", ignore = true)
    abstract public void convert(ChangeNodeCreateModel request, @MappingTarget ExeNodeEntity entity);

    /**
     * Convert ops cloud change exec order submit request.
     *
     * @param request the request
     * @return the ops cloud change exec order submit request
     */
    abstract public ChangeExecOrderSubmitRequest convert(ChangeExecOrderSubmitAndStartRequest request);

    /**
     * Convert exe change effective info entity.
     *
     * @param request the request
     * @return the exe change effective info entity
     */
    abstract public ExeChangeEffectiveInfoEntity convert(ChangeActionStartNotifyRequest request);

    /**
     * Convert ops cloud change exec order submit request model.
     *
     * @param request the request
     * @return the ops cloud change exec order submit request model
     */
    public abstract ChangeExecOrderSubmitRequestModel convert(ChangeStartNotifyRequest request);

    /**
     * Convert exe change batch effective info entity.
     *
     * @param request the request
     * @return the exe change batch effective info entity
     */
    @Mapping(target = "effectiveType", ignore = true)
    @Mapping(target = "effectiveLocations", ignore = true)
    abstract public ExeChangeBatchEffectiveInfoEntity convert(ChangeExecBatchStartNotifyRequest request);

    /**
     * After all.
     *
     * @param request the request
     * @param entity  the entity
     */
    @AfterMapping
    protected void afterAll(ChangeNodeCreateModel request, @MappingTarget ExeNodeEntity entity) {

        switch (entity.getChangeStepType()) {
            case STEP_GRAY_BATCH_ACTION:
            case STEP_BEFORE_GRAY_START:
            case STEP_AFTER_GRAY_FINISH:
                ExeActionNodeEntity exeActionNodeEntity = (ExeActionNodeEntity) entity;
                ChangeActionStartNotifyRequest r = (ChangeActionStartNotifyRequest) request.getRequest();
                ExeChangeEffectiveInfoEntity exeChangeEffectiveInfoEntity = convert(r);
                if (exeChangeEffectiveInfoEntity != null) {
                    exeActionNodeEntity.getChangeEffectiveInfoRef().write(exeChangeEffectiveInfoEntity);
                }
                entity.getParamRef().write(r.getChangeParamJson());
                entity.setChangePhase(ChangePhaseEnum.getByPhase(r.getChangePhase()));
                entity.setChangeKey(r.getChangeKey());
                break;
            case STEP_GRAY_BATCH:
                ExeBatchNodeEntity batchNodeEntity = (ExeBatchNodeEntity) entity;
                ChangeExecBatchStartNotifyRequest br = (ChangeExecBatchStartNotifyRequest) request.getRequest();
                entity.setChangePhase(ChangePhaseEnum.getByPhase(br.getChangePhase()));
                ExeChangeBatchEffectiveInfoEntity batchEffectiveInfoEntity = convert(br);
                if (batchEffectiveInfoEntity != null) {
                    batchNodeEntity.getChangeEffectiveInfoRef().write(batchEffectiveInfoEntity);
                }
                //默认使用 changeStepEntity 里面的changeKey
                if(Objects.nonNull(request.getChangeStepEntity())){
                    entity.setChangeKey(request.getChangeStepEntity().getChangeKey());
                }else{
                    entity.setChangeKey(br.getChangeSceneKey() + "._batch");
                }
                break;

            case STEP_ORDER:
                entity.setChangeKey(request.getChangeSceneKey());
                break;
        }
    }

    @AfterMapping
    protected void afterConvertToEffEntity(ChangeExecBatchStartNotifyRequest request, @MappingTarget ExeChangeBatchEffectiveInfoEntity entity) {
        entity.setEffectiveType(request.getEffectiveTargetType());
        entity.setEffectiveLocations(request.getEffectiveTargetLocations());
    }

    /**
     * Create exe node entity exe node entity.
     *
     * @param metaChangeStepTypeEnum the meta change step type enum
     * @return the exe node entity
     */
    public ExeNodeEntity createExeNodeEntity(MetaChangeStepTypeEnum metaChangeStepTypeEnum) {
        switch (metaChangeStepTypeEnum) {
            case STEP_ORDER:
                return new ExeOrderNodeEntity(shardingKeyValueStorage);
            case STEP_GRAY_BATCH:
                return new ExeBatchNodeEntity(shardingKeyValueStorage);
        }

        ExeActionNodeEntity exeActionNodeEntity = new ExeActionNodeEntity(shardingKeyValueStorage);
        exeActionNodeEntity.setChangeStepType(metaChangeStepTypeEnum);
        return exeActionNodeEntity;
    }

}