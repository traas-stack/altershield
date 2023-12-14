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
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.change.exe.service.check.impl;

import com.alipay.altershield.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.altershield.change.exe.service.check.ExeChangeOrderService;
import com.alipay.altershield.change.exe.service.check.model.ChangeExecOrderSubmitRequestModel;
import com.alipay.altershield.change.exe.service.check.model.OpsTraceStack;
import com.alipay.altershield.change.exe.service.convert.ExeChangeOrderRequestConverter;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.logger.intercept.AlterShieldLogAnnotation;
import com.alipay.altershield.common.logger.intercept.DetailLogTypeEnum;
import com.alipay.altershield.common.logger.intercept.DigestLogTypeEnum;
import com.alipay.altershield.common.service.AlterShieldGroupService;
import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.framework.core.change.facade.request.ChangeExecOrderFinishRequest;
import com.alipay.altershield.framework.core.change.facade.result.ChangeFinishStateEnum;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.entity.ExeOrderContext;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import com.alipay.altershield.shared.change.exe.service.ExeChangeOrderQueryService;
import com.alipay.altershield.shared.schedule.event.change.ChangeExecOrderCreateEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Exe change order service.
 *
 * @author yuanji
 * @version : ExeChangeOrderServiceImpl.java, v 0.1 2022年04月01日 2:55 下午 yuanji Exp $
 */
@Service
public class ExeChangeOrderServiceImpl implements ExeChangeOrderService, ExeChangeOrderQueryService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ExeChangeOrderRepository exeChangeOrderRepository;

    @Autowired
    private ExeChangeOrderRequestConverter exeChangeOrderRequestConverter;

    @Autowired
    private AlterShieldGroupService alterShieldGroupService;

    @Value("${altershield.framework.server.common.cloudId}")
    private String cloudId;

    /**
     * create order entity
     * @param metaChangeSceneEntity
     * @param request
     * @return
     */
    @Override
    @AlterShieldLogAnnotation(serviceDesc = "createExeChangeOrder", digestLogType = DigestLogTypeEnum.SERVICE_OPERATE_DIGEST,
            detailLogType = DetailLogTypeEnum.SERVICE_INFO, keyArg = 1, keyProps = "bizExecOrderId")
    public ExeChangeOrderEntity createExeChangeOrder(
            MetaChangeSceneEntity metaChangeSceneEntity, ChangeExecOrderSubmitRequestModel request) {
        ExeChangeOrderEntity entity = convertToOrder(cloudId, request);
        entity.setTenantCode(metaChangeSceneEntity.getTenantCode());
        exeChangeOrderRepository.insert(entity);
        publishChangeExecOrderCreateEvent(metaChangeSceneEntity, entity);
        return entity;
    }

    private void publishChangeExecOrderCreateEvent(MetaChangeSceneEntity metaChangeSceneEntity, ExeChangeOrderEntity entity)
    {
        ChangeExecOrderCreateEvent opsCloudChangeExecOrderCreateEvent = new ChangeExecOrderCreateEvent();
        opsCloudChangeExecOrderCreateEvent.setChangeExecOrderId(entity.getOrderId());
        opsCloudChangeExecOrderCreateEvent.setChangeSceneKey(entity.getChangeSceneKey());
        opsCloudChangeExecOrderCreateEvent.setPlatform(entity.getPlatform());
        opsCloudChangeExecOrderCreateEvent.setGeneration(metaChangeSceneEntity.getGeneration());
    }


    /**
     * Gets get ops trace stack.
     *
     * @param platform       the platform
     * @param bizExecOrderId the biz exec order id
     * @return the get ops trace stack
     */
    @Override
    public OpsTraceStack getOpsTraceStack(String platform, String bizExecOrderId) {

        ExeChangeOrderEntity exeChangeOrderEntity = exeChangeOrderRepository.getChangeOrder(platform, bizExecOrderId);
        if (exeChangeOrderEntity == null) {
            return null;
        }
        ExeOrderContext orderContext = exeChangeOrderEntity.getContextRef();
        OpsChngTrace beforeBatchTrace = null;
        if (orderContext != null) {
            beforeBatchTrace = orderContext.getBeforeBatchTrace();
        }
        return new OpsTraceStack(exeChangeOrderEntity.getTrace(), beforeBatchTrace);
    }


    /**
     * Gets get change order.
     *
     * @param platform       the platform
     * @param bizExecOrderId the biz exec order id
     * @return the get change order
     */
    @Override
    @AlterShieldLogAnnotation(serviceDesc = "getChangeOrder", digestLogType = DigestLogTypeEnum.SERVICE_OPERATE_DIGEST,
            detailLogType = DetailLogTypeEnum.SERVICE_INFO, keyArg = 1, keyProps = "bizExecOrderId")
    public ExeChangeOrderEntity getChangeOrder(String platform, String bizExecOrderId) {
        return exeChangeOrderRepository.getChangeOrder(platform, bizExecOrderId);
    }

    private ExeChangeOrderEntity convertToOrder(String fromCloudId, ChangeExecOrderSubmitRequestModel request) {
        ExeChangeOrderEntity entity = exeChangeOrderRequestConverter.createChangeOrder();
        exeChangeOrderRequestConverter.convert(request, entity);
        String orderId = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_EXE_ORDER, request.getTrace().getTraceId());
        String uid = IdUtil.getUid(orderId);
        entity.setUid(uid);
        entity.setOrderId(orderId);
        entity.setFromCloudId(fromCloudId);
        entity.setDispatchGroup(alterShieldGroupService.getGroup());
        entity.setTenantCode(request.getTenantCode());
        return entity;
    }

    /**
     * Update entity.
     *
     * @param exeChangeOrderEntity the exe change order entity
     * @param request              the request
     */
    @Override
    public void updateEntity(ExeChangeOrderEntity exeChangeOrderEntity, ChangeExecOrderFinishRequest request) {
        exeChangeOrderEntity.setMsg(request.getMsg());
        exeChangeOrderEntity.setStatus(convert(ChangeFinishStateEnum.getByCode(request.getFinishState())));
        exeChangeOrderEntity.setFinishTime(request.getFinishTime());
        if(StringUtils.isNotBlank(request.getChangeResultJsn()))
        {
            exeChangeOrderEntity.getResultRef().write(request.getChangeResultJsn());
        }
        exeChangeOrderRepository.update(exeChangeOrderEntity);
    }

    private ExeOrderStatusEnum convert(ChangeFinishStateEnum finishStateEnum)
    {
        if(finishStateEnum == null)
        {
            return null;
        }
        if(finishStateEnum == ChangeFinishStateEnum.CANCEL)
        {
            return ExeOrderStatusEnum.CANCEL;
        }
        if(finishStateEnum == ChangeFinishStateEnum.ROLLBACK)
        {
            return ExeOrderStatusEnum.ROLLBACK;
        }
        if(finishStateEnum == ChangeFinishStateEnum.SUCCESS)
        {
            return ExeOrderStatusEnum.SUCCESS;
        }
        return null;
    }

    /**
     * Gets get change order.
     *
     * @param orderId the order id
     * @return the get change order
     */
    @Override
    public ExeChangeOrderEntity getChangeOrder(String orderId) {
        if(StringUtils.isBlank(orderId))
        {
            return null;
        }
        return exeChangeOrderRepository.getChangeOrderById(orderId);
    }
}