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

import com.alipay.altershield.change.exe.service.check.ChangeTraceService;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * The type Change trace service.
 *
 * @author yuanji
 * @version : ChangeTraceServiceImpl.java, v 0.1 2022年04月01日 4:19 下午 yuanji Exp $
 */
@Service
public class ChangeTraceServiceImpl implements ChangeTraceService {

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public OpsChngTrace generateTrace(ChangeScenarioEnum changeScenarioEnum, String bizExecOrderId) {
        Assert.notNull(changeScenarioEnum, "changeScenario is null");
        Assert.notNull(bizExecOrderId, "bizExecOrderId is null");
        return _generateTrace(changeScenarioEnum, bizExecOrderId);
    }

    @Override
    public OpsChngTrace generateTrace(String orderId, MetaChangeStepTypeEnum metaChangeStepTypeEnum) {
        return null;
    }


    private OpsChngTrace _generateTrace(ChangeScenarioEnum changeScenarioEnum,  String bizExecOrderId) {

        final String uid = generateUid(bizExecOrderId);
        String traceId = idGenerator.generateIdByUid(IdBizCodeEnum.OPSCLD_TRACE, uid, changeScenarioEnum);
        String coord = "";
        return new OpsChngTrace(traceId, coord);

    }

    /**
     * Generate uid string.
     *
     * @param bizExecOrderId the biz exec order id
     * @return the string
     */
    public static String generateUid(String bizExecOrderId) {
        if (bizExecOrderId == null || bizExecOrderId.isEmpty()) {
            return "00";
        }
        int hash = bizExecOrderId.hashCode();
        return hash2uid2(hash);
    }

    /**
     * Hash 2 uid 2 string.
     *
     * @param hash the hash
     * @return the string
     */
    static String hash2uid2(int hash) {
        if (hash < 0) {
            hash = hash + 1 + Integer.MAX_VALUE;
        }

        for (hash = hash % 100; hash < 0; hash += 100) {
        }

        if (hash < 10) {
            return "0" + hash;
        }
        return "" + hash;
    }

}