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
import com.alipay.altershield.framework.core.change.facade.result.ChangeFinishStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChangeExecOrderFinishRequest extends ChangeBaseRequest {

    private static final long serialVersionUID = 5963725601076637361L;
    /**
     * End Status Identification: normal,rollback,cancel see {@link ChangeFinishStateEnum}
     */
    @NotBlank
    private String finishState;

    /**
     * @optional Change execute result. json string
     */
    @JsonFormat
    private String changeResultJsn;

    @StringLength(max = 16384)
    private String msg;

    /**
     * Change order finish time. If null, use the current time.
     */
    private Date finishTime;

    /**
     * Sets change finish success.
     */
    public void setChangeFinishSuccess() {
        setFinishState(ChangeFinishStateEnum.SUCCESS.getCode());
    }

    /**
     * Sets change finish rollback.
     */
    public void setChangeFinishRollback() {
        setFinishState(ChangeFinishStateEnum.ROLLBACK.getCode());
    }

    /**
     * Sets change finish cancel.
     */
    public void setChangeFinishCancel() {
        setFinishState(ChangeFinishStateEnum.CANCEL.getCode());
    }

}
