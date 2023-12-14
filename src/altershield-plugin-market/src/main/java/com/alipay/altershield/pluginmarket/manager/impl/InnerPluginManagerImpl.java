/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.pluginmarket.manager.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.pluginmarket.PluginMarketImpl;
import com.alipay.altershield.pluginmarket.manager.InnerPluginManager;
import com.alipay.altershield.pluginmarket.util.SpringUtils;
import com.alipay.altershield.shared.pluginmarket.annotations.PluginAutowired;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author xiangyue
 * @version : InnerPluginManagerImpl.java, v 0.1 2022-11-24 13:51 xiangyue Exp $$
 */
@Service
public class InnerPluginManagerImpl implements InnerPluginManager {

    private static final Logger logger = Loggers.PLUGIN_MARKET;

    @Override
    public <PluginType, ResultType> AlterShieldResult<ResultType> executePlugin(String pluginKey, String mainClass, Function<PluginType, ResultType> invoker) {
        if (StringUtils.isBlank(pluginKey)) {
            AlterShieldLoggerManager.log("error", Loggers.PLUGIN_MARKET, "PluginMarketImpl", "executePlugin",
                    "plugin key cannot be empty", pluginKey);
            return AlterShieldResult.illegalArgument("invalid plugin key");
        }

        if (StringUtils.isBlank(mainClass)) {
            AlterShieldLoggerManager.log("error", Loggers.PLUGIN_MARKET, "PluginMarketImpl", "executePlugin",
                    "main class cannot be empty", pluginKey);
            return AlterShieldResult.illegalArgument("invalid main class");
        }

        ClassLoader currentClassLoader = PluginMarketImpl.class.getClassLoader();
        try {
            Class<?> pluginClazz = currentClassLoader.loadClass(mainClass);
            PluginType pluginObj = (PluginType) pluginClazz.newInstance();
            injectDependency(pluginObj);
            ResultType result = invoker.apply(pluginObj);
            return new AlterShieldResult<>(result);
        } catch (ClassNotFoundException e) {
            AlterShieldLoggerManager.log("error", Loggers.PLUGIN_MARKET, "PluginMarketImpl", "executePlugin",
                    "load inner plugin error", pluginKey, mainClass);
            return AlterShieldResult.systemError("load inner plugin error");
        } catch (Throwable t) {
            AlterShieldLoggerManager.log("error", Loggers.PLUGIN_MARKET, "PluginMarketImpl", "executePlugin",
                    "execute inner plugin got an exception", pluginKey, mainClass, t);
            return AlterShieldResult.systemError("execute inner plugin got an exception");
        }
    }

    /**
     * Inject dependencies into plugin instance objects
     *
     * @param pluginObj
     * @throws IllegalAccessException
     */
    protected void injectDependency(Object pluginObj) throws IllegalAccessException {
        Set<Field> fields = retrieveAllFields(pluginObj);
        for (Field field : fields) {
            PluginAutowired annotation = field.getAnnotation(PluginAutowired.class);
            if (annotation != null) {
                field.setAccessible(true);
                field.set(pluginObj, SpringUtils.getBean(field.getType()));
            }
        }
    }

    private static Set<Field> retrieveAllFields(Object plugin) {
        final Set<Field> fields = new HashSet<>();
        for (Class<?> clz = plugin.getClass(); !clz.getName().equals(Object.class.getName()); clz = clz
                .getSuperclass()) {
            Field[] fs = clz.getDeclaredFields();
            if (fs != null) {
                fields.addAll(Arrays.asList(fs));
            }
        }
        return fields;
    }
}
