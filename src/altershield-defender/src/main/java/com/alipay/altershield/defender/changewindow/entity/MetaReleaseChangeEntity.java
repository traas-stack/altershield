/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.entity;

import com.alipay.opscloud.changewindow.enums.ReleaseChangeStatusEnum;
import com.alipay.opscloud.changewindow.vo.ReleaseChangeConfigNode;
import com.alipay.opscloud.common.base.businessdomain.entity.ArchDomainTreeNode;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhijian.leixiao
 * @version 1.0: MetaReleaseChangeEntity, v 0.1 2019-08-01 14:35 lx207087 Exp $
 */
@Data
public class MetaReleaseChangeEntity {
    /**
     * 破网申请id
     */
    private String id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 发起人
     */
    private String operator;
    /**
     * 破网申请名称
     */
    private String name;
    /**
     * 对应封网计划ID
     */
    private String blockChangeId;
    /**
     * 对应封网计划名称
     */
    private String blockChangeName;
    /**
     * 破网开始时间
     */
    private Date startTime;
    /**
     * 破网结束时间
     */
    private Date endTime;
    /**
     * 生效环境
     */
    private List<String> effectEnvironment;
    /**
     * 破网原因类型
     */
    private String reasonType;

    /**
     * 破网原因
     */
    private String reason;
    /**
     * 破网架构域范围
     */
    private List<ArchDomainTreeNode> archDomainRange;
    /**
     * 破网业务域范围
     */
    private List<String> busGroupRange;
    /**
     * 变更类型
     */
    private List<String> changeTargetType;
    /**
     * 破网对象配置
     */
    private List<ReleaseChangeConfigNode> config;
    /**
     * 破网申请状态
     */
    private ReleaseChangeStatusEnum status;
    /**
     * 破网申请审批流id
     */
    private String approvalFlowId;

    /**
     * 业务影响
     */
    private String bizInfluence;

    /**
     * 风险控制方法
     */
    private String riskDescription;

    /**
     * 站点信息
     */
    private String tenant;

    /**
     * 变更源链接
     */
    private String srcUrl;

    /**
     * 变更工单id
     */
    private String nodeId;
}
