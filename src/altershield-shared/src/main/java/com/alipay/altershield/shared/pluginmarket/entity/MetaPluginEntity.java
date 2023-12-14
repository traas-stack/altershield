/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.pluginmarket.entity;

import com.alipay.altershield.framework.core.base.AbstractBasicEntity;
import lombok.Data;

/**
 * @author xiangyue
 * @version : MetaAbilityPluginEntity.java, v 0.1 2022-11-24 10:29 xiangyue Exp $$
 */
@Data
public class MetaPluginEntity extends AbstractBasicEntity {

    /**
     * Main class of the plugin
     */
    private String mainClass;

    /**
     * invoke type of the plugin
     */
    private String invokeType;
}
