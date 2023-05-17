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
package com.alipay.altershield.framework.common.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum AlterShieldInternalErrorCode implements Serializable {

    /**
     * Success ops cloud internal error code.
     */
    SUCCESS("000", ErrorLevels.INFO, "success"),
    /**
     * The Common illegal argument.
     */
    COMMON_ILLEGAL_ARGUMENT("101", ErrorLevels.ERROR, "illegal argument"),
    /**
     * The Permission denied.
     */
    PERMISSION_DENIED("102", ErrorLevels.ERROR, "permission denied"),
    /**
     * The Not supported.
     */
    NOT_SUPPORTED("103", ErrorLevels.ERROR, "function not support yet"),

    /** 200 - 299 resource */
    /// ** 没有加锁就释放锁 */
    // NOT_LOCK_BEFORE_RELEASE("200", ErrorLevels.ERROR,"not lock before release"),

    /** 300 - 399 meta manager */
    /**
     * 没有开启发布
     */
    NOT_START_RELEASE_ERROR("300", ErrorLevels.ERROR, "not start release error"),
    /**
     * 非编辑状态不能编辑
     */
    NOT_EDIT_STATUS_WHEN_EDIT_ERROR("301", ErrorLevels.ERROR, "not edit status when edit error"),
    /**
     * 平台版本内部数据错乱
     */
    PLATFORM_VERSION_INTERNAL_DATA_DISORDER_ERROR("302", ErrorLevels.ERROR,
            "platform version internal data disorder error"),
    /**
     * 没有copy不能编辑
     */
    NOT_COPY_WHEN_EDIT_ERROR("303", ErrorLevels.ERROR, "not copy when edit error"),
    /**
     * 不能copy edit状态的元素
     */
    CAN_NOT_COPY_EDIT_STATUS_ELEMENT_ERROR("304", ErrorLevels.ERROR,
            "can not copy edit status element error"),

    /** 400 - 499 execute */
    /**
     * The change service closed.
     */
    CHANGE_SERVICE_CLOSED("401", ErrorLevels.ERROR, "change service reference closed"),
    /**
     * The Chng srv not support.
     */
    CHANGE_SERVICE_NOT_SUPPORT("402", ErrorLevels.ERROR, "change service dose not support this operate"),
    /**
     * The Chng srv temp fail.
     */
    CHANGE_SERVICE_TEMP_FAIL("403", ErrorLevels.ERROR,
            "change service's rpc operate not available at this moment, just retry"),
    /**
     * 请求的租户没有权限
     */
    TENANT_NOT_AUTH("403", ErrorLevels.ERROR, "request's tenant does not have permission"),
    /**
     * The Spi callback wrong status.
     */
    SPI_CALLBACK_WRONG_STATUS("404", ErrorLevels.ERROR,
            "change service's status not valid for callback"),
    /**
     * The Schd pnt too early.
     */
    SCHEDULE_POINT_TOO_EARLY("405", ErrorLevels.ERROR, "schedule point handled too early"),

    /**
     * (会导致schdPoint hold)
     */
    EXECUTE_WRONG_STATE("420", ErrorLevels.ERROR, "invalid execution status in state machine"),
    /**
     * (会导致schdPoint hold)
     */
    EXECUTE_SERVICE_NOT_FOUND("421", ErrorLevels.ERROR, "found no service"),
    /**
     * The Unknown tier type.
     */
    UNKNOWN_TIER_TYPE("422", ErrorLevels.ERROR, "unknown tier type"),
    /**
     * The Unknown manual cmd.
     */
    UNKNOWN_MANUAL_CMD("423", ErrorLevels.ERROR, "unknown manual order cmd"),
    /**
     * The Unlock failed.
     */
    UNLOCK_FAILED("424", ErrorLevels.ERROR, "unlock failed"),
    /**
     * The Mutex check failed.
     */
    MUTEX_CHECK_FAILED("425", ErrorLevels.ERROR, "mutex check failed"),
    /**
     * The Change target null.
     */
    CHANGE_TARGET_NULL("426", ErrorLevels.ERROR, "change service with no change target"),
    /**
     * The Retry tier get wrong state.
     */
    RETRY_TIER_GET_WRONG_STATE("427", ErrorLevels.ERROR, "retry tier get invalid state"),
    /**
     * The Exe event send error.
     */
    EXE_EVENT_SEND_ERROR("430", ErrorLevels.ERROR, "exe event error"),
    /**
     * The Common invalid meta.
     */
    COMMON_INVALID_META("450", ErrorLevels.ERROR, "invalid meta"),
    /**
     * The Found no node meta.
     */
    FOUND_NO_NODE_META("451", ErrorLevels.ERROR, "found no metaNode"),
    /**
     * The Invalid gray tier conf.
     */
    INVALID_GRAY_TIER_CONF("455", ErrorLevels.ERROR, "unknown gray conf"),
    /**
     * The Found no exe node.
     */
    FOUND_NO_EXE_NODE("456", ErrorLevels.ERROR, "found no exeNode"),

    /** 500 - 550 plugin */
    /**
     * (会导致schdPoint hold)
     */
    PLUGIN_NOT_FOUND("500", ErrorLevels.ERROR, "plugin not found"),
    /**
     * The Plugin uninstalled.
     */
    PLUGIN_UNINSTALLED("501", ErrorLevels.ERROR, "plugin uninstalled"),
    /**
     * The Plugin wrong expect type.
     */
    PLUGIN_WRONG_EXPECT_TYPE("502", ErrorLevels.ERROR, "wrong plugin type"),
    /**
     * The Plugin wrong param rst dto clz name.
     */
    PLUGIN_WRONG_PARAM_RST_DTO_CLZ_NAME("503", ErrorLevels.ERROR, "wrong class name"),
    /**
     * The plugin execute failed.
     */
    PLUGIN_EXECUTE_FAILED("504", ErrorLevels.ERROR, "plugin execute failed"),
    /**
     * The Plugin install error.
     */
    PLUGIN_INSTALL_ERROR("510", ErrorLevels.ERROR, "plugin cannot install"),
    /**
     * The Plugin param 2 json schema class not found error.
     */
    PLUGIN_PARAM_2_JSON_SCHEMA_CLASS_NOT_FOUND_ERROR("520", ErrorLevels.ERROR,
            "plugin param -> json schema class not found error"),

    /**
     * (会导致schdPoint hold)
     */
    RPC_ITEM_NOT_FOUND("520", ErrorLevels.ERROR, "rpc item not found"),

    /**
     * 600 - 650 业务异常
     */
    SYNC_ROLLBACK_ERROR("600", ErrorLevels.ERROR, "sync rollback error"),
    /**
     * flow 参数异常(会导致schdPoint hold)
     */
    FLOW_PARAM_ERROR("601", ErrorLevels.ERROR, "flow param error"),
    /**
     * flow 定义异常会导致schdPoint hold)
     */
    FLOW_DEF_ERROR("602", ErrorLevels.ERROR, "flow def error"),

    /**
     * 事件参数异常 定义异常会导致schdPoint hold)
     */
    EVENT_ILLEGAL_ERROR("603", ErrorLevels.ERROR, "event dispatcher error"),

    /**
     * 变更风险等级权重模板重复
     */
    MULTIPLE_CHANGE_RISK_TEMPLATE("604", ErrorLevels.ERROR, "too much templates matched"),
    /**
     * 无效的变更风险等级模板
     */
    INVALID_CHANGE_RISK_TEMPLATE("605", ErrorLevels.ERROR, "invalid change risk template"),

    /** 800 - 899 integration */
    /**
     * The Connect limit.
     */
    CONNECT_LIMIT("800", ErrorLevels.ERROR, "connect limit"),
    /**
     * The Unknown host.
     */
    UNKNOWN_HOST("801", ErrorLevels.ERROR, "unknown host"),
    /**
     * The Zappinfo app query error.
     */
    ZAPPINFO_APP_QUERY_ERROR("802", ErrorLevels.ERROR, "zappinfo app query error"),
    /**
     * The Integration read timeout.
     */
    INTEGRATION_READ_TIMEOUT("803", ErrorLevels.ERROR, "integration read timeout"),
    /**
     * The Aliwork query error.
     */
    ALIWORK_QUERY_ERROR("804", ErrorLevels.ERROR, "aliwork query error"),
    /**
     * The Invoke pb error.
     */
    INVOKE_PB_ERROR("805", ErrorLevels.ERROR, "invoke pb error"),
    /**
     * The Invoke tr error.
     */
    INVOKE_TR_ERROR("806", ErrorLevels.ERROR, "invoke tr error"),
    /**
     * The Cipher error.
     */
    CIPHER_ERROR("807", ErrorLevels.ERROR, "cryptprod error"),
    /**
     * 调用SPI成功,SPI内部错误
     */
    SPI_INNER_ERROR("808", ErrorLevels.ERROR, "SPI internal error"),
    /**
     * The Sofa route error.
     */
    SOFA_ROUTE_ERROR("809", ErrorLevels.ERROR, "SOFA route error"),
    /**
     * The Miss meta data query.
     */
    MISS_META_DATA_QUERY("810", ErrorLevels.ERROR, "meta data query is not matched"),

    /** 900 - 999 system */
    /**
     * The System error.
     */
    SYSTEM_ERROR("901", ErrorLevels.ERROR, "system error"),
    /**
     * The Db error.
     */
    DB_ERROR("902", ErrorLevels.ERROR, "db error"),
    /**
     * 内部校验错误
     */
    INNER_CHECK_ERROR("903", ErrorLevels.ERROR, "inner check error"),

    /**
     * 无效的变更检查类型
     */
    INVALID_CHANGE_CHECK_ERROR("904", ErrorLevels.ERROR, "invalid change type error"),

    /**
     * 系统环境未知
     */
    UNKNOWN_SYSTEM_ENV_ERROR("998", ErrorLevels.ERROR, "unknown system env error"),
    /**
     * The Unknown error.
     */
    UNKNOWN_ERROR("999", ErrorLevels.ERROR, "unknown error"),;

    /**
     * code
     */
    private final String code;

    /**
     * error level
     */
    private final String errorLevel;

    /**
     * description
     */
    private final String description;


    /**
     * Gets by code.
     *
     * @param code the code
     * @return by code
     */
    public static AlterShieldInternalErrorCode getByCode(String code) {
        for (AlterShieldInternalErrorCode detailCode : values()) {
            if (detailCode.getCode().equals(code)) {

                return detailCode;
            }
        }
        return null;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "[" + code + ":" + description + "]";
    }
}
