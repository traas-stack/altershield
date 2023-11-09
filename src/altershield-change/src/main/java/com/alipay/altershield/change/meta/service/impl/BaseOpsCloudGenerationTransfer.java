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
package com.alipay.altershield.change.meta.service.impl;

import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
import com.alipay.opscloud.change.meta.model.effective.MetaChangeOrderStepEntity;
import com.alipay.opscloud.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.opscloud.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.opscloud.change.meta.service.OpsCloudGenerationTransfer;
import com.alipay.opscloud.change.meta.service.OpsCloudGenerationTransferManager;
import com.alipay.opscloud.common.service.ServiceProcessTemplate;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.framework.core.meta.change.model.enums.MetaChangeSceneGenerationEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 代G转换基类
 *
 * @author yuanji
 * @version : BaseOpsCloudGenerationTransfer.java, v 0.1 2022年10月11日 11:24 yuanji Exp $
 */

public abstract class BaseOpsCloudGenerationTransfer implements OpsCloudGenerationTransfer, InitializingBean {

    @Autowired
    protected MetaChangeSceneRepository metaChangeSceneRepository;

    @Resource
    protected TransactionTemplate confTransactionTemplate;


    @Autowired
    private OpsCloudGenerationTransferManager opsCloudGenerationTransferManager;

    @Override
    public void afterPropertiesSet() {
        opsCloudGenerationTransferManager.register(this);
    }

    protected void buildG0Status(MetaChangeSceneEntity metaChangeSceneEntity) {
        Map<String, List<String>> tags = metaChangeSceneEntity.getTags();
        if (tags == null) {
            tags = new HashMap<>();
            metaChangeSceneEntity.setTags(tags);
        }
        tags.put("noneBatchReason", Arrays.asList("implByPlatform"));
    }

    @Override
    public OpsCloudResult<String> toG0(MetaChangeSceneEntity metaChangeSceneEntity) {

        return doProcess(metaChangeSceneEntity, () -> {
            buildG0Status(metaChangeSceneEntity);
            return toG0G1(metaChangeSceneEntity, MetaChangeSceneGenerationEnum.G0, metaChangeSceneEntity1 -> {
                //全部删除
                return metaChangeSceneEntity1.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps().stream().map(
                        MetaChangeStepEntity::getId).collect(
                        Collectors.toSet());
            });
        });

    }

    @Override
    public OpsCloudResult<String> toG1(MetaChangeSceneEntity metaChangeSceneEntity,
                                       Function<MetaChangeSceneEntity, MetaChangeStepEntity> orderStepCreateFunction) {
        return doProcess(metaChangeSceneEntity, () -> toG0G1(metaChangeSceneEntity, MetaChangeSceneGenerationEnum.G1, new TransferHelp() {
            @Override
            public Set<String> removedId(MetaChangeSceneEntity metaChangeSceneEntity) {
                Set<String> result = new HashSet<>();
                metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps().forEach(metaChangeStepEntity -> {
                    if (metaChangeStepEntity instanceof MetaChangeOrderStepEntity) {
                        result.add(metaChangeStepEntity.getId());
                    }
                    result.add(metaChangeStepEntity.getId());
                });
                return result;
            }

            @Override
            public void createOrderStep(MetaChangeSceneEntity metaChangeSceneEntity) {

                MetaChangeStepEntity metaChangeStepEntity = orderStepCreateFunction.apply(metaChangeSceneEntity);
                metaChangeSceneRepository.insertStep(metaChangeStepEntity);
            }
        }));

    }

    private OpsCloudResult<String> toG0G1(MetaChangeSceneEntity metaChangeSceneEntity, MetaChangeSceneGenerationEnum targetGeneration,
                                          G2OpsCloudGenerationTransfer.TransferHelp transferHelp) {


        return ServiceProcessTemplate.wrapTryCatch(() ->

                confTransactionTemplate.execute(status -> {
                    metaChangeSceneEntity.setGeneration(targetGeneration);
                    //不支持回调
                    metaChangeSceneEntity.setCallbackConfigEntity(null);
                    MetaChangeOrderStepEntity metaChangeOrderStepEntity = metaChangeSceneEntity.getChangeEffectiveConfig()
                            .getChangeProgress().getOrderChangeStep();
                    //如果G2没有工单级别，则需要创建一个sk,
                    if (metaChangeOrderStepEntity == null) {
                        transferHelp.createOrderStep(metaChangeSceneEntity);
                    }
                    Set<String> deletedIdList = transferHelp.removedId(metaChangeSceneEntity);
                    //设置其它模型
                    metaChangeSceneEntity.getChangeEffectiveConfig().setChangeProgress(null);
                    metaChangeSceneEntity.getChangeEffectiveConfig().setChangeGrayModeType(null);
                    metaChangeSceneEntity.getChangeEffectiveConfig().setChangeGrayEnvType(null);
                    metaChangeSceneRepository.update(metaChangeSceneEntity);
                    //删除多余的step
                    for (String stepId : deletedIdList) {
                        metaChangeSceneRepository.deleteStep(stepId);
                    }
                    return OpsCloudResult.succeed("降级到" + targetGeneration.getName() + "成功", metaChangeSceneEntity.getId());
                }));

    }

    @Override
    public OpsCloudResult<String> toG2(MetaChangeSceneEntity metaChangeSceneEntity) {
        return doProcess(metaChangeSceneEntity, () -> ServiceProcessTemplate.wrapTryCatch(() -> {
            metaChangeSceneEntity.setGeneration(MetaChangeSceneGenerationEnum.G3);
            metaChangeSceneRepository.updateGeneration(metaChangeSceneEntity);
            return OpsCloudResult.succeed("升级到G2成功", metaChangeSceneEntity.getId());
        }));

    }

    @Override
    public OpsCloudResult<String> toG3(MetaChangeSceneEntity metaChangeSceneEntity) {

        return doProcess(metaChangeSceneEntity, () -> ServiceProcessTemplate.wrapTryCatch(() -> {
            metaChangeSceneEntity.setGeneration(MetaChangeSceneGenerationEnum.G3);
            metaChangeSceneRepository.updateGeneration(metaChangeSceneEntity);
            return OpsCloudResult.succeed("升级到G3成功", metaChangeSceneEntity.getId());
        }));

    }

    private OpsCloudResult<String> doProcess(MetaChangeSceneEntity metaChangeSceneEntity, Supplier<OpsCloudResult<String>> supplier) {
        if (metaChangeSceneEntity.getGeneration() == getChangeSceneGeneration()) {
            OpsCloudResult.illegalArgument("当前场景是" + getChangeSceneGeneration().getName() + "，不需要转换");
        }
        return supplier.get();
    }

    interface TransferHelp {

        Set<String> removedId(MetaChangeSceneEntity metaChangeSceneEntity);

        default void createOrderStep(MetaChangeSceneEntity metaChangeSceneEntity) {};
    }

}