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

/**
 * @author jinyalong
 */
public class MetaChangeTypeDO {
    /**
     * Database Column Remarks:
     *   主鍵
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
     *   变更内容类型。唯一标识
     *
     *
     * @mbg.generated
     */
    private String type;

    /**
     * Database Column Remarks:
     *   变更内容类型名字
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   详情描述
     *
     *
     * @mbg.generated
     */
    private String typeDesc;

    /**
     * Database Column Remarks:
     *   分类
     *
     *
     * @mbg.generated
     */
    private String category;

    /**
     *
     * @return the value of altershield_meta_change_type.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for altershield_meta_change_type.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_meta_change_type.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_meta_change_type.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.type
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type the value for altershield_meta_change_type.type
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for altershield_meta_change_type.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.type_desc
     *
     * @mbg.generated
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     *
     * @param typeDesc the value for altershield_meta_change_type.type_desc
     *
     * @mbg.generated
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    /**
     *
     * @return the value of altershield_meta_change_type.category
     *
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category the value for altershield_meta_change_type.category
     *
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", typeDesc=").append(typeDesc);
        sb.append(", category=").append(category);
        sb.append("]");
        return sb.toString();
    }
}