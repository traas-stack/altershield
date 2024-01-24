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

import com.alipay.altershield.framework.common.validate.JsonFormat;
import com.alipay.altershield.framework.common.validate.StringLength;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author yuanji
 * @version : OpsCloudChangeFinishNotifyRequest.java, v 0.1 2022年01月27日 5:45 下午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChangeFinishNotifyRequest extends ChangeCheckRequest {
    private static final long serialVersionUID = 8625263286973885637L;
    /**
     * [required] Is the execution of the change itself successful or failed. Failure indicates that
     * the change has no impact, has not occurred, or has been rolled back. For partial execution
     * situations, it is necessary to roll back.
     */
    private boolean success = true;

    /**
     * @optional The execution result msg of the change itself (no longer than 4K bytes after UTF-8
     *           encoding)
     */
    @StringLength(max = 16384)
    private String msg;

    /**
     * [optional] 变更本身的执行结果 json string
     * @Deprecated
     * use {@link }
     */
    @JsonFormat
    private String            serviceResult;

    /**
     * @optional The execution results of the change itself. json string
     */
    @JsonFormat
    private String changeResultJsn;

    /**
     * @optional Change the end time. If not empty, use the current time
     */
    private Date finishTime = new Date();

    /**
     * Change Effective Target Type。 If the carrier type changes after the change, this new value
     * needs to be passed
     */
    private String effectiveTargetType;

    /**
     * Change Effective Target Locations If the carrier type changes after the change, this new
     * value needs to be passed
     */
    private Set<String> effectiveTargetLocations;

    /**
     * @optional Change Extension Fields If the impact of the change changes after the change, this
     *           new value needs to be passed
     */
    private Map<String, Collection<String>> extensionInfo;
}
