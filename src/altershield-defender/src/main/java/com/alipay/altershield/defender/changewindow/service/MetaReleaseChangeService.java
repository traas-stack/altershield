package com.alipay.altershield.defender.changewindow.service;

import com.alipay.opscloud.changewindow.service.request.MetaReleaseChangeRequest;
import com.alipay.opscloud.changewindow.service.request.QueryReleaseChangeInPageRequest;
import com.alipay.opscloud.changewindow.vo.MetaReleaseChangeVO;
import com.alipay.opscloud.common.result.OpsCloudPageResult;

import java.util.List;

/**
 * 破网相关
 *
 * @author wb-zs909667
 * @version : MetaReleaseChangeService.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
public interface MetaReleaseChangeService {

    /**
     * 分页查询路障信息
     *
     * @param request
     * @return
     */
    OpsCloudPageResult<List<MetaReleaseChangeVO>> pageQuery(QueryReleaseChangeInPageRequest request);

    /**
     * 根据bizBarrierId删除
     *
     * @param id
     * @return
     */
    String deleteById(String id);

    /**
     *
     * @param request
     * @return
     */
    Boolean updateReleaseChangeStatus(MetaReleaseChangeRequest request);
}
