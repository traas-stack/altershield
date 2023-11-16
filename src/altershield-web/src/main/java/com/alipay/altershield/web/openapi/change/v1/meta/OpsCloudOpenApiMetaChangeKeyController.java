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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.web.openapi.change.v1.meta;

import com.alipay.opscloud.change.meta.service.MetaChangeSceneService;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.sdk.constant.OpsCloudApiConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * The type Ops cloud open api meta change scene controller.
 *
 * @author yuanji
 * @version : MetaChangeSceneController.java, v 0.1 2022年06月09日 10:44 yuanji Exp $
 */
@Controller
@RequestMapping(OpsCloudApiConstant.metaPrefix)
public class OpsCloudOpenApiMetaChangeKeyController {

    @Autowired
    private MetaChangeSceneService metaChangeSceneService;

    @ApiOperation(value = "检查changeKey是否存在",notes = "存在返回true，不存在false")
    @ResponseBody
    @RequestMapping(value = "/check_change_key", method = RequestMethod.GET)
    public OpsCloudResult<Boolean> checkChangeKey(@NotNull @RequestParam @ApiParam("变更key") String changeKey)
    {
        return metaChangeSceneService.checkChangeKey(changeKey);
    }

    @ApiOperation(value = "检查changeKey是否存在",notes = "存在返回true，不存在false")
    @ResponseBody
    @RequestMapping(value = "/only_check_change_key", method = RequestMethod.GET)
    public OpsCloudResult<Boolean> onlyCheckChangeKey(@NotNull @RequestParam @ApiParam("变更key") String changeKey)
    {
        return metaChangeSceneService.onlyCheckChangeKey(changeKey);
    }
}