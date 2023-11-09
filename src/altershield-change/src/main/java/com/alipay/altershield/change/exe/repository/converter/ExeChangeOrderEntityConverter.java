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

import com.alipay.altershield.change.exe.dal.dataobject.ExeChangeOrderDO;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import com.alipay.altershield.common.largefield.convertor.ConvertUtil;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.order.entity.ExeBaseChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeOrderContext;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author yuanji
 * @version : ExeChangeOrderConverter.java, v 0.1 2022年03月24日 3:20 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring")
public abstract class ExeChangeOrderEntityConverter extends AbstractEntityConverter {

    public static final String delimiter = ",";

    @Mapping(target = "trace", ignore = true)
    @Mapping(target = "changeAppsRef", ignore = true)
    @Mapping(target = "paramRef", ignore = true)
    @Mapping(target = "resultRef", ignore = true)
    @Mapping(target = "changeContentRef", ignore = true)
    @Mapping(target = "changeTargetRef", ignore = true)
    @Mapping(target = "changePhases", ignore = true)
    @Mapping(target = "contextRef", ignore = true)
    @Mapping(target = "parentOrderInfoRef", ignore = true)
    abstract public ExeChangeOrderEntity convertToEntity(ExeChangeOrderDO exeChangeOrderDO);

    @Mapping(target = "trace", ignore = true)
    abstract public ExeBaseChangeOrderEntity convertToBaseEntity(ExeChangeOrderDO exeChangeOrderDO);


    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "coord", ignore = true)
    @Mapping(target = "changeAppsRef", ignore = true)
    @Mapping(target = "paramRef", ignore = true)
    @Mapping(target = "rstRef", ignore = true)
    @Mapping(target = "changeContentRef", ignore = true)
    @Mapping(target = "changeTargetRef", ignore = true)
    @Mapping(target = "changePhases", ignore = true)
    @Mapping(target = "contextRef", ignore = true)
    @Mapping(target = "parentOrderInfoRef", ignore = true)
    abstract public ExeChangeOrderDO convertToDataObject(ExeChangeOrderEntity entity);

    @AfterMapping
    protected void afterConvertEntity(ExeChangeOrderDO changeOrderDO, @MappingTarget ExeChangeOrderEntity entity) {
        entity.setChangeAppsRef(convertKValueToRef(changeOrderDO.getChangeAppsRef(), KvRefCodec.STR_SET));
        entity.setParamRef(convertKValueToRef(changeOrderDO.getParamRef(), KvRefCodec.NOOP));
        entity.setResultRef(convertKValueToRef(changeOrderDO.getRstRef(), KvRefCodec.NOOP));
        entity.setChangeContentRef(convertKValueToRef(changeOrderDO.getChangeContentRef(), KvRefCodec.CHANGE_CONTENTS));
        entity.setChangeTargetRef(convertKValueToRef(changeOrderDO.getChangeTargetRef(), KvRefCodec.CHANGE_TARGETS));
        entity.setParentOrderInfoRef(convertKValueToRef(changeOrderDO.getParentOrderInfoRef(),KvRefCodec.PARENT_ORDER_INFO));
        entity.setTrace(new OpsChngTrace(changeOrderDO.getTraceId(), changeOrderDO.getCoord()));
        entity.setContextRef(ConvertUtil.convertJsonStringToObject(changeOrderDO.getContextRef(), ExeOrderContext.class));
        String[] changePhaseArray = StringUtils.split(changeOrderDO.getChangePhases(), delimiter);
        if (changePhaseArray != null) {
            entity.setChangePhases(Arrays.asList(changePhaseArray).stream().map(s -> ChangePhaseEnum.getByPhase(s))
                    .collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    protected void afterConvertDataObject(ExeChangeOrderEntity entity, @MappingTarget ExeChangeOrderDO changeOrderDO) {
        changeOrderDO.setTraceId(entity.getTrace().getTraceId());
        changeOrderDO.setCoord(entity.getTrace().getCoord().getCoord());
        changeOrderDO.setParamRef(flushKvRef(entity.getParamRef(), entity.getOrderId()));
        changeOrderDO.setRstRef(flushKvRef(entity.getResultRef(), entity.getOrderId()));
        changeOrderDO.setChangeContentRef(flushKvRef(entity.getChangeContentRef(), entity.getOrderId()));
        changeOrderDO.setChangeTargetRef(flushKvRef(entity.getChangeTargetRef(), entity.getOrderId()));
        changeOrderDO.setParentOrderInfoRef(flushKvRef(entity.getParentOrderInfoRef(), entity.getOrderId()));
        changeOrderDO.setChangeAppsRef(flushKvRef(entity.getChangeAppsRef(), entity.getOrderId()));
        if (!CollectionUtils.isEmpty(entity.getChangePhases())) {
            changeOrderDO.setChangePhases(entity.getChangePhases().stream().sorted().map(c -> c.getPhase()).collect(Collectors.joining(delimiter)));
        }
        changeOrderDO.setContextRef(ConvertUtil.convertObjectToJsonString(entity.getContextRef()));

    }

}