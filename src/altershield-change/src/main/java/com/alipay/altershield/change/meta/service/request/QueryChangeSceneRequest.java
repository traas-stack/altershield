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

import com.alipay.altershield.framework.core.change.facade.request.AlterShieldPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author yuanji
 * @version : QueryChangeSceneRequest.java, v 0.1 2022年04月15日 1:39 下午 yuanji Exp $
 */
@Data
@ApiModel(value = "变更场景查询请求", description = "支持分页查询")
public class QueryChangeSceneRequest extends AlterShieldPageRequest {

    @ApiModelProperty(value = "变更场景名", example = "变更场景测试")
    private String changeSceneName;

    @ApiModelProperty(value = "变更场景归属的平台", example = "opscloudcore")
    private String platformName;

    /**
     * 0 是暂存，1是正式态
     */
    @ApiModelProperty(value = "变更场景状态", notes = "0:暂存态;1:发布态")
    private Integer status;

    /**
     * 变更代G
     */
    @ApiModelProperty(value = "变更场景状态", notes = "0:暂存态;1:发布态" ,example = "G0,G1,G2,G3,G4")
    private String generation;

    /**
     * 可信租户
     */
    @ApiModelProperty(value = "可信租户码")
    private List<String> tldcTenantCodes;

    /**
     * 场景管理员
     */
    @ApiModelProperty(value = "场景管理员")
    private String owner;

    /**
     * 场景管理员
     */
    @ApiModelProperty(value = "场景码")
    private String changeSceneKey;



}