/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.meta.repository;

import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;

import java.util.List;
import java.util.Set;

/**
 * Defense rules repository layer method
 *
 * @author yhaoxuan
 * @version MetaDefenderRuleRepository.java, v 0.1 2022年08月25日 10:14 下午 yhaoxuan
 */
public interface MetaDefenderRuleRepository {

    /**
     * Query all rule lists
     *
     * @return rule list
     */
    List<MetaDefenderRuleEntity> selectAll();

    /**
     * Get the rule list based on the arming range identification and verification stage
     *
     * @param defenseRangeKeys defense range key - changeSceneKey + changeTag / changeKey + changeTag
     * @param stage pre or post
     * @return rule list
     */
    List<MetaDefenderRuleEntity> selectByRangeKeysAndStage(Set<String> defenseRangeKeys, DefenseStageEnum stage);

    /**
     * Obtain the corresponding record according to the defense rule id
     *
     * @return rule entity
     */
    MetaDefenderRuleEntity loadByRuleId(String ruleId);

    /**
     * Obtain the corresponding record according to the defense rule id list
     *
     * @param ruleIds rule id list
     * @return rule list
     */
    List<MetaDefenderRuleEntity> selectByRuleIds(Set<String> ruleIds);

    /**
     * Query the list of defense rules by paging according to conditions
     *
     * @param ruleId rule id
     * @param name rule name
     * @param owners rule owner list, multiple time 'or' related query
     * @param status rule status
     * @param tenant tenant
     * @param stage pre or post
     * @param defenseRangeKeys defense range key list
     * @param current current page
     * @param pageSize page size
     * @return rule list
     */
    List<MetaDefenderRuleEntity> selectRuleInPage(String ruleId, String name, List<String> owners, String status, String tenant,
                                                  String stage, String pluginKey, List<String> defenseRangeKeys, int current, int pageSize);

    /**
     * Query the number of defense rules based on conditions
     *
     * @param ruleId rule id
     * @param name rule name
     * @param owners rule owner list, multiple time 'or' related query
     * @param status rule status
     * @param tenant tenant
     * @param stage pre or post
     * @param defenseRangeKeys defense range key list
     * @return number of rule
     */
    long count(String ruleId, String name, List<String> owners, String status, String tenant, String stage, String pluginKey, List<String> defenseRangeKeys);

    /**
     * create rule
     *
     * @param rule rule entity
     * @return create result
     */
    boolean insert(MetaDefenderRuleEntity rule);

    /**
     * modify rule
     *
     * @param rule rule entity
     * @return modify result
     */
    boolean update(MetaDefenderRuleEntity rule);

    /**
     * delete rule
     *
     * @param ruleId rule id
     * @return delete result
     */
    boolean delete(String ruleId);
}