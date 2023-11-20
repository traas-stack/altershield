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
///*
// * MIT License
// *
// * Copyright (c) [2023]
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
///**
// * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
// */
//package com.alipay.altershield.change.meta.service.event.converter;
//
//import com.alipay.opscloud.change.meta.model.MetaChangeSceneEntity;
//import com.alipay.opscloud.change.meta.repository.converter.MetaChangeSceneEnumMapper;
//import com.alipay.opscloud.framework.event.model.changescene.MetaChangeSceneEO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
///**
// *
// * @author yuanji
// * @version : MetaChangeSceneEventConverter.java, v 0.1 2022年07月18日 20:58 yuanji Exp $
// */
//@Mapper(uses = MetaChangeSceneEnumMapper.class )
//public abstract class MetaChangeSceneEventConverter {
//
//    /**
//     * The constant INSTANCE.
//     */
//    public static final MetaChangeSceneEventConverter INSTANCE = Mappers.getMapper(MetaChangeSceneEventConverter.class);
//
//    /**
//     * Covert to entity meta change scene entity.
//     *
//     * @param metaChangeSceneEntity the meta change scene entity
//     * @return the meta change scene EO
//     */
//    @Mapping(source = "changeEffectiveConfig.changeProgress.batchStep.childChangActionSteps", target = "changeEffectiveConfig.changeProgress.grayBatchActionSteps")
//    abstract public MetaChangeSceneEO covertToEO(MetaChangeSceneEntity metaChangeSceneEntity);
//}