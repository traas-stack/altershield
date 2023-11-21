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
package com.alipay.altershield.web.change.meta.service;

import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.web.change.converter.MetaChangeSceneVOConverter;
import com.alipay.altershield.web.change.meta.vo.MetaChangeInterfaceVO;
import com.alipay.altershield.web.change.meta.vo.MetaChangeScene2VO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用于前端显示
 *
 * @author yuanji
 * @version : MetaChangeSceneConfigurationServiceImpl.java, v 0.1 2022年04月26日 8:39 下午 yuanji Exp $
 */
@Service
public class MetaChangeSceneConfigurationService implements InitializingBean {

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    private  List<MetaChangeInterfaceVO> g0Interface = null;
    private Map<String, MetaChangeInterfaceVO>                              g2InterfaceMap   = new HashMap<>();

    private static final String METHOD_INVOKE_WAY_SYNC = "sync";
    private static final String METHOD_INVOKE_WAY_ASYNC = "async";


    private void init()
    {
        g0Interface =  buildG0Interface();
        buildG2Interface();

    }
    private  List<MetaChangeInterfaceVO> buildG1Interface(MetaChangeStepEntity metaChangeStepEntity)
    {
        List<MetaChangeInterfaceVO> aList = new ArrayList<>(1);
        MetaChangeInterfaceVO metaChangeInterfaceVO = buildInterfaceInfo(metaChangeStepEntity);

        metaChangeInterfaceVO.setCanConfig(metaChangeStepEntity != null);
        metaChangeInterfaceVO.setInterfaceType(METHOD_INVOKE_WAY_SYNC);
        metaChangeInterfaceVO.setChangeStepName("提交变更执行单");
        metaChangeInterfaceVO.setParamDocumentUrl(AlterShieldConstant.ALTER_SHIELD_DOCUMENT_G1);
        metaChangeInterfaceVO.setInterfaceInfo("OpsCloudSimpleChangeClient.submitChangeExecOrder(OpsCloudChangeExecOrderSubmitRequest)");

        aList.add(metaChangeInterfaceVO);
        metaChangeInterfaceVO = new MetaChangeInterfaceVO();
        metaChangeInterfaceVO.setDisplayOnly(true);
        metaChangeInterfaceVO.setCanConfig(false);
        metaChangeInterfaceVO.setInterfaceType(METHOD_INVOKE_WAY_SYNC);
        metaChangeInterfaceVO.setChangeStepName("结束变更执行单");
        metaChangeInterfaceVO.setInterfaceInfo("OpsCloudSimpleChangeClient.finishChangeExecOrder(OpsCloudChangeExecOrderFinishRequest)");
        aList.add(metaChangeInterfaceVO);
        return aList;
    }


    private List<MetaChangeInterfaceVO> buildG0Interface()
    {
        List<MetaChangeInterfaceVO> aList = new ArrayList<>(1);
        MetaChangeInterfaceVO metaChangeInterfaceVO = new MetaChangeInterfaceVO();
        metaChangeInterfaceVO.setDisplayOnly(true);
        metaChangeInterfaceVO.setInterfaceType(METHOD_INVOKE_WAY_SYNC);
        metaChangeInterfaceVO.setChangeStepName("提交变更事件");
        metaChangeInterfaceVO.setParamDocumentUrl(AlterShieldConstant.ALTER_SHIELD_DOCUMENT_G0);
        metaChangeInterfaceVO.setInterfaceInfo("OpsCloudChangeNotifyClient.submitChangeEvent(OpsCloudChangeEventRequest)");
        aList.add(metaChangeInterfaceVO);
        return aList;
    }

    private void buildG2Interface()
    {
        String interfaceValue = "OpsCloudChangeClient.submitChangeStartNotify(%s)";
        createStepInterface(MetaChangeStepTypeEnum.STEP_ORDER.getStep(),String.format(interfaceValue,"OpsCloudChangeExecOrderStartNotifyRequest"));
        createStepInterface(MetaChangeStepTypeEnum.STEP_GRAY_BATCH.getStep(),String.format(interfaceValue,"OpsCloudChangeExecBatchStartNotifyRequest"));
        String actionInterfaceInfo = String.format(interfaceValue,"OpsCloudChangeActionStartNotifyRequest");
        createStepInterface(MetaChangeStepTypeEnum.STEP_GRAY_BATCH_ACTION.getStep(),actionInterfaceInfo);
        createStepInterface(MetaChangeStepTypeEnum.STEP_BEFORE_GRAY_START.getStep(),actionInterfaceInfo);
        createStepInterface(MetaChangeStepTypeEnum.STEP_AFTER_GRAY_FINISH.getStep(),actionInterfaceInfo);

    }

    private void createStepInterface(String typeName, String interfaceInfo)
    {
        MetaChangeInterfaceVO  metaChangeInterfaceVO = new MetaChangeInterfaceVO();
        metaChangeInterfaceVO.setDisplayOnly(false);
        metaChangeInterfaceVO.setCanConfig(true);
        metaChangeInterfaceVO.setInterfaceType(METHOD_INVOKE_WAY_ASYNC);
        metaChangeInterfaceVO.setInterfaceInfo(interfaceInfo);
        metaChangeInterfaceVO.setParamDocumentUrl(AlterShieldConstant.ALTER_SHIELD_DOCUMENT_G2);
        g2InterfaceMap.put(typeName,metaChangeInterfaceVO);
    }

    /**
     * Gets configuration info.
     *
     * @param changeSceneId the change scene id
     * @return the configuration info
     */
    public AlterShieldResult<MetaChangeScene2VO> getConfigurationInfo(String changeSceneId) {

        return ServiceProcessTemplate.wrapTryCatch(() ->{

            MetaChangeSceneEntity metaChangeSceneEntity =  metaChangeSceneRepository.getChangeSceneDetailsById(changeSceneId);
            if(metaChangeSceneEntity == null)
            {
                return AlterShieldResult.illegalArgument("change scene not found[" + changeSceneId +"]");
            }

            MetaChangeScene2VO metaChangeScene2VO = null;
            switch (metaChangeSceneEntity.getGeneration())
            {
                case G0:
                    metaChangeScene2VO= buildG0Result(metaChangeSceneEntity,g0Interface);
                    break;
                case G1:
                    metaChangeScene2VO= buildG1Result(metaChangeSceneEntity);
                    break;
                default:
                    metaChangeScene2VO= buildG2Result(metaChangeSceneEntity);

            }
            AlterShieldResult<MetaChangeScene2VO> result = new AlterShieldResult<>();
            result.setSuccess(true);
            result.setDomain(metaChangeScene2VO);
            return result;
        });
    }

    private MetaChangeScene2VO buildG0Result(MetaChangeSceneEntity metaChangeSceneEntity, List<MetaChangeInterfaceVO> metaChangeInterfaceVOS) {
        MetaChangeScene2VO metaChangeScene2VO = buildBaseInfo(metaChangeSceneEntity);
        metaChangeScene2VO.setMetaChangeInterfaceVOList(Collections.unmodifiableList(metaChangeInterfaceVOS));
        return metaChangeScene2VO;
    }

    private MetaChangeScene2VO buildBaseInfo(MetaChangeSceneEntity metaChangeSceneEntity)
    {
        MetaChangeScene2VO metaChangeScene2VO = new MetaChangeScene2VO();
        metaChangeScene2VO.setGeneration(metaChangeSceneEntity.getGeneration());
        metaChangeScene2VO.setChangeSceneId(metaChangeSceneEntity.getId());
        return metaChangeScene2VO;
    }

    private MetaChangeScene2VO buildG1Result(MetaChangeSceneEntity metaChangeSceneEntity) {
        MetaChangeScene2VO metaChangeScene2VO = buildBaseInfo(metaChangeSceneEntity);
        List<MetaChangeStepEntity> metaChangeStepEntityList = null;
        if(metaChangeSceneEntity.getChangeEffectiveConfig() != null && metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress() != null)
        {
            metaChangeStepEntityList = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps();
        }
        MetaChangeStepEntity metaChangeStepEntity = null;
        if(!CollectionUtils.isEmpty(metaChangeStepEntityList))
        {
            metaChangeStepEntity = metaChangeStepEntityList.get(0);
        }
        List<MetaChangeInterfaceVO> metaChangeInterfaceVOS = buildG1Interface(metaChangeStepEntity);
        metaChangeScene2VO.setMetaChangeInterfaceVOList(metaChangeInterfaceVOS);
        return metaChangeScene2VO;
    }

    private MetaChangeScene2VO buildG2Result(MetaChangeSceneEntity metaChangeSceneEntity) {
        MetaChangeScene2VO metaChangeScene2VO = buildBaseInfo(metaChangeSceneEntity);
        if(metaChangeSceneEntity.getCallbackConfigEntity() != null && !CollectionUtils.isEmpty(metaChangeSceneEntity.getCallbackConfigEntity().getCallbackConfig()))
        {
            metaChangeScene2VO.setCallbackConfig(new HashMap<>(metaChangeSceneEntity.getCallbackConfigEntity().getCallbackConfig()));
        }

        List<MetaChangeStepEntity> metaChangeStepEntityList = null;
        if(metaChangeSceneEntity.getChangeEffectiveConfig() != null && metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress() != null)
        {
            metaChangeStepEntityList = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps();
        }

        if(!CollectionUtils.isEmpty(metaChangeStepEntityList))
        {
            metaChangeScene2VO.setMetaChangeInterfaceVOList(metaChangeStepEntityList.stream().map(metaChangeStepEntity -> buildInterfaceInfo(metaChangeStepEntity)).collect(Collectors.toList()));
        }
        return metaChangeScene2VO;
    }

    private MetaChangeInterfaceVO buildInterfaceInfo(MetaChangeStepEntity metaChangeStepEntity)
    {
        MetaChangeInterfaceVO metaChangeInterfaceVO = MetaChangeSceneVOConverter.INSTANCE.convert(g2InterfaceMap.get(metaChangeStepEntity.getStepType().getStep()));
        metaChangeInterfaceVO.setChangeStepName(metaChangeStepEntity.getName());
        metaChangeInterfaceVO.setId(metaChangeStepEntity.getId());
        metaChangeInterfaceVO.setChangeKey(metaChangeStepEntity.getChangeKey());
        if(metaChangeStepEntity.getDefenceConfig() != null)
        {
            metaChangeInterfaceVO.setPostCheckTimeout(metaChangeStepEntity.getDefenceConfig().getPostCheckTimeout());
            metaChangeInterfaceVO.setPreCheckTimeout(metaChangeStepEntity.getDefenceConfig().getPreCheckTimeout());
        }
        return metaChangeInterfaceVO;
    }

    @Override
    public void afterPropertiesSet() {
        init();
    }
}