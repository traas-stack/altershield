/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.repository;


import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;

import java.util.List;

/**
 * 破网相关
 *
 * @author wb-zs909667
 * @version : MetaReleaseChangeRepository.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
public interface MetaReleaseChangeRepository {

    /**
     * 分页查询封网计划对应的破网申请列表
     *
     * @param param
     * @return
     */
    List<MetaReleaseChangeEntity> selectByPage(OpscldMetaReleasechangeParam param);

    /**
     * 分页查询封网计划对应的破网申请列表
     *
     * @param param
     * @return
     */
    long selectTotalCount(OpscldMetaReleasechangeParam param);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 更新
     *
     * @param id
     * @return
     */
    boolean updateReleaseChangeStatus(String status, String id);
}
