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
package com.alipay.altershield.framework.core.util;

/**
 *
 * @author lex
 * @version $Id: ExceptionUtils.java, v 0.1 2020年02月10日 下午9:09 lex Exp $
 */
public class ExceptionUtils {

    /**
     * Return the root cause recursively from the exception chain.
     *
     * @param t
     * @return
     */
    public static Throwable getRootCause(Throwable t) {
        Throwable q = t.getCause();
        while (q != null && q != t) {
            t = q;
            q = q.getCause();
        }

        return t;
    }

    /**
     * Return the exception message found recursively from the exception chain.
     *
     * @param t
     * @return
     */
    public static String getErrorMessage(Throwable t) {
        Throwable r = t;
        Throwable q = t.getCause();
        while (t.getMessage() == null && q != null && q != t) {
            t = q;
            q = q.getCause();
        }

        if (t.getMessage() == null) {
            return r.toString();
        }

        return t.getMessage();
    }

}
