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
package com.alipay.altershield.change.exe.repository;


import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;

/**
 * 变更执行单的操作
 *
 * @author yuanji
 * @version : ExeChangeOrderRepository.java, v 0.1 2022年03月24日 3:04 下午 yuanji Exp $
 */
public interface ExeChangeOrderRepository {

    /**
     * 接入一个变更单
     *
     * @param entity
     */
    boolean insert(ExeChangeOrderEntity entity);

    /**
     * 更新一个变更单信息
     *
     * @param entity
     * @return
     */
    boolean update(ExeChangeOrderEntity entity);

    /**
     * 根据变更单id获取一个变更单
     *
     * @param orderId
     * @return
     */
    ExeChangeOrderEntity getChangeOrderById(String orderId);

    /**
     * 根据平台和业务id获取变更单信息
     *
     * @param platform
     * @param bizExecOrderId
     * @return
     */
    ExeChangeOrderEntity getChangeOrder(String platform, String bizExecOrderId);

    /**
     * 根据变更单的基本信息
     *
     * @param orderId
     * @param status
     * @param msg
     */
    void updateOrderInfo(String orderId, ExeOrderStatusEnum status, String msg);

    /**
     * 根据orderId获取业务id
     *
     * @param orderId
     * @return
     */
    String getBizExecOrderIdByOrderId(String orderId);

    /**
     * 根据平台和业务id获取变更单id
     *
     * @param platform
     * @param bizExecOrderId
     * @return
     */
    String getOrderIdByBizExecOrderId(String platform, String bizExecOrderId);

}