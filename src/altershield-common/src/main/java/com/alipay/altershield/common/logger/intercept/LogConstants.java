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
package com.alipay.altershield.common.logger.intercept;

/**
 * 日志中的字符常量
 *
 * @author lijunjie
 * @version $Id: LogConstants.java, v 0.1 2014年11月7日 下午3:40:16 lijunjie Exp $
 */
public class LogConstants {

    /** 左括号"(" */
    public static final String S_PREFIX   = "(";

    /** 右括号")" */
    public static final String S_SUFFIX   = ")";

    /** 左中括号"[" */
    public static final String C_PREFIX   = "[";

    /** 右中括号"]" */
    public static final String C_SUFFIX   = "]";

    /** 逗号"," */
    public static final String COMMA      = ",";

    /** 句号"." */
    public static final String DOT        = ".";

    /** 冒号":" */
    public static final String COLON      = ":";

    /** 成功"Y" */
    public static final String SUCCESS    = "Y";

    /** 失败"N" */
    public static final String FAIL       = "N";

    /** 异常"E" */
    public static final String EXCEPTION  = "E";

    /** 毫秒"ms" */
    public static final String MSEL       = "ms";

    /** 无"-" */
    public static final String NONE       = "-";

    /** 竖线"|" */
    public static final String VRTL       = "|";

    /** 相等 */
    public static final String EQUAL      = "=";

    /** getter方法前缀"get" */
    public static final String PRE_GETTER = "get";
}
