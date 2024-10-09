package com.yt.furns.test;

import com.yt.furns.javaBean.Member;
import com.yt.furns.service.impl.MemberService;
import com.yt.furns.service.impl.MemberServiceImpl;
import org.junit.Test;

public class TestMemberService {
    private MemberService memberService = new MemberServiceImpl();
    @Test
    public void isExists() {
        if(memberService.isExistsUsername("admin")){
            System.out.println("user is exists");
        } else {
            System.out.println("user is not exists");
        }
    }

    @Test
    public void register() {
        Member member = new Member(null, "mary", "mary", "mary@gmail.com");
        if (memberService.registerMember(member)) {
            System.out.println("register success");
        } else {
            System.out.println("register failed");
        }
    }

    @Test
    public void login(){
        Member member = new Member(null, "yt13138", "yt13138", null);
        if(memberService.login(member) != null){
            System.out.println(member);
        } else {
            System.out.println("login failed");
        }
    }
}
