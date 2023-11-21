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
//package com.alipay.altershield.defender.changewindow.repository.impl;
//
//import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleaseChangeDO;
//import com.alipay.opscloud.changewindow.dal.dataobject.OpscldMetaReleasechangeParam;
//import com.alipay.opscloud.changewindow.dal.mapper.OpscldMetaReleasechangeMapper;
//import com.alipay.opscloud.changewindow.entity.MetaReleaseChangeEntity;
//import com.alipay.opscloud.changewindow.repository.MetaReleaseChangeRepository;
//import com.alipay.opscloud.changewindow.repository.convert.MetaReleaseChangeEntityConvertor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * 破网相关
// *
// * @author wb-zs909667
// * @version : MetaReleaseChangeRepository.java, v 0.1 2023-03-15 15:12 wb-zs909667 Exp $$
// */
//@Repository
//public class MetaReleaseChangeRepositoryImpl implements MetaReleaseChangeRepository {
//
//    @Autowired
//    private OpscldMetaReleasechangeMapper opscldMetaReleasechangeMapper;
//
//    @Override
//    public List<MetaReleaseChangeEntity> selectByPage(OpscldMetaReleasechangeParam param) {
//        return MetaReleaseChangeEntityConvertor.convert2ModelList(opscldMetaReleasechangeMapper.selectByParam(param));
//    }
//
//    @Override
//    public long selectTotalCount(OpscldMetaReleasechangeParam param) {
//        return opscldMetaReleasechangeMapper.countByParam(param);
//    }
//
//    @Override
//    public boolean deleteById(String id) {
//        return opscldMetaReleasechangeMapper.deleteByPrimaryKey(id) > 0;
//    }
//
//    @Override
//    public boolean updateReleaseChangeStatus(String status, String id) {
//        OpscldMetaReleaseChangeDO record = new OpscldMetaReleaseChangeDO();
//        record.setStatus(status);
//        record.setId(id);
//        return opscldMetaReleasechangeMapper.updateByPrimaryKeySelective(record) > 0;
//    }
//}
