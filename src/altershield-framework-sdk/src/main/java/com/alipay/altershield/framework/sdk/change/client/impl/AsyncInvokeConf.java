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
package com.alipay.altershield.framework.sdk.change.client.impl;

/**
 * 跨洲异步化相关配置.
 * platformAsync是总开关。如果这个等于false则完全关闭异步化。如果这个等于true，则genTrace/precheck/finishCheck可以分别打开和关闭。这三个配置如果是null则
 * 默认按照总开关的状态决定是否异步化。
 *
 * @author lex
 * @version $Id: AsyncInvokeConf.java, v 0.1 2019年08月02日 下午5:57 lex Exp $
 */
public class AsyncInvokeConf {

    /** 是否整个平台的三种接口：genTrace/precheck/finishCheck都异步化/本地化 */
    private boolean platformAsync = false;

    /** 是否使用本地的假Trace */
    private Boolean genTraceLocal = null;

    /** precheck接口异步化 */
    private Boolean precheckAsync = null;

    /** postcheck接口异步化 */
    private Boolean finishCheckAsync = null;

    private int maxThreadPoolSize = 5;

    /**
     * Getter method for property <tt>platformAsync</tt>.
     *
     * @return property value of platformAsync
     */
    public boolean isPlatformAsync() {
        return platformAsync;
    }

    /**
     * Setter method for property <tt>platformAsync</tt>.
     *
     * @param platformAsync value to be assigned to property platformAsync
     */
    public void setPlatformAsync(boolean platformAsync) {
        this.platformAsync = platformAsync;
    }

    /**
     * Getter method for property <tt>genTraceLocal</tt>.
     *
     * @return property value of genTraceLocal
     */
    public Boolean getGenTraceLocal() {
        return genTraceLocal;
    }

    /**
     * Setter method for property <tt>genTraceLocal</tt>.
     *
     * @param genTraceLocal value to be assigned to property genTraceLocal
     */
    public void setGenTraceLocal(Boolean genTraceLocal) {
        this.genTraceLocal = genTraceLocal;
    }

    /**
     * Getter method for property <tt>precheckAsync</tt>.
     *
     * @return property value of precheckAsync
     */
    public Boolean getPrecheckAsync() {
        return precheckAsync;
    }

    /**
     * Setter method for property <tt>precheckAsync</tt>.
     *
     * @param precheckAsync value to be assigned to property precheckAsync
     */
    public void setPrecheckAsync(Boolean precheckAsync) {
        this.precheckAsync = precheckAsync;
    }

    /**
     * Getter method for property <tt>finishCheckAsync</tt>.
     *
     * @return property value of finishCheckAsync
     */
    public Boolean getFinishCheckAsync() {
        return finishCheckAsync;
    }

    /**
     * Setter method for property <tt>finishCheckAsync</tt>.
     *
     * @param finishCheckAsync value to be assigned to property finishCheckAsync
     */
    public void setFinishCheckAsync(Boolean finishCheckAsync) {
        this.finishCheckAsync = finishCheckAsync;
    }

    /**
     * Getter method for property <tt>maxThreadPoolSize</tt>.
     *
     * @return property value of maxThreadPoolSize
     */
    public int getMaxThreadPoolSize() {
        return maxThreadPoolSize;
    }

    /**
     * Setter method for property <tt>maxThreadPoolSize</tt>.
     *
     * @param maxThreadPoolSize value to be assigned to property maxThreadPoolSize
     */
    public void setMaxThreadPoolSize(int maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AsyncInvokeConf{");
        sb.append("platformAsync=").append(platformAsync);
        sb.append(", genTraceLocal=").append(genTraceLocal);
        sb.append(", precheckAsync=").append(precheckAsync);
        sb.append(", finishCheckAsync=").append(finishCheckAsync);
        sb.append(", maxThreadPoolSize=").append(maxThreadPoolSize);
        sb.append('}');
        return sb.toString();
    }
}
