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
package com.alipay.altershiled.schedule.service.impl;

import com.alipay.altershield.common.service.AlterShieldGroupService;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
import com.alipay.altershiled.schedule.enums.SchedulePointStatusEnum;
import com.alipay.altershiled.schedule.event.SchedulerPointThreadLocal;
import com.alipay.altershiled.schedule.model.SchedulerEventEntity;
import com.alipay.altershiled.schedule.repository.SchedulerEventRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * The type Scheduler event publisher.
 *
 * @author yuanji
 * @version : SchedulerEventPublisherImpl.java, v 0.1 2022年08月18日 14:12 yuanji Exp $
 */
@Component
public class AlterShieldSchedulerEventPublisherImpl implements AlterShieldSchedulerEventPublisher {

    @Autowired
    private SchedulerEventRepository schedulerEventRepository;
    @Autowired
    private AlterShieldGroupService alterShieldGroupService;

    private static final Logger               logger = Loggers.SCHEDULE_POINT_DIGEST;


    @Override
    public String publish(String sourceId, AlterShieldSchedulerEvent event) {
        Assert.notNull(sourceId, "sourceId is null");
        Assert.notNull(event, "event is null");
        AlterShieldLoggerManager.log("info",logger, "schedulerEvent.publish.start", sourceId,event);
        ResolvableType resolvableType = ResolvableType.forClass(event.getClass());
        if (resolvableType == null) {
            throw new IllegalArgumentException("event object must has SchedulerEvent annotation:" + event.getClass());
        }
        Class clazz = resolvableType.resolve();
        SchedulerEvent schedulerEvent = (SchedulerEvent) clazz.getAnnotation(SchedulerEvent.class);
        if (schedulerEvent == null) {
            throw new IllegalArgumentException("event object must has SchedulerEvent annotation:" + event.getClass());
        }
        String eventId = schedulerEventRepository.generateSchedulerEventId(sourceId);
        SchedulerEventEntity eventEntity = create(eventId, sourceId, event);
        schedulerEventRepository.insert(eventEntity);
        AlterShieldLoggerManager.log("info",logger, "schedulerEvent.publish.success", eventEntity);
        return eventId;
    }

    private SchedulerEventEntity create(String eventId, String sourceId, AlterShieldSchedulerEvent event) {
        SchedulerEventEntity schedulerEventEntity = new SchedulerEventEntity();
        schedulerEventEntity.setStatus(SchedulePointStatusEnum.TODO);
        schedulerEventEntity.setRetried(0);
        schedulerEventEntity.setInfo(JSONUtil.toJSONString(event, false));
        SchedulerEvent schedulerEvent = event.getClass().getAnnotation(SchedulerEvent.class);
        String name = schedulerEvent.value();
        schedulerEventEntity.setEventId(eventId);
        schedulerEventEntity.setEventType(name);
        schedulerEventEntity.setGmtPlan(event.getGmtPlan());
        schedulerEventEntity.setGmtCreate(new Date());
        schedulerEventEntity.setGmtModified(new Date());
        schedulerEventEntity.setSourceId(sourceId);
        schedulerEventEntity.setEventGroup(alterShieldGroupService.getGroup());
        mergeInfo(schedulerEventEntity);
        return schedulerEventEntity;
    }

    /**
     * Merge info.
     *
     * @param current the current
     */
    protected void mergeInfo(SchedulerEventEntity current) {
        SchedulerEventEntity parentEvent = SchedulerPointThreadLocal.get();
        if (parentEvent == null) {
            current.setGmtEventStart(new Date());
        } else {
            current.setGmtEventStart(parentEvent.getGmtEventStart());
        }
    }
}