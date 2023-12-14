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
package com.alipay.altershield.change.meta.model;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.framework.core.base.AbstractBasicEntity;
import com.alipay.altershield.framework.core.risk.config.RiskDefenseConfig;
import com.alipay.altershield.shared.common.largefield.ref.KvRef;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import lombok.Data;


/**
 * @author yukeyang
 * @date Jan 14, 2019 3:36:41 PM
 */
@Data
public class MetaRiskDefenseEntity extends AbstractBasicEntity {
    /**  */
    private static final long   serialVersionUID = -2428893321314394433L;

    /**
     * 管控微服务唯一标识
     */
    private String controlKey;

    /**
     * 管控微服务名称
     */
    private String serviceName;

    /**
     * 管控微服务owner
     */
    private String owner;

    /**
     * 管控微服务描述信息
     */
    private String desc;

    /**
     * 管控微服务配置
     */
    private KvRef<RiskDefenseConfig> config;

    /**
     * 管控服务生效开关
     */
    private Boolean enable;

    /**
     * 防御防线类型
     */
    private String defenseType;

    /**
     * tldc租户
     */
    private String tenantCode;

    /**
     * Constructor.
     *
     * @param keyValueStorage the key value storage
     */
    public MetaRiskDefenseEntity(KeyValueStorage keyValueStorage) {
        super();
        this.config = new KvRef<>(keyValueStorage, KvRefCodec.DEFENSE_CONF, null);
    }


}
