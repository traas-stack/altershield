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
package com.alipay.altershield.change.exe.service.check;


import com.alipay.altershield.change.exe.service.check.model.ChangeExecOrderSubmitRequestModel;
import com.alipay.altershield.change.exe.service.check.model.OpsTraceStack;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;

/**
 * The interface Exe change order service.
 *
 * @author yuanji
 * @version : ExeChangeOrderService.java, v 0.1 2022年04月01日 2:55 下午 yuanji Exp $
 */
public interface ExeChangeOrderService {

    /**
     * 创建变更工单
     * @param metaChangeSceneEntity
     * @param request
     * @return
     */
    ExeChangeOrderEntity createExeChangeOrder(
            MetaChangeSceneEntity metaChangeSceneEntity, ChangeExecOrderSubmitRequestModel request);

    /**
     * Gets ops trace stack.
     *
     * @param platform       the platform
     * @param bizExecOrderId the biz exec order id
     * @return the ops trace stack
     */
    OpsTraceStack getOpsTraceStack(String platform, String bizExecOrderId);

    /**
     * Gets change order.
     *
     * @param platform       the platform
     * @param bizExecOrderId the biz exec order id
     * @return the change order
     */
    ExeChangeOrderEntity getChangeOrder(String platform, String bizExecOrderId);

    /**
     * Update entity.
     *
     * @param exeChangeOrderEntity the exe change order entity
     * @param request              the request
     */
    void updateEntity(ExeChangeOrderEntity exeChangeOrderEntity, ChangeExecOrderFinishRequest request);

}