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
package com.alipay.altershield.common.logger.intercept;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.intercept.DefaultSensitiveContentHandler;
import com.alipay.altershield.common.intercept.SensitiveContentHandler;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.thread.ExecutionThreadLocal;
import com.alipay.altershield.common.util.ConstantsUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.common.validate.JsonFormat;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 统一日志拦截器
 *
 * @author lijunjie
 * @version $Id: LogInterceptor.java, v 0.1 2014年11月7日 下午5:16:44 lijunjie Exp $
 */
@Configuration
@Aspect
public class AlterShieldLogInterceptor implements InitializingBean {

    /**
     * 日志工具
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlterShieldLogInterceptor.class);

    @Autowired(required = false)
    private SensitiveContentHandler sensitiveContentHandler;


    @Around("@annotation(com.alipay.altershield.common.logger.intercept.AlterShieldLogAnnotation)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {

        if (!(point.getSignature() instanceof MethodSignature)) {
            return point.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        AlterShieldLoggerManager.log("debug",LOGGER, "start intercept log invoke", methodSignature);
        final String key =  point.getTarget().getClass().getSimpleName() + "." + point.getSignature().getName();
        boolean resetFlag = false;
        if(Profiler.getEntry()  == null)
        {
            Profiler.start(key);
            resetFlag = true;
        }
        else
        {
            Profiler.enter(key);
        }


        // 准备trace等信息到ExecutionThreadLocal全局可用。
        ExecutionThreadLocal.ExecutionThreadLocalObj oldThreadLocalObj = ExecutionThreadLocal.get();
        prepareTraceContext(point);

        Object result = null;
        try {
            //定于摘要日志和详细日志枚举
            DigestLogTypeEnum digestlogType = null;
            DetailLogTypeEnum detailLogType = null;

            //String simpleClassName = methodSignature.getMethod().getDeclaringClass().getSimpleName();

            //String methodName = simpleClassName + LogConstants.DOT + methodSignature.getMethod().getName();

            //获取接口方法
            Method interfaceMethod = methodSignature.getMethod();

            //获取实现方法
            Method implementMethod = point.getThis().getClass()
                    .getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());

            //获取被tdk代理的方法
            Method proxyMethod = null;
            if (AopUtils.isJdkDynamicProxy(point.getThis())) {
                proxyMethod = getJdkDynamicProxyTargetObject(point.getThis()).getClass().getMethod(interfaceMethod.getName(),
                        interfaceMethod.getParameterTypes());
            }

            //对@LogAnnotation标记的方法进行统一解析，打印自定义的日志；优先判断是否在接口声明出标记@LogAnnotation
            if (interfaceMethod.isAnnotationPresent(AlterShieldLogAnnotation.class)) {
                result = logAnnotationParser(point, interfaceMethod, result, digestlogType,
                        detailLogType);
            } else if (implementMethod.isAnnotationPresent(AlterShieldLogAnnotation.class)) {
                result = logAnnotationParser(point, implementMethod, result, digestlogType,
                        detailLogType);
            } else if (proxyMethod != null && proxyMethod.isAnnotationPresent(AlterShieldLogAnnotation.class)) {
                result = logAnnotationParser(point, proxyMethod, result, digestlogType,
                        detailLogType);
            } else {
                //对于没有日志注解的，进行正常处理
                result = point.proceed();
            }

        } finally {
            // 清空线程缓存
            ExecutionThreadLocal.set(oldThreadLocalObj);
            Profiler.release();
            if (resetFlag) {
                try {
                        final Long duration = Profiler.getDuration();
                        if (duration > AlterShieldConstant.PROFILER_THRESHOLD) {
                            final String detail = Profiler.dump("Detail: ", "           ");
                            String log = String.format("调用服务：%s的方法%s耗时：%dms，超过预期\n%s\n",new Object[] {
                                    point.getTarget().getClass().getSimpleName(),
                                    point.getSignature().getName(), duration, detail});
                           AlterShieldLoggerManager.log("warn", Loggers.SERVICE_PROFILER, log);
                        }
                } finally {
                    Profiler.reset();
                }
            }

        }
        AlterShieldLoggerManager.log("debug",LOGGER, "intercept log invoke finish", methodSignature);
        //返回拦截器处理结果
        return result;
    }

    /*
     * 从java动态代理中获取原始类
     *
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            AopProxy aopProxy = (AopProxy) h.get(proxy);
            Field advised = aopProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
            return target;
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", LOGGER, "get JDK dynamic proxy target object:", e);
            return proxy;
        }
    }

    /**
     * <p>日志注解解析器：对@LogAnnotation标记的方法进行统一解析</p>
     *
     * @param point         ProceedingJoinPoint
     * @param method        方法
     * @param result        执行结果
     * @param digestlogType 摘要日志类型枚举类
     * @param detailLogType 详情日志类型枚举
     * @return 日志信息对象
     * @throws Throwable 异常
     */
    private Object logAnnotationParser(ProceedingJoinPoint point, Method method, Object result,
                                       DigestLogTypeEnum digestlogType,
                                       DetailLogTypeEnum detailLogType) throws Throwable {
        LogInfo logInfo = null;
        try {
            AlterShieldLogAnnotation annotated = method.getAnnotation(AlterShieldLogAnnotation.class);
            digestlogType = annotated.digestLogType();
            detailLogType = annotated.detailLogType();
            logInfo = before(point, method, LOGGER, annotated.serviceDesc(),
                    annotated.keyArg(), annotated.keyProps(), annotated.extProps());

        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", LOGGER, "before invoke:", e);
        }

        try {

            try {
                result = point.proceed();
            } catch (Throwable e) {
                logInfo.setExceptional(true);
                throw e;
            }

        } finally {
            //日志后置处理：集中打印摘要日志和详细日志
            try {
                after(result, logInfo, LOGGER, digestlogType, detailLogType);
            } catch (Exception e) {
                AlterShieldLoggerManager.log("error", LOGGER, "after 2 invoke", e);
            }
        }
        return result;
    }

    private void prepareTraceContext(ProceedingJoinPoint point) {
        try {
            ExecutionThreadLocal.set(new ExecutionThreadLocal.ExecutionThreadLocalObj());
            Object[] arguments = point.getArgs();
            //TODO 获取trace信息

            for (Object arg : arguments) {
                // 提取成功一次后就break掉。
                if (extractTrace(arg)) {
                    break;
                }
            }
        } catch (Throwable t) {
            // 抓住任何异常，不能影响主流程
            AlterShieldLoggerManager.log("error", LOGGER, "Failed to prepare trace context.", t);
        }
    }

    private boolean extractTrace(Object arg) { // NOPMD
        // TODO
        return false;
    }

    private void putTraceToContext(OpsChngTrace trace) {
        if (trace != null) {
            ExecutionThreadLocal.get().setTraceId(trace.getTraceId());
            ExecutionThreadLocal.get().setCoord(trace.getCoordStr());
        }
    }

    private void putTraceToContext(String traceId, String coorationId) {
        ExecutionThreadLocal.get().setTraceId(traceId);
        ExecutionThreadLocal.get().setCoord(coorationId);
    }

    /**
     * 方法执行前操作
     *
     * @param joinPoint   方法执行体
     * @param method      方法
     * @param logger      日志logger
     * @param serviceDesc 服务描述
     * @param keyArg      参数
     * @param keyProps    参数数据
     * @param extProps    扩展数据
     * @return 日志信息
     */
    private LogInfo before(ProceedingJoinPoint joinPoint, Method method, Logger logger,
                           String serviceDesc, short keyArg, String keyProps, String extProps) {

        LogInfo logInfo = new LogInfo();
        logInfo.setClassName(method.getDeclaringClass().getSimpleName());
        logInfo.setMethodName(method.getName());
        logInfo.setServiceDesc(serviceDesc);

        //打印paramIn输入参数信息
        logInfo.setArgs(joinPoint.getArgs());
        //打印开始时间
        logInfo.setStartTime(System.currentTimeMillis());
        logInfo.setExceptional(false);

        //组装固定信息
        assembleFixInfo(logger, logInfo);

        //根据keyArg解析keyProps和extProps
        if (joinPoint.getArgs().length > 0 && keyArg >= 0
                && keyArg < joinPoint.getArgs().length) {

            if (StringUtils.isBlank(keyProps) && StringUtils.isBlank(extProps)) {
                LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
                String[] parameterNames = u.getParameterNames(method);
                String paramName = parameterNames[keyArg];
                Parameter parameter = method.getParameters()[keyArg];
                Class<?> parameterType = parameter.getType();
                boolean basicType = ConstantsUtil.isBasicType(parameterType);
                if (basicType) {
                    Object argument = joinPoint.getArgs()[keyArg];
                    logInfo.setKeyProps(paramName + "=" + argument);
                    return logInfo;
                }
            }

            //组装keyProps
            if (StringUtils.isNotBlank(keyProps)) {
                assembleKeyProps(joinPoint, logger, keyArg, keyProps, logInfo);
            }
            //组装extProps
            if (StringUtils.isNotBlank(extProps)) {
                assembleExtProps(joinPoint, logger, keyArg, extProps, logInfo);
            }
        }
        return logInfo;
    }

    protected void assembleFixInfo(Logger logger, LogInfo logInfo) {

    }

    /**
     * 方法执行后操作
     *
     * @param result        结果
     * @param logInfo       日志信息
     * @param logger        日志logger
     * @param digestLogType 摘要日志类型
     * @param detailLogType 详情日志类型
     */
    protected void after(Object result, LogInfo logInfo, Logger logger,
                         DigestLogTypeEnum digestLogType, DetailLogTypeEnum detailLogType) {
        logInfo.setResult(result);
        logInfo.setEndTime(System.currentTimeMillis());
        try {

            writeDigest(digestLogType.getLogger(), logInfo);

            writeDetail(detailLogType.getLogger(), logInfo);

        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", logger, "after invoke:", e);
        }
    }

    /**
     * <p>打印详细日志</p>
     *
     * @param logger  日志处理器
     * @param logInfo 日志信息
     */
    private void writeDetail(Logger logger, LogInfo logInfo) {
        AlterShieldLoggerManager.log("info", logger, logInfo.getParams(sensitiveContentHandler));
    }

    /**
     * <p>打印服务摘要日志</p>
     *
     * @param logger  日志处理器
     * @param logInfo 日志信息
     */
    private void writeDigest(Logger logger, LogInfo logInfo) {
        AlterShieldLoggerManager.log("info", logger, logInfo.printDigestLog());
    }

    /**
     * <p>组装业务关键字段keyProps</p>
     *
     * @param joinPoint ProceedingJoinPoint
     * @param logger    Logger
     * @param keyArg    关键业务参数，用于获取方法的第几个参数
     * @param keyProps  关键业务属性，用于Monitor业务统计
     * @param logInfo   日志信息对象
     */
    private void assembleKeyProps(ProceedingJoinPoint joinPoint, Logger logger, short keyArg,
                                  String keyProps, LogInfo logInfo) {
        String[] keyPropNames = StringUtils.split(keyProps, LogConstants.VRTL);
        for (int i = 0; i < keyPropNames.length; i++) {
            String keyPropName = keyPropNames[i];
            try {

                final Object argument = joinPoint.getArgs()[keyArg];
                if (null == argument) {
                    logInfo.setKeyProps(String.format("日志打印获取参数异常！！第[%s]个参数为null", keyArg));
                    break;
                }
                Object keyPropsResult = null;
                if(isSimpleType(argument))
                {
                    keyPropsResult = argument;
                }
                else
                {
                    //得到javabean中的get方法
                    Method getter = argument.getClass().getMethod(
                            LogConstants.PRE_GETTER + keyPropName.substring(0, 1).toUpperCase()
                                    + keyPropName.substring(1));
                    //调用get方法获取值
                     keyPropsResult = getter.invoke(argument);

                    //敏感数据,脱敏
                    try {
                        Field field = getField(argument.getClass(), keyPropName);
                        if (field != null && field.getAnnotation(JsonFormat.class) != null) {
                            keyPropsResult = sensitiveContentHandler.replaceJsonStr((String) keyPropsResult);
                        }
                    } catch (Exception e) {
                        AlterShieldLoggerManager.log("warn", logger, "日志打印获取keyPropName=" + keyPropName + "值异常，请检查声明", e);
                    }
                }


                if (i == 0) {
                    //如果只有一个值，就不用拼接
                    logInfo.setKeyProps(keyPropName + "=" + keyPropsResult);
                } else {
                    //以","分隔符进行拼接
                    logInfo.setKeyProps(logInfo.getKeyProps() + LogConstants.COMMA + keyPropName
                            + "=" + keyPropsResult);
                }
            } catch (Exception e) {
                AlterShieldLoggerManager.log("warn", logger, "日志打印获取keyPropName=" + keyPropName + "值异常，请检查声明", e);
            }
        }
    }

    private static boolean isSimpleType(Object obj)
    {
        if(obj.getClass().isPrimitive()){
            return true;
        }

        if (obj instanceof String || obj instanceof Integer || obj instanceof Double || obj instanceof Long || obj instanceof Short
                || obj instanceof Byte || obj instanceof Float  || obj instanceof Boolean
                || obj instanceof Character) {
            return true;
        }
        return false;
    }

    /**
     * 循环获取值
     * @param clzz
     * @param filedName
     * @return
     */
    private Field getField(Class clzz, String filedName)
    {
        if(clzz == null)
        {
            return null;
        }
        Field[] declaredFields = clzz.getDeclaredFields();
        if(declaredFields == null)
        {
            return null;
        }
        for (Field f : declaredFields)
        {
            if(StringUtils.equals(f.getName(), filedName))
            {
                return f;
            }
        }
        return getField( clzz.getSuperclass(), filedName);
    }

    /**
     * <p>组装业务关键字段extProps</p>
     *
     * @param joinPoint ProceedingJoinPoint
     * @param logger    Logger
     * @param keyArg    关键业务参数，用于获取方法的第几个参数
     * @param extProps  扩展数据
     * @param logInfo   日志信息对象
     */
    private void assembleExtProps(ProceedingJoinPoint joinPoint, Logger logger, short keyArg,
                                  String extProps, LogInfo logInfo) {
        String[] extPropsNames = StringUtils.split(extProps, LogConstants.VRTL);
        for (int i = 0; i < extPropsNames.length; i++) {
            String extPropName = extPropsNames[i];
            try {
                final Object argument = joinPoint.getArgs()[keyArg];
                if (null == argument) {
                    logInfo.setExtProps(String.format("日志打印获取参数异常！！第[%s]个参数为null", keyArg));
                    break;
                }

                Object extPropResult = null;
                if(isSimpleType(argument))
                {
                    extPropResult = argument;
                }
                else
                {
                    //得到javabean中的get方法
                    Method getter = argument.getClass().getMethod(
                            LogConstants.PRE_GETTER + extPropName.substring(0, 1).toUpperCase()
                                    + extPropName.substring(1));
                    //调用get方法获取值
                    extPropResult = getter.invoke(argument);

                    //敏感数据,脱敏
                    try {
                        Field field = getField(argument.getClass(), extPropName);
                        if (field != null && field.getAnnotation(JsonFormat.class) != null) {
                            extPropResult = sensitiveContentHandler.replaceJsonStr((String) extPropResult);
                        }
                    } catch (Exception e) {
                        AlterShieldLoggerManager.log("warn", logger, "日志打印获取extPropName=" + extPropName + "值异常，请检查声明", e);
                    }
                }


                if (i == 0) {
                    //如果只有一个值，就不用拼接
                    logInfo.setExtProps(extPropName + "=" + extPropResult);
                } else {
                    //以","分隔符进行拼接
                    logInfo.setExtProps(logInfo.getExtProps() + LogConstants.COMMA + extPropName
                            + "=" + extPropResult);
                }
            } catch (Exception e) {
                AlterShieldLoggerManager.log("warn", logger, "日志打印获取extPropName=" + extPropName + "值异常，请检查声明", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sensitiveContentHandler == null) {
            sensitiveContentHandler = new DefaultSensitiveContentHandler();
        }
    }
}
