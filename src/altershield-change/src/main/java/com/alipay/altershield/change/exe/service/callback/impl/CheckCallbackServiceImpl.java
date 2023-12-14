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
package com.alipay.altershield.change.exe.service.callback.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.altershield.change.exe.service.callback.ChangeCheckCallbackProxy;
import com.alipay.altershield.change.exe.service.callback.ChangeCheckCallbackProxyModel;
import com.alipay.altershield.change.exe.service.callback.CheckCallbackService;
import com.alipay.altershield.change.meta.model.MetaChangeSceneCallbackConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.repository.MetaChangePlatformRepository;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.callback.request.ChangeCheckCallbackRequest;
import com.alipay.altershield.framework.core.change.facade.callback.result.ChangeCheckCallbackResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.change.model.enums.ChangeCheckTypeEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.service.ExeChangeNodeService;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.schedule.event.change.NodeCheckCallbackEvent;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventContext;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventListener;
import com.alipay.altershield.shared.schedule.event.result.AlterShieldSchedulerEventExecuteResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Check callback service.
 *
 * @author yuanji
 * @version : CheckCallbackServiceImpl.java, v 0.1 2022年04月07日 1:47 下午 yuanji Exp $
 */
@Service
public class CheckCallbackServiceImpl implements CheckCallbackService, InitializingBean,
        AlterShieldSchedulerEventListener<NodeCheckCallbackEvent> {

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    @Value("${spring.profiles.active}")
    private String env;

    private static final String DEFAULT_ENV = "DEFAULT";

    @Autowired
    private MetaChangePlatformRepository metaChangePlatformRepository;

    @Autowired
    private ChangeCheckCallbackProxy changeCheckCallbackProxy;

    @Autowired
    private ExeChangeOrderRepository exeChangeOrderRepository;

    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ExeChangeNodeService exeChangeNodeService;


    private static Logger logger = Loggers.OUTER_TASK;


    /**
     * Async check callback ops cloud change check callback result.
     *
     * @param entity       the entity
     * @param verdict      the verdict
     * @param defenseStage the defense stage
     * @return the ops cloud change check callback result
     */
    @Override
    public ChangeCheckCallbackResult asyncCheckCallback(ExeNodeEntity entity, ChangeCheckVerdict verdict,
                                                        DefenseStageEnum defenseStage, String checkId) {
        AlterShieldLoggerManager.log("info", logger, "start asyncCheckCallback", checkId, entity);
        MetaChangeSceneEntity changeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(entity.getChangeSceneKey());
        if (changeSceneEntity == null) {
            AlterShieldLoggerManager.log("warn", logger, "change scene not found for:" + entity.getChangeSceneKey());
            return null;
        }
        String platform = changeSceneEntity.getPlatformName();
        if (StringUtils.isBlank(platform)) {
            AlterShieldLoggerManager.log("warn", logger, "change scene platform not found for:" + entity.getChangeSceneKey());
            return null;
        }

        String url = getCallbackLocation(changeSceneEntity.getCallbackConfigEntity());
        if (url == null) {
            AlterShieldLoggerManager.log("warn", logger, "change scene callback config  not found ",
                    changeSceneEntity.getCallbackConfigEntity());
            return null;
        }
        AlterShieldLoggerManager.log("debug", logger, "get call back url", url);
        ChangeCheckCallbackProxyModel model = create(platform, url, entity, defenseStage, checkId, verdict);
        return changeCheckCallbackProxy.doCallback(model);
    }

    /**
     * Async check callback ops cloud change check callback result.
     *
     * @param entity       the entity
     * @param verdict      the verdict
     * @param defenseStage the defense stage
     * @return the ops cloud change check callback result
     */
    public ChangeCheckCallbackResult asyncCheckCallback(ExeNodeEntity entity, ChangeCheckVerdict verdict,
                                                                DefenseStageEnum defenseStage) {

        return asyncCheckCallback(entity, verdict, defenseStage, null);
    }

    private ChangeCheckCallbackProxyModel create(String platform, String url, ExeNodeEntity entity, DefenseStageEnum defenseStage,
                                                         String checkId, ChangeCheckVerdict verdict) {
        ChangeCheckCallbackProxyModel model = new ChangeCheckCallbackProxyModel();
        String token = metaChangePlatformRepository.getPlatformToken(platform);
        model.setPlatform(platform);
        model.setToken(token);
        model.setUrl(url);
        ChangeCheckCallbackRequest request = new ChangeCheckCallbackRequest();


        request.setBizExecOrderId(exeChangeOrderRepository.getBizExecOrderIdByOrderId(entity.getOrderId()));
        request.setDefenseStageEnum(defenseStage);
        request.setVerdict(verdict);
        request.setChangeSceneKey(entity.getChangeSceneKey());
        if (StringUtils.isNotBlank(checkId)) {
            request.setNodeId(checkId);
        } else {
            request.setNodeId(entity.getNodeExeId());
        }
        MetaChangeStepTypeEnum changeStepType = entity.getChangeStepType();
        ChangeCheckTypeEnum checkType = null;
        switch (changeStepType) {
            case STEP_GRAY_BATCH:
                checkType = ChangeCheckTypeEnum.CHANGE_BATCH;
                break;
            case STEP_ORDER:
                checkType = ChangeCheckTypeEnum.CHANGE_ORDER;
                break;
            default:
                checkType = ChangeCheckTypeEnum.CHANGE_ACTION;

        }
        model.setCheckType(checkType);
        model.setRequest(request);
        return model;
    }

    private String getCallbackLocation(MetaChangeSceneCallbackConfigEntity entity) {
        if (entity == null) {
            return null;
        }

        String callbackUrl = entity.getCallbackConfig().get(env);
        if (StringUtils.isBlank(callbackUrl)) {
            callbackUrl = entity.getCallbackConfig().get(DEFAULT_ENV);
        }
        return callbackUrl;
    }

    @Override
    public void afterPropertiesSet()  {
        env = StringUtils.upperCase(env);
    }

    @Override
    public AlterShieldSchedulerEventExecuteResult onEvent(AlterShieldSchedulerEventContext context,NodeCheckCallbackEvent event) {

        ExeNodeEntity exeNodeEntity = exeChangeNodeRepository.getNodeEntity(event.getNodeExeId());
        if (exeNodeEntity == null) {
            AlterShieldLoggerManager.log("WARN", logger, "exe node not found", event.getNodeExeId());
            return null;
        }
        ChangeCheckVerdict verdict = query(exeNodeEntity, event.getDefenseStage());
        AlterShieldLoggerManager.log("info", logger, "check.callback.start", event.getNodeExeId());
        asyncCheckCallback(exeNodeEntity, verdict, event.getDefenseStage(), null);
        AlterShieldLoggerManager.log("info", logger, "check.callback.finish", event.getNodeExeId());
        return AlterShieldSchedulerEventExecuteResult.success("check.callback.success");
    }


    private ChangeCheckVerdict query(ExeNodeEntity entity, DefenseStageEnum defenseStageEnum)
    {
        boolean checkFinishSuccess = true;
        if(defenseStageEnum == DefenseStageEnum.PRE)
        {
            checkFinishSuccess = entity.getStatus() == ExeNodeStateEnum.PRE_AOP_FINISH;
        }
        else
        {
            checkFinishSuccess = entity.getStatus() == ExeNodeStateEnum.POST_AOP_FINISH;

        }

        return  exeChangeNodeService.queryChangeVerdict(entity,defenseStageEnum, true, checkFinishSuccess);
    }
}