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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.common.backconfig.request;

/**
 * 查询后台配置请求结构体
 *
 * @author haoxuan
 * @version QueryConfigRequest.java, v 0.1 2022年11月25日 3:06 下午 haoxuan
 */
public class QueryConfigRequest {

    /**
     * 配置名
     */
    private String name;

    /**
     * 当前页数
     */
    private int current;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>current</tt>.
     *
     * @return property value of current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Setter method for property <tt>current</tt>.
     *
     * @param current value to be assigned to property current
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     *
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     *
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}