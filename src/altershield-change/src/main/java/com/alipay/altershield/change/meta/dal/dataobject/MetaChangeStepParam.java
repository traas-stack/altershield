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
package com.alipay.altershield.change.meta.dal.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jinyalong
 */
public class MetaChangeStepParam {
    /**
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     *
     * @mbg.generated
     */
    @Deprecated
    protected boolean distinct;

    /**
     *
     * @mbg.generated
     */
    protected boolean page;

    /**
     *
     * @mbg.generated
     */
    protected int pageIndex;

    /**
     *
     * @mbg.generated
     */
    protected int pageSize;

    /**
     *
     * @mbg.generated
     */
    protected int pageStart;

    /**
     *
     * @mbg.generated
     */
    protected String distinctSql;

    /**
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbg.generated
     */
    public MetaChangeStepParam() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * @param orderCondition
     * @param sortType
     * @return
     *
     * @mbg.generated
     */
    public MetaChangeStepParam appendOrderByClause(OrderCondition orderCondition, SortType sortType) {
        if (null != orderByClause) {
            orderByClause = orderByClause + ", " + orderCondition.getColumnName() + " " + sortType.getValue();
        } else {
            orderByClause = orderCondition.getColumnName() + " " + sortType.getValue();
        }
        return this;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * @param distinct
     *
     * @mbg.generated
     */
    @Deprecated
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Deprecated
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * @param page
     * @return
     *
     * @mbg.generated
     */
    public MetaChangeStepParam setPage(boolean page) {
        this.page = page;
        return this;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public boolean isPage() {
        return page;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * @param pageSize
     * @return
     *
     * @mbg.generated
     */
    public MetaChangeStepParam setPageSize(int pageSize) {
        this.pageSize = pageSize < 1 ? 10 : pageSize;
        this.pageIndex = pageStart < 1 ? 0 : (pageStart - 1) * this.pageSize;
        return this;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageStart
     * @return
     *
     * @mbg.generated
     */
    public MetaChangeStepParam setPageStart(int pageStart) {
        this.pageStart = pageStart < 1 ? 1 : pageStart;
        this.pageIndex = (this.pageStart - 1) * this.pageSize;
        return this;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public int getPageStart() {
        return pageStart;
    }

    /**
     * @param pageStart
     * @param pageSize
     *
     * @mbg.generated
     */
    public void setPagination(int pageStart, int pageSize) {
        this.page = true;
        this.pageSize = pageSize < 1 ? 10 : pageSize;
        this.pageIndex = pageStart < 1 ? 0 : (pageStart - 1) * this.pageSize;
    }

    /**
     * @param condition
     * @return
     *
     * @mbg.generated
     */
    public MetaChangeStepParam appendDistinct(OrderCondition condition) {
        if (null != distinctSql){
            distinctSql = distinctSql + ", " + condition.getColumnName();
        } else {
            distinctSql = condition.getColumnName();
        }
        return this;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * @param criteria
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *
     * @mbg.generated
     */
    protected abstract static class AbstractGeneratedCriteria {
        protected List<Criterion> criteria;

        protected AbstractGeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyIsNull() {
            addCriterion("change_scene_key is null");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyIsNotNull() {
            addCriterion("change_scene_key is not null");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyEqualTo(String value) {
            addCriterion("change_scene_key =", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyNotEqualTo(String value) {
            addCriterion("change_scene_key <>", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyGreaterThan(String value) {
            addCriterion("change_scene_key >", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyGreaterThanOrEqualTo(String value) {
            addCriterion("change_scene_key >=", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyLessThan(String value) {
            addCriterion("change_scene_key <", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyLessThanOrEqualTo(String value) {
            addCriterion("change_scene_key <=", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyLike(String value) {
            addCriterion("change_scene_key like", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyNotLike(String value) {
            addCriterion("change_scene_key not like", value, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyIn(List<String> values) {
            addCriterion("change_scene_key in", values, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyNotIn(List<String> values) {
            addCriterion("change_scene_key not in", values, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyBetween(String value1, String value2) {
            addCriterion("change_scene_key between", value1, value2, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeSceneKeyNotBetween(String value1, String value2) {
            addCriterion("change_scene_key not between", value1, value2, "changeSceneKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyIsNull() {
            addCriterion("change_key is null");
            return (Criteria) this;
        }

        public Criteria andChangeKeyIsNotNull() {
            addCriterion("change_key is not null");
            return (Criteria) this;
        }

        public Criteria andChangeKeyEqualTo(String value) {
            addCriterion("change_key =", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyNotEqualTo(String value) {
            addCriterion("change_key <>", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyGreaterThan(String value) {
            addCriterion("change_key >", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyGreaterThanOrEqualTo(String value) {
            addCriterion("change_key >=", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyLessThan(String value) {
            addCriterion("change_key <", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyLessThanOrEqualTo(String value) {
            addCriterion("change_key <=", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyLike(String value) {
            addCriterion("change_key like", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyNotLike(String value) {
            addCriterion("change_key not like", value, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyIn(List<String> values) {
            addCriterion("change_key in", values, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyNotIn(List<String> values) {
            addCriterion("change_key not in", values, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyBetween(String value1, String value2) {
            addCriterion("change_key between", value1, value2, "changeKey");
            return (Criteria) this;
        }

        public Criteria andChangeKeyNotBetween(String value1, String value2) {
            addCriterion("change_key not between", value1, value2, "changeKey");
            return (Criteria) this;
        }

        public Criteria andStepTypeIsNull() {
            addCriterion("step_type is null");
            return (Criteria) this;
        }

        public Criteria andStepTypeIsNotNull() {
            addCriterion("step_type is not null");
            return (Criteria) this;
        }

        public Criteria andStepTypeEqualTo(String value) {
            addCriterion("step_type =", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotEqualTo(String value) {
            addCriterion("step_type <>", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeGreaterThan(String value) {
            addCriterion("step_type >", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeGreaterThanOrEqualTo(String value) {
            addCriterion("step_type >=", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeLessThan(String value) {
            addCriterion("step_type <", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeLessThanOrEqualTo(String value) {
            addCriterion("step_type <=", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeLike(String value) {
            addCriterion("step_type like", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotLike(String value) {
            addCriterion("step_type not like", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeIn(List<String> values) {
            addCriterion("step_type in", values, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotIn(List<String> values) {
            addCriterion("step_type not in", values, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeBetween(String value1, String value2) {
            addCriterion("step_type between", value1, value2, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotBetween(String value1, String value2) {
            addCriterion("step_type not between", value1, value2, "stepType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeIsNull() {
            addCriterion("change_content_type is null");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeIsNotNull() {
            addCriterion("change_content_type is not null");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeEqualTo(String value) {
            addCriterion("change_content_type =", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeNotEqualTo(String value) {
            addCriterion("change_content_type <>", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeGreaterThan(String value) {
            addCriterion("change_content_type >", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("change_content_type >=", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeLessThan(String value) {
            addCriterion("change_content_type <", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeLessThanOrEqualTo(String value) {
            addCriterion("change_content_type <=", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeLike(String value) {
            addCriterion("change_content_type like", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeNotLike(String value) {
            addCriterion("change_content_type not like", value, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeIn(List<String> values) {
            addCriterion("change_content_type in", values, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeNotIn(List<String> values) {
            addCriterion("change_content_type not in", values, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeBetween(String value1, String value2) {
            addCriterion("change_content_type between", value1, value2, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andChangeContentTypeNotBetween(String value1, String value2) {
            addCriterion("change_content_type not between", value1, value2, "changeContentType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeIsNull() {
            addCriterion("effective_target_type is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeIsNotNull() {
            addCriterion("effective_target_type is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeEqualTo(String value) {
            addCriterion("effective_target_type =", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeNotEqualTo(String value) {
            addCriterion("effective_target_type <>", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeGreaterThan(String value) {
            addCriterion("effective_target_type >", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("effective_target_type >=", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeLessThan(String value) {
            addCriterion("effective_target_type <", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeLessThanOrEqualTo(String value) {
            addCriterion("effective_target_type <=", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeLike(String value) {
            addCriterion("effective_target_type like", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeNotLike(String value) {
            addCriterion("effective_target_type not like", value, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeIn(List<String> values) {
            addCriterion("effective_target_type in", values, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeNotIn(List<String> values) {
            addCriterion("effective_target_type not in", values, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeBetween(String value1, String value2) {
            addCriterion("effective_target_type between", value1, value2, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andEffectiveTargetTypeNotBetween(String value1, String value2) {
            addCriterion("effective_target_type not between", value1, value2, "effectiveTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeIsNull() {
            addCriterion("change_target_type is null");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeIsNotNull() {
            addCriterion("change_target_type is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeEqualTo(String value) {
            addCriterion("change_target_type =", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeNotEqualTo(String value) {
            addCriterion("change_target_type <>", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeGreaterThan(String value) {
            addCriterion("change_target_type >", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("change_target_type >=", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeLessThan(String value) {
            addCriterion("change_target_type <", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeLessThanOrEqualTo(String value) {
            addCriterion("change_target_type <=", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeLike(String value) {
            addCriterion("change_target_type like", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeNotLike(String value) {
            addCriterion("change_target_type not like", value, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeIn(List<String> values) {
            addCriterion("change_target_type in", values, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeNotIn(List<String> values) {
            addCriterion("change_target_type not in", values, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeBetween(String value1, String value2) {
            addCriterion("change_target_type between", value1, value2, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andChangeTargetTypeNotBetween(String value1, String value2) {
            addCriterion("change_target_type not between", value1, value2, "changeTargetType");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefIsNull() {
            addCriterion("defence_config_json_ref is null");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefIsNotNull() {
            addCriterion("defence_config_json_ref is not null");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefEqualTo(String value) {
            addCriterion("defence_config_json_ref =", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefNotEqualTo(String value) {
            addCriterion("defence_config_json_ref <>", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefGreaterThan(String value) {
            addCriterion("defence_config_json_ref >", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefGreaterThanOrEqualTo(String value) {
            addCriterion("defence_config_json_ref >=", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefLessThan(String value) {
            addCriterion("defence_config_json_ref <", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefLessThanOrEqualTo(String value) {
            addCriterion("defence_config_json_ref <=", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefLike(String value) {
            addCriterion("defence_config_json_ref like", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefNotLike(String value) {
            addCriterion("defence_config_json_ref not like", value, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefIn(List<String> values) {
            addCriterion("defence_config_json_ref in", values, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefNotIn(List<String> values) {
            addCriterion("defence_config_json_ref not in", values, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefBetween(String value1, String value2) {
            addCriterion("defence_config_json_ref between", value1, value2, "defenceConfigJsonRef");
            return (Criteria) this;
        }

        public Criteria andDefenceConfigJsonRefNotBetween(String value1, String value2) {
            addCriterion("defence_config_json_ref not between", value1, value2, "defenceConfigJsonRef");
            return (Criteria) this;
        }
    }

    /**
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends AbstractGeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    /**
     *
     * @mbg.generated
     */
    public enum OrderCondition {
        /**
         *主键
         */
        ID("id"),
        /**
         *创建时间
         */
        GMTCREATE("gmt_create"),
        /**
         *修改时间
         */
        GMTMODIFIED("gmt_modified"),
        /**
         *变更步骤名称
         */
        NAME("name"),
        /**
         *变更场景key
         */
        CHANGESCENEKEY("change_scene_key"),
        /**
         *变更key
         */
        CHANGEKEY("change_key"),
        /**
         *变更步骤类型。change_batch,change_action,change_action_prefix,change_action_suffix
         */
        STEPTYPE("step_type"),
        /**
         *变更内容类型
         */
        CHANGECONTENTTYPE("change_content_type"),
        /**
         *生效载体类型
         */
        EFFECTIVETARGETTYPE("effective_target_type"),
        /**
         *变更对象类型
         */
        CHANGETARGETTYPE("change_target_type"),
        /**
         *防御配置信息
         */
        DEFENCECONFIGJSONREF("defence_config_json_ref");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public static OrderCondition getEnumByName(String name) {
            OrderCondition[] orderConditions = OrderCondition.values();
            for (OrderCondition orderCondition : orderConditions) {
                if (orderCondition.name().equalsIgnoreCase(name)) {
                    return orderCondition;
                }
            }
            throw new RuntimeException("OrderCondition of " + name + " enum not exist");
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    /**
     *
     * @mbg.generated
     */
    public enum SortType {
        /**
         * 升序
         */
        ASC("asc"),
        /**
         * 降序
         */
        DESC("desc");

        private String value;

        SortType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SortType getEnumByName(String name) {
            SortType[] sortTypes = SortType.values();
            for (SortType sortType : sortTypes) {
                if (sortType.name().equalsIgnoreCase(name)) {
                    return sortType;
                }
            }
            throw new RuntimeException("SortType of " + name + " enum not exist");
        }

        @Override
        public String toString() {
            return value;
        }
    }
}