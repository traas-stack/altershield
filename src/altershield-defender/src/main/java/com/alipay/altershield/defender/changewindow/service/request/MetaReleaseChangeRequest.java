/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.service.request;

import com.alipay.opscloud.changewindow.enums.ReleaseChangeStatusEnum;
import lombok.Data;

/**
 * 根据条件查询破网申请
 *
 * @author wb-zs909667
 * @version : MetaReleaseChangeRequest.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
@Data
public class MetaReleaseChangeRequest {
    /**
     * 破网申请id
     */
    private String id;
    /**
     * 破网申请状态
     */
    private ReleaseChangeStatusEnum status;
}
