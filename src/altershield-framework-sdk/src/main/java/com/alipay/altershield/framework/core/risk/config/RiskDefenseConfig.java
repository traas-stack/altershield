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
package com.alipay.altershield.framework.core.risk.config;

import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.framework.core.change.facade.enums.RiskInvokeModeEnum;
import com.alipay.altershield.framework.core.change.facade.enums.RiskTypeEnum;
import lombok.Data;

import java.util.List;

/**
 *
 * @author haoxuan.yhx
 * @version $Id: RiskDefenseConfigEntity.java, v 0.1 2019年04月18日 下午8:14 haoxuan.yhx Exp $
 */
@Data
public class RiskDefenseConfig {

    /**
     * 回调host
     */
    private String host;

    /**
     * 默认url
     */
    private String defaultUrl;

    /**
     * 调用方式：同步tr、异步tr、http、https、plugin
     */
    private RiskInvokeModeEnum invokeMode = RiskInvokeModeEnum.ASYNC_TR;

    /**
     * 风险能力类型
     */
    private RiskTypeEnum riskType = RiskTypeEnum.CHECKER;

    /**
     * 扩展字段
     */
    private JSONObject ext;

    /**
     * 白名单服务标识列表
     */
    private List<String> whiteServiceKeyList;

    /**
     * 黑名单服务标识列表
     */
    private List<String> blackServiceKeyList;

    /**
     * 是否对用户可见
     */
    private boolean visible = false;

}
