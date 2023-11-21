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
package com.alipay.altershiled.schedule.repository.impl;


import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershiled.schedule.dal.mapper.ExeSchedulerEventMapper;
import com.alipay.altershiled.schedule.enums.SchedulePointStatusEnum;
import com.alipay.altershiled.schedule.model.SchedulerEventEntity;
import com.alipay.altershiled.schedule.repository.SchedulerEventRepository;
import com.alipay.altershiled.schedule.repository.converter.ExeSchedulerEventEntityConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author jinyalong
 * @version : SchedulerEventRepositoryImpl.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
@Repository
public class SchedulerEventRepositoryImpl implements SchedulerEventRepository {

    @Autowired
    private ExeSchedulerEventMapper exeSchedulerEventMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Value("${altershield.framework.server.scheduler.testUid:#{null}}")
    private String testUid;

    private static final ExeSchedulerEventEntityConverter converter = ExeSchedulerEventEntityConverter.INSTANCE;

    /**
     * Generate scheduler event id string.
     *
     * @param relatedId the related id
     * @return the string
     */
    @Override
    public String generateSchedulerEventId(String relatedId) {

        if (StringUtils.isNotBlank(testUid)) {
            return idGenerator.generateIdByRelatedIdButNewUid(IdBizCodeEnum.OPSCLD_SCHD_EVENT, relatedId, testUid);
        }
        return idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_SCHD_EVENT, relatedId);

    }

    /**
     * Lock by id scheduler event entity.
     *
     * @param id the id
     * @return the scheduler event entity
     */
    @Override
    public SchedulerEventEntity lockById(String id) {

        return converter.toEntity(exeSchedulerEventMapper.lockById(id));
    }

    /**
     * Lock by id nowait scheduler event entity.
     *
     * @param id the id
     * @return the scheduler event entity
     */
    @Override
    public SchedulerEventEntity lockByIdNowait(String id) {

        return converter.toEntity(exeSchedulerEventMapper.lockByIdNowait(id));
    }

    /**
     * Insert.
     *
     * @param point the point
     */
    @Override
    public void insert(SchedulerEventEntity point) {
        if (StringUtils.isBlank(point.getEventGroup())) {
            throw new IllegalStateException("Zone name for schedule point can not be null.");
        }
        exeSchedulerEventMapper.insert(converter.toDataObject(point));
    }

    /**
     * Update.
     *
     * @param eventEntity the event entity
     */
    @Override
    public void update(SchedulerEventEntity eventEntity) {
        exeSchedulerEventMapper.updateByPrimaryKey(converter.toDataObject(eventEntity));
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(String id) {
        exeSchedulerEventMapper.deleteByPrimaryKey(id);
    }

    /**
     * Query todo event id list list.
     *
     * @param uid      the uid
     * @param before   the before
     * @param group    the group
     * @param priority the priority
     * @param size     the size
     * @return the list
     */
    @Override
    public List<String> queryTodoEventIdList(String uid, Date before, String group, Integer priority, int size) {
        Assert.notNull(uid, "uid is null");
        Assert.notNull(before, "before date is null");
        Assert.notNull(group, "group  is null");
        return exeSchedulerEventMapper.queryTaskIds(uid, SchedulePointStatusEnum.TODO.getCode(), before, group, priority, size);
    }

}