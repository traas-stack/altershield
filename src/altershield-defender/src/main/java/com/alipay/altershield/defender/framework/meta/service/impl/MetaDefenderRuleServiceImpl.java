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
 * Alipay.com Inc. Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.meta.service.impl;

import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.common.util.DateUtil;
import com.alipay.altershield.defender.framework.AbstractDefenderService;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.defender.framework.meta.repository.MetaDefenderRuleRepository;
import com.alipay.altershield.defender.framework.meta.service.MetaDefenderRuleService;
import com.alipay.altershield.defender.framework.request.CreateDefenderRuleRequest;
import com.alipay.altershield.defender.framework.request.ModifyDefenderRuleRequest;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author jinyalong
 * @version : MetaDefenderRuleServiceImpl.java, v 0.1 2023-12-08 10:03 jinyalong Exp $$
 */
@Service
public class MetaDefenderRuleServiceImpl extends AbstractDefenderService implements MetaDefenderRuleService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MetaDefenderRuleRepository metaDefenderRuleRepository;

    @Override
    public AlterShieldResult<Boolean> createRule(CreateDefenderRuleRequest request) {
        return ServiceProcessTemplate.process(() -> {
            MetaDefenderRuleEntity rule = request.convert2Entity();
            String ruleId = idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_DEFENDER_RULE_ID);
            rule.setId(ruleId);
            rule.setStatus(DefenderRuleStatusEnum.DISABLED);
            rule.setGmtCreate(DateUtil.getNowDate());
            rule.setGmtModified(DateUtil.getNowDate());
            return metaDefenderRuleRepository.insert(rule);
        });
    }

    @Override
    public AlterShieldResult<Boolean> modifyRule(ModifyDefenderRuleRequest request) {
        return ServiceProcessTemplate.process(() -> {
            MetaDefenderRuleEntity rule = request.convert2Entity();
//            // 如果规则审批中，编辑后 更新为试运行
//            if (DefenderRuleStatusEnum.APPROVAL.equals(rule.getStatus())) {
//                rule.setStatus(DefenderRuleStatusEnum.GRAY);
//            }
            rule.setGmtModified(DateUtil.getNowDate());

            return metaDefenderRuleRepository.update(rule);
        });
    }

    @Override
    public AlterShieldResult<Boolean> deleteRule(String ruleId) {
        return ServiceProcessTemplate.process(() -> transactionTemplate.execute(status -> {
            try {
                return metaDefenderRuleRepository.delete(ruleId);
            } catch (Exception e) {
                AlterShieldLoggerManager.log("error", Loggers.DEFENDER, "MetaDefenderRuleServiceImpl",
                        "deleteRule", "failed", "got an exception", e);
                status.setRollbackOnly();
                return false;
            }
        }));
    }


}
