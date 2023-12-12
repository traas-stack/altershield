/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.defender.framework.meta.dal.dataobject;

import java.util.Date;

/**
 *
 * @author xiangyue
 */
public class MetaDefenderRuleDO {
    /**
     * Database Column Remarks:
     *   The primary key
     *
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   Create time
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   Last modify time
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   Rule name
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   Handling suggestions
     *
     *
     * @mbg.generated
     */
    private String suggestion;

    /**
     * Database Column Remarks:
     *   Defense rule administrator, multiple commas separated
     *
     *
     * @mbg.generated
     */
    private String owner;

    /**
     * Database Column Remarks:
     *   Pre or post
     *
     *
     * @mbg.generated
     */
    private String stage;

    /**
     * Database Column Remarks:
     *   Defense rule status-closed/in effect/trial running
     *
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   Rule exception handling strategy - intercept changes/release changes
     *
     *
     * @mbg.generated
     */
    private String exceptionStrategy;

    /**
     * Database Column Remarks:
     *   Defense range type
     *
     *
     * @mbg.generated
     */
    private String defenseRangeType;

    /**
     * Database Column Remarks:
     *   Defense range key
     *
     *
     * @mbg.generated
     */
    private String defenseRangeKey;

    /**
     * Database Column Remarks:
     *   Associated downstream platform rule id, optional
     *
     *
     * @mbg.generated
     */
    private String externalId;

    /**
     * Database Column Remarks:
     *   Tenant to which defense rules belong
     *
     *
     * @mbg.generated
     */
    private String tenant;

    /**
     * Database Column Remarks:
     *   Delayed execution time, unit: seconds
     *
     *
     * @mbg.generated
     */
    private Integer delaySecond;

    /**
     * Database Column Remarks:
     *   Plugin key used by defense rules
     *
     *
     * @mbg.generated
     */
    private String pluginKey;

    /**
     * Database Column Remarks:
     *   Main class of plugin used by defense rules
     *
     *
     * @mbg.generated
     */
    private String mainClass;

    /**
     * Database Column Remarks:
     *   Plugin call type: synchronous, asynchronous
     *
     *
     * @mbg.generated
     */
    private String pluginInvokeType;

    /**
     * Database Column Remarks:
     *   Ignore permissions: IGNORE directly ignores, NOT ALLOW prohibits ignoring
     *
     *
     * @mbg.generated
     */
    private String ignoreTag;

    /**
     * Database Column Remarks:
     *   The maximum detect time, when pre check, is the forward detect time. Unit: seconds
     *
     *
     * @mbg.generated
     */
    private Integer maxDetectSecond;

    /**
     * Database Column Remarks:
     *   Defense rule parameter configuration
     *
     *
     * @mbg.generated
     */
    private String argRef;

    /**
     * Database Column Remarks:
     *   Defense rule filter conditions
     *
     *
     * @mbg.generated
     */
    private String changeFilterRef;

    /**
     *
     * @return the value of altershield_meta_defender_rule.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for altershield_meta_defender_rule.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_meta_defender_rule.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_meta_defender_rule.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for altershield_meta_defender_rule.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.suggestion
     *
     * @mbg.generated
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     *
     * @param suggestion the value for altershield_meta_defender_rule.suggestion
     *
     * @mbg.generated
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.owner
     *
     * @mbg.generated
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner the value for altershield_meta_defender_rule.owner
     *
     * @mbg.generated
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.stage
     *
     * @mbg.generated
     */
    public String getStage() {
        return stage;
    }

    /**
     *
     * @param stage the value for altershield_meta_defender_rule.stage
     *
     * @mbg.generated
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_meta_defender_rule.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.exception_strategy
     *
     * @mbg.generated
     */
    public String getExceptionStrategy() {
        return exceptionStrategy;
    }

    /**
     *
     * @param exceptionStrategy the value for altershield_meta_defender_rule.exception_strategy
     *
     * @mbg.generated
     */
    public void setExceptionStrategy(String exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.defense_range_type
     *
     * @mbg.generated
     */
    public String getDefenseRangeType() {
        return defenseRangeType;
    }

    /**
     *
     * @param defenseRangeType the value for altershield_meta_defender_rule.defense_range_type
     *
     * @mbg.generated
     */
    public void setDefenseRangeType(String defenseRangeType) {
        this.defenseRangeType = defenseRangeType;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.defense_range_key
     *
     * @mbg.generated
     */
    public String getDefenseRangeKey() {
        return defenseRangeKey;
    }

    /**
     *
     * @param defenseRangeKey the value for altershield_meta_defender_rule.defense_range_key
     *
     * @mbg.generated
     */
    public void setDefenseRangeKey(String defenseRangeKey) {
        this.defenseRangeKey = defenseRangeKey;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.external_id
     *
     * @mbg.generated
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     *
     * @param externalId the value for altershield_meta_defender_rule.external_id
     *
     * @mbg.generated
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.tenant
     *
     * @mbg.generated
     */
    public String getTenant() {
        return tenant;
    }

    /**
     *
     * @param tenant the value for altershield_meta_defender_rule.tenant
     *
     * @mbg.generated
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.delay_second
     *
     * @mbg.generated
     */
    public Integer getDelaySecond() {
        return delaySecond;
    }

    /**
     *
     * @param delaySecond the value for altershield_meta_defender_rule.delay_second
     *
     * @mbg.generated
     */
    public void setDelaySecond(Integer delaySecond) {
        this.delaySecond = delaySecond;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.plugin_key
     *
     * @mbg.generated
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     *
     * @param pluginKey the value for altershield_meta_defender_rule.plugin_key
     *
     * @mbg.generated
     */
    public void setPluginKey(String pluginKey) {
        this.pluginKey = pluginKey;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.main_class
     *
     * @mbg.generated
     */
    public String getMainClass() {
        return mainClass;
    }

    /**
     *
     * @param mainClass the value for altershield_meta_defender_rule.main_class
     *
     * @mbg.generated
     */
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.plugin_invoke_type
     *
     * @mbg.generated
     */
    public String getPluginInvokeType() {
        return pluginInvokeType;
    }

    /**
     *
     * @param pluginInvokeType the value for altershield_meta_defender_rule.plugin_invoke_type
     *
     * @mbg.generated
     */
    public void setPluginInvokeType(String pluginInvokeType) {
        this.pluginInvokeType = pluginInvokeType;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.ignore_tag
     *
     * @mbg.generated
     */
    public String getIgnoreTag() {
        return ignoreTag;
    }

    /**
     *
     * @param ignoreTag the value for altershield_meta_defender_rule.ignore_tag
     *
     * @mbg.generated
     */
    public void setIgnoreTag(String ignoreTag) {
        this.ignoreTag = ignoreTag;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.max_detect_second
     *
     * @mbg.generated
     */
    public Integer getMaxDetectSecond() {
        return maxDetectSecond;
    }

    /**
     *
     * @param maxDetectSecond the value for altershield_meta_defender_rule.max_detect_second
     *
     * @mbg.generated
     */
    public void setMaxDetectSecond(Integer maxDetectSecond) {
        this.maxDetectSecond = maxDetectSecond;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.arg_ref
     *
     * @mbg.generated
     */
    public String getArgRef() {
        return argRef;
    }

    /**
     *
     * @param argRef the value for altershield_meta_defender_rule.arg_ref
     *
     * @mbg.generated
     */
    public void setArgRef(String argRef) {
        this.argRef = argRef;
    }

    /**
     *
     * @return the value of altershield_meta_defender_rule.change_filter_ref
     *
     * @mbg.generated
     */
    public String getChangeFilterRef() {
        return changeFilterRef;
    }

    /**
     *
     * @param changeFilterRef the value for altershield_meta_defender_rule.change_filter_ref
     *
     * @mbg.generated
     */
    public void setChangeFilterRef(String changeFilterRef) {
        this.changeFilterRef = changeFilterRef;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", name=").append(name);
        sb.append(", suggestion=").append(suggestion);
        sb.append(", owner=").append(owner);
        sb.append(", stage=").append(stage);
        sb.append(", status=").append(status);
        sb.append(", exceptionStrategy=").append(exceptionStrategy);
        sb.append(", defenseRangeType=").append(defenseRangeType);
        sb.append(", defenseRangeKey=").append(defenseRangeKey);
        sb.append(", externalId=").append(externalId);
        sb.append(", tenant=").append(tenant);
        sb.append(", delaySecond=").append(delaySecond);
        sb.append(", pluginKey=").append(pluginKey);
        sb.append(", mainClass=").append(mainClass);
        sb.append(", pluginInvokeType=").append(pluginInvokeType);
        sb.append(", ignoreTag=").append(ignoreTag);
        sb.append(", maxDetectSecond=").append(maxDetectSecond);
        sb.append(", argRef=").append(argRef);
        sb.append(", changeFilterRef=").append(changeFilterRef);
        sb.append("]");
        return sb.toString();
    }
}