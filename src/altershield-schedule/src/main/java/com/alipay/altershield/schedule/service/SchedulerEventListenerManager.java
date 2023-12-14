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

import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventListener;
import com.alipay.altershield.schedule.service.impl.SchedulerEventListenerManagerImpl;

import java.util.List;

/**
 * @author jinyalong
 * @version : SchedulerEventListenerManager.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public interface SchedulerEventListenerManager {

    /**
     * 根据 eventType获取所有注册的listener数量
     * @param eventType
     * @return
     */
    int getListenerCount(String eventType);

    /**
     * 根据 eventType获取所有注册的listener实例
     * @param eventType
     * @return
     */
    List<SchedulerEventListenerManagerImpl.SchedulerPointEventListenerInstance> getListenerInstances(String eventType);

    /**
     * 根据eventType和listener名字获取listener
     * @param eventType
     * @param listenerName
     * @return
     */
    AlterShieldSchedulerEventListener getListener(String eventType, String listenerName);

}