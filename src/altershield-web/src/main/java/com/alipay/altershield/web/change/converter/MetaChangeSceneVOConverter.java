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
package com.alipay.altershield.web.change.converter;

import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeProgressEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.change.meta.service.request.CreateTempMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeProgressRequest;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeStepRequest;
import com.alipay.altershield.framework.core.change.facade.request.MetaChangeEffectiveConfigRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldPageResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeGrayModeNameEnum;
import com.alipay.altershield.web.change.meta.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Request -> Entity
 *
 * @author yuanji
 * @version : MetaChangeSceneEntityMapper.java, v 0.1 2022年03月15日 8:28 下午 yuanji Exp $
 */
@Mapper
public interface MetaChangeSceneVOConverter {

    /**
     * The constant INSTANCE.
     */
    MetaChangeSceneVOConverter INSTANCE = Mappers.getMapper(MetaChangeSceneVOConverter.class);

    /**
     * The constant DEFAULT_PRE_TIMEOUT.
     */
    long DEFAULT_PRE_TIMEOUT = 1000;
    /**
     * The constant DEFAULT_POST_TIMEOUT.
     */
    long DEFAULT_POST_TIMEOUT = 10000;

    /**
     * Convert to base change scene list result page result.
     *
     * @param result the result
     * @return the page result
     */
    AlterShieldPageResult<List<MetaBaseChangeSceneVO>> convertToBaseChangeSceneListResult(AlterShieldPageResult<List<MetaBaseChangeSceneEntity>> result);

    /**
     * Convert meta change interface vo.
     *
     * @param metaChangeInterfaceVO the meta change interface vo
     * @return the meta change interface vo
     */
    MetaChangeInterfaceVO convert(MetaChangeInterfaceVO metaChangeInterfaceVO);

    /**
     * Convert tovo list.
     *
     * @param entities the entities
     * @return the list
     */
    List<MetaBaseChangeSceneVO> convertTOVO(List<MetaBaseChangeSceneEntity> entities);

    /**
     * Convert base change scene ops cloud result.
     *
     * @param entityAlterShieldResult the entity ops cloud result
     * @return the ops cloud result
     */
    AlterShieldResult<MetaChangeSceneVO> convertBaseChangeScene(AlterShieldResult<MetaChangeSceneEntity> entityAlterShieldResult);

    /**
     * Convert tovo meta change scene vo.
     *
     * @param entity the entity
     * @return the meta change scene vo
     */
    MetaChangeSceneVO convertTOVO(MetaChangeSceneEntity entity);

    /**
     * Convert create meta change scene request.
     *
     * @param createTempMetaChangeSceneRequest the create temp meta change scene request
     * @return the create meta change scene request
     */
    CreateMetaChangeSceneRequest convert(CreateTempMetaChangeSceneRequest createTempMetaChangeSceneRequest);

    /**
     * Convert meta change step vo.
     *
     * @param entity the entity
     * @return the meta change step vo
     */
    MetaChangeStepVO convert(MetaChangeStepEntity entity);

    /**
     * Convert meta change progress vo.
     *
     * @param entity the entity
     * @return the meta change progress vo
     */
    default MetaChangeProgressVO convert(MetaChangeProgressEntity entity)
    {
        if(entity == null)
        {
            return null;
        }

        MetaChangeProgressVO metaChangeProgressVO = new MetaChangeProgressVO();
        metaChangeProgressVO.setOrderChangeStep(convert(entity.getOrderChangeStep()));
        metaChangeProgressVO.setAfterGrayFinishStep(convert(entity.getAfterGrayFinishStep()));
        metaChangeProgressVO.setBeforeGrayStartStep(convert(entity.getBeforeGrayStartStep()));
        if(entity.getBatchStep() != null)
        {
            metaChangeProgressVO.setBatchChangeStep(convert(entity.getBatchStep()));
            if(!CollectionUtils.isEmpty(entity.getBatchStep().getChildChangActionSteps()))
            {
                metaChangeProgressVO.setGrayBatchActionSteps(entity.getBatchStep().getChildChangActionSteps().stream().map(action -> convert(action)).collect(Collectors.toList()));
            }
        }


        return metaChangeProgressVO;
    }

    /**
     * Convert standard create meta change scene request.
     *
     * @param createTempMetaChangeSceneRequest the create temp meta change scene request
     * @return the create meta change scene request
     */
    default CreateMetaChangeSceneRequest convertStandard(CreateTempMetaChangeSceneRequest createTempMetaChangeSceneRequest)
    {
        CreateMetaChangeSceneRequest  request = convert(createTempMetaChangeSceneRequest);
        MetaChangeEffectiveConfigRequest effectiveConfigRequest =  request.getChangeEffectiveConfig();
        if(effectiveConfigRequest == null)
        {
            effectiveConfigRequest = new MetaChangeEffectiveConfigRequest();
            request.setChangeEffectiveConfig(effectiveConfigRequest);
        }
        CreateMetaChangeProgressRequest changeProgress = effectiveConfigRequest.getChangeProgress();
        if(changeProgress == null)
        {
            changeProgress = new CreateMetaChangeProgressRequest();
            effectiveConfigRequest.setChangeProgress(changeProgress);
            effectiveConfigRequest.setChangeGrayModeType(MetaChangeGrayModeNameEnum.PIPELINE.getName());
        }
        if(createTempMetaChangeSceneRequest.getChangeEffectiveConfig() != null && createTempMetaChangeSceneRequest.getChangeEffectiveConfig().getChangeProgress() != null)
        {
            if(createTempMetaChangeSceneRequest.getChangeEffectiveConfig().getChangeProgress().isEnableBatchStep())
            {
                changeProgress.setBatchStep(new CreateMetaChangeStepRequest(true, DEFAULT_PRE_TIMEOUT, true, DEFAULT_POST_TIMEOUT));
            }
            if(createTempMetaChangeSceneRequest.getChangeEffectiveConfig().getChangeProgress().isEnableOrderStep())
            {
                changeProgress.setOrderStep(new CreateMetaChangeStepRequest(true, DEFAULT_PRE_TIMEOUT, true, DEFAULT_POST_TIMEOUT));
            }
        }
        return request;
    }


}