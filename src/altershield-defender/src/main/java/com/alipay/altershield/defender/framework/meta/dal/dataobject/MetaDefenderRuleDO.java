package com.alipay.altershield.defender.framework.meta.dal.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author fujinfei
 */
public class MetaDefenderRuleDO {
    /**
     * Database Column Remarks:
     *   primary key
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String id;

    /**
     * Database Column Remarks:
     *   creation time
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   last modify time
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   rule name
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String name;

    /**
     * Database Column Remarks:
     *   handling suggestion
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String suggestion;

    /**
     * Database Column Remarks:
     *   rule owners, seperated by comma
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String owner;

    /**
     * Database Column Remarks:
     *   pre or post
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String stage;

    /**
     * Database Column Remarks:
     *   rule status
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String status;

    /**
     * Database Column Remarks:
     *   handling strategy when got an exceptino
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String exceptionStrategy;

    /**
     * Database Column Remarks:
     *   defense range type
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String defenseRangeType;

    /**
     * Database Column Remarks:
     *   defense range key
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String defenseRangeKey;

    /**
     * Database Column Remarks:
     *   down streams rule id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String externalId;

    /**
     * Database Column Remarks:
     *   tenant
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tenant;

    /**
     * Database Column Remarks:
     *   delay second
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer delaySecond;

    /**
     * Database Column Remarks:
     *   plugin key
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String pluginKey;

    /**
     * Database Column Remarks:
     *   main class of the plugin
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String mainClass;

    /**
     * Database Column Remarks:
     *   invoke type
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String pluginInvokeType;

    /**
     * Database Column Remarks:
     *   ignore type
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ignoreTag;

    /**
     * Database Column Remarks:
     *   max detect second
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer maxDetectSecond;

    /**
     * Database Column Remarks:
     *   rule arguments
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String argRef;

    /**
     * Database Column Remarks:
     *   change filter
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String changeFilterRef;

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