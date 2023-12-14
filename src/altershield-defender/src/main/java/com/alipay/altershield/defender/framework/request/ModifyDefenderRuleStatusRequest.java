/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Modify the defense rule state request struct
 */
@ApiModel(value = "Modify the defense rule state request struct")
public class ModifyDefenderRuleStatusRequest{

    /**
     * Defense rule id
     */
    @ApiModelProperty(value = "Defense rule id")
    private String ruleId;

    /**
     * Defense rule status
     *
     * @see DefenderRuleStatusEnum
     */
    @ApiModelProperty(value = "Defense rule status")
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

}