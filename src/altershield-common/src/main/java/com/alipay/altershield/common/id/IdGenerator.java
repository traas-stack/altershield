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
package com.alipay.altershield.common.id;

import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;

/**
 * @author shuo.qius
 * @version May 25, 2018
 */
public interface IdGenerator {

    /**
     * 根据业务码和相关的id来产生唯一id
     * @param bizCode
     * @param relatedId
     * @return
     */
    String generateIdByRelatedId(IdBizCodeEnum bizCode, String relatedId);

    /**
     * @param bizCode
     * @param relatedId
     * @param uid
     * @return
     */
    String generateIdByRelatedIdButNewUid(IdBizCodeEnum bizCode, String relatedId, String uid);

    /**
     * @param bizCode
     * @param uid
     * @param changeScenarioEnum
     * @return
     */
    String generateIdByUid(IdBizCodeEnum bizCode, String uid, ChangeScenarioEnum changeScenarioEnum);


    /**
     * @param bizCode never null
     * @return
     */
    String generateIdWithNoSharding(IdBizCodeEnum bizCode);

    /**
     * 生成老变更场景ID
     */
    String generateChangeModelCode(IdBizCodeEnum bizCode);


}
