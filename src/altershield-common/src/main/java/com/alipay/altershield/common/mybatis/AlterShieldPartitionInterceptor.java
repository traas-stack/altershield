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
package com.alipay.altershield.common.mybatis;

import com.alipay.altershield.common.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @author yuanji
 * @version : OpsCloudPartionInterceptor.java, v 0.1 2022年03月08日 7:53 下午 yuanji Exp $
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class AlterShieldPartitionInterceptor implements Interceptor {

    /**
     * sql中的占位符，会根据这个字段把 这个字段替换 AND uid= value
     */
    private static final String UID_PLACEHOLDER = "@uid_placeholder@";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();


        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());

        // 先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler
        // 的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // id为执行的mapper方法的全路径名，如com.cq.UserMapper.insertUser， 便于后续使用反射
        String id = mappedStatement.getId();
        // sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();


        BoundSql boundSql = statementHandler.getBoundSql();

        // 获取到原始sql语句
        String sql = boundSql.getSql().toLowerCase();
        //log.info("SQL：{}", sql);

        // 增强sql
        // 通过反射，拦截方法上带有自定义@InterceptAnnotation注解的方法，并增强sql
        String mSql = sqlAnnotationEnhance(id, sqlCommandType, sql, boundSql);


        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, mSql);
        //log.info("增强后的SQL：{}", mSql);
        return invocation.proceed();

    }

    private String sqlAnnotationEnhance(String id, String sqlCommandType, String sql,BoundSql boundSql) throws ClassNotFoundException {
        sqlCommandType = sqlCommandType; // NOPMD
        // 通过类全路径获取Class对象
        Class<?> classType = Class.forName(id.substring(0, id.lastIndexOf(".")));
        // 获取当前所拦截的方法名称
        String mName = id.substring(id.lastIndexOf(".") + 1);
        // 遍历类中所有方法名称，并if匹配上当前所拦截的方法
        for (Method method : classType.getDeclaredMethods()) {
            if (mName.equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Annotation[][] pa = method.getParameterAnnotations();
                for(int i = 0; i < parameterTypes.length; i++)
                {
                    Class<?> clazz = parameterTypes[i];
                    //只有字符串才处理
                    if(!String.class.equals(clazz))
                    {
                        continue;
                    }
                    for(Annotation annotation : pa[i])
                    {
                        if(annotation instanceof PartitionHolder)
                        {
                            Object paramObject = boundSql.getParameterObject();
                            String standardId = null;
                            if(paramObject instanceof  String)
                            {
                                standardId = (String)paramObject;
                            }
                            else if(paramObject instanceof Map)
                            {
                                Map<String,Object> paraMap = (Map<String, Object>) paramObject;
                                String key = findParamKey(pa[i]);
                                if(key != null)
                                {
                                    standardId = (String) paraMap.get(key);
                                }
                            }
                            if(StringUtils.isNoneBlank(standardId))
                            {
                                String uid = IdUtil.getUid(standardId);
                                if(StringUtils.isBlank(uid))
                                {
                                    throw new RuntimeException("invalidate sql" + boundSql);
                                }
                               return  sql.replace(UID_PLACEHOLDER," AND  uid = '" + uid + "' ");
                            }
                        }
                    }
                }
            }
        }
        return sql;
    }

    private String findParamKey(Annotation[] annotations)
    {
        for(Annotation annotation : annotations) {
            if (annotation instanceof Param) {
                return ((Param)annotation).value();
            }
        }
        return null;
    }



    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}