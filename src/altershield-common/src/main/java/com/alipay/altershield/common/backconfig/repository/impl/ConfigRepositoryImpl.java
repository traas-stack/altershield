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
package com.alipay.altershield.common.backconfig.repository.impl;

import com.alipay.altershield.common.backconfig.dal.dataobject.MetaConfigDO;
import com.alipay.altershield.common.backconfig.dal.mapper.MetaConfigMapper;
import com.alipay.altershield.common.backconfig.entity.ConfigEntity;
import com.alipay.altershield.common.backconfig.repository.ConfigRepository;
import com.alipay.altershield.common.backconfig.repository.converter.ConfigConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author liushengdong
 * @version $Id: ConfigRepositoryImpl.java, v 0.1 2018年12月03日 11:06 PM liushengdong Exp $
 */
@Repository
public class ConfigRepositoryImpl implements ConfigRepository {

    @Autowired
    private MetaConfigMapper metaConfigMapper;
    @Override
    public String create(ConfigEntity entity) {
        return metaConfigMapper.insert(ConfigConverter.INSTANCE.convertToDataObject(entity));
    }

    @Override
    public void save(ConfigEntity entity) {
        metaConfigMapper.saveByName(ConfigConverter.INSTANCE.convertToDataObject(entity));
    }

    @Override
    public void delete(String name) {
        metaConfigMapper.deleteByName(name);
    }

    @Override
    public ConfigEntity getByName(String name) {
        MetaConfigDO configDO = metaConfigMapper.selectByName(name);
        return ConfigConverter.INSTANCE.convertToEntity(configDO);
    }

    /**
     */
    @Override
    public ConfigEntity getByNameWithCache(String name) {
        return getByName(name);
    }

    @Override
    public ConfigEntity lockByName(String name) {
        MetaConfigDO opsCloudConfigDO = metaConfigMapper.lockByName(name);
        return ConfigConverter.INSTANCE.convertToEntity(opsCloudConfigDO);
    }

    /**
     * 分页查询配置信息
     *
     * @param name 配置名称，查全可以传null
     * @param start 开始页数
     * @param size 分页大小
     * @return
     */
    @Override
    public List<ConfigEntity> selectByPage(String name, int start, int size) {
        List<MetaConfigDO> metaConfigDOS = metaConfigMapper.selectByPage(name, start, size);
        return ConfigConverter.INSTANCE.convertToEntityList(metaConfigDOS);
    }

    @Override
    public long selectTotalCount() {
        return metaConfigMapper.selectTotalCount();
    }
}