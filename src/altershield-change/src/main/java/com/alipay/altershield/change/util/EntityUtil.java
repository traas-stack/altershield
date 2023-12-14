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
package com.alipay.altershield.change.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.change.meta.service.request.MetaChangeSceneImportRequest;
/**
 * The type Entity util.
 *
 * @author jinyalong
 * @version : EntityUtil.java, v 0.1 2023年10月08日 09:30 下午 jinyalong Exp $
 */
public class EntityUtil {

    /**
     * Export entity string.
     *
     * @param metaChangeSceneImportRequest the meta change scene import request
     * @return the string
     */
    public static String exportEntity(MetaChangeSceneImportRequest metaChangeSceneImportRequest) {
        String json = JSON.toJSONString(metaChangeSceneImportRequest, (PropertyFilter) (object, name, value) -> {
            if ("id".equals(name)) {
                return false;
            }
            if ("gmtCreate".equals(name)) {
                return false;
            }
            if ("gmtModified".equals(name)) {
                return false;
            }
            if (name.endsWith("JsonRef")) {
                return false;
            }
            if("changeProgress".equals(name))
            {
                return false;
            }
            return true;
        }, SerializerFeature.DisableCircularReferenceDetect);
        return json;
    }

    /**
     * Build detail url string.
     *
     * @param exeNode the exe node
     * @return the string
     */
    public static String buildNodeDetailUrl(ExeNodeEntity exeNode)
    {
        return String.format(AlterShieldConstant.ALTER_SHIELD_NODE_DETAIL_URL, exeNode.getOrderId(), exeNode.getNodeExeId());
    }

    /**
     * Build order detail url string.
     *
     * @param orderId the order id
     * @return the string
     */
    public static String buildOrderDetailUrl(String orderId)
    {
        return AlterShieldConstant.ALTER_SHIELD_ORDER_DETAIL_URL + orderId;
    }
}