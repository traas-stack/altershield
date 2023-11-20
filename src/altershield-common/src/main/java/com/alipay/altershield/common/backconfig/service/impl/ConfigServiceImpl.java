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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.common.backconfig.service.impl;

import com.alipay.altershield.common.backconfig.entity.ConfigEntity;
import com.alipay.altershield.common.backconfig.repository.ConfigRepository;
import com.alipay.altershield.common.backconfig.request.QueryConfigRequest;
import com.alipay.altershield.common.backconfig.result.QueryConfigResult;
import com.alipay.altershield.common.backconfig.service.ConfigService;
import com.alipay.altershield.common.service.ServicePageProcessTemplate;
import com.alipay.altershield.common.service.ServicePageQuery;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldPageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台配置管理服务
 *
 * @author haoxuan
 * @version ConfigServiceImpl.java, v 0.1 2022年11月25日 3:16 下午 haoxuan
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    /**
     * 查询配置列表
     *
     * @param request 查询配置列表请求结构体
     * @return 配置列表
     */
    @Override
    public AlterShieldPageResult<List<QueryConfigResult>> queryConfigList(QueryConfigRequest request) {
        return ServicePageProcessTemplate.process(request.getCurrent(), request.getPageSize(), new ServicePageQuery<QueryConfigResult>() {
            @Override
            public List<QueryConfigResult> query(int offset, int limit) {
                List<ConfigEntity> configEntities = configRepository.selectByPage(request.getName(), offset, limit);
                return configEntities.stream().map(c -> buildQueryConfigResult(c)).collect(Collectors.toList());
            }

            @Override
            public long total() {
                return configRepository.selectTotalCount();
            }
        });
    }

    /**
     * 构建配置项查询结果
     *
     * @param config 配置项实体
     * @return 查询结果结构体
     */
    private QueryConfigResult buildQueryConfigResult(ConfigEntity config) {
        QueryConfigResult result = new QueryConfigResult();
        result.setName(config.getName());
        result.setValue(config.getValue());
        result.setDesc(config.getDesc());

        return result;
    }
}