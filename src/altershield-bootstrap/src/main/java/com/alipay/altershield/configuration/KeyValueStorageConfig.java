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
package com.alipay.altershield.configuration;

import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.largefield.kv.KeyValueDAO;
import com.alipay.altershield.common.largefield.kv.KeyValueStorageImpl;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.shared.common.largefield.kv.KeyValueStorage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * keyvalue storage config。用于创建基于分区表和单表的keyvalue存储结构
 *
 * @author yuanji
 * @version : KeyValueStorageConfig.java, v 0.1 2022年03月03日 9:01 下午 yuanji Exp $
 */
@Configuration
public class KeyValueStorageConfig {

    private static final Logger logger = Loggers.DEFAULT;


    @Resource
    private TransactionTemplate confTransactionTemplate;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    @Qualifier("singleKeyValueDAO")
    private KeyValueDAO singleKeyValueDAO;

    @Autowired
    @Qualifier("shardingKeyValueDAO")
    private KeyValueDAO shardingKeyValueDAO;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * Create single key value store key value storage.
     *
     * @return the key value storage
     */
    @Bean(name = "singleKeyValueStorage")
    public KeyValueStorage createSingleKeyValueStore()
    {
        logger.info("start create single keyValue storage");
        KeyValueStorageImpl keyValueStorage = new KeyValueStorageImpl();
        keyValueStorage.setTransactionTemplate(confTransactionTemplate);
        keyValueStorage.setKeyValueDAO(singleKeyValueDAO);
        logger.info("create single keyValue storage success");
        return keyValueStorage;
    }

    /**
     * Create sharding key value store key value storage.
     *
     * @return the key value storage
     */
    @Bean(name = "shardingKeyValueStorage")
    public KeyValueStorage createShardingKeyValueStore()
    {
        logger.info("start create sharding keyValue storage");
        KeyValueStorageImpl keyValueStorage = new KeyValueStorageImpl();
        keyValueStorage.setTransactionTemplate(transactionTemplate);
        keyValueStorage.setKeyValueDAO(shardingKeyValueDAO);
        keyValueStorage.setIdGenerator(idGenerator);
        logger.info("start create sharding keyValue storage");
        return keyValueStorage;
    }
}