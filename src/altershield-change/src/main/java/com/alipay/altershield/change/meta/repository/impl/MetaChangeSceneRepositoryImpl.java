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
package com.alipay.altershield.change.meta.repository.impl;

import com.alipay.altershield.change.meta.dal.dataobject.*;
import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.change.meta.repository.MetaChangeSceneRepository;
import com.alipay.altershield.change.meta.repository.converter.MetaChangeSceneConverter;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneBatchEntity;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneQueryEntity;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuanji
 * @version : OpsCloudMetaChangeSceneRepositoryImpl.java, v 0.1 2022年03月11日 8:08 下午 yuanji Exp $
 */
@Repository
public class MetaChangeSceneRepositoryImpl implements MetaChangeSceneRepository {

    @Autowired
    private MetaChangeSceneMapper metaChangeSceneMapper;

    @Autowired
    private MetaChangeStepMapper metaChangeStepMapper;

    @Autowired
    private MetaChangeSceneConverter metaChangeSceneConverter;

    public MetaChangeSceneRepositoryImpl() {
    }

    @Override
    public MetaChangeSceneEntity getChangeSceneDetailsById(String id) {
        Assert.notNull(id, "id is null");
        MetaChangeSceneDO changeSceneDO = metaChangeSceneMapper.selectWithStepByPrimaryKey(id);
        return metaChangeSceneConverter.convertToFullEntity(changeSceneDO);
    }


    @Override
    @MetaCache
    public MetaChangeSceneEntity getChangeSceneByChangeSceneKey(String changeSceneKey) {
        Assert.notNull(changeSceneKey, "changeSceneKey is null");
        MetaChangeSceneDO changeSceneDO = metaChangeSceneMapper.selectByPrimaryChangeSceneKey(changeSceneKey);
        return metaChangeSceneConverter.convertToFullEntity(changeSceneDO);
    }

    @Override
    public List<MetaBaseChangeSceneEntity> selectChangeSceneList(MetaChangeSceneQueryParam param) {

        List<MetaBaseChangeSceneDO> changeSceneDOList = metaChangeSceneMapper.selectByParam(param);
        return metaChangeSceneConverter.convertToChangeSceneEntityList(changeSceneDOList);
    }

    @Override
    public long selectChangeSceneListCount(MetaChangeSceneQueryParam param) {
        return metaChangeSceneMapper.selectByParamCount(param);
    }

    @Override
    public boolean insert(MetaChangeSceneEntity metaChangeSceneEntity) {
        MetaChangeSceneDO changeSceneDO = metaChangeSceneConverter.toChangeSceneDO(metaChangeSceneEntity);
        metaChangeSceneMapper.insert(changeSceneDO);
        if (metaChangeSceneEntity.getChangeEffectiveConfig() != null
                && metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress() != null) {
            List<MetaChangeStepEntity> metaChangeStepEntityList = metaChangeSceneEntity.getChangeEffectiveConfig().getChangeProgress()
                    .getAllChangeSteps();
            List<MetaChangeStepDO> stepList = metaChangeSceneConverter.toChangeStepDOList(metaChangeStepEntityList);
            if (!CollectionUtils.isEmpty(stepList)) {
                metaChangeStepMapper.batchInsert(stepList);
            }
        }

        return false;
    }

    public boolean insert(MetaChangeSceneEntity metaChangeSceneEntity, List<MetaChangeStepEntity> metaChangeStepEntityList) {
        MetaChangeSceneDO changeSceneDO = metaChangeSceneConverter.toChangeSceneDO(metaChangeSceneEntity);
        metaChangeSceneMapper.insert(changeSceneDO);
        List<MetaChangeStepDO> stepList = metaChangeSceneConverter.toChangeStepDOList(metaChangeStepEntityList);
        if (!CollectionUtils.isEmpty(stepList)) {
            metaChangeStepMapper.batchInsert(stepList);
        }
        return true;
    }

    @Override
    public boolean insertStep(MetaChangeStepEntity metaChangeStepEntity) {
        Assert.notNull(metaChangeStepEntity, "change step is null");
        MetaChangeStepDO stepDO = metaChangeSceneConverter.toChangeStepDO(metaChangeStepEntity);
        return metaChangeStepMapper.insert(stepDO) == 1;
    }

    @Override
    public boolean updateStep(MetaChangeStepEntity metaChangeStepEntity) {
        Assert.notNull(metaChangeStepEntity, "change step is null");
        MetaChangeStepDO stepDO = metaChangeSceneConverter.toChangeStepDO(metaChangeStepEntity);
        return metaChangeStepMapper.updateByPrimaryKey(stepDO) == 1;
    }

    @Override
    public boolean deleteStep(String id) {
        Assert.notNull(id, "id is null");
        return metaChangeStepMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    @MetaCache
    public MetaChangeStepEntity getChangeStepEntity(String changeKey) {
        Assert.notNull(changeKey, "id is null");
        MetaChangeStepDO opsCloudMetaChangeStepDO = metaChangeStepMapper.selectByChangeKey(changeKey);
        return metaChangeSceneConverter.convertToStepEntity(opsCloudMetaChangeStepDO);
    }

    @Override
    public boolean update(MetaChangeSceneEntity metaChangeSceneEntity) {
        Assert.notNull(metaChangeSceneEntity, "metaChangeSceneEntity is null");
        MetaChangeSceneDO changeSceneDO = metaChangeSceneConverter.toChangeSceneDO(metaChangeSceneEntity);
        return metaChangeSceneMapper.updateByPrimaryKey(changeSceneDO) == 1;
    }


    public boolean updateGeneration(MetaChangeSceneEntity metaChangeSceneEntity) {
        Assert.notNull(metaChangeSceneEntity, "metaChangeSceneEntity is null");
        return metaChangeSceneMapper.updateGeneration(metaChangeSceneEntity.getId(), metaChangeSceneEntity.getGeneration().name()) == 1;
    }

    @Override
    public boolean deleteChangeScene(String id) {
        Assert.notNull(id, "id is null");
        MetaChangeSceneDO changeSceneDO = metaChangeSceneMapper.selectByPrimaryKey(id);
        if (changeSceneDO == null) {
            return true;
        }
        String changeSceneKey = changeSceneDO.getChangeSceneKey();
        metaChangeSceneMapper.deleteByPrimaryKey(id);
        metaChangeStepMapper.deleteByChangeSceneKey(changeSceneKey);
        return true;
    }

    @Override
    public int selectCountChangeStepByKeyAndType(String changeKey, MetaChangeStepTypeEnum typeEnum) {
        return metaChangeStepMapper.selectCountByChangeKeyAndType(changeKey, typeEnum.name());
    }

    @Override
    public List<MetaChangeStepEntity> selectChangeStepByChangeSceneKey(String changeSceneKey) {
        List<MetaChangeStepDO> changeStepDOList = metaChangeStepMapper.selectByChangeSceneKey(changeSceneKey);
        if (CollectionUtils.isEmpty(changeStepDOList)) {
            return Collections.emptyList();
        }
        return changeStepDOList.stream().map(metaChangeStepDO -> metaChangeSceneConverter.convertToStepEntity(metaChangeStepDO)).collect(
                Collectors.toList());
    }

    @Override
    public List<MetaChangeStepEntity> selectChangeStepByChangeSceneKeyAndType(String changeSceneKey, MetaChangeStepTypeEnum typeEnum) {
        List<MetaChangeStepDO> changeStepDOList = metaChangeStepMapper.selectChangeStepByChangeSceneKeyAndType(changeSceneKey, typeEnum.name());
        if (CollectionUtils.isEmpty(changeStepDOList)) {
            return Collections.emptyList();
        }
        return changeStepDOList.stream().map(metaChangeStepDO -> metaChangeSceneConverter.convertToStepEntity(metaChangeStepDO)).collect(
                Collectors.toList());
    }

    @Override
    public int batchUpdateChangeStep(List<MetaChangeStepEntity> metaChangeStepEntityList) {
        List<MetaChangeStepDO> stepList = metaChangeSceneConverter.toChangeStepDOList(metaChangeStepEntityList);
        if (!CollectionUtils.isEmpty(stepList)) {
            return metaChangeStepMapper.batchUpdate(stepList);
        }
        return 0;
    }

    @Override
    public boolean existed(String changeSceneKey) {
        if (StringUtils.isBlank(changeSceneKey)) {
            return false;
        }
        MetaChangeSceneQueryParam queryParam = new MetaChangeSceneQueryParam();
        queryParam.setChangeSceneKey(changeSceneKey);
        return metaChangeSceneMapper.selectByParamCount(queryParam) == 1;
    }

    @Override
    public boolean changeKeyExisted(String changeKey) {
        if (StringUtils.isBlank(changeKey)) {
            return false;
        }
        return metaChangeStepMapper.selectCountByChangeKeyAndType(changeKey, null) == 1;
    }

    @Override
    public List<MetaChangeSceneBatchEntity> queryAllScene() {
        List<MetaChangeSceneBatchDO> metaChangeSceneBatchDOS = metaChangeSceneMapper.queryAllScene();
        return metaChangeSceneConverter.DOSToEntities(metaChangeSceneBatchDOS);
    }

    @Override
    public List<MetaChangeSceneQueryEntity> querySceneMainInfo() {
        List<MetaChangeSceneQueryEntity> list = new ArrayList<>();
        List<MetaChangeSceneDO> opscldMetaChangeSceneDOS = metaChangeSceneMapper.querySceneMainInfo();
        if (!CollectionUtils.isEmpty(opscldMetaChangeSceneDOS)) {
            for (MetaChangeSceneDO opscldMetaChangeSceneDO : opscldMetaChangeSceneDOS) {
                MetaChangeSceneQueryEntity entity = new MetaChangeSceneQueryEntity();
                entity.setId(opscldMetaChangeSceneDO.getId());
                entity.setChangeSceneName(opscldMetaChangeSceneDO.getName());
                entity.setChangeSceneKey(opscldMetaChangeSceneDO.getChangeSceneKey());
                list.add(entity);
            }
        }
        return list;
    }

    @Override
    public MetaChangeSceneQueryEntity getFullEntity(String changeSceneKey) {
        MetaChangeSceneQueryEntity entity = new MetaChangeSceneQueryEntity();
        MetaChangeSceneDO sceneDO = metaChangeSceneMapper.getFullEntity(changeSceneKey);
        if (sceneDO != null) {
            entity.setId(sceneDO.getId());
            entity.setChangeSceneName(sceneDO.getName());
            entity.setChangeSceneKey(sceneDO.getChangeSceneKey());
        }
        return entity;
    }
}