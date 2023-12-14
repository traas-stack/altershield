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
package com.alipay.altershield.web.change.meta.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author yuanji
 * @version : MetaChangeEffectiveConfigVO.java, v 0.1 2022年06月28日 11:21 yuanji Exp $
 */
@Data
public class MetaChangeEffectiveConfigVO {

    /**
     * 是否支持一键回滚
     */
    @ApiModelProperty(value = "是否支持回滚")
    private  boolean enableRollback;

    /**
     * 灰度策略
     */
    @ApiModelProperty(value = "灰度策略")
    private String changeGrayModeType;

    /**
     * 灰度环境
     */
    @ApiModelProperty(value = "灰度环境")
    private String changeGrayEnvType;

    /**
     * 变更生效过程
     */
    @ApiModelProperty(value = "灰度策略")
    private MetaChangeProgressVO changeProgress;
}
