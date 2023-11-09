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
package com.alipay.altershield.shared.change.exe.node.entity;


import com.alipay.altershield.common.largefield.kv.KeyValueStorage;
import com.alipay.altershield.common.largefield.ref.KvRef;
import com.alipay.altershield.shared.change.exe.node.ref.ExeChangeRefCodec;
import com.alipay.altershield.shared.change.meta.model.enums.MetaChangeStepTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分批执行node实体
 * @author yuanji
 * @version : ExeBatchNodeEntity.java, v 0.1 2022年03月24日 8:55 下午 yuanji Exp $
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExeBatchNodeEntity extends ExeNodeEntity {

    /**
     * 分批灰度信息
     */
    private KvRef<ExeChangeBatchEffectiveInfoEntity> changeEffectiveInfoRef;

    public ExeBatchNodeEntity() {
        setChangeStepType(MetaChangeStepTypeEnum.STEP_GRAY_BATCH);
    }

    public ExeBatchNodeEntity(KeyValueStorage keyValueStorage) {
        super(keyValueStorage);
        setChangeStepType(MetaChangeStepTypeEnum.STEP_GRAY_BATCH);
        changeEffectiveInfoRef = new KvRef<>(keyValueStorage, ExeChangeRefCodec.BATCH_CHANGE_EFFECTIVE_INFO, null);
    }
}