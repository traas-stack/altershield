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
package com.alipay.altershield.shared.change.exe.node.enums;

/**
 * @author shuo.qius
 * @version Jun 8, 2018
 */
public enum ExeNodeStateEnum {

 //   node -> 提交前检查 ->  前置防御检查中 -> 前置完成 -明确通过/不通过 -> 变更执行中(回调才有) ->  提交后检查  -> 后置防御检查中 -> 后置完成 （明确通过/不通过）  -> 变更完成(flow中同步这个状态)
 //                                        前置检查超时 -> 通过        前置跳过                                                     后置检查超时 -> 通过 -> 后置跳过-终
 //                                        前置检查异常 -> 通过                                                                    后置检查异常 -> 通过
    /** 提交前置aop */
    PRE_AOP_SUBMIT("AOP_PRE_SUBMIT", "提交前置AOP"),
    /** 前置aop */
    PRE_AOP("AOP_PRE", "前置AOP执行中"),
    /** 前置AOP完成 */
    PRE_AOP_FINISH("AOP_PRE_FIN", "前置AOP完成成功"),

    /** 前置AOP超时完成 */
    PRE_AOP_TIMEOUT("AOP_PRE_TIMEOUT", "前置AOP超时完成"),

    /** 前置AOP异常失败 */
    PRE_AOP_FAIL("AOP_PRE_FAIL", "前置AOP异常失败"),


    /** 前置检查忽略，等待提交后置校验 */
    PRE_AOP_IGNORE("PRE_AOP_IGNORE", "前置检查忽略"),

    /** 变更执行中，等待提交后置校验,只有回调才会有这个状态 */
    EXECUTE_DOING("EXECUTE_DOING", "变更执行中"),

    /** 提交后置aop */
    POST_AOP_SUBMIT("POST_AOP_SUBMIT", "提交后置AOP"),

    /** 后置aop */
    POST_AOP("AOP_POST", "后置AOP执行中"),
    /** 后置AOP完成 */
    POST_AOP_FINISH("AOP_POST_FIN", "后置AOP完成成功"),
    /** 后置AOP超时完成 */
    POST_AOP_TIMEOUT("AOP_POST_TIMEOUT", "后置AOP超时完成"),

    /** 后置AOP异常失败，可以重新发起检查，也可以是结束 */
    POST_AOP_FAIL("AOP_POST_FAIL", "后置AOP异常失败"),

    /** 后置AOP跳过 */
    POST_AOP_IGNORE("AOP_POST_IGNORE", "终-后置AOP跳过"),

    /** 变更执行完成，多用于同步状态 */
    EXECUTE_DONE("EXECUTE_DONE", "终-变更执行完成");

    private final String status;
    private final String desc;

    ExeNodeStateEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * @return property value of {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return property value of {@link #desc}
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param status
     * @return
     */
    public static ExeNodeStateEnum getByStatus(String status) {
        for (ExeNodeStateEnum e : ExeNodeStateEnum
                .values()) {
            if (e.status.equalsIgnoreCase(status) || e.name().equalsIgnoreCase(status)) {
                return e;
            }
        }
        return null;
    }

    /**
     *
     * @return true if this state is final state.
     */
    public boolean isFinal() {
        switch (this) {
            case POST_AOP_IGNORE:
            case POST_AOP_FINISH:
            case POST_AOP_TIMEOUT:
            case POST_AOP_FAIL:
            case EXECUTE_DONE:
                return true;
            default:
                return false;
        }
    }
}
