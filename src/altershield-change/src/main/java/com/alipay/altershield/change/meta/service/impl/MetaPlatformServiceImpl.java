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

import com.alipay.altershield.change.meta.model.MetaChangePlatformEntity;
import com.alipay.altershield.change.meta.repository.MetaChangePlatformRepository;
import com.alipay.altershield.change.meta.service.MetaPlatformService;
import com.alipay.altershield.change.meta.service.request.CreateMetaPlatformRequest;
import com.alipay.altershield.change.meta.service.request.converter.MetaPlatformRequestConverter;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanji
 * @version : MetaPlatformServiceImpl.java, v 0.1 2022年06月22日 16:24 yuanji Exp $
 */
@Service
public class MetaPlatformServiceImpl implements MetaPlatformService {

    @Autowired
    private MetaChangePlatformRepository metaChangePlatformRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MetaPlatformRequestConverter reqConverter;

    @Override
    public AlterShieldResult<List<String>> queryAllPlatform() {

        return ServiceProcessTemplate.wrapTryCatch(() -> {
            List<String> platforms = metaChangePlatformRepository.getAllPlatform();
            return AlterShieldResult.succeed("query success", platforms);
        });
    }

    @Override
    public AlterShieldResult<String> create(CreateMetaPlatformRequest request) {
        MetaChangePlatformEntity metaPlatform = reqConverter.convert2Entity(request);
        metaPlatform.setId(idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_PLATFORM));
        return AlterShieldResult.succeed("create success", metaChangePlatformRepository.create(metaPlatform));
    }
}