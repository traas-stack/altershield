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
package com.alipay.altershield.framework.core.risk.filter;

import com.alipay.altershield.framework.core.risk.model.enums.DefenseFilterTypeEnum;

/**
 *
 * @author haoxuan.yhx
 * @version $Id: SimpleDefenseFilter.java, v 0.1 2019年06月18日 下午9:52 haoxuan.yhx Exp $
 */
public class SimpleDefenseFilter<T> {

    /**
     * 过滤方式：全部包含、包含其一
     */
    private DefenseFilterTypeEnum filterType;

    /**
     * 过滤的条件
     */
    private T data;

    public DefenseFilterTypeEnum getFilterType() {
        return filterType;
    }

    public void setFilterType(DefenseFilterTypeEnum filterType) {
        this.filterType = filterType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
