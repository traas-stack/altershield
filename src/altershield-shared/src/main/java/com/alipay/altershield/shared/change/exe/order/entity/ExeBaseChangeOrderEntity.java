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
package com.alipay.altershield.shared.change.exe.order.entity;

import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import com.alipay.altershield.shared.change.exe.entity.AbstractExeTraceEntity;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 变更执行单基本信息对象
 * @author yuanji
 * @version : ExeBaseChangeOrderEntity.java, v 0.1 2022年03月24日 3:10 下午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExeBaseChangeOrderEntity extends AbstractExeTraceEntity {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String orderId;

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
     *   业务工单id
     *
     *
     * @mbg.generated
     */
    private String bizExecOrderId;

    /**
     * Database Column Remarks:
     *   场景key
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   变更平台名
     *
     *
     * @mbg.generated
     */
    private String platform;

    /**
     * Database Column Remarks:
     *   分区位
     *
     *
     * @mbg.generated
     */
    private String uid;



    /**
     * Database Column Remarks:
     *   变更单标题
     *
     *
     * @mbg.generated
     */
    private String changeTitle;

    /**
     * Database Column Remarks:
     *   变更地址
     *
     *
     * @mbg.generated
     */
    private String changeUrl;



    /**
     * Database Column Remarks:
     *   变更情景码
     *
     *
     * @mbg.generated
     */
    private ChangeScenarioEnum changeScenarioCode;

    /**
     * Database Column Remarks:
     *   变更单状态
     *
     *
     * @mbg.generated
     */
    private ExeOrderStatusEnum status;

    /**
     * Database Column Remarks:
     *   变更执行单创建人
     *
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * Database Column Remarks:
     *   变更执行单执行人
     *
     *
     * @mbg.generated
     */
    private String executor;

}