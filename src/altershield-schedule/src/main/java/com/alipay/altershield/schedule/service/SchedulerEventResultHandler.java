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
package com.alipay.altershield.schedule.service;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.RetryUtil;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.schedule.enums.SchedulerEventHandleStatus;
import com.alipay.altershield.schedule.model.SchedulerEventEntity;
import com.alipay.altershield.schedule.enums.SchedulePointStatusEnum;
import com.alipay.altershield.schedule.repository.SchedulerEventRepository;
import org.slf4j.Logger;

import java.util.Date;

/**
 * @author jinyalong
 * @version : SchedulerEventResultHandler.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public abstract class SchedulerEventResultHandler {

    /**
     * 处理事件结果
     * @param msg
     * @param status
     * @param schedulerEntity
     * @param schedulePointRepository
     */
    abstract public void handle(String msg, SchedulerEventHandleStatus status, SchedulerEventEntity schedulerEntity, SchedulerEventRepository schedulePointRepository);

    private static final Logger logger = Loggers.SCHEDULE_POINT_DIGEST;

    /**
     * 空处理器
     */
    public static final SchedulerEventResultHandler NONE_HANDLER = new SchedulerEventResultHandler() {
        @Override
        public void handle(String msg, SchedulerEventHandleStatus status, SchedulerEventEntity schedulerEntity, SchedulerEventRepository schedulerEventRepository) {

        }
    };

    /**
     * 删除事件处理器
     */
    public static final SchedulerEventResultHandler DELETE_SCHEDULER_EVENT_HANDLER = new SchedulerEventResultHandler() {
        @Override
        public void handle(String msg, SchedulerEventHandleStatus status, SchedulerEventEntity schedulerEntity, SchedulerEventRepository schedulerEventRepository) {
            doDelete(msg, status.getChangeStatus(), schedulerEntity, schedulerEventRepository);
        }
    };

    public static final SchedulerEventResultHandler UPDATE_SCHEDULER_EVENT_STATUS_HANDLER = new SchedulerEventResultHandler() {
        @Override
        public void handle(String msg, SchedulerEventHandleStatus status, SchedulerEventEntity schedulerEntity, SchedulerEventRepository schedulerEventRepository) {
            schedulerEntity.setRstMsg(msg);
            schedulerEntity.setStatus(status.getChangeStatus());
            schedulerEventRepository.update(schedulerEntity);
        }
    };

    public static final SchedulerEventResultHandler RETRY_SCHEDULER_EVENT_STATUS_HANDLER = new SchedulerEventResultHandler() {
        @Override
        public void handle(String msg, SchedulerEventHandleStatus status, SchedulerEventEntity schedulerEntity, SchedulerEventRepository schedulerEventRepository) {
            schedulerEntity.setRstMsg(msg);
            if(schedulerEntity.getRetried() >= AlterShieldConstant.SCHEDULE_POINT_MAX_RETRY)
            {

                doDelete(msg + ". 失败次数达到" + AlterShieldConstant.SCHEDULE_POINT_MAX_RETRY + "次上限，放弃重试.", SchedulePointStatusEnum.ABANDON,
                        schedulerEntity, schedulerEventRepository);
                return;
            }
            long interval = RetryUtil.retryInterval(schedulerEntity.getRetried());
            schedulerEntity.setGmtPlan(new Date(System.currentTimeMillis() + interval));
            schedulerEntity.setRetried(schedulerEntity.getRetried() + 1);
            schedulerEventRepository.update(schedulerEntity);
        }
    };

    protected static final void doDelete(String msg, SchedulePointStatusEnum status, SchedulerEventEntity schedulerEvent, SchedulerEventRepository schedulePointRepository)
    {
        logger.info("schedulerEvent.exe.delete start, eventId:{}, status:{}", schedulerEvent.getEventId(), status);
        schedulerEvent.setRstMsg(msg);
        schedulerEvent.setGmtEventEnd(new Date());
        schedulerEvent.setStatus(status);
        schedulePointRepository.delete(schedulerEvent.getEventId());
        logger.info("schedulerEvent.exe.delete finish, {}", schedulerEvent);
    }

}