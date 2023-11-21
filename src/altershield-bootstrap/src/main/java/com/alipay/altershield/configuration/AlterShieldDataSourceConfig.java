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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.configuration;

import com.alipay.altershield.common.logger.Loggers;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置类表数据源相关配置
 *
 * @author yuanji
 * @version : OpsCloudMetaDataSourceConfig.java, v 0.1 2022年03月04日 11:06 上午 yuanji Exp $
 */
@Configuration
@MapperScan(value = {
        "com.alipay.altershield.common.largefield.dal.mapper",
        "com.alipay.altershield.common.id.sequence.dal.mapper",
        "com.alipay.altershield.common.backconfig.dal.mapper",
        "com.alipay.altershield.common.ability.mapper",
        "com.alipay.altershield.change.meta.dal.mapper",
        "com.alipay.altershield.defender.meta.dal.mapper",
        "com.alipay.altershield.common.ability.dal.mapper",
        "com.alipay.altershield.change.search.dal.mapper",
        "com.alipay.altershield.smart.meta.dal.mapper",
        "com.alipay.altershield.smart.exe.dal.mapper"
},
        sqlSessionFactoryRef = "confSqlSessionFactory")
public class AlterShieldDataSourceConfig {
    private static final Logger logger = Loggers.DEFAULT;

    /**
     * Data source data source.
     *
     * @return the data source
     */
    @Bean(name = "confDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        logger.info("starting create config datasource");
        DataSource dataSource = DataSourceBuilder.create().build();
        logger.info("create sharding config success");
        return dataSource;
    }

    /**
     * Sql session factory sql session factory.
     *
     * @param dataSource the data source
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean(name = "confSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("confDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/config/mybatis-config.xml"));
        bean.setMapperLocations(resolveMapperLocations());
        return bean.getObject();
    }

    /**
     * Transaction manager data source transaction manager.
     *
     * @param dataSource the data source
     * @return the data source transaction manager
     */
    @Bean(name = "confTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("confDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Sql session template sql session template.
     *
     * @param sqlSessionFactory the sql session factory
     * @return the sql session template
     */
    @Bean(name = "confSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("confSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * Transaction template transaction template.
     *
     * @param dataSourceTransactionManager the data source transaction manager
     * @return the transaction template
     */
    @Bean(name = "confTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("confTransactionManager") DataSourceTransactionManager dataSourceTransactionManager) {
        return new TransactionTemplate(dataSourceTransactionManager);
    }

    /**
     * Resolve mapper locations resource [ ].
     *
     * @return the resource [ ]
     */
    public Resource[] resolveMapperLocations() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:mybatis/meta/mapper/*.xml");
        mapperLocations.add("classpath*:mybatis/exe/mapper/*.xml");
        mapperLocations.add("classpath*:mybatis/mapper/*.xml");
        List<Resource> resources = new ArrayList();
        mapperLocations.parallelStream().forEach(mapperLocation -> {
            try {
                Resource[] mappers = resourceResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (Exception ignored) {
                logger.error("create mapper locations error");
            }
        });
        return resources.toArray(new Resource[0]);
    }

}