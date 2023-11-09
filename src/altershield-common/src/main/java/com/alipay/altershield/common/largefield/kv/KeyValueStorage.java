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
package com.alipay.altershield.common.largefield.kv;

import com.alipay.altershield.common.mybatis.PartitionHolder;

/**
 * @author shuo.qius
 * @version Jun 4, 2018
 */
public interface KeyValueStorage {

    /**
     * @param key
     * @return
     */
    byte[] getValueBase64(String key);

    /**
     * @param oldKey
     * @param value
     * @param relatedId 保证当前kv和relateId同库
     * @return newKey might be equals to old key
     */
    String putBase64Value(String oldKey, byte[] value, String relatedId);

    /**
     * @param key
     * @return
     */
    String getValue(@PartitionHolder String key);

    /**
     * @param oldKey
     * @param value
     * @param relatedId 保证当前kv和relateId同库
     * @return newKey might be equals to old key(if oldKey == null,return new key)
     */
    String putValue(String oldKey, String value, String relatedId);

    /**
     * @param key
     */
    void removeValue(String key);

}
