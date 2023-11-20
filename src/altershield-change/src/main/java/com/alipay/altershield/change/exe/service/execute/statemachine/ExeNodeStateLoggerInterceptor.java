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
package com.alipay.altershield.change.exe.service.execute.statemachine;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author yuanji
 * @version : ExeNodeStateLoggerInterceptor.java, v 0.1 2022年10月25日 15:44 yuanji Exp $
 */
@Configuration
@Aspect
@Order(1)
public class ExeNodeStateLoggerInterceptor {

    private static final Logger logger = Loggers.EXE_STATE_MACHINE;

    /**
     * Do check ops cloud result.
     *
     * @param point the point
     * @return the ops cloud result
     * @throws Throwable the throwable
     */
    @Around("execution(public * com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine+.set*(..)) || execution"
            + "(public * com.alipay.altershield.change.exe.service.execute.statemachine.ExeNodeStateMachine+.submit*(..))")
    public Object execute(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return point.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        if (point.getArgs().length == 0 || !(point.getArgs()[0] instanceof ExeNodeEntity)) {
            return point.proceed();
        }
        ExeNodeEntity entity = (ExeNodeEntity) point.getArgs()[0];
        ExeNodeStateEnum from = entity.getStatus();
        String msg = "current:%s.(%s)-(%s) -> %s";

        try {
            Object object = point.proceed();
            if (entity != null) {
                String result = formatMsg(msg, from, methodSignature, entity);
                AlterShieldLoggerManager.log("info", logger, "exeState.transfer.success", entity.getNodeExeId(), result);
            }
            return object;
        } catch (Exception e) {
            String result =  formatMsg(msg, from, methodSignature, entity);;
            if (entity != null) {
                AlterShieldLoggerManager.log("error", logger, e, "exeState.transfer.fail", entity.getNodeExeId(), result);
            } else {
                AlterShieldLoggerManager.log("error", logger, e, "exeState.transfer.fail, entity is null", result);
            }
            throw e;
        }
    }

    private static String formatMsg(String msg,  ExeNodeStateEnum from, MethodSignature methodSignature,ExeNodeEntity to)
    {
        String methodName = methodSignature.getName();
        Class<?> declaringClass = methodSignature.getMethod().getDeclaringClass();
        ExeNodeStateEnum transferStatus = to != null ? to.getStatus() : null;
        return String.format(msg, from, declaringClass, methodName, transferStatus);

    }

}