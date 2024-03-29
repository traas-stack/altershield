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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.web.common.config;

import com.alipay.altershield.common.backconfig.request.ConfigRequest;
import com.alipay.altershield.common.backconfig.request.QueryConfigRequest;
import com.alipay.altershield.common.backconfig.result.QueryConfigResult;
import com.alipay.altershield.common.backconfig.service.ConfigService;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 后台配置管理接口
 *
 * @author haoxuan
 * @version ConfigController.java, v 0.1 2022年11月25日 3:52 下午 haoxuan
 */
@CrossOrigin
@Controller
@RequestMapping("/meta/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ResponseBody
    @RequestMapping(value = "/queryConfigByPage", method = RequestMethod.POST)
    public AlterShieldResult queryConfigByPage(@NotNull @RequestBody QueryConfigRequest request) {
        try {
            return configService.queryConfigList(request);
        } catch (Exception e) {
            return new AlterShieldResult<>(AlterShieldInternalErrorCode.SYSTEM_ERROR.getCode(), "未知异常");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AlterShieldResult<Void> update(@RequestBody ConfigRequest request) {
        try {
            configService.modifyConfig(request);
            return new AlterShieldResult<>();
        } catch (Exception e) {
            return new AlterShieldResult<>(false, AlterShieldInternalErrorCode.SYSTEM_ERROR.getCode(), "未知异常");
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public AlterShieldResult<Void> create(@RequestBody ConfigRequest request) {
        try {
            configService.saveConfig(request);
            return new AlterShieldResult<>();
        } catch (Exception e) {
            return new AlterShieldResult<>(false, AlterShieldInternalErrorCode.SYSTEM_ERROR.getCode(), "未知异常");
        }
    }
}