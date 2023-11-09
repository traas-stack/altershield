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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.meta.service.impl;

import com.alipay.opscloud.api.scheduler.event.change.OpsCloudMetaChangeSceneCreateEvent;
import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.opscloud.change.meta.service.MetaChangeSceneEventService;
import com.alipay.opscloud.change.meta.service.event.converter.MetaChangeSceneEventConverter;
import com.alipay.opscloud.framework.common.util.JSONUtil;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.event.OpsCloudChangeSceneCreateEvent;
import com.alipay.opscloud.framework.event.OpsCloudEventTypeEnum;
import com.alipay.opscloud.framework.event.OpsCloudEventWrapper;
import com.alipay.opscloud.framework.event.model.changescene.MetaChangeSceneEO;
import com.alipay.opscloud.framework.event.spi.OpsCloudEventFacade;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Meta change scene event service.
 *
 * @author yuanji
 * @version : MetaChangeSceneEventServiceImpl.java, v 0.1 2022年07月18日 20:11 yuanji Exp $
 */
@Service
public class MetaChangeSceneEventServiceImpl implements MetaChangeSceneEventService {


    private OpsCloudEventFacade opsCloudEventFacade;

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    private static final Logger             logger = Loggers.OUTTER_TASK;

    @Override
    public void publishChangeSceneCreatedEvent(MetaChangeSceneEntity metaChangeSceneEntity) {

        if(metaChangeSceneEntity == null)
        {
            OpsCloudLoggerManager.log("warn", logger, "metaChangeSceneEntity is null");
            return;
        }
        if (opsCloudEventFacade == null) {
            OpsCloudLoggerManager.log("warn", logger, "event facade not init");
            return;
        }
        if (!filterEvent(metaChangeSceneEntity)) {
            OpsCloudLoggerManager.log("info", logger, "ignore fire event", metaChangeSceneEntity.getChangeSceneKey());
            return;
        }
        try {
            MetaChangeSceneEO metaChangeSceneEO = convert(metaChangeSceneEntity);
            triggerSendEvent(metaChangeSceneEO);
        } catch (Exception e) {
            OpsCloudLoggerManager.log("error", logger, e, "fire event fail " + metaChangeSceneEntity.getChangeSceneKey());
        }
    }

    public void publishChangeSceneCreatedEvent(OpsCloudMetaChangeSceneCreateEvent event) {

        if(event == null || StringUtils.isBlank(event.getId()))
        {
            OpsCloudLoggerManager.log("warn", logger, "OpsCloudMetaChangeSceneCreateEvent is null or id  is null", event);
            return;
        }
        MetaChangeSceneEntity metaChangeSceneEntity =  metaChangeSceneRepository.getChangeSceneDetailsById(event.getId());
        publishChangeSceneCreatedEvent(metaChangeSceneEntity);

    }

    /**
     * Trigger send event.
     *
     * @param metaChangeSceneEO the meta change scene eo
     */
    public void triggerSendEvent(MetaChangeSceneEO metaChangeSceneEO) {
        OpsCloudLoggerManager.log("info", logger, "start fire event", metaChangeSceneEO);
        OpsCloudEventWrapper wrapper = new OpsCloudEventWrapper();
        wrapper.setEventType(OpsCloudEventTypeEnum.CHANGE_SCENE_CREATE.getType());
        OpsCloudChangeSceneCreateEvent opsCloudChangeSceneCreateEvent = new OpsCloudChangeSceneCreateEvent();
        opsCloudChangeSceneCreateEvent.setData(metaChangeSceneEO);
        wrapper.setEventJson(JSONUtil.toJSONString(opsCloudChangeSceneCreateEvent, false));
        opsCloudEventFacade.onEvent(wrapper);
        OpsCloudLoggerManager.log("info", logger, "fire event finish", metaChangeSceneEO);

    }

    /**
     * Filter event boolean.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @return the boolean
     */
    public boolean filterEvent(MetaChangeSceneEntity metaChangeSceneEntity) {
        if (CollectionUtils.isEmpty(metaChangeSceneEntity.getTags())) {
            return false;
        }
        List<String> userDefineTags = metaChangeSceneEntity.getTags().get(OpsCloudConstant.OPSCLOUD_USER_DEFINE_TAG_KEY);
        if (CollectionUtils.isEmpty(userDefineTags)) {
            return false;
        }

        return userDefineTags.contains(OpsCloudConstant.LANJIN_CHANGESCENE_KEY);
    }


    /**
     * 当前只返回用户自定义的标签
     */
    private MetaChangeSceneEO convert(MetaChangeSceneEntity metaChangeSceneEntity) {
        MetaChangeSceneEO metaChangeSceneEO = MetaChangeSceneEventConverter.INSTANCE.covertToEO(metaChangeSceneEntity);
        Map<String, List<String>> userTags = new HashMap<>();
        userTags.put(OpsCloudConstant.OPSCLOUD_USER_DEFINE_TAG_KEY,
                metaChangeSceneEO.getTags().get(OpsCloudConstant.OPSCLOUD_USER_DEFINE_TAG_KEY));
        metaChangeSceneEO.setTags(userTags);
        return metaChangeSceneEO;
    }


}