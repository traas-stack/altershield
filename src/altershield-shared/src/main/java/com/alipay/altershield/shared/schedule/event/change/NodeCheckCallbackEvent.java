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

import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;
import lombok.Data;

/**
 * The type Ops cloud node check callback event.
 *
 * @author yuanji
 * @version : OpsCloudNodeCheckCallbackEvent.java, v 0.1 2022年10月20日 16:48 yuanji Exp $
 */
@Data
@SchedulerEvent("NodeCheckCallbackEvent")
public class NodeCheckCallbackEvent extends AlterShieldSchedulerEvent {

    /**
     * node id
     */
    private String nodeExeId;

    /**
     * change key
     */
    private String changeKey;

    /**
     * 防御阶段
     */
    private DefenseStageEnum defenseStage;

    /**
     * Instantiates a new Ops cloud node check callback event.
     */
    public NodeCheckCallbackEvent() {
    }

    /**
     * Instantiates a new Ops cloud node check callback event.
     *
     * @param nodeExeId    the node exe id
     * @param changeKey    the change key
     * @param defenseStage the defense stage
     */
    public NodeCheckCallbackEvent(String nodeExeId, String changeKey,
                                  DefenseStageEnum defenseStage) {
        this.nodeExeId = nodeExeId;
        this.changeKey = changeKey;
        this.defenseStage = defenseStage;
    }
}