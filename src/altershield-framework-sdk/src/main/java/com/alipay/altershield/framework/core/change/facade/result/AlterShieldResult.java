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


/**
 *
 * @author yuanji
 * @version : OpsCloudResult.java, v 0.1 2022年01月27日 4:58 下午 yuanji Exp $
 */
public class AlterShieldResult<T> {

    /** true for {@link AlterShieldResultCodeEnum#SUCCESS} */
    private boolean success = true;
    /** {@link AlterShieldResultCodeEnum} */
    private String resultCode = AlterShieldResultCodeEnum.SUCCESS.getCode();
    private String msg;

    /** jsn string */
    private T domain;

    /**
     * 参数错误。
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> illegalArgument(String message) {
        return new AlterShieldResult<T>(false, AlterShieldResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                message, null);
    }

    /**
     * 系统错误。
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> systemError(String message) {
        return new AlterShieldResult<T>(false, AlterShieldResultCodeEnum.SYSTEM_ERROR.getCode(),
                message, null);
    }

    /**
     * 不支持。
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> notSupport(String message) {
        return new AlterShieldResult<T>(false, AlterShieldResultCodeEnum.NOT_SUPPORTED.getCode(),
                message, null);
    }

    /**
     * 成功。
     *
     * @param message
     * @param domain
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> succeed(String message, T domain) {
        return new AlterShieldResult<T>(true, AlterShieldResultCodeEnum.SUCCESS.getCode(), message,
                domain);
    }

    /**
     */
    public AlterShieldResult() {
        super();
    }

    /**
     * for fail
     *
     * @param code
     * @param msg
     */
    public AlterShieldResult(AlterShieldResultCodeEnum code, String msg) {
        this(false, code.getCode(), msg, null);
    }

    /**
     * for success
     *
     * @param domain
     * @param msg
     */
    public AlterShieldResult(T domain, String msg) {
        this(true, AlterShieldResultCodeEnum.SUCCESS.getCode(), msg, domain);
    }

    /**
     *
     * @param success
     * @param resultCode
     * @param msg
     */
    public AlterShieldResult(boolean success, String resultCode, String msg) {
        this(success, resultCode, msg, null);
    }

    /**
     * @param success
     * @param resultCode
     * @param msg
     * @param domain
     */
    public AlterShieldResult(boolean success, String resultCode, String msg, T domain) {
        this();
        this.success = success;
        this.resultCode = resultCode;
        this.msg = msg;
        this.domain = domain;
    }

    /**
     * 检查结果是否成功
     *
     * @return 是否成功
     */
    public boolean checkSuccess() {
        return this.success && this.getDomain() != null;
    }

    /**
     * @param domain
     */
    public AlterShieldResult(T domain) {
        this(true, AlterShieldResultCodeEnum.SUCCESS.getCode(), null, domain);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "OpsCloudResult [succ=" + success + ":" + resultCode + ", " + msg + ", domain="
                + domain + "]";
    }

    /**
     * @return property value of {@link #success}
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success value to be assigned to property {@link #success}
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return property value of {@link #resultCode}
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode value to be assigned to property {@link #resultCode}
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return property value of {@link #msg}
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg value to be assigned to property {@link #msg}
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return property value of {@link #domain}
     */
    public T getDomain() {
        return domain;
    }

    /**
     * Setter method for property <tt>domain</tt>.
     *
     * @param domain value to be assigned to property domain
     */
    public void setDomain(T domain) {
        this.domain = domain;
    }
}
