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
package com.alipay.altershield.web.change.meta.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.change.meta.service.MetaChangeSceneService;
import com.alipay.altershield.change.meta.service.request.AlterChangeSceneGenerationRequest;
import com.alipay.altershield.change.meta.service.request.CreateMetaChangeScene2Request;
import com.alipay.altershield.change.meta.service.request.CreateTempMetaChangeSceneRequest;
import com.alipay.altershield.change.meta.service.request.QueryChangeSceneRequest;
import com.alipay.altershield.change.meta.service.request.group.ChangeSceneBatchGroup;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldPageResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.CreateMetaChangeSceneResult;
import com.alipay.altershield.web.change.converter.MetaChangeSceneVOConverter;
import com.alipay.altershield.web.change.meta.service.MetaChangeSceneConfigurationService;
import com.alipay.altershield.web.change.meta.vo.MetaBaseChangeSceneVO;
import com.alipay.altershield.web.change.meta.vo.MetaChangeScene2VO;
import com.alipay.altershield.web.change.meta.vo.MetaChangeSceneVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Meta change scene controller.
 *
 * @author yuanji
 * @version : controller.java, v 0.1 2022年03月15日 6:05 下午 yuanji Exp $
 */
@Api
@Controller
@CrossOrigin
@RequestMapping("/meta/change_scene/")
public class MetaChangeSceneController {

    @Autowired
    private MetaChangeSceneService metaChangeSceneService;

    @Autowired
    private MetaChangeSceneConfigurationService metaChangeSceneConfigurationService;

    private static final MetaChangeSceneVOConverter converter = MetaChangeSceneVOConverter.INSTANCE;

    /**
     * Create base change scene ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @ApiOperation(value = "创建基本变更场景第一步，G0,G1场景使用")
    @ResponseBody
    @RequestMapping(value = "/create/base", method = RequestMethod.POST)
    public AlterShieldResult<CreateMetaChangeSceneResult> createBaseChangeScene(@Validated @RequestBody CreateTempMetaChangeSceneRequest request) {
        return doCreateOrUpdateChangeScene(request);
    }

    /**
     * Create standard change scene ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @ApiOperation(value = "创建标准变更场景第一步,G2，G3，G4场景使用")
    @ResponseBody
    @RequestMapping(value = "/create/standard", method = RequestMethod.POST)
    public AlterShieldResult<CreateMetaChangeSceneResult> createStandardChangeScene(@Validated(ChangeSceneBatchGroup.class) @RequestBody
                                                                    CreateTempMetaChangeSceneRequest request) {
        return doCreateOrUpdateChangeScene(request);
    }

    private AlterShieldResult<CreateMetaChangeSceneResult> doCreateOrUpdateChangeScene(CreateTempMetaChangeSceneRequest request) {
        CreateMetaChangeSceneRequest createMetaChangeSceneRequest = converter.convertStandard(request);
        return metaChangeSceneService.createOrUpdateChangeScene(request.getId(),  createMetaChangeSceneRequest);
    }

    /**
     * Create standard change scene 2 ops cloud result.
     *
     * @param request the request
     * @return the ops cloud result
     */
    @ApiOperation(value = "创建标准变更场景第二步，G2,G3,G4场景使用")
    @ResponseBody
    @RequestMapping(value = "/create2/standard", method = RequestMethod.POST)
    public AlterShieldResult<Boolean> createStandardChangeScene2(@Validated @RequestBody CreateMetaChangeScene2Request request) {
        return metaChangeSceneService.createChangeScene(request);
    }

    /**
     * Gets change scene 2 vo.
     *
     * @param id the id
     * @return the change scene 2 vo
     */
    @ApiOperation(value = "获取变更场景第二步信息")
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public AlterShieldResult<MetaChangeScene2VO> getChangeScene2VO(@NotNull @RequestParam @ApiParam("变更场景id") String id)
    {
        return metaChangeSceneConfigurationService.getConfigurationInfo(id);
    }

    /**
     * Remove change scene ops cloud result.
     *
     * @param id the id
     * @return the ops cloud result
     */
    @ApiOperation(value = "删除变更场景")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AlterShieldResult<Boolean> removeChangeScene(@ApiParam(name = "变更场景id", required = true) @NotNull @RequestParam String id)
    {
        return metaChangeSceneService.removeChangeScene(id);
    }

    /**
     * Remove change step ops cloud result.
     *
     * @param id the id
     * @return the ops cloud result
     */
    @ApiOperation(value = "删除变更步骤")
    @ResponseBody
    @RequestMapping(value = "/change_step/remove", method = RequestMethod.POST)
    public AlterShieldResult<Boolean> removeChangeStep(@NotNull @RequestParam @ApiParam("变更步骤id") String id) {
        return metaChangeSceneService.removeChangeStep(id);
    }

    /**
     * Query change scene by platform page result.
     *
     * @param request the request
     * @return the page result
     */
    @ApiOperation(value = "查询变更场景")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public AlterShieldPageResult<List<MetaBaseChangeSceneVO>> queryChangeSceneByPlatform(@NotNull @RequestBody QueryChangeSceneRequest request) {
        return converter.convertToBaseChangeSceneListResult(metaChangeSceneService.query(request));
    }

    /**
     * Gets change scene.
     *
     * @param id the id
     * @return the change scene
     */
    @ApiOperation(value = "获取变更场景信息")
    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public AlterShieldResult<MetaChangeSceneVO> getChangeScene(@NotNull @RequestParam @ApiParam("变更场景id") String id) {
        return converter.convertBaseChangeScene(metaChangeSceneService.getMetaChangeSceneById(id));
    }

    /**
     * Export ops cloud result.
     *
     * @param id the id
     * @return the ops cloud result
     */
    @ApiOperation(value = "导出变更场景信息",notes = "直接返回大json数据")
    @ResponseBody
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public AlterShieldResult<String> export(@NotNull @RequestParam @ApiParam("变更场景id") String id) {
        return metaChangeSceneService.exportChangeScene(id);
    }

    /**
     * Import change scene ops cloud result.
     *
     * @param config the config
     * @return the ops cloud result
     */
    @ApiOperation(value = "导入变更场景")
    @ResponseBody
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public AlterShieldResult<String> importChangeScene(@NotNull @RequestBody @ApiParam("变更场景json值") JSONObject config) {
        return metaChangeSceneService.importChangeScene(config);
    }

    @ApiOperation(value = "检查changeKey是否存在",notes = "存在返回true，不存在false")
    @ResponseBody
    @RequestMapping(value = "/check_change_key", method = RequestMethod.GET)
    public AlterShieldResult<Boolean> checkChangeKey(@NotNull @RequestParam @ApiParam("变更key") String changeKey)
    {
        return metaChangeSceneService.checkChangeKey(changeKey);
    }

    @ApiOperation(value = "变更场景代G",notes = "变更返回true，不存在false。data为变更场景id")
    @ResponseBody
    @RequestMapping(value = "/alter_generation", method = RequestMethod.POST)
    public AlterShieldResult<String> alterGeneration(@Validated @RequestBody AlterChangeSceneGenerationRequest request)
    {
        return metaChangeSceneService.alterGeneration(request);
    }


}