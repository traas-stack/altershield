/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.exe.service;

import com.alipay.opscloud.common.result.OpsCloudPageResult;
import com.alipay.opscloud.defender.exe.vo.DefenderDetectRecordVO;
import com.alipay.opscloud.defender.request.QueryDefenderDetectRecordRequest;

import java.util.List;

/**
 * 防御执行记录前端查询服务
 *
 * @author haoxuan
 * @version DefenderDetectRecordQueryService.java, v 0.1 2022年11月29日 8:32 下午 haoxuan
 */
public interface DefenderDetectRecordQueryService {

    /**
     * 分页查询防御规则执行记录
     *
     * @param request 查询防御规则执行记录请求结构体
     * @return 查询结果
     */
    OpsCloudPageResult<List<DefenderDetectRecordVO>> queryRuleDetectRecord(QueryDefenderDetectRecordRequest request);
}