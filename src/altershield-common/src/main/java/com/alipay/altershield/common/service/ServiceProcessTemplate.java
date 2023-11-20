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
package com.alipay.altershield.common.service;


import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.thread.ExecutionThreadLocal;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum;
import com.alipay.altershield.framework.core.util.ExceptionUtils;
import org.slf4j.Logger;


/**
 * @author shuo.qius
 * @version Aug 15, 2018
 */
public class ServiceProcessTemplate {

    private static Logger logger = Loggers.BIZ_SERVICE;

    /**
     * @param processor
     * @return same object as passed
     */
    public static <T> AlterShieldResult<T> process(ServiceProcessor<T> processor) {
        return wrapTryCatch(() -> new AlterShieldResult<>(processor.process()), false);
    }

    /**
     * 对Facade层的方法进行Try-Catch。
     *
     * @param processor
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> wrapTryCatch(ServiceProcessor<AlterShieldResult<T>> processor) {
        return wrapTryCatch(processor, false);
    }

    /**
     * 抓住所有异常并包装成标准返回类。
     *
     * @param processor
     * @param wrapThreadLocal 如果为true则会对线程变量进行替换，避免线程变量串线程造成问题
     * @param <T>
     * @return
     */
    public static <T> AlterShieldResult<T> wrapTryCatch(ServiceProcessor<AlterShieldResult<T>> processor,
                                                        boolean wrapThreadLocal) {
        final ExecutionThreadLocal.ExecutionThreadLocalObj localObj = ExecutionThreadLocal.get();
        if (wrapThreadLocal) {
            ExecutionThreadLocal.set(new ExecutionThreadLocal.ExecutionThreadLocalObj());
        }

        AlterShieldResult<T> result = new AlterShieldResult<>();
        result.setSuccess(true);
        result.setResultCode(AlterShieldResultCodeEnum.SUCCESS.getCode());
        try {
            return processor.process();
        } catch (FacadeProcessTemplatePlainException ftpe) {
            result.setSuccess(false);
            result.setMsg(ftpe.getMsg());
            result.setResultCode(ftpe.getCode());
        } catch (IllegalArgumentException ae) {
            AlterShieldLoggerManager.log("error", logger, ae);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
            result.setResultCode(AlterShieldResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        } catch (AlterShieldInternalException e) {
            result.setSuccess(false);
            result.setMsg(e.getErrorCode().getCode() + ":" + e.getMessage());
            switch (e.getErrorCode()) {
                case COMMON_ILLEGAL_ARGUMENT:
                    result.setResultCode(AlterShieldResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
                    break;
                case PERMISSION_DENIED:
                    result.setResultCode(AlterShieldResultCodeEnum.PERMISSION_DENIED.getCode());
                    break;
                case CHANGE_SERVICE_NOT_SUPPORT:
                case NOT_SUPPORTED:
                    result.setResultCode(AlterShieldResultCodeEnum.NOT_SUPPORTED.getCode());
                    break;
                default:
                    result.setResultCode(AlterShieldResultCodeEnum.SYSTEM_ERROR.getCode());
                    break;
            }
            AlterShieldLoggerManager.log("error", logger, e, result);
        } catch (Throwable t) {
            result.setSuccess(false);
            result.setResultCode(AlterShieldInternalErrorCode.SYSTEM_ERROR.getCode());
            result.setMsg(ExceptionUtils.getErrorMessage(t));
            AlterShieldLoggerManager.log("error", logger, t, result);
        } finally {
            if (wrapThreadLocal) {
                ExecutionThreadLocal.set(localObj);
            }
        }
        return result;
    }

    /**
     * @author shuo.qius
     * @version Apr 30, 2020
     */
    public static class FacadeProcessTemplatePlainException extends RuntimeException {
        private static final long serialVersionUID = 7912224268109413755L;
        private String            code;
        private String            msg;

        public FacadeProcessTemplatePlainException(String code, String msg) {
            super();
            this.code = code;
            this.msg = msg;
        }

        /**
         * @return property value of {@link #code}
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code value to be assigned to property {@link #code}
         */
        public void setCode(String code) {
            this.code = code;
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

    }

}
