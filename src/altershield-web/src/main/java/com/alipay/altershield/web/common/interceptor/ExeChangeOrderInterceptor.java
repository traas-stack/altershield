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
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.web.common.interceptor;

import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeExecOrderRequest;
import com.alipay.opscloud.framework.core.change.model.OpsCloudChangeContent;
import com.alipay.opscloud.framework.core.change.model.OpsCloudChangeTarget;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @author yuanji
 * @version : ExeChangeOrderInterceptor.java, v 0.1 2022年10月11日 15:28 yuanji Exp $
 */
@Configuration
@Aspect
@Order(10)
public class ExeChangeOrderInterceptor implements ApplicationContextAware {

    private static Validator validator;

    @Around("execution(* com.alipay.opscloud.web.openapi.change.v1..*.*(..))")
    public Object doReplace(ProceedingJoinPoint point) throws Throwable {

        if (point.getArgs().length == 1) {

            Object object = point.getArgs()[0];
            if (object instanceof OpsCloudChangeExecOrderRequest) {
                OpsCloudChangeExecOrderRequest request = (OpsCloudChangeExecOrderRequest) object;
                if (request.getChangeTargets() != null) {
                    validateChangeTarget(request.getChangeTargets());
                } else {
                    request.setChangeTargets(convert(request.getChangeContents()));
                }
            }
        }

        return point.proceed();
    }

    /**
     * 转换成changeTarget 兼容老版本
     * @param opsCloudChangeContents
     * @return
     */
    private OpsCloudChangeTarget[] convert(OpsCloudChangeContent[] opsCloudChangeContents) {

        return Arrays.stream(opsCloudChangeContents).filter(Objects::nonNull).map(
                opsCloudChangeContent -> new OpsCloudChangeTarget(opsCloudChangeContent.getContentType().getTypeName(),
                        opsCloudChangeContent.getInstanceName())).toArray(OpsCloudChangeTarget[]::new);

    }

    /**
     * 手动验证changeTarget的
     * @param opsCloudChangeTargets
     */
    private void validateChangeTarget(OpsCloudChangeTarget[] opsCloudChangeTargets) {
        if (opsCloudChangeTargets == null || opsCloudChangeTargets.length == 0) {
            throw new ValidationException("change targets is empty");
        }
        for (OpsCloudChangeTarget changeTarget : opsCloudChangeTargets) {
            Set<ConstraintViolation<OpsCloudChangeTarget>> violationSet = validator.validate(changeTarget);
            for (ConstraintViolation<OpsCloudChangeTarget> violation : violationSet) {
                throw new ValidationException(violation.getMessage());
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Validator validatorBean = applicationContext.getBean(Validator.class);
        setValidator(validatorBean);
    }

    private static void setValidator(Validator validatorBean) {
        if (validatorBean instanceof LocalValidatorFactoryBean) {
            validator = ((LocalValidatorFactoryBean) validatorBean).getValidator();
        } else if (validatorBean instanceof SpringValidatorAdapter) {
            validator = validatorBean.unwrap(Validator.class);
        } else {
            validator = validatorBean;
        }

    }
}