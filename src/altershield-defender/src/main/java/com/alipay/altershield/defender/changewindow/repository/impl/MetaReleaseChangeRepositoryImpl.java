package com.alipay.altershield.defender.changewindow.repository.impl;

import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleaseChangeDO;
import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
import com.alipay.opscloud.changewindow.dal.mapper.OpscldMetaReleasechangeMapper;
import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;
import com.alipay.opscloud.changewindow.repository.MetaReleaseChangeRepository;
import com.alipay.opscloud.changewindow.repository.convert.MetaReleaseChangeEntityConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 破网相关
 *
 * @author wb-zs909667
 * @version : MetaReleaseChangeRepository.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
 */
@Repository
public class MetaReleaseChangeRepositoryImpl implements MetaReleaseChangeRepository {

    @Autowired
    private OpscldMetaReleasechangeMapper opscldMetaReleasechangeMapper;

    @Override
    public List<MetaReleaseChangeEntity> selectByPage(OpscldMetaReleasechangeParam param) {
        return MetaReleaseChangeEntityConvertor.convert2ModelList(opscldMetaReleasechangeMapper.selectByParam(param));
    }

    @Override
    public long selectTotalCount(OpscldMetaReleasechangeParam param) {
        return opscldMetaReleasechangeMapper.countByParam(param);
    }

    @Override
    public boolean deleteById(String id) {
        return opscldMetaReleasechangeMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean updateReleaseChangeStatus(String status, String id) {
        OpscldMetaReleaseChangeDO record = new OpscldMetaReleaseChangeDO();
        record.setStatus(status);
        record.setId(id);
        return opscldMetaReleasechangeMapper.updateByPrimaryKeySelective(record) > 0;
    }
}
