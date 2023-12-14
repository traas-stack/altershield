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
public class MetaChangeStepDO {
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
     *   变更步骤名称
     *
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   变更场景key
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   变更key
     *
     *
     * @mbg.generated
     */
    private String changeKey;

    /**
     * Database Column Remarks:
     *   变更步骤类型。change_batch,change_action,change_action_prefix,change_action_suffix
     *
     *
     * @mbg.generated
     */
    private String stepType;

    /**
     * Database Column Remarks:
     *   变更内容类型
     *
     *
     * @mbg.generated
     */
    private String changeContentType;

    /**
     * Database Column Remarks:
     *   生效载体类型
     *
     *
     * @mbg.generated
     */
    private String effectiveTargetType;

    /**
     * Database Column Remarks:
     *   变更对象类型
     *
     *
     * @mbg.generated
     */
    private String changeTargetType;

    /**
     * Database Column Remarks:
     *   防御配置信息
     *
     *
     * @mbg.generated
     */
    private String defenceConfigJsonRef;

    /**
     *
     * @return the value of altershield_meta_change_step.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the value for altershield_meta_change_step.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for altershield_meta_change_step.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for altershield_meta_change_step.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the value for altershield_meta_change_step.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.change_scene_key
     *
     * @mbg.generated
     */
    public String getChangeSceneKey() {
        return changeSceneKey;
    }

    /**
     *
     * @param changeSceneKey the value for altershield_meta_change_step.change_scene_key
     *
     * @mbg.generated
     */
    public void setChangeSceneKey(String changeSceneKey) {
        this.changeSceneKey = changeSceneKey;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.change_key
     *
     * @mbg.generated
     */
    public String getChangeKey() {
        return changeKey;
    }

    /**
     *
     * @param changeKey the value for altershield_meta_change_step.change_key
     *
     * @mbg.generated
     */
    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.step_type
     *
     * @mbg.generated
     */
    public String getStepType() {
        return stepType;
    }

    /**
     *
     * @param stepType the value for altershield_meta_change_step.step_type
     *
     * @mbg.generated
     */
    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.change_content_type
     *
     * @mbg.generated
     */
    public String getChangeContentType() {
        return changeContentType;
    }

    /**
     *
     * @param changeContentType the value for altershield_meta_change_step.change_content_type
     *
     * @mbg.generated
     */
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.effective_target_type
     *
     * @mbg.generated
     */
    public String getEffectiveTargetType() {
        return effectiveTargetType;
    }

    /**
     *
     * @param effectiveTargetType the value for altershield_meta_change_step.effective_target_type
     *
     * @mbg.generated
     */
    public void setEffectiveTargetType(String effectiveTargetType) {
        this.effectiveTargetType = effectiveTargetType;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.change_target_type
     *
     * @mbg.generated
     */
    public String getChangeTargetType() {
        return changeTargetType;
    }

    /**
     *
     * @param changeTargetType the value for altershield_meta_change_step.change_target_type
     *
     * @mbg.generated
     */
    public void setChangeTargetType(String changeTargetType) {
        this.changeTargetType = changeTargetType;
    }

    /**
     *
     * @return the value of altershield_meta_change_step.defence_config_json_ref
     *
     * @mbg.generated
     */
    public String getDefenceConfigJsonRef() {
        return defenceConfigJsonRef;
    }

    /**
     *
     * @param defenceConfigJsonRef the value for altershield_meta_change_step.defence_config_json_ref
     *
     * @mbg.generated
     */
    public void setDefenceConfigJsonRef(String defenceConfigJsonRef) {
        this.defenceConfigJsonRef = defenceConfigJsonRef;
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
        sb.append(", name=").append(name);
        sb.append(", changeSceneKey=").append(changeSceneKey);
        sb.append(", changeKey=").append(changeKey);
        sb.append(", stepType=").append(stepType);
        sb.append(", changeContentType=").append(changeContentType);
        sb.append(", effectiveTargetType=").append(effectiveTargetType);
        sb.append(", changeTargetType=").append(changeTargetType);
        sb.append(", defenceConfigJsonRef=").append(defenceConfigJsonRef);
        sb.append("]");
        return sb.toString();
    }
}