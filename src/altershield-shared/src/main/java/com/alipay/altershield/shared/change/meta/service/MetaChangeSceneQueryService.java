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
package com.alipay.altershield.shared.change.meta.service;

import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneQueryEntity;

import java.util.List;

/**
 * @author yuanji, xiangyue, haoxuan.yhx
 * @version : MetaOpsCloudChangeSceneQueryService.java, v 0.1 2022年09月28日 14:28 yuanji Exp $
 */
public interface MetaChangeSceneQueryService {

    /**
     * 根据changeSceneKey查询场景名称
     *
     * @param changeSceneKey
     * @return
     */
    String queryMetaChangeSceneName(String changeSceneKey);

    /**
     * 根据changeSceneKey查询变更服务所属平台
     *
     * @param changeSceneKey
     * @return
     */
    String queryMetaChangeScenePlatformName(String changeSceneKey);

    /**
     * 根据changeKey获取其名称
     *
     * @param changeKey 变更批次标识
     * @return 中文名称
     */
    String queryChangeKeyName(String changeKey);

    /**
     * 根据changeSceneKey获取其关联的changeKey
     *
     * @param changeSceneKey 变更场景标识
     * @return changeKey列表
     */
    List<String> queryChangeKeysByChangeSceneKey(String changeSceneKey);

    /**
     * 获取场景主要信息
     *
     * @param
     * @return java.util.List<com.alipay.opscloud.api.change.exe.entity.MetaChangeSceneEntity>
     * @author gaofeng.pan
     * @date 2022/11/9 14:47
     **/
    List<MetaChangeSceneQueryEntity> querySceneMainInfo();

    /**
     * 根据changeSceneKey获取对象数据
     *
     * @param changeSceneKey
     * @return com.alipay.opscloud.api.change.exe.entity.MetaChangeSceneQueryEntity
     * @author gaofeng.pan
     * @date 2022/11/10 10:42
     **/
    MetaChangeSceneQueryEntity getFullEntity(String changeSceneKey);

}