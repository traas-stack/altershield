/**
 * Alipay.com Inc. Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service;

import com.alipay.opscloud.common.base.user.model.User;
import com.alipay.opscloud.defender.meta.entity.MetaDefenderRuleEntity;

/**
 * @author wb-zs909667
 * @version : ExeApprovalManagerService.java, v 0.1 2023-02-08 15:39 wb-zs909667 Exp $$
 */
public interface ExeApprovalManagerService {

    /**
     * 防御规则审批结束回调
     *
     * @param processInstanceId
     * @param status
     */
    void approvalEnd(String processInstanceId, String status);

    /**
     * 防御规则上线提交审批
     *
     * @param entity
     * @param operator
     * @return
     */
    boolean submitApproval(MetaDefenderRuleEntity entity, User operator);

    /**
     * 防御规则取消审批
     *
     * @param entity
     * @param operator
     * @return
     */
    void cancelApproval(MetaDefenderRuleEntity entity, String operator);
}
