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
package com.alipay.altershield.framework.core.change.facade.request;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The type Meta change effective config request.
 *
 * @author yuanji
 * @version : MetaChangeEffectiveConfigRequest.java, v 0.1 2022年04月15日 11:00 上午 yuanji Exp $
 */
public class MetaChangeEffectiveConfigRequest {

    /**
     * 是否支持一键回滚
     */
    private  boolean enableRollback;

    /**
     * 灰度策略
     */
    private String changeGrayModeType;

    /**
     * 灰度环境
     */
    private String changeGrayEnvType;

    /**
     * 变更步骤
     */
    @NotNull
    @Valid
    private CreateMetaChangeProgressRequest changeProgress;

    /**
     * Is enable rollback boolean.
     *
     * @return the boolean
     */
    public boolean isEnableRollback() {
        return enableRollback;
    }

    /**
     * Sets enable rollback.
     *
     * @param enableRollback the enable rollback
     */
    public void setEnableRollback(boolean enableRollback) {
        this.enableRollback = enableRollback;
    }

    /**
     * Gets change gray mode type.
     *
     * @return the change gray mode type
     */
    public String getChangeGrayModeType() {
        return changeGrayModeType;
    }

    /**
     * Sets change gray mode type.
     *
     * @param changeGrayModeType the change gray mode type
     */
    public void setChangeGrayModeType(String changeGrayModeType) {
        this.changeGrayModeType = changeGrayModeType;
    }

    /**
     * Gets change progress.
     *
     * @return the change progress
     */
    public CreateMetaChangeProgressRequest getChangeProgress() {
        return changeProgress;
    }

    /**
     * Sets change progress.
     *
     * @param changeProgress the change progress
     */
    public void setChangeProgress(CreateMetaChangeProgressRequest changeProgress) {
        this.changeProgress = changeProgress;
    }

    public String getChangeGrayEnvType() {
        return changeGrayEnvType;
    }

    public void setChangeGrayEnvType(String changeGrayEnvType) {
        this.changeGrayEnvType = changeGrayEnvType;
    }
}