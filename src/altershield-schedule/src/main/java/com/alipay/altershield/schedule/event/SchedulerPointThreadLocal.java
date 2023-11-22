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
package com.alipay.altershield.schedule.event;

import com.alipay.altershield.schedule.model.SchedulerEventEntity;

/**
 * 获取上下文中的调度任务
 * @author jinyalong
 * @version : SchedulerPointThreadLocal.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public class SchedulerPointThreadLocal {

    private  static ThreadLocal<SchedulerEventEntity> schdPointEntityThreadLocal = new ThreadLocal<>();

    /**
     * Set.
     *
     * @param schdPoint the schd point
     */
    public static void set(SchedulerEventEntity schdPoint)
    {
        schdPointEntityThreadLocal.set(schdPoint);
    }

    /**
     * Get scheduler event entity.
     *
     * @return the scheduler event entity
     */
    public static SchedulerEventEntity get()
    {
        return schdPointEntityThreadLocal.get();
    }

    /**
     * Clear.
     */
    public static void clear()
    {
        schdPointEntityThreadLocal.set(null);
    }


}