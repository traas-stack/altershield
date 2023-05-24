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
package com.alipay.altershield.framework.sdk.meta.client.builder;

import com.alipay.altershield.framework.core.change.facade.request.CreateActionChangeStepRequest;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeStepRequest;
import com.alipay.altershield.framework.core.change.facade.request.*;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 标准三板斧变更场景builder
 *
 * @author yuanji
 * @version : StandardChangeSceneBuilder.java, v 0.1 2022年05月31日 11:04 上午 yuanji Exp $
 */
public class StandardChangeSceneBuilder
        extends BaseChangeSceneBuilder<StandardChangeSceneBuilder> {

    /**
     * 设置工单级别检查信息
     */
    private CreateMetaChangeStepRequest orderStepCheck;
    /**
     * 设置批次级别检查信息
     */
    private CreateMetaChangeStepRequest batchStepCheck;

    /**
     * 是否允许回滚
     */
    private boolean enableRollback = false;
    /**
     * 分批策略
     */
    private String changeGrayModeType = "PIPELINE";

    /**
     * 创建变更前置动作
     */
    private CreateActionChangeStepRequest beforeGrayStartStep;
    /**
     * 变更生效结束后置动作
     */
    private CreateActionChangeStepRequest afterGrayFinishStep;

    /**
     * 分批内变更动作, 分批里包含0个或多个变更动作 但所有的分批内的变更动作应该是相同的
     */
    private List<CreateActionChangeStepRequest> grayBatchActionSteps;

    /**
     * 构建方法
     *
     * @param generation
     */
    private StandardChangeSceneBuilder(@NotNull String generation) {
        super(generation);
    }

    /**
     * 获取G2场景builder实例
     *
     * @return
     */
    public static StandardChangeSceneBuilder getG2Instance() {
        return new StandardChangeSceneBuilder(MetaChangeSceneGenerationEnum.G2.name());
    }

    /**
     * 获取G3场景builder实例
     *
     * @return
     */
    public static StandardChangeSceneBuilder getG3Instance() {
        return new StandardChangeSceneBuilder(MetaChangeSceneGenerationEnum.G3.name());
    }

    /**
     * 设置工单级别检查配置信息 非必须
     *
     * @param enablePreCheck 是否支持前置校验
     * @param preCheckTimeout 前置校验超时时间 单位:ms
     * @param enablePostCheck 是否支持后置校验
     * @param postCheckTimeout 后置校验超时时间 单位:ms
     * @return
     */
    public StandardChangeSceneBuilder enableOrderStepCheck(boolean enablePreCheck,
                                                           long preCheckTimeout, boolean enablePostCheck, long postCheckTimeout) {
        this.orderStepCheck = new CreateMetaChangeStepRequest(enablePreCheck, preCheckTimeout,
                enablePostCheck, postCheckTimeout);
        return this;
    }

    /**
     * 设置分批阶段检查配置信息
     *
     * @param enablePreCheck 是否支持前置校验
     * @param preCheckTimeout 前置校验超时时间 单位:ms
     * @param enablePostCheck 是否支持后置校验
     * @param postCheckTimeout 后置校验超时时间 单位:ms
     * @return
     */
    public StandardChangeSceneBuilder enableBatchStepCheck(boolean enablePreCheck,
                                                           long preCheckTimeout, boolean enablePostCheck, long postCheckTimeout) {
        this.batchStepCheck = new CreateMetaChangeStepRequest(enablePreCheck, preCheckTimeout,
                enablePostCheck, postCheckTimeout);
        return this;
    }

    /**
     * 设置不做分批检查
     *
     * @return
     */
    public StandardChangeSceneBuilder disableBatchStepCheck() {
        this.batchStepCheck = new CreateMetaChangeStepRequest(false, 0, false, 0);
        return this;
    }

    /**
     * 场景支持一键回滚
     *
     * @return
     */
    public StandardChangeSceneBuilder enableRollback() {
        this.enableRollback = true;
        return this;
    }

    /**
     * 变更场景分批模式，默认是pipeline
     *
     * @return
     */
    public StandardChangeSceneBuilder setChangeGrayModeType(String changeGrayModeType) {
        this.changeGrayModeType = changeGrayModeType;
        return this;
    }

    /**
     * 设置灰度开始前动作 非必须
     *
     * @return
     */
    public StandardChangeSceneBuilder setBeforeGrayStartStep(
            CreateActionChangeStepRequest beforeGrayStartStep) {
        this.beforeGrayStartStep = beforeGrayStartStep;
        return this;
    }

    /**
     * 设置灰度结事后动作 非必须
     *
     * @return
     */
    public StandardChangeSceneBuilder setAfterGrayFinishStep(
            CreateActionChangeStepRequest afterGrayFinishStep) {
        this.afterGrayFinishStep = afterGrayFinishStep;
        return this;
    }

    /**
     * 设置灰度分批里的动作 非必须
     *
     * @param grayBatchActionSteps
     * @return
     */
    public StandardChangeSceneBuilder setGrayBatchActionSteps(
            CreateActionChangeStepRequest... grayBatchActionSteps) {
        this.grayBatchActionSteps = Arrays.asList(grayBatchActionSteps);
        return this;
    }

    /**
     * 构建创建场景请求对象
     *
     * @return
     */
    public CreateMetaChangeSceneRequest build() {
        CreateMetaChangeSceneRequest createMetaChangeSceneRequest = super.build();
        MetaChangeEffectiveConfigRequest changeEffectiveConfig =
                new MetaChangeEffectiveConfigRequest();
        createMetaChangeSceneRequest.setChangeEffectiveConfig(changeEffectiveConfig);
        changeEffectiveConfig.setEnableRollback(enableRollback);
        changeEffectiveConfig.setChangeGrayModeType(changeGrayModeType);
        Assert.notNull(batchStepCheck,
                "G2/G3/G4 change scene must contain batch change step is null");
        CreateMetaChangeProgressRequest progressRequest = new CreateMetaChangeProgressRequest();
        changeEffectiveConfig.setChangeProgress(progressRequest);
        progressRequest.setAfterGrayFinishStep(afterGrayFinishStep);
        progressRequest.setBatchStep(batchStepCheck);
        progressRequest.setOrderStep(orderStepCheck);
        progressRequest.setBeforeGrayStartStep(beforeGrayStartStep);
        progressRequest.setGrayBatchActionSteps(grayBatchActionSteps);
        return createMetaChangeSceneRequest;
    }
}
