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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.common.service;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldPageResult;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResultCodeEnum;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author yuanji
 * @version : ServicePageProcessTemplate.java, v 0.1 2022年06月22日 18:14 yuanji Exp $
 */
public class ServicePageProcessTemplate {

    private static Logger logger = Loggers.SERVICE_QUERY_DIGEST;

    /**
     * @param processor
     * @return same object as passed
     */
    public static  <T> AlterShieldPageResult<List<T>> process(int current, int pageSize, ServicePageQuery<T> processor) {
        AlterShieldPageResult<List<T>> result = new AlterShieldPageResult<List<T>>();
        result.setSuccess(true);
        result.setResultCode(AlterShieldResultCodeEnum.SUCCESS.getCode());
        try {

            long total = processor.total();
            result.setCurrent(current);
            result.setPageSize(pageSize);

            if (total == 0) {
                result.setTotal(total);
                result.setDomain(Collections.emptyList());
                return result;
            }

            //paginator
            Paginator paginator = new Paginator();
            paginator.setItems((int) total);
            paginator.setItemsPerPage(pageSize);
            paginator.setPage(current);

            List<T> resultObj = processor.query(paginator.getOffset(), paginator.getLength());
            result.setDomain(resultObj);
            result.setTotal(total);

        } catch (AlterShieldInternalException e) {
            result.setSuccess(false);
            result.setMsg(e.getErrorCode().getCode() + ":" + e.getMessage());
            switch (e.getErrorCode()) {
                case COMMON_ILLEGAL_ARGUMENT:
                    result.setResultCode(AlterShieldResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
                    break;
                case PERMISSION_DENIED:
                    result.setResultCode(AlterShieldResultCodeEnum.PERMISSION_DENIED.getCode());
                    break;
                default:
                    result.setResultCode(AlterShieldResultCodeEnum.SYSTEM_ERROR.getCode());
                    break;
            }
            AlterShieldLoggerManager.log("error", logger, e, result);
        } catch (Throwable t) {
            result.setSuccess(false);
            result.setResultCode(AlterShieldInternalErrorCode.SYSTEM_ERROR.getCode());
            result.setMsg(t.getMessage());
            AlterShieldLoggerManager.log("error", logger, t, result);
        }
        return result;
    }
}