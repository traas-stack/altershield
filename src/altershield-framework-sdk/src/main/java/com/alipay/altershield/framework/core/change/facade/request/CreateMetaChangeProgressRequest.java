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
import java.util.List;

/**
 * The type Create meta change progress request.
 *
 * @author yuanji
 * @version : CreateMetaChangeProgressRequest.java, v 0.1 2022年04月21日 2:54 下午 yuanji Exp $
 */
public class CreateMetaChangeProgressRequest {

    /**
     * 是否支持order级别的步骤
     */
    @Valid
    private CreateMetaChangeStepRequest orderStep;

    /**
     * 是否支持分批步骤
     */
    @Valid
    private CreateMetaChangeStepRequest batchStep;

    /**
     * 变更生效前置动作
     */
    @Valid
    private CreateActionChangeStepRequest beforeGrayStartStep;
    /**
     * 变更生效结束后置动作
     */
    @Valid
    private CreateActionChangeStepRequest afterGrayFinishStep;

    /**
     * 分批内变更动作,
     * 分批里包含0个或多个变更动作
     * 但所有的分批内的变更动作应该是相同的
     */
    @Valid
    private List<CreateActionChangeStepRequest> grayBatchActionSteps;

    /**
     * Gets order step.
     *
     * @return the order step
     */
    public CreateMetaChangeStepRequest getOrderStep() {
        return orderStep;
    }

    /**
     * Sets order step.
     *
     * @param orderStep the order step
     */
    public void setOrderStep(CreateMetaChangeStepRequest orderStep) {
        this.orderStep = orderStep;
    }

    /**
     * Gets batch step.
     *
     * @return the batch step
     */
    public CreateMetaChangeStepRequest getBatchStep() {
        return batchStep;
    }

    /**
     * Sets batch step.
     *
     * @param batchStep the batch step
     */
    public void setBatchStep(CreateMetaChangeStepRequest batchStep) {
        this.batchStep = batchStep;
    }

    /**
     * Gets before gray start step.
     *
     * @return the before gray start step
     */
    public CreateActionChangeStepRequest getBeforeGrayStartStep() {
        return beforeGrayStartStep;
    }

    /**
     * Sets before gray start step.
     *
     * @param beforeGrayStartStep the before gray start step
     */
    public void setBeforeGrayStartStep(
            CreateActionChangeStepRequest beforeGrayStartStep) {
        this.beforeGrayStartStep = beforeGrayStartStep;
    }

    /**
     * Gets after gray finish step.
     *
     * @return the after gray finish step
     */
    public CreateActionChangeStepRequest getAfterGrayFinishStep() {
        return afterGrayFinishStep;
    }

    /**
     * Sets after gray finish step.
     *
     * @param afterGrayFinishStep the after gray finish step
     */
    public void setAfterGrayFinishStep(
            CreateActionChangeStepRequest afterGrayFinishStep) {
        this.afterGrayFinishStep = afterGrayFinishStep;
    }

    /**
     * Gets gray batch action steps.
     *
     * @return the gray batch action steps
     */
    public List<CreateActionChangeStepRequest> getGrayBatchActionSteps() {
        return grayBatchActionSteps;
    }

    /**
     * Sets gray batch action steps.
     *
     * @param grayBatchActionSteps the gray batch action steps
     */
    public void setGrayBatchActionSteps(
            List<CreateActionChangeStepRequest> grayBatchActionSteps) {
        this.grayBatchActionSteps = grayBatchActionSteps;
    }
}