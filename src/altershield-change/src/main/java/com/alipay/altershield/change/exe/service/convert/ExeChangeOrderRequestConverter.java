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
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderSubmitAndStartRequest;
import com.alipay.altershield.framework.core.change.facade.request.ChangeFinishNotifyRequest;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The type Exe change order request converter.
 *
 * @author yuanji
 * @version : ChangeExecOrderRequestConverter.java, v 0.1 2022年04月01日 2:50 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring")
public abstract class ExeChangeOrderRequestConverter extends AbstractEntityConverter {

    /**
     * Convert.
     *
     * @param request     the request
     * @param orderEntity the order entity
     */
    @Mapping(target = "contextRef", ignore = true)
    @Mapping(target = "changePhases", ignore = true)

    abstract public void convert(ChangeExecOrderSubmitRequestModel request, @MappingTarget ExeChangeOrderEntity orderEntity);

    /**
     * Covert string.
     *
     * @param opsCloudChangeScenarioEnum the ops cloud change scenario enum
     * @return the string
     */
    protected String covert(ChangeScenarioEnum opsCloudChangeScenarioEnum)
    {
        return opsCloudChangeScenarioEnum.getCode();
    }

    /**
     * Covert to ops cloud change scenario enum ops cloud change scenario enum.
     *
     * @param code the code
     * @return the ops cloud change scenario enum
     */
    protected ChangeScenarioEnum covertToOpsCloudChangeScenarioEnum(String code)
    {
        return ChangeScenarioEnum.getByCode(code);
    }

    /**
     * Convert ops cloud change exec order submit request model.
     *
     * @param request the request
     * @return the ops cloud change exec order submit request model
     */
    abstract public ChangeExecOrderSubmitRequestModel convert(ChangeExecOrderSubmitAndStartRequest request);


    /**
     * 转finish request
     * @param request
     * @return
     */
    abstract public ChangeFinishNotifyRequest covertFinishRequest(ChangeExecOrderFinishRequest request);
    /**
     * Write ref.
     *
     * @param request     the request
     * @param orderEntity the order entity
     */
    @AfterMapping
    protected void writeRef(ChangeExecOrderSubmitRequestModel request,  @MappingTarget ExeChangeOrderEntity orderEntity)
    {
        orderEntity.getParamRef().write(request.getChangeParamJson());
        if(request.getChangeContents() != null)
        {
            orderEntity.getChangeContentRef().write(Arrays.asList(request.getChangeContents()));
        }
        if(!CollectionUtils.isEmpty(request.getChangePhases())) {
            orderEntity.setChangePhases(request.getChangePhases().stream().map(s -> {
                ChangePhaseEnum opsCloudChangePhaseEnum = ChangePhaseEnum.getByPhase(s);
                if(opsCloudChangePhaseEnum == null)
                {
                    throw new IllegalArgumentException("invalid change phase " + s);
                }
                return ChangePhaseEnum.getByPhase(s);})
                    .collect(Collectors.toSet()));
        }
        if(request.getParentOrderInfo() != null)
        {
            orderEntity.getParentOrderInfoRef().write(request.getParentOrderInfo());
        }
        orderEntity.getChangeAppsRef().write(request.getChangeApps());
    }

    /**
     * Create change order exe change order entity.
     *
     * @return the exe change order entity
     */
    public ExeChangeOrderEntity createChangeOrder()
    {
        return new ExeChangeOrderEntity(shardingKeyValueStorage);
    }
}