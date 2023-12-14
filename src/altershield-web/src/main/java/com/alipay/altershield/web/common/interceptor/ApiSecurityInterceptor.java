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
package com.alipay.altershield.web.common.interceptor;

import com.alipay.altershield.change.meta.repository.MetaChangePlatformRepository;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.openapi.OpenApiRequestThreadLocal;
import com.alipay.altershield.framework.common.httpclient.HttpAlterShieldClient;
import com.alipay.altershield.framework.common.util.SignUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum;
import com.alipay.altershield.common.logger.Loggers;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 安全校验 AOP，对于请求进行鉴权
 *
 * @author yuanji
 * @version : ApiSecurityInterceptor.java, v 0.1 2022年04月11日 7:49 下午 yuanji Exp $
 */
@Configuration
@Aspect
@Order(4)
public class ApiSecurityInterceptor {

    @Autowired
    private MetaChangePlatformRepository metaChangePlatformRepository;

    private static final Logger logger = Loggers.FACADE_EXE;

    /**
     * Do check ops cloud result.
     *
     * @param point the point
     * @return the ops cloud result
     * @throws Throwable the throwable
     */
    @Around("execution(* com.alipay.altershield.web.openapi.change.v1..*.*(..))")
    public AlterShieldResult doCheck(ProceedingJoinPoint point) throws Throwable {

        AlterShieldLoggerManager.log("debug", logger, "start check authentication", point.getTarget(),point.getSignature().getName());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String platform = null;
        String signResult = null;
        long t = 0l;
        String content = null;
        try {
            platform = request.getHeader(HttpAlterShieldClient.H_PLATFORM);
            signResult = request.getHeader(HttpAlterShieldClient.H_PLATFORM_SIGN);
            String tString = request.getHeader(HttpAlterShieldClient.H_PLATFORM_TIMESTAMP);
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(signResult) || StringUtils.isBlank(tString) )
            {
                return new AlterShieldResult(AlterShieldResultCodeEnum.PERMISSION_DENIED, "authentication request fail");
            }
            //TODO 后期 增加重放校验
            t = Long.parseLong(tString);
            String token = metaChangePlatformRepository.getPlatformToken(platform);
            AlterShieldLoggerManager.log("debug", logger, "get token from platform=" + platform, token);

            content = getContent(request);
            boolean result = SignUtil.verifySign(t, content, signResult, token);
            if (AlterShieldConstant.SKIP_OPEN_API_AUTH) {
                result = true;
            }
            if (!result) {
                String msg = String.format("check authentication fail, platform=%s, signResult=%s, t =%d, content=%s", platform, signResult,
                        t,
                        content);
                AlterShieldLoggerManager.log("warn", logger, msg);
                return new AlterShieldResult(AlterShieldResultCodeEnum.PERMISSION_DENIED, "authentication request fail");
            }
        } catch (Exception e) {
            String msg = String.format("check authentication fail, platform=%s, signResult=%s, t =%d, content=%s", platform, signResult, t,
                    content);
            AlterShieldLoggerManager.log("error", logger, e, msg);
            return new AlterShieldResult<>(AlterShieldResultCodeEnum.PERMISSION_DENIED, "authentication request fail " + e.getMessage());
        }
        AlterShieldLoggerManager.log("debug", logger, "check authentication success", point.getTarget());
        OpenApiRequestThreadLocal.setPlatform(platform);
        try
        {
            return (AlterShieldResult) point.proceed();
        }
        finally {
            OpenApiRequestThreadLocal.clear();
        }
    }

    private String getContent(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), HttpAlterShieldClient.CHAR_SET));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            Enumeration<String> enumeration = request.getParameterNames();
            Map<String, String> params = new HashMap<>();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getParameter(key);
                params.put(key, value);
            }
            return SignUtil.sortMap(params);
        }
        return null;
    }
}