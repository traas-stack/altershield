/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.enums;

/**
 * @author zhijian.leixiao
 * @version 1.0: ReleaseChangeStatusEnum, v 0.1 2019-08-01 15:03 lx207087 Exp $
 */
public enum ReleaseChangeStatusEnum {

    /**
     * 审批中
     */
    APPROVLING("APPROVLING"),

    /**
     * 审批撤回取消
     */
    CANCEL("CANCEL"),
    /**
     * 审批通过
     */
    PASS("PASS"),
    /**
     * 审批不通过
     */
    REJECT("REJECT");

    private final String type;

    ReleaseChangeStatusEnum(String type) {
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

    public static ReleaseChangeStatusEnum getByType(String type) {
        for (ReleaseChangeStatusEnum e : ReleaseChangeStatusEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
