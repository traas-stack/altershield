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
package com.alipay.altershield.framework.core.change.config;

import com.alipay.altershield.framework.core.change.facade.enums.ChangeCureActionEnum;
import com.alipay.altershield.framework.core.change.facade.enums.ChangeCureStrategyEnum;
import lombok.Data;

/**
 * @author yuefan.wyf
 * @version : ChangeCureActionConfig.java, v 0.1 2020年09月02日 4:38 下午 yuefan.wyf Exp $
 */
@Data
public class ChangeCureActionConfig {

    /**
     * 止血恢复动作
     */
    private ChangeCureActionEnum changeCureAction;

    /**
     * 止血恢复策略
     */
    private ChangeCureStrategyEnum changeCureStrategy;

    /**
     * 止血恢复链接，当changeCureStrategy = MANUAL_TO_URL 生效
     */
    private String cureUrl;

    /**
     * 止血参数转换的配置
     */
    private ChangeCureParamConvertConfig paramConvertConfig;

}