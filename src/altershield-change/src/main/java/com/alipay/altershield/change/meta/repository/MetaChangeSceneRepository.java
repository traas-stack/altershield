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
package com.alipay.altershield.change.meta.repository;


import com.alipay.altershield.change.meta.model.MetaBaseChangeSceneEntity;
import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.change.meta.model.effective.MetaChangeStepEntity;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneBatchEntity;
import com.alipay.altershield.shared.change.exe.entity.MetaChangeSceneQueryEntity;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;

import java.util.List;

/**
 * The interface Meta change scene repository.
 *
 * @author yuanji
 * @version : OpsCloudChangeSceneRepository.java, v 0.1 2022年02月14日 2:42 下午 yuanji Exp $
 */
public interface MetaChangeSceneRepository {

    /**
     * 根据id得到变更场景的详情
     *
     * @param id the id
     * @return change scene details by id
     */
    MetaChangeSceneEntity getChangeSceneDetailsById(String id);

    /**
     * 根据changeKey获取变更场景的基本信息
     *
     * @param changeSceneKey the change scene key
     * @return change scene by change scene key
     */
    MetaChangeSceneEntity getChangeSceneByChangeSceneKey(String changeSceneKey);

    /**
     * 查询变更场景基本信息
     *
     * @param param the param
     * @return list
     */
    List<MetaBaseChangeSceneEntity> selectChangeSceneList(MetaChangeSceneQueryParam param);

    /**
     * 查询变更场景基本信息数量
     *
     * @param param the param
     * @return long
     */
    long selectChangeSceneListCount(MetaChangeSceneQueryParam param);

    /**
     * Insert boolean.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @return the boolean
     */
    boolean insert(MetaChangeSceneEntity metaChangeSceneEntity);

    /**
     * Insert boolean.
     *
     * @param metaChangeSceneEntity    the meta change scene entity
     * @param metaChangeStepEntityList the meta change step entity list
     * @return the boolean
     */
    boolean insert(MetaChangeSceneEntity metaChangeSceneEntity, List<MetaChangeStepEntity> metaChangeStepEntityList);

    /**
     * Insert step boolean.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @return the boolean
     */
    boolean insertStep(MetaChangeStepEntity metaChangeStepEntity);

    /**
     * Update step boolean.
     *
     * @param metaChangeStepEntity the meta change step entity
     * @return the boolean
     */
    boolean updateStep(MetaChangeStepEntity metaChangeStepEntity);

    /**
     * Delete step boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteStep(String id);

    /**
     * Gets change step entity.
     *
     * @param changeKey the change key
     * @return the change step entity
     */
    MetaChangeStepEntity getChangeStepEntity(String changeKey);

    /**
     * Update boolean.
     *
     * @param metaChangeSceneEntity the meta change scene entity
     * @return the boolean
     */
    boolean update(MetaChangeSceneEntity metaChangeSceneEntity);

    /**
     * 根据代G
     * @param metaChangeSceneEntity
     * @return
     */
    boolean updateGeneration(MetaChangeSceneEntity metaChangeSceneEntity);

    /**
     * Delete change scene boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteChangeScene(String id);

    /**
     * 根据changeKey和type查询数量
     *
     * @param changeKey the change key
     * @param typeEnum  the type enum
     * @return int
     */
    int selectCountChangeStepByKeyAndType(String changeKey, MetaChangeStepTypeEnum typeEnum);

    /**
     * Select change step by change scene key list.
     *
     * @param changeSceneKey the change scene key
     * @return the list
     */
    List<MetaChangeStepEntity> selectChangeStepByChangeSceneKey(String changeSceneKey);

    /**
     * Select change step by change scene key and type list.
     *
     * @param changeKey the change key
     * @param typeEnum  the type enum
     * @return the list
     */
    List<MetaChangeStepEntity> selectChangeStepByChangeSceneKeyAndType(String changeKey, MetaChangeStepTypeEnum typeEnum);

    /**
     * 批量更新
     *
     * @param metaChangeStepEntityList the meta change step entity list
     * @return int
     */
    int batchUpdateChangeStep(List<MetaChangeStepEntity> metaChangeStepEntityList);

    /**
     * 判断场景key是否存在
     * @param changeSceneKey
     * @return
     */
    boolean existed(String changeSceneKey);


    /**
     * 判断change key是否存在
     * @param changeKey
     * @return
     */
    boolean changeKeyExisted(String changeKey);

    /**
     * 獲取場景數據
     *
     * @return java.util.List<com.alipay.opscloud.change.meta.model.effective.MetaChangeSceneBatchEntity>
     * @author gaofeng.pan
     * @date 2022/10/24 19:00
     **/
    List<MetaChangeSceneBatchEntity> queryAllScene();


    /**
     * 获取场景主要信息
     *
     * @param
     * @return java.util.List<com.alipay.opscloud.api.change.exe.entity.MetaChangeSceneQueryEntity>
     * @author gaofeng.pan
     * @date 2022/11/9 16:53
     **/
    List<MetaChangeSceneQueryEntity> querySceneMainInfo();

    /**
     * 根据changeSceneKey获取对象
     *
     * @param changeSceneKey
     * @return com.alipay.opscloud.api.change.exe.entity.MetaChangeSceneQueryEntity
     * @author gaofeng.pan
     * @date 2022/11/10 10:44
     **/
    MetaChangeSceneQueryEntity getFullEntity(String changeSceneKey);
}