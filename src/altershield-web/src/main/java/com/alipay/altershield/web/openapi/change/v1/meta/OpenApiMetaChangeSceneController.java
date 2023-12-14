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

import com.alipay.altershield.change.meta.service.MetaChangeSceneService;
import com.alipay.altershield.change.meta.service.request.SyncMetaChangeSceneRequest;
import com.alipay.altershield.common.openapi.OpenApiRequestThreadLocal;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.CreateMetaChangeSceneResult;
import com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Ops cloud open api meta change scene controller.
 *
 * @author yuanji
 * @version : MetaChangeSceneController.java, v 0.1 2022年06月09日 10:44 yuanji Exp $
 */
@Controller
@RequestMapping(AlterShieldApiConstant.metaPrefix)
public class OpenApiMetaChangeSceneController {

    @Autowired
    private MetaChangeSceneService metaChangeSceneService;

    /**
     * Create base change scene ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @ResponseBody
    @RequestMapping(value = AlterShieldApiConstant.createChangeSceneAction, method = RequestMethod.POST)
    public AlterShieldResult<CreateMetaChangeSceneResult> createBaseChangeScene(@Validated @RequestBody CreateMetaChangeSceneRequest request) {

        String platform = OpenApiRequestThreadLocal.getPlatform();
        if(!StringUtils.equals(platform, request.getPlatformName()))
        {
            return AlterShieldResult.notSupport("platform must equal to platform in request," + platform +"!=" + request.getPlatformName());
        }
        return metaChangeSceneService.createReleaseChangeScene(request);
    }


    /**
     * Create standard change scene 2 ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @ApiOperation(value = "创建标准变更场景第二步，G2,G3,G4场景使用")
    @ResponseBody
    @RequestMapping(value = "/sync/serviceKeyConfig", method = RequestMethod.POST)
    public AlterShieldResult<Boolean> syncServiceKeyConfig(@Validated @RequestBody SyncMetaChangeSceneRequest request) {
        return metaChangeSceneService.syncServiceKey(request);
    }
}