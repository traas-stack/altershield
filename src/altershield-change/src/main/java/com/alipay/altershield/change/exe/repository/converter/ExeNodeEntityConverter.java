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
package com.alipay.altershield.change.exe.repository.converter;

import com.alipay.altershield.change.exe.dal.dataobject.ExeNodeDO;
import com.alipay.altershield.common.largefield.convertor.ConvertUtil;
import com.alipay.altershield.shared.change.exe.node.entity.*;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.node.ref.ExeChangeRefCodec;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * The type Exe node entity converter.
 *
 * @author yuanji
 * @version : ExeChangeNodeConverter.java, v 0.1 2022年03月24日 7:27 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring")
public abstract class ExeNodeEntityConverter extends AbstractEntityConverter {

    /**
     * Convert to node entity.
     *
     * @param exeNodeDO the exe node do
     * @param entity    the entity
     */
    @Mapping(target = "trace", ignore = true)
    @Mapping(target = "paramRef", ignore = true)
    @Mapping(target = "rstRef", ignore = true)
    @Mapping(target = "searchExtRef", ignore = true)
    @Mapping(target = "exeNodeCheckInfo", ignore = true)
    @Mapping(target = "changeStepType", source = "nodeType")
    @Mapping(target = "contextRef", ignore = true)
    @Mapping(target = "changeContentRef", ignore = true)
    @Mapping(target = "changeTargetRef", ignore = true)
    @Mapping(target = "emergency", ignore = true)
    @Mapping(target = "changePhase", ignore = true)
    @Mapping(target = "status", ignore = true)
    abstract public void convertToNodeEntity(ExeNodeDO exeNodeDO, @MappingTarget ExeNodeEntity entity);

    /**
     * Convert to base node entity list.
     *
     * @param exeNodeDO the exe node do
     * @return the list
     */
    abstract public List<ExeBaseNodeEntity> convertToBaseNodeEntity(List<ExeNodeDO> exeNodeDO);

    /**
     * Convert to data object exe node do.
     *
     * @param entity the entity
     * @return the exe node do
     */
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "coord", ignore = true)
    @Mapping(target = "paramRef", ignore = true)
    @Mapping(target = "changeContentRef", ignore = true)
    @Mapping(target = "changeTargetRef", ignore = true)
    @Mapping(target = "nodeType", source = "changeStepType")
    @Mapping(target = "contextRef", ignore = true)
    @Mapping(target = "changeEffectiveInfoRef", ignore = true)
    @Mapping(target = "rstRef", ignore = true)
    @Mapping(target = "searchExtRef", ignore = true)
    @Mapping(target = "checkIdInfo", ignore = true)
    @Mapping(target = "emergencyFlag", ignore = true)
    @Mapping(target = "changePhase", ignore = true)
    abstract public ExeNodeDO convertToDataObject(ExeNodeEntity entity);

    /**
     * After convert entity.
     *
     * @param exeNodeDO the exe node do
     * @param entity    the entity
     */
    @AfterMapping
    protected void afterConvertEntity(ExeNodeDO exeNodeDO, @MappingTarget ExeNodeEntity entity) {
        entity.setSearchExtRef(convertKValueToRef(exeNodeDO.getSearchExtRef(), KvRefCodec.OBJ_MAP));
        entity.setParamRef(convertKValueToRef(exeNodeDO.getParamRef(), KvRefCodec.NOOP));
        entity.setRstRef(convertKValueToRef(exeNodeDO.getRstRef(), KvRefCodec.NOOP));
        entity.setChangeContentRef(convertKValueToRef(exeNodeDO.getChangeContentRef(), KvRefCodec.CHANGE_CONTENTS));
        entity.setChangeTargetRef(convertKValueToRef(exeNodeDO.getChangeTargetRef(), KvRefCodec.CHANGE_TARGETS));
        entity.setTrace(new OpsChngTrace(exeNodeDO.getTraceId(), exeNodeDO.getCoord()));
        entity.setEmergency("Y".equals(exeNodeDO.getEmergencyFlag()));

        if ( exeNodeDO.getStatus() != null ) {
            String status = exeNodeDO.getStatus();
            if ("INIT".equals(status)) {
                entity.setStatus(ExeNodeStateEnum.PRE_AOP_SUBMIT);
            } else {
                entity.setStatus( Enum.valueOf( ExeNodeStateEnum.class, status ) );
            }
        }

        if (entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH) {
            ((ExeBatchNodeEntity) entity).setChangeEffectiveInfoRef(convertKValueToRef(exeNodeDO.getChangeEffectiveInfoRef(),
                    ExeChangeRefCodec.BATCH_CHANGE_EFFECTIVE_INFO));
        } else if (entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_ATOMIC_ACTION || entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH_ACTION || entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_AFTER_GRAY_FINISH || entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_BEFORE_GRAY_START) {
            ((ExeActionNodeEntity) entity).setChangeEffectiveInfoRef(convertKValueToRef(exeNodeDO.getChangeEffectiveInfoRef(),
                    ExeChangeRefCodec.CHANGE_EFFECTIVE_INFO));
        }

        if (StringUtils.isNotBlank(exeNodeDO.getChangePhase())) {
            entity.setChangePhase(ChangePhaseEnum.getByPhase(exeNodeDO.getChangePhase()));
        }

        entity.setExeNodeCheckInfo(ConvertUtil.convertJsonStringToObject(exeNodeDO.getCheckIdInfo(), ExeNodeCheckInfo.class));
        entity.setContextRef(ConvertUtil.convertJsonStringToObject(exeNodeDO.getContextRef(), ExeNodeContext.class));
    }

    /**
     * After convert data object.
     *
     * @param entity    the entity
     * @param exeNodeDO the exe node do
     */
    @AfterMapping
    protected void afterConvertDataObject(ExeNodeEntity entity, @MappingTarget ExeNodeDO exeNodeDO) {
        exeNodeDO.setSearchExtRef(flushKvRef(entity.getSearchExtRef(), entity.getNodeExeId()));
        exeNodeDO.setCoord(entity.getTrace().getCoord().getCoord());
        exeNodeDO.setTraceId(entity.getTrace().getTraceId());
        exeNodeDO.setParamRef(flushKvRef(entity.getParamRef(), entity.getNodeExeId()));
        exeNodeDO.setRstRef(flushKvRef(entity.getRstRef(), entity.getNodeExeId()));
        exeNodeDO.setChangeContentRef(flushKvRef(entity.getChangeContentRef(), entity.getNodeExeId()));
        exeNodeDO.setChangeTargetRef(flushKvRef(entity.getChangeTargetRef(), entity.getNodeExeId()));
        exeNodeDO.setEmergencyFlag(entity.isEmergency() ? "Y" : "N");
        flushSearchExtRef(entity, exeNodeDO);
        if (entity.getChangePhase() != null) {
            exeNodeDO.setChangePhase(entity.getChangePhase().getPhase());
        }

        String ref = null;
        if (entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH) {
            ref = flushKvRef(((ExeBatchNodeEntity) entity).getChangeEffectiveInfoRef(), entity.getNodeExeId());

        } else if (entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_AFTER_GRAY_FINISH
                || entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_BEFORE_GRAY_START
                || entity.getChangeStepType() == MetaChangeStepTypeEnum.STEP_GRAY_BATCH_ACTION) {
            ref = flushKvRef(((ExeActionNodeEntity) entity).getChangeEffectiveInfoRef(), entity.getNodeExeId());
        }

        exeNodeDO.setChangeEffectiveInfoRef(ref);
        flushCheckInfo(entity, exeNodeDO);
        exeNodeDO.setContextRef(ConvertUtil.convertObjectToJsonString(entity.getContextRef()));

    }

    /**
     * Flush search ext ref.
     *
     * @param entity    the entity
     * @param exeNodeDO the exe node do
     */
    public void flushSearchExtRef(ExeNodeEntity entity, ExeNodeDO exeNodeDO)
    {
        exeNodeDO.setSearchExtRef(flushKvRef(entity.getSearchExtRef(), entity.getNodeExeId()));
    }

    /**
     * Flush result.
     *
     * @param entity    the entity
     * @param exeNodeDO the exe node do
     */
    public void flushResult(ExeNodeEntity entity, ExeNodeDO exeNodeDO)
    {
        exeNodeDO.setRstRef(flushKvRef(entity.getRstRef(), entity.getNodeExeId()));
    }

    /**
     * Flush check info.
     *
     * @param entity    the entity
     * @param exeNodeDO the exe node do
     */
    public void flushCheckInfo(ExeNodeEntity entity, ExeNodeDO exeNodeDO)
    {
        exeNodeDO.setCheckIdInfo(ConvertUtil.convertObjectToJsonString(entity.getExeNodeCheckInfo()));
    }


}