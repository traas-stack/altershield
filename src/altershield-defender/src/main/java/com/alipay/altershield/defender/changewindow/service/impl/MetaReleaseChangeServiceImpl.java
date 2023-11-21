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
//package com.alipay.altershield.defender.changewindow.service.impl;
//
//import com.alibaba.common.lang.StringUtil;
//import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
//import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;
//import com.alipay.opscloud.changewindow.repository.MetaReleaseChangeRepository;
//import com.alipay.opscloud.changewindow.repository.convert.MetaReleaseChangeEntityConvertor;
//import com.alipay.opscloud.changewindow.service.MetaReleaseChangeService;
//import com.alipay.opscloud.changewindow.service.request.MetaReleaseChangeRequest;
//import com.alipay.opscloud.changewindow.service.request.QueryReleaseChangeInPageRequest;
//import com.alipay.opscloud.changewindow.vo.MetaReleaseChangeVO;
//import com.alipay.opscloud.common.base.user.model.User;
//import com.alipay.opscloud.common.base.user.service.UserService;
//import com.alipay.opscloud.common.result.OpsCloudPageResult;
//import com.alipay.opscloud.common.service.ServicePageProcessTemplate;
//import com.alipay.opscloud.common.service.ServicePageQuery;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * 破网相关
// *
// * @author wb-zs909667
// * @version : MetaReleaseChangeServiceImpl.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
// */
//@Service
//public class MetaReleaseChangeServiceImpl implements MetaReleaseChangeService {
//
//    @Autowired
//    private MetaReleaseChangeRepository metaReleaseChangeRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public OpsCloudPageResult<List<MetaReleaseChangeVO>> pageQuery(QueryReleaseChangeInPageRequest request) {
//
//        OpscldMetaReleasechangeParam param = MetaReleaseChangeEntityConvertor.requestToparam(request);
//        return ServicePageProcessTemplate.process(request.getCurrent(), request.getPageSize(),
//                new ServicePageQuery<MetaReleaseChangeVO>() {
//                    @Override
//                    public List<MetaReleaseChangeVO> query(int offset, int limit) {
//                        param.setPagination(request.getCurrent(), request.getPageSize());
//                        List<MetaReleaseChangeEntity> releaseChangeEntities = metaReleaseChangeRepository.selectByPage(param);
//                        return releaseChangeEntities.stream().map(e -> buildReleaseChangeVO(e)).collect(Collectors.toList());
//                    }
//
//                    @Override
//                    public long total() {
//                        return metaReleaseChangeRepository.selectTotalCount(param);
//                    }
//                });
//    }
//
//    @Override
//    public String deleteById(String id) {
//        // 1. todo 校验是否有超管权限
//        // 2. 删除破网工单
//        boolean tag = metaReleaseChangeRepository.deleteById(id);
//        if (tag) {
//            return "删除成功";
//        } else {
//            return "操作失败";
//        }
//    }
//
//    @Override
//    public Boolean updateReleaseChangeStatus(MetaReleaseChangeRequest request) {
//        // 1. todo 校验是否有超管权限
//        return metaReleaseChangeRepository.updateReleaseChangeStatus(request.getStatus().getType(), request.getId());
//    }
//
//    private MetaReleaseChangeVO buildReleaseChangeVO(MetaReleaseChangeEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//        MetaReleaseChangeVO releaseChangeVO = new MetaReleaseChangeVO();
//
//        releaseChangeVO.setId(entity.getId());
//        releaseChangeVO.setOperator(entity.getOperator());
//
//        // 域账号查询真名
//        User user = userService.getByLoginName(entity.getOperator());
//        if (!Objects.isNull(user)) {
//            releaseChangeVO.setOperatorView(user.getNickNameCn());
//        }
//        if (StringUtil.isNotBlank(entity.getName())) {
//            releaseChangeVO.setName(entity.getName());
//        }
//        releaseChangeVO.setBlockChangeId(entity.getBlockChangeId());
//        if (StringUtil.isNotBlank(entity.getBlockChangeName())) {
//            releaseChangeVO.setBlockChangeName(entity.getBlockChangeName());
//        }
//        releaseChangeVO.setStartTime(entity.getStartTime());
//        releaseChangeVO.setEndTime(entity.getEndTime());
//        releaseChangeVO.setStatus(entity.getStatus());
//        releaseChangeVO.setGmtCreate(entity.getGmtCreate());
//        releaseChangeVO.setGmtModified(entity.getGmtModified());
//        releaseChangeVO.setReason(entity.getReason());
//        releaseChangeVO.setBizInfluence(entity.getBizInfluence());
//        releaseChangeVO.setArchDomainRange(entity.getArchDomainRange());
//        releaseChangeVO.setBusGroupRange(entity.getBusGroupRange());
//        releaseChangeVO.setReasonType(entity.getReasonType());
//        releaseChangeVO.setTargetTypes(entity.getChangeTargetType());
//
//        return releaseChangeVO;
//    }
//
//
//}
