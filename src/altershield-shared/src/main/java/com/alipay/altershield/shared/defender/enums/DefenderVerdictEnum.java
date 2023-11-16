/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.enums;

/**
 * The detection conclusion enumeration provided by change defense to upstreams
 *
 * @author yhaoxuan
 * @version DefenderVerdictEnum.java, v 0.1 2022年08月30日 11:13 上午 yhaoxuan
 */
public enum DefenderVerdictEnum {

    /**
     * Wait for defense to execute
     */
    WAIT("WAIT"),

    /**
     * Defense check passed
     */
    PASS("PASS"),

    /**
     * Defense check failed
     */
    FAIL("FAIL"),
    ;

    /**
     * detection conclusion
     */
    private final String verdict;

    DefenderVerdictEnum(String verdict) {
        this.verdict = verdict;
    }

    /**
     * Getter method for property <tt>verdict</tt>.
     *
     * @return property value of verdict
     */
    public String getVerdict() {
        return verdict;
    }
}