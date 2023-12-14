/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.pluginmarket.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring container management tool class
 * @author xiangyue
 * @version : SpringUtils.java, v 0.1 2022-10-09 17:42 xiangyue Exp $$
 */
@Component
public final class SpringUtils implements ApplicationContextAware {
    /** Spring application context */
    private static ApplicationContext applicationContext;

    /**
     * Get an object of type requiredType
     *
     * @param clz
     * @return
     * @throws BeansException
     *
     */
    public static <T> T getBean(Class<T> clz) throws BeansException
    {
        T result = (T) applicationContext.getBean(clz);
        return result;
    }

    public static <T> T getBean(String beanName, Class<T> clz) throws BeansException
    {
        T result = (T) applicationContext.getBean(beanName, clz);
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
}
