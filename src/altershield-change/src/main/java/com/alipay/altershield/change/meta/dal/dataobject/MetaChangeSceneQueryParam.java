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
package com.alipay.altershield.change.meta.dal.dataobject;

import java.util.List;

/**
 * @author yuanji
 * @version : MetaChangeSceneQueryParam.java, v 0.1 2022年06月22日 18:26 yuanji Exp $
 */
public class MetaChangeSceneQueryParam {

    /**
     * 场景名
     */
    private String changeSceneName;

    /**
     * 场景key
     */
    private String changeSceneKey;

    /**
     * 平台名
     */
    private String platformName;

    /**
     * 0 是暂存，1是正式态
     */
    private Integer status;

    /**
     * 变更代G
     */
    private String generation;

    /**
     * 可信租户
     */
    private List<String> tldcTenantCodes;

    /**
     * 场景管理员
     */
    private String owner;

    /**
     * 查询偏移
     */
    private int offset = 1;

    /**
     * 大小
     */
    private int limit = 20;

    /**
     * Gets get change scene name.
     *
     * @return the get change scene name
     */
    public String getChangeSceneName() {
        return changeSceneName;
    }

    /**
     * Sets set change scene name.
     *
     * @param changeSceneName the change scene name
     */
    public void setChangeSceneName(String changeSceneName) {
        this.changeSceneName = changeSceneName;
    }

    /**
     * Gets get platform name.
     *
     * @return the get platform name
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * Sets set platform name.
     *
     * @param platformName the platform name
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * Gets get status.
     *
     * @return the get status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets set status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets get generation.
     *
     * @return the get generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * Sets set generation.
     *
     * @param generation the generation
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * Gets get tldc tenant codes.
     *
     * @return the get tldc tenant codes
     */
    public List<String> getTldcTenantCodes() {
        return tldcTenantCodes;
    }

    /**
     * Sets set tldc tenant codes.
     *
     * @param tldcTenantCodes the tldc tenant codes
     */
    public void setTldcTenantCodes(List<String> tldcTenantCodes) {
        this.tldcTenantCodes = tldcTenantCodes;
    }

    /**
     * Gets get offset.
     *
     * @return the get offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets set offset.
     *
     * @param offset the offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Gets get limit.
     *
     * @return the get limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets set limit.
     *
     * @param limit the limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Gets get change scene key.
     *
     * @return the get change scene key
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     * Sets set change scene key.
     *
     * @param changeSceneKey the change scene key
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     * Gets get owner.
     *
     * @return the get owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets set owner.
     *
     * @param owner the owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}