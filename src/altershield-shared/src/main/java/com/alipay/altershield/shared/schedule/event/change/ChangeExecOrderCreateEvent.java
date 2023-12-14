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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.schedule.event.change;

import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;
import lombok.Data;

/**
 * 变更执行单创建事件
 *
 * @author yuanji
 * @version : OpsCloudChangeExecOrderCreateEvent.java, v 0.1 2022年08月26日 11:38 yuanji Exp $
 */
@Data
@SchedulerEvent("ChangeExecOrderCreateEvent")
public class ChangeExecOrderCreateEvent extends AlterShieldSchedulerEvent {


    /**
     * 变更场景
     */
    private String                        changeSceneKey;
    /**
     * 变更平台
     */
    private String                        platform;
    /**
     * 场景代G
     */
    private MetaChangeSceneGenerationEnum generation;

    /**
     * 变更工单Id
     */
    private String changeExecOrderId;

}