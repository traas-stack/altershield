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
package com.alipay.altershield.shared.change.exe.order.entity;

import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.framework.core.change.model.AlterShieldChangeContent;
import com.alipay.altershield.framework.core.change.model.ChangeParentOrderInfo;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRef;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 变更执行单详细对象
 *
 * @author yuanji
 * @version : ExeChangeOrderEntity.java, v 0.1 2022年03月24日 3:09 下午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExeChangeOrderEntity extends ExeBaseChangeOrderEntity {

    /**
     * result的长度。数据库长度是20000，要留一点buffer
     */
    private static final int           RESULT_LEN_THRESHOLD = 20000 - 100;
    /**
     * Database Column Remarks: 变更参数
     *
     * @mbg.generated
     */

    private              KvRef<String> paramRef;

    /**
     * Database Column Remarks: 变更结果信息参数
     *
     * @mbg.generated
     */

    private KvRef<String> resultRef;

    /**
     * 上下文信息
     */
    private ExeOrderContext contextRef;

    /**
     * Database Column Remarks: 变更阶段
     *
     * @mbg.generated
     */
    private Set<ChangePhaseEnum> changePhases;

    /**
     * Database Column Remarks: 变更消息
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * Database Column Remarks: 可信租户
     *
     * @mbg.generated
     */
    private String tenantCode;

    /**
     * Database Column Remarks: 来源云id
     *
     * @mbg.generated
     */
    private String fromCloudId;

    /**
     * Database Column Remarks: 来源工单id
     *
     * @mbg.generated
     */
    private String referId;

    /**
     * Database Column Remarks: 来源租户
     *
     * @mbg.generated
     */
    private String fromTenantCode;

    /**
     * Database Column Remarks: 影响的应用
     *
     * @mbg.generated
     */

    private KvRef<Set<String>> changeAppsRef;

    /**
     * 变更内容
     */
    private KvRef<List<AlterShieldChangeContent>> changeContentRef;

    /**
     * 变更目标
     */
    private KvRef<List<ChangeTarget>> changeTargetRef;

    /**
     * 父工单变更对象
     */
    private KvRef<ChangeParentOrderInfo> parentOrderInfoRef;

    /**
     * Database Column Remarks: 变更计划时间
     *
     * @mbg.generated
     */
    private Date planStartTime;

    /**
     * Database Column Remarks: 变更实际执行时间
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date finishTime;

    /**
     * 调度分组
     */
    private String dispatchGroup;

    /**
     * Instantiates a new Exe change order entity.
     */
    public ExeChangeOrderEntity() {
    }

    /**
     * Instantiates a new Exe change order entity.
     *
     * @param keyValueStorage the key value storage
     */
    public ExeChangeOrderEntity(KeyValueStorage keyValueStorage) {

        this.changeContentRef = new KvRef<>(keyValueStorage, KvRefCodec.CHANGE_CONTENTS, null);
        this.changeTargetRef = new KvRef<>(keyValueStorage, KvRefCodec.CHANGE_TARGETS, null);
        this.changeAppsRef = new KvRef<>(keyValueStorage, KvRefCodec.STR_SET, null);
        this.paramRef = new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null);
        this.parentOrderInfoRef = new KvRef<>(keyValueStorage, KvRefCodec.PARENT_ORDER_INFO, null);
        this.resultRef = new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null, RESULT_LEN_THRESHOLD);

    }

}