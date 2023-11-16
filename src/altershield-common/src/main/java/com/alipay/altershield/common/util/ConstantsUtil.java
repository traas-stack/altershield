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
package com.alipay.altershield.common.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 *
 * @author liushengdong
 * @version $Id: ConstantsUtil.java, v 0.1 2018年12月04日 11:49 PM liushengdong Exp $
 */
public class ConstantsUtil {

    /**
     * Use the specified config map to fill the static fields of constant class.
     *
     * @param config
     * @param constantsClass
     */
    public static void fillConstantsClass(Map<String, String> config, Class<?> constantsClass) {
        for (Map.Entry<String, String> configEntry : config.entrySet()) {
            try {
                fillConstantsClass(configEntry.getKey(), configEntry.getValue(), constantsClass);
            } catch (Throwable t) {
                // Ignore.
            }
        }
    }

    /**
     * 用key:value的值来填充静态产量类中的字段
     * @param key 静态常量类的字段名
     * @param value 静态产量类的值(全部以String定义)
     * @param constantsClass 静态常量类,内部的field全部为public static字段
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static boolean fillConstantsClass(String key, String value, Class<?> constantsClass)
            throws NoSuchFieldException, IllegalAccessException {
        if(key == null || value == null){
            return false;
        }
        Field f = constantsClass.getDeclaredField(key);
        if (f == null) {
            return false;
        }
        Class<?> clz = f.getType();
        f.setAccessible(true);

        if (String.class.getName().equals(clz.getName())) {
            f.set(null, value);
        } else {
            if (double.class.getName().equals(clz.getName())) {
                double d = Double.parseDouble(value);
                f.set(null, d);
            } else if (int.class.getName().equals(clz.getName())) {
                int i = Integer.parseInt(value);
                f.set(null, i);
            } else if (long.class.getName().equals(clz.getName())) {
                long l = Long.parseLong(value);
                f.set(null, l);
            } else if (boolean.class.getName().equals(clz.getName())) {
                f.set(null, "true".equalsIgnoreCase(value));
            } else if (char.class.getName().equals(clz.getName())) {
                f.set(null, value.charAt(0));
            } else {
                throw new IllegalArgumentException("unknown type " + clz);
            }
        }
        return true;
    }

    public static boolean isBasicType(Class<?> clz) {
        if(clz == null){
            return false;
        }
        if (String.class.getName().equals(clz.getName())) {
            return true;
        } else if (double.class.getName().equals(clz.getName())) {
            return true;
        } else if (float.class.getName().equals(clz.getName())) {
            return true;
        } else if (int.class.getName().equals(clz.getName())) {
            return true;
        } else if (long.class.getName().equals(clz.getName())) {
            return true;
        } else if (boolean.class.getName().equals(clz.getName())) {
            return true;
        } else if (char.class.getName().equals(clz.getName())) {
            return true;
        } else if (byte.class.getName().equals(clz.getName())) {
            return true;
        } else if (short.class.getName().equals(clz.getName())) {
            return true;
        } else {
            return false;
        }
    }

    private static Set<Object> set = new HashSet<Object>();
    static {
        set.add(String.class);
        set.add(double.class);
        set.add(int.class);
        set.add(long.class);
        set.add(boolean.class);
        set.add(char.class);
    }

    public static String getFieldsStr(Class<?> constantsClass){
        if(constantsClass == null){
            return "{}";
        }
        Field[] declaredFields = constantsClass.getDeclaredFields();
        if(declaredFields == null || declaredFields.length == 0){
            return "{}";
        }

        List<Field> fields = new ArrayList<Field>();
        for (Field e : declaredFields) {
            if(set.contains(e.getType())){
                fields.add(e);
            }
        }

        //List<Field> fields = Arrays.stream(declaredFields).filter(e -> set.contains(e.getType())).collect(Collectors.toList());
        if(fields.isEmpty()){
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int index = 0;
        for (Field field : fields) {
            try {
                String name = field.getName();
                Object o = field.get(null);
                sb.append("\"").append(name).append("\":\"").append(o==null?"null":o.toString()).append("\"");
                if(index+1 < fields.size()){
                    sb.append(",");
                }
            } catch (Throwable e) {
                // NOPMD
            }
            index++;
        }
        sb.append("}");
        return sb.toString();
    }
}