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

import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseLastBatchEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChangeExecBatchStartNotifyRequest extends ChangeStartNotifyRequest {

    private static final long serialVersionUID = -7310760448269054517L;
    /**
     * Change batch sequence number.Each environment should start from 0
     */
    @Min(0)
    private int batchNo = -1;
    /**
     * The total number of batches for the current environment
     */
    @Min(0)
    private int totalBatchNumInChangePhase = -1;

    /**
     * Is this the final batch for the current change phase (environment)
     */
    @NotBlank
    private String isLastBatchInChangePhaseTag = ChangePhaseLastBatchEnum.NOT_LAST.getTag();

    /**
     * Is this the final batch for the entire change
     */
    @NotBlank
    private String isLastBatchTag = ChangePhaseLastBatchEnum.NOT_LAST.getTag();
    /**
     * The total number of batches for the change
     */
    @Min(0)
    private int totalBatchNum = -1;

    /**
     * Change Phase {@link ChangePhaseEnum#getPhase()}
     */
    @NotBlank
    private String changePhase;

    /**
     * Change Effective Target Type。
     */
    @NotBlank
    private String effectiveTargetType;

    /**
     * Change Effective Target Locations
     */
    @NotNull
    private Set<String> effectiveTargetLocations;

    /**
     * Additional information
     */
    private Map<String, Object> extInfo;

    /**
     * Mark Not Last Batch
     */
    public void markLastBatchTagInChangePhase() {
        setIsLastBatchInChangePhaseTag(ChangePhaseLastBatchEnum.NOT_LAST.getTag());
    }

    /**
     * Mark Last Batch
     */
    public void markLastBatchTag() {
        setIsLastBatchTag(ChangePhaseLastBatchEnum.LAST.getTag());
    }

}
