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
package com.alipay.altershield.change.exe.repository.impl;

import com.alipay.altershield.change.exe.dal.dataobject.ExeChangeOrderDO;
import com.alipay.altershield.change.exe.dal.mapper.ExeChangeOrderMapper;
import com.alipay.altershield.change.exe.repository.ExeChangeOrderRepository;
import com.alipay.altershield.change.exe.repository.converter.ExeChangeOrderEntityConverter;
import com.alipay.altershield.common.service.ServiceProcessTemplate;
import com.alipay.altershield.shared.change.exe.order.entity.ExeChangeOrderEntity;
import com.alipay.altershield.shared.change.exe.order.enums.ExeOrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import static com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum.Duplicate_BIZ_ORDERID;


/**
 * @author yuanji
 * @version : ExeChangeOrderRepositoryImpl.java, v 0.1 2022年03月29日 2:40 下午 yuanji Exp $
 */
@Repository
public class ExeChangeOrderRepositoryImpl implements ExeChangeOrderRepository {

    @Autowired
    private ExeChangeOrderMapper exeChangeOrderMapper;

    @Autowired
    private ExeChangeOrderEntityConverter exeChangeOrderEntityConverter;


    @Override
    public boolean insert(ExeChangeOrderEntity entity) {

        Assert.notNull(entity,"entity is null");
        ExeChangeOrderDO exeChangeOrderDO = exeChangeOrderEntityConverter.convertToDataObject(entity);
        try
        {
            return exeChangeOrderMapper.insert(exeChangeOrderDO) == 1;
        }
        catch (Exception e)
        {
            if(e instanceof DuplicateKeyException)
            {
                throw new ServiceProcessTemplate.FacadeProcessTemplatePlainException(Duplicate_BIZ_ORDERID.getCode(), e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public boolean update(ExeChangeOrderEntity entity) {
        Assert.notNull(entity,"entity is null");
        ExeChangeOrderDO exeChangeOrderDO = exeChangeOrderEntityConverter.convertToDataObject(entity);
        return exeChangeOrderMapper.updateByPrimaryKey(exeChangeOrderDO) == 1;
    }

    @Override
    public ExeChangeOrderEntity getChangeOrderById(String orderId) {
        Assert.notNull(orderId,"orderId is null");
        ExeChangeOrderDO exeChangeOrderDO =  exeChangeOrderMapper.selectByPrimaryKey(orderId);
        return exeChangeOrderEntityConverter.convertToEntity(exeChangeOrderDO);
    }

    @Override
    public ExeChangeOrderEntity getChangeOrder(String platform, String bizExecOrderId) {
        Assert.notNull(platform,"platform is null");
        Assert.notNull(bizExecOrderId,"bizExecOrderId is null");
        ExeChangeOrderDO exeChangeOrderDO =  exeChangeOrderMapper.selectByPlatformAndBizOrderId(platform, bizExecOrderId);
        return exeChangeOrderEntityConverter.convertToEntity(exeChangeOrderDO);    }



    @Override
    public void updateOrderInfo(String orderId, ExeOrderStatusEnum status, String msg) {

        ExeChangeOrderDO exeChangeOrderDO = new ExeChangeOrderDO();
        exeChangeOrderDO.setOrderId(orderId);
        if(status != null)
        {
            exeChangeOrderDO.setStatus(status.name());
        }
        exeChangeOrderDO.setMsg(msg);
        exeChangeOrderMapper.updateByPrimaryKey(exeChangeOrderDO);

    }

    @Override
    public String getBizExecOrderIdByOrderId(String orderId) {
        return exeChangeOrderMapper.getBizExecOrderIdByOrderId(orderId);
    }

    @Override
    public String getOrderIdByBizExecOrderId(String platform, String bizExecOrderId) {
        return exeChangeOrderMapper.getOrderIdByPlatformAndBizOrderId(platform, bizExecOrderId);
    }
}