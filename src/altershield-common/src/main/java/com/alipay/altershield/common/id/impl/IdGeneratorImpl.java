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
package com.alipay.altershield.common.id.impl;

import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.id.sequence.SequenceGenerator;
import com.alipay.altershield.common.util.IdUtil;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shuo.qius
 * @version May 25, 2018
 */
@Component
public class IdGeneratorImpl implements IdGenerator, InitializingBean {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    private static final String CHANGE_MODEL_CODE_PREFIX = "CM0";

    /**
     * @see InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        if (sequenceGenerator == null) {
            throw new IllegalArgumentException("property 'sequenceGenerator' is null");
        }
    }

    /**
     *
     */
    @Override
    public String generateIdByRelatedId(IdBizCodeEnum bizCode, String relatedId) {
        return generateIdByUid(bizCode, IdUtil.getUid(relatedId), IdUtil.getRandomId(relatedId),
                IdUtil.getChangeScenarioEnum(relatedId));
    }

    @Override
    public String generateIdByRelatedIdButNewUid(IdBizCodeEnum bizCode, String relatedId, String uid) {
        return generateIdByUid(bizCode, uid, IdUtil.getRandomId(relatedId),
                IdUtil.getChangeScenarioEnum(relatedId));
    }

    /**
     *
     * @param bizCode
     * @param uid
     * @param changeScenarioEnum
     * @return
     */
    @Override
    public String generateIdByUid(IdBizCodeEnum bizCode, String uid,
                                  ChangeScenarioEnum changeScenarioEnum) {
        return generateIdByUid(bizCode, uid, IdUtil.getRandomSharding(),  changeScenarioEnum);
    }


    private String generateIdByUid(IdBizCodeEnum bizCode, String uid, String random2digit,
                                   ChangeScenarioEnum changeScenarioEnum) {
        final String date = IdUtil.getDate();
        final String dataSystemVersion = IdUtil.getDataSystemVersion();
        final String sysCode = IdUtil.SYS_CODE;
        final String bizCd = bizCode.getCode();
        final String extCode = IdUtil.getExtString(changeScenarioEnum);
        final String sharding = uid;
        final String random = getRandomDigit(random2digit);
        final String seq = sequenceGenerator.getNextSeq(bizCode.getSeqName());
        return new StringBuilder(IdUtil.ID_LEN).append(date).append(dataSystemVersion).append(sysCode).append(bizCd)
                .append(extCode).append(sharding).append(random).append(seq).toString();
    }

    /**
     *
     */
    @Override
    public String generateIdWithNoSharding(IdBizCodeEnum bizCode) {
        final String date = IdUtil.getDate();//8
        final String dataSystemVersion = IdUtil.getDataSystemVersion();//2
        final String sysCode = IdUtil.SYS_CODE;//3
        final String bizCd = bizCode.getCode();//3
        final String extCode = IdUtil.getDefaultExtString();//8
        final String sharding = IdUtil.getNoSharding();//2
        final String random = getRandomDigit(IdUtil.getRandomSharding());
        final String seq = sequenceGenerator.getNextSeq(bizCode.getSeqName());
        return new StringBuilder(IdUtil.ID_LEN).append(date).append(dataSystemVersion).append(sysCode).append(bizCd)
                .append(extCode).append(sharding).append(random).append(seq).toString();
    }

    @Override
    public String generateChangeModelCode(IdBizCodeEnum bizCode) {
        return new StringBuilder().append(CHANGE_MODEL_CODE_PREFIX).append(sequenceGenerator.getNextSeq(bizCode.getSeqName())).toString();
    }

    private String getRandomDigit(String random2digit) {

        return random2digit;
    }

    /**
     * @param sequenceGenerator value to be assigned to property {@link #sequenceGenerator}
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

}
