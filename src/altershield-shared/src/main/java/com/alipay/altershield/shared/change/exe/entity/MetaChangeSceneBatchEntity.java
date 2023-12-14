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
package com.alipay.altershield.shared.change.exe.entity;

import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import lombok.Data;

import java.util.List;

/**
 * @author gaofeng.pan
 * @version : MetaChangeSceneBatchDO.java,v 0.1 2022/10/24 17:53 gaofeng.pan
 */
@Data
public class MetaChangeSceneBatchEntity {
    /**
     * Database Column Remarks:
     * 主键
     *
     * @mbg.generated
     */
    private String id;

    /**
     * 场景Key
     */
    private String changeSceneKey;

    /**
     * 场景名称
     */
    private String changeSceneName;

    /**
     * 场景代际
     */
    private MetaChangeSceneGenerationEnum sceneGeneration;

    /**
     * 对应的批次数据
     */
    private List<MetaChangeBatchEntity> changeBatchDOS;
}
