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

import com.alipay.altershield.change.meta.dal.dataobject.MetaChangeTypeDO;
import com.alipay.altershield.change.meta.model.MetaChangeTypeEntity;
import com.alipay.altershield.change.meta.service.request.CreateMetaChangeTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The interface Meta change type converter.
 *
 * @author yuanji
 * @version : MetaChangeContentTypeConvertor.java, v 0.1 2022年03月14日 7:32 下午 yuanji Exp $
 */
@Mapper
public interface MetaChangeTypeConverter {

    /**
     * The constant INSTANCE.
     */
    MetaChangeTypeConverter INSTANCE = Mappers.getMapper(MetaChangeTypeConverter.class);

    /**
     * Map to entity meta change type entity.
     *
     * @param metaChangeTypeDO the meta change type do
     * @return the meta change type entity
     */
    @Mapping(source = "typeDesc", target = "description")
    MetaChangeTypeEntity mapToEntity( MetaChangeTypeDO metaChangeTypeDO);

    /**
     * Map to entity meta change type entity.
     *
     * @param request the request
     * @return the meta change type entity
     */
    MetaChangeTypeEntity mapToEntity(CreateMetaChangeTypeRequest request);

    /**
     * Map to entity list list.
     *
     * @param metaChangeTypeDOList the meta change type do list
     * @return the list
     */
    @Mapping(source = "typeDesc", target = "description")
    List<MetaChangeTypeEntity> mapToEntityList(List<MetaChangeTypeDO> metaChangeTypeDOList);

    /**
     * To data object meta change type do.
     *
     * @param metaChangeTypeEntity the meta change type entity
     * @return the meta change type do
     */
    @Mapping(target = "typeDesc", source = "description")
    MetaChangeTypeDO toDataObject(MetaChangeTypeEntity metaChangeTypeEntity);

    /**
     * To data object list list.
     *
     * @param metaChangeTypeEntityList the meta change type entity list
     * @return the list
     */
    @Mapping(target = "typeDesc", source = "description")
    List<MetaChangeTypeDO> toDataObjectList(List<MetaChangeTypeEntity> metaChangeTypeEntityList);


}