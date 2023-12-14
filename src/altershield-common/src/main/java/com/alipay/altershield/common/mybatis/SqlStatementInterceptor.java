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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.common.mybatis;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * sql 统计
 *
 * @author yuanji
 * @version : SqlStatementInterceptor.java, v 0.1 2022年07月11日 20:15 yuanji Exp $
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
public class SqlStatementInterceptor implements Interceptor {

    /**
     * sql超时阀值
     */
    private long slowSqlTime = 100;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long cost = System.currentTimeMillis() - startTime;
            if (cost > slowSqlTime) {
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                BoundSql boundSql = statementHandler.getBoundSql();
                String sql = boundSql.getSql();
                sql = sql.replace("\n", "").replace("\t", "").replaceAll("\\s+", " ");
                String log = String.format("SQL EXEC: [%s] COST:%d ms", sql, cost);
                AlterShieldLoggerManager.log("info", Loggers.DAO_DIGEST, log);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String value = properties.getProperty("slowSqlTime");
        if (StringUtils.isNotBlank(value)) {
            this.slowSqlTime = Long.valueOf(value);
        }
    }
}