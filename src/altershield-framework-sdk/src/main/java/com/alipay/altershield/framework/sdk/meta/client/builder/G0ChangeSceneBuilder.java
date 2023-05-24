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

import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import javax.validation.constraints.NotNull;

/**
 * @author yuanji
 * @version : G0ChangeSceneBuilder.java, v 0.1 2022年05月31日 10:56 上午 yuanji Exp $
 */
public class G0ChangeSceneBuilder
        extends BaseChangeSceneBuilder<G0ChangeSceneBuilder> {

    /**
     * 构建方法
     *
     * @param generation
     */
    G0ChangeSceneBuilder(@NotNull String generation) {
        super(generation);
    }

    /**
     * 获取G0场景builder实例
     *
     * @return
     */
    public static G0ChangeSceneBuilder instance() {
        return new G0ChangeSceneBuilder(MetaChangeSceneGenerationEnum.G0.name());
    }

}
