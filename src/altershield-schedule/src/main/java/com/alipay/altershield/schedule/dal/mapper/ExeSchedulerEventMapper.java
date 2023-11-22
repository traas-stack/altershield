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
package com.alipay.altershield.schedule.dal.mapper;

import com.alipay.altershield.schedule.dal.dataobject.ExeSchedulerEventDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author jinyalong
 * @version : ExeSchedulerEventMapper.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
@Mapper
public interface ExeSchedulerEventMapper {
    /**
     * 根据主键删除
     * @param eventId
     * @return
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String eventId);

    /**
     * 插入单条记录
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int insert(ExeSchedulerEventDO record);

    /**
     * 根据主键查询
     * @param eventId
     * @return
     *
     * @mbg.generated
     */
    ExeSchedulerEventDO selectByPrimaryKey(String eventId);

    /**
     * 根据主键更新，不更新长文本字段
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ExeSchedulerEventDO record);

    /**
     * 查询id
     * @param uid
     * @param status
     * @param before
     * @param group
     * @param priority
     * @param size
     * @return
     */
    List<String> queryTaskIds(String uid, String status, Date before, String group, Integer priority, int size);

    /**
     * 根据point锁住任务
     * @param eventId
     * @return
     */
    ExeSchedulerEventDO lockById(String eventId);

    /**
     * 根据point尝试使用nowait的锁，如果底层不支持，则使用wait的锁
     * @param eventId
     * @return
     */
    ExeSchedulerEventDO lockByIdNowait(String eventId);
}