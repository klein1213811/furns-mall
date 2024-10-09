package com.yt.furns.dao.impl;


import com.yt.furns.utils.JDBC_Druid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

// 其他Dao的父类

public class BasicDAO<T> {
    private QueryRunner qr = new QueryRunner();

    //开发通用的DML针对任意的表

    /**1.update通用类
     * @param sql
     * @param params
     * @return int
     **/
    public int update(String sql, Object... params) {
        Connection conn = null;
        try {
            // 同一个请求(线程中)保证为同一个连接
            conn = JDBC_Druid.getConnection();
            return qr.update(conn, sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**2.select多行查询
     * @param sql 就是sql语句，可以有？
     * @param clazz 传入一个类的Class对象，比如Actor.class
     * @param params 传入?的具体的值，多个值之间用 ,
     * @return java.util.List<T>
     **/
    public List<T> queryMultiply(String sql, Class<T> clazz, Object... params) {
        Connection conn = null;
        try {
            conn = JDBC_Druid.getConnection();
            return qr.query(conn, sql, new BeanListHandler<T>(clazz), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3.select单行查询
     *
     * @param sql
     * @param clazz
     * @param params
     * @return T
     **/
    public T querySingle(String sql, Class<T> clazz, Object... params) {
        Connection conn = null;
        try {
            conn = JDBC_Druid.getConnection();
            return qr.query(conn, sql, new BeanHandler<T>(clazz), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**4.查询单行单列
     * @param sql
     * @param params
     * @return java.lang.Object
     **/
    public Object queryScalar(String sql, Object... params) {
        Connection conn = null;
        try {
            conn = JDBC_Druid.getConnection();
            return qr.query(conn, sql, new ScalarHandler<>(), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
