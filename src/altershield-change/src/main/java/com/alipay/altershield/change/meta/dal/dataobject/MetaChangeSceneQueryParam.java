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
package com.alipay.altershield.change.meta.dal.dataobject;

import lombok.Data;

import java.util.List;

/**
 * @author yuanji
 * @version : MetaChangeSceneQueryParam.java, v 0.1 2022年06月22日 18:26 yuanji Exp $
 */
@Data
public class MetaChangeSceneQueryParam {

    /**
     * 场景名
     */
    private String changeSceneName;

    /**
     * 场景key
     */
    private String changeSceneKey;

    /**
     * 平台名
     */
    private String platformName;

    /**
     * 0 是暂存，1是正式态
     */
    private Integer status;

    /**
     * 变更代G
     */
    private String generation;

    /**
     * 可信租户
     */
    private List<String> tenantCodes;

    /**
     * 场景管理员
     */
    private String owner;

    /**
     * 查询偏移
     */
    private int offset = 1;

    /**
     * 大小
     */
    private int limit = 20;
}