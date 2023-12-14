/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Query defense rule list request structure
 *
 * @author yhaoxuan
 * @version QueryDefenderRuleListRequest.java, v 0.1 2022年08月30日 7:39 下午 yhaoxuan
 */
@ApiModel(value = "Query defense rule list request structure")
public class QueryDefenderRuleListRequest {

    /**
     * rule id
     */
    private String ruleId;

    /**
     * rule name
     */
    @ApiModelProperty(value = "防御规则名称")
    private String name;

    /**
     * plugin key
     */
    @ApiModelProperty(value = "防御服务")
    private String pluginKey;

    /**
     * rule owners
     */
    @ApiModelProperty(value = "防御规则管理员列表")
    private List<String> owners;

    /**
     * rule status
     *
     * @see DefenderRuleStatusEnum
     */
    @ApiModelProperty(value = "防御规则状态")
    private String status;

    /**
     * tenant
     */
    @ApiModelProperty(value = "防御规则所属租户")
    private String tenant;

    /**
     * pre or post
     */
    @ApiModelProperty(value = "校验阶段 - 前/后置")
    private String stage;

    /**
     * defense range
     */
    @ApiModelProperty(value = "防御规则布防范围")
    private DefenseRange defenseRange;

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
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>owners</tt>.
     *
     * @return property value of owners
     */
    public List<String> getOwners() {
        return owners;
    }

    /**
     * Setter method for property <tt>owners</tt>.
     *
     * @param owners value to be assigned to property owners
     */
    public void setOwners(List<String> owners) {
        this.owners = owners;
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

    /**
     * Getter method for property <tt>tenant</tt>.
     *
     * @return property value of tenant
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Setter method for property <tt>tenant</tt>.
     *
     * @param tenant value to be assigned to property tenant
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * Getter method for property <tt>stage</tt>.
     *
     * @return property value of stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Setter method for property <tt>stage</tt>.
     *
     * @param stage value to be assigned to property stage
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     * Getter method for property <tt>defenseRange</tt>.
     *
     * @return property value of defenseRange
     */
    public DefenseRange getDefenseRange() {
        return defenseRange;
    }

    /**
     * Setter method for property <tt>defenseRange</tt>.
     *
     * @param defenseRange value to be assigned to property defenseRange
     */
    public void setDefenseRange(DefenseRange defenseRange) {
        this.defenseRange = defenseRange;
    }

    /**
     * Getter method for property <tt>pluginKey</tt>.
     *
     * @return property value of pluginKey
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     * Setter method for property <tt>pluginKey</tt>.
     *
     * @param pluginKey value to be assigned to property pluginKey
     */
    public void setPluginKey(String pluginKey) {
        this.pluginKey = pluginKey;
    }

    /**
     * 防御规则布防范围查询结构体
     */
    @ApiModel(value = "防御规则布防范围查询结构体")
    public static class DefenseRange {

        /**
         * 变更场景标识列表
         */
        @ApiModelProperty(value = "变更场景标识列表")
        private List<String> changeSceneKeys;

        /**
         * 关联的变更标签
         */
        @ApiModelProperty(value = "关联的变更标签")
        private List<String> changeTags;

        /**
         * Getter method for property <tt>changeSceneKeys</tt>.
         *
         * @return property value of changeSceneKeys
         */
        public List<String> getChangeSceneKeys() {
            return changeSceneKeys;
        }

        /**
         * Setter method for property <tt>changeSceneKeys</tt>.
         *
         * @param changeSceneKeys value to be assigned to property changeSceneKeys
         */
        public void setChangeSceneKeys(List<String> changeSceneKeys) {
            this.changeSceneKeys = changeSceneKeys;
        }

        /**
         * Getter method for property <tt>changeTags</tt>.
         *
         * @return property value of changeTags
         */
        public List<String> getChangeTags() {
            return changeTags;
        }

        /**
         * Setter method for property <tt>changeTags</tt>.
         *
         * @param changeTags value to be assigned to property changeTags
         */
        public void setChangeTags(List<String> changeTags) {
            this.changeTags = changeTags;
        }
    }
}