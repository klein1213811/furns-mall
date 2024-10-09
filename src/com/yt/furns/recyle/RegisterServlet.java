package com.yt.furns.recyle;

import com.yt.furns.javaBean.Member;
import com.yt.furns.service.impl.MemberService;
import com.yt.furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private MemberService memberService = new MemberServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户的注册信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        //判断用户名是否存在
        if (!memberService.isExistsUsername(username)) {
            // 不存在就注册
            Member member = new Member(null, username, password, email);
            if (memberService.registerMember(member)) {
               // 转发到成功页面
                req.getRequestDispatcher("/views/member/register_ok.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("/views/member/register_fail.html").forward(req, resp);
            }
        } else {
            // 如果用户存在就返回注册页面
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
