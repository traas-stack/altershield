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
package com.alipay.altershield.framework.core.change.facade.request;


import javax.validation.constraints.Min;

/**
 * The type Meta change defence config request.
 *
 * @author yuanji
 * @version : MetaChangeDefenceConfigRequest.java, v 0.1 2022年03月17日 4:43 下午 yuanji Exp $
 */
public class MetaChangeDefenceConfigRequest {

    /**
     * Instantiates a new Meta change defence config request.
     *
     * @param enablePreCheck   the enable pre check
     * @param enablePostCheck  the enable post check
     * @param preCheckTimeout  the pre check timeout
     * @param postCheckTimeout the post check timeout
     */
    public MetaChangeDefenceConfigRequest(boolean enablePreCheck, boolean enablePostCheck,
                                          @Min(value = 0) long preCheckTimeout,
                                          @Min(value = 0) long postCheckTimeout) {
        this.enablePreCheck = enablePreCheck;
        this.enablePostCheck = enablePostCheck;
        this.preCheckTimeout = preCheckTimeout;
        this.postCheckTimeout = postCheckTimeout;
    }

    /**
     * Instantiates a new Meta change defence config request.
     */
    public MetaChangeDefenceConfigRequest() {
    }

    /**
     * 是否开启前置检查。
     */
    private boolean enablePreCheck = true;

    /**
     * 是否开启后置检查
     */
    private boolean enablePostCheck = true;

    /**
     * 前置检查超时时间,单位:ms
     */
    @Min(value = 0)
    private long preCheckTimeout = 0;

    /**
     * 后置检查超时时间,单位ms
     */
    @Min(value = 0)
    private long postCheckTimeout = 0;

    /**
     * Is enable pre check boolean.
     *
     * @return the boolean
     */
    public boolean isEnablePreCheck() {
        return enablePreCheck;
    }

    /**
     * Sets enable pre check.
     *
     * @param enablePreCheck the enable pre check
     */
    public void setEnablePreCheck(boolean enablePreCheck) {
        this.enablePreCheck = enablePreCheck;
    }

    /**
     * Is enable post check boolean.
     *
     * @return the boolean
     */
    public boolean isEnablePostCheck() {
        return enablePostCheck;
    }

    /**
     * Sets enable post check.
     *
     * @param enablePostCheck the enable post check
     */
    public void setEnablePostCheck(boolean enablePostCheck) {
        this.enablePostCheck = enablePostCheck;
    }

    /**
     * Gets pre check timeout.
     *
     * @return the pre check timeout
     */
    public long getPreCheckTimeout() {
        return preCheckTimeout;
    }

    /**
     * Sets pre check timeout.
     *
     * @param preCheckTimeout the pre check timeout
     */
    public void setPreCheckTimeout(long preCheckTimeout) {
        this.preCheckTimeout = preCheckTimeout;
    }

    /**
     * Gets post check timeout.
     *
     * @return the post check timeout
     */
    public long getPostCheckTimeout() {
        return postCheckTimeout;
    }

    /**
     * Sets post check timeout.
     *
     * @param postCheckTimeout the post check timeout
     */
    public void setPostCheckTimeout(long postCheckTimeout) {
        this.postCheckTimeout = postCheckTimeout;
    }

    @Override
    public String toString() {
        return "MetaChangeDefenceConfigRequest{" +
                "enablePreCheck=" + enablePreCheck +
                ", enablePostCheck=" + enablePostCheck +
                ", preCheckTimeout=" + preCheckTimeout +
                ", postCheckTimeout=" + postCheckTimeout +
                '}';
    }
}