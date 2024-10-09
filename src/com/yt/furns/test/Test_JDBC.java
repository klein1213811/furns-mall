package com.yt.furns.test;

import com.yt.furns.utils.JDBC_Druid;
import org.junit.Test;

import java.sql.Connection;

public class Test_JDBC {
    @Test
    public void getConnection() {
        Connection connection = JDBC_Druid.getConnection();
        System.out.println(connection);
        JDBC_Druid.close(null, null, connection);
    }
}
