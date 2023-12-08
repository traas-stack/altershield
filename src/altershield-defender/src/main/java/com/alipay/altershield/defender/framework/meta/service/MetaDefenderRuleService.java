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
package com.alipay.altershield.defender.framework.meta.service;

import com.alipay.altershield.defender.framework.request.CreateDefenderRuleRequest;
import com.alipay.altershield.defender.framework.request.ModifyDefenderRuleRequest;
import com.alipay.altershield.defender.framework.request.ModifyDefenderRuleStatusRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;

import java.util.List;
import java.util.Set;

/**
 * 变更防御规则service层方法
 *
 * @author haoxuan
 * @version MetaDefenderRuleService.java, v 0.1 2022年08月30日 7:57 下午 haoxuan
 */
public interface MetaDefenderRuleService {

    /**
     * 创建防御规则
     *
     * @param request 创建防御规则请求体
     * @return 创建结果
     */
    AlterShieldResult<Boolean> createRule(CreateDefenderRuleRequest request);

    /**
     * 编辑防御规则
     *
     * @param request 编辑防御规则请求体
     * @return 编辑结果
     */
    AlterShieldResult<Boolean> modifyRule(ModifyDefenderRuleRequest request);

    /**
     * 删除防御规则
     *
     * @param ruleId 防御规则id
     * @return 删除结果
     */
    AlterShieldResult<Boolean> deleteRule(String ruleId);

}