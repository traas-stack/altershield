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
package com.alipay.altershield.change.meta.service.request;

import com.alipay.altershield.change.meta.service.request.group.ChangeSceneBatchGroup;
import com.alipay.altershield.framework.common.validate.StringLength;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.alipay.altershield.common.constant.AlterShieldConstant.CHANGE_KEY_PATTERN;

/**
 * @author yuanji
 * @version : CreateMetaChangeSceneRequest.java, v 0.1 2022年03月15日 6:11 下午 yuanji Exp $
 */
@Data
@ApiModel("创建变更场景请求")
public class CreateTempMetaChangeSceneRequest extends BaseMetaChangeSceneRequest {

    /**
     * 变更场景key，在一个云下唯一标识
     */
    @NotNull
    @Pattern(regexp = CHANGE_KEY_PATTERN, message = "场景key只能包含字母，数字，点和下划线，并且只能以字母开头和结尾")
    @StringLength(max = 200)
    @ApiModelProperty(value = "变更场景key", required = true)
    private String changeSceneKey;



    @ApiModelProperty(value = "变更场景生效配置")
    @Valid
    @NotNull(groups = ChangeSceneBatchGroup.class)
    private MetaChangeEffectiveConfigRequest changeEffectiveConfig;

}