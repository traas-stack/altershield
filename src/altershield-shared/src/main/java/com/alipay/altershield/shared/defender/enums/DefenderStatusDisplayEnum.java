/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;


import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeration used to display and query defense detection status to users
 *
 * @author yhaoxuan
 * @version DefenderStatusDisplayEnum.java, v 0.1 2022年12月01日 11:01 上午 yhaoxuan
 */
public enum DefenderStatusDisplayEnum {

    /**
     * Executing
     */
    EXE("EXE", Arrays.asList(DefenderStatusEnum.EXE, DefenderStatusEnum.INIT, DefenderStatusEnum.ASYNC_CHECK,
            DefenderStatusEnum.SUSPEND)),

    /**
     * Pass
     */
    PASS("PASS", Arrays.asList(DefenderStatusEnum.PASS)),

    /**
     * Got a risk (block the change)
     */
    FAIL("FAIL", Arrays.asList(DefenderStatusEnum.FAIL)),

    /**
     * Got an exception when detecting
     */
    EXCEPTION("EXCEPTION", Arrays.asList(DefenderStatusEnum.EXCEPTION)),

    /**
     * Ignored
     */
    IGNORED("IGNORED", Arrays.asList());
    ;

    /**
     * Status displayed to users
     */
    private final String status;

    /**
     * Corresponding backend status enumeration
     */
    private final List<DefenderStatusEnum> backgroundStatus;

    DefenderStatusDisplayEnum(String status, List<DefenderStatusEnum> backgroundStatus) {
        this.status = status;
        this.backgroundStatus = backgroundStatus;
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
     * Getter method for property <tt>backgroundStatus</tt>.
     *
     * @return property value of backgroundStatus
     */
    public List<DefenderStatusEnum> getBackgroundStatus() {
        return backgroundStatus;
    }

    /**
     * get enum value by status.
     *
     * @param status the status
     * @return the target enum value
     */
    public static DefenderStatusDisplayEnum getByStatus(String status) {
        DefenderStatusDisplayEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            DefenderStatusDisplayEnum e = var1[var3];
            if (e.getStatus().equalsIgnoreCase(status)) {
                return e;
            }
        }

        return null;
    }
}