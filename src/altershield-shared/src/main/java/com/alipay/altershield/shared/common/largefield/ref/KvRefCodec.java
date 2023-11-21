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
package com.alipay.altershield.shared.common.largefield.ref;

import com.alibaba.fastjson.JSON;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.core.change.model.AlterShieldChangeContent;
import com.alipay.altershield.framework.core.change.model.ChangeParentOrderInfo;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.risk.config.RiskDefenseConfig;
import com.alipay.altershield.shared.defender.model.ChangeFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shuo.qius
 * @version Nov 11, 2018
 */
public interface KvRefCodec<T> {
    /**
     *
     */
    KvRefCodec<String> NOOP = new KvRefCodec<String>() {
        @Override
        public String decode(String value) {
            return value;
        }

        @Override
        public String encode(String obj) {
            return obj;
        }
    };

    /**
     * defender change filter
     */
    KvRefCodec<ChangeFilter> CHANGE_FILTER = new KvRefCodec<ChangeFilter>() {
        @Override
        public ChangeFilter decode(String value) {
            return JSONUtil.parseJSONToObj(value,
                    ChangeFilter.class);
        }

        @Override
        public String encode(ChangeFilter obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };

    /**
     * defender rule configuration
     */
    KvRefCodec<RiskDefenseConfig> DEFENSE_CONF = new KvRefCodec<RiskDefenseConfig>() {
        @Override
        public RiskDefenseConfig decode(String value) {
            return JSONUtil.parseJSONToObj(value,
                    RiskDefenseConfig.class);
        }

        @Override
        public String encode(RiskDefenseConfig obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };

    /**
     *
     */
    KvRefCodec<List<Boolean>>       BOOL_LIST = new KvRefCodec<List<Boolean>>() {
        @Override
        public List<Boolean> decode(String value) {
            return JSONUtil.parseJSONToObjList(value,
                    Boolean.class);
        }

        @Override
        public String encode(List<Boolean> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };
    /**
     *
     */
    KvRefCodec<List<String>>        STR_LIST  = new KvRefCodec<List<String>>() {
        @Override
        public List<String> decode(String value) {
            return JSONUtil.parseJSONToObjList(value,
                    String.class);
        }

        @Override
        public String encode(List<String> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };
    /**
     *
     */
    KvRefCodec<Set<String>>         STR_SET   = new KvRefCodec<Set<String>>() {
        @Override
        public Set<String> decode(String value) {
            List<String> list = JSONUtil
                    .parseJSONToObjList(value, String.class);
            if (list == null) {
                return null;
            }
            return new HashSet<>(list);
        }

        @Override
        public String encode(Set<String> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };
    /**
     *
     */
    KvRefCodec<Map<String, String>> STR_MAP   = new KvRefCodec<Map<String, String>>() {
        @Override
        public Map<String, String> decode(String value) {
            return JSONUtil.parseJSONToMap(value);
        }

        @Override
        public String encode(Map<String, String> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };
    /***/
    KvRefCodec<Map<String, Object>> OBJ_MAP   = new KvRefCodec<Map<String, Object>>() {
        @Override
        public Map<String, Object> decode(String value) {
            if (CommonUtil.isBlank(value)) {
                return null;
            }
            return JSON.parseObject(value);
        }

        @Override
        public String encode(Map<String, Object> obj) {
            if (obj == null) {
                return null;
            }
            return JSONUtil.toJSONString(obj, false);
        }
    };

    /**
     * 变更对象类型
     **/
    KvRefCodec<List<AlterShieldChangeContent>> CHANGE_CONTENTS = new KvRefCodec<List<AlterShieldChangeContent>>() {
        @Override
        public List<AlterShieldChangeContent> decode(String value) {
            return JSONUtil.parseJSONToObjList(value,
                    AlterShieldChangeContent.class);
        }

        @Override
        public String encode(List<AlterShieldChangeContent> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };


    /**
     * 变更目标
     **/
    KvRefCodec<List<ChangeTarget>> CHANGE_TARGETS = new KvRefCodec<List<ChangeTarget>>() {
        @Override
        public List<ChangeTarget> decode(String value) {
            return JSONUtil.parseJSONToObjList(value,
                    ChangeTarget.class);
        }

        @Override
        public String encode(List<ChangeTarget> obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };

    /**
     * 变更父工单信息
     **/
    KvRefCodec<ChangeParentOrderInfo> PARENT_ORDER_INFO = new KvRefCodec<ChangeParentOrderInfo>() {
        @Override
        public ChangeParentOrderInfo decode(String value) {
            return JSONUtil.parseJSONToObj(value,
                    ChangeParentOrderInfo.class);
        }

        @Override
        public String encode(ChangeParentOrderInfo obj) {
            return JSONUtil.toJSONString(obj, false);
        }
    };

    /**
     * @param value
     * @return
     */
    T decode(String value);

    /**
     * @param obj
     * @return
     */
    String encode(T obj);

}
