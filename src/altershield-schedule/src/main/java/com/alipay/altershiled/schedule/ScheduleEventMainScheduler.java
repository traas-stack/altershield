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
package com.alipay.altershiled.schedule;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershiled.schedule.service.SchedulerEventDispatcher;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 分配uid的主线程，单机负责捞取调度事件
 * @author yuanji
 * @version : ScheduleEventMainScheduler.java, v 0.1 2022年03月21日 2:42 下午 yuanji Exp $
 */
@Component
public class ScheduleEventMainScheduler implements ApplicationRunner, InitializingBean, DisposableBean {

    @Value("${opscloud.framework.server.scheduler.maxUid}")
    private int uidMaxSize = 100;

    private static UidToolkit uidToolkit;

    /**
     * 调度专用线程
     */
    private volatile Thread scheduleThread;

    @Autowired
    protected SchedulerEventDispatcher schedulerEventDispatcher;


    /**
     * 判断服务器是否正在关闭。
     */
    private volatile boolean isShutdown = false;

    private static final Logger logger = Loggers.LOCAL_SCHEDULE;

    /**
     * 开启一个新的调度线程。
     */
    synchronized void startScheduleThread() {
        if (scheduleThread != null & scheduleThread.isAlive()) {
            return;
        }
        AlterShieldLoggerManager.log("info", logger, "mainSchedule.start scheduleThread");
        scheduleThread.start();
    }

    private void doSchedulerRun() {
        while (!isShutdown && !Thread.currentThread().isInterrupted()) {
            // 调度开关：默认false，开启调度
            if(!AlterShieldConstant.SWITCH_SCHEDULER_PAUSE)
            {
                dispatchTask(uidToolkit.getNextUid());
            }

        }
    }

    private void dispatchTask(String uid) {
        try {
            AlterShieldLoggerManager.log("debug", logger, "start consumer uid=", uid, " tasks");
            int handCount = schedulerEventDispatcher.dispatchTask(uid);
            AlterShieldLoggerManager.log("debug", logger, "total  " + handCount + " is handle for:" + uid);
        } catch(Throwable e) {
            AlterShieldLoggerManager.log("error", logger, e, uid, "schedulerEventDispatcher.dispatchTask error:" + e.getMessage());
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        startScheduleThread();
    }

    @Override
    public synchronized void destroy() {
        if (!isShutdown) {
            AlterShieldLoggerManager.log("info", logger, "mockScheduler.shutdown");
            isShutdown = true;
            scheduleThread.interrupt();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (scheduleThread == null) {
            uidToolkit = new UidToolkit(uidMaxSize);
            scheduleThread = new Thread(this::doSchedulerRun, "opscloud-schedulerThread");
            AlterShieldLoggerManager.log("info", logger, "mainScheduler.init schedulerThread");
        }
    }
}
