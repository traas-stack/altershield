/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender.model.result;

import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;

/**
 * entity of detect task result
 *
 * @author yhaoxuan
 * @version DefenderDetectPluginResult.java, v 0.1 2022年08月29日 4:22 下午 yhaoxuan
 */
public class DefenderDetectPluginResult {

    /**
     * Whether to execute defense logic this time
     */
    private boolean defensed;

    /**
     * Is the defense verification completed
     */
    private boolean defenseFinished;

    /**
     * Defender rule id, optional
     */
    private String ruleId;

    /**
     * Detection group id, not required
     */
    private String detectGroupId;

    /**
     * The status of this detection
     */
    private DefenderStatusEnum status;

    /**
     * Brief information about this detection
     */
    private String msg;

    /**
     * Details of this detection
     */
    private String resultJson;

    public static DefenderDetectPluginResult exception(String msg){
        return exception(msg, true);
    }

    public static DefenderDetectPluginResult exception(String msg, boolean defenseFinished){
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setDefensed(false);
        result.setMsg(msg);
        result.setStatus(DefenderStatusEnum.EXCEPTION);
        result.setDefenseFinished(defenseFinished);
        return result;
    }

    public static DefenderDetectPluginResult illegalArgument(String msg) {
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setDefensed(false);
        result.setMsg(msg);
        result.setStatus(DefenderStatusEnum.EXCEPTION);
        result.setDefenseFinished(true);
        return result;
    }

    public static DefenderDetectPluginResult success() {
        return success(true);
    }

    public static DefenderDetectPluginResult success(boolean defenseFinished) {
        DefenderDetectPluginResult result = new DefenderDetectPluginResult();
        result.setDefensed(true);
        result.setMsg("success");
        result.setStatus(DefenderStatusEnum.PASS);
        result.setDefenseFinished(defenseFinished);
        return result;
    }

    public boolean isDefenseFinished() {
        return defenseFinished;
    }

    public void setDefenseFinished(boolean defenseFinished) {
        this.defenseFinished = defenseFinished;
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
     * Getter method for property <tt>resultJson</tt>.
     *
     * @return property value of resultJson
     */
    public String getResultJson() {
        return resultJson;
    }

    /**
     * Setter method for property <tt>resultJson</tt>.
     *
     * @param resultJson value to be assigned to property resultJson
     */
    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}