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
package com.alipay.altershield.change.meta.repository.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alipay.altershield.change.meta.dal.dataobject.MetaBaseChangeSceneDO;
import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeSceneDO;
import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeEffectiveConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneCallbackConfigEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Map;

/**
 * The type Meta change scene entity converter.
 *
 * @author yuanji
 * @version : MetaChangeSceneEntityConverter.java, v 0.1 2022年03月16日 3:31 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring", uses = MetaChangeSceneEnumMapper.class )
public abstract class MetaChangeSceneEntityConverter extends AbstractEntityConverter {

    /**
     * Covert to entity meta change scene entity.
     *
     * @param metaChangeSceneDO the meta change scene do
     * @return the meta change scene entity
     */
    @Mapping(source = "changeEffectiveConfigJsonRef", target = "changeEffectiveConfig")
    @Mapping(source = "changeEffectiveConfigJsonRef", target = "changeEffectiveConfigJsonRef")
    @Mapping(source = "callbackConfigJsonRef", target = "callbackConfigEntity")
    @Mapping(source = "callbackConfigJsonRef", target = "callbackConfigJsonRef")
    @Mapping(source = "tagsJsonRef", target = "tags")
    @Mapping(source = "tagsJsonRef", target = "tagsJsonRef")
    abstract public MetaChangeSceneEntity covertToEntity(MetaChangeSceneDO metaChangeSceneDO);

    /**
     * Covert to data object meta change scene do.
     *
     * @param entity the entity
     * @return the meta change scene do
     */
    abstract public MetaChangeSceneDO covertToDataObject(MetaChangeSceneEntity entity);

    /**
     * 基础类型转换
     *
     * @param metaBaseChangeSceneDOList the meta base change scene do list
     * @return list
     */
    public abstract List<MetaBaseChangeSceneEntity> convertToEntityList(List<MetaBaseChangeSceneDO> metaBaseChangeSceneDOList);

    /**
     * 从引用类型来设置entity
     *
     * @param effectiveConfigJsonRef 可能是id，也可能是实值
     * @return meta change effective config entity
     */
    MetaChangeEffectiveConfigEntity convertToEffectiveConfigEntity(String effectiveConfigJsonRef) {
        return convertKValueToEntity(effectiveConfigJsonRef, MetaChangeEffectiveConfigEntity.class);
    }

    /**
     * Covert to callback entity meta change scene callback config entity.
     *
     * @param callbackConfigJsonRef the callback config json ref
     * @return the meta change scene callback config entity
     */
    MetaChangeSceneCallbackConfigEntity covertToCallbackEntity(String callbackConfigJsonRef) {
        return convertKValueToEntity(callbackConfigJsonRef, MetaChangeSceneCallbackConfigEntity.class);
    }

    /**
     * Covert to tag entity map.
     *
     * @param tagsJsonRef the tags json ref
     * @return the map
     */
    Map<String, List<String>> covertToTagEntity(String tagsJsonRef) {
        return convertKValueToEntity(tagsJsonRef, new TypeReference<Map<String,List<String>>>(){});
    }

    /**
     * Fill k value.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @param metaChangeSceneDO     the meta change scene do
     */
    @AfterMapping
    protected void fillKValue(MetaChangeSceneEntity metaChangeSceneEntity, @MappingTarget MetaChangeSceneDO metaChangeSceneDO) {
        putKValueToEntity(metaChangeSceneEntity.getCallbackConfigEntity(),
                metaChangeSceneEntity.getCallbackConfigJsonRef(), metaChangeSceneDO.getId(), s -> {
                    metaChangeSceneDO.setCallbackConfigJsonRef(s);
                    metaChangeSceneEntity.setCallbackConfigJsonRef(s);
                });

        putKValueToEntity(metaChangeSceneEntity.getChangeEffectiveConfig(), metaChangeSceneEntity.getChangeEffectiveConfigJsonRef(),
                metaChangeSceneDO.getId(), s -> {
                    metaChangeSceneDO.setChangeEffectiveConfigJsonRef(s);
                    metaChangeSceneEntity.setChangeEffectiveConfigJsonRef(s);
                }, o -> JSON.toJSONString(o, (PropertyFilter) (object, name, value) -> !"changeProgress".equals(name)));

        putKValueToEntity(metaChangeSceneEntity.getTags(), metaChangeSceneEntity.getTagsJsonRef(),
                metaChangeSceneDO.getId(), s -> {
                    metaChangeSceneDO.setTagsJsonRef(s);
                    metaChangeSceneEntity.setTagsJsonRef(s);
                });
    }

}