package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Member;

public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO{

    /** 根据用户名查询表中是否存在对象
     * @param username 用户名
     * @return 存在返回Member，不存在返回null
     **/
    @Override
    public Member getMemberByName(String username) {
        // 现在sqlyog中测试
        String sql = "select * from member where username = ?";
        return querySingle(sql, Member.class, username);
    }

    /**
     * @param member 传入Member对象，
     * @return 返回-1就是失败，返回其他的数字就是受影响的行数
     **/
    @Override
    public int addMember(Member member) {
        String sql = "insert into member values(null,?,MD5(?),?)";
        return update(sql, member.getUsername(), member.getPassword(), member.getEmail());
    }

    /** 根据用户名和密码返回一个对象
     * @param username 用户名
     * @param password 密码
     * @return 返回一个Member，如果不存在就返回null
     **/
    @Override
    public Member queryMemberByName(String username, String password) {
        String sql = "select * from member where username = ? and password = MD5(?)";
        return querySingle(sql, Member.class, username, password);
    }
}
