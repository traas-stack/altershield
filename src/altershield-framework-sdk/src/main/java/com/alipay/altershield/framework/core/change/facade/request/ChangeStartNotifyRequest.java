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

import com.alipay.altershield.framework.common.validate.StringLength;
import com.alipay.altershield.framework.common.validate.UserInfoFormat;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 变更开始通知请求。
 *
 * @author yuanji
 * @version : OpsCloudChangeStartNotifyRequest.java, v 0.1 2022年01月27日 10:43 上午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ChangeStartNotifyRequest extends ChangeBaseRequest {

    private static final long serialVersionUID = 399425988852521390L;
    /**
     * The actual executor of the change execution order
     */
    @NotBlank
    @StringLength(max = 128)
    @UserInfoFormat
    private String executor;

    /**
     * The actual start time of the change. If null, use system time
     */
    private Date startTime;

    /**
     * Change trace
     */
    private OpsChngTrace trace;

    /**
     * Emergency change signs. During the change process, this field can be set to true in emergency
     * situations, and all subsequent operations are required to set the current change to emergency
     * mode. If it is null, use the scenario of the change order.
     */
    private Boolean emergency;

    /**
     * Is the rule list returned when the check fails
     */
    private boolean returnRuleWhenCheckFail = false;

    /**
     * @optional Extension field for change analysis.
     */
    private Map<String, Collection<String>> extensionInfo;

}
