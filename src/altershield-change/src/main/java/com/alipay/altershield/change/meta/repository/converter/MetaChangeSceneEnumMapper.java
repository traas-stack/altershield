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
package com.alipay.altershield.change.meta.repository.converter;

import com.alipay.altershield.change.meta.model.enums.MetaChangeSceneStatus;
import org.springframework.stereotype.Component;

/**
 * The type Meta change scene enum mapper.
 *
 * @author yuanji
 * @version : MetChangeSceneEnumMapper.java, v 0.1 2022年03月16日 2:37 下午 yuanji Exp $
 */
@Component
public class MetaChangeSceneEnumMapper {

    /**
     * MetaChangeSceneStatus 转换
     *
     * @param status the status
     * @return int
     */
    public int toInt(MetaChangeSceneStatus status) {
        return status == null ? MetaChangeSceneStatus.TEMP.getStatus() : status.getStatus();
    }

    /**
     * metaChangeSceneStatus 转换
     *
     * @param status the status
     * @return meta change scene status
     */
    public MetaChangeSceneStatus toEnum(int status) {
        return MetaChangeSceneStatus.getByStatus(status);
    }

}