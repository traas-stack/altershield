/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * Modify defense rule request structure
 *
 * @author yhaoxuan
 * @version ModifyDefenderRuleRequest.java, v 0.1 2022年08月30日 8:43 下午 yhaoxuan
 */
@ApiModel(value = "Modify defense rule request structure")
public class ModifyDefenderRuleRequest extends CreateDefenderRuleRequest {

    /**
     * rule id
     */
    @ApiModelProperty(value = "rule id")
    private String ruleId;

    /**
     * rule status
     *
     * @see DefenderRuleStatusEnum
     */
    @ApiModelProperty(value = "rule status")
    private String status;

    /**
     * Getter method for property <tt>ruleId</tt>.
     *
     * @return property value of ruleId
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * Setter method for property <tt>ruleId</tt>.
     *
     * @param ruleId value to be assigned to property ruleId
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Convert to Defense Rule Entity
     *
     * @return Defense Rule Entity
     */
    public MetaDefenderRuleEntity convert2Entity() {
        MetaDefenderRuleEntity rule = super.convert2Entity();
        rule.setId(ruleId);
        if (StringUtils.isNotBlank(status)) {
            rule.setStatus(DefenderRuleStatusEnum.getByStatus(status));
        }
        return rule;
    }
}