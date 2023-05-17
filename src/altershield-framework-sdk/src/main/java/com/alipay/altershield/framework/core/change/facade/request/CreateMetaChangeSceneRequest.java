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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The type Create meta change scene request.
 *
 * @author yuanji
 * @version : CreateMetaChangeSceneRequest.java, v 0.1 2022年03月15日 6:11 下午 yuanji Exp $
 */
public class CreateMetaChangeSceneRequest extends BaseMetaChangeSceneRequest {

    /**
     * 变更场景key，在当前云站点唯一标识
     * 场景key只能包含字母，数字，点和下划线。同时只能以字母开头和结尾
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9\\.\\_\\-\\:]*[a-zA-Z0-9]$")
    @StringLength(max = 200)
    private String changeSceneKey;

    /**
     * "变更场景生效配置
     */
    @Valid
    private MetaChangeEffectiveConfigRequest changeEffectiveConfig;

    /**
     * Gets change scene key.
     *
     * @return the change scene key
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     * Sets change scene key.
     *
     * @param changeSceneKey the change scene key
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     * Gets change effective config.
     *
     * @return the change effective config
     */
    public MetaChangeEffectiveConfigRequest getChangeEffectiveConfig() {
        return changeEffectiveConfig;
    }

    /**
     * Sets change effective config.
     *
     * @param changeEffectiveConfig the change effective config
     */
    public void setChangeEffectiveConfig(
            MetaChangeEffectiveConfigRequest changeEffectiveConfig) {
        this.changeEffectiveConfig = changeEffectiveConfig;
    }
}