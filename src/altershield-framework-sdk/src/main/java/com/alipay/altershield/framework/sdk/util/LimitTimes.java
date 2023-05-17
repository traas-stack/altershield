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
package com.alipay.altershield.framework.sdk.util;

import org.slf4j.Logger;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author liushengdong
 * @version $Id: LimitTimes.java, v 0.1 2017年09月30日 下午5:13 liushengdong Exp $
 */
public class LimitTimes {

    private static final Logger logger = ClientLoggers.LOGGER;;

    private static final int INTERVAL = 1000;

    /** 当前秒,使用AtomicLong的原因是初始化的必须使用compareAndSet来保证起始currSecond的唯一性 */
    private volatile AtomicLong currSecondTimestamp = new AtomicLong(0);

    /** 每秒对应的计数器 */
    private final ConcurrentHashMap<Long, AtomicLong> time2CounterMap =
            new ConcurrentHashMap<Long, AtomicLong>();

    /** 每秒对应的最大次数 */
    private volatile long maxCountPerSecond;

    /**
     * @param maxCountPerSecond
     */
    public LimitTimes(long maxCountPerSecond) {
        this.maxCountPerSecond = maxCountPerSecond;
    }

    /**
     * 修改 每秒对应的最大次数
     *
     * @param maxCountPerSecond value to be assigned to property maxCountPerSecond
     */
    public void setMaxCountPerSecond(long maxCountPerSecond) {
        this.maxCountPerSecond = maxCountPerSecond;
    }

    /**
     *
     * @return true:通过 false:限流
     */
    public boolean addCountAndCheckIsPass() {
        long currentTime = System.currentTimeMillis();
        addNewCounterIfNeed(currentTime);

        long currSecond = currSecondTimestamp.get();

        // 上一行代码,到这一行代码之间,刚好是跨秒的时间点.导致下面这行的counter=null
        AtomicLong counter = time2CounterMap.get(currSecond);

        // 可能currSecond对应的计数器被删除了
        if (counter == null) {
            long nextSecond = currSecond + INTERVAL;
            // 这里的counter为null,除非是第一个time2CounterMap.get到这个time2CounterMap.get之间间隔超过1s
            counter = time2CounterMap.get(nextSecond);
            // 除非从获取currSecond到执行timeCountMap.get中间等待了很长的时间,不然这个地方不会返回null
            if (counter == null) {
                String msg =
                        String.format("limiter can't get count.currSecond:%s ,time2CounterMap:%s",
                                currSecond, time2CounterMap);
                logger.warn(msg);
                return true;
            }
        }

        // 这里有个极端情况,就是这个counter不是最新的counter(例如:由于GC过了很久才执行这行代码).暂时不考虑这种情况
        long c = counter.incrementAndGet();

        return c <= maxCountPerSecond;
    }

    /**
     * 当进入下一秒或者第一次进入,需要初始化计数器
     *
     * @param currentTime 当前时间
     */
    private void addNewCounterIfNeed(long currentTime) {
        for (int i = 0; i < 3; i++) {
            long currSecond = currSecondTimestamp.get();
            if (currSecond == 0) {
                boolean isInit = currSecondTimestamp.compareAndSet(0, currentTime);
                if (isInit) {
                    time2CounterMap.put(currentTime, new AtomicLong(0));
                }
            } else if (currentTime - currSecond >= INTERVAL) {

                // 如果中间间隔很久没有流量导致currSecond一直没有更新,需要一次性跳跃足够多的步长来恢复
                long step = (currentTime - currSecond) / INTERVAL;
                long nextSecond = currSecond + INTERVAL * step;

                // 删除当前计数器,提前put下一秒的值进去
                time2CounterMap.remove(currSecond);
                time2CounterMap.putIfAbsent(nextSecond, new AtomicLong(0));


                // 如果有并发问题,也会删除多余计数器.以防万一,如果代码不出bug应该size == 1
                if (time2CounterMap.size() > 2) {
                    logger.warn("limiter time2CounterMap.size(" + time2CounterMap.size() + ") >2");

                    Iterator<Entry<Long, AtomicLong>> iter = time2CounterMap.entrySet().iterator();
                    if (iter.hasNext()) {
                        Entry<Long, AtomicLong> e = iter.next();
                        if (e.getKey() < currSecond) {
                            iter.remove();
                        }
                    }
                }

                boolean isSucc = currSecondTimestamp.compareAndSet(currSecond, nextSecond);

                if (isSucc) {
                    return;
                }

            }
        }
    }

    public long getCurrSecond() {
        return currSecondTimestamp.get();
    }

    @Override
    public String toString() {
        return "LimitTimes{" + "maxCountPerSecond=" + maxCountPerSecond + '}';
    }
}
