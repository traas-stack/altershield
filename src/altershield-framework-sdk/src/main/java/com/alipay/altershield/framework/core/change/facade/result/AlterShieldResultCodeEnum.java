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
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co.,
 * Ltd. This software and all the relevant information, including but not limited to any signs,
 * images, photographs, animations, text, interface design, audios and videos, and printed
 * materials, are protected by copyright laws and other intellectual property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation
 * License Agreement/Software Use Agreement updated from time to time. Without authorization from
 * Ant Small and Micro Financial Services Group Co., Ltd., no one may conduct the following actions:
 *
 * 1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 * 2) reverse engineer, decompile the source code of this software or try to find the source code in
 * any other ways;
 *
 * 3) modify, translate and adapt this software, or develop derivative products, works, and services
 * based on this software;
 *
 * 4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this
 * software, or authorize the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.framework.core.change.facade.result;

import java.io.Serializable;

/**
 * @author shuo.qius
 * @version Jul 30, 2018
 */
public enum AlterShieldResultCodeEnum implements Serializable {
    /** success */
    SUCCESS("000"),
    /** change service spi never executed */
    ILLEGAL_ARGUMENT("101"),
    /** change service spi never executed */
    PERMISSION_DENIED("102"),
    /** this operation not supported anymore */
    NOT_SUPPORTED("103"),
    /** this is for access limit exceeded */
    ACCESS_LIMIT_EXCEEDED("104"),
    /** this is for data not found */
    DATA_NOT_FOUND("105"),
    /** duplicate biz orderId */
    Duplicate_BIZ_ORDERID("106"),
    /** change service spi never executed */
    PRECHECK_FAIL("110"),
    /** 后置check失败 */
    POSTCHECK_FAIL("111"),
    /** 没有执行防御or防御规则 */
    WITHOUT_CHECK("151"),
    /** 没有执行后置防御or后置防御规则 */
    WITHOUT_POST_CHECK("152"),
    /** change service spi executed and failed (调用SPI失败,没有接收到SPI返回结果) */
    INVOKE_SPI_FAILED("210"),
    /** 调用SPI成功,SPI内部错误 */
    SPI_INNER_ERROR("211"),
    /** 近端包发起调用时，遇到未知异常 */
    CLIENT_INVOKE_ERROR("311"),
    /** 近端包发起调用时，遇到超时 */
    CLIENT_TIMEOUT("312"),
    /** 近端包发起调用时，遇到路由问题(网络不通或者目标服务器宕机) */
    CLIENT_ROUTE_ERROR("313"),
    /** common internal error from opscloud */
    SYSTEM_ERROR("998"),;

    private final String code;

    /**
     * @param code
     */
    private AlterShieldResultCodeEnum(String code) {
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public static AlterShieldResultCodeEnum getByCode(String code) {
        for (AlterShieldResultCodeEnum e : AlterShieldResultCodeEnum.values()) {
            if (e.getCode().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * @return property value of {@link #code}
     */
    public String getCode() {
        return code;
    }

}
