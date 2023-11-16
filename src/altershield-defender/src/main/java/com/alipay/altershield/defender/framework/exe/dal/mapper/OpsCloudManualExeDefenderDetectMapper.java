package com.alipay.altershield.defender.framework.exe.dal.mapper;

import com.alipay.opscloud.defender.exe.dal.dataobject.OpsCloudExeDefenderDetectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wb-zs909667
 * @version $Id: OpsCloudManualExeDefenderDetectMapper.java, v 0.1 2022年10月14日 18:12 wb-zs909667 Exp $
 */
@Mapper
public interface OpsCloudManualExeDefenderDetectMapper {

    /**
     * 查node下所有执行记录
     *
     * @param detectDO
     * @return
     */
    List<OpsCloudExeDefenderDetectDO> selectAllByNode(OpsCloudExeDefenderDetectDO detectDO);

    /**
     * 防御执行分页查询
     *
     * @param param
     * @return
     */
    long selectAllByNodeByPageCount(@Param("param") OpsCloudExeDefenderDetectDO param,
                                    @Param("controlKeys") List<String> controlKeys,
                                    @Param("controlKeysExc") List<String> controlKeysExc,
                                    @Param("custTag") String custTag);

    /**
     * @param param
     * @return
     */
    List<OpsCloudExeDefenderDetectDO> selectAllByNodeByPage(@Param("param") OpsCloudExeDefenderDetectDO param,
                                                            @Param("controlKeys") List<String> controlKeys,
                                                            @Param("controlKeysExc") List<String> controlKeysExc,
                                                            @Param("custTag") String custTag,
                                                            @Param("start") int start, @Param("offset") int offset);
}