package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Admin;
import com.yt.furns.javaBean.Member;

public interface AdminDAO {
    // 提供一个根据用户名返回一个对应Member的方法
    public Admin getAdminByName(String username);
    // 保存一个Member对象到数据库，返回1表示成功，返回-1表示失败
    public int addAdmin(Admin admin);
    // 从表中获取用户的密码和
    public Admin queryAdminByName(String username, String password);
}
