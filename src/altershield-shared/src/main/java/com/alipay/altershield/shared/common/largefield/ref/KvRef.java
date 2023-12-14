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
package com.alipay.altershield.shared.common.largefield.ref;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author shuo.qius
 * @version Nov 11, 2018
 */
public class KvRef<T> {
    private static final char     REAL_CONTENT_PREFIX_LEGACY         = '$';
    private static final char     REAL_CONTENT_PREFIX_DATA           = '{';
    private static final char     REAL_CONTENT_PREFIX                = ' ';
    private static final int      DEFAULT_REAL_CONTENT_LEN_THRESHOLD = 65000 - 1;

    private final KeyValueStorage keyValueStorage;
    protected final KvRefCodec<T> codec;
    private final int realContentLenThreshold;

    /**
     * @param keyValueStorage
     * @param codec
     * @param originlRef
     */
    public KvRef(KeyValueStorage keyValueStorage, KvRefCodec<T> codec, String originlRef) {
        this(keyValueStorage, codec, originlRef, DEFAULT_REAL_CONTENT_LEN_THRESHOLD);
    }

    /**
     * @param keyValueStorage
     * @param codec
     * @param originlRef
     * @param realContentLenThreshold
     */
    public KvRef(KeyValueStorage keyValueStorage, KvRefCodec<T> codec, String originlRef, int realContentLenThreshold) {
        super();
        this.originlRef = originlRef;
        this.codec = codec;
        this.keyValueStorage = keyValueStorage;
        this.realContentLenThreshold = realContentLenThreshold;
    }

    /**
     * ONLY {@link #flush(String)} can change it!<br/>
     * key for keyValue table OR content itself
     */
    private String             originlRef;

    protected volatile boolean read    = false;
    private volatile boolean   written = false;
    protected T                obj;

    /**
     * no db operation
     *
     * @param value
     * @return this
     */
    public synchronized KvRef<T> write(T value) {
        written = true;
        obj = value;
        return this;
    }

    /**
     * @return
     */
    public synchronized T readObject() {
        if (written) {
            return obj;
        }
        if (read) {
            return obj;
        }
        read = true;

        // read content
        String value;
        if (isRealContent(originlRef)) {
            // 数据存在本表，提取数据
            value = retrieveRealContent(originlRef);
        } else {
            value = keyValueStorage.getValue(originlRef);
        }

        // decode content to object
        if (value == null) {
            obj = null;
        } else {
            obj = codec.decode(value);
        }
        return obj;
    }

    /**
     * Write data into DB and return the reference.
     * 刷新数据，写入DB（kv表或本表字段）返回kv表的key
     * @param relatedId
     * @return new ref key
     */
    public synchronized String flush(String relatedId) {
        if (!written) {
            return originlRef;
        }
        String value = this.codec.encode(obj);

        if (needWriteAsRealContent(value)) {
            // 写入真实jsonString
            if (!isRealContent(originlRef)) {
                keyValueStorage.removeValue(originlRef);
            }
            originlRef = composeRealContent(value);
        } else {
            // 需要写kv表
            if (isRealContent(originlRef)) {
                originlRef = null;
            }
            // 数据写入kv表
            originlRef = keyValueStorage.putValue(originlRef, value, relatedId);
        }
        return originlRef;
    }

    /**
     * 是否需要写入本表字段（字段长度未超约定长度时）*
     * @param kvValue
     * @return
     */
    private boolean needWriteAsRealContent(String kvValue) {
        try {
            return AlterShieldConstant.SWITCH_KV_REF_WRITE_VAR_LEN && (StringUtils.isEmpty(kvValue)
                   || kvValue.getBytes("UTF8").length < realContentLenThreshold);
        } catch (UnsupportedEncodingException e) {
            AlterShieldLoggerManager.log("error", Loggers.DAL, e, kvValue);
        }
        return false;
    }

    // -------------------------------- format --------------------------------

    /**
     * 判断是否是真实字段内容（存在本表字段中）*
     * @param kvRefString
     * @return
     */
    private static boolean isRealContent(String kvRefString) {
        if (kvRefString == null || kvRefString.isEmpty()) {
            return true;
        }
        // 取首位，若为空格，则在本表，若不是空格，则存储的是kv表的key
        char char0 = kvRefString.charAt(0);
        return REAL_CONTENT_PREFIX == char0 || REAL_CONTENT_PREFIX_LEGACY == char0 || REAL_CONTENT_PREFIX_DATA == char0;
    }

    /**
     * @param kvRefString never empty
     * @return
     */
    private static String retrieveRealContent(String kvRefString) {
        if (kvRefString == null || kvRefString.isEmpty()) {
            return kvRefString;
        }
        if (REAL_CONTENT_PREFIX_DATA == kvRefString.charAt(0)) {
            return kvRefString;
        }
        return kvRefString.substring(1);
    }

    /**
     * @param realContent never empty
     * @return
     */
    private static String composeRealContent(String realContent) {
        if (realContent == null) {
            return null;
        }
        // 如果不需要写kv表，则开头加空格，后写入jsonString
        return new StringBuilder(1 + realContent.length()).append(REAL_CONTENT_PREFIX).append(realContent).toString();
    }

}
