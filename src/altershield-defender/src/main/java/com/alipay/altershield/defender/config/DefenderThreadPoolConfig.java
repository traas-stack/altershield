/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * The thread pool configuration used by defender
 *
 * @author yhaoxuan
 * @version DefenderThreadPoolConfig.java, v 0.1 2022年12月12日 10:35 上午 yhaoxuan
 */
@Configuration
public class DefenderThreadPoolConfig {

    /**
     * Number of core threads
     */
    private static final int CORE_POOL_SIZE = 16;

    /**
     * Maximum number of threads
     */
    private static final int MAX_POOL_SIZE = 64;

    /**
     * Queue size
     */
    private static final int QUEUE_CAPACITY = 500;

    @Bean("defenderThreadPool")
    public ThreadPoolTaskExecutor createSearchThreadPool()
    {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        threadPoolTaskExecutor.setThreadNamePrefix("altershield-defender-");
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setQueueCapacity(0);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }
}