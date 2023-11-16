/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.pluginmarket;


import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;

import java.util.function.Function;

/**
 * the main process service of plugin market
 *
 * @author xiangyue
 * @version : PluginMarket.java, v 0.1 2022-11-24 11:26 xiangyue Exp $$
 */
public interface PluginMarket {

    /**
     * Is an enabled plugin
     *
     * @param pluginKey the plugin key
     * @return is enabled or not
     */
    boolean isEnablePlugin(String pluginKey);

    /**
     * Execute plugin
     *
     * @param pluginKey the plugin key
     * @param mainClass the main class
     * @param invoker invoke function
     * @param <PluginType> type of the plugin
     * @param <ResultType> result type of the plugin
     * @return 插件执行结果
     */
    <PluginType, ResultType> AlterShieldResult<ResultType> executePlugin(String pluginKey, String mainClass,
                                                                         Function<PluginType, ResultType> invoker);
}
