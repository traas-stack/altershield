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
package com.alipay.altershield.common.util;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author haoxuan
 * @version CommonUtil.java, v 0.1 2022年07月22日 2:50 下午 haoxuan
 */
public class CommonUtil {

    private static final String   EMPTY_STRING       = "";
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * str 总共包换了多少个 containsStr.如果str或者containsStr为blank,返回0
     * @param str
     * @param containsStr
     * @return
     */
    public static int containsStrTotalCount(String str, String containsStr) {
        if (str == null || str.length() == 0 || containsStr == null || containsStr.length() == 0) {
            return 0;
        }
        int count = 0;
        //包含的字符串的在str中的起始位置
        int index = 0;
        String subStr = str;
        int subIndex = -1;
        do {
            subIndex = subStr.indexOf(containsStr);
            if (subIndex >= 0) {
                count++;
                index += subIndex ;
                subStr = str.substring(index + containsStr.length());
                index += containsStr.length();
            }
        } while (subIndex >= 0);
        return count;
    }

    /**
     * no '\n' included in both key and value; no '=' included in key; no null
     *
     * @param map
     * @return
     */
    public static String encodePropertyMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder prop = new StringBuilder();
        for (Entry<String, String> en : map.entrySet()) {
            prop.append(en.getKey()).append('=').append(en.getValue()).append('\n');
        }
        return prop.toString();
    }

    /**
     * @param string e.g. "abc=123\nbcd=456"
     * @return
     */
    public static Map<String, String> parsePropertyMap(String string) {
        if (string == null) {
            return Collections.emptyMap();
        }
        List<String> lines = splitAsList(string, "\n", true);
        Map<String, String> map = new HashMap<String, String>(lines.size());

        for (String l : lines) {
            if (l == null) {
                continue;
            }
            l = l.trim();
            if (l.startsWith("#")) {
                continue;
            }
            int i = l.indexOf('=');
            if (i < 0) {
                continue;
            }
            String key = l.substring(0, i);
            String value = l.substring(i + 1, l.length());
            map.put(key, value);
        }

        return map;

    }

    /**
     * @param strs k1,v1,k2,v2
     * @return
     */
    public static Map<String, String> composeMap(Object... strs) {
        if (strs == null || strs.length == 0) {
            return new HashMap<String, String>(1);
        }
        Map<String, String> map = new HashMap<String, String>(strs.length / 2);
        for (int i = 0; i < strs.length; i += 2) {
            map.put(strs[i] == null ? null : String.valueOf(strs[i]),
                    strs[i + 1] == null ? null : String.valueOf(strs[i + 1]));
        }
        return map;
    }

    /**
     * @param kvs k1,v1,k2,v2
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> composeTypedMap(Object... kvs) {
        if (kvs == null || kvs.length == 0) {
            return new HashMap<K, V>(1);
        }
        Map<K, V> map = new HashMap<K, V>(kvs.length / 2);
        for (int i = 0; i < kvs.length - 1; i += 2) {
            K k = (K) kvs[i];
            V v = (V) kvs[i + 1];
            map.put(k, v);
        }
        return map;
    }

    /**
     * @param o1
     * @param o2
     * @return
     */
    public static <T> boolean equals(T o1, T o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    /**
     * @param from
     * @param sub
     * @return
     */
    public static <T> List<T> subtractCollection(Collection<T> from, Collection<T> sub) {
        if (from == null || from.isEmpty()) {
            return Collections.emptyList();
        }
        if (sub == null || sub.isEmpty()) {
            return new ArrayList<T>(from);
        }
        List<T> rst = new ArrayList<T>(from);
        for (T t : sub) {
            rst.remove(t);
        }
        return rst;
    }

    /**
     * Is not blank boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param str
     * @param separatorChars
     * @param trim
     * @return never null
     */
    public static List<String> splitAsList(String str, String separatorChars, final boolean trim) {
        String[] strs = split(str, separatorChars);
        if (strs == null || strs.length <= 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>(strs.length);
        for (String s : strs) {
            if (s != null && trim) {
                s = s.trim();
            }
            if (!trim || isNotBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * @param str
     * @param separatorChars
     * @param trim
     * @return
     */
    public static Set<String> splitAsSet(String str, String separatorChars, final boolean trim) {
        String[] strs = split(str, separatorChars);
        if (strs == null || strs.length <= 0) {
            return Collections.emptySet();
        }
        Set<String> list = new HashSet<String>(strs.length);
        for (String s : strs) {
            if (s != null && trim) {
                s = s.trim();
            }
            if (!trim || isNotBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * Split string [ ].
     *
     * @param str the str
     * @return the string [ ]
     */
    public static String[] split(String str) {
        return split(str, null, -1);
    }

    /**
     * Split string [ ].
     *
     * @param str            the str
     * @param separatorChars the separator chars
     * @return the string [ ]
     */
    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }

    /**
     * Split string [ ].
     *
     * @param str            the str
     * @param separatorChars the separator chars
     * @param max            the max
     * @return the string [ ]
     */
    public static String[] split(String str, String separatorChars, int max) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        if (separatorChars == null) {
            while (i < length) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);
            while (i < length) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        } else {
            while (i < length) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * Is blank boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Substring before last string.
     *
     * @param str       the str
     * @param separator the separator
     * @return the string
     */
    public static String substringBeforeLast(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
            return str;
        }

        int pos = str.lastIndexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    /**
     * Substring after last string.
     *
     * @param str       the str
     * @param separator the separator
     * @return the string
     */
    public static String substringAfterLast(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if ((separator == null) || (separator.length() == 0)) {
            return EMPTY_STRING;
        }

        int pos = str.lastIndexOf(separator);

        if ((pos == -1) || (pos == (str.length() - separator.length()))) {
            return EMPTY_STRING;
        }

        return str.substring(pos + separator.length());
    }

    /**
     * Substring before string.
     *
     * @param str       the str
     * @param separator the separator
     * @return the string
     */
    public static String substringBefore(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0)) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY_STRING;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * Substring after string.
     *
     * @param str       the str
     * @param separator the separator
     * @return the string
     */
    public static String substringAfter(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }
        if (separator == null) {
            return EMPTY_STRING;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY_STRING;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * Join string.
     *
     * @param delimiter the delimiter
     * @param elements  the elements
     * @return the string
     */
    public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        Iterator<? extends CharSequence> iter = elements.iterator();
        if (!iter.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(iter.next());
        while (iter.hasNext()) {
            sb.append(delimiter).append(iter.next());
        }
        return sb.toString();
    }

    /**
     * 扩展并右对齐字符串，用指定字符串填充左边。
     * <pre>
     * StringUtil.alignRight(null, *, *)      = null
     * StringUtil.alignRight("", 3, "z")      = "zzz"
     * StringUtil.alignRight("bat", 3, "yz")  = "bat"
     * StringUtil.alignRight("bat", 5, "yz")  = "yzbat"
     * StringUtil.alignRight("bat", 8, "yz")  = "yzyzybat"
     * StringUtil.alignRight("bat", 1, "yz")  = "bat"
     * StringUtil.alignRight("bat", -1, "yz") = "bat"
     * StringUtil.alignRight("bat", 5, null)  = "  bat"
     * StringUtil.alignRight("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str 要对齐的字符串
     * @param size 扩展字符串到指定宽度
     * @param padStr 填充字符串
     *
     * @return 扩展后的字符串，如果字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String alignRight(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding  = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return new String(padding).concat(str);
        }
    }

    /**
     * 扩展并左对齐字符串，用指定字符串填充右边。
     * <pre>
     * StringUtil.alignLeft(null, *, *)      = null
     * StringUtil.alignLeft("", 3, "z")      = "zzz"
     * StringUtil.alignLeft("bat", 3, "yz")  = "bat"
     * StringUtil.alignLeft("bat", 5, "yz")  = "batyz"
     * StringUtil.alignLeft("bat", 8, "yz")  = "batyzyzy"
     * StringUtil.alignLeft("bat", 1, "yz")  = "bat"
     * StringUtil.alignLeft("bat", -1, "yz") = "bat"
     * StringUtil.alignLeft("bat", 5, null)  = "bat  "
     * StringUtil.alignLeft("bat", 5, "")    = "bat  "
     * </pre>
     *
     * @param str 要对齐的字符串
     * @param size 扩展字符串到指定宽度
     * @param padStr 填充字符串
     *
     * @return 扩展后的字符串，如果字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String alignLeft(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads   = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding  = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return str.concat(new String(padding));
        }
    }

    /**
     * 去除重复
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        if(list == null || list.isEmpty()){
            return null;
        }
        HashSet<T> h = new HashSet<T>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 计算两个时间的耗时，如果有一个为null，则返回 -1
     * @param finishTime
     * @param startTime
     * @return
     */
    public static long cost(Date finishTime, Date startTime)
    {
        if(finishTime == null || startTime == null)
        {
            return -1;
        }
        return finishTime.getTime() - startTime.getTime();
    }
}