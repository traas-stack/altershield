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
package com.alipay.altershield.framework.core.change.facade.result;

/**
 * @author haoxuan
 * @version OpsCloudPageResult.java, v 0.1 2022年07月22日 2:34 下午 haoxuan
 */
public class AlterShieldPageResult<T> extends AlterShieldResult<T> {

    /**
     * current Page
     */
    private long current;

    /**
     * page Size
     */
    private long pageSize;

    /**
     * total record
     */
    private long total;

    /**
     * Constructor.
     */
    public AlterShieldPageResult() {}

    /** Constructor.
     * @param current the current
     * @param pageSize the page size
     * @param total the total
     */
    public AlterShieldPageResult(long current, long pageSize, long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    /** Constructor.
     * @param success the success
     * @param resultCode the result code
     * @param msg the msg
     * @param domain the version
     * @param current the current
     * @param pageSize the page size
     * @param total the total
     */
    public AlterShieldPageResult(boolean success, String resultCode, String msg, T domain, long current, long pageSize, long total) {
        super(success, resultCode, msg, domain);
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * Constructor.
     *
     * @param resultCode the result code
     * @param msg        the msg
     */
    public AlterShieldPageResult(String resultCode, String msg){
        super(false, resultCode, msg, null);
        this.current = 0;
        this.pageSize = 0;
        this.total = 0;
    }

    /** Constructor.
     * @param domain the version
     * @param current the current
     * @param pageSize the page size
     * @param total the total
     */
    public AlterShieldPageResult(T domain, long current, long pageSize, long total) {
        super(domain);
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    /** Constructor.
     * @param domain the version
     * @param current the current
     * @param pageSize the page size
     * @param total the total
     * @param msg the msg
     */
    public AlterShieldPageResult(T domain, long current, long pageSize, long total, String msg) {
        super(domain);
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
        this.setMsg(msg);
    }

    /** Gets get current.
     * @return the get current
     */
    public long getCurrent() {
        return current;
    }

    /** Sets set current.
     * @param current the current
     */
    public void setCurrent(long current) {
        this.current = current;
    }

    /** Gets get page size.
     * @return the get page size
     */
    public long getPageSize() {
        return pageSize;
    }

    /** Sets set page size.
     * @param pageSize the page size
     */
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    /** Gets get total.
     * @return the get total
     */
    public long getTotal() {
        return total;
    }

    /** Sets set total.
     * @param total the total
     */
    public void setTotal(long total) {
        this.total = total;
    }
}