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

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ChangeActionStartNotifyRequest extends ChangeStartNotifyRequest {

    private static final long serialVersionUID = -1430285678659523138L;
    /**
     * Change Key
     */
    @NotBlank
    private String changeKey;
    /**
     * Change Effective Target Type
     */
    @NotBlank
    private String effectiveTargetType;
    /**
     * Change Effective Target Locations
     */
    @NotNull
    private Set<String> effectiveTargetLocations;
    /**
     * Change Content Parameters,json
     */
    @NotBlank
    private String changeParamJson;

    /**
     * Change Phase
     * {@link com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum#getPhase()}
     */
    @NotBlank
    private String changePhase;

}
