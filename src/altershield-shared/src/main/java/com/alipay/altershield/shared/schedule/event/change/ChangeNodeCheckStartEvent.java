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
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent;
import lombok.Data;

/**
 * 变更核心后置防御事件
 * @author yuanji
 * @version : OpsCloudAsyncPostCheckStartEvent.java, v 0.1 2022年10月18日 19:09 yuanji Exp $
 */
@Data
@SchedulerEvent("ChangeNodeCheckStartEvent")
public class ChangeNodeCheckStartEvent extends AlterShieldSchedulerEvent {

    /**
     * exeNodeId
     */
    private String exeNodeId;

    /**
     * 场景key，可能会空
     */
    private String changeSceneKey;

    /**
     * 变更key，不为空
     */
    private String changeKey;

    /**
     * 代G，可为空
     */
    private MetaChangeSceneGenerationEnum generation;

    /**
     * 变类节点类型
     */
    private MetaChangeStepTypeEnum changeStepType;

    /**
     * 是否应急变更
     */
    private boolean emergency;

    /**
     * 影响面变化
     */
    private boolean influenceChanged = false;

    /**
     * 防御检查的阶段
     */
    private DefenseStageEnum defenseStage;

    public ChangeNodeCheckStartEvent() {
    }

    public ChangeNodeCheckStartEvent(String exeNodeId, String changeSceneKey, String changeKey,
                                     MetaChangeSceneGenerationEnum generation,
                                     MetaChangeStepTypeEnum changeStepType, boolean emergency, boolean influenceChanged, DefenseStageEnum defenseStage) {
        this.exeNodeId = exeNodeId;
        this.changeSceneKey = changeSceneKey;
        this.changeKey = changeKey;
        this.generation = generation;
        this.changeStepType = changeStepType;
        this.emergency = emergency;
        this.influenceChanged = influenceChanged;
        this.defenseStage = defenseStage;
    }
}