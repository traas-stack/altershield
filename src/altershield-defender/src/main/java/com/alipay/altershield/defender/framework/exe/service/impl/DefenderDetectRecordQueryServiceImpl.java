/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service.impl;

import com.alipay.opscloud.api.defender.enums.DefenderStatusDisplayEnum;
import com.alipay.opscloud.api.migration.defender.OldDefenderCheckService;
import com.alipay.opscloud.api.migration.defender.request.QueryOldDefenderCheckRequest;
import com.alipay.opscloud.api.migration.defender.result.OldDefenderCheckRecord;
import com.alipay.opscloud.common.result.OpsCloudPageResult;
import com.alipay.opscloud.common.service.ServicePageProcessTemplate;
import com.alipay.opscloud.common.service.ServicePageQuery;
import com.alipay.opscloud.defender.exe.repository.ExeDefenderDetectRepository;
import com.alipay.opscloud.defender.exe.service.DefenderDetectRecordQueryService;
import com.alipay.opscloud.defender.exe.vo.DefenderDetectRecordVO;
import com.alipay.opscloud.defender.request.QueryDefenderDetectRecordRequest;
import com.alipay.opscloud.tools.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 防御执行记录前端查询服务
 *
 * @author haoxuan
 * @version DefenderDetectRecordQueryServiceImpl.java, v 0.1 2022年11月29日 8:34 下午 haoxuan
 */
@Service
public class DefenderDetectRecordQueryServiceImpl implements DefenderDetectRecordQueryService {

    @Autowired
    private ExeDefenderDetectRepository exeDefenderDetectRepository;

    @Autowired
    private OldDefenderCheckService oldDefenderCheckService;

    @Override
    public OpsCloudPageResult<List<DefenderDetectRecordVO>> queryRuleDetectRecord(QueryDefenderDetectRecordRequest request) {
        return ServicePageProcessTemplate.process(request.getCurrent(), request.getPageSize(),
                new ServicePageQuery<DefenderDetectRecordVO>() {
                    @Override
                    public List<DefenderDetectRecordVO> query(int offset, int limit) {
                        List<OldDefenderCheckRecord> records = oldDefenderCheckService.queryCheckRecord(buildQueryOldCheckRequest(request),
                                offset, limit);
                        return records.stream().filter(Objects::nonNull).map(r -> buildDefenderDetectRecordVO(r))
                                .collect(Collectors.toList());
                    }

                    @Override
                    public long total() {
                        return oldDefenderCheckService.count(buildQueryOldCheckRequest(request));
                    }
                });
    }

    /**
     * 构建防御执行记录视图
     *
     * @param oldDefenderCheckRecord 老防御规则执行记录
     * @return 视图结果
     */
    private DefenderDetectRecordVO buildDefenderDetectRecordVO(OldDefenderCheckRecord oldDefenderCheckRecord) {
        DefenderDetectRecordVO recordVO = new DefenderDetectRecordVO();
        recordVO.setDetectExeId(oldDefenderCheckRecord.getDetectExeId());
        recordVO.setRuleId(oldDefenderCheckRecord.getRuleId());
        recordVO.setRuleName(oldDefenderCheckRecord.getRuleName());
        recordVO.setRuleStatus(oldDefenderCheckRecord.getRuleStatus());
        recordVO.setStatus(oldDefenderCheckRecord.getStatus());
        recordVO.setExternalId(oldDefenderCheckRecord.getExternalId());
        recordVO.setDetectResult(oldDefenderCheckRecord.getDetectResult());
        recordVO.setMsg(oldDefenderCheckRecord.getMsg());
        recordVO.setNodeId(oldDefenderCheckRecord.getNodeId());
        recordVO.setFeedbackStatus(oldDefenderCheckRecord.getFeedbackStatus());
        recordVO.setFeedbackReason(oldDefenderCheckRecord.getFeedbackReason());
        recordVO.setFeedbackOperator(oldDefenderCheckRecord.getFeedbackOperator());
        recordVO.setGmtPlan(oldDefenderCheckRecord.getGmtPlan());
        recordVO.setGmtStart(oldDefenderCheckRecord.getGmtStart());
        recordVO.setGmtFinish(oldDefenderCheckRecord.getGmtFinish());
        return recordVO;
    }

    /**
     * 构建查询老防御规则校验记录请求结构体
     *
     * @param req 查询防御执行记录请求结构体
     * @return 构建结果
     */
    private QueryOldDefenderCheckRequest buildQueryOldCheckRequest(QueryDefenderDetectRecordRequest req) {
        QueryOldDefenderCheckRequest request = new QueryOldDefenderCheckRequest();
        request.setRuleId(req.getRuleId());
        // TODO
        request.setStartTime(DateUtil.formatDate(req.getStartTime(), DateUtil.DATE_JFP_STR_YMD));
        request.setEndTime(DateUtil.formatDate(req.getEndTime(), DateUtil.DATE_JFP_STR_YMD));
        request.setFilterFeedback(req.isFilterFeedback());

        if (!CollectionUtils.isEmpty(req.getStatus())){
            request.setStatus(req.getStatus()
                    .stream()
                    .map(DefenderStatusDisplayEnum::getStatus)
                    .collect(Collectors.toList()));
        }

        return request;
    }
}