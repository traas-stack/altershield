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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.altershield.change.exe.repository.ExeChangeNodeRepository;
import com.alipay.altershield.change.exe.service.check.ChangeSyncCheckService;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.ChangeCheckVerdict;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author yuanji
 * @version : ChangeSyncCheckServiceImpl.java, v 0.1 2022年11月11日 16:41 yuanji Exp $
 */
@Service
public class ChangeSyncCheckServiceImpl implements ChangeSyncCheckService, InitializingBean, DisposableBean {

    private ThreadPoolTaskExecutor searchTaskThreadPool;

    private ThreadPoolTaskExecutor syncCheckThreadPool;

    @Autowired
    private DefenderDetectService defenderDetectService;


    @Autowired
    private ExeChangeNodeRepository exeChangeNodeRepository;

    @Autowired
    private ExeDefenderDetectService exeDefenderDetectService;

    protected static final Logger logger = Loggers.DEFENDER;

    @Override
    public ChangeCheckVerdict syncCheck(long checkTimeOut, ExeChangeOrderEntity changeOrder, ExeNodeEntity entity,
                                        MetaChangeSceneEntity metaChangeSceneEntity) {

        ChangeCheckVerdict verdict = new ChangeCheckVerdict();
        verdict.setAllFinish(true);
        ExeNodeStateEnum status;
        if(!entity.isEmergency())
        {
            long syncCheckTimeout = checkTimeOut;
            if(syncCheckTimeout <= 0)
            {
                syncCheckTimeout = AlterShieldConstant.SYNC_CHNG_CHECK_TIMEOUT;
            }
            Future<ChangeCheckVerdict> syncCheckFuture = syncCheckThreadPool.submit(() -> doSyncCheck(changeOrder, entity));
            try {
                verdict = syncCheckFuture.get(syncCheckTimeout, TimeUnit.MILLISECONDS);
                status = ExeNodeStateEnum.PRE_AOP_FINISH;
            } catch (TimeoutException e) {
                verdict.setMsg(String.format("获取校验结果超过最大校验时间:%d", AlterShieldConstant.G1_SYNC_DECISION_TAG_TIMEOUT_MS));
                AlterShieldLoggerManager.log("error", logger, e, "异步校验线程执行时异常", "nodeId=" + entity.getNodeExeId());
                status = ExeNodeStateEnum.PRE_AOP_TIMEOUT;
            } catch (Exception e) {
                //检查失败
                verdict.setMsg("check fail, paas all");
                AlterShieldLoggerManager.log("error", logger, e, "异步校验线程执行时异常", "nodeId=" + entity.getNodeExeId());
                status = ExeNodeStateEnum.PRE_AOP_FAIL;
            }
        }
        else
        {
            status = ExeNodeStateEnum.PRE_AOP_FINISH;
            verdict.setMsg("应急变更，忽略前置检查");
        }

        entity.setStatus(status);
        entity.setMsg(verdict.getMsg());

        //前置防御执行完成一定是从前置检查执行中的状态改过来的
        List<ExeNodeStateEnum> oldState = new ArrayList<>();
        oldState.add(ExeNodeStateEnum.PRE_AOP);
        oldState.add(ExeNodeStateEnum.PRE_AOP_SUBMIT);
        exeChangeNodeRepository.updateNodeCheckInfoWithOldStatus(entity, oldState);
        return verdict;
    }

    private ChangeCheckVerdict doSyncCheck(ExeChangeOrderEntity changeOrder, ExeNodeEntity entity) {
        AlterShieldResult<DefenderDetectResult> defenderResult = defenderDetectService.syncDetect(
                buildPreCheckRequest(entity, null, changeOrder));
        ChangeCheckVerdict result = exeDefenderDetectService.convert2VerdictResult(defenderResult, entity.isEmergency());
        entity.getExeNodeCheckInfo().setPreCheckPass(Boolean.TRUE);
        entity.getExeNodeCheckInfo().markFinish(DefenseStageEnum.PRE);
        return result;
    }


    /**
     * 构造defender request
     *
     * @param entity
     * @param tagIds
     * @return
     */
    private DefenderDetectRequest buildPreCheckRequest(ExeNodeEntity entity, Set<String> tagIds, ExeChangeOrderEntity changeOrder) {
        // 1、组装基本信息
        DefenderDetectRequest request = new DefenderDetectRequest();
        request.setChangeOrderId(entity.getOrderId());
        request.setChangeSceneKey(entity.getChangeSceneKey());
        request.setNodeId(entity.getNodeExeId());
        request.setChangeKey(entity.getChangeKey());
        request.setEmergency(entity.isEmergency());
        request.setChangeTagIds(tagIds);
        request.setDefenseStage(DefenseStageEnum.PRE);
        // 组装变更原始信息
        ChangeBaseInfo changeBaseInfo = new ChangeBaseInfo();
        changeBaseInfo.setChangeTitle(changeOrder.getChangeTitle());
        changeBaseInfo.setCreator(changeOrder.getCreator());
        changeBaseInfo.setExecutor(changeOrder.getExecutor());
        changeBaseInfo.setPlatform(changeOrder.getPlatform());
        changeBaseInfo.setChangeParamJSON(changeOrder.getParamRef().readObject());
        ChangeContent changeContent = new ChangeContent();
        List<OpsCloudChangeContent> contents = changeOrder.getChangeContentRef().readObject();
        if (!CollectionUtils.isEmpty(contents)) {
            // 这一步有点恶心，解析OpsCloudChangeContent的结构，默认使用第一个content的类型，因为一个工单只有一个content类型
            changeContent.setChangeContentType(contents.get(0).getContentType().getTypeName());
            changeContent.setChangeContentInstance(contents.stream().map(OpsCloudChangeContent::getInstanceName)
                    .collect(Collectors.toList()));
        }
        changeBaseInfo.setChangeContent(changeContent);
        request.setChangeBaseInfo(changeBaseInfo);
        ChangeExecuteInfo changeExecuteInfo = new ChangeExecuteInfo();
        changeExecuteInfo.setChangeStartTime(entity.getStartTime());
        if (entity.getFinishTime() != null) {
            changeExecuteInfo.setChangeFinishTime(entity.getFinishTime());
        }
        changeExecuteInfo.setOrderPhase(MetaChangeStepTypeEnum.STEP_ORDER.equals(entity.getChangeStepType()));
        if (entity.getChangePhase() != null) {
            changeExecuteInfo.setChangePhase(entity.getChangePhase().getPhase());
        }

        // 如果不是工单级别的node，获取批次信息
        if (!MetaChangeStepTypeEnum.STEP_ORDER.equals(entity.getChangeStepType())) {
            ExeBatchNodeEntity batchNodeEntity = (ExeBatchNodeEntity) entity;
            if (batchNodeEntity.getChangeEffectiveInfoRef() != null
                    && batchNodeEntity.getChangeEffectiveInfoRef().readObject() != null) {
                ExeChangeBatchEffectiveInfoEntity effectiveInfoEntity = batchNodeEntity.getChangeEffectiveInfoRef().readObject();
                changeExecuteInfo.setBatchNo(effectiveInfoEntity.getBatchNo());
                changeExecuteInfo.setLastBatch(
                        OpsCloudChangePhaseLastBatchEnum.LAST.getTag().equals(effectiveInfoEntity.getIsLastBatchTag()));
                changeExecuteInfo.setLastBatchInPhase(
                        OpsCloudChangePhaseLastBatchEnum.LAST.getTag().equals(effectiveInfoEntity.getIsLastBatchInPhaseTag()));
                changeExecuteInfo.setTotalBatchNum(effectiveInfoEntity.getTotalBatchNum());
                changeExecuteInfo.setTotalBatchInPhase(effectiveInfoEntity.getTotalBatchInEnvNum());
            }
        }
        request.setChangeExecuteInfo(changeExecuteInfo);
        return request;
    }

    @Override
    public synchronized void afterPropertiesSet()  {
        if(searchTaskThreadPool == null)
        {
            searchTaskThreadPool = create("searchThreadPool", 50);
        }
        if(syncCheckThreadPool == null)
        {
            syncCheckThreadPool = create("syncCheckThreadPool", 50);
        }
    }

    @Override
    public synchronized  void destroy() throws Exception {
        if(searchTaskThreadPool != null)
        {
            searchTaskThreadPool.shutdown();
        }
        if(syncCheckThreadPool != null)
        {
            syncCheckThreadPool.shutdown();
        }
    }

    private ThreadPoolTaskExecutor create(String threadName, int size)
    {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数信息
        taskExecutor.setCorePoolSize(size);
        taskExecutor.setMaxPoolSize(size);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix(threadName + "--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }

}