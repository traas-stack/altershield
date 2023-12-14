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
package com.alipay.altershield.common.cache;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.thread.MetaCacheRepositoryThreadLocal;
import com.alipay.altershield.common.util.MethodNameUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yuanji
 * @version : MetaCacheApsect.java, v 0.1 2022年04月12日 10:50 上午 yuanji Exp $
 */
@Aspect
@Component
@Configuration
public class MetaCacheAspect {

    @Autowired
    private MetaCacheRepository metaCacheRepository;

    protected static final Logger logger = Loggers.META_CACHE;

    @Around("@annotation(com.alipay.altershield.common.cache.MetaCache)")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {

        if (!AlterShieldConstant.SWITCH_META_CACHE) {
            return point.proceed();
        }
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return point.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        if (MetaCacheRepositoryThreadLocal.getUseCache()) {

            MetaCacheRepository.CacheRst cache = metaCacheRepository.getCache(methodSignature.getMethod(), point.getArgs());
            if (cache != null) {
                AlterShieldLoggerManager.log("info", logger, "interceptor.hitcache",
                        MethodNameUtil.getMethodNameFormat(methodSignature.getMethod()));
                return cache.getObj();
            }
            AlterShieldLoggerManager.log("info", logger, "interceptor.misscache",
                    MethodNameUtil.getMethodNameFormat(methodSignature.getMethod()));
        }
        Object rst = point.proceed();
        if (MetaCacheRepositoryThreadLocal.getUseCache()) {
            metaCacheRepository.putCache(methodSignature.getMethod(), point.getArgs(), rst);
        }
        return rst;
    }
}