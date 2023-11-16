package com.alipay.altershield.defender.changewindow.service.impl;

import com.alibaba.common.lang.StringUtil;
import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;
import com.alipay.opscloud.changewindow.repository.MetaReleaseChangeRepository;
import com.alipay.opscloud.changewindow.repository.convert.MetaReleaseChangeEntityConvertor;
import com.alipay.opscloud.changewindow.service.MetaReleaseChangeService;
import com.alipay.opscloud.changewindow.service.request.MetaReleaseChangeRequest;
import com.alipay.opscloud.changewindow.service.request.QueryReleaseChangeInPageRequest;
import com.alipay.opscloud.changewindow.vo.MetaReleaseChangeVO;
import com.alipay.opscloud.common.base.user.model.User;
import com.alipay.opscloud.common.base.user.service.UserService;
import com.alipay.opscloud.common.result.OpsCloudPageResult;
import com.alipay.opscloud.common.service.ServicePageProcessTemplate;
import com.alipay.opscloud.common.service.ServicePageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 破网相关
 *
 * @author wb-zs909667
 * @version : MetaReleaseChangeServiceImpl.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
@Service
public class MetaReleaseChangeServiceImpl implements MetaReleaseChangeService {

    @Autowired
    private MetaReleaseChangeRepository metaReleaseChangeRepository;

    @Autowired
    private UserService userService;

    @Override
    public OpsCloudPageResult<List<MetaReleaseChangeVO>> pageQuery(QueryReleaseChangeInPageRequest request) {

        OpscldMetaReleasechangeParam param = MetaReleaseChangeEntityConvertor.requestToparam(request);
        return ServicePageProcessTemplate.process(request.getCurrent(), request.getPageSize(),
                new ServicePageQuery<MetaReleaseChangeVO>() {
                    @Override
                    public List<MetaReleaseChangeVO> query(int offset, int limit) {
                        param.setPagination(request.getCurrent(), request.getPageSize());
                        List<MetaReleaseChangeEntity> releaseChangeEntities = metaReleaseChangeRepository.selectByPage(param);
                        return releaseChangeEntities.stream().map(e -> buildReleaseChangeVO(e)).collect(Collectors.toList());
                    }

                    @Override
                    public long total() {
                        return metaReleaseChangeRepository.selectTotalCount(param);
                    }
                });
    }

    @Override
    public String deleteById(String id) {
        // 1. todo 校验是否有超管权限
        // 2. 删除破网工单
        boolean tag = metaReleaseChangeRepository.deleteById(id);
        if (tag) {
            return "删除成功";
        } else {
            return "操作失败";
        }
    }

    @Override
    public Boolean updateReleaseChangeStatus(MetaReleaseChangeRequest request) {
        // 1. todo 校验是否有超管权限
        return metaReleaseChangeRepository.updateReleaseChangeStatus(request.getStatus().getType(), request.getId());
    }

    private MetaReleaseChangeVO buildReleaseChangeVO(MetaReleaseChangeEntity entity) {
        if (entity == null) {
            return null;
        }
        MetaReleaseChangeVO releaseChangeVO = new MetaReleaseChangeVO();

        releaseChangeVO.setId(entity.getId());
        releaseChangeVO.setOperator(entity.getOperator());

        // 域账号查询真名
        User user = userService.getByLoginName(entity.getOperator());
        if (!Objects.isNull(user)) {
            releaseChangeVO.setOperatorView(user.getNickNameCn());
        }
        if (StringUtil.isNotBlank(entity.getName())) {
            releaseChangeVO.setName(entity.getName());
        }
        releaseChangeVO.setBlockChangeId(entity.getBlockChangeId());
        if (StringUtil.isNotBlank(entity.getBlockChangeName())) {
            releaseChangeVO.setBlockChangeName(entity.getBlockChangeName());
        }
        releaseChangeVO.setStartTime(entity.getStartTime());
        releaseChangeVO.setEndTime(entity.getEndTime());
        releaseChangeVO.setStatus(entity.getStatus());
        releaseChangeVO.setGmtCreate(entity.getGmtCreate());
        releaseChangeVO.setGmtModified(entity.getGmtModified());
        releaseChangeVO.setReason(entity.getReason());
        releaseChangeVO.setBizInfluence(entity.getBizInfluence());
        releaseChangeVO.setArchDomainRange(entity.getArchDomainRange());
        releaseChangeVO.setBusGroupRange(entity.getBusGroupRange());
        releaseChangeVO.setReasonType(entity.getReasonType());
        releaseChangeVO.setTargetTypes(entity.getChangeTargetType());

        return releaseChangeVO;
    }


}
