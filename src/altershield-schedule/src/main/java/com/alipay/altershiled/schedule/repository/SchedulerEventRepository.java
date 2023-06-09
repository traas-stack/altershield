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
package com.alipay.altershiled.schedule.repository;


import com.alipay.altershiled.schedule.model.SchedulerEventEntity;

import java.util.Date;
import java.util.List;

/**
 * 调度点仓库，在内心中灵活的控制使用哪个版本的调度点。
 *
 * @author lex
 * @version $Id: SchdPointRepository.java, v 0.1 2019年10月23日 下午5:17 lex Exp $
 */
public interface SchedulerEventRepository {

    /**
     * 所有Schedule Event Id必须从这里产生
     *
     * @param relatedId
     * @return
     */
    String generateSchedulerEventId(String relatedId);

    /**
     * 根据 Schedule event Id对数据加锁（等待）。
     * @param id
     * @return
     */
    SchedulerEventEntity lockById(String id);

    /**
     * 根据 Schedule Point Id对数据加锁（不等待），如果底层数据不支持，则会使用等待加锁策略。
     * @param id
     * @return
     */
    SchedulerEventEntity lockByIdNowait(String id);

    /**
     * 插入新的调度点。
     *
     * @param eventEntity
     */
    void insert(SchedulerEventEntity eventEntity);

    /**
     * 更新旧的调度点。
     *
     * @param eventEntity
     */
    void update(SchedulerEventEntity eventEntity);

    /**
     * 根据 Schedule Point Id删除数据。
     *
     * @param id
     */
    void delete(String id);

    /**
     * 获取待处理的事件id列表
     * @param uid
     * @param date
     * @param group
     * @param priority
     * @param size
     * @return
     */
    List<String> queryTodoEventIdList(String uid, Date date, String group, Integer priority, int size);

}