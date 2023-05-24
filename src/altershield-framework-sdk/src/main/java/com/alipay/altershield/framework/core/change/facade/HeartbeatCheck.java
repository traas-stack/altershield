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
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.alipay.altershield.framework.core.change.facade;

import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;

import java.util.Map;

/**
 * @author liushengdong
 * @version $Id: HeartbeatCheck.java, v 0.1 2018年12月03日 9:17 PM liushengdong Exp $
 */
public interface HeartbeatCheck {

    /**
     *
     */
    String OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS =
            "OPSCLOUD_CLIENT_SWITCH_OFF_CHANGE_SCENE_KEYS";

    String OPSCLOUD_CLIENT_SWITCH_OFF_DATA_IDS = "OPSCLOUD_CLIENT_SWITCH_OFF_DATA_IDS";

    /**
     * client的开关控制,白名单 内部是一个jsonstr,配置只针对于当前平台 {"whiteListChangeSceneKeys":""} --都不能访问ops
     * {"whiteListChangeSceneKeys":"*"} --都能访问ops
     * {"whiteListChangeSceneKeys":"changeSceneKey1,changeSceneKey2"} --指定的serviceKey能访问ops
     */
    String OPSCLOUD_CLIENT_SWITCH_CONF = "OPSCLOUD_CLIENT_SWITCH_CONF";

    /**
     * serviceKey维度的限流 内部是一个jsonstr,配置只针对于当前平台 对应的value为json格式: {
     * <p>
     * "$serviceKey":"$limit", --具体某个serviceKey的值,优先级 > default
     * <p>
     * "default":"$limit" --默认值.如果配置了,则所有是serviceKey都会被限流.如果不配置,则只有配置了serviceKey的才会被限流
     * <p>
     * }
     */
    String OPSCLOUD_LIMIT_TIMES_CONF = "OPSCLOUD_LIMIT_TIMES_CONF";

    String OPSCLOUD_ASYNC_INVOKE_CONF = "OPSCLOUD_ASYNC_INVOKE_CONF";

    /**
     * 纯管控接口切换开关的KEY (true:开启check false:关闭check)
     */
    String OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK = "OPSCLOUD_CLIENT_SWITCH_CHG_SRV_CHECK";

    /**
     * @return Map[key, value]
     */
    AlterShieldResult<Map<String, String>> doCheck();

    /**
     * @return Map[key, value]
     */
    AlterShieldResult<Map<String, String>> doCheck(String platform);

}
