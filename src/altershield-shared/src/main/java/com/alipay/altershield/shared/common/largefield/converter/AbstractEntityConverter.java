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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.shared.common.largefield.converter;

import com.alibaba.fastjson.TypeReference;
import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.common.largefield.ref.KvRef;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yuanji
 * @version : AbstractEntityConvertor.java, v 0.1 2022年03月11日 8:16 下午 yuanji Exp $
 */
public abstract class AbstractEntityConverter {

    @Autowired
    @Qualifier("singleKeyValueStorage")
    protected KeyValueStorage singleKeyValueStorage;

    @Autowired
    @Qualifier("shardingKeyValueStorage")
    protected KeyValueStorage shardingKeyValueStorage;

    public void setSingleKeyValueStorage(KeyValueStorage singleKeyValueStorage) {
        this.singleKeyValueStorage = singleKeyValueStorage;
    }

    public void setKeyValueStorage(KeyValueStorage keyValueStorage) {
        this.shardingKeyValueStorage = keyValueStorage;
    }

    public <Entity, DataObject> List<Entity> convertToEntityList(List<DataObject> dataObjectList,
                                                                    Function<DataObject, Entity> function) {
        if (CollectionUtils.isEmpty(dataObjectList)) {
            return Collections.emptyList();
        }

        List<Entity> result = new ArrayList<>(dataObjectList.size());
        for (DataObject dataObject : dataObjectList) {
            result.add(function.apply(dataObject));
        }

        return result;
    }

    protected <T> T convertKValueToEntity(String refValue, Class<T> entityClazz) {

        if (refValue != null) {
            String value = singleKeyValueStorage.getValue(refValue);
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return JSONUtil.parseJSONToObj(value, entityClazz);
        }
        return null;
    }

    protected <T> T convertKValueToEntity(String refValue, TypeReference<T> entityClazz) {

        if (refValue != null) {
            String value = singleKeyValueStorage.getValue(refValue);
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return JSONUtil.parseJSONToObj(value, entityClazz);
        }
        return null;
    }

    /**
     * 对于多记录的转换
     * @param refValue
     * @param codec
     * @param realContentLenThreshold
     * @param <T>
     * @return
     */
    protected <T> KvRef<T> convertKValueToRef(String refValue, KvRefCodec<T> codec, int realContentLenThreshold) {
        return new KvRef<T>(shardingKeyValueStorage, codec, refValue, realContentLenThreshold);
    }

    protected <T> KvRef<T> convertKValueToRef(String refValue, KvRefCodec<T> codec) {
        return new KvRef<T>(shardingKeyValueStorage, codec, refValue);
    }

    protected String flushKvRef(KvRef kvRef, String id) {
        return kvRef.flush(id);
    }

    /**
     * 始终于meta单kvValue转换
     * @param entity
     * @param oldKey
     * @param id
     * @param consumer
     * @return
     */
    protected String putKValueToEntity(Object entity, String oldKey, String id, Consumer<String> consumer) {
      return putKValueToEntity(entity, oldKey, id, consumer, o -> JSONUtil.toJSONString(entity, false));
    }


    protected String putKValueToEntity(Object entity, String oldKey, String id, Consumer<String> consumer, Function<Object,String> jsonFunction) {
        if (entity != null) {
            String jsonString = jsonFunction.apply(entity);
            String newId = singleKeyValueStorage.putValue(oldKey, jsonString, id);
            consumer.accept(newId);
            return newId;
        }
        else
        {
            consumer.accept(null);
        }
        return null;
    }

}