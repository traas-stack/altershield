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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.exe.service.check;

import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;


/**
 * @author yuanji
 * @version : ChangeSyncCheckService.java, v 0.1 2022年11月11日 16:40 yuanji Exp $
 */
public interface ChangeSyncCheckService {

    /**
     * 同步前置检查
     *
     * @param checkTimeOut
     * @param changeOrder
     * @param entity
     * @param metaChangeSceneEntity
     * @return
     */
    ChangeCheckVerdict syncCheck(long checkTimeOut, ExeChangeOrderEntity changeOrder, ExeNodeEntity entity,
                                 MetaChangeSceneEntity metaChangeSceneEntity);
}