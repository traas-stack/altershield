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

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author yuanji
 * @version : MetaChangeProgressVO.java, v 0.1 2022年06月28日 11:24 yuanji Exp $
 */
@Data
public class MetaChangeProgressVO {

    /**
     * 是否支持order级别的步骤
     */
    @ApiModelProperty(value = "工单级别防御步骤。如果为null，则代表不支持工单级别防御步骤")
    private MetaChangeStepVO orderChangeStep;

    /**
     * 是否支持分批步骤
     */
    @ApiModelProperty(value = "批次级别防御步骤。如果为null，则代表不支持工单级别防御步骤")
    private MetaChangeStepVO batchChangeStep;

    /**
     * 变更生效前置动作
     */
    @ApiModelProperty(value = "灰度开始前置步骤。如果为null，则代表不支持工单级别防御步骤")
    @Valid
    private MetaChangeStepVO beforeGrayStartStep;
    /**
     * 变更生效结束后置动作
     */
    @ApiModelProperty(value = "灰度完成后置步骤。如果为null，则代表不支持工单级别防御步骤")
    @Valid
    private MetaChangeStepVO afterGrayFinishStep;

    /**
     * 分批内变更动作
     */
    @ApiModelProperty(value = "分批内动作步骤定义。 如果为null，则代表不支持工单级别防御步骤。如果batchType为null，这个也不存在")
    @Valid
    private List<MetaChangeStepVO> grayBatchActionSteps;

}