/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service.impl;

import com.alipay.opscloud.api.defender.ExeDefenderDetectService;
import com.alipay.opscloud.api.defender.entity.ExeDefenderDetectEntity;
import com.alipay.opscloud.api.defender.request.DefenderDetectBatchFeedbackRequest;
import com.alipay.opscloud.api.defender.result.DefenderDetectResult;
import com.alipay.opscloud.api.migration.defender.OldDefenderRuleFeedbackService;
import com.alipay.opscloud.defender.exe.repository.ExeDefenderDetectRepository;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalErrorCode;
import com.alipay.opscloud.framework.common.util.exception.OpsCloudInternalException;
import com.alipay.opscloud.framework.common.util.logger.OpsCloudLoggerManager;
import com.alipay.opscloud.framework.core.change.facade.request.OpsCloudChangeSkipCheckRequest;
import com.alipay.opscloud.framework.core.change.facade.result.OpsCloudChangeSkipCheckResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResultCodeEnum;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdict;
import com.alipay.opscloud.framework.core.common.result.OpsCloudChangeCheckVerdictEnum;
import com.alipay.opscloud.framework.core.risk.model.RiskDefenseRuleFeedbackVO;
import com.alipay.opscloud.framework.core.risk.model.check.OpsCloudChangeCheckRule;
import com.alipay.opscloud.framework.core.risk.model.check.OpsCloudChangeCheckStatusEnum;
import com.alipay.opscloud.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.opscloud.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.opscloud.tools.common.logger.Loggers;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fujinfei
 * @version ExeDefenderDetectServiceImpl.java, v 0.1 2022年10月19日 15:52 fujinfei
 */
@Service
public class ExeDefenderDetectServiceImpl implements ExeDefenderDetectService {

    private static final Logger logger = Loggers.DEFENDER;

    @Autowired
    private ExeDefenderDetectRepository exeDefenderDetectRepository;

    @Autowired
    private OldDefenderRuleFeedbackService oldDefenderRuleFeedbackService;

    @Override
    public List<ExeDefenderDetectEntity> queryDetectInfoByOrderId(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            // orderId 为空
            return Collections.emptyList();
        }

        return exeDefenderDetectRepository.selectByChangeOrderId(orderId);
    }

    @Override
    public List<ExeDefenderDetectEntity> queryDetectInfoByNodeId(String nodeId, DefenseStageEnum stageEnum) {
        if (StringUtils.isBlank(nodeId) || Objects.isNull(stageEnum)) {
            // nodeId or stage is null
            return Collections.emptyList();
        }

        return exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, stageEnum);
    }

    @Override
    public List<OpsCloudChangeCheckRule> queryDetectStatusByNodeId(String nodeId, DefenseStageEnum stageEnum) {
        if (StringUtils.isBlank(nodeId) || Objects.isNull(stageEnum)) {
            // nodeId or stage is null
            return Collections.emptyList();
        }

        return convert2CheckRules(exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, stageEnum));
    }


    @Override
    public OpsCloudResult<OpsCloudChangeSkipCheckResult> skipChangeCheck(OpsCloudChangeSkipCheckRequest request) {
        if (Objects.isNull(request) ||
                StringUtils.isBlank(request.getNodeId()) ||
                Objects.isNull(request.getDefenseStage()) ||
                CollectionUtils.isEmpty(request.getFeedbackVOList())) {
            return OpsCloudResult.illegalArgument("request param exception");
        }

        String nodeId = request.getNodeId();
        DefenseStageEnum defenseStage = request.getDefenseStage();

        OpsCloudChangeSkipCheckResult skipCheckResult = new OpsCloudChangeSkipCheckResult();
        OpsCloudResult<OpsCloudChangeSkipCheckResult> result = new OpsCloudResult<>();

        try {
            // 具体跳过的规则列表
            List<RiskDefenseRuleFeedbackVO> feedbackVOList = request.getFeedbackVOList();
            Set<String> exeRuleIds = feedbackVOList.stream().
                    map(RiskDefenseRuleFeedbackVO::getRuleExeId).collect(Collectors.toSet());

            List<ExeDefenderDetectEntity> exeDefenderDetectEntities = exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, defenseStage);
            if (Objects.isNull(exeDefenderDetectEntities)) {
                // 说明该node的前/后置阶段没有命中防御规则，所以根本就不存在跳过的情况
                return OpsCloudResult.notSupport("未查询到防御规则校验实体");
            }

            List<String> detectExeIds = exeDefenderDetectEntities.stream().
                    map(ExeDefenderDetectEntity::getDetectExeId).
                    filter(exeRuleIds::contains).
                    collect(Collectors.toList());

            // 批量更新校验记录的忽略状态
            boolean isUpdate = exeDefenderDetectRepository.batchUpdateStatusByDetectExeIds(detectExeIds, "Y");
            if (isUpdate) {
                exeDefenderDetectEntities = exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, defenseStage);
                List<DefenderStatusEnum> statusList = exeDefenderDetectEntities.stream()
                        .map(ExeDefenderDetectEntity::getStatus)
                        .collect(Collectors.toList());
                if (!checkIsAllFinished(statusList)) {
                    // 如果没有全部处于终态
                    skipCheckResult.setCheckFinished(false);
                    result.setSuccess(false);
                    result.setMsg("校验规则并未全部处于终态");
                    return result;
                }

                // 被阻断且尚未跳过的规则id列表
                Set<String> ids = exeDefenderDetectEntities.stream()
                        .filter(ExeDefenderDetectEntity::isBlocked)
                        .filter(e -> !e.isIgnored())
                        .map(ExeDefenderDetectEntity::getDetectExeId)
                        .collect(Collectors.toSet());
                skipCheckResult.setBlockRuleExeIds(ids);
                skipCheckResult.setCheckFinished(true);
            } else {
                result.setSuccess(false);
                result.setMsg("batchUpdate failed");
                return result;
            }
        } catch (Throwable t) {
            OpsCloudLoggerManager.log("error", logger, "batchUpdate exception", t.getMessage());
            result.setMsg(t.getMessage());
            result.setSuccess(false);
            result.setResultCode(OpsCloudResultCodeEnum.SYSTEM_ERROR.getCode());
            return result;
        }

        result.setDomain(skipCheckResult);
        result.setSuccess(true);
        return result;
    }

    @Override
    public OpsCloudChangeCheckVerdict convert2VerdictResult(OpsCloudResult<DefenderDetectResult> defenderDetectResult, boolean emergency) {
        OpsCloudChangeCheckVerdict opsCloudChangeCheckVerdict = new OpsCloudChangeCheckVerdict();

        if (Objects.isNull(defenderDetectResult) || !defenderDetectResult.isSuccess() || Objects.isNull(defenderDetectResult.getDomain())) {
            // 防御结果为空 返回状态为 INCONC
            opsCloudChangeCheckVerdict.setVerdict(OpsCloudChangeCheckVerdictEnum.INCONC.getVerdict());
            opsCloudChangeCheckVerdict.setEmergency(emergency);
            return opsCloudChangeCheckVerdict;
        }

        DefenderDetectResult detectResult = defenderDetectResult.getDomain();

        if (detectResult.getStatus() != null) {
            opsCloudChangeCheckVerdict.setVerdict(convertVerdict(detectResult.getStatus()).getVerdict());
        }
        opsCloudChangeCheckVerdict.setAllFinish(detectResult.isAllFinish());
        opsCloudChangeCheckVerdict.setMsg(detectResult.getMsg());
        if (!CollectionUtils.isEmpty(detectResult.getDefenderDetectRecords())) {
            opsCloudChangeCheckVerdict.setRules(convert2CheckRules(new ArrayList<>(detectResult.getDefenderDetectRecords())));
        }

//        opsCloudChangeCheckVerdict.setRollback(); // 是否回滚，默认不回滚
        opsCloudChangeCheckVerdict.setEmergency(emergency);
        return opsCloudChangeCheckVerdict;
    }

    /**
     * 批量反馈
     *
     * @param request 批量反馈请求结构体
     * @return 反馈是否成功
     */
    @Override
    public Boolean batchFeedback(DefenderDetectBatchFeedbackRequest request) {
        // TODO
        // 目前仅支持对老变更核心表进行操作
        if (Objects.isNull(request) || CollectionUtils.isEmpty(request.getDetectExeIds())) {
            OpsCloudLoggerManager.log("error", logger, "param exception");
            throw new OpsCloudInternalException(OpsCloudInternalErrorCode.FLOW_PARAM_ERROR, "参数错误");
        }

        return oldDefenderRuleFeedbackService.batchFeedback(request);
    }

    // --------------------------- 内部方法 -----------------------------

    private List<OpsCloudChangeCheckRule> convert2CheckRules(List<ExeDefenderDetectEntity> exeDefenderDetectEntityList) {
        if (CollectionUtils.isEmpty(exeDefenderDetectEntityList)) {
            return Collections.emptyList();
        }

        List<OpsCloudChangeCheckRule> res = new ArrayList<>(exeDefenderDetectEntityList.size());

        for (ExeDefenderDetectEntity exeDefenderDetectEntity : exeDefenderDetectEntityList) {
            res.add(convert2CheckRule(exeDefenderDetectEntity));
        }

        return res;
    }

    private OpsCloudChangeCheckRule convert2CheckRule(ExeDefenderDetectEntity exeDefenderDetectEntity) {
        OpsCloudChangeCheckRule opsCloudChangeCheckRule = new OpsCloudChangeCheckRule();
        opsCloudChangeCheckRule.setRuleExeId(exeDefenderDetectEntity.getDetectExeId());
        if (!Objects.isNull(exeDefenderDetectEntity.getResultRef())) {
            opsCloudChangeCheckRule.setResultJsn(exeDefenderDetectEntity.getResultRef().readObject());
        }
        opsCloudChangeCheckRule.setExternalRuleId(exeDefenderDetectEntity.getExternalId());
        opsCloudChangeCheckRule.setControlKey(exeDefenderDetectEntity.getPluginKey());
        opsCloudChangeCheckRule.setStatus(convertStatus(exeDefenderDetectEntity.getStatus()));
        opsCloudChangeCheckRule.setVerdict(convertVerdict(exeDefenderDetectEntity.getStatus()));
        opsCloudChangeCheckRule.setMsg(exeDefenderDetectEntity.getMsg());
        // 校验规则url 默认值：""
        opsCloudChangeCheckRule.setUrl("");
        opsCloudChangeCheckRule.setRelationId(exeDefenderDetectEntity.getRuleId());
        opsCloudChangeCheckRule.setStage(exeDefenderDetectEntity.getStage());
//        opsCloudChangeCheckRule.setGrayStatus(exeDefenderDetectEntity.); // 是否处于灰度状态 TODO 等数据迁移分支合过来
        // 风险等级，默认阻断
        opsCloudChangeCheckRule.setRiskLevel("BLOCK");
        opsCloudChangeCheckRule.setName(exeDefenderDetectEntity.getRuleName());
        opsCloudChangeCheckRule.setOwner(exeDefenderDetectEntity.getRuleOwner());
        opsCloudChangeCheckRule.setAllowIgnore(exeDefenderDetectEntity.isAllowIgnore());
        opsCloudChangeCheckRule.setIgnored(exeDefenderDetectEntity.isIgnored());
        // 防御防线类型 默认空
        opsCloudChangeCheckRule.setDefenseType(null);
        opsCloudChangeCheckRule.setGmtStart(exeDefenderDetectEntity.getGmtStart());
        opsCloudChangeCheckRule.setGmtFinish(exeDefenderDetectEntity.getGmtFinish());
        return opsCloudChangeCheckRule;
    }

    private boolean checkIsAllFinished(List<DefenderStatusEnum> statusEnumList){
        if (CollectionUtils.isEmpty(statusEnumList)) {
            return false;
        }

        for (DefenderStatusEnum status : statusEnumList) {
            if (!checkIsFinished(status)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIsFinished(DefenderStatusEnum status){
        if (Objects.isNull(status)) {
            return false;
        }
        switch (status) {
            case PASS:
            case FAIL:
            case TIMEOUT:
            case CANCEL:
            case EXCEPTION:
                return true;
            case INIT:
            case EXE:
            case ASYNC_CHECK:
            case SUSPEND:
            default:
                return false;
        }
    }

    private OpsCloudChangeCheckVerdictEnum convertVerdict(DefenderStatusEnum status) {
        if (Objects.isNull(status)) {
            // default
            return OpsCloudChangeCheckVerdictEnum.INCONC;
        }
        switch (status) {
            case INIT:
            case EXE:
            case ASYNC_CHECK:
            case SUSPEND:
                return OpsCloudChangeCheckVerdictEnum.NONE;
            case PASS:
            case CANCEL:
                return OpsCloudChangeCheckVerdictEnum.PASS;
            case FAIL:
                return OpsCloudChangeCheckVerdictEnum.FAIL;
            case TIMEOUT:
            case EXCEPTION:
            default:
                return OpsCloudChangeCheckVerdictEnum.INCONC;
        }
    }

    /**
     * * status: INIT、EXE、ASYNC_CHECK、SUSPEND、PASS、FAIL、EXCEPTION、CANCEL、TIMEOUT
     * result： EXE、FAIL、SUCC、TIMEOUT
     * @param status
     * @return
     */
    private OpsCloudChangeCheckStatusEnum convertStatus(DefenderStatusEnum status) {
        if (Objects.isNull(status)) {
            return null;
        }
        switch (status) {
            case INIT:
            case EXE:
            case ASYNC_CHECK:
            case SUSPEND:
                return OpsCloudChangeCheckStatusEnum.EXE;
            case PASS:
            case CANCEL:
            case FAIL:
                return OpsCloudChangeCheckStatusEnum.SUCC;
            case EXCEPTION:
                return OpsCloudChangeCheckStatusEnum.FAIL;
            case TIMEOUT:
            default:
                return OpsCloudChangeCheckStatusEnum.TIMEOUT;
        }
    }
}