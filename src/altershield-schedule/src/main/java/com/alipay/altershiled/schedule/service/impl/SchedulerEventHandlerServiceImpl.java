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

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.thread.ExecutionThreadLocal;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.schedule.enums.AlterShieldScheduleEventResultStatus;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventContext;
import com.alipay.altershield.shared.schedule.event.result.AlterShieldSchedulerEventExecuteResult;
import com.alipay.altershiled.schedule.enums.SchedulePointStatusEnum;
import com.alipay.altershiled.schedule.enums.SchedulerEventHandleStatus;
import com.alipay.altershiled.schedule.event.AlterShieldSchedulerEventHolder;
import com.alipay.altershiled.schedule.event.SchedulerPointThreadLocal;
import com.alipay.altershiled.schedule.exception.SchedulerEventDispatcherException;
import com.alipay.altershiled.schedule.model.ScheduleDispatchContext;
import com.alipay.altershiled.schedule.model.SchedulerEventEntity;
import com.alipay.altershiled.schedule.repository.SchedulerEventRepository;
import com.alipay.altershiled.schedule.service.SchedulerEventHandlerService;
import com.alipay.altershiled.schedule.service.SchedulerEventListenerManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jinyalong
 * @version : SchedulerEventHandlerServiceImpl.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
@Service
public class SchedulerEventHandlerServiceImpl implements SchedulerEventHandlerService {

    private static final Logger logger = Loggers.SCHEDULE_POINT_DIGEST;

    private static final Logger SPLITE_LOGGER = Loggers.SCHEDULE_POINT;

    @Autowired
    private SchedulerEventListenerManager opsCloudSchedulerEventListenerManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SchedulerEventRepository schedulePointRepository;

    /**
     *
     */
    @Override
    public SchedulerEventHandleStatus executePoint(final ScheduleDispatchContext scheduleDispatchContext) {
        long start = System.currentTimeMillis();
        AlterShieldLoggerManager.log("info", logger, "schedulerEvent.exe.start", scheduleDispatchContext.getEventId());
        SchedulerEventHandleStatus result = null;
        try {
            result = internalExecutePoint(scheduleDispatchContext);
            return result;
        } finally {
            long during = System.currentTimeMillis() - start;
            // TODO NA优化
            final String pointType = ExecutionThreadLocal.getSchedulePointType();
            AlterShieldLoggerManager.log("info", logger, scheduleDispatchContext.getEventId(),
                    pointType == null ? "NA" : pointType, during + "ms", result);
            SchedulerPointThreadLocal.clear();
        }
    }

    /**
     * 真实执行SchedulePoint同时返回用于打印日志的消息。
     *
     * @param scheduleDispatchContext the schedule dispatch context
     * @return ops cloud scheduler event handle status
     */
    public SchedulerEventHandleStatus internalExecutePoint(final ScheduleDispatchContext scheduleDispatchContext) {

        try {
            return transactionTemplate.execute(
                    status -> handlePoint(scheduleDispatchContext));
        } catch (CannotAcquireLockException lockE) {
            AlterShieldLoggerManager.log("warn", logger, "schedulerEvent.warn", "doNothing.NoLock", scheduleDispatchContext.getEventId());
            return SchedulerEventHandleStatus.CONFLICT;
        }
    }

    private SchedulerEventHandleStatus handlePoint(ScheduleDispatchContext scheduleDispatchContext) {
        //如果获取任务失败，说明这个任务被其它机器提前处理了，需要通知主线程不再处理
        SchedulerEventEntity eventEntity = fetchPointEntity(scheduleDispatchContext);

        try {
            AlterShieldSchedulerEventExecuteResult executeResult = handPoint(eventEntity);
            SchedulerEventHandleStatus opsCloudSchedulerEventHandleStatus = convertStatus(
                    executeResult);
            opsCloudSchedulerEventHandleStatus.getHandler().handle(executeResult.getMsg(), opsCloudSchedulerEventHandleStatus, eventEntity,
                    schedulePointRepository);
            return opsCloudSchedulerEventHandleStatus;
        } catch (SchedulerEventDispatcherException exception) {
            AlterShieldLoggerManager.log("error", logger, exception, "schedulePoint.handle fail", scheduleDispatchContext.getEventId());
            exception.getStatus().getHandler().handle(exception.getMessage(), exception.getStatus(), eventEntity, schedulePointRepository);
            return exception.getStatus();
        } catch (Throwable e) {
            logger.error("schedulePoint.handle fail, unknown exception" + scheduleDispatchContext.getEventId(), e);
            AlterShieldLoggerManager.log("error", logger, e, "schedulePoint.handle fail, unknown exception", scheduleDispatchContext.getEventId());
            SchedulerEventHandleStatus.FAIL.getHandler().handle(e.getMessage(), SchedulerEventHandleStatus.FAIL, eventEntity,
                    schedulePointRepository);
            return SchedulerEventHandleStatus.FAIL;
        }
    }

    private SchedulerEventHandleStatus convertStatus(AlterShieldSchedulerEventExecuteResult executeResult) {
        SchedulerEventHandleStatus opsCloudSchedulerEventHandleStatus;
        switch (executeResult.getStatus())
        {
            case SUCCESS:
                opsCloudSchedulerEventHandleStatus = SchedulerEventHandleStatus.SUCCESS;
                break;
            case ABANDON:
                opsCloudSchedulerEventHandleStatus = SchedulerEventHandleStatus.ABANDON;
                break;
            default:
                opsCloudSchedulerEventHandleStatus = SchedulerEventHandleStatus.FAIL;
        }
        return opsCloudSchedulerEventHandleStatus;
    }

    private AlterShieldSchedulerEventExecuteResult handPoint(SchedulerEventEntity eventEntity) throws SchedulerEventDispatcherException {
        logger.info("SchedulerEventHandlerServiceImpl$handPoint{}", eventEntity);
        if (eventEntity.getStatus() != SchedulePointStatusEnum.TODO) {
            throw new SchedulerEventDispatcherException("point status is changed", SchedulerEventHandleStatus.STATUS_ERROR);
        }
        //放入上下文中
        SchedulerPointThreadLocal.set(eventEntity);
        Class<? extends AlterShieldSchedulerEvent> scheduleEventClass = getEventType(eventEntity.getEventType());
        List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> listeners = getSchedulePointEventListeners(eventEntity.getEventType());
        SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance listener = getHandleEventListener(eventEntity, listeners);
        try
        {
            eventEntity.setGmtExecuteStart(new Date());
            // 如果事件绑定了listener
            if (listener != null) {
                return fireEvent(listener, eventEntity, scheduleEventClass);
            }
            return splitEvent(listeners, eventEntity);
        }
        finally {
            eventEntity.setGmtExecuteEnd(new Date());
        }
    }

    private SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance getHandleEventListener(SchedulerEventEntity eventEntity, List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> listeners) {
        SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance listener = null;
        if (listeners.size() == 1) {
            listener = listeners.get(0);
            return listener;
        }

        if (StringUtils.isNotBlank(eventEntity.getListenerType())) {
            listener = getListenersByTargetType(listeners, eventEntity.getListenerType());
            if (listener == null) {
                String msg = String.format("none handle event listener, pointId:%s, targetType:%s", eventEntity.getEventId(),
                        eventEntity.getListenerType());
                throw new SchedulerEventDispatcherException(msg, SchedulerEventHandleStatus.LISTENER_NOT_FOUND);
            }
        }
        return listener;
    }

    private List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> getSchedulePointEventListeners(String eventType) {
        List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> listeners = opsCloudSchedulerEventListenerManager.getListenerInstances(eventType);
        if (CollectionUtils.isEmpty(listeners)) {
            throw new SchedulerEventDispatcherException("none listeners for event:" + eventType,
                    SchedulerEventHandleStatus.LISTENER_NOT_FOUND);
        }
        return listeners;
    }

    private SchedulerEventEntity fetchPointEntity(ScheduleDispatchContext scheduleDispatchContext) {
        logger.info("SchedulerEventHandlerServiceImpl$fetchPointEntity{}", scheduleDispatchContext);
        SchedulerEventEntity eventEntity = schedulePointRepository.lockByIdNowait(scheduleDispatchContext.getEventId());
        if (eventEntity == null) {
            throw new CannotAcquireLockException("cannotAcquireLock");
        }
        eventEntity.setGmtEventStart(new Date());
        return eventEntity;
    }

    private SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance getListenersByTargetType(List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> listeners, String targetType) {
        for (SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance listener : listeners) {
            if (listener.getName().equals(targetType)) {
                return listener;
            }
        }
        return null;
    }

    private AlterShieldSchedulerEventExecuteResult splitEvent(List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> listeners, SchedulerEventEntity originPointEntity) {
        AlterShieldLoggerManager.log("info", SPLITE_LOGGER, "start split event success", originPointEntity);
        for (SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance listenerInstance : listeners) {
            SchedulerEventEntity newSchedulerEventEntity = new SchedulerEventEntity();
            newSchedulerEventEntity.setSourceId(originPointEntity.getEventId());
            newSchedulerEventEntity.setEventType(originPointEntity.getEventType());
            newSchedulerEventEntity.setGmtPlan(originPointEntity.getGmtPlan());
            newSchedulerEventEntity.setInfo(originPointEntity.getInfo());
            newSchedulerEventEntity.setEventGroup(originPointEntity.getEventGroup());
            newSchedulerEventEntity.setStatus(originPointEntity.getStatus());
            newSchedulerEventEntity.setListenerType(listenerInstance.getName());
            newSchedulerEventEntity.setRstMsg("split event");
            newSchedulerEventEntity.setGmtEventStart(originPointEntity.getGmtEventStart());
            String eventId = schedulePointRepository.generateSchedulerEventId(newSchedulerEventEntity.getSourceId());
            newSchedulerEventEntity.setEventId(eventId);

            schedulePointRepository.insert(newSchedulerEventEntity);
            AlterShieldLoggerManager.log("info", SPLITE_LOGGER, "split event success", listenerInstance.getName(), newSchedulerEventEntity);
        }
        return new AlterShieldSchedulerEventExecuteResult("split event success", AlterShieldScheduleEventResultStatus.SUCCESS);
    }

    private AlterShieldSchedulerEventExecuteResult fireEvent(SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance schedulerPointEventListener,
                                                          SchedulerEventEntity eventEntity,
                                                          Class<? extends AlterShieldSchedulerEvent> schedulePointEventClass) {

        AlterShieldSchedulerEvent opscloudSchedulerEvent = parseEvent(eventEntity, schedulePointEventClass);
        AlterShieldLoggerManager.log("info", logger, "schedulerPoint.fireEvent.start", eventEntity.getSourceId(), eventEntity.getEventId(),
                schedulerPointEventListener.getName());
        if(StringUtils.isBlank(eventEntity.getListenerType()))
        {
            eventEntity.setListenerType(schedulerPointEventListener.getName());
        }
        AlterShieldSchedulerEventContext context = createContext(eventEntity);
        AlterShieldSchedulerEventExecuteResult result = schedulerPointEventListener.getListener().onEvent(context, opscloudSchedulerEvent);
        eventEntity.setRstMsg(result.getMsg());
        AlterShieldLoggerManager.log("info", logger, "schedulerPoint.fireEvent.finish", eventEntity, result);
        return result;

    }

    protected AlterShieldSchedulerEventContext createContext(SchedulerEventEntity eventEntity)
    {
        AlterShieldSchedulerEventContext context = new AlterShieldSchedulerEventContext();
        context.setEventId(eventEntity.getEventId());
        context.setSourceId(eventEntity.getSourceId());
        return  context;
    }

    private AlterShieldSchedulerEvent parseEvent(SchedulerEventEntity schedulerEvent,
                                              Class<? extends AlterShieldSchedulerEvent> schedulePointEventClass) {
        AlterShieldLoggerManager.log("info", logger,
                "schedulerPoint.parseEvent.start, eventType={}" + schedulePointEventClass.getName() + "\n" + schedulerEvent.getInfo());
        AlterShieldSchedulerEvent opscloudSchedulerEvent = null;
        try {
            opscloudSchedulerEvent = JSONUtil.parseJSONToObj(schedulerEvent.getInfo(), schedulePointEventClass);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", logger, e,
                    "schedulerPoint.parseEvent.fail, class" + schedulePointEventClass.getName() + "\n" + schedulerEvent.getInfo());
        }
        if (opscloudSchedulerEvent == null) {
            throw new SchedulerEventDispatcherException("schedulerPoint.parseEvent.fail", SchedulerEventHandleStatus.PARSE_EVENT_ERROR);
        }
        return opscloudSchedulerEvent;
    }

    private Class<? extends AlterShieldSchedulerEvent> getEventType(String code) {
        Class<? extends AlterShieldSchedulerEvent> schedulePointEventClass = AlterShieldSchedulerEventHolder.getEventType(code);
        if (schedulePointEventClass == null) {
            throw new SchedulerEventDispatcherException("event type not found:" + code, SchedulerEventHandleStatus.UNKNOWN_EVENT_TYPE);
        }
        return schedulePointEventClass;
    }

}
