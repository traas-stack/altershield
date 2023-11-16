/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.model;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defender Base Entity Converter
 *
 * @author yhaoxuan
 * @version AbstractDefenderEntityConvertor.java, v 0.1 2022年07月25日 10:57 上午 yhaoxuan
 */
public abstract class AbstractDefenderEntityConvertor {

    @Autowired
    protected KeyValueStorage singleKeyValueStorage;
    @Autowired
    protected KeyValueStorage shardingKeyValueStorage;
}