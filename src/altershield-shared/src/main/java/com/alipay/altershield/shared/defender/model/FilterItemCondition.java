/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.model;

import com.alibaba.fastjson.JSONPath;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.shared.defender.enums.FilterKeyEnum;
import com.alipay.altershield.shared.defender.enums.MatchTypeEnum;
import com.alipay.altershield.shared.defender.request.ChangeFilterRequest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The filter item condition structure
 *
 * @author yuefan.wyf
 * @version $Id: FilterCondition.java, v 0.1 2019年12月05日 下午12:07 yuefan.wyf Exp $
 */
public class FilterItemCondition {

    /**
     * Filter condition item name
     */
    private FilterKeyEnum name;

    /**
     * jsonpath path, currently only supported in change param and change result modes
     */
    private String jsonPath;

    /**
     * match expression
     */
    private MatchTypeEnum matchType;

    /**
     * rvalue
     */
    private String value;

    /**
     * Filter match
     *
     * @param request Match request structure
     * @return Does this item match
     */
    public boolean isMatch(ChangeFilterRequest request) {
        return isMatch(request, false);
    }

    /**
     * Filter match
     *
     * @param request Match request structure
     * @return Does this item match
     */
    public boolean isMatch(ChangeFilterRequest request, boolean ignoreMatchScope) {

        if (matchType == null || name == null) {
            return false;
        }

        Set<String> list = getFilterLeftValue(request, name);

        // During pre-matching, the matching rules of the changed scope are ignored and the matching is successful by default.
        if (ignoreMatchScope) {
            boolean isChangeScopeFilter = FilterKeyEnum.INFLUENCE_LOGIC_CELL.equals(name)
                    || FilterKeyEnum.INFLUENCE_IDC.equals(name)
                    || FilterKeyEnum.INFLUENCE_HOST.equals(name);

            // When matching the environment influenced, special judgment is required,
            // because the upstream may not provide the environment parameters during pre-matching.
            // If not, the matching will be successful by default.
            if (FilterKeyEnum.INFLUENCE_ENV.equals(name)) {
                if (CollectionUtils.isEmpty(request.getEnvs())) {
                    return true;
                }
            }

            if (isChangeScopeFilter) {
                return true;
            }
        }

        // If the lvalue is empty and the matching condition is not a reverse judgment, this item will be judged to be false by default.
        if (CollectionUtils.isEmpty(list)
                && !MatchTypeEnum.NOT_EQUAL.equals(matchType)
                && !MatchTypeEnum.CONTAINS_NONE.equals(matchType)
                && !MatchTypeEnum.NOT_START_WITH.equals(matchType)
                && !MatchTypeEnum.NOT_END_WITH.equals(matchType)) {
            return false;
        }

        if (MatchTypeEnum.EQUAL.equals(matchType)) {
            return list.contains(value);

        } else if (MatchTypeEnum.NOT_EQUAL.equals(matchType)) {
            return !list.contains(value);

        } else if (MatchTypeEnum.CONTAINS.equals(matchType)) {
            boolean match = false;
            for (String tmp : list) {
                match = match || tmp.contains(value);
            }
            return match;
        } else if (MatchTypeEnum.CONTAINS_NONE.equals(matchType)) {
            boolean match = true;
            for (String tmp : list) {
                match = match && !tmp.contains(value);
            }
            return match;
        } else if (MatchTypeEnum.START_WITH.equals(matchType)) {
            boolean match = false;
            for (String tmp : list) {
                match = match || tmp.startsWith(value);
            }
            return match;
        } else if (MatchTypeEnum.NOT_START_WITH.equals(matchType)) {
            boolean match = false;
            if (CollectionUtils.isEmpty(list)) {
                return true;
            }

            for (String tmp : list) {
                match = match || !tmp.startsWith(value);
            }
            return match;
        } else if (MatchTypeEnum.END_WITH.equals(matchType)) {
            boolean match = false;
            for (String tmp : list) {
                match = match || tmp.endsWith(value);
            }

            return match;
        } else if (MatchTypeEnum.NOT_END_WITH.equals(matchType)) {
            boolean match = false;
            if (CollectionUtils.isEmpty(list)) {
                return true;
            }

            for (String tmp : list) {
                match = match || !tmp.endsWith(value);
            }

            return match;
        } else if (MatchTypeEnum.LESS_THAN.equals(matchType)) {
            boolean match = false;
            if (checkBySize()) {
                match = list.size() < Integer.parseInt(value);
            } else {
                for (String tmp : list) {
                    try {

                        double index = Double.parseDouble(tmp);
                        match = match || index < Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }

            return match;
        } else if (MatchTypeEnum.GREATER_THAN.equals(matchType)) {
            boolean match = false;
            if (checkBySize()) {
                match = list.size() > Integer.parseInt(value);
            } else {
                for (String tmp : list) {
                    try {
                        double index = Double.parseDouble(tmp);
                        match = match || index > Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }

            return match;
        }

        return false;
    }

    /**
     * @param request
     * @param name
     * @return
     */
    public Set<String> getFilterLeftValue(ChangeFilterRequest request, FilterKeyEnum name) {
        switch (name) {
            case CHANGE_TITLE:
                if (CommonUtil.isBlank(request.getChangeTile())) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton(request.getChangeTile());
                }
            case CHANGE_PHASE:
                if (CommonUtil.isBlank(request.getChangePhase())) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton(request.getChangePhase());
                }
            case CHANGE_PHASE_BATCH_NAME:
                if (CommonUtil.isBlank(request.getChangePhaseBatchName())) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton(request.getChangePhaseBatchName());
                }
            case CHANGE_PHASE_BATCH_NO:
                if (request.getChangePhaseBatchNo() == -1) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton(String.valueOf(request.getChangePhaseBatchNo()));
                }
            case CHANGE_PARAM:
                return new HashSet<>(readFromJson(request.getChangeParam(), jsonPath));
            case CHANGE_RESULT:
                return new HashSet<>(readFromJson(request.getChangeResult(), jsonPath));
            case CHANGE_OPERATOR:
                if (request.getOperator() == null) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton((String) request.getOperator());
                }
            case CHANGE_TARGET_INSTANCE:
            case CHANGE_TARGET_TYPE:
                if (request.getChangeTargetTypes() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getChangeTargetTypes();
                }
            case CHANGE_SOURCE_PLATFORM:
                if (request.getSourcePlatform() == null) {
                    return Collections.emptySet();
                } else {
                    return Collections.singleton(request.getSourcePlatform());
                }
            case INFLUENCE_APP:
                if (request.getChangeApps() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getChangeApps();
                }
            case INFLUENCE_IDC:
                if (request.getIdcs() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getIdcs();
                }
            case INFLUENCE_HOST:
                if (request.getHosts() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getHosts();
                }
            case INFLUENCE_LOGIC_CELL:
                if (request.getLogicalCells() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getLogicalCells();
                }
            case INFLUENCE_TENANT:
                if (request.getTenants() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getTenants();
                }
            case CHANGE_ENTRANCE_CODE:
                if (request.getEntranceCodes() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getEntranceCodes();
                }
            case CHANGE_SCENARIO_CODE:
                if (request.getSceneCodes() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getSceneCodes();
                }
            case INFLUENCE_ENV:
                if (request.getEnvs() == null) {
                    return Collections.emptySet();
                } else {
                    return request.getEnvs();
                }
            default:
                break;
        }

        return Collections.emptySet();
    }

    /**
     * When comparing by greater than or less than, some condition item should be matched by size
     *
     * @return need matched by size
     */
    private boolean checkBySize() {
        if (FilterKeyEnum.CHANGE_TARGET_INSTANCE.equals(name)
                || FilterKeyEnum.INFLUENCE_APP.equals(name)
                || FilterKeyEnum.INFLUENCE_LOGIC_CELL.equals(name)
                || FilterKeyEnum.INFLUENCE_HOST.equals(name)
                || FilterKeyEnum.INFLUENCE_ENV.equals(name)
                || FilterKeyEnum.INFLUENCE_IDC.equals(name)
                || FilterKeyEnum.INFLUENCE_TENANT.equals(name)) {
            return true;
        }

        return false;
    }

    /**
     * parse json by json path
     *
     * @param json the json string
     * @param path json path
     * @return parse result
     */
    private List<String> readFromJson(String json, String path) {
        try {
            Object result = JSONPath.read(json, path);
            if (result == null) {
                return Collections.emptyList();
            } else if (result instanceof List) {
                return (List<String>) result;
            } else if (result instanceof String || result instanceof Number || result instanceof Boolean) {
                return new ArrayList<>(Collections.singletonList(String.valueOf(result)));
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            // todo log
        }

        return Collections.emptyList();
    }

    /**
     * Gets get match type.
     *
     * @return the get match type
     */
    public MatchTypeEnum getMatchType() {
        return matchType;
    }

    /**
     * Sets set match type.
     *
     * @param matchType the match type
     */
    public void setMatchType(MatchTypeEnum matchType) {
        this.matchType = matchType;
    }

    /**
     * Gets get name.
     *
     * @return the get name
     */
    public FilterKeyEnum getName() {
        return name;
    }

    /**
     * Sets set name.
     *
     * @param name the name
     */
    public void setName(FilterKeyEnum name) {
        this.name = name;
    }

    /**
     * Gets get value.
     *
     * @return the get value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets set value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets get json path.
     *
     * @return the get json path
     */
    public String getJsonPath() {
        return jsonPath;
    }

    /**
     * Sets set json path.
     *
     * @param jsonPath the json path
     */
    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "FilterCondition{" +
                "matchType=" + matchType +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}