/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.pluginmarket.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The plugin uses the bean in the spring container to mark this annotation
 *
 * @author xiangyue
 * @version : PluginAutowired.java, v 0.1 2022-10-09 17:48 xiangyue Exp $$
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginAutowired {
}
