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
package com.alipay.altershield.common.id.enums;

import com.alipay.altershield.framework.common.util.CommonUtil;

/**
 * @author shuo.qius
 * @version May 25, 2018
 */
public enum IdBizCodeEnum {
    /**
     *
     */
    DEFAULT("999", "seq_default"),
    /**
     * {@link CommonUtil#FAKE_ID_BIZ_CODE}
     */
    OPSCLD_FAKE_ID(CommonUtil.FAKE_ID_BIZ_CODE, "opscld_fake"),

    /**
     *
     */
    OPSCLD_TRACE("001", "opscld_trace"),
    /**
     *
     */
    OPSCLD_TRANSACTION("002", "opscld_transaction"),


    /**
     *
     */
    OPSCLD_KEY_VALUE("010", "opscld_key_value"),

    OPSCLOUD_KEY_VALUE("013", "opscloud_key_value"),
    /**
     * 调度事件
     */
    OPSCLD_SCHD_EVENT("011", "opscld_scheduler_event"),

    // ---------------- meta ------------------------------------------------
    /**
     * 变更场景业务码
     */
    OPSCLD_META_CHANGE_SCENE("020", "opscld_meta_change_scene"),

    /**
     * 变更场景步骤业务码
     */
    OPSCLD_META_CHANGE_STEP("021", "opscld_meta_change_scene"),

    /**
     * 变更类型
     */
    OPSCLD_META_CHANGE_TYPE("022", "opscld_meta_change_type"),

    /**
     * 业务域
     */
    OPSCLD_META_BUSINESS_DOMAIN("023", "opscld_meta_business_domain"),

    /**
     * 变更平台
     */
    OPSCLD_META_PLATFORM("024", "opscld_meta_platform"),


    // ---------------- res ------------------------------------------------


    // ---------------- exe ------------------------------------------------
    OPSCLD_EXE_ORDER("100", "opscld_exe_order"),

    OPSCLD_EXE_NODE("101", "opscld_exe_node"),

    // ---------------- plan ------------------------------------------------
    // TODO 表替换
    OPSPLAN_RECORD_LOG("201", "opsplan_record_log"),

    OPSPLAN_CHANGE_ORDER("202", "opsplan_change_order"),

    OPSPLAN_CHANGE_INSTANCE("203", "opsplan_change_instance"),

    OPSPLAN_CHANGE_STEP("204", "opsplan_change_tep"),

    OPSPLAN_CHANGE_PLAN("205", "opsplan_change_plan"),

    OPSPLAN_RELATED_PERSONNEL("206", "opsplan_related_personnel"),

    OPSPLAN_CHANGE_REPORT("207", "opsplan_change_report"),

    OPSPLAN_OPERATION_LOG("208", "opsplan_operation_log"),

    OPSPLAN_CHANGE_SUPPORT_SCOPE("209", "opsplan_change_support_scope"),

    OPSPLAN_EXE_DRM_STRATEGY("210", "opsplan_exe_drm_strategy"),

    OPSPLAN_EXE_RELEASE_STRATEGY("211", "opsplan_exe_release_strategy"),

    OPSPLAN_META_PERSON_RECEIVE("212", "opsplan_meta_person_receive"),

    OPSPLAN_SYNC_CHANGE_EVENT("213", "opsplan_sync_change_event"),

    //---------------- decision ---------------------------------------------
    OPSCLD_META_DECISION_SCENE("301", "opscld_meta_decision_scene"),

    OPSCLD_META_DECISION_RULE("302", "opscld_meta_decision_rule"),
    /**
     * 变更打标
     */
    OPSCLD_META_DECISION_RELATION("305", "opscloud_meta_decision_relation"),

    OPSCLD_EXE_DECISION_MATCH_RECORD("303", "opscld_exe_decision_match_record"),

    OPSCLD_EXE_DECISION_RESULT("304", "opscld_exe_decision_result"),


    //---------------- defender ---------------------------------------------
    /**
     * 防御校验执行记录
     */
    OPSCLD_EXE_DEFENDER_DETECT_EXE_ID("401", "opscloud_exe_defender_detect_exe_id"),
    /**
     * 防御校验分组id
     */
    OPSCLD_DEFENDER_DETECT_GROUP_ID("402", "opscloud_defender_detect_group_id"),
    /**
     * 防御规则主键id
     */
    OPSCLD_META_DEFENDER_RULE_ID("403", "opscloud_meta_defender_rule_id"),
    /**
     * 防御服务主键id
     */
    OPSCLD_META_DEFENDER_SERVICE_ID("404", "opscloud_meta_defender_service_id"),
    OPSCLOUD_META_ABILITY_PLUGIN_VERSION("405", "opscloud_meta_ability_plugin_version"),
    OPSCLOUD_META_ABILITY("406", "opscloud_meta_ability"),
    OPSCLOUD_META_ABILITY_ANALYST_RELATION_COPY("407", "opscloud_meta_ability_analyst_relation"),

    /**
     * 老防御规则表中groupId生成
     */
    OPSCLD_OLD_META_DEFENDER_RULE_GROUP_ID("405", "opscloud_old_meta_defender_rule_gourp_id"),
    /**
     * 老防御规则表中ruleId生成*
     */
    OPSCLD_OLD_META_DEFENDER_RULE_ID("406", "opscloud_old_meta_defender_rule_id"),

    /**
     * 智能防线校验id
     */
    OPSCLOUD_SMART_DETECT_ID("408", "opscloud_smart_detect_id"),
    /**
     * 智能防线指标校验id*
     */
    OPSCLOUD_SMART_METRIC_DETECT_ID("409", "opscloud_smart_metric_detect_id"),

    //---------------- plugin ---------------------------------------------
    OPSCLD_META_PLUGIN_ID("501", "opscloud_meta_plugin_id"),
    // ------------------  subscription  --------------------------------------
//    OPSCLD_META_SUBSCRIPTION_GROUP("050", "opscld_subscription_group"),
//
    OPSCLD_META_SUBSCRIPTION_RULE("051", "opscld_subscription_rule"),

    OPSCLD_META_SUBSCRIPTION_NOTIFY("054", "opscld_subscription_notify"),

//    OPSCLD_META_BREAKDOWN_INFO("052", "opscld_meta_breakdown_info"),
//
//    OPSCLD_META_EFFECTIVE_DEFENSE_RULE_CASE("053", "opscld_effective_defense_rule_case"),

    OPSCLD_META_INFLUENCE_PLUGIN_EXTEND("055", "opscloud_meta_influence_plugin_extend"),

    //---------------- change tag ---------------------------------------------
    OPSCLOUD_CHANGE_TAG("601", "opscloud_change_tag_id"),

    CHANGE_MODEL_CODE("063", "change_model_code"),

    //---------------- search ---------------------------------------------
    OPSCLOUD_META_SEARCH_TEMPLATE("602", "opscloud_meta_search_template")
    ;

    private final String code;
    private final String seqName;

    IdBizCodeEnum(String code, String seqName) {
        this.code = code;
        this.seqName = seqName;
    }

    /**
     * @return property value of {@link #seqName}
     */
    public String getSeqName() {
        return seqName;
    }

    /**
     * @return property value of {@link #code}
     */
    public String getCode() {
        return code;
    }

    public static IdBizCodeEnum getByCode(String code) {
        for (IdBizCodeEnum c : IdBizCodeEnum.values()) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;

    }
}
