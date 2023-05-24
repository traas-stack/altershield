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
package com.alipay.altershield.framework.sdk.constant;

/**
 * The type Ops cloud api constant.
 *
 * @author yuanji
 * @version : OpsCloudServerContant.java, v 0.1 2022年02月22日 4:06 下午 yuanji Exp $
 */
public abstract class AlterShieldApiConstant {

    /**
     * The constant apiPrefix.
     */
    public static final String apiPrefix = "/openapi/v1/";
    /**
     * The constant exePrefix.
     */
    public static final String exePrefix = apiPrefix + "exe/";
    /**
     * The constant riskPrefix.
     */
    public static final String riskPrefix = apiPrefix + "risk/";
    /**
     * The constant metaPrefix.
     */
    public static final String metaPrefix = apiPrefix + "meta/";

    /**
     * The constant submitAndStartChangeExcOrderAction.
     */
    public static final String submitAndStartChangeExcOrderAction = "submitAndStartChangeExecOrder";
    /**
     * The constant submitAndStartChangeExcOrderUri.
     */
    public static final String submitAndStartChangeExcOrderUri =
            exePrefix + submitAndStartChangeExcOrderAction;

    /**
     * The constant submitChangeExcOrderAction.
     */
    public static final String submitChangeExcOrderAction = "submitChangeExecOrder";
    /**
     * The constant submitChangeExcOrderUri.
     */
    public static final String submitChangeExcOrderUri = exePrefix + submitChangeExcOrderAction;

    /**
     * The constant submitChangeExecOrderStartNotifyAction.
     */
    public static final String submitChangeExecOrderStartNotifyAction =
            "submitChangeExecOrderStartNotify";
    /**
     * The constant submitChangeExecOrderStartStartNotifyUri.
     */
    public static final String submitChangeExecOrderStartStartNotifyUri =
            exePrefix + submitChangeExecOrderStartNotifyAction;

    /**
     * The constant submitChangeExecBatchStartNotifyAction.
     */
    public static final String submitChangeExecBatchStartNotifyAction =
            "submitChangeExecBatchStartNotify";
    /**
     * The constant submitChangeExecBatchStartStartNotifyUri.
     */
    public static final String submitChangeExecBatchStartStartNotifyUri =
            exePrefix + submitChangeExecBatchStartNotifyAction;

    /**
     * The constant submitChangeActionStartStartNotifyAction.
     */
    public static final String submitChangeActionStartStartNotifyAction =
            "submitChangeActionStartStartNotify";
    /**
     * The constant submitChangeActionStartStartNotifyUri.
     */
    public static final String submitChangeActionStartStartNotifyUri =
            exePrefix + submitChangeActionStartStartNotifyAction;

    /**
     * The constant retrieveChangeCheckResultAction.
     */
    public static final String retrieveChangeCheckResultAction = "retrieveChangeCheck";
    /**
     * The constant retrieveChangeCheckResultUri.
     */
    public static final String retrieveChangeCheckResultUri =
            exePrefix + retrieveChangeCheckResultAction;

    /**
     * The constant submitChangeFinishNotifyAction.
     */
    public static final String submitChangeFinishNotifyAction = "submitChangeFinishNotify";
    /**
     * The constant submitChangeFinishNotifyUri.
     */
    public static final String submitChangeFinishNotifyUri =
            exePrefix + submitChangeFinishNotifyAction;

    /**
     * The constant resubmitChangeStartNotifyAction.
     */
    public static final String resubmitChangeStartNotifyAction = "resubmitChangeStartNotifyAction";
    /**
     * The constant resubmitChangeStartNotifyUri.
     */
    public static final String resubmitChangeStartNotifyUri =
            exePrefix + resubmitChangeStartNotifyAction;

    /**
     * The constant finishChangeExecOrderAction.
     */
    public static final String finishChangeExecOrderAction = "finishChangeExecOrderAction";
    /**
     * The constant finishChangeExecOrderUri.
     */
    public static final String finishChangeExecOrderUri = exePrefix + finishChangeExecOrderAction;

    /**
     * The constant skipChangeCheckAction.
     */
    public static final String skipChangeCheckAction = "skipChangeCheckAction";
    /**
     * The constant skipChangeCheckActionUri.
     */
    public static final String skipChangeCheckActionUri = exePrefix + skipChangeCheckAction;

    /**
     * The constant submitChangeEventAction.
     */
    public static final String submitChangeEventAction = "submitChangeEventAction";
    /**
     * The constant submitChangeEventUri.
     */
    public static final String submitChangeEventUri = exePrefix + submitChangeEventAction;

    /**
     * The constant heartbeatConfigAction.
     */
    public static final String heartbeatConfigAction = "heartbeat/config";
    /**
     * The constant heartbeatConfigUri.
     */
    public static final String heartbeatConfigUri = apiPrefix + heartbeatConfigAction;

    /**
     * The constant queryRiskDefenseCheckRuleAction.
     */
    public static final String queryRiskDefenseCheckRuleAction = "queryRiskDefenseCheckRuleAction";

    /**
     * defenseCallbackAction
     */
    public static final String defenseCallbackAction = "defenseCallbackAction";
    /**
     * The constant queryRiskDefenseCheckRuleUri.
     */
    public static final String queryRiskDefenseCheckRuleUri =
            riskPrefix + queryRiskDefenseCheckRuleAction;

    /**
     * The constant createChangeSceneAction.
     */
    public static final String createChangeSceneAction = "createChangeSceneAction";
    /**
     * The constant createChangeSceneActionUri.
     */
    public static final String createChangeSceneActionUri = metaPrefix + createChangeSceneAction;
}
