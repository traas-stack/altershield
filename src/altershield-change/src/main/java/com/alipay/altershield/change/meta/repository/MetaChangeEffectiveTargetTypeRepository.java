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
package com.alipay.altershield.change.meta.repository;

import com.alipay.altershield.change.meta.model.MetaChangeEffectiveTargetTypeEntity;

import java.util.List;

/**
 *
 * @author yuanji
 * @version : MetaChangeEffectiveTargetTypeRepository.java, v 0.1 2022年03月14日 7:05 下午 yuanji Exp $
 */
public interface MetaChangeEffectiveTargetTypeRepository {

    /**
     * 根据类型查询生效类型
     * @param type  type名字
     * @param size
     * @return
     */
    List<MetaChangeEffectiveTargetTypeEntity> query(String type, int size);

    /**
     * 根据类型获取类型实体
     * @param type
     * @return
     */
    MetaChangeEffectiveTargetTypeEntity get(String type);

    /**
     * 写入新的生效类型
     * @param metaChangeEffectiveTargetTypeEntity
     * @return
     */
    boolean insert(MetaChangeEffectiveTargetTypeEntity metaChangeEffectiveTargetTypeEntity);

}