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
package com.alipay.altershield.change.exe.service.check.model;


import com.alipay.altershield.common.util.TraceUtil;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;

/**
 * The type Ops trace stack.
 *
 * @author yuanji
 * @version : OpsTraceStack.java, v 0.1 2022年04月02日 10:49 上午 yuanji Exp $
 */
public class OpsTraceStack {

    /**
     * traceId
     */
    private OpsChngTrace orderTrace;

    /**
     * 父坐标
     * 在当前模型下。
     * 只支持：
     * order:
     *      orderCheck
     *      preAction
     *      Batch1
     *          batchAction11
     *          batchAction12
     *      Batch2
     *           batchAction21
     *           batchAction22
     */
    private OpsChngTrace beforeBatchTrace;

    /**
     * Instantiates a new Ops trace stack.
     *
     * @param orderTrace       the order trace
     * @param beforeBatchTrace the before batch trace
     */
    public OpsTraceStack(OpsChngTrace orderTrace, OpsChngTrace beforeBatchTrace) {
        this.orderTrace = orderTrace;
        this.beforeBatchTrace = beforeBatchTrace;
    }

    /**
     * Gets parent trace.
     *
     * @param stepType the step type
     * @return the parent trace
     */
    public OpsChngTrace getParentTrace(MetaChangeStepTypeEnum stepType) {
        switch (stepType) {
            case STEP_BEFORE_GRAY_START:
            case STEP_ORDER:
            case STEP_AFTER_GRAY_FINISH:
            case STEP_GRAY_BATCH:
                return new OpsChngTrace(orderTrace.getTraceId(), orderTrace.getCoordStr());
            case STEP_GRAY_BATCH_ACTION:
                //需要返回灰度下的coord
                return TraceUtil.genTraceServiceDirectFromNode(beforeBatchTrace);
            default:
                return new OpsChngTrace(orderTrace.getTraceId(), orderTrace.getCoordStr());

        }
    }

}