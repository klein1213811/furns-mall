package com.yt.furns.test;

import com.yt.furns.dao.impl.MemberDAO;
import com.yt.furns.dao.impl.MemberDAOImpl;
import com.yt.furns.javaBean.Member;
import org.junit.Test;

public class MemberDAOTest {
    private MemberDAO dao = new MemberDAOImpl();
    @Test
    public void getMemberTest(){
        Member admin = dao.getMemberByName("admin");
        if(admin != null){
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    @Test
    public void saveMemberTest(){
        Member member = new Member(null, "jay", "jay", "jay@gmail.com");
        if(dao.addMember(member) == 1){
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }

    @Test
    public void getMemberPwdTest(){
        Member member = dao.queryMemberByName("yt13138", "yt13138");
        if(member != null){
            System.out.println("Yes");
            System.out.println(member);
        } else {
            System.out.println("No");
        }
    }
}
