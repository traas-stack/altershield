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
package com.alipay.altershield.change.meta.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 变更防御配置元信息
 * @author yuanji
 * @version : OpsCloudMetaChangeDefenceConfig.java, v 0.1 2022年02月09日 5:26 下午 yuanji Exp $
 */
@Getter
@Setter
public class MetaChangeDefenceConfigEntity {

    /**
     * 是否开启前置检查。
     */
    private boolean enablePreCheck = true;

    /**
     * 是否开启后置检查
     */
    private boolean enablePostCheck = true;

    /**
     * 前置检查超时时间
     */
    private long preCheckTimeout = 1000;

    /**
     * 后置检查超时时间
     */
    private long postCheckTimeout = 1000;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MetaChangeDefenceConfigEntity{");
        sb.append("enablePreCheck=").append(enablePreCheck);
        sb.append(", enablePostCheck=").append(enablePostCheck);
        sb.append(", preCheckTimeout=").append(preCheckTimeout);
        sb.append(", postCheckTimeout=").append(postCheckTimeout);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof MetaChangeDefenceConfigEntity)) { return false; }
        MetaChangeDefenceConfigEntity that = (MetaChangeDefenceConfigEntity) o;
        return enablePreCheck == that.enablePreCheck &&
                enablePostCheck == that.enablePostCheck &&
                preCheckTimeout == that.preCheckTimeout &&
                postCheckTimeout == that.postCheckTimeout;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enablePreCheck, enablePostCheck, preCheckTimeout, postCheckTimeout);
    }
}