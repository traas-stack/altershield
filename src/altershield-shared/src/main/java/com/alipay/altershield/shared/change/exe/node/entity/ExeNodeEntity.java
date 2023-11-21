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
package com.alipay.altershield.shared.change.exe.node.entity;


import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.shared.common.largefield.ref.KvRef;
import com.alipay.altershield.framework.core.change.model.AlterShieldChangeContent;
import com.alipay.altershield.framework.core.change.model.ChangeTarget;
import com.alipay.altershield.framework.core.change.model.enums.ChangePhaseEnum;
import com.alipay.altershield.shared.change.exe.entity.AbstractExeTraceEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import com.alipay.altershield.shared.common.largefield.ref.KvRefCodec;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public abstract class ExeNodeEntity extends AbstractExeTraceEntity {

    /**
     * change target的长度。数据库长度是20000，要留一点buffer
     */
    private static final int      CHANGE_TARGET_REAL_CONTENT_LEN_THRESHOLD = 20000-100;


    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private String nodeExeId;

    /**
     *
     *
     * @mbg.generated
     */
    private String uid;



    /**
     * Database Column Remarks:
     *   变更单id
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
     *    状态
     *
     *
     * @mbg.generated
     */
    private ExeNodeStateEnum status;

    /**
     * Database Column Remarks:
     *   变更场景key标识
     *
     *
     * @mbg.generated
     */
    private String changeSceneKey;

    /**
     * Database Column Remarks:
     *   变更key标识
     *
     *
     * @mbg.generated
     */
    private String changeKey;

    /**
     * Database Column Remarks:
     *   变更类型
     *
     *
     * @mbg.generated
     */
    private MetaChangeStepTypeEnum changeStepType;

    /**
     * Database Column Remarks:
     *   变更环境
     *
     *
     * @mbg.generated
     */
    private ChangePhaseEnum changePhase;

    /**
     * Database Column Remarks:
     *   入参ref
     *
     *
     * @mbg.generated
     */
    private KvRef<String> paramRef;

    /**
     * Database Column Remarks:
     *   返回结果ref
     *
     *
     * @mbg.generated
     */
    private KvRef<String> rstRef;


    /**
     * Database Column Remarks:
     *   节点执行备注
     *
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * Database Column Remarks:
     *   操作人
     *
     *
     * @mbg.generated
     */
    private String executor;


    /**
     * Database Column Remarks:
     *   业务扩展字段
     *
     *
     * @mbg.generated
     */
    private String bizExt;

    /**
     * Database Column Remarks:
     *   搜索扩展字段
     *
     *
     * @mbg.generated
     */
    private KvRef<Map<String, Object>>  searchExtRef;

    /**
     * Database Column Remarks:
     *   变更前置和后置的AOP的checkId
     *
     *
     * @mbg.generated
     */
    private ExeNodeCheckInfo exeNodeCheckInfo;

    /**
     * Database Column Remarks:
     *   变更开始时间
     *
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * Database Column Remarks:
     *   变更结束时间
     *
     *
     * @mbg.generated
     */
    private Date finishTime;

    /**
     * Database Column Remarks:
     *   YN表示是否应急变更
     *
     *
     * @mbg.generated
     */
    private boolean emergency;

    /**
     * Database Column Remarks:
     *   上下文扩展字段
     *
     *
     * @mbg.generated
     */
    private ExeNodeContext contextRef;

    /**
     * Database Column Remarks:
     *   TLDC架构租户信息
     *
     *
     * @mbg.generated
     */
    private String tldcTenantCode;

    /**
     * Database Column Remarks:
     *   来源云id
     *
     *
     * @mbg.generated
     */
    private String fromCloudId;

    /**
     * Database Column Remarks:
     *   来源id
     *
     *
     * @mbg.generated
     */
    private String referId;

    /**
     * Database Column Remarks:
     *   来源TLDC架构租户信息
     *
     *
     * @mbg.generated
     */
    private String fromTldcTenantCode;

    /**
     * Database Column Remarks:
     *   变更内容
     *
     *
     * @mbg.generated
     */
    private KvRef<List<AlterShieldChangeContent>>  changeContentRef;



    /**
     * Database Column Remarks:
     *   变更目标
     *
     *
     * @mbg.generated
     */
    private KvRef<List<ChangeTarget>> changeTargetRef;

    /**
     * 调度分组
     */
    private String dispatchGroup;

    /**
     * 给spi打标调用成功
     * @param tag
     */
    public void tagSpiSuccess(boolean tag)
    {
        if(getContextRef() == null)
        {
            setContextRef(new ExeNodeContext());
        }
        getContextRef().setSpiSucc(tag);
    }


    /**
     * Database Column Remarks:
     *   记录更新的触发源id
     *
     *
     * @mbg.generated
     */
    private String lastTriggerId;

    public ExeNodeEntity() {
    }

    public ExeNodeEntity(KeyValueStorage keyValueStorage) {
        this.changeContentRef = new KvRef<>(keyValueStorage, KvRefCodec.CHANGE_CONTENTS, null);
        this.changeTargetRef = new KvRef<>(keyValueStorage, KvRefCodec.CHANGE_TARGETS, null, CHANGE_TARGET_REAL_CONTENT_LEN_THRESHOLD);
        this.paramRef = new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null);
        this.rstRef = new KvRef<>(keyValueStorage, KvRefCodec.NOOP, null);
        this.searchExtRef = new KvRef<>(keyValueStorage, KvRefCodec.OBJ_MAP, null);
    }
}