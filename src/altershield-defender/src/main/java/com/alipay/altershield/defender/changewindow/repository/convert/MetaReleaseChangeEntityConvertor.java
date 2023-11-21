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
///**
// * Alipay.com Inc.
// * Copyright (c) 2004-2019 All Rights Reserved.
// */
//package com.alipay.altershield.defender.changewindow.repository.convert;
//
//import com.alibaba.common.lang.StringUtil;
//import com.alibaba.fastjson.JSON;
//import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleaseChangeDO;
//import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
//import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;
//import com.alipay.opscloud.changewindow.enums.ReleaseChangeStatusEnum;
//import com.alipay.opscloud.changewindow.service.request.QueryReleaseChangeInPageRequest;
//import com.alipay.opscloud.changewindow.vo.ReleaseChangeConfigNode;
//import com.alipay.opscloud.common.base.businessdomain.entity.ArchDomainTreeNode;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 破网相关
// *
// * @author wb-zs909667
// * @version : MetaReleaseChangeEntityConvertor.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
// */
//@Component
//public class MetaReleaseChangeEntityConvertor {
//
//    /**
//     * Convert 2 model meta release change entity.
//     *
//     * @param releaseChangeDO the release change do
//     * @return the meta release change entity
//     */
//    public static MetaReleaseChangeEntity convert2Model(OpscldMetaReleaseChangeDO releaseChangeDO) {
//        if (releaseChangeDO == null) {
//            return null;
//        }
//        MetaReleaseChangeEntity entity = new MetaReleaseChangeEntity();
//
//        entity.setId(releaseChangeDO.getId());
//        entity.setGmtCreate(releaseChangeDO.getGmtCreate());
//        entity.setGmtModified(releaseChangeDO.getGmtModified());
//        entity.setOperator(releaseChangeDO.getOperator());
//        if (StringUtil.isNotBlank(releaseChangeDO.getName())) {
//            entity.setName(releaseChangeDO.getName());
//        }
//        entity.setBlockChangeId(releaseChangeDO.getBlockChangeId());
//        if (StringUtil.isNotBlank(releaseChangeDO.getBlockChangeName())) {
//            entity.setBlockChangeName(releaseChangeDO.getBlockChangeName());
//        }
//        entity.setStartTime(releaseChangeDO.getStartTime());
//        entity.setEndTime(releaseChangeDO.getEndTime());
//        entity.setEffectEnvironment(JSON.parseArray(releaseChangeDO.getEffectEnvironment(), String.class));
//        entity.setReasonType(releaseChangeDO.getReasonType());
//        if (StringUtil.isNotBlank(releaseChangeDO.getReasonRef())) {
//            entity.setReason(releaseChangeDO.getReasonRef());
//        }
//        entity.setBizInfluence(releaseChangeDO.getBizInfluence());
//        entity.setRiskDescription(releaseChangeDO.getRiskDescription());
//        entity.setArchDomainRange(JSON.parseArray(releaseChangeDO.getArchdomainRange(), ArchDomainTreeNode.class));
//        entity.setBusGroupRange(JSON.parseArray(releaseChangeDO.getBusgroupRange(), String.class));
//        entity.setChangeTargetType(JSON.parseArray(releaseChangeDO.getChangeTargetType(), String.class));
//        entity.setConfig(JSON.parseArray(releaseChangeDO.getConfigRef(), ReleaseChangeConfigNode.class));
//        entity.setStatus(ReleaseChangeStatusEnum.getByType(releaseChangeDO.getStatus()));
//        if (StringUtil.isNotBlank(releaseChangeDO.getApprovalFlowId())) {
//            entity.setApprovalFlowId(releaseChangeDO.getApprovalFlowId());
//        }
//        entity.setGmtCreate(releaseChangeDO.getGmtCreate());
//        entity.setGmtModified(releaseChangeDO.getGmtModified());
//        entity.setTenant(releaseChangeDO.getTenant());
//
//        return entity;
//    }
//
//    /**
//     * Convert 2 model list list.
//     *
//     * @param modelListDO the model list do
//     * @return the list
//     */
//    public static List<MetaReleaseChangeEntity> convert2ModelList(List<OpscldMetaReleaseChangeDO> modelListDO) {
//        List<MetaReleaseChangeEntity> modelList = new ArrayList<>();
//        for (OpscldMetaReleaseChangeDO doModel : modelListDO) {
//            modelList.add(convert2Model(doModel));
//        }
//        return modelList;
//    }
//
//    /**
//     * Convert 2 do opscld meta release change do.
//     *
//     * @param entity the entity
//     * @return the opscld meta release change do
//     */
//    public static OpscldMetaReleaseChangeDO convert2DO(MetaReleaseChangeEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//        OpscldMetaReleaseChangeDO releaseChangeDO = new OpscldMetaReleaseChangeDO();
//
//        releaseChangeDO.setId(entity.getId());
//        releaseChangeDO.setOperator(entity.getOperator());
//        if (StringUtil.isNotBlank(entity.getName())) {
//            releaseChangeDO.setName(entity.getName().trim());
//        }
//        releaseChangeDO.setBlockChangeId(entity.getBlockChangeId());
//        if (StringUtil.isNotBlank(entity.getBlockChangeName())) {
//            releaseChangeDO.setBlockChangeName(entity.getBlockChangeName().trim());
//        }
//        releaseChangeDO.setStartTime(entity.getStartTime());
//        releaseChangeDO.setEndTime(entity.getEndTime());
//        releaseChangeDO.setEffectEnvironment(JSON.toJSONString(entity.getEffectEnvironment()));
//        releaseChangeDO.setReasonType(entity.getReasonType() == null ? "OTHER" : entity.getReasonType());
//        if (StringUtil.isNotBlank(entity.getReason())) {
//            releaseChangeDO.setReasonRef(entity.getReason().trim());
//        }
//        releaseChangeDO.setBizInfluence(entity.getBizInfluence());
//        releaseChangeDO.setRiskDescription(entity.getRiskDescription());
//        releaseChangeDO.setArchdomainRange(JSON.toJSONString(entity.getArchDomainRange()));
//        releaseChangeDO.setBusgroupRange(JSON.toJSONString(entity.getBusGroupRange()));
//        releaseChangeDO.setChangeTargetType(JSON.toJSONString(entity.getChangeTargetType()));
//        if (CollectionUtils.isNotEmpty(entity.getConfig())) {
//            releaseChangeDO.setConfigRef(JSON.toJSONString(entity.getConfig()));
//        }
//        if (entity.getStatus() != null) {
//            releaseChangeDO.setStatus(entity.getStatus().getType());
//        }
//        if (StringUtil.isNotBlank(entity.getApprovalFlowId())) {
//            releaseChangeDO.setApprovalFlowId(entity.getApprovalFlowId());
//        }
//        releaseChangeDO.setGmtCreate(entity.getGmtCreate());
//        releaseChangeDO.setGmtModified(entity.getGmtModified());
//        releaseChangeDO.setTenant(entity.getTenant());
//
//        return releaseChangeDO;
//    }
//
//    /**
//     * request 转param
//     * <p>
//     *
//     * @param request 实体对象模型
//     * @return DO对象
//     */
//    public static OpscldMetaReleasechangeParam requestToparam(QueryReleaseChangeInPageRequest request) {
//        if (request == null) {
//            return null;
//        }
//        OpscldMetaReleasechangeParam param = new OpscldMetaReleasechangeParam();
//        OpscldMetaReleasechangeParam.Criteria criteria = param.createCriteria();
//        if (StringUtils.isNotBlank(request.getBlockChangeId())) {
//            criteria.andBlockchangeIdEqualTo(request.getBlockChangeId());
//        }
//        if (StringUtils.isNotBlank(request.getReleaseChangeName())) {
//            criteria.andNameLike(request.getReleaseChangeName());
//        }
//        if (StringUtils.isNotBlank(request.getOperator())) {
//            criteria.andOperatorEqualTo(request.getOperator());
//        }
//        if (StringUtils.isNotBlank(request.getId())) {
//            List<ReleaseChangeConfigNode> config = new ArrayList<>();
//            ReleaseChangeConfigNode configNode = new ReleaseChangeConfigNode();
//            configNode.setId(request.getId());
//            config.add(configNode);
//            criteria.andConfigRefLike(JSON.toJSONString(config));
//        }
//        return param;
//    }
//
//}
