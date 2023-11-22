/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.defender.framework.exe.repository.ExeDefenderDetectRepository;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdictEnum;
import com.alipay.altershield.framework.core.change.facade.result.ChangeSkipCheckResult;
import com.alipay.altershield.framework.core.risk.model.RiskDefenseRuleFeedbackVO;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckRule;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckStatusEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.ExeDefenderDetectService;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.result.DefenderDetectResult;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    @Override
    public List<ExeDefenderDetectEntity> queryDetectInfoByOrderId(String orderId) {
        if (StringUtils.isBlank(orderId)) {
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
    public List<ChangeCheckRule> queryDetectStatusByNodeId(String nodeId, DefenseStageEnum stageEnum) {
        if (StringUtils.isBlank(nodeId) || Objects.isNull(stageEnum)) {
            return Collections.emptyList();
        }

        return convert2CheckRules(exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, stageEnum));
    }


    @Override
    public AlterShieldResult<ChangeSkipCheckResult> skipChangeCheck(ChangeSkipCheckRequest request) {
        if (Objects.isNull(request) ||
                StringUtils.isBlank(request.getNodeId()) ||
                Objects.isNull(request.getDefenseStage()) ||
                CollectionUtils.isEmpty(request.getFeedbackVOList())) {
            return AlterShieldResult.illegalArgument("request param exception");
        }

        String nodeId = request.getNodeId();
        DefenseStageEnum defenseStage = request.getDefenseStage();

        ChangeSkipCheckResult skipCheckResult = new ChangeSkipCheckResult();
        AlterShieldResult<ChangeSkipCheckResult> result = new AlterShieldResult<>();

        try {
            List<RiskDefenseRuleFeedbackVO> feedbackVOList = request.getFeedbackVOList();
            Set<String> exeRuleIds = feedbackVOList.stream().
                    map(RiskDefenseRuleFeedbackVO::getRuleExeId).collect(Collectors.toSet());

            List<ExeDefenderDetectEntity> exeDefenderDetectEntities = exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, defenseStage);
            if (Objects.isNull(exeDefenderDetectEntities)) {
                return AlterShieldResult.notSupport("No defense rule detection records found");
            }

            List<String> detectExeIds = exeDefenderDetectEntities.stream().
                    map(ExeDefenderDetectEntity::getDetectExeId).
                    filter(exeRuleIds::contains).
                    collect(Collectors.toList());

            boolean isUpdate = exeDefenderDetectRepository.batchUpdateStatusByDetectExeIds(detectExeIds, "Y");
            if (isUpdate) {
                exeDefenderDetectEntities = exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, defenseStage);
                List<DefenderStatusEnum> statusList = exeDefenderDetectEntities.stream()
                        .map(ExeDefenderDetectEntity::getStatus)
                        .collect(Collectors.toList());
                if (!checkIsAllFinished(statusList)) {
                    skipCheckResult.setCheckFinished(false);
                    result.setSuccess(false);
                    result.setMsg("Not all defense rules are finalized");
                    return result;
                }

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
            AlterShieldLoggerManager.log("error", logger, "batchUpdate exception", t.getMessage());
            result.setMsg(t.getMessage());
            result.setSuccess(false);
            result.setResultCode(AlterShieldResultCodeEnum.SYSTEM_ERROR.getCode());
            return result;
        }

        result.setDomain(skipCheckResult);
        result.setSuccess(true);
        return result;
    }

    @Override
    public ChangeCheckVerdict convert2VerdictResult(AlterShieldResult<DefenderDetectResult> defenderDetectResult, boolean emergency) {
        ChangeCheckVerdict changeCheckVerdict = new ChangeCheckVerdict();

        if (Objects.isNull(defenderDetectResult) || !defenderDetectResult.isSuccess() || Objects.isNull(defenderDetectResult.getDomain())) {
            changeCheckVerdict.setVerdict(ChangeCheckVerdictEnum.INCONC.getVerdict());
            changeCheckVerdict.setEmergency(emergency);
            return changeCheckVerdict;
        }

        DefenderDetectResult detectResult = defenderDetectResult.getDomain();

        if (detectResult.getStatus() != null) {
            changeCheckVerdict.setVerdict(convertVerdict(detectResult.getStatus()).getVerdict());
        }
        changeCheckVerdict.setAllFinish(detectResult.isAllFinish());
        changeCheckVerdict.setMsg(detectResult.getMsg());
        if (!CollectionUtils.isEmpty(detectResult.getDefenderDetectRecords())) {
            changeCheckVerdict.setRules(convert2CheckRules(new ArrayList<>(detectResult.getDefenderDetectRecords())));
        }

        changeCheckVerdict.setEmergency(emergency);
        return changeCheckVerdict;
    }

    private List<ChangeCheckRule> convert2CheckRules(List<ExeDefenderDetectEntity> exeDefenderDetectEntityList) {
        if (CollectionUtils.isEmpty(exeDefenderDetectEntityList)) {
            return Collections.emptyList();
        }

        List<ChangeCheckRule> res = new ArrayList<>(exeDefenderDetectEntityList.size());

        for (ExeDefenderDetectEntity exeDefenderDetectEntity : exeDefenderDetectEntityList) {
            res.add(convert2CheckRule(exeDefenderDetectEntity));
        }

        return res;
    }

    private ChangeCheckRule convert2CheckRule(ExeDefenderDetectEntity exeDefenderDetectEntity) {
        ChangeCheckRule changeCheckRule = new ChangeCheckRule();
        changeCheckRule.setRuleExeId(exeDefenderDetectEntity.getDetectExeId());
        if (!Objects.isNull(exeDefenderDetectEntity.getResultRef())) {
            changeCheckRule.setResultJsn(exeDefenderDetectEntity.getResultRef().readObject());
        }
        changeCheckRule.setExternalRuleId(exeDefenderDetectEntity.getExternalId());
        changeCheckRule.setControlKey(exeDefenderDetectEntity.getPluginKey());
        changeCheckRule.setStatus(convertStatus(exeDefenderDetectEntity.getStatus()));
        changeCheckRule.setVerdict(convertVerdict(exeDefenderDetectEntity.getStatus()));
        changeCheckRule.setMsg(exeDefenderDetectEntity.getMsg());
        changeCheckRule.setUrl("");
        changeCheckRule.setRelationId(exeDefenderDetectEntity.getRuleId());
        changeCheckRule.setStage(exeDefenderDetectEntity.getStage());
        changeCheckRule.setRiskLevel("BLOCK");
        changeCheckRule.setName(exeDefenderDetectEntity.getRuleName());
        changeCheckRule.setOwner(exeDefenderDetectEntity.getRuleOwner());
        changeCheckRule.setAllowIgnore(exeDefenderDetectEntity.isAllowIgnore());
        changeCheckRule.setIgnored(exeDefenderDetectEntity.isIgnored());
        changeCheckRule.setDefenseType(null);
        changeCheckRule.setGmtStart(exeDefenderDetectEntity.getGmtStart());
        changeCheckRule.setGmtFinish(exeDefenderDetectEntity.getGmtFinish());
        return changeCheckRule;
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

    private ChangeCheckVerdictEnum convertVerdict(DefenderStatusEnum status) {
        if (Objects.isNull(status)) {
            return ChangeCheckVerdictEnum.INCONC;
        }
        switch (status) {
            case INIT:
            case EXE:
            case ASYNC_CHECK:
            case SUSPEND:
                return ChangeCheckVerdictEnum.NONE;
            case PASS:
            case CANCEL:
                return ChangeCheckVerdictEnum.PASS;
            case FAIL:
                return ChangeCheckVerdictEnum.FAIL;
            case TIMEOUT:
            case EXCEPTION:
            default:
                return ChangeCheckVerdictEnum.INCONC;
        }
    }

    /**
     * * status: INIT、EXE、ASYNC_CHECK、SUSPEND、PASS、FAIL、EXCEPTION、CANCEL、TIMEOUT
     * result： EXE、FAIL、SUCC、TIMEOUT
     * @param status
     * @return
     */
    private ChangeCheckStatusEnum convertStatus(DefenderStatusEnum status) {
        if (Objects.isNull(status)) {
            return null;
        }
        switch (status) {
            case INIT:
            case EXE:
            case ASYNC_CHECK:
            case SUSPEND:
                return ChangeCheckStatusEnum.EXE;
            case PASS:
            case CANCEL:
            case FAIL:
                return ChangeCheckStatusEnum.SUCCESS;
            case EXCEPTION:
                return ChangeCheckStatusEnum.FAIL;
            case TIMEOUT:
            default:
                return ChangeCheckStatusEnum.TIMEOUT;
        }
    }
}