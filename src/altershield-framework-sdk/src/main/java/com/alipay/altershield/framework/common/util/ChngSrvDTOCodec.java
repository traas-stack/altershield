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
package com.alipay.altershield.framework.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.altershield.framework.common.util.logger.AlterShieldFrameworkLoggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.slf4j.Logger;

public class ChngSrvDTOCodec {
    private static final Logger logger = AlterShieldFrameworkLoggers.FRAMEWORK;

    /**
     * Decode 2 json array json array.
     *
     * @param string the string
     * @return json array
     */
    public static JSONArray decode2JSONArray(String string) {
        if (CommonUtil.isBlank(string)) {
            return null;
        }
        return JSON.parseArray(string);
    }

    /**
     * Decode dto as json json object.
     *
     * @param encodedStr the encoded str
     * @return json object
     */
    public static JSONObject decodeDTOAsJSON(String encodedStr) {
        if (CommonUtil.isBlank(encodedStr)) {
            return null;
        }
        ParserConfig conf = ParserConfig.getGlobalInstance();
        try {
            return JSON.parseObject(encodedStr, Feature.IgnoreAutoType);
        } catch (RuntimeException e) {
            AlterShieldLoggerManager.log("error", logger, "decodeDTOAsJSON.fail", e,
                    "autoTypeSupprt=" + conf.isAutoTypeSupport(), "asmEnable=" + conf.isAsmEnable(),
                    encodedStr);
            throw e;
        }
    }

    /**
     * Encode dto string.
     *
     * @param obj the obj
     * @return string
     */
    public static String encodeDTO(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj, SerializerFeature.SkipTransientField,
                SerializerFeature.WriteClassName);
    }

    /**
     * Decode param dto string t.
     *
     * @param <T> the type parameter
     * @param encodedStr the encoded str
     * @param clz the clz
     * @return t
     */
    public static <T> T decodeParamDTOString(String encodedStr, Class<T> clz) {
        if (CommonUtil.isBlank(encodedStr)) {
            return null;
        }

        return JSON.parseObject(encodedStr, clz);
    }

}
