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
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.meta.repository.impl;

import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.defender.framework.meta.dal.dataobject.MetaDefenderRuleDO;
import com.alipay.altershield.defender.framework.meta.dal.dataobject.MetaDefenderRuleParam;
import com.alipay.altershield.defender.framework.meta.dal.mapper.MetaDefenderRuleMapper;
import com.alipay.altershield.defender.framework.meta.entity.MetaDefenderRuleEntity;
import com.alipay.altershield.defender.framework.meta.entity.convertor.MetaDefenderRuleConvertor;
import com.alipay.altershield.defender.framework.meta.repository.MetaDefenderRuleRepository;
import com.alipay.altershield.framework.core.risk.model.enums.DefenseStageEnum;
import com.alipay.altershield.shared.defender.enums.DefenderRuleStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Change defense rules repository layer
 *
 * @author yhaoxuan
 * @version MetaDefenderRuleRepositoryImpl.java, v 0.1 2022年08月25日 10:24 下午 yhaoxuan
 */
@Repository
public class MetaDefenderRuleRepositoryImpl implements MetaDefenderRuleRepository {

    @Autowired
    private MetaDefenderRuleMapper metaDefenderRuleMapper;

    @Autowired
    private MetaDefenderRuleConvertor defenderRuleConvertor;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * Select all list.
     *
     * @return the list
     */
    @Override
    public List<MetaDefenderRuleEntity> selectAll() {
        List<MetaDefenderRuleDO> ruleDOs = metaDefenderRuleMapper.selectAll();
        return defenderRuleConvertor.convert2ModelList(ruleDOs);
    }

    /**
     * Get the rule list based on the arming range identification and verification stage
     *
     * @param defenseRangeKeys defense range key - changeSceneKey + changeTag / changeKey + changeTag
     * @param stage pre or post
     * @return rule list
     */
    @Override
    public List<MetaDefenderRuleEntity> selectByRangeKeysAndStage(Set<String> defenseRangeKeys, DefenseStageEnum stage) {
        MetaDefenderRuleParam param = new MetaDefenderRuleParam();
        MetaDefenderRuleParam.Criteria criteria = param.createCriteria();
        criteria.andDefenseRangeKeyIn(new ArrayList<>(defenseRangeKeys));
        criteria.andStatusNotEqualTo(DefenderRuleStatusEnum.DISABLED.getStatus());
        if (stage != null) {
            criteria.andStageEqualTo(stage.getStage());
        }

        List<MetaDefenderRuleDO> ruleDOs = metaDefenderRuleMapper.selectByParam(param);
        return defenderRuleConvertor.convert2ModelList(ruleDOs);
    }

    /**
     * Obtain the corresponding record according to the defense rule id
     *
     * @return rule entity
     */
    @Override
    public MetaDefenderRuleEntity loadByRuleId(String ruleId) {
        MetaDefenderRuleDO ruleDO = metaDefenderRuleMapper.selectByPrimaryKey(ruleId);
        return defenderRuleConvertor.convert2Model(ruleDO);
    }

    /**
     * Obtain the corresponding record according to the defense rule id list
     *
     * @param ruleIds rule id list
     * @return rule list
     */
    @Override
    public List<MetaDefenderRuleEntity> selectByRuleIds(Set<String> ruleIds) {
        MetaDefenderRuleParam param = new MetaDefenderRuleParam();
        param.createCriteria().andIdIn(new ArrayList<>(ruleIds));
        List<MetaDefenderRuleDO> ruleDOs = metaDefenderRuleMapper.selectByParam(param);
        return defenderRuleConvertor.convert2ModelList(ruleDOs);
    }

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
    @Override
    public List<MetaDefenderRuleEntity> selectRuleInPage(String ruleId, String name, List<String> owners, String status, String tenant,
                                                         String stage, String pluginKey, List<String> defenseRangeKeys, int current, int pageSize) {
        MetaDefenderRuleParam param = new MetaDefenderRuleParam();

        if (!CollectionUtils.isEmpty(owners)) {
            for (String owner : owners) {
                MetaDefenderRuleParam.Criteria criteria = param.createCriteria();
                criteria.andOwnerLike(buildLikeString(owner));
                extendPageSelectCriteria(criteria, ruleId, name, status, tenant, stage, pluginKey, defenseRangeKeys);
            }
        } else {
            MetaDefenderRuleParam.Criteria criteria = param.createCriteria();
            extendPageSelectCriteria(criteria, ruleId, name, status, tenant, stage, pluginKey, defenseRangeKeys);
        }

        param.appendOrderByClause(MetaDefenderRuleParam.OrderCondition.GMTCREATE, MetaDefenderRuleParam.SortType.DESC);
        param.setPagination(current / pageSize, pageSize);

        List<MetaDefenderRuleDO> ruleDOs = metaDefenderRuleMapper.selectByParam(param);
        return defenderRuleConvertor.convert2ModelList(ruleDOs);
    }

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
    @Override
    public long count(String ruleId, String name, List<String> owners, String status, String tenant, String stage,
                      String pluginKey, List<String> defenseRangeKeys) {
        MetaDefenderRuleParam param = new MetaDefenderRuleParam();

        if (!CollectionUtils.isEmpty(owners)) {
            for (String owner : owners) {
                MetaDefenderRuleParam.Criteria criteria = param.createCriteria();
                criteria.andOwnerLike(buildLikeString(owner));
                extendPageSelectCriteria(criteria, ruleId, name, status, tenant, stage, pluginKey, defenseRangeKeys);
            }
        } else {
            MetaDefenderRuleParam.Criteria criteria = param.createCriteria();
            extendPageSelectCriteria(criteria, ruleId, name, status, tenant, stage, pluginKey, defenseRangeKeys);
        }

        return metaDefenderRuleMapper.countByParam(param);
    }

    /**
     * create rule
     *
     * @param rule rule entity
     * @return create result
     */
    @Override
    public boolean insert(MetaDefenderRuleEntity rule) {
        MetaDefenderRuleDO ruleDO = defenderRuleConvertor.convert2DO(rule);
        int column = metaDefenderRuleMapper.insert(ruleDO);
        return column > 0;
    }

    /**
     * modify rule
     *
     * @param rule rule entity
     * @return modify result
     */
    @Override
    public boolean update(MetaDefenderRuleEntity rule) {
        MetaDefenderRuleDO ruleDO = defenderRuleConvertor.convert2DO(rule);
        int column = metaDefenderRuleMapper.updateByPrimaryKeySelective(ruleDO);
        return column > 0;
    }

    /**
     * delete rule
     *
     * @param ruleId rule id
     * @return delete result
     */
    @Override
    public boolean delete(String ruleId) {
        int column = metaDefenderRuleMapper.deleteByPrimaryKey(ruleId);
        return column > 0;
    }

    /**
     * Query conditions for extended paging query defense rules
     *
     * @param criteria Query conditional statement
     * @param ruleId rule id
     * @param name rule name
     * @param status rule status
     * @param tenant tenant
     * @param stage pre or post
     * @param defenseRangeKeys defense range key list
     */
    private void extendPageSelectCriteria(MetaDefenderRuleParam.Criteria criteria, String ruleId, String name, String status,
                                          String tenant, String stage, String pluginKey, List<String> defenseRangeKeys) {
        if (StringUtils.isNotBlank(ruleId)) {
            criteria.andIdEqualTo(ruleId);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(buildLikeString(name));
        }

        if (StringUtils.isNotBlank(status)) {
            criteria.andStatusEqualTo(status);
        }

        if (StringUtils.isNotBlank(tenant)) {
            criteria.andTenantEqualTo(tenant);
        }

        if (StringUtils.isNotBlank(stage)) {
            criteria.andStageEqualTo(stage);
        }

        if (!CollectionUtils.isEmpty(defenseRangeKeys)) {
            criteria.andDefenseRangeKeyIn(defenseRangeKeys);
        }

        if (!StringUtils.isBlank(pluginKey)) {
            criteria.andPluginKeyEqualTo(pluginKey);
        }
    }

    /**
     * Like
     * @param str
     * @return
     */
    private String buildLikeString(String str){
        return StringUtils.isBlank(str) ? null : "%" + str + "%";
    }
}