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
package com.alipay.altershield.framework.core.change.facade.request;

import com.alipay.altershield.framework.common.validate.ChangeTargetNotEmpty;
import com.alipay.altershield.framework.common.validate.StringLength;
import com.alipay.altershield.framework.common.validate.UserInfoFormat;
import com.alipay.altershield.framework.core.change.model.ChangeParentOrderInfo;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 变更执行单请求
 *
 * @author yuanji
 * @version : OpsCloudChangeExecOrderRequest.java, v 0.1 2022年02月22日 11:05 上午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ChangeExecOrderRequest extends ChangeBaseRequest {

    private static final long serialVersionUID = -1139725783136770213L;
    /**
     * Change Order Title
     */
    @StringLength(max = 256)
    @NotBlank
    private String changeTitle;
    /**
     * @optional Change Order Url
     */
    @StringLength(max = 512)
    private String changeUrl;
    /**
     * Change Order Creator
     */
    @NotBlank
    @StringLength(max = 128)
    @UserInfoFormat
    private String creator;
    /**
     * Change Scenario Code
     * {@link com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum}
     */
    @NotBlank
    private String changeScenarioCode = ChangeScenarioEnum.DAILY.getCode();
    /**
     * Change Phase List
     * {@link com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum#getPhase()} }
     */
    @NotNull
    private Set<String> changePhases;
    /**
     * [required] Change Target List
     */
    @ChangeTargetNotEmpty
    private ChangeTarget[] changeTargets;
    /**
     * Change Content Parameters. json
     */
    @NotBlank
    private String changeParamJson;

    /**
     * @optional Change Influenced Apps
     */
    private Set<String> changeApps;

    /**
     * @optional Change Parent Order Id
     */
    private ChangeParentOrderInfo parentOrderInfo;

    /**
     * @optional Change Trace. If it is not necessary to generate it separately, keep it null,
     *           otherwise the business side will control the trace itself
     */
    private OpsChngTrace trace;
}
