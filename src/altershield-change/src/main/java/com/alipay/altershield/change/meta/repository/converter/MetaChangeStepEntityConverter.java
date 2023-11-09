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

import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeStepDO;
import com.alipay.altershield.change.meta.model.MetaChangeDefenceConfigEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.shared.common.largefield.converter.AbstractEntityConverter;
import com.alipay.altershield.framework.common.util.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * The type Meta change step entity converter.
 *
 * @author yuanji
 * @version : MetaChangeStepConverter.java, v 0.1 2022年03月16日 3:13 下午 yuanji Exp $
 */
@Mapper(componentModel = "spring")
public abstract class MetaChangeStepEntityConverter extends AbstractEntityConverter {

    /**
     * Convert to entity.
     *
     * @param metaChangeStepDO     the meta change step do
     * @param metaChangeStepEntity the meta change step entity
     */
    @Mapping(source = "defenceConfigJsonRef", target = "defenceConfig")
    abstract public void convertToEntity(MetaChangeStepDO metaChangeStepDO, @MappingTarget MetaChangeStepEntity metaChangeStepEntity);

    /**
     * Convert to data object list list.
     *
     * @param metaChangeStepEntities the meta change step entities
     * @return the list
     */
    abstract public List<MetaChangeStepDO> convertToDataObjectList(List<MetaChangeStepEntity> metaChangeStepEntities);

    /**
     * Convert to data object meta change step do.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @return the meta change step do
     */
    abstract public MetaChangeStepDO convertToDataObject(MetaChangeStepEntity metaChangeStepEntity);

    /**
     * Map meta change defence config entity.
     *
     * @param defenceConfigRef the defence config ref
     * @return the meta change defence config entity
     */
    protected MetaChangeDefenceConfigEntity map(String defenceConfigRef) {
        if(StringUtils.isBlank(defenceConfigRef))
        {
            return null;
        }
        return JSONUtil.parseJSONToObj(defenceConfigRef, MetaChangeDefenceConfigEntity.class);
    }

    /**
     * Fill k value.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @param metaChangeStepDO     the meta change step do
     */
    @AfterMapping
    protected void fillKValue(MetaChangeStepEntity metaChangeStepEntity, @MappingTarget MetaChangeStepDO metaChangeStepDO) {
        if(metaChangeStepEntity.getDefenceConfig() != null)
        {
            metaChangeStepDO.setDefenceConfigJsonRef(JSONUtil.toJSONString(metaChangeStepEntity.getDefenceConfig(), false));
        }
    }

}