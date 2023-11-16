/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.result;


import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;

import java.util.Set;

/**
 * Change defense overall check result structure
 *
 * @author yhaoxuan
 * @version DefenderDetectResult.java, v 0.1 2022年08月17日 11:34 上午 yhaoxuan
 */
public class DefenderDetectResult {

    /**
     * Whether the defense logic executed this time
     */
    private boolean defensed;

    /**
     * Whether all change prevention rules have been executed - there will be no waiting in case of timeout
     */
    private boolean allFinish;

    /**
     * This verification group id - corresponds to a pre/post position
     */
    private String detectGroupId;

    /**
     * The detection status of this change of defense
     */
    private DefenderStatusEnum status;

    /**
     * result information of this detection
     */
    private String msg;

    /**
     * Details of the defense rules executed
     */
    private Set<ExeDefenderDetectEntity> defenderDetectRecords;

    /**
     * Build passed results
     *
     * @param detectGroupId detect group id
     * @return the result structure
     */
    public static DefenderDetectResult pass(String detectGroupId) {
        DefenderDetectResult result = new DefenderDetectResult();
        result.setAllFinish(true);
        result.setDefensed(true);
        result.setDetectGroupId(detectGroupId);
        result.setStatus(DefenderStatusEnum.PASS);
        result.setMsg("Detect passed!");

        return result;
    }

    /**
     * Build defense execution exception results
     *
     * @param detectGroupId detect group id
     * @param msg exception message
     * @return the result structure
     */
    public static DefenderDetectResult exception(String detectGroupId, String msg) {
        DefenderDetectResult result = new DefenderDetectResult();
        result.setAllFinish(true);
        result.setDefensed(false);
        result.setDetectGroupId(detectGroupId);
        result.setStatus(DefenderStatusEnum.EXCEPTION);
        result.setMsg(msg);

        return result;
    }

    /**
     * Build defense execution timeout results
     *
     * @param detectGroupId detect group id
     * @param msg timeout message
     * @return the result structure
     */
    public static DefenderDetectResult timeout(String detectGroupId, String msg) {
        DefenderDetectResult result = new DefenderDetectResult();
        result.setAllFinish(true);
        result.setDefensed(false);
        result.setDetectGroupId(detectGroupId);
        result.setStatus(DefenderStatusEnum.TIMEOUT);
        result.setMsg(msg);

        return result;
    }

    /**
     * Getter method for property <tt>defensed</tt>.
     *
     * @return property value of defensed
     */
    public boolean isDefensed() {
        return defensed;
    }

    /**
     * Setter method for property <tt>defensed</tt>.
     *
     * @param defensed value to be assigned to property defensed
     */
    public void setDefensed(boolean defensed) {
        this.defensed = defensed;
    }

    /**
     * Getter method for property <tt>allFinish</tt>.
     *
     * @return property value of allFinish
     */
    public boolean isAllFinish() {
        return allFinish;
    }

    /**
     * Setter method for property <tt>allFinish</tt>.
     *
     * @param allFinish value to be assigned to property allFinish
     */
    public void setAllFinish(boolean allFinish) {
        this.allFinish = allFinish;
    }

    /**
     * Getter method for property <tt>detectGroupId</tt>.
     *
     * @return property value of detectGroupId
     */
    public String getDetectGroupId() {
        return detectGroupId;
    }

    /**
     * Setter method for property <tt>detectGroupId</tt>.
     *
     * @param detectGroupId value to be assigned to property detectGroupId
     */
    public void setDetectGroupId(String detectGroupId) {
        this.detectGroupId = detectGroupId;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public DefenderStatusEnum getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(DefenderStatusEnum status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     *
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter method for property <tt>defenderDetectRecords</tt>.
     *
     * @return property value of defenderDetectRecords
     */
    public Set<ExeDefenderDetectEntity> getDefenderDetectRecords() {
        return defenderDetectRecords;
    }

    /**
     * Setter method for property <tt>defenderDetectRecords</tt>.
     *
     * @param defenderDetectRecords value to be assigned to property defenderDetectRecords
     */
    public void setDefenderDetectRecords(
            Set<ExeDefenderDetectEntity> defenderDetectRecords) {
        this.defenderDetectRecords = defenderDetectRecords;
    }
}