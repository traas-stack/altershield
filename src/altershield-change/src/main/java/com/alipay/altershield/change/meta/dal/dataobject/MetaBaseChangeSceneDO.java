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
package com.alipay.altershield.change.meta.dal.dataobject;

import java.util.Date;
import java.util.Objects;

/**
 * @author jinyalong
 */
public class MetaBaseChangeSceneDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   租户
     *
     *
     * @mbg.generated
     */
    private String tenantCode;

    /**
     * Database Column Remarks:
     *   变更场景名字
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   场景key,一个场景的唯一标识
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   场景负责人，多个用逗号隔开
     *
     *
     * @mbg.generated
     */
    private String owner;

    /**
     * Database Column Remarks:
     *   场景代G；G0,G1,G2,G3,G4
     *
     *
     * @mbg.generated
     */
    private String generation;

    /**
     * Database Column Remarks:
     *   平台名字，altershield_meta_platform
     *
     *
     * @mbg.generated
     */
    private String platformName;

    /**
     * Database Column Remarks:
     *   变更场景状态 0.暂存态 1.发布状态
     *
     *
     * @mbg.generated
     */
    private int status;

    /**
     *
     * @return the value of altershield_meta_change_scene.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for altershield_meta_change_scene.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_meta_change_scene.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_meta_change_scene.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.tenant_code
     *
     * @mbg.generated
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     *
     * @param tenantCode the value for altershield_meta_change_scene.tenant_code
     *
     * @mbg.generated
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for altershield_meta_change_scene.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.change_scene_key
     *
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     *
     * @param changeSceneKey the value for altershield_meta_change_scene.change_scene_key
     *
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.owner
     *
     * @mbg.generated
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner the value for altershield_meta_change_scene.owner
     *
     * @mbg.generated
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.generation
     *
     * @mbg.generated
     */
    public String getGeneration() {
        return generation;
    }

    /**
     *
     * @param generation the value for altershield_meta_change_scene.generation
     *
     * @mbg.generated
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.platform_name
     *
     * @mbg.generated
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     *
     * @param platformName the value for altershield_meta_change_scene.platform_name
     *
     * @mbg.generated
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     *
     * @return the value of altershield_meta_change_scene.status
     *
     * @mbg.generated
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status the value for altershield_meta_change_scene.status
     *
     * @mbg.generated
     */
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MetaBaseChangeSceneDO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", tenantCode='").append(tenantCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", changeSceneKey='").append(changeSceneKey).append('\'');
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", generation='").append(generation).append('\'');
        sb.append(", platformName='").append(platformName).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof MetaBaseChangeSceneDO)) { return false; }
        MetaBaseChangeSceneDO that = (MetaBaseChangeSceneDO) o;
        return id.equals(that.id) &&
                tenantCode.equals(that.tenantCode) &&
                name.equals(that.name) &&
                changeSceneKey.equals(that.changeSceneKey) &&
                owner.equals(that.owner) &&
                generation.equals(that.generation) &&
                platformName.equals(that.platformName) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(changeSceneKey);
    }
}
