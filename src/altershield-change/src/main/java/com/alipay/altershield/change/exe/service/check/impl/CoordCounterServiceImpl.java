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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.altershield.change.exe.dal.dataobject.ExeCounterDO;
import com.alipay.altershield.change.exe.dal.mapper.ExeCounterMapper;
import com.alipay.altershield.change.exe.service.check.CoordCounterService;
import com.alipay.altershield.common.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * The type Coord counter service.
 *
 * @author liushengdong
 * @version $Id : CoordCounterServiceImpl.java, v 0.1 2018年12月06日 9:23 PM liushengdong Exp $
 */
@Service("coordCounterService")
public class CoordCounterServiceImpl implements CoordCounterService {

    @Resource
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ExeCounterMapper exeCounterMapper;

    /**
     * @param traceId
     */
    @Override
    public long addAndGetByTraceId (String traceId) {
        return transactionTemplate.execute(new TransactionCallback<Long>() {
            @Override
            public Long doInTransaction(TransactionStatus status) {
                Assert.isTrue(IdUtil.isValidId(traceId), "invalid trace id");
                ExeCounterDO exeCounterDO = exeCounterMapper.lockById(traceId);

                if (exeCounterDO == null) {
                    exeCounterDO = new ExeCounterDO();
                    exeCounterDO.setId(traceId);
                    exeCounterDO.setCount(0L);
                    try {
                        exeCounterMapper.insert(exeCounterDO);
                        return 0L;
                    } catch (DataIntegrityViolationException e) {
                        //幂等
                        exeCounterDO = exeCounterMapper.lockById(traceId);
                        Assert.notNull(exeCounterDO,"lock counter fail");
                        return addAndUpdate(exeCounterDO);
                    }
                }

                return addAndUpdate(exeCounterDO);
            }
        });
    }

    /**
     * 累加,并更新到DB
     * @param opscldCounterDO
     * @return 累加后的值
     */
    private long addAndUpdate(ExeCounterDO opscldCounterDO) {
        long count = opscldCounterDO.getCount();
        count = count + 1;
        opscldCounterDO.setCount(count);
        exeCounterMapper.updateByPrimaryKey(opscldCounterDO);
        return count;
    }
}