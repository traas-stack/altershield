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
/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service.impl;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.defender.framework.exe.repository.ExeDefenderDetectRepository;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.request.ChangeSkipCheckRequest;
import com.alipay.altershield.framework.core.change.facade.result.*;
import com.alipay.altershield.framework.core.risk.model.RiskDefenseRuleFeedbackVO;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckRule;
import com.alipay.altershield.framework.core.risk.model.check.ChangeCheckStatusEnum;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.ExeDefenderDetectService;
import com.alipay.altershield.shared.defender.entity.ExeDefenderDetectEntity;
import com.alipay.altershield.shared.defender.request.DefenderDetectBatchFeedbackRequest;
import com.alipay.altershield.shared.defender.result.DefenderDetectResult;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
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
    public List<ChangeCheckRule> queryDetectStatusByNodeId(String nodeId, DefenseStageEnum stageEnum) {
        if (StringUtils.isBlank(nodeId) || Objects.isNull(stageEnum)) {
            // nodeId or stage is null
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
            // 具体跳过的规则列表
            List<RiskDefenseRuleFeedbackVO> feedbackVOList = request.getFeedbackVOList();
            Set<String> exeRuleIds = feedbackVOList.stream().
                    map(RiskDefenseRuleFeedbackVO::getRuleExeId).collect(Collectors.toSet());

            List<ExeDefenderDetectEntity> exeDefenderDetectEntities = exeDefenderDetectRepository.selectDetectStatusByNodeId(nodeId, defenseStage);
            if (Objects.isNull(exeDefenderDetectEntities)) {
                // 说明该node的前/后置阶段没有命中防御规则，所以根本就不存在跳过的情况
                return AlterShieldResult.notSupport("未查询到防御规则校验实体");
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
        ChangeCheckVerdict opsCloudChangeCheckVerdict = new ChangeCheckVerdict();

        if (Objects.isNull(defenderDetectResult) || !defenderDetectResult.isSuccess() || Objects.isNull(defenderDetectResult.getDomain())) {
            // 防御结果为空 返回状态为 INCONC
            opsCloudChangeCheckVerdict.setVerdict(ChangeCheckVerdictEnum.INCONC.getVerdict());
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

    // --------------------------- 内部方法 -----------------------------

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
        ChangeCheckRule opsCloudChangeCheckRule = new ChangeCheckRule();
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

    private ChangeCheckVerdictEnum convertVerdict(DefenderStatusEnum status) {
        if (Objects.isNull(status)) {
            // default
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