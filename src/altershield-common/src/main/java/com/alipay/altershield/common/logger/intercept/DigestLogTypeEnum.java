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
import com.alipay.altershield.common.logger.Loggers;

import org.slf4j.Logger;

/**
 * 摘要日志枚举
 * <p><font color="red">为规范管理，不要随便新增枚举；如有需要请联系作者</font></p>
 *
 * @author lijunjie
 * @version $Id: DigestLogTypeEnum.java, v 0.1 2014年11月7日 下午5:10:44 lijunjie Exp $
 */
public enum DigestLogTypeEnum {

    /** 外部操作类调用摘要日志 */
    SERVICE_OPERATE_DIGEST("外部操作类调用摘要日志", Loggers.SERVICE_OPERATE_DIGEST),

    /** 外部查询类调用摘要日志 */
    SERVICE_QUERY_DIGEST("外部查询类调用摘要日志", Loggers.SERVICE_QUERY_DIGEST),

    ///**  dal摘要日志 */
    //DAL_DIGET("dal摘要日志",Loggers.DAL_DIGEST),

    /** 无摘要日志 */
    NULL_LOG("无摘要日志", null),

    ;

    /** 枚举信息 */
    private String message;

    /** 获取摘要日志logger */
    private Logger logger;

    /**
     * 构造器
     * @param message   枚举信息
     * @param logger 日志句柄
     */
    DigestLogTypeEnum(String message, Logger logger) {
        this.message = message;
        this.logger = logger;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter method for property <tt>logger</tt>.
     *
     * @return property value of logger
     */
    public Logger getLogger() {
        return logger;
    }
}
