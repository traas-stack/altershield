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

import com.alipay.altershield.framework.common.util.CommonUtil;

/**
 * @author shuo.qius
 * @version Mar 24, 2019
 */
public class OpsChngTraceCommonUtil {


    /**
     * @param yyyyMMdd never nulll
     * @return 4-digit
     */
    public static String compressDate(String yyyyMMdd) {
        int diffDay = Integer.parseInt(yyyyMMdd) - Integer.parseInt("20190101");
        return CommonUtil.convertTo36FixLength(diffDay, 4);
    }

    /**
     * @param date 4-digit
     * @return
     */
    public static String deCompressDate(String date) {
        long dateDiff = CommonUtil.convertFrom36(date);
        return String.valueOf(20190101 + dateDiff);
    }

}
