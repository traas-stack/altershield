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
package com.alipay.altershiled.schedule.model;

import com.alipay.altershiled.schedule.enums.SchedulePointStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 一个事件的处理周期
 * eventStart -> (create-> executeStart -> executeEnd）+一次或多次，只会记录最后的一次时间  -> eventEnd
 *
 * @author jinyalong
 * @version : SchedulerEventEntity.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
@Getter
@Setter
public class SchedulerEventEntity {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String eventId;

    /**
     * Database Column Remarks:
     *   创建时间，整个时间第一次创建的时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;
    /**
     *
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * Database Column Remarks:
     *   任务分组
     *
     *
     * @mbg.generated
     */
    private String eventGroup;

    /**
     * Database Column Remarks:
     *   事件类型，用于序列化事件对象
     *
     *
     * @mbg.generated
     */
    private String eventType;

    /**
     * Database Column Remarks:
     *   附加信息
     *
     *
     * @mbg.generated
     */
    private String info;

    /**
     * Database Column Remarks:
     *   重试次数
     *
     *
     * @mbg.generated
     */
    private int retried;

    /**
     * Database Column Remarks:
     *   状态
     *
     *
     * @mbg.generated
     */
    private SchedulePointStatusEnum status;

    /**
     * Database Column Remarks:
     *   执行结果
     *
     *
     * @mbg.generated
     */
    private String rstMsg;

    /**
     * Database Column Remarks:
     *   计划执行时间
     *
     *
     * @mbg.generated
     */
    private Date gmtPlan;
    /**
     * 事件开始时间，这儿指的是事件源的时间
     */
    private Date gmtEventStart;

    /**
     * Database Column Remarks:
     *   实际执行时间开始时间
     *   只记录最近一次
     *
     *
     * @mbg.generated
     */
    private Date gmtExecuteStart;

    /**
     * 事件执行完成的时间
     */
    private Date gmtExecuteEnd;

    /**
     * 事件结束时间
     */
    private Date gmtEventEnd;

    /**
     * Database Column Remarks:
     *  源id，根据id可以解释事件产生的源
     *
     * @mbg.generated
     */
    private String sourceId;

    /**
     * Database Column Remarks:
     *   对应事件处理的listener的类型
     *
     * @mbg.generated
     */
    private String listenerType;

    /**
     * 任务优先级
     */
    private int priority = 0;

    /**
     * 最近一次捞取耗时
     * @return
     */
    public long fetchCost()
    {
        return CommonUtil.cost(gmtCreate, gmtExecuteStart);
    }

    /**
     * 执行耗时
     * @return
     */
    public long executeCost()
    {
        return CommonUtil.cost(gmtExecuteStart, gmtExecuteEnd);
    }

    /**
     * 被消费总共耗时
     * @return
     */
    public long consumerCost()
    {
        return CommonUtil.cost(gmtEventStart, gmtEventEnd);
    }

    @Override
    public String toString() {
        return "SchdPointEntity{" +
                "pointId='" + eventId + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", uid='" + uid + '\'' +
                ", group='" + eventGroup + '\'' +
                ", eventCode='" + eventType + '\'' +
                ", retried=" + retried +
                ", status='" + status + '\'' +
                ", rstMsg='" + rstMsg + '\'' +
                ", gmtPlan=" + gmtPlan +
                ", gmtEventStart=" + gmtEventStart +
                ", gmtExecuteStart=" + gmtExecuteStart +
                ", gmtExecuteEnd=" + gmtExecuteEnd +
                ", gmtEventEnd=" + gmtEventEnd +
                ", sourceId='" + sourceId + '\'' +
                ", listenerType='" + listenerType + '\'' +
                ", priority=" + priority +
                ", fetchCost=" + fetchCost() +
                ", executeCost=" + executeCost() +
                ", consumerCost=" + consumerCost() +
                '}';
    }
}