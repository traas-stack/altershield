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
package com.alipay.altershield.framework.core.change.entity;


import com.alipay.altershield.framework.core.change.entity.enums.PermissionLevelEnum;
import com.alipay.altershield.framework.core.change.vo.ServicePermissionConfigVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务权限信息
 * @author yuefan.wyf
 */
@Data
public class ServicePermissionConfigEntity implements Serializable {

    private static final long serialVersionUID = 2322315656112987251L;

    private PermissionLevelEnum permissionLevel = PermissionLevelEnum.PUBLIC;

    private List<String> whiteList = new ArrayList<>();

    /**
     * Convert 2 dto service permission config vo.
     *
     * @return the service permission config vo
     */
    public ServicePermissionConfigVO convert2DTO() {
        ServicePermissionConfigVO model = new ServicePermissionConfigVO();
        model.setPermissionLevel(this.permissionLevel);
        model.setWhiteList(this.getWhiteList());
        return model;
    }

    /**
     * Fill.
     *
     * @param dto the dto
     */
    public void fill(ServicePermissionConfigVO dto) {
        if (dto != null) {
            this.setPermissionLevel(dto.getPermissionLevel());
            this.setWhiteList(dto.getWhiteList());
        }
    }

}
