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
package com.alipay.altershield.change.meta.service;


import com.alipay.altershield.change.meta.model.MetaChangeTypeEntity;
import com.alipay.altershield.change.meta.service.request.CreateMetaChangeTypeRequest;
import com.alipay.altershield.change.meta.service.request.QueryChangeTypeRequest;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;

import java.util.List;

/**
 * 变更类型元数据服务，负责管理变更对象类型，变更内容类型和变更生效戴体类型
 *
 * @author yuanji
 * @version : MetaChangeTypeService.java, v 0.1 2022年03月21日 10:23 上午 yuanji Exp $
 */
public interface MetaChangeTypeService {

    /**
     * 根据type查询分类类型
     *
     * @param request
     * @return
     */
    AlterShieldResult<List<MetaChangeTypeEntity>> queryChangeType(QueryChangeTypeRequest request);

    /**
     * 获取分类详情
     *
     * @param category
     * @param type
     * @return
     */
    MetaChangeTypeEntity getChangeType(String category, String type);

    /**
     * 插入变更分类
     * @param request
     * @return
     */
    AlterShieldResult<String> insert(CreateMetaChangeTypeRequest request);
}