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
package com.alipay.altershield.framework.core.change.facade.callback.request;

import com.alipay.altershield.framework.core.change.facade.request.ChangeStartNotifyRequest;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.framework.sdk.change.client.ChangeClient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shuo.qius
 * @version Apr 3, 2019
 */
@Data
@NoArgsConstructor
public class ChangeCheckCallbackRequest implements Serializable {
    private static final long serialVersionUID = 2831668530893182192L;
    /**
     * {@link ChangeClient#submitChangeStartNotify(ChangeStartNotifyRequest)} 或者
     *
     * 的返回值
     */
    private String nodeId;

    /**
     * 变更场景key
     */
    private String changeSceneKey;
    /**
     *
     * 变更执行单id。
     *
     */

    private String bizExecOrderId;

    /**
     * 校验的结果
     */
    private ChangeCheckVerdict verdict;

    /**
     * 防御阶段
     */
    private DefenseStageEnum defenseStageEnum;

    public ChangeCheckCallbackRequest(String nodeId, String changeSceneKey, String bizExecOrderId,
            ChangeCheckVerdict verdict, DefenseStageEnum defenseStageEnum) {
        this.nodeId = nodeId;
        this.changeSceneKey = changeSceneKey;
        this.bizExecOrderId = bizExecOrderId;
        this.verdict = verdict;
        this.defenseStageEnum = defenseStageEnum;
    }

}
