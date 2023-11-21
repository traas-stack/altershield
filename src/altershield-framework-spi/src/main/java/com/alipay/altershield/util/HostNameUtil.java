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
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.util;

import java.util.regex.Pattern;

/**
 *
 * @author lex
 * @version $Id: HostNameUtil.java, v 0.1 2021年02月05日 上午10:35 lex Exp $
 */
public class HostNameUtil {

    private static final Pattern DOMAIN_NAME_PATTERN = Pattern.compile("^((?!-)[A-Za-z0-9-]{1,128}(?<!-)\\.)+[A-Za-z0-9]{2,16}$");
    private static final Pattern HOST_NAME_PATTERN = Pattern.compile("^(?!-)[A-Za-z0-9-]{1,128}(?<!-)$");

    /**
     * Check if the given string is valid host name format.
     *
     * @param s
     * @return
     */
    public static boolean isHostName(String s) {
        return s != null && HOST_NAME_PATTERN.matcher(s).matches();
    }

    /**
     * Check if the given string is valid domain name format.
     *
     * @param s
     * @return
     */
    public static boolean isDomainName(String s) {
        return s != null && DOMAIN_NAME_PATTERN.matcher(s).matches();
    }

}