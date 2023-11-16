package com.alipay.altershield.defender.framework.exe.dal.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author fujinfei
 */
public class OpsCloudExeDefenderDetectDO {
    /**
     * Database Column Remarks:
     *   主键，执行记录id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String detectExeId;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   校验分组id，对应一次前/后置
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String detectGroupId;

    /**
     * Database Column Remarks:
     *   关联的变更工单id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String changeOrderId;

    /**
     * Database Column Remarks:
     *   关联的变更节点id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String nodeId;

    /**
     * Database Column Remarks:
     *   对应防御规则id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ruleId;

    /**
     * Database Column Remarks:
     *   对应防御规则名称
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ruleName;

    /**
     * Database Column Remarks:
     *   对应防御规则管理员
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ruleOwner;

    /**
     * Database Column Remarks:
     *   防御插件标识
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String pluginKey;

    /**
     * Database Column Remarks:
     *   防御校验状态
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String status;

    /**
     * Database Column Remarks:
     *   本条校验是否阻断变更执行
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String blocked;

    /**
     * Database Column Remarks:
     *   阻断变更执行原因
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String blockMsg;

    /**
     * Database Column Remarks:
     *   本次校验结果简要信息
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String msg;

    /**
     * Database Column Remarks:
     *   本次校验结果详情信息
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String resultRef;

    /**
     * Database Column Remarks:
     *   计划开始时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtPlan;

    /**
     * Database Column Remarks:
     *   实际开始时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtStart;

    /**
     * Database Column Remarks:
     *   实际完成时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtFinish;

    /**
     * Database Column Remarks:
     *   防御校验结果是否被忽略-Y是;N否
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ignored;

    /**
     * Database Column Remarks:
     *   用户反馈状态
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String feedbackStatus;

    /**
     * Database Column Remarks:
     *   用户反馈详情原因
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String feedbackReason;

    /**
     * Database Column Remarks:
     *   反馈人
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String feedbackOperator;

    /**
     * Database Column Remarks:
     *   分表位：00~99
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String uid;

    /**
     * Database Column Remarks:
     *   下游平台防御规则id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String externalId;

    /**
     * Database Column Remarks:
     *   校验阶段 - 前/后置
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String stage;

    /**
     * Database Column Remarks:
     *   防御插件入口类
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String mainClass;

    /**
     * Database Column Remarks:
     *   防御规则状态：试运行、线上等
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String ruleStatus;

    /**
     * Database Column Remarks:
     *   规则是否允许忽略
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String allowIgnore;

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
        sb.append(", detectExeId=").append(detectExeId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", detectGroupId=").append(detectGroupId);
        sb.append(", changeOrderId=").append(changeOrderId);
        sb.append(", nodeId=").append(nodeId);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", ruleName=").append(ruleName);
        sb.append(", ruleOwner=").append(ruleOwner);
        sb.append(", pluginKey=").append(pluginKey);
        sb.append(", status=").append(status);
        sb.append(", blocked=").append(blocked);
        sb.append(", blockMsg=").append(blockMsg);
        sb.append(", msg=").append(msg);
        sb.append(", resultRef=").append(resultRef);
        sb.append(", gmtPlan=").append(gmtPlan);
        sb.append(", gmtStart=").append(gmtStart);
        sb.append(", gmtFinish=").append(gmtFinish);
        sb.append(", ignored=").append(ignored);
        sb.append(", feedbackStatus=").append(feedbackStatus);
        sb.append(", feedbackReason=").append(feedbackReason);
        sb.append(", feedbackOperator=").append(feedbackOperator);
        sb.append(", uid=").append(uid);
        sb.append(", externalId=").append(externalId);
        sb.append(", stage=").append(stage);
        sb.append(", mainClass=").append(mainClass);
        sb.append(", ruleStatus=").append(ruleStatus);
        sb.append(", allowIgnore=").append(allowIgnore);
        sb.append("]");
        return sb.toString();
    }
}