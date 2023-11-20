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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 统一 日志注解
 *
 * <p>@LogAnnotation注解配置示例：
 *      <ol>
 *          <li>@LogAnnotation(bizCode = "支付服务", digestLogType = DigestLogTypeEnum.SERVICE, detailLogType = DetailLogTypeEnum.SERVICE_INFO, keyArg = 0, keyProps = "payUserId|payeeUserId|creditAmount")</li>
 *          <li>public VmpPaymentResult pay(VmpPaymentRequest vmpPaymentRequest);</li>
 *      </ol>
 * </p>
 *
 * @author lijunjie
 * @version $Id: LogAnnotation.java, v 0.1 2014年11月7日 下午5:03:31 lijunjie Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AlterShieldLogAnnotation {
    /** 摘要日志类型 */
    DigestLogTypeEnum digestLogType() default DigestLogTypeEnum.NULL_LOG;

    /** 详情日志类型 */
    DetailLogTypeEnum detailLogType() default DetailLogTypeEnum.SERVICE_INFO;

    /** 服务描述 */
    String serviceDesc() default "";

    /** 业务关键字段序号 */
    short keyArg() default -1;

    /** 业务关键字段的字段名，用于monitor上的业务监控或者汇总 */
    String keyProps() default "";

    /** 非汇总性扩展字段，用于快速定位问题，不在monitor日志模型中(输出到digest) */
    String extProps() default "";
}
