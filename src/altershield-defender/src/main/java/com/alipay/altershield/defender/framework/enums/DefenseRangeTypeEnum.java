/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.enums;

/**
 * the defense range of single defender rule enumeration
 *
 * @author yhaoxuan
 * @version DefenseRangeTypeEnum.java, v 0.1 2022年08月25日 9:52 下午 yhaoxuan
 */
public enum DefenseRangeTypeEnum {

    /**
     * related to a change scene
     */
    CHANGE_SCENE("CHANGE_SCENE"),

    /**
     * related to a type of batch of the change scene
     */
    CHANGE_BATCH("CHANGE_BATCH"),

    /**
     * related to a change tag
     */
    CHANGE_TAG("CHANGE_TAG"),
    ;

    /**
     * the defense range type
     */
    private final String type;

    DefenseRangeTypeEnum(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * get enumeration object by string value
     *
     * @param type the string value
     * @return target enumeration object
     */
    public static DefenseRangeTypeEnum getByType(String type) {
        for (DefenseRangeTypeEnum e : DefenseRangeTypeEnum.values()) {
            if (e.getType().equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}