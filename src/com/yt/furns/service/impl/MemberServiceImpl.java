package com.yt.furns.service.impl;

import com.yt.furns.dao.impl.MemberDAO;
import com.yt.furns.dao.impl.MemberDAOImpl;
import com.yt.furns.javaBean.Member;

public class MemberServiceImpl implements MemberService {
    // 这个类中要存在一个DAO，这个DAO要是实现了MemberDAO接口的一个类
    private MemberDAO memberDAO = new MemberDAOImpl();
    /** 注册一个用户
     * @param member 输入一个Member对象
     * @return boolean 成功返回1
     **/
    @Override
    public boolean registerMember(Member member) {
        return memberDAO.addMember(member) == 1;
    }
    /** 判断用户名是否存在
     * @param username 用户名
     * @return boolean 存在返回true, 否则返回false
     **/
    @Override
    public boolean isExistsUsername(String username) {
        return memberDAO.getMemberByName(username) != null; // 如果存在就返回一个Member对象，不存在就返回null
    }

    /** 在登录功能中，我们谁将提交在表单中的信息创建一个新的Member对象，这里传进来的就是根据表单中的信息创造的对象
     * @param member 表单中提交的信息创造的对象，不一定存在于数据库
     * @return 返回的是对应的数据库中的Member，所以如果登录的用户存在于数据库，那么就返回，不存在就返回None
     **/
    @Override
    public Member login(Member member) {
        return memberDAO.queryMemberByName(member.getUsername(), member.getPassword());
    }
}
