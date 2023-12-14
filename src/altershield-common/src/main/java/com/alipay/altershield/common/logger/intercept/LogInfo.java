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
package com.alipay.altershield.common.logger.intercept;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.common.intercept.SensitiveContentHandler;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 统一日志信息对象
 *
 * @author lijunjie
 * @version $Id: LogInfo.java, v 0.1 2014年11月7日 下午3:45:09 lijunjie Exp $
 */
public class LogInfo implements Serializable {

    /** 序列化ID */
    private static final long serialVersionUID = -2894307721250973952L;

    /** 租户编号 */
    private String            tntInstId;

    /** 类名 */
    private String            className;

    /** 方法名 */
    private String            methodName;

    /** 进入方法时间 */
    private long              startTime;

    /** 结束方法时间 */
    private long              endTime;

    /** 是否抛出异常 */
    private boolean           exceptional;

    /** 调用参数 */
    private Object[]          args;

    /** 设置个性化的返回结果 */
    private Object            result;

    /** 服务描述 */
    private String            serviceDesc;

    /** 业务关键字段的字段名，用于monitor上的业务监控或者汇总 */
    private Object            keyProps;

    /** 业务扩展属性值，用于快速定位问题，不在monitor日志模型中 */
    private Object            extProps;

    /**
     * 以服务摘要日志的格式打印
     *
     * @return          服务名
     */
    public String printDigestLog() {

        //获取业务成功标识和错误码
        String successFlag = LogConstants.SUCCESS;

        //如果抛出了异常，则返回false
        if (this.exceptional) {
            successFlag = LogConstants.EXCEPTION;
        }else if(result == null){
            successFlag = LogConstants.SUCCESS;
        }else if (result instanceof AlterShieldResult) {
            AlterShieldResult budgetResult = (AlterShieldResult) result;
            successFlag = budgetResult.isSuccess() ? LogConstants.SUCCESS : LogConstants.FAIL;
        }else {
            successFlag = LogConstants.SUCCESS;
        }

        //打印标准摘要日志:[tntInstId](服务描述)(接口名.方法名,Y,耗时ms)(关键参数信息)(扩展参数)
        StringBuilder sb = new StringBuilder();

        // 固定信息[tntInstId]
        sb.append(LogConstants.C_PREFIX);
        sb.append(StringUtils.defaultIfBlank(tntInstId, LogConstants.NONE));
        sb.append(LogConstants.C_SUFFIX);

        // [
        sb.append(LogConstants.C_PREFIX);
        // (
        sb.append(LogConstants.S_PREFIX);
        sb.append(StringUtils.defaultIfBlank(serviceDesc, LogConstants.NONE));
        // )
        sb.append(LogConstants.S_SUFFIX);
        // (
        sb.append(LogConstants.S_PREFIX);
        sb.append(StringUtils.defaultIfBlank(className, LogConstants.NONE));
        sb.append(LogConstants.DOT);
        sb.append(StringUtils.defaultIfBlank(methodName, LogConstants.NONE));
        sb.append(LogConstants.COMMA);
        sb.append(successFlag);
        sb.append(LogConstants.COMMA);
        sb.append(getElapseTime());
        sb.append(LogConstants.MSEL);
        sb.append(LogConstants.S_SUFFIX);

        //打印关键参数信息，便于monitor统计业务信息
        sb.append(LogConstants.S_PREFIX);
        sb.append(keyProps == null ? LogConstants.NONE : keyProps);
        sb.append(LogConstants.S_SUFFIX);

        //打印扩展参数，便于线上监控
        sb.append(LogConstants.S_PREFIX);
        sb.append(extProps == null ? LogConstants.NONE : extProps);
        sb.append(LogConstants.S_SUFFIX);

        // ]
        sb.append(LogConstants.C_SUFFIX);

        return sb.toString();
    }

    /**
     * 获取方法消耗时间
     *
     * @return 方法消耗时间
     */
    public long getElapseTime() {
        return endTime - startTime;
    }

    /**
     * 格式化输入参数
     *
     * @return 文本[tntInstId][服务名称](接口名.方法名)[入参][出参]
     */
    public String getParams(SensitiveContentHandler handler) {
        StringBuilder sb = new StringBuilder();

        if (null == args) {
            return sb.toString();
        }

        // 固定信息[tntInstId]
        sb.append(LogConstants.C_PREFIX);
        sb.append(StringUtils.defaultIfBlank(tntInstId, LogConstants.NONE));
        sb.append(LogConstants.C_SUFFIX);

        // [
        sb.append(LogConstants.C_PREFIX);
        sb.append(StringUtils.defaultString(serviceDesc, LogConstants.NONE));
        sb.append(LogConstants.C_SUFFIX);
        // ]
        sb.append("invoke");
        sb.append(LogConstants.S_PREFIX);
        sb.append(StringUtils.defaultString(className, LogConstants.NONE));
        sb.append(LogConstants.DOT);
        sb.append(StringUtils.defaultString(methodName, LogConstants.NONE));
        sb.append(LogConstants.S_SUFFIX);

        sb.append(LogConstants.C_PREFIX);
        for (Object obj : args) {
            if (null == obj) {
                continue;
            }
            String s = JSON.toJSONString(obj);
            if (s.length() > AlterShieldConstant.LOG_PARAM_MAX_SIZE) {
                if (s.startsWith("{")) {
                    final JSONObject jsonObject = JSON.parseObject(s);
                    jsonObject.remove("paramJsn");
                    jsonObject.remove("serviceResult");
                    s = jsonObject.toJSONString();
                }
                if (s.length() > AlterShieldConstant.LOG_PARAM_MAX_SIZE) {
                    s = s.substring(0, AlterShieldConstant.LOG_PARAM_MAX_SIZE);
                }
            }
            //sb.append(DesenUtil.scanAndDesensText(s));
            sb.append(handler.logHandle(s));
            sb.append(LogConstants.COMMA);
        }
        sb.append(LogConstants.C_SUFFIX);

        sb.append(LogConstants.C_PREFIX);
        sb.append(result == null ? LogConstants.NONE : JSON.toJSONString(result));
        sb.append(LogConstants.C_SUFFIX);

        return sb.toString();
    }

    /**
     * Setter method for property <tt>className</tt>.
     *
     * @param className value to be assigned to property className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Setter method for property <tt>methodName</tt>.
     *
     * @param methodName value to be assigned to property methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Setter method for property <tt>startTime</tt>.
     *
     * @param startTime value to be assigned to property startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Setter method for property <tt>exceptional</tt>.
     *
     * @param exceptional value to be assigned to property exceptional
     */
    public void setExceptional(boolean exceptional) {
        this.exceptional = exceptional;
    }

    /**
     * Setter method for property <tt>args</tt>.
     *
     * @param args value to be assigned to property args
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * Setter method for property <tt>serviceDesc</tt>.
     *
     * @param serviceDesc value to be assigned to property serviceDesc
     */
    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    /**
     * Setter method for property <tt>keyProps</tt>.
     *
     * @param keyProps value to be assigned to property keyProps
     */
    public void setKeyProps(Object keyProps) {
        this.keyProps = keyProps;
    }

    /**
     * Setter method for property <tt>extProps</tt>.
     *
     * @param extProps value to be assigned to property extProps
     */
    public void setExtProps(Object extProps) {
        this.extProps = extProps;
    }

    /**
     * Setter method for property <tt>tntInstId</tt>.
     *
     * @param tntInstId value to be assigned to property tntInstId
     */
    public void setTntInstId(String tntInstId) {
        this.tntInstId = tntInstId;
    }

    /**
     * Getter method for property <tt>keyProps</tt>.
     *
     * @return property value of keyProps
     */
    public Object getKeyProps() {
        return keyProps;
    }

    /**
     * Getter method for property <tt>extProps</tt>.
     *
     * @return property value of extProps
     */
    public Object getExtProps() {
        return extProps;
    }

}
