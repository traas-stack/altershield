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

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    private static final String algorithm = "sha-256";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generate sign string.
     *
     * @param timestamp the timestamp
     * @param content the content
     * @param token the token
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String generateSign(long timestamp, String content, String token)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        String strToSign = timestamp + content + "&token=" + token;
        digest.update(strToSign.getBytes(UTF_8));
        byte[] output = digest.digest();
        return Base64.getUrlEncoder().encodeToString(output).toUpperCase();

    }

    /**
     * Verify sign boolean.
     *
     * @param timestamp the timestamp
     * @param content the content
     * @param signValue the sign value
     * @param token the token
     * @return the boolean
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static boolean verifySign(long timestamp, String content, String signValue, String token)
            throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(signValue)
                || StringUtils.isBlank(token)) {
            return false;
        }
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        String strToSign = timestamp + content + "&token=" + token;
        digest.update(strToSign.getBytes(UTF_8));
        byte[] output = digest.digest();
        String encodeStr = Base64.getUrlEncoder().encodeToString(output).toUpperCase();
        return StringUtils.equals(encodeStr, signValue);

    }

    /**
     * Sort map string.
     *
     * @param vars the vars
     * @return the string
     */
    public static String sortMap(Map<String, String> vars) {
        Map<String, String> treeMap = new TreeMap<>(Comparator.naturalOrder());
        treeMap.putAll(vars);
        return JSONUtil.toJSONString(treeMap);
    }

}
