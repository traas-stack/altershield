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
import com.alipay.altershield.common.service.AlterShieldGroupService;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershiled.schedule.enums.SchedulerEventHandleStatus;
import com.alipay.altershiled.schedule.model.ScheduleDispatchContext;
import com.alipay.altershiled.schedule.repository.SchedulerEventRepository;
import com.alipay.altershiled.schedule.service.SchedulerEventDispatcher;
import com.alipay.altershiled.schedule.service.SchedulerEventHandlerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jinyalong
 * @version : SchedulerEventDispatcherImpl.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
@Component
public class SchedulerEventDispatcherImpl implements SchedulerEventDispatcher {

    /**
     * The Scheduler point handler service.
     */
    @Autowired
    protected SchedulerEventHandlerService schedulerPointHandlerService;

    @Autowired
    @Qualifier("schedulePointThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor schedulePointThreadPoolTaskExecutor;

    @Autowired
    private SchedulerEventRepository schedulePointRepository;

    @Autowired
    private AlterShieldGroupService alterShieldGroupService;

    private static final Logger logger = Loggers.SCHEDULE_POINT;

    @Override
    public int dispatchTask(String uid) {
        List<String> pointIdsList = retrieveEvents(uid);
        if (CollectionUtils.isEmpty(pointIdsList)) {
            AlterShieldLoggerManager.log("info", logger, "none task found", uid);
            return 0;
        }
        return dispatchTask(pointIdsList);
    }

    @Override
    public int dispatchTask(List<String> eventIdList) {
        // 要统计消费了多少个任务
        // 如果任务处于满的状态，则阻塞等待执行
        int totalEventCount = eventIdList.size();
        int handledCount = 0;
        List<Future<DispatchResult>> resultList = new ArrayList<>(totalEventCount);
        Iterator<String> iterator = eventIdList.iterator();
        try {
            while (!Thread.currentThread().isInterrupted() && iterator.hasNext()) {
                String id = iterator.next();
                ScheduleDispatchContext context = new ScheduleDispatchContext(id, null);
                resultList.add(doDispatch(context));
                handledCount++;
                try {
                    if (checkConflictStatus(resultList)) {
                        break;
                    }
                } catch (InterruptedException e) {
                    AlterShieldLoggerManager.log("error", logger, e, "task quit");
                    break;
                }
            }
        } finally {
            resultList.clear();
        }
        return handledCount;
    }

    private Future<DispatchResult> doDispatch(ScheduleDispatchContext context) {

        return schedulePointThreadPoolTaskExecutor.submit(() -> {
            long start = System.currentTimeMillis();
            AlterShieldLoggerManager.log("info", logger,
                    schedulePointThreadPoolTaskExecutor.getThreadNamePrefix() + ".started",
                    "queued " + (System.currentTimeMillis() - start) + " ms");
            SchedulerEventHandleStatus result = schedulerPointHandlerService.executePoint(context);
            long cost = System.currentTimeMillis() - start;
            AlterShieldLoggerManager.log("info", logger, schedulePointThreadPoolTaskExecutor.getThreadNamePrefix() + ".finish",
                    "cost " + cost + " ms");
            return new DispatchResult(context.getEventId(), result);
        });

    }

    private boolean checkConflictStatus(List<Future<DispatchResult>> resultList) throws InterruptedException {
        for (Future<DispatchResult> future : resultList) {
            if (future.isDone()) {

                DispatchResult dispatchResult = null;
                try {
                    dispatchResult = future.get();
                } catch (ExecutionException e) {
                    // never happen
                    AlterShieldLoggerManager.log("error", logger, e,
                            "task:" + dispatchResult.pointId + " is conflict with other thread");
                    continue;
                }
                if (dispatchResult.status == SchedulerEventHandleStatus.CONFLICT) {
                    AlterShieldLoggerManager.log("warn", logger,
                            "task:" + dispatchResult.pointId + " is conflict with other thread");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The type Dispatch result.
     */
    static class DispatchResult {
        /**
         * The Point id.
         */
        String                             pointId;
        /**
         * The Status.
         */
        SchedulerEventHandleStatus status;

        /**
         * Instantiates a new Dispatch result.
         *
         * @param pointId the point id
         * @param status  the status
         */
        public DispatchResult(String pointId, SchedulerEventHandleStatus status) {
            this.pointId = pointId;
            this.status = status;
        }
    }

    /**
     * Retrieve events list.
     *
     * @param uid the uid
     * @return the list
     */
    protected List<String> retrieveEvents(String uid) {

        //不捞取zoneName为blank的任务
        String group = alterShieldGroupService.getGroup();
        AlterShieldLoggerManager.log("info", logger, "start retrieve task", uid,group);
        List<String> pointIdsList = schedulePointRepository.queryTodoEventIdList(uid, new Date(), group, null, AlterShieldConstant.SCHEDULE_POINT_SINGLE_UID_LOAD_SIZE);
        return pointIdsList;
    }

}
