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


import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class CommonUtil {
    /**
     * The constant UTF_8.
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * The constant SYS_CODE.
     */
    public static final String SYS_CODE = "247";
    /**
     * The constant FAKE_ID_BIZ_CODE.
     */
    public static final String FAKE_ID_BIZ_CODE = "XXX";
    private static final String EMPTY_STRING = "";
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Noop.
     */
    public static void noop() {}

    /**
     * Brief string string.
     *
     * @param origin the origin
     * @param headSize ≥0
     * @param tailSize ≥0
     * @return string
     */
    public static String briefString(String origin, int headSize, int tailSize) {
        if (headSize < 0) {
            headSize = 0;
        }
        if (tailSize < 0) {
            tailSize = 0;
        }
        if (origin == null || origin.length() <= headSize + tailSize) {
            return origin;
        }

        String head = origin.substring(0, headSize);
        String tail = origin.substring(origin.length() - tailSize);
        return new StringBuilder(3 + headSize + tailSize).append(head).append("...").append(tail)
                .toString();
    }

    /**
     * Gets opscld id default ext string.
     *
     * @return the opscld id default ext string
     */
    public static String getOpscldIdDefaultExtString() {
        return "00000000";
    }

    /**
     * Gets default data system version.
     *
     * @return the default data system version
     */
    public static String getDefaultDataSystemVersion() {
        return "01";
    }

    /**
     * Gets default id random string.
     *
     * @return the default id random string
     */
    public static String getDefaultIdRandomString() {
        return "XX";
    }

    /**
     * Convert from 36 long.
     *
     * @param str never null, in right range
     * @return long
     */
    public static long convertFrom36(String str) {
        if (CommonUtil.isBlank(str)) {
            return 0L;
        }
        long rst = 0l;
        for (int i = 0; i < str.length(); ++i) {
            rst = rst * 36;
            char c = str.charAt(i);
            rst += c2d(c);
        }
        return rst;
    }

    /**
     * Convert to 36 fix length string.
     *
     * @param num always >= 0
     * @param len the len
     * @return string
     */
    public static String convertTo36FixLength(long num, int len) {
        char[] seq = new char[len];
        for (int i = 0; i < len; ++i) {
            char c = d2c((int) (num % 36));
            seq[len - 1 - i] = c;
            num /= 36;
        }
        return new String(seq);
    }

    private static int c2d(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        }
        throw new IllegalArgumentException("cannot convert 36-base c " + c);
    }

    /**
     * @param i 0~35
     * @return
     */
    private static char d2c(int i) {
        if (i < 10) {
            return (char) ('0' + i);
        } else {
            return (char) ('A' + (i - 10));
        }
    }

    /**
     * no '\n' included in both key and value; no '=' included in key; no null
     *
     * @param map the map
     * @return string
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
     * Parse property map map.
     *
     * @param string e.g. "abc=123\nbcd=456"
     * @return map
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
     * Compose map map.
     *
     * @param strs k1,v1,k2,v2
     * @return map
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
     * Compose typed map map.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param kvs k1,v1,k2,v2
     * @return map
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
     * Equals boolean.
     *
     * @param <T> the type parameter
     * @param o1 the o 1
     * @param o2 the o 2
     * @return boolean
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
     * Gets local ip.
     *
     * @return the local IP or null if can't get.
     */
    public static String getLocalIp() {
        try {
            InetAddress add = InetAddress.getLocalHost();
            return add.getHostAddress();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Subtract collection list.
     *
     * @param <T> the type parameter
     * @param from the from
     * @param sub the sub
     * @return list
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
     * Add into list list.
     *
     * @param <T> the type parameter
     * @param coll the coll
     * @param ele the ele
     * @return list
     */
    public static <T> List<T> addIntoList(List<T> coll, T ele) {
        if (coll == null) {
            coll = new ArrayList<T>();
        }
        coll.add(ele);
        return coll;
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
     * Split as list list.
     *
     * @param str the str
     * @param separatorChars the separator chars
     * @param trim the trim
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
     * Split as set set.
     *
     * @param str the str
     * @param separatorChars the separator chars
     * @param trim the trim
     * @return set
     */
    public static Set<String> splitAsSet(String str, String separatorChars, final boolean trim) {
        return new HashSet<String>(splitAsList(str, separatorChars, trim));
    }

    /**
     * 按照空格对字符串进行拆分。
     *
     * @param str the str
     * @return string [ ]
     */
    public static String[] split(String str) {
        return split(str, null, -1);
    }

    /**
     * str: "12345",separatorChars: "24" => {"1","3","5"}
     *
     * @param str the str
     * @param separatorChars the separator chars
     * @return string [ ]
     */
    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }

    /**
     * Split string [ ].
     *
     * @param str the str
     * @param separatorChars the separator chars
     * @param max the max
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
        return list.toArray(new String[list.size()]);
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
     * @param str the str
     * @param separator the separator
     * @return the string
     */
    public static String substringBeforeLast(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0)
                || (separator.length() == 0)) {
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
     * @param str the str
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
     * @param str the str
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
     * @param str the str
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
     * Is empty boolean.
     *
     * @param coll the coll
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * Is empty boolean.
     *
     * @param map the map
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Is not empty boolean.
     *
     * @param coll the coll
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return coll != null && !coll.isEmpty();
    }

    /**
     * Join string.
     *
     * @param delimiter the delimiter
     * @param elements the elements
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
     *
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
     * @return 扩展后的字符串 ，如果字符串为<code>null</code>，则返回<code>null</code>
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
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return new String(padding).concat(str);
        }
    }

    /**
     * 扩展并左对齐字符串，用指定字符串填充右边。
     *
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
     * @return 扩展后的字符串 ，如果字符串为<code>null</code>，则返回<code>null</code>
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
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return str.concat(new String(padding));
        }
    }

    /**
     * 去除重复，保留原来的顺序。注意的是，输入列表也会被删除重复。
     *
     * @param <T> the type parameter
     * @param list the list
     * @return list
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        HashSet<T> h = new HashSet<T>();
        List<T> list2 = new ArrayList<T>();

        if (list == null || list.isEmpty()) {
            return list2;
        }
        for (T t : list) {
            if (!h.contains(t)) {
                h.add(t);
                list2.add(t);
            }
        }
        list.clear();
        list.addAll(list2);
        return list2;
    }

    /**
     * str 总共包换了多少个 containsStr.如果str或者containsStr为blank,返回0
     *
     * @param str the str
     * @param containsStr the contains str
     * @return int
     */
    public static int containsStrTotalCount(String str, String containsStr) {
        if (str == null || str.length() == 0 || containsStr == null || containsStr.length() == 0) {
            return 0;
        }
        int count = 0;
        // 包含的字符串的在str中的起始位置
        int index = 0;
        int cLen = containsStr.length();
        int sLen = str.length();
        while (index < sLen) {
            index = str.indexOf(containsStr, index);
            if (index != -1) {
                ++count;
                index += cLen;
            } else {
                break;
            }
        }

        return count;
    }

    /**
     * 创建一个包含了{#count}个空白字符的字符串。
     *
     * @param count the count
     * @return string
     */
    public static String blanks(int count) {
        StringBuilder blank = new StringBuilder();
        for (int j = 0; j < count; j++) {
            blank.append(" ");
        }
        return blank.toString();
    }

    /**
     * Find the max length of string(measured in UTF8 bytes) with the last character equals the
     * specified endChar.
     *
     * @param source the source string
     * @param maxBytes the max bytes of target string(excluding the postFix)
     * @param endChar the endChar to be found when truncate
     * @param postFix the string to be padded to the return result if truncate operated
     * @return the target string
     * @throws IllegalArgumentException if target endChar not found in correct range.
     */
    public static String ensureMaxBytes(String source, int maxBytes, char endChar, String postFix) {
        int utf8Len = utf8Length(source);
        if (utf8Len <= maxBytes) {
            return source;
        }

        String target = makesureMaxBytesLen(source, maxBytes);
        int lastIdx = target.length() + 200;

        do {
            lastIdx = source.lastIndexOf(endChar, lastIdx - 1);
            if (lastIdx == -1) {
                throw new IllegalArgumentException(
                        "Character: '" + endChar + "' not found in range.");
            }
            target = source.substring(0, lastIdx + 1);
        } while (utf8Length(target) > maxBytes);

        return target + postFix;
    }

    /**
     * Return the UTF-8 bytes length of the specified string.
     *
     * @param s the s
     * @return int
     */
    public static int utf8Length(String s) {
        if (s == null) {
            return -1;
        }
        return s.getBytes(UTF_8).length;
    }

    /**
     * 确保str的utf8编码后的bytes.length不超过指定长度
     *
     * @param str the str
     * @param maxBytesLen the max bytes len
     * @return 切割后的字符 string
     */
    public static String makesureMaxBytesLen(String str, int maxBytesLen) {
        return makesureMaxBytesLen0(str, maxBytesLen, 0);
    }

    private static String makesureMaxBytesLen0(String str, int maxBytesLen, int level) {
        if (str == null) {
            return null;
        }

        if (!(maxBytesLen >= 1)) {
            throw new IllegalArgumentException("maxBytesLen <= 0:" + maxBytesLen);
        }

        int strLen = str.length();
        int byteLen = utf8Length(str);
        if (byteLen > maxBytesLen) {
            // 由于存在中文的情况 因此byteLen比maxLen大的情况只能按照比例来缩减,保证缩减后实际值能够小于maxLen
            double scale = maxBytesLen * 1.0d / byteLen;

            // 由于scale < 1,因此最后endIndex的值肯定会比strLen小.因为Double#intValue只会取整数的部分
            Double endIndexD = scale * strLen;
            int endIndex = endIndexD.intValue();

            endIndex = endIndex <= 0 ? 1 : endIndex;
            String result = str.substring(0, endIndex);

            return makesureMaxBytesLen0(result, maxBytesLen, ++level);
        }

        return str;
    }

    /**
     * Parse batch no int.
     *
     * @param batchNo the batch no
     * @return the int
     */
    public static int parseBatchNo(String batchNo) {
        try {
            return Integer.parseInt(batchNo);
        } catch (Exception e) {
            return -1;
        }
    }

}
