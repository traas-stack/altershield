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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.change.meta.model.effective;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 变更过程实体定义 描述了变更场景一次完整的变更涉及的过程。包括:变更生效开始前置动作(可选) -> 变更生效过程 -> 变更生效结束后置动作(可以)
 *
 * @author yuanji
 * @version : OpsCloudMetaChangeProcessConfigEntity.java, v 0.1 2022年02月10日 11:11 上午 yuanji Exp $
 */
@Setter
@Getter
@ToString
public class MetaChangeProgressEntity {

    /**
     * 变更工单级别
     */
    private MetaChangeOrderStepEntity  orderChangeStep;
    /**
     * 变更生效前置动作
     */
    private MetaChangeActionStepEntity beforeGrayStartStep;
    /**
     * 变更生效结束后置动作
     */
    private MetaChangeActionStepEntity afterGrayFinishStep;
    /**
     * 分批度模型定义
     */
    private MetaChangeBatchStepEntity  batchStep;

    /**
     * Gets all change steps.
     *
     * @return the all change steps
     */
    public List<MetaChangeStepEntity> getAllChangeSteps() {
        List<MetaChangeStepEntity> allChangeSteps = new ArrayList<>();

        addChangeStep(orderChangeStep, allChangeSteps);
        addChangeStep(beforeGrayStartStep, allChangeSteps);
        addChangeStep(afterGrayFinishStep, allChangeSteps);
        addChangeStep(batchStep, allChangeSteps);
        if (batchStep != null && batchStep.getChildChangActionSteps() != null) {
            batchStep.getChildChangActionSteps().forEach(metaChangeActionEntity -> {
                addChangeStep(metaChangeActionEntity, allChangeSteps);
            });
        }
        return allChangeSteps;
    }

    private static void addChangeStep(MetaChangeStepEntity changeStepEntity, List<MetaChangeStepEntity> allChangeSteps) {
        if (changeStepEntity != null) {
            allChangeSteps.add(changeStepEntity);
        }
    }

}