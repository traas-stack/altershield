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
package com.alipay.altershield.common.backconfig.dal.mapper;

import com.alipay.altershield.common.backconfig.dal.dataobject.MetaConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MetaConfigMapper {


    /**
     * Insert string.
     *
     * @param metaConfigDO the meta config do
     * @return the string
     */
    public String insert(MetaConfigDO metaConfigDO);


    /**
     * Delete by name int.
     *
     * @param name the name
     * @return the int
     */
    public int deleteByName(String name);


    /**
     * Lock by name meta config do.
     *
     * @param name the name
     * @return the meta config do
     */
    public MetaConfigDO lockByName(String name);


    /**
     * Select by name meta config do.
     *
     * @param name the name
     * @return the meta config do
     */
    public MetaConfigDO selectByName(String name);


    /**
     * Save by name int.
     *
     * @param opscldConfig the opscld config
     * @return the int
     */
    public int saveByName(MetaConfigDO opscldConfig);

    /**
     * Select total count long.
     *
     * @return the long
     */
    public long selectTotalCount();


    /**
     * Select by page list.
     *
     * @param name  the name
     * @param start the start
     * @param size  the size
     * @return the list
     */
    public List<MetaConfigDO> selectByPage(@Param("name") String name, @Param("start") int start, @Param("size") int size);

}