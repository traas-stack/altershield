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
package com.alipay.altershield.framework.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

/**
 * The type Json util.
 *
 * @author shuo.qius
 * @version Jun 7, 2018
 */
public class JSONUtil {

    /**
     * 判断给定字符串是否符合JSON格式。
     *
     * @param str the str
     * @return boolean
     */
    public static boolean isValidJSON(String str) {
        if (CommonUtil.isBlank(str)) {
            return true;
        }

        try {
            JSON.parse(str, Feature.IgnoreAutoType);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * To json string string.
     *
     * @param map the map
     * @return the string
     */
    public static String toJSONString(Map<String, String> map) {
        return map == null ? null : JSON.toJSONString(map);
    }

    /**
     * To json string string.
     *
     * @param objectList the list
     * @return the string
     */
    public static String toJSONString(List<Object> objectList) {
        return objectList == null ? null
                : JSON.toJSONString(objectList, SerializerFeature.UseSingleQuotes);
    }

    /**
     * Parse json to obj t.
     *
     * @param <T> the type parameter
     * @param jsn the jsn
     * @param clz the clz
     * @return the t
     */
    public static <T> T parseJSONToObj(String jsn, Class<T> clz) {
        if (jsn == null || CommonUtil.isBlank(jsn)) {
            return null;
        }
        return JSON.parseObject(jsn, clz, Feature.IgnoreAutoType);
    }

    /**
     * Parse json to obj t.
     *
     * @param <T> the type parameter
     * @param jsn the jsn
     * @param typeReference the type reference
     * @return the t
     */
    public static <T> T parseJSONToObj(String jsn, TypeReference<T> typeReference) {
        if (jsn == null || CommonUtil.isBlank(jsn)) {
            return null;
        }
        return JSON.parseObject(jsn, typeReference, Feature.IgnoreAutoType);
    }

    /**
     * To json string with class string.
     *
     * @param <T> the type parameter
     * @param t the t
     * @return the string
     */
    public static <T> String toJsonStringWithClass(T t) {
        return JSON.toJSONString(t, SerializerFeature.WriteClassName);
    }

    /**
     * To json string string.
     *
     * @param obj the obj
     * @param writeClassName true:写入反序列化的类名.保证反序列化一定不会出错,缺点是json中会增加很多字符
     * @return string
     */
    public static String toJSONString(Object obj, boolean writeClassName) {
        return obj == null ? null
                : (writeClassName ? JSON.toJSONString(obj, SerializerFeature.WriteClassName)
                        : JSON.toJSONString(obj));
    }

    /**
     * Parse json to obj list list.
     *
     * @param <T> the type parameter
     * @param jsn the jsn
     * @param clz the clz
     * @return list
     */
    public static <T> List<T> parseJSONToObjList(String jsn, Class<T> clz) {
        if (jsn == null) {
            return null;
        }
        return JSON.parseArray(jsn, clz);
    }

    /**
     * Parse json to map map.
     *
     * @param jsn the jsn
     * @return never null
     */
    public static Map<String, String> parseJSONToMap(String jsn) {
        if (CommonUtil.isBlank(jsn)) {
            return Collections.emptyMap();
        }
        JSONObject j = ChngSrvDTOCodec.decodeDTOAsJSON(jsn);
        return convertJSONToMap(j);
    }

    /**
     * Convert json to map map.
     *
     * @param j the j
     * @return never null
     */
    public static Map<String, String> convertJSONToMap(JSONObject j) {
        if (j == null) {
            return Collections.emptyMap();
        }
        Map<String, String> m = new HashMap<>();
        for (Object k : j.keySet()) {
            Object o = j.get(k);
            m.put(k == null ? null : String.valueOf(k), o == null ? null : String.valueOf(o));
        }
        return m;
    }

    /**
     * Parse json object json object.
     *
     * @param jsn the jsn
     * @return the json object
     */
    public static JSONObject parseJSONObject(String jsn) {
        return ChngSrvDTOCodec.decodeDTOAsJSON(jsn);
    }

    /**
     * @param jsnArray
     * @param clz
     * @return
     */
    public static <T> List<T> parseJSONToObjList(JSONArray jsnArray, Class<T> clz) {
        if (jsnArray == null) {
            return null;
        }
        if (jsnArray.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(jsnArray.size());
        for (int i = 0; i < jsnArray.size(); ++i) {
            T s = jsnArray.getObject(i, clz);
            list.add(s);
        }
        return list;
    }
}
