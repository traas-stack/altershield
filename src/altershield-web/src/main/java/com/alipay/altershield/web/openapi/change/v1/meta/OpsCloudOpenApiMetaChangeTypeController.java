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
package com.alipay.altershield.web.openapi.change.v1.meta;

import com.alipay.opscloud.change.meta.model.MetaChangeTypeEntity;
import com.alipay.opscloud.change.meta.service.MetaChangeTypeService;
import com.alipay.opscloud.change.meta.service.request.CreateMetaChangeTypeRequest;
import com.alipay.opscloud.change.meta.service.request.QueryChangeTypeRequest;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.sdk.constant.OpsCloudApiConstant;
import com.alipay.opscloud.web.change.converter.MetaChangeTypeVOConverter;
import com.alipay.opscloud.web.change.meta.vo.MetaChangeTypeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(OpsCloudApiConstant.metaPrefix+"change_type")
public class OpsCloudOpenApiMetaChangeTypeController {

    @Autowired
    private MetaChangeTypeService metaChangeTypeService;

    @ApiOperation(value = "获取变更type")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public OpsCloudResult<List<MetaChangeTypeVO>> query(@Valid @RequestBody QueryChangeTypeRequest request) {
        OpsCloudResult<List<MetaChangeTypeEntity>> rs = metaChangeTypeService.queryChangeType(request);

        return MetaChangeTypeVOConverter.INSTANCE.covertToVOResult(rs);
    }

    @ApiOperation(value = "创建变更类型")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public  OpsCloudResult<String> insert(@Valid @RequestBody CreateMetaChangeTypeRequest request) {

        return metaChangeTypeService.insert(request);
    }

    @ApiOperation("查询全量变更类型列表")
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public OpsCloudResult<List<String>> queryAllChangeType() {
        return new OpsCloudResult<>();
    }
}
