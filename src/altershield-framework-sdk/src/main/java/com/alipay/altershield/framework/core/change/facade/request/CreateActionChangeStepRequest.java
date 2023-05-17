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

import com.alipay.altershield.framework.common.validate.StringLength;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 创建动作级别的step
 *
 * @author yuanji
 * @version : CreateActionChangeStepRequest.java, v 0.1 2022年06月02日 3:35 下午 yuanji Exp $
 */
public class CreateActionChangeStepRequest extends CreateMetaChangeStepRequest {

    /**
     * Instantiates a new Create action change step request.
     *
     * @param name             变更动作名字
     * @param changeKey        变更动作key，全局唯一
     * @param enablePreCheck   是否支持前置校验
     * @param preCheckTimeout  前置校验超时时间
     * @param enablePostCheck  是否支持后置校验
     * @param postCheckTimeout 后置校验超时时间
     */
    public CreateActionChangeStepRequest(@NotNull String name,
                                         @NotNull @Pattern(
                                                 regexp = "^[a-zA-Z][a-zA-Z0-9\\.\\_]*[a-zA-Z0-9]$",
                                                 message = "场景key只能包含字母，数字，点和下划线。并且只能以字母开头和结尾") String changeKey,
                                         boolean enablePreCheck, long preCheckTimeout, boolean enablePostCheck, long postCheckTimeout
    ) {
        super(enablePreCheck, preCheckTimeout, enablePostCheck, postCheckTimeout);
        this.name = name;
        this.changeKey = changeKey;
    }

    /**
     * Instantiates a new Create action change step request.
     */
    public CreateActionChangeStepRequest() {
        super(true, 10000, true, 10000);
    }

    /**
     * 变更步骤名字
     */
    @StringLength(max = 256)
    @NotNull
    private String name;

    /**
     * 变更key
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9\\.\\_]*[a-zA-Z0-9]$", message = "变更key只能包含字母，数字，点和下划线。并且只能以字母开头和结尾")
    @StringLength(max = 256)
    private String changeKey;

    /**
     * 变更内容类型
     */
    @StringLength(max = 256)
    private String changeContentType;

    /**
     * 变更生效载体类型
     */
    @StringLength(max = 256)
    private String changeEffectiveTargetType;

    /**
     * 变更
     */
    @StringLength(max = 4000)
    private String effectiveTargetType;

    /**
     * Gets change key.
     *
     * @return the change key
     */
    public String getChangeKey() {
        return changeKey;
    }

    /**
     * Sets change key.
     *
     * @param changeKey the change key
     */
    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getChangeContentType() {
        return changeContentType;
    }

    @Override
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    @Override
    public String getChangeEffectiveTargetType() {
        return changeEffectiveTargetType;
    }

    @Override
    public void setChangeEffectiveTargetType(String changeEffectiveTargetType) {
        this.changeEffectiveTargetType = changeEffectiveTargetType;
    }

    public String getEffectiveTargetType() {
        return effectiveTargetType;
    }

    public void setEffectiveTargetType(String effectiveTargetType) {
        this.effectiveTargetType = effectiveTargetType;
    }

    @Override
    public String toString() {
        return "CreateActionChangeStepRequest{" +
                "name='" + name + '\'' +
                ", changeKey='" + changeKey + '\'' +
                ", changeContentType='" + changeContentType + '\'' +
                ", changeEffectiveTargetType='" + changeEffectiveTargetType + '\'' +
                "} " + super.toString();
    }
}