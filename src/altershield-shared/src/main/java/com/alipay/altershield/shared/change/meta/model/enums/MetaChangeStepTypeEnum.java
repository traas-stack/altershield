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
package com.alipay.altershield.shared.change.meta.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 变更step的分类，基于位置分为执行单，灰度开始前运作，灰度结束后动作，分批，分批内动作
 * @author yuanji
 * @version : OpsCloudMetaChangeStepEnum.java, v 0.1 2022年03月13日 3:16 下午 yuanji Exp $
 */
public enum MetaChangeStepTypeEnum {
    /**
     * 执行单
     */
    STEP_ORDER("order"),
    /**
     * 灰度开始前动作
     */
    STEP_BEFORE_GRAY_START("before_gray_start_action"),
    /**
     * 灰度开始后动作
     */
    STEP_AFTER_GRAY_FINISH("after_gray_finish_action"),
    /**
     * 分批
     */
    STEP_GRAY_BATCH("gray_batch"),
    /**
     * 分批内动作
     */
    STEP_GRAY_BATCH_ACTION("gray_batch_action"),

    /**
     * 原子动作,可以复用的动作
     */
    STEP_ATOMIC_ACTION("atomic_action");

    private String step;

    MetaChangeStepTypeEnum(String step) {
        this.step = step;
    }

    public String getStep() {
        return step;
    }

    /**
     * 根据step字符串获取对应枚举*
     * @param step step
     * @return 枚举类型
     */
    public static MetaChangeStepTypeEnum getByStep(String step){
        if (StringUtils.isBlank(step)) {
            return null;
        }

        for (MetaChangeStepTypeEnum value : MetaChangeStepTypeEnum.values()) {
            if (value.getStep().equalsIgnoreCase(step)) {
                return value;
            }
        }

        return null;
    }

}