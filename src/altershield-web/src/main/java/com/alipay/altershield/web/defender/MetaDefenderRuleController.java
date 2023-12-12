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
package com.alipay.altershield.web.defender;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.defender.framework.meta.service.MetaDefenderRuleService;
import com.alipay.altershield.defender.framework.request.CreateDefenderRuleRequest;
import com.alipay.altershield.defender.framework.request.ModifyDefenderRuleRequest;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 变更防御规则相关web接口
 *
 * @author haoxuan
 * @version MetaDefenderRuleController.java, v 0.1 2022年08月30日 4:44 下午 haoxuan
 */
@Api
@CrossOrigin
@Controller
@RequestMapping("/meta/defender/rule")
public class MetaDefenderRuleController {

    @Autowired
    private MetaDefenderRuleService metaDefenderRuleService;

    @ApiOperation("创建防御规则")
    @ResponseBody
    @RequestMapping(value = "/createRule", method = RequestMethod.POST)
    public AlterShieldResult<Boolean> createRule(@NotNull @RequestBody CreateDefenderRuleRequest request) {
        try {
            return metaDefenderRuleService.createRule(request);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "MetaDefenderRuleController", "createRule",
                    "create rule got an exception", e);
            return AlterShieldResult.systemError("未知异常");
        }
    }

    @ApiOperation("编辑防御规则")
    @ResponseBody
    @RequestMapping(value = "/modifyRule", method = RequestMethod.POST)
    public AlterShieldResult<Boolean> modifyRule(@NotNull @RequestBody ModifyDefenderRuleRequest request) {
        try {
            return metaDefenderRuleService.modifyRule(request);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "MetaDefenderRuleController", "queryRuleList",
                    "modify rule got an exception", e);
            return AlterShieldResult.systemError("未知异常");
        }
    }

    @ApiOperation("删除防御规则")
    @ResponseBody
    @RequestMapping(value = "/deleteRule", method = RequestMethod.DELETE)
    public AlterShieldResult<Boolean> deleteRule(@NotNull @ApiParam(value = "防御规则id") @RequestParam String ruleId) {
        try {
            return metaDefenderRuleService.deleteRule(ruleId);
        } catch (Exception e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "MetaDefenderRuleController", "deleteRule",
                    "delete rule got an exception", e);
            return AlterShieldResult.systemError("未知异常");
        }
    }

}