/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.model;

import com.alipay.altershield.shared.defender.enums.LogicalOperatorEnum;
import com.alipay.altershield.shared.defender.request.ChangeFilterRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * the filter condition structure
 *
 * @author yuefan.wyf
 * @version $Id: FilterCondition.java, v 0.1 2019年12月17日 下午9:17 yuefan.wyf Exp $
 */
public class FilterCondition {

    /**
     * logical opreator
     */
    private LogicalOperatorEnum logicalOperator = LogicalOperatorEnum.OR;

    /**
     * filter items list
     */
    private List<FilterItemCondition> itemConditions;

    /**
     * Filter match
     *
     * @param request Match request structure
     * @return Does it match
     */
    public boolean isMatch(ChangeFilterRequest request) {
        return isMatch(request, false);
    }

    /**
     * Filter match
     *
     * @param request Match request structure
     * @return Does it match
     */
    public boolean isMatch(ChangeFilterRequest request, boolean ignoreMatchScope) {
        // default return matched
        if (CollectionUtils.isEmpty(itemConditions)) {
            return true;
        }

        boolean match = LogicalOperatorEnum.AND.equals(logicalOperator);

        for (FilterItemCondition condition : itemConditions) {
            if (LogicalOperatorEnum.AND.equals(logicalOperator)) {
                if (!match) {
                    return match;
                }
                match = match && condition.isMatch(request, ignoreMatchScope);

            } else {

                if (match) {
                    return match;
                }
                match = match || condition.isMatch(request, ignoreMatchScope);
            }
        }

        return match;
    }

    /**
     * Gets get logical operator.
     *
     * @return the get logical operator
     */
    public LogicalOperatorEnum getLogicalOperator() {
        return logicalOperator;
    }

    /**
     * Sets set logical operator.
     *
     * @param logicalOperator the logical operator
     */
    public void setLogicalOperator(LogicalOperatorEnum logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    /**
     * Gets get item conditions.
     *
     * @return the get item conditions
     */
    public List<FilterItemCondition> getItemConditions() {
        return itemConditions;
    }

    /**
     * Sets set item conditions.
     *
     * @param itemConditions the item conditions
     */
    public void setItemConditions(List<FilterItemCondition> itemConditions) {
        this.itemConditions = itemConditions;
    }
}