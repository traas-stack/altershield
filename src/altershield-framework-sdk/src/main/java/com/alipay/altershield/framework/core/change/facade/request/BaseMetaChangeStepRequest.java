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

/**
 * The type Base meta change step request.
 *
 * @author yuanji
 * @version : BaseMetaChangeStepRequest.java, v 0.1 2022年03月17日 4:38 下午 yuanji Exp $
 */
public class BaseMetaChangeStepRequest {

    /**
     * Instantiates a new Base meta change step request.
     *
     * @param enablePreCheck   是否支持前置校验
     * @param preCheckTimeout  前置校验超时时间 单位:ms
     * @param enablePostCheck  是否支持后置校验
     * @param postCheckTimeout 后置校验超时时间 单位:ms
     */
    public BaseMetaChangeStepRequest(boolean enablePreCheck, long preCheckTimeout, boolean enablePostCheck, long postCheckTimeout) {
        this.defenceConfig = new MetaChangeDefenceConfigRequest(enablePreCheck, enablePostCheck, preCheckTimeout, postCheckTimeout);
    }

    /**
     * Instantiates a new Base meta change step request.
     */
    public BaseMetaChangeStepRequest() {
    }

    /**
     * 变更内容类型
     */
    private String changeContentType;

    /**
     * 变更生效载体类型
     */
    private String changeEffectiveTargetType;

    /**
     * 防御配置信息
     */
    private MetaChangeDefenceConfigRequest defenceConfig;

    /**
     * Gets change content type.
     *
     * @return the change content type
     */
    public String getChangeContentType() {
        return changeContentType;
    }

    /**
     * Sets change content type.
     *
     * @param changeContentType the change content type
     */
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    /**
     * Gets change effective target type.
     *
     * @return the change effective target type
     */
    public String getChangeEffectiveTargetType() {
        return changeEffectiveTargetType;
    }

    /**
     * Sets change effective target type.
     *
     * @param changeEffectiveTargetType the change effective target type
     */
    public void setChangeEffectiveTargetType(String changeEffectiveTargetType) {
        this.changeEffectiveTargetType = changeEffectiveTargetType;
    }

    /**
     * Gets defence config.
     *
     * @return the defence config
     */
    public MetaChangeDefenceConfigRequest getDefenceConfig() {
        return defenceConfig;
    }

    /**
     * Sets defence config.
     *
     * @param defenceConfig the defence config
     */
    public void setDefenceConfig(MetaChangeDefenceConfigRequest defenceConfig) {
        this.defenceConfig = defenceConfig;
    }


}