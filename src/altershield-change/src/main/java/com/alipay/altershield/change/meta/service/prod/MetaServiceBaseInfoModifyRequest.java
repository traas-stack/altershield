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
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.alipay.altershield.change.meta.service.prod;


/**
 * 元数据服务修改
 *
 * @author liuchuanghui
 * @version $Id: MetaServiceModifyRequest.java, v 0.1 2020年03月23日 2:34 PM liuchuanghui Exp $
 */
public class MetaServiceBaseInfoModifyRequest {

    /** 服务标识 */
    private String              serviceKey;

    /** owner */
    private String              owner;

    /**
     * 服务描述
     */
    private String              description;

    /** 是否进行前置校验 */
    private boolean             preCheckEnable     = false;

    /** 前置变更校验超时时间，单位:ms*/
    //@Param(title = "变更校验超时时间")
    private long                checkTimeout       = 1000;

    /** 是否进行后置校验 */
    private boolean             postCheckEnable    = false;

    /** 后置变更校验超时时间，单位:ms */
    private long                postCheckTimeout   = 1000;
    /**
     * 服务名
     */
    private String serviceName;

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPreCheckEnable() {
        return preCheckEnable;
    }

    public void setPreCheckEnable(boolean preCheckEnable) {
        this.preCheckEnable = preCheckEnable;
    }

    public long getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(long checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public boolean isPostCheckEnable() {
        return postCheckEnable;
    }

    public void setPostCheckEnable(boolean postCheckEnable) {
        this.postCheckEnable = postCheckEnable;
    }

    public long getPostCheckTimeout() {
        return postCheckTimeout;
    }

    public void setPostCheckTimeout(long postCheckTimeout) {
        this.postCheckTimeout = postCheckTimeout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}