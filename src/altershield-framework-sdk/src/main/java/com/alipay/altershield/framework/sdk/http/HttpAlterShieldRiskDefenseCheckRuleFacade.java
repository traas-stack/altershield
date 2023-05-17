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
package com.alipay.altershield.framework.sdk.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.risk.facade.RiskDefenseCheckRuleFacade;
import com.alipay.altershield.framework.core.risk.facade.request.QueryCheckRulesRequest;
import com.alipay.altershield.framework.core.risk.facade.result.RiskCheckRuleResult;
import static com.alipay.altershield.framework.sdk.constant.AlterShieldApiConstant.queryRiskDefenseCheckRuleUri;

/**
 *
 * @author yuanji
 * @version : HttpOpsCloudRiskDefenseCheckRuleFacade.java, v 0.1 2022年05月10日 11:47 上午 yuanji Exp $
 */
public class HttpAlterShieldRiskDefenseCheckRuleFacade extends BaseHttpAlterShieldFacade
        implements RiskDefenseCheckRuleFacade {

    @Override
    public AlterShieldResult<RiskCheckRuleResult> retrieveCheckRuleResult(
            QueryCheckRulesRequest request) {
        return postRequest(queryRiskDefenseCheckRuleUri, request, s -> JSON.parseObject(s,
                new TypeReference<AlterShieldResult<RiskCheckRuleResult>>() {}));
    }
}
