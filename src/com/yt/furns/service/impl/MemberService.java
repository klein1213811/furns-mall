package com.yt.furns.service.impl;

import com.yt.furns.javaBean.Member;

public interface MemberService {
    // 注册用户
    public boolean registerMember(Member member);

    // 判断用户名是否存在
    public boolean isExistsUsername(String username);

    // 登录
    public Member login(Member member);
}
