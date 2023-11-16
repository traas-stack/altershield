/**
 * Alipay.com Inc. Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.opscloud.api.defender.enums.DefenderRuleStatusEnum;
import com.alipay.opscloud.api.defender.enums.LogicalOperatorEnum;
import com.alipay.opscloud.api.defender.model.ChangeFilter;
import com.alipay.opscloud.api.defender.model.FilterCondition;
import com.alipay.opscloud.api.defender.model.FilterItemCondition;
import com.alipay.opscloud.api.migration.defender.OldDefenderRuleService;
import com.alipay.opscloud.api.migration.defender.request.ModifyOldDefenderRuleRequest;
import com.alipay.opscloud.api.user.EnhancedUser;
import com.alipay.opscloud.api.user.EnhancedUserService;
import com.alipay.opscloud.common.base.archdomain.entity.MetaArchDomainEntity;
import com.alipay.opscloud.common.base.archdomain.service.MetaArchDomainService;
import com.alipay.opscloud.common.base.user.model.User;
import com.alipay.opscloud.common.base.user.service.UserService;
import com.alipay.opscloud.common.largefield.kv.KeyValueStorage;
import com.alipay.opscloud.common.workflow.ApprovalStatusEnum;
import com.alipay.opscloud.common.workflow.WorkflowService;
import com.alipay.opscloud.common.workflow.request.ApprovalCallbackRequest;
import com.alipay.opscloud.common.workflow.request.ApprovalCancelRequest;
import com.alipay.opscloud.common.workflow.request.ApprovalCreateRequest;
import com.alipay.opscloud.common.workflow.response.ApprovalCreateResponse;
import com.alipay.opscloud.defender.exe.service.ExeApprovalManagerService;
import com.alipay.opscloud.defender.meta.entity.MetaDefenderRuleEntity;
import com.alipay.opscloud.defender.meta.repository.MetaDefenderRuleRepository;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.tools.common.constant.OpsCloudConstant;
import com.alipay.opscloud.tools.common.logger.Loggers;
import com.alipay.opscloud.tools.common.logger.OpsCloudLoggerUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wb-zs909667
 * @version : ExeApprovalManagerService.java, v 0.1 2023-02-08 15:39 wb-zs909667 Exp $$
 */
@Service
public class ExeApprovalManagerServiceImpl implements ExeApprovalManagerService {

    private static final Logger logger = Loggers.DEFENDER;

    public static final String APPROVAL_TITLE_PREFIX = "防御规则上线审批-";

    public static final String APPROVAL_POINT_NAME_PREFIX = "approverList";

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private OldDefenderRuleService oldDefenderRuleService;

    @Autowired
    private MetaDefenderRuleRepository metaDefenderRuleRepository;

    @Autowired
    MetaArchDomainService metaArchDomainService;

    @Autowired
    private KeyValueStorage singleKeyValueStorage;

    @Autowired
    private EnhancedUserService enhancedUserService;


    @Override
    public void approvalEnd(String processInstanceId, String status) {
        OpsCloudLoggerUtil.log("info", logger, String.format("审批完成,进入回调处理逻辑[processInstanceId:%s][status:%s]", processInstanceId, status));
        // 更新防御规则状态
        MetaDefenderRuleEntity entity = metaDefenderRuleRepository.loadByApprovalProcessId(processInstanceId);
        if (entity == null) {
            return;
        }
        // 审批成功状态变为执行中
        if (ApprovalStatusEnum.PASSED.getCode().equals(status)) {
            entity.setStatus(DefenderRuleStatusEnum.ENABLED);
        }
        // 审批失败状态更改为评估中
        if (ApprovalStatusEnum.REJECTED.getCode().equals(status)) {
            entity.setStatus(DefenderRuleStatusEnum.GRAY);
        }
        // 取消审批状态更改为评估中
        if (ApprovalStatusEnum.CANCELLED.getCode().equals(status)) {
            entity.setStatus(DefenderRuleStatusEnum.GRAY);
        }
        updateStatus(entity);
    }

    @Override
    public boolean submitApproval(MetaDefenderRuleEntity entity, User operator) {
        ApprovalCreateRequest request = new ApprovalCreateRequest();
        request.setOperator(operator.getEmpId());
        request.setApprovalTypeCode(OpsCloudConstant.HUANYU_RELATION_EFFECTIVE_CHANGE_TYPE);

        String bpmsName = entity.getName() + "防御规则生效审批";
        request.setTitle(bpmsName);

        Map<String, String> initData = new HashMap<>(9);
        initData.put("relationId", entity.getId());
        initData.put("relationName", entity.getName());
        initData.put("controlKey", entity.getPluginKey());
        if (!Objects.isNull(entity.getChangeFilter()) && !Objects.isNull(entity.getChangeFilter().readObject())) {
            initData.put("changeFilter", convert2changeFilterDesc(entity.getChangeFilter().readObject()));
        }
        initData.put("delayTime", Long.toString(entity.getDelaySecond()));
        initData.put("allowIgnore", entity.getIgnoreTag().getType());
        initData.put("suggestion", entity.getSuggestion());

        String operatorEmpId = operator.getEmpId();
        // 新增效率审批人
        if (OpsCloudConstant.EFFICIENCY_APPROVER_ENABLE && StringUtils.isNotBlank(OpsCloudConstant.BPMS_DEFENSE_RELATION_EFFICIENCY_APPROVER)) {
            List<String> efficiencyApprover = new ArrayList<>(Arrays.asList(OpsCloudConstant.BPMS_DEFENSE_RELATION_EFFICIENCY_APPROVER.split(",")));
            List<String> efficiencyApproverTemp = new ArrayList<>(efficiencyApprover.size());
            for (String approver : efficiencyApprover) {
                efficiencyApproverTemp.add(StringUtils.leftPad(approver, OpsCloudConstant.PROD_EMPID_LENGTH, "0"));
            }
            initData.put("efficiencyApprover", JSON.toJSONString(efficiencyApproverTemp));
        }
        request.setProcessVariables(initData);
        // 增加主管信息
        tryAppendSuperior(initData, operatorEmpId);
        ApprovalCreateResponse response = workflowService.createApproval(request);
        if (response == null) {
            return false;
        } else {
            // 更新防御规则审批流id
            MetaDefenderRuleEntity updateEntity = new MetaDefenderRuleEntity(singleKeyValueStorage);
            updateEntity.setId(entity.getId());
            updateEntity.setApprovalProcessId(response.getApprovalInstanceId());
            updateEntity.setStatus(DefenderRuleStatusEnum.APPROVAL);
            return updateStatus(updateEntity);
        }
    }

    @Override
    public void cancelApproval(MetaDefenderRuleEntity entity, String operator) {
        ApprovalCancelRequest cancelRequest = new ApprovalCancelRequest();
        cancelRequest.setOperator(operator);
        cancelRequest.setProcessInstanceId(entity.getApprovalProcessId());
        // 调用huanyu取消审批
        workflowService.cancelApproval(cancelRequest);
        //规则更新为试运行
        MetaDefenderRuleEntity updateEntity = new MetaDefenderRuleEntity(singleKeyValueStorage);
        updateEntity.setId(entity.getId());
        updateEntity.setApprovalProcessId(entity.getApprovalProcessId());
        updateEntity.setStatus(DefenderRuleStatusEnum.GRAY);
        updateStatus(updateEntity);

    }

    /**
     * 在审批流程变量中增加主管信息
     */
    private void tryAppendSuperior(Map<String, String> initMap, String orginatorId) {
        // 6位工号

        EnhancedUser enhancedUser = enhancedUserService.getUserBossByAccount(orginatorId);
        if (Objects.isNull(enhancedUser)) {
            return;
        }
        // 取一个主管即可
        initMap.put("superior", JSON.toJSONString(enhancedUser.getEmpId()));
    }

    /**
     * *
     * @param updateEntity
     * @return
     */
    private boolean updateStatus(MetaDefenderRuleEntity updateEntity) {
        if (OpsCloudConstant.DEFENSE_DOUBLE_WRITE_SWITCH) {
            return Boolean.TRUE.equals(transactionTemplate.execute(status -> {
                boolean isNewUpdate = metaDefenderRuleRepository.update(updateEntity);

                ModifyOldDefenderRuleRequest modifyOldDefenderRuleRequest = new ModifyOldDefenderRuleRequest();
                modifyOldDefenderRuleRequest.setId(updateEntity.getId());
                modifyOldDefenderRuleRequest.setApproverInfo(updateEntity.getApprovalProcessId());
                modifyOldDefenderRuleRequest.setStatus(updateEntity.getStatus().getStatus());
                boolean doubleWriteRst = oldDefenderRuleService.doubleWrite(modifyOldDefenderRuleRequest);

                if (!isNewUpdate || !doubleWriteRst) {
                    OpsCloudLoggerManager.log("error", Loggers.DEFENDER_MANAGE, "MetaDefenderRuleServiceImpl",
                            "modifyRule", "failed", "new defender rule modify result", isNewUpdate,
                            "old defender rule modify result", doubleWriteRst);
                    status.setRollbackOnly();
                    return false;
                }
                return true;
            }));
        } else {
            return metaDefenderRuleRepository.update(updateEntity);
        }
    }

    /**
     * 规则过滤条件
     *
     * @param changeFilter
     * @return
     */
    private String convert2changeFilterDesc(ChangeFilter changeFilter) {
        List<FilterCondition> changeFilterConditions = changeFilter.getConditions();
        if (CollectionUtils.isEmpty(changeFilterConditions)) {
            return "未设置任何过滤条件,该规则会阻断所有变更";
        }
        StringBuilder changeFilterDesc = new StringBuilder();
        changeFilterDesc.append(convert2MatchDesc(changeFilter.getLogicalOperator())).append("\n");
        for (int i = 0; i < changeFilterConditions.size(); i++) {
            changeFilterDesc.append("\n").append("条件组").append(i + 1).append(":\n").append("当");
            List<FilterItemCondition> filterItemConditions = changeFilterConditions.get(i).getItemConditions();
            for (int j = 0; j < filterItemConditions.size(); j++) {
                FilterItemCondition filterItemCondition = filterItemConditions.get(j);
                String filterConditionName = filterItemCondition.getName().getName();
                String filterConditionMatchType = filterItemCondition.getMatchType().getName();
                String filterConditionValue = "";
                MetaArchDomainEntity metaArchEntity;
                switch (filterItemCondition.getName()) {
                    case INFLUENCE_MAIN_ARCH_DOMAIN_ID:
                        metaArchEntity = metaArchDomainService.queryArchDomainById(Long.valueOf(filterItemCondition.getValue()));
                        if (!Objects.isNull(metaArchEntity)) {
                            filterConditionValue = metaArchEntity.getArchDomainName();
                        }
                        break;
                    case INFLUENCE_SUB_ARCH_DOMAIN_ID:
                        metaArchEntity = metaArchDomainService.queryArchDomainById(Long.valueOf(filterItemCondition.getValue()));
                        if (!Objects.isNull(metaArchEntity)) {
                            filterConditionValue = metaArchEntity.getParentArchDomainName() + "-" + metaArchEntity.getArchDomainName();
                        }
                        break;
                    default:
                        filterConditionValue = filterItemCondition.getValue();
                        break;
                }
                changeFilterDesc.append(filterConditionName)
                        .append(" ").append(filterConditionMatchType)
                        .append(" ").append(filterConditionValue)
                        .append("\n");
                if (j != 0) {
                    changeFilterDesc.append(changeFilterConditions.get(i).getLogicalOperator().getName()).append(" ");
                }
            }
        }
        return changeFilterDesc.toString();
    }

    private String convert2MatchDesc(LogicalOperatorEnum logicalOperatorEnum) {
        if (Objects.equals(LogicalOperatorEnum.OR, logicalOperatorEnum)) {
            return "所有条件同时成立，则防御规则执行";
        }
        return "以下条件任一成立，则防御规则执行";
    }

    @PostConstruct
    public void init() {
        ApprovalCallbackRequest req = new ApprovalCallbackRequest();
        req.setApprovalTypeCode(OpsCloudConstant.HUANYU_RELATION_EFFECTIVE_CHANGE_TYPE);
        req.setInvoker((callbackParam) -> this.approvalEnd(callbackParam.getProcessInstanceId(), callbackParam.getStatus()));
        workflowService.registerApprovalCallback(req);
    }
}
