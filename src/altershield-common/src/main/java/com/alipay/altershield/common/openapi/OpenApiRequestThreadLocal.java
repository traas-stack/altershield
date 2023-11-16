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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.common.openapi;

/**
 * @author yuanji
 * @version : OpenApiRequestThreadLocal.java, v 0.1 2022年06月10日 16:32 yuanji Exp $
 */
public class OpenApiRequestThreadLocal {

    private static final ThreadLocal<OpenApiRequestObject> local = new ThreadLocal<>();

    /**
     * 设置平台信息
     * @param platform
     */
    public static void setPlatform(String platform)
    {
        OpenApiRequestObject object =  local.get();
        if(object == null)
        {
            object = new OpenApiRequestObject();
            local.set(object);
        }
        object.setPlatform(platform);

    }

    /**
     * 获取上下文中的平台信息
     * @return
     */
    public static String getPlatform()
    {
        OpenApiRequestObject object =  local.get();
        return object == null ? null : object.getPlatform();
    }

    /**
     * 清除线程中的信息
     */
    public static void clear()
    {
        local.remove();
    }

    public static class OpenApiRequestObject {

        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
    }
}