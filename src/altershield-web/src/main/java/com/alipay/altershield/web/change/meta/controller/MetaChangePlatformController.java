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
package com.alipay.altershield.web.change.meta.controller;

import com.alipay.altershield.change.meta.service.MetaPlatformService;
import com.alipay.altershield.change.meta.service.request.CreateMetaPlatformRequest;
import com.alipay.altershield.common.id.sequence.dal.mapper.SequenceMapper;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yuanji
 * @version : MetaChangePlatformController.java, v 0.1 2022年06月22日 16:28 yuanji Exp $
 */
@Api
@Controller
@CrossOrigin
@RequestMapping("/meta/platform/")
public class MetaChangePlatformController {

    @Autowired
    private MetaPlatformService metaPlatformService;

    @Autowired
    private SequenceMapper seqMapper;

    @ApiOperation(value = "查询所有的平台信息")
    @ResponseBody
    @RequestMapping(value = "/queryAllPlatform", method = RequestMethod.GET)
    public AlterShieldResult<List<String>> queryAllPlatform() {
        return metaPlatformService.queryAllPlatform();
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public AlterShieldResult<String> createPlatform(@RequestBody CreateMetaPlatformRequest request) {
        return metaPlatformService.create(request);
    }

    @ResponseBody
    @RequestMapping(value = "/createSequence", method = RequestMethod.POST)
    public void createSequence() {
         seqMapper.insert("seq_default");
    }
}