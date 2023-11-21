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
package com.alipay.altershield.web.change.meta.vo;

import com.alipay.altershield.change.meta.model.enums.MetaChangeSceneStatus;
import com.alipay.altershield.framework.common.validate.StringLength;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yuanji
 * @version : MetaBaseChangeSceneVO.java, v 0.1 2022年03月23日 11:01 上午 yuanji Exp $
 */
@Data
@ApiModel(value = "变更场景基本信息")
public class MetaBaseChangeSceneVO implements Serializable {


    @ApiModelProperty(value = "变更场景id")
    private String id;
    /**
     * tldc租户，在一个云上是唯一标识
     */
    @ApiModelProperty(value = "可信租户码")
    private String tldcTenantCode;

    /**
     * 服务租户；多个租户用逗号隔开
     */
    @ApiModelProperty(value="可以服务可信租户编码，多个用逗号隔开", required = true)
    private String serverTldcTenantCode;

    /**
     * 变更场景key，在一个云下唯一标识
     */
    @ApiModelProperty(value = "变更场景key")
    private String                                changeSceneKey;
    /**
     * 变更场景名
     */
    @ApiModelProperty(value = "变更场景名")
    private String                                name;

    /**
     * 变更场景负责人
     */
    @ApiModelProperty(value = "变更场景负责人，多个用逗号隔开", example = "yuanji,jiuzhi")
    private String                                owner;
    /**
     * 变更场景归属的平台.例如:appName
     */
    @ApiModelProperty(value = "变更场景归属的平台", example = "opscloudcore")
    private String                                platformName;

    /**
     * 变更场景代G
     */
    @ApiModelProperty(value = "变更场景代G", example = "G0,G1,G2,G3,G4")
    private String  generation;

    /**
     * 变更场景的状态
     */
    @ApiModelProperty(value = "变更场景状态", notes = "0:暂存态;1:发布态")
    private MetaChangeSceneStatus status;

    /**
     * 变更内容类型
     */
    @StringLength(max = 128)
    @ApiModelProperty(value = "变更内容类型")
    private String changeContentType;

    /**
     * 变更对象类型
     */
    @StringLength(max = 128)
    @ApiModelProperty(value = "变更对象类型")
    private String changeTargetType;

    /**
     * 生效载体类型,多个内容用逗号分开
     */
    @StringLength(max = 4096)
    @ApiModelProperty(value = "生效载体类型", notes = "多个用逗号隔开")
    private String  effectiveTargetType;

    /**
     * 场景标识
     */
    @ApiModelProperty(value = "标签属性")
    private Map<String, List<String>> tags;

}