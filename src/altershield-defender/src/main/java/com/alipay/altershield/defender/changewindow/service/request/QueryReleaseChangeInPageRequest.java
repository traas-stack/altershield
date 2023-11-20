/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.service.request;

import com.alipay.opscloud.common.request.OpsCloudPageRequest;
import lombok.Data;

/**
 * 根据条件查询破网申请
 *
 * @author wb-zs909667
 * @version : QueryReleaseChangeInPageRequest.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
@Data
public class QueryReleaseChangeInPageRequest extends OpsCloudPageRequest {
    /**
     * 封网计划id,为空是查询所有破网申请，不为空是查询这个破网计划id对应的所有破网申请
     */
    private String blockChangeId;

    /**
     * 破网申请人
     */
    private String operator;

    /**
     * 破网名称
     */
    private String releaseChangeName;

    /**
     * 迭代单号
     */
    private String id;
}