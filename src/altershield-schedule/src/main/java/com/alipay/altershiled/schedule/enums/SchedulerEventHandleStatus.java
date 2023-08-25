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
package com.alipay.altershiled.schedule.enums;

import com.alipay.altershiled.schedule.service.SchedulerEventResultHandler;

/**
 * 调度状态
 *
 * @author jinyalong
 * @version : SchedulerEventHandleStatus.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public enum SchedulerEventHandleStatus {

    /**
     * 调度成功
     */
    SUCCESS(SchedulerEventResultHandler.DELETE_SCHEDULER_EVENT_HANDLER),

    /**
     * 业务放弃
     */
    ABANDON(SchedulerEventResultHandler.DELETE_SCHEDULER_EVENT_HANDLER, SchedulePointStatusEnum.ABANDON),

    /**
     * 调度冲突
     */
    CONFLICT(SchedulerEventResultHandler.NONE_HANDLER),

    /**
     * 未知事件类型
     * 有可能废弃了
     * 有可能是在发布期间的新的事件
     */
    UNKNOWN_EVENT_TYPE(SchedulerEventResultHandler.RETRY_SCHEDULER_EVENT_STATUS_HANDLER),

    /**
     * 未找到事件监听器
     * 有可能废弃了
     * 有可能是在发布期间的新的事件
     */
    LISTENER_NOT_FOUND(SchedulerEventResultHandler.RETRY_SCHEDULER_EVENT_STATUS_HANDLER),

    /**
     * 解析事件错误
     * 有点复杂，有可能是老版本问题导致的。
     * 这种需要业务兼容，否则都直接删除
     */
    PARSE_EVENT_ERROR(SchedulerEventResultHandler.DELETE_SCHEDULER_EVENT_HANDLER, SchedulePointStatusEnum.ABANDON),

    /**
     * 状态错误
     */
    STATUS_ERROR(SchedulerEventResultHandler.NONE_HANDLER),

    /**
     * 调度失败，任何原因
     */
    FAIL(SchedulerEventResultHandler.RETRY_SCHEDULER_EVENT_STATUS_HANDLER);

    /**
     * 是否删除
     */
    private SchedulerEventResultHandler handler;

    /**
     * 变更point状态，如果为空，则不变化
     */
    private SchedulePointStatusEnum     changeStatus;

    SchedulerEventHandleStatus(SchedulerEventResultHandler handler) {
        this.handler = handler;
    }

    SchedulerEventHandleStatus(SchedulerEventResultHandler handler,
                               SchedulePointStatusEnum changeStatus) {
        this.handler = handler;
        this.changeStatus = changeStatus;
    }

    /**
     * Gets get change status.
     *
     * @return the get change status
     */
    public SchedulePointStatusEnum getChangeStatus() {
        return changeStatus;
    }

    /**
     * Gets get handler.
     *
     * @return the get handler
     */
    public SchedulerEventResultHandler getHandler() {
        return handler;
    }
}