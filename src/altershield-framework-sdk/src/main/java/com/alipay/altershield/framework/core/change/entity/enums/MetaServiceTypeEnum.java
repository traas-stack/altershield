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
package com.alipay.altershield.framework.core.change.entity.enums;

import com.alibaba.fastjson.annotation.JSONCreator;

import java.io.Serializable;

/**
 *
 * @author liushengdong
 * @version $Id: MetaServiceType.java, v 0.1 2018年05月31日 下午5:51 liushengdong Exp $
 */
public enum MetaServiceTypeEnum implements Serializable {
    /** 没有任何实现 */
    NONE("none"),
    /** ==================== 异步 ==================== **/
    /** 异步-原子服务(正逆向-TR服务) */
    ATOMIC("atomic"),
    /** 异步-插件托管的gateway服务 */
    PLUGIN_GATEWAY("pluginGateway"),
    /** 异步-插件托管的两阶段变更 */
    PLUGIN_TWO_PHASE("plugin2Phase"),
    /** cloud流程服务 */
    CLD_FLOW("cloudflow"),
    /** 异步-PB服务 */
    PB_CHNG_SRV("pbChngSrv"),
    /** 异步-SPI（正逆向-HTTP服务） */
    ASYNC_HTTP("asyncHttp"),
    /** ==================== 同步 ==================== **/
    /** 同步-atomic(正逆向-TR服务)  */
    SYNC_ATOMIC("syncAtomic"),
    /** 同步-SPI（正逆向-HTTP服务） */
    SYNC_HTTP("syncHttp"),
    /** 同步-插件托管的gateway服务 */
    SYNC_PLUGIN_GATEWAY("syncPluginGateway"),
    /** 同步-PB服务 */
    SYNC_PB_CHNG_SRV("syncPbChngSrv"),
    ;

    private final String code;

    @JSONCreator
    private MetaServiceTypeEnum(String code) {
        this.code = code;
    }

    /**
     * @return property value of {@link #code}
     */
    public String getCode() {
        return code;
    }

    public static MetaServiceTypeEnum getByCode(String code) {
        for (MetaServiceTypeEnum e : values()) {
            if (e.getCode().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }

    public static boolean isSync(MetaServiceTypeEnum serviceType){
        if(serviceType == MetaServiceTypeEnum.SYNC_ATOMIC || serviceType == MetaServiceTypeEnum.SYNC_PB_CHNG_SRV
                || serviceType == MetaServiceTypeEnum.SYNC_PLUGIN_GATEWAY){
            return true;
        }
        return false;
    }
}
