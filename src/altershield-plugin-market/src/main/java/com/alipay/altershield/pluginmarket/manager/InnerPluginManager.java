/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.pluginmarket.manager;

import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.shared.pluginmarket.entity.MetaPluginEntity;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author xiangyue
 * @version : InnerPluginManager.java, v 0.1 2022-11-24 11:13 xiangyue Exp $$
 */
public interface InnerPluginManager {

    /**
     * Execute plugin
     *
     * @param pluginKey plugin key
     * @param mainClass main class of the plugin
     * @param invoker plugin execute function
     * @param <PluginType> plugin type
     * @param <ResultType> plugin result type
     * @return execute result
     */
    <PluginType, ResultType> AlterShieldResult<ResultType> executePlugin(String pluginKey, String mainClass,
                                                                         Function<PluginType, ResultType> invoker);
}
