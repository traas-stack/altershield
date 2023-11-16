/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service;

import com.alipay.opscloud.api.defender.request.DefenderTaskExecuteRequest;

/**
 * @author fujinfei
 * @version ProxyDefenseSpiExe.java, v 0.1 2022年10月28日 11:06 fujinfei
 */
public interface ProxyDefenseSpiExe {

    /**
     * 代理执行单条防御插件*
     * @param request
     */
    void proxyDefenderExe(DefenderTaskExecuteRequest request);

}