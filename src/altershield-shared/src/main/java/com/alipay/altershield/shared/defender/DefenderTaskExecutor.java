/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender;

import com.alipay.altershield.shared.defender.request.DefenderTaskExecuteRequest;
import com.alipay.altershield.shared.defender.result.DefenderTaskResult;

/**
 * Defender task executor
 *
 * @author yhaoxuan
 * @version DefenderTaskExecutor.java, v 0.1 2022年08月28日 7:17 下午 yhaoxuan
 */
public interface DefenderTaskExecutor {

    /**
     * execute single detection task
     *
     * @param request Single defense rule task execution request body
     * @return task submission results
     */
    DefenderTaskResult execute(DefenderTaskExecuteRequest request);
}