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
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 * all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 * interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 * property laws and treaties.
 * <p>
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 * Services Group Co., Ltd., no one may conduct the following actions:
 * <p>
 * 1) reproduce, spread, present, set up a mirror of, upload, download this software;
 * <p>
 * 2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 * <p>
 * 3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 * software;
 * <p>
 * 4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 * the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiangyue
 * @version : MiscUtil.java, v 0.1 2022-09-26 19:21 xiangyue Exp $$
 */
public class MiscUtil {

    /**
     * 通用的未知类型字段解析成set的方法。
     *
     * @param param 未知类型字段
     * @return 解析后的set
     */
    public static Set<String> parseSet(Object param) {
        Set<String> set = new HashSet<>();
        if (param == null) {
            return set;
        } else if (param instanceof Collection) {
            Collection<Object> collection = (Collection<Object>) param;
            for (Object p : collection) {
                if (p != null) {
                    set.add(p.toString().trim());
                }
            }
        } else {
            String s = param.toString().trim();
            // 首先尝试解析一下是不是一个JSON的数组
            if (s.startsWith("[")) {
                try {
                    List<String> array = JSON.parseArray(s, String.class);
                    set.addAll(array.stream().map(String::trim).collect(Collectors.toList()));
                } catch (Throwable ignored) {
                    ;
                }
            } else {
                // 没有约定的情况下，通常我们会以逗号，分号，空格，冒号去分隔。
                String[] strings = s.split("[,; :]");
                for (String p : strings) {
                    set.add(p.trim());
                }
            }
        }
        // 删除可能出现的空白字符串。
        set.remove("");
        return set;
    }

    public static boolean isIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        List<String> toks = Arrays.asList(ip.split("."));
        if (toks.size() != 4) {
            return false;
        }
        for (String tok : toks) {
            for (int i = 0; i < tok.length(); ++i) {
                char c = tok.charAt(i);
                if (c > '9' || c < '0') {
                    return false;
                }
            }
            int num = Integer.parseInt(tok);
            if (num > 255 || num < 0) {
                return false;
            }
        }
        return true;
    }
}
