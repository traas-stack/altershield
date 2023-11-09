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
package com.alipay.altershield.change.meta.service;

import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.meta.change.model.enums.MetaChangeSceneGenerationEnum;

import java.util.function.Function;

/**
 * @author yuanji
 * @version : OpsCloudGenerationTransfer.java, v 0.1 2022年10月10日 15:25 yuanji Exp $
 */
public interface OpsCloudGenerationTransfer {

    /**
     * 支持的代G
     * @return
     */
    MetaChangeSceneGenerationEnum getChangeSceneGeneration();

    /**
     * 到g0
     * @param metaChangeSceneEntity
     * @return
     */
    OpsCloudResult<String> toG0(MetaChangeSceneEntity metaChangeSceneEntity);

    /**
     * 到g1
     * @param metaChangeSceneEntity
     * @return
     */
    OpsCloudResult<String> toG1(MetaChangeSceneEntity metaChangeSceneEntity, Function<MetaChangeSceneEntity, MetaChangeStepEntity> orderStepCreateFunction);

    /**
     * 到g2
     * @param metaChangeSceneEntity
     * @return
     */
    OpsCloudResult<String> toG2(MetaChangeSceneEntity metaChangeSceneEntity);

    /**
     * 到g3
     * @param metaChangeSceneEntity
     * @return
     */
    OpsCloudResult<String> toG3(MetaChangeSceneEntity metaChangeSceneEntity);

}