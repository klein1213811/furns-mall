package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Admin;
import com.yt.furns.javaBean.Member;

public class AdminDAOImpl extends BasicDAO<Admin> implements AdminDAO{
    @Override
    public Admin getAdminByName(String username) {
        String sql = "select * from admin where username=?";
        return querySingle(sql, Admin.class, username);
    }

    @Override
    public int addAdmin(Admin admin) {
        String sql = "insert into admin values(null, ?, ?)";
        return update(sql, admin.getUsername(), admin.getPassword());
    }

    @Override
    public Admin queryAdminByName(String username, String password) {
        String sql = "select * from admin where username = ? and password = MD5(?)";
        return querySingle(sql, Admin.class, username, password);
    }
}
