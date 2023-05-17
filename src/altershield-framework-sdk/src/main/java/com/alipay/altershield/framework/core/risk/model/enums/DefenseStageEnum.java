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
package com.alipay.altershield.framework.core.risk.model.enums;

/**
 * 防御阶段
 *
 * @author yuefan.wyf
 * @version $Id: DefenseStageEnum.java, v 0.1 2019年06月15日 下午5:21 yuefan.wyf Exp $
 */
public enum DefenseStageEnum {
    /**
     * 变更前置
     */
    PRE("PRE"),
    /**
     * 变更后置
     */
    POST("POST");

    private final String stage;

    DefenseStageEnum(String stage) {
        this.stage = stage;
    }

    /**
     * @return property value of {@link #stage}
     */
    public String getStage() {
        return stage;
    }

    /**
     * @param stage
     * @return
     */
    public static DefenseStageEnum getByStage(String stage) {
        for (DefenseStageEnum e : DefenseStageEnum.values()) {
            if (e.stage.equalsIgnoreCase(stage)) {
                return e;
            }
        }
        return null;
    }
}
