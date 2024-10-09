package com.yt.furns.service.impl;

import com.yt.furns.javaBean.Admin;
import com.yt.furns.javaBean.Member;

public interface AdminService {
    // 注册用户
    public boolean registerAdmin(Admin admin);

    // 判断用户名是否存在
    public boolean isExistsUsername(String username);

    // 登录
    public Admin login(Admin admin);
}
