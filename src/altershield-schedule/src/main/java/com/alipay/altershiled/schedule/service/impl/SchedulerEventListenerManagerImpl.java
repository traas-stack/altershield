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
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;
import com.alipay.altershield.shared.schedule.event.listener.AlterShieldSchedulerEventListener;
import com.alipay.altershiled.schedule.service.SchedulerEventListenerManager;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * schedulerPointEventListener管理器 负责管理listener的获取和注册
 *
 * @author yuanji
 * @version : SchedulerPointEventListenerManagerImpl.java, v 0.1 2022年08月15日 14:28 yuanji Exp $
 */
@Service
public class SchedulerEventListenerManagerImpl implements SchedulerEventListenerManager, ApplicationContextAware {

    private              Map<String, List<SchedulerPointEventListenerInstance>> eventListenerMap = new HashMap<>();
    private static final Logger                                                 logger           = Loggers.DEFAULT;

    @Override
    public int getListenerCount(String eventType) {
        List<SchedulerPointEventListenerInstance> listeners = getListenerInstances(eventType);
        return CollectionUtils.isEmpty(listeners) ? 0 : listeners.size();
    }

    @Override
    public List<SchedulerPointEventListenerInstance> getListenerInstances(String eventType) {
        return eventListenerMap.get(eventType);
    }

    @Override
    public AlterShieldSchedulerEventListener getListener(String eventType, String name) {
        List<SchedulerPointEventListenerInstance> listeners = getListenerInstances(eventType);
        if (listeners.isEmpty()) {
            return null;
        }
        for (SchedulerPointEventListenerInstance instance : listeners) {
            if (instance.name.equals(name)) {
                return instance.getListener();
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AlterShieldSchedulerEventListener> listenerMap = applicationContext.getBeansOfType(AlterShieldSchedulerEventListener.class);
        register(listenerMap);
    }

    /**
     * Register.
     *
     * @param listenerMap the listener map
     */
    public synchronized void register(Map<String, AlterShieldSchedulerEventListener> listenerMap) {
        eventListenerMap = resolveListener(listenerMap);
    }

    /**
     * 解出来 事件和listener的关系
     *
     * @param listenerMap the listener map
     * @return map
     */
    public static Map<String, List<SchedulerPointEventListenerInstance>> resolveListener(
            Map<String, AlterShieldSchedulerEventListener> listenerMap) {
        if (CollectionUtils.isEmpty(listenerMap)) {
            return Collections.emptyMap();
        }
        AlterShieldLoggerManager.log("info", logger, "scheduler.listener.init start");
        Map<String, List<SchedulerPointEventListenerInstance>> eventListenerMap = new HashMap<>();
        for (Map.Entry<String, AlterShieldSchedulerEventListener> entry : listenerMap.entrySet()) {
            ResolvableType resolvableType = ResolvableType.forClass(AlterShieldSchedulerEventListener.class,
                    entry.getValue().getClass());
            if (resolvableType != null) {
                ResolvableType eventType = resolvableType.getGenerics()[0];
                Class clazz = eventType.resolve();
                SchedulerEvent schedulerEvent = (SchedulerEvent) clazz.getAnnotation(SchedulerEvent.class);
                if(schedulerEvent == null)
                {
                    throw new RuntimeException("OpsCloudSchedulerEvent class must be contain SchedulerEvent annotation:" + clazz);
                }
                String evenType = schedulerEvent.value();
                List<SchedulerPointEventListenerInstance> aList = eventListenerMap.get(evenType);
                if (aList == null) {
                    aList = new ArrayList<>();
                    eventListenerMap.put(evenType, aList);
                }
                AlterShieldLoggerManager.log("info", logger, "scheduler.listener load",evenType, entry.getKey(), entry.getValue());
                aList.add(new SchedulerPointEventListenerInstance(entry.getKey(), entry.getValue()));
            }
        }
        AlterShieldLoggerManager.log("info", logger, "scheduler.listener.init finish");
        return eventListenerMap;
    }

    /**
     * The type Scheduler point event listener instance.
     */
    public static class SchedulerPointEventListenerInstance
    {
        /**
         * The Listener.
         */
        AlterShieldSchedulerEventListener listener;

        /**
         * The Name.
         */
        String name;

        /**
         * Instantiates a new Scheduler point event listener instance.
         *
         * @param name     the name
         * @param listener the listener
         */
        public SchedulerPointEventListenerInstance(String name, AlterShieldSchedulerEventListener listener) {
            this.listener = listener;
            this.name = name;
        }

        /**
         * Gets listener.
         *
         * @return the listener
         */
        public AlterShieldSchedulerEventListener getListener() {
            return listener;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }
    }


}