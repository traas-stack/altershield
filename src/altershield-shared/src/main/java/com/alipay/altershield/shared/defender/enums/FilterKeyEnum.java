/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * Enumeration of lvalue types supported when matching defense rules
 *
 * @author yuefan.wyf
 * @version $Id: FilterKeyEnum.java, v 0.1 2019年12月18日 下午10:18 yuefan.wyf Exp $
 */
public enum FilterKeyEnum {

    /**
     * Change parameters
     */
    CHANGE_PARAM("changeParam"),

    /**
     * Change result parameters
     */
    CHANGE_RESULT("changeResult"),

    /**
     * Value of the change target
     */
    CHANGE_TARGET_INSTANCE("changeTargetInstance"),

    /**
     * Type of the change target
     */
    CHANGE_TARGET_TYPE("changeTargetType"),

    /**
     * Change title
     */
    CHANGE_TITLE("changeTitle"),

    /**
     * operator of the change
     */
    CHANGE_OPERATOR("changeOperator"),

    /**
     * source platform of the change
     */
    CHANGE_SOURCE_PLATFORM("changeSourcePlatform"),

    /**
     * entrance code of the change
     */
    CHANGE_ENTRANCE_CODE("changeEntranceCode"),

    /**
     * Change scenario code
     */
    CHANGE_SCENARIO_CODE("changeScenarioCode"),

    /**
     * Change environment phase
     */
    CHANGE_PHASE("changePhase"),

    /**
     * name of the batch of the change
     */
    CHANGE_PHASE_BATCH_NAME("changePhaseBatchName"),

    /**
     * number of the batch of the change
     */
    CHANGE_PHASE_BATCH_NO("changePhaseBatchNo"),

    /**
     * the physical cell influenced by the change
     */
    INFLUENCE_IDC("influenceIdc"),

    /**
     * the logical cell influenced by the change
     */
    INFLUENCE_LOGIC_CELL("influenceLogicCell"),

    /**
     * the environment influenced by the change
     */
    INFLUENCE_ENV("influenceEnv"),

    /**
     * the application system influenced by the change
     */
    INFLUENCE_APP("influenceApp"),

    /**
     * the server influenced by the change
     */
    INFLUENCE_HOST("influenceHost"),

    /**
     * the tenant influenced by the change
     */
    INFLUENCE_TENANT("influenceTenant"),
    ;

    /**
     * Type of the filter key
     */
    private final String type;

    FilterKeyEnum(String type) {
        this.type = type;
    }

    /**
     * Gets get type.
     *
     * @return the get type
     */
    public String getType() {
        return type;
    }

    /**
     * get enum value by type.
     *
     * @param type the string value
     * @return the enumeration object
     */
    public static FilterKeyEnum getByCode(String type) {
        for (FilterKeyEnum e : FilterKeyEnum.values()) {
            if (e.getType().equalsIgnoreCase(type)) {
                return e;
            }
        }

        return null;
    }
}