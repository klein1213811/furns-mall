package com.yt.furns.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 使用Druid连接池来创建连接工具类
public class JDBC_Druid {

    private static DataSource ds;
    // 定义属性ThreadLocal，这里存放一个Connection
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    // 静态代码块完成初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(JDBC_Druid.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static Connection getConnection(){
//        try {
//            return ds.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 从ThreadLocal中获取connection，从而保证在同一个线程中，获取的是同一个Connection
     *
     * @return java.sql.Connection
     **/
    public static Connection getConnection() {
        Connection connection = threadLocalConn.get();
        if (connection == null) {
            // 说明当前的threadLocalConn没有连接,那么就从数据库连接池中取出一个连接放进去,下次取的时候就是同一个连接了
            try {
                connection = ds.getConnection();
                connection.setAutoCommit(false); // 让本次连接取消自动提交⭐⭐⭐⭐⭐
                threadLocalConn.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
    /** 在过滤器中的提交事务方法
     **/
    public static void commit(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                // 如果连接不为空，就提交
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    // 将这个连接放回连接池
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 当提交后，需要将connection从threadLocalConn中删除掉，否则会造成
        // threadLocalConn长期持有该连接
        threadLocalConn.remove();
    }

    /** 跟连接相关的所有操作都要回滚
     **/
    public static void rollback(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        threadLocalConn.remove();
    }
    public static void close(ResultSet set, Statement statement, Connection connection) {
        try {
            if (set != null) {
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
}
