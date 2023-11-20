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
package com.alipay.altershield.change.meta.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeSceneQueryParam;
import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeEffectiveConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneCallbackConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeBatchStepEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeOrderStepEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeProgressEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.change.meta.model.enums.MetaChangeGrayModeTypeEnum;
import com.alipay.altershield.change.meta.model.enums.MetaChangeSceneStatus;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.change.meta.service.MetaChangeSceneService;
import com.alipay.altershield.change.meta.service.OpsCloudGenerationTransfer;
import com.alipay.altershield.change.meta.service.OpsCloudGenerationTransferManager;
import com.alipay.altershield.change.meta.service.request.*;
import com.alipay.altershield.change.meta.service.request.converter.MetaChangeSceneRequestConverter;
import com.alipay.altershield.change.meta.service.request.converter.MetaChangeStepRequestConverter;
import com.alipay.altershield.change.util.EntityUtil;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.service.ServicePageProcessTemplate;
import com.alipay.altershield.common.service.ServicePageQuery;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldPageResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.core.change.facade.result.CreateMetaChangeSceneResult;
import com.alipay.altershield.framework.core.change.model.enums.MetaChangeSceneGenerationEnum;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneQueryEntity;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.change.meta.service.MetaChangeSceneQueryService;
import com.alipay.altershield.shared.schedule.event.change.MetaChangeSceneCreateEvent;
import com.alipay.altershield.shared.schedule.event.publish.AlterShieldSchedulerEventPublisher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Meta change scene service.
 *
 * @author yuanji
 * @version : MetaChangeSceneServiceImpl.java, v 0.1 2022年03月15日 3:27 下午 yuanji Exp $
 */
@Service
public class MetaChangeSceneServiceImpl implements MetaChangeSceneService, MetaChangeSceneQueryService, InitializingBean {

    @Autowired
    private MetaChangeSceneRepository metaChangeSceneRepository;

    @Resource
    private TransactionTemplate confTransactionTemplate;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private AlterShieldSchedulerEventPublisher opsCloudSchedulerEventPublisher;

    @Autowired
    private OpsCloudGenerationTransferManager opsCloudGenerationTransferManager;

    private Function<String, String> createStepIdFunction;

    private static final Logger logger = Loggers.META_CHANGE;

    @Override
    public AlterShieldResult<MetaChangeSceneEntity> getMetaChangeSceneById(String id) {
        return ServiceProcessTemplate.wrapTryCatch(() ->
                AlterShieldResult.succeed("success", metaChangeSceneRepository.getChangeSceneDetailsById(id)));
    }

    @Override
    public MetaChangeSceneEntity getMetaChangeSceneByChangeSceneKey(String changeSceneKey) {
        return metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeSceneKey);
    }

    @Override
    public MetaChangeStepEntity getMetaChangeStepByChangeKey(String changeKey) {
        return metaChangeSceneRepository.getChangeStepEntity(changeKey);
    }

    @Override
    public MetaChangeStepEntity getOrderMetaChangeStep(String changeSceneKey) {
        return metaChangeSceneRepository.getChangeStepEntity(changeSceneKey);
    }

    @Override
    public MetaChangeStepEntity getBatchMetaChangeStep(String changeSceneKey) {
        List<MetaChangeStepEntity> metaChangeStepEntityList = metaChangeSceneRepository.selectChangeStepByChangeSceneKeyAndType(
                changeSceneKey,
                MetaChangeStepTypeEnum.STEP_GRAY_BATCH);
        if (CollectionUtils.isEmpty(metaChangeStepEntityList)) {
            return null;
        }
        return metaChangeStepEntityList.get(0);
    }


    public AlterShieldResult<CreateMetaChangeSceneResult> createTempChangeScene(CreateMetaChangeSceneRequest request) {
        return createOrUpdateChangeScene(null, request, MetaChangeSceneStatus.TEMP);
    }

    public AlterShieldResult<CreateMetaChangeSceneResult> createOrUpdateChangeScene(String id, CreateMetaChangeSceneRequest request) {
        return createOrUpdateChangeScene(id, request, MetaChangeSceneStatus.TEMP);
    }

    public AlterShieldResult<CreateMetaChangeSceneResult> createReleaseChangeScene(CreateMetaChangeSceneRequest request) {
        return createOrUpdateChangeScene(null, request, MetaChangeSceneStatus.RELEASE);
    }

    /**
     * 这里是一个组合操作
     * 有id的时候， status=temp  -> 全部覆盖，保留id
     * 有id的时候， status=release -> 仅能更新name,owner, tags
     * 无id的时候， 没有状态的问题，创建的状态和 metaChangeSceneStatus 有关系了。
     * metaChangeSceneStatus 是release的情况是接口同步过来的
     *
     * @param id
     * @param request
     * @param metaChangeSceneStatus
     * @return
     */
    private AlterShieldResult<CreateMetaChangeSceneResult> createOrUpdateChangeScene(final String id, CreateMetaChangeSceneRequest request,
                                                                                  MetaChangeSceneStatus metaChangeSceneStatus) {
        return ServiceProcessTemplate.wrapTryCatch(() ->
                confTransactionTemplate.execute(status -> {
                    doCheck(request);
                    AlterShieldLoggerManager.log("debug", logger, "start create change scene :", request);
                    MetaChangeSceneEntity metaChangeSceneEntity = null;
                    if (StringUtils.isNotBlank(id)) {
                        metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneDetailsById(id);
                        if (metaChangeSceneEntity == null) {
                            return AlterShieldResult.illegalArgument("update changeScene fail, change scene not found:" + id);
                        }
                    } else {
                        metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(request.getChangeSceneKey());
                        if (metaChangeSceneEntity != null) {
                            return AlterShieldResult.illegalArgument("创建场景失败, 场景码 :" + request.getChangeSceneKey() + "已经存在");
                        }
                    }


                    //如果已经是release态的了，则只能更新name和owner, tags信息
                    if (metaChangeSceneEntity != null && metaChangeSceneEntity.getStatus() == MetaChangeSceneStatus.RELEASE) {

                        updateBaseChangeScene(request, metaChangeSceneEntity);
                        CreateMetaChangeSceneResult createMetaChangeSceneResult = new CreateMetaChangeSceneResult();
                        createMetaChangeSceneResult.setId(id);
                        return AlterShieldResult.succeed("create changeScene Success", createMetaChangeSceneResult);
                    }
                    //如果是暂存态，则直接先删除了之前的信息，复用id而已
                    if (metaChangeSceneEntity != null && metaChangeSceneEntity.getStatus() == MetaChangeSceneStatus.TEMP) {
                        metaChangeSceneRepository.deleteChangeScene(id);
                    }
                    return createMetaChangeScene(request, metaChangeSceneStatus, id);
                })
        );
    }

    private void updateBaseChangeScene(CreateMetaChangeSceneRequest request, MetaChangeSceneEntity metaChangeSceneEntity) {
        metaChangeSceneEntity.setName(request.getName());
        metaChangeSceneEntity.setOwner(request.getOwner());
        metaChangeSceneEntity.setTags(request.getTags());
        metaChangeSceneRepository.update(metaChangeSceneEntity);
        if (metaChangeSceneEntity.getGeneration() != MetaChangeSceneGenerationEnum.G0) {
            //update step的基本名字
            if (metaChangeSceneEntity.getChangeEffectiveConfig() != null && metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress() != null) {
                MetaChangeStepEntity metaChangeStepEntity = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getOrderChangeStep();
                if (metaChangeStepEntity != null) {
                    MetaChangeSceneRequestConverter.buildStepName(metaChangeSceneEntity, metaChangeStepEntity);
                    metaChangeSceneRepository.updateStep(metaChangeStepEntity);
                }
                metaChangeStepEntity = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getBatchStep();
                if (metaChangeStepEntity != null) {
                    MetaChangeSceneRequestConverter.buildStepName(metaChangeSceneEntity, metaChangeStepEntity);
                    metaChangeSceneRepository.updateStep(metaChangeStepEntity);
                }
            }
        }
    }

    /**
     * 对key做重复校验
     *
     * @param request
     */
    private void doCheck(CreateMetaChangeSceneRequest request) {
        if (request.getChangeEffectiveConfig() != null && request.getChangeEffectiveConfig().getChangeProgress() != null) {
            com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeProgressRequest progressRequest = request.getChangeEffectiveConfig().getChangeProgress();
            Set<String> keySet = new HashSet<>();
            doCheck(keySet, progressRequest.getAfterGrayFinishStep());
            doCheck(keySet, progressRequest.getBeforeGrayStartStep());
            if (!CollectionUtils.isEmpty(progressRequest.getGrayBatchActionSteps())) {
                progressRequest.getGrayBatchActionSteps().forEach(createActionChangeStepRequest -> doCheck(keySet, createActionChangeStepRequest));
            }
        }
    }

    private void doCheck(Set<String> keySet, com.alipay.altershield.framework.core.change.facade.request.CreateActionChangeStepRequest changeStepRequest) {
        if (changeStepRequest != null) {
            if (keySet.contains(changeStepRequest.getChangeKey())) {
                throw new IllegalArgumentException("duplicated change key:" + changeStepRequest.getChangeKey());
            }
            keySet.add(changeStepRequest.getChangeKey());
        }
    }

    private AlterShieldResult<CreateMetaChangeSceneResult> createMetaChangeScene(CreateMetaChangeSceneRequest request,
                                                                              MetaChangeSceneStatus metaChangeSceneStatus, String id) {
        MetaChangeSceneEntity metaChangeSceneEntity;
        //1、先创建出对象
        //2、校验各代G参数
        //3、写入对象

        MetaChangeStepEntity metaChangeStepEntity = null;
        metaChangeSceneEntity = MetaChangeSceneRequestConverter.INSTANCE.convertToEntity(request);
        metaChangeSceneEntity.setStatus(metaChangeSceneStatus);
        switch (metaChangeSceneEntity.getGeneration()) {
            case G1:
                metaChangeStepEntity = buildG1OrderStep(metaChangeSceneEntity, createStepIdFunction);
                break;
            case G2:
            case G3:
            case G4:
                buildStep(request, metaChangeSceneEntity, createStepIdFunction);
                break;
        }
        if (!StringUtils.isNotBlank(id)) {
            id = idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_SCENE);
        }
        metaChangeSceneEntity.setId(id);
        metaChangeSceneRepository.insert(metaChangeSceneEntity);
        if (metaChangeStepEntity != null) {
            metaChangeSceneRepository.insertStep(metaChangeStepEntity);
        }
        CreateMetaChangeSceneResult createMetaChangeSceneResult = new CreateMetaChangeSceneResult();
        createMetaChangeSceneResult.setId(id);
        return AlterShieldResult.succeed("create changeScene Success", createMetaChangeSceneResult);
    }


    public AlterShieldResult<String> importChangeScene(JSONObject json) {
        return ServiceProcessTemplate.wrapTryCatch(() ->
                confTransactionTemplate.execute(status -> {
                    if (json == null) {
                        return AlterShieldResult.illegalArgument("change scene json is null");
                    }
                    MetaChangeSceneImportRequest metaChangeSceneImportRequest = json.toJavaObject(MetaChangeSceneImportRequest.class);
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneImportRequest.getMetaChangeSceneEntity();
                    if (metaChangeSceneEntity == null) {
                        return AlterShieldResult.illegalArgument("change scene  is null");
                    }
                    String id = idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_SCENE);
                    metaChangeSceneEntity.setId(id);
                    List<MetaChangeStepEntity> metaChangeStepEntityList = metaChangeSceneImportRequest.getMetaChangeStepEntityList();
                    if (!CollectionUtils.isEmpty(metaChangeStepEntityList)) {
                        metaChangeStepEntityList.stream().forEach(metaChangeStepEntity -> metaChangeStepEntity
                                .setId(idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_STEP)));
                    }
                    metaChangeSceneRepository.insert(metaChangeSceneEntity, metaChangeStepEntityList);
                    return AlterShieldResult.succeed("import changeScene Success", id);
                }));
    }

    public AlterShieldResult<String> exportChangeScene(String changeSceneId) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {
            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneDetailsById(changeSceneId);
            if (metaChangeSceneEntity == null) {
                return AlterShieldResult.illegalArgument("change scene not found");
            }

            List<MetaChangeStepEntity> metaChangeStepEntityList = null;
            if (metaChangeSceneEntity.getChangeEffectiveConfig() != null
                    && metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress() != null && !CollectionUtils.isEmpty(
                    metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps())) {
                metaChangeStepEntityList = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress().getAllChangeSteps();
            }

            MetaChangeSceneImportRequest metaChangeSceneImportRequest = new MetaChangeSceneImportRequest();
            metaChangeSceneImportRequest.setMetaChangeSceneEntity(metaChangeSceneEntity);
            metaChangeSceneImportRequest.setMetaChangeStepEntityList(metaChangeStepEntityList);
            String json = EntityUtil.exportEntity(metaChangeSceneImportRequest);
            return AlterShieldResult.succeed("create changeScene Success", json);
        });

    }

    @Override
    public AlterShieldResult<Boolean> createChangeScene(CreateMetaChangeScene2Request request) {
        return ServiceProcessTemplate.wrapTryCatch(() ->

                confTransactionTemplate.execute(status -> {
                    AlterShieldLoggerManager.log("debug", logger, "start create change scene step2:", request);
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneDetailsById(request.getId());
                    if (metaChangeSceneEntity == null) {
                        return AlterShieldResult.illegalArgument("change scene not found");
                    }
                    //1、先创建出对象
                    //2、校验各代G参数
                    //3、写入对象
                    List<MetaChangeStepEntity> metaChangeStepEntityList = null;
                    switch (metaChangeSceneEntity.getGeneration()) {
                        case G2:
                        case G3:
                        case G4:
                            update(request, metaChangeSceneEntity);
                            metaChangeStepEntityList = MetaChangeSceneRequestConverter.INSTANCE.convertTOChangeStepEntityList(
                                    request.getDefenceConfigs());
                            break;
                    }
                    if (!CollectionUtils.isEmpty(metaChangeStepEntityList)) {
                        metaChangeSceneRepository.batchUpdateChangeStep(metaChangeStepEntityList);
                    }
                    boolean syncNew = metaChangeSceneEntity.getStatus() == MetaChangeSceneStatus.TEMP;
                    metaChangeSceneEntity.setStatus(MetaChangeSceneStatus.RELEASE);
                    metaChangeSceneRepository.update(metaChangeSceneEntity);
                    return AlterShieldResult.succeed("create changeScene Success", Boolean.TRUE);
                })
        );
    }

    private void update(SyncMetaChangeSceneRequest request, MetaChangeSceneEntity metaChangeSceneEntity) {
        if (request.getCallbackConfig() != null && !CollectionUtils.isEmpty(request.getCallbackConfig().getCallbackConfig())) {
            MetaChangeSceneCallbackConfigEntity metaChangeSceneCallbackConfigEntity = new MetaChangeSceneCallbackConfigEntity();
            metaChangeSceneCallbackConfigEntity.setCallbackConfig(request.getCallbackConfig().getCallbackConfig());
            metaChangeSceneEntity.setCallbackConfigEntity(metaChangeSceneCallbackConfigEntity);
        }

    }

    private void update(CreateMetaChangeScene2Request request, MetaChangeSceneEntity metaChangeSceneEntity) {
        if (request.getCallbackConfig() != null && !CollectionUtils.isEmpty(request.getCallbackConfig().getCallbackConfig())) {
            MetaChangeSceneCallbackConfigEntity metaChangeSceneCallbackConfigEntity = new MetaChangeSceneCallbackConfigEntity();
            metaChangeSceneCallbackConfigEntity.setCallbackConfig(request.getCallbackConfig().getCallbackConfig());
            metaChangeSceneEntity.setCallbackConfigEntity(metaChangeSceneCallbackConfigEntity);
        }

    }


    private MetaChangeStepEntity buildG1OrderStep(MetaChangeSceneEntity metaChangeSceneEntity, Function<String, String> function) {
        com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeStepRequest changeStepRequest
                = new com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeStepRequest(true, 10000, true, 10000);
        MetaChangeStepEntity metaChangeStepEntity = MetaChangeSceneRequestConverter.INSTANCE.buildMetaChangeOrderStepEntity(
                metaChangeSceneEntity, changeStepRequest, function);
        return metaChangeStepEntity;
    }

    private void buildStep(CreateMetaChangeSceneRequest request, MetaChangeSceneEntity metaChangeSceneEntity,
                           Function<String, String> function) {
        if (request.getChangeEffectiveConfig() != null && request.getChangeEffectiveConfig().getChangeProgress() != null) {
            MetaChangeProgressEntity changeProgress = MetaChangeSceneRequestConverter.INSTANCE.covert(metaChangeSceneEntity,
                    request.getChangeEffectiveConfig().getChangeProgress(), function);
            if (changeProgress != null) {
                List<MetaChangeStepEntity> metaChangeStepEntityList = changeProgress.getAllChangeSteps();
                if (!CollectionUtils.isEmpty(metaChangeStepEntityList)) {
                    metaChangeStepEntityList.stream().forEach(metaChangeStepEntity -> {
                        //HISTORYMODE不校验。
                        if(Objects.nonNull(metaChangeSceneEntity.getChangeEffectiveConfig()) &&
                                MetaChangeGrayModeTypeEnum.HISTORYMODE.getName().equals(metaChangeSceneEntity.getChangeEffectiveConfig().getChangeGrayModeType().getName())){
                            return;
                        }
                        if (changeKeyExisted(metaChangeStepEntity.getChangeKey())) {
                            throw new IllegalArgumentException("观察点:  " + metaChangeStepEntity.getName() + "(" + metaChangeStepEntity.getChangeKey() + ") 已经存在");
                        }
                    });
                }
            }
            metaChangeSceneEntity.getChangeEffectiveConfig().setChangeProgress(changeProgress);
        }
    }

    @Override
    public AlterShieldResult<Boolean> updateBasicChangeScene(UpdateMetaChangeSceneRequest request) {

        return ServiceProcessTemplate.wrapTryCatch(() -> {
            MetaChangeSceneEntity metaChangeSceneEntity = MetaChangeSceneRequestConverter.INSTANCE.convertToEntity(request);
            AlterShieldLoggerManager.log("debug", logger, "start update change scene :", metaChangeSceneEntity);
            boolean result = metaChangeSceneRepository.update(metaChangeSceneEntity);
            return result ? AlterShieldResult.succeed("success", true) : AlterShieldResult.illegalArgument("change scene not found");
        });
    }

    @Override
    public AlterShieldResult<Boolean> updateChangeStep(UpdateMetaChangeStepRequest request) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {
            MetaChangeStepEntity metaChangeStepEntity = MetaChangeStepRequestConverter.INSTANCE.convertToEntity(request);
            boolean result = metaChangeSceneRepository.updateStep(metaChangeStepEntity);
            return result ? AlterShieldResult.succeed("success", true) : AlterShieldResult.illegalArgument("change step not found");
        });
    }

    @Override
    public AlterShieldResult<Boolean> createChangeStep(CreateMetaChangeStepRequest request) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {

                    MetaChangeStepTypeEnum metaChangeStepTypeEnum = MetaChangeStepTypeEnum.valueOf(request.getStepType());
                    if (metaChangeStepTypeEnum == null) {
                        return AlterShieldResult.illegalArgument("invalid step type:" + request.getStepType());
                    }
                    if (metaChangeStepTypeEnum != MetaChangeStepTypeEnum.STEP_GRAY_BATCH_ACTION) {
                        int count = metaChangeSceneRepository.selectCountChangeStepByKeyAndType(request.getChangeKey(),
                                metaChangeStepTypeEnum);
                        if (count > 0) {
                            String message = String.format("change type : %s existed, in %s is only one", metaChangeStepTypeEnum.getStep(),
                                    request.getChangeKey());
                            return AlterShieldResult.illegalArgument(message);
                        }
                    }
                    MetaChangeStepEntity metaChangeStepEntity = MetaChangeStepRequestConverter.INSTANCE.convertToEntity(request);
                    String id = idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_STEP);
                    metaChangeStepEntity.setId(id);
                    metaChangeSceneRepository.insertStep(metaChangeStepEntity);
                    return AlterShieldResult.succeed("success", true);
                }
        );
    }

    @Override
    public AlterShieldResult<Boolean> removeChangeStep(String id) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {
            metaChangeSceneRepository.deleteStep(id);
            return AlterShieldResult.succeed("remove success", true);
        });
    }

    @Override
    public AlterShieldResult<Boolean> removeChangeScene(String id) {
        return ServiceProcessTemplate.wrapTryCatch(() -> {
            metaChangeSceneRepository.deleteChangeScene(id);
            return AlterShieldResult.succeed("remove success", true);
        });
    }

    @Override
    public AlterShieldPageResult<List<MetaBaseChangeSceneEntity>> query(final QueryChangeSceneRequest request) {

        MetaChangeSceneQueryParam param = MetaChangeSceneRequestConverter.INSTANCE.convertQueryParam(request);
        return ServicePageProcessTemplate.process(request.getCurrent(), request.getPageSize(),
                new ServicePageQuery<MetaBaseChangeSceneEntity>() {
                    @Override
                    public List<MetaBaseChangeSceneEntity> query(int offset, int limit) {
                        param.setOffset(offset);
                        param.setLimit(limit);
                        return metaChangeSceneRepository.selectChangeSceneList(param);
                    }

                    @Override
                    public long total() {
                        return metaChangeSceneRepository.selectChangeSceneListCount(param);
                    }
                });
    }

    @Override
    public AlterShieldResult<Boolean> checkChangeKey(String changeKey) {

        return ServiceProcessTemplate.process(() -> changeKeyExisted(changeKey));
    }

    @Override
    public AlterShieldResult<Boolean> onlyCheckChangeKey(String changeKey) {
        return ServiceProcessTemplate.process(() -> {
            MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeKey);
            if(Objects.nonNull(metaChangeSceneEntity)){
                return true;
            }
            return metaChangeSceneRepository.changeKeyExisted(changeKey);
        });
    }


    private boolean changeKeyExisted(String changeKey) {
        if (metaChangeSceneRepository.changeKeyExisted(changeKey)) {
            return true;
        }
        return false;
    }

    private void buildMetaChangeStepEntity(String changeKey, String name, MetaChangeStepEntity stepEntity) {
        stepEntity.setChangeKey(changeKey);
        stepEntity.setName(name);
        String id = idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_STEP);
        stepEntity.setId(id);
    }

    private AlterShieldResult rebuildChangeStep(MetaChangeProgressEntity metaChangeProgressEntity,
                                                MetaChangeEffectiveConfigEntity metaChangeEffectiveConfigEntity,
                                                MetaChangeSceneEntity metaChangeSceneEntity) {
        MetaChangeBatchStepEntity batchStepEntity = metaChangeProgressEntity.getBatchStep();
        if (batchStepEntity != null) {
            //检查是否支持分批
            if (!metaChangeEffectiveConfigEntity.getChangeGrayModeType().isBatchEnable()) {
                return AlterShieldResult.illegalArgument(
                        String.format("变更场景 %s 的生效模型: %s 选择错误，请选择支持分批的模型", metaChangeSceneEntity.getName(),
                                metaChangeEffectiveConfigEntity.getChangeGrayModeType()));
            }
            buildMetaChangeStepEntity(
                    metaChangeSceneEntity.getChangeSceneKey() + metaChangeEffectiveConfigEntity.getChangeGrayModeType()
                            .getChangKeySuffix(), metaChangeSceneEntity.getName() + "分批", batchStepEntity);
        }
        MetaChangeOrderStepEntity stepEntity = metaChangeProgressEntity.getOrderChangeStep();
        if (stepEntity != null) {
            //order change step 和entity 一致
            buildMetaChangeStepEntity(metaChangeSceneEntity.getChangeSceneKey(), metaChangeSceneEntity.getName(),
                    stepEntity);
        }
        return null;
    }

    private void sendEvent(MetaChangeSceneEntity metaChangeSceneEntity) {
        if (metaChangeSceneEntity != null) {
            MetaChangeSceneCreateEvent event = new MetaChangeSceneCreateEvent();
            event.setChangeSceneKey(metaChangeSceneEntity.getChangeSceneKey());
            event.setPlatform(metaChangeSceneEntity.getPlatformName());
            event.setId(metaChangeSceneEntity.getId());
            opsCloudSchedulerEventPublisher.publish(metaChangeSceneEntity.getId(), event);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createStepIdFunction = s -> {
            if (changeKeyExisted(s)) {
                throw new IllegalArgumentException("观察点标识： " + s + " 已经存在");
            }
            return idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_CHANGE_STEP);
        };
    }

    @Override
    public String queryMetaChangeSceneName(String changeSceneKey) {
        if (StringUtils.isBlank(changeSceneKey)) {
            return null;
        }
        MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeSceneKey);
        return metaChangeSceneEntity != null ? metaChangeSceneEntity.getName() : null;
    }

    @Override
    public String queryMetaChangeScenePlatformName(String changeSceneKey) {
        if (StringUtils.isBlank(changeSceneKey)) {
            return null;
        }
        MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(changeSceneKey);
        return metaChangeSceneEntity != null ? metaChangeSceneEntity.getPlatformName() : null;
    }

    /**
     * 根据changeKey获取其名称
     *
     * @param changeKey 变更批次标识
     * @return 中文名称
     */
    @Override
    public String queryChangeKeyName(String changeKey) {
        if (StringUtils.isBlank(changeKey)) {
            return null;
        }
        MetaChangeStepEntity metaChangeStepEntity = metaChangeSceneRepository.getChangeStepEntity(changeKey);
        return metaChangeStepEntity != null ? metaChangeStepEntity.getName() : null;
    }

    /**
     * 根据changeSceneKey获取其关联的changeKey
     *
     * @param changeSceneKey 变更场景标识
     * @return changeKey列表
     */
    @Override
    public List<String> queryChangeKeysByChangeSceneKey(String changeSceneKey) {
        List<MetaChangeStepEntity> changeSteps = metaChangeSceneRepository.selectChangeStepByChangeSceneKey(changeSceneKey);
        if (CollectionUtils.isEmpty(changeSteps)) {
            return Collections.emptyList();
        }
        return changeSteps.stream().filter(Objects::nonNull).map(MetaChangeStepEntity::getName).collect(Collectors.toList());
    }

    public AlterShieldResult<String> alterGeneration(AlterChangeSceneGenerationRequest request) {

        MetaChangeSceneGenerationEnum metaChangeSceneGenerationEnum = MetaChangeSceneGenerationEnum.getByName(request.getGeneration());
        if (metaChangeSceneGenerationEnum == null) {
            return AlterShieldResult.illegalArgument("错误的代G码：" + request.getGeneration());
        }
        MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneDetailsById(request.getId());
        if (metaChangeSceneEntity == null) {
            return AlterShieldResult.illegalArgument("变更场景不存在");
        }
        if (metaChangeSceneEntity.getStatus() != MetaChangeSceneStatus.RELEASE) {
            return AlterShieldResult.illegalArgument("变更场景还未发布，不支持代G调整");
        }
        OpsCloudGenerationTransfer opsCloudGenerationTransfer = opsCloudGenerationTransferManager.getOpsCloudGenerationTransfer(metaChangeSceneEntity.getGeneration());
        if (opsCloudGenerationTransfer == null) {
            return AlterShieldResult.illegalArgument("不支持" + metaChangeSceneEntity.getGeneration() + "调整到：" + metaChangeSceneGenerationEnum.name());
        }

        switch (metaChangeSceneGenerationEnum) {
            case G0:
                return opsCloudGenerationTransfer.toG0(metaChangeSceneEntity);
            case G1:
                return opsCloudGenerationTransfer.toG1(metaChangeSceneEntity,
                        metaChangeSceneEntity1 -> buildG1OrderStep(metaChangeSceneEntity1, createStepIdFunction));
            case G2:
                return opsCloudGenerationTransfer.toG2(metaChangeSceneEntity);
            case G3:
                return opsCloudGenerationTransfer.toG3(metaChangeSceneEntity);
        }
        return AlterShieldResult.illegalArgument("不支持" + metaChangeSceneEntity.getGeneration() + "调整到：" + metaChangeSceneGenerationEnum.name());
    }



    @Override
    public AlterShieldResult<Boolean> syncServiceKey(SyncMetaChangeSceneRequest request) {
        return ServiceProcessTemplate.wrapTryCatch(() ->

                confTransactionTemplate.execute(status -> {
                    AlterShieldLoggerManager.log("debug", logger, "start create change scene step2:", request);
                    MetaChangeSceneEntity metaChangeSceneEntity = metaChangeSceneRepository.getChangeSceneByChangeSceneKey(request.getChangeSecneKey());
                    if (metaChangeSceneEntity == null) {
                        return AlterShieldResult.illegalArgument("change scene not found");
                    }
                    //serviceKey同步的场景应该是serviceKey<->changeScene<->changeKey一一对应。
                    MetaChangeStepEntity metaChangeStepEntity = metaChangeSceneRepository.getChangeStepEntity(request.getChangeSecneKey());
                    if (metaChangeStepEntity == null) {
                        return AlterShieldResult.illegalArgument("change key not found");
                    }
                    if(CollectionUtils.isEmpty(request.getDefenceConfigs()) || request.getDefenceConfigs().size() != 1){
                        return AlterShieldResult.illegalArgument("change key in change scene more than one");
                    }

                    //设置changeKey的id

                    request.getDefenceConfigs().get(0).setId(metaChangeStepEntity.getId());
                    update(request, metaChangeSceneEntity);

                    List<MetaChangeStepEntity> metaChangeStepEntityList = MetaChangeSceneRequestConverter.INSTANCE.convertTOChangeStepEntityList(
                            request.getDefenceConfigs());

                    if (!CollectionUtils.isEmpty(metaChangeStepEntityList)) {
                        metaChangeSceneRepository.batchUpdateChangeStep(metaChangeStepEntityList);
                    }
                    metaChangeSceneRepository.update(metaChangeSceneEntity);
                    return AlterShieldResult.succeed("create changeScene Success", Boolean.TRUE);
                })
        );
    }



    @Override
    public List<MetaChangeSceneQueryEntity> querySceneMainInfo() {
        return metaChangeSceneRepository.querySceneMainInfo();
    }

    @Override
    public MetaChangeSceneQueryEntity getFullEntity(String changeSceneKey) {
        return metaChangeSceneRepository.getFullEntity(changeSceneKey);
    }
}