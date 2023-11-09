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

import com.alipay.opscloud.change.meta.model.MetaPlatformEntity;
import com.alipay.opscloud.change.meta.repository.MetaChangePlatformRepository;
import com.alipay.opscloud.change.meta.service.MetaPlatformService;
import com.alipay.opscloud.change.meta.service.request.CreateMetaPlatformRequest;
import com.alipay.opscloud.change.meta.service.request.converter.MetaPlatformRequestConverter;
import com.alipay.opscloud.common.id.IdGenerator;
import com.alipay.opscloud.common.service.ServiceProcessTemplate;
import com.alipay.opscloud.framework.core.common.facade.result.OpsCloudResult;
import com.alipay.opscloud.tools.common.id.IdBizCodeEnum;
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
    public OpsCloudResult<List<String>> queryAllPlatform() {

        return ServiceProcessTemplate.wrapTryCatch(() -> {
            List<String> platforms = metaChangePlatformRepository.getAllPlatform();
            return OpsCloudResult.succeed("query success", platforms);
        });
    }

    @Override
    public OpsCloudResult<String> create(CreateMetaPlatformRequest request) {
        MetaPlatformEntity metaPlatform = reqConverter.convert2Entity(request);
        metaPlatform.setId(idGenerator.generateIdWithNoSharding(IdBizCodeEnum.OPSCLD_META_PLATFORM));
        return OpsCloudResult.succeed("create success", metaChangePlatformRepository.create(metaPlatform));
    }
}