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
package com.alipay.altershield.common.service.impl;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.framework.core.change.facade.HeartbeatCheck;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liushengdong
 * @version $Id: HeartbeatCheckImpl.java, v 0.1 2018年12月05日 3:54 PM liushengdong Exp $
 */
@Service("heartbeatCheck")
public class HeartbeatCheckImpl implements HeartbeatCheck {



    @Override
    public AlterShieldResult<Map<String, String>> doCheck() {
        //心跳,可以通过配置推送,直接返回false.导致一键停止check操作
        Map<String, String> map = new HashMap<>();
        map.put(OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK, String.valueOf(AlterShieldConstant.ALTER_SHIELD_CLIENT_SWITCH_CHG_SRV_CHECK));
        map.put(OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS, AlterShieldConstant.ALTER_SHIELD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS);
        return AlterShieldResult.succeed("success", map);
    }

    @Override
    public AlterShieldResult<Map<String, String>> doCheck(String platform) {
        Map<String, String> map = new HashMap<>();
        map.put(OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK, String.valueOf(AlterShieldConstant.ALTER_SHIELD_CLIENT_SWITCH_CHG_SRV_CHECK));
        map.put(OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS, AlterShieldConstant.ALTER_SHIELD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS);

        //todo 完善控制
        return AlterShieldResult.succeed("success", map);
    }

}