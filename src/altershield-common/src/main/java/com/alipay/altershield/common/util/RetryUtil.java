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
package com.alipay.altershield.common.util;

import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.common.constant.AlterShieldConstant;

import java.util.function.Supplier;

/**
 *
 * @author liushengdong
 * @version $Id: RetryUtil.java, v 0.1 2018年12月04日 11:18 PM liushengdong Exp $
 */
public class RetryUtil {

    /**
     * 计算重试间隔,会按照已经重试次数,先指数自增，然后线性自增。
     *
     * @param retried 已经重试的次数
     * @return 重试间隔
     */
    public static long retryInterval(int retried) {
        final long firstRetryInterval = AlterShieldConstant.SCHEDULE_POINT_FIRST_RETRY_INTERVAL;
        final int maxRetryThreshold = AlterShieldConstant.SCHEDULE_POINT_MAX_RETRY_THRESHOLD;
        int exponent = retried;
        int linear = 0;
        if (retried > maxRetryThreshold) {
            exponent = maxRetryThreshold;
            linear = retried - exponent;
        }

        long exponentTime = (long) (firstRetryInterval * Math.pow(2, exponent));
        return exponentTime + exponentTime * linear;
    }

    /**
     * 线性梯度下降的重试间隔计算，到10分钟后，就不再增加间隔。
     *
     * @param retried 已经重试的次数
     * @return 重试间隔
     */
    public static long linearDescentRetryInterval(long retried) {
        final long firstRetryInterval = AlterShieldConstant.SCHEDULE_POINT_FIXED_RETRY_FIRST_INTERVAL;
        final long maxRetryInterval = AlterShieldConstant.SCHEDULE_POINT_FIXED_RETRY_MAX_INTERVAL;
        long time = firstRetryInterval * (retried / 10 + 1);
        return time > maxRetryInterval ? maxRetryInterval : time;
    }

    /**
     * 如果func失败,则重试N次
     * @param <T>
     * @param func 调用的动作
     * @param retryTime 重试次数
     * @param errorCode
     * @return
     */
    public static <T> T executeWithRetry(Supplier<T> func, int retryTime,
                                         AlterShieldInternalErrorCode errorCode) {
        retryTime = retryTime <= 0 ? 1 : retryTime;
        Throwable t = null;
        int i = 0;
        for (; i < retryTime; ++i) {
            try {
                return func.get();
            } catch (Throwable e) {
                t = e;
            }
        }
        if (t instanceof AlterShieldInternalException) {
            AlterShieldInternalException t1 = (AlterShieldInternalException) t;
            throw new AlterShieldInternalException(t1.getErrorCode(), "queryError, retryTime=" + i + ". " + t1.getMessage(), t1.getCause());
        }

        throw new AlterShieldInternalException(errorCode,
                "queryError, retryTime=" + i + ". " + t.getMessage(), t);
    }
}