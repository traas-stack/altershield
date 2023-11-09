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
package com.alipay.altershield.change.meta.service.prod;

import lombok.Data;

/**
 * The type Meta service create request.
 *
 * @author yuanji
 * @version : MetaServiceCreateRequest.java, v 0.1 2022年05月05日 4:30 下午 yuanji Exp $
 */
@Data
public class MetaServiceCreateRequest {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务描述
     */
    private String serviceDesc;

    /**
     * 服务标识
     */
    private String serviceKey;

    /**
     * owner
     */
    private String owner;

    /**
     * 服务风险信息
     */
    private String riskInfo = "中风险";

    /**
     * 发布服务的平台.例如:appName
     */
    private String servicePlatform;

    /**
     * 变更实体类型
     */
    private String changeTargetType;

    /**
     * 管控调用方式
     */
    private String invokeModeEnum = "SYNC";

    /**
     * 前置校验是否同步，否则是异步
     */
    private boolean preCheckSync = false;

    /**
     * 后置校验是否同步，否则是异步
     */
    private boolean postCheckSync = false;

    /**
     * 权限等级
     */
    //@Param(title = "变更服务权限等级")
    private String permissionLevel = "PUBLIC";

    /**
     * 是否进行前置校验
     */
    private boolean preCheckEnable = false;

    /**
     * 前置变更校验超时时间，单位:ms
     */
    //@Param(title = "变更校验超时时间")
    private long checkTimeout = 1000;

    /**
     * 是否进行后置校验
     */
    private boolean postCheckEnable = false;

    /**
     * 后置变更校验超时时间，单位:ms
     */
    private long postCheckTimeout = 1000;

    /**
     * 租户码
     */
    private String tldcTenantCode;

}