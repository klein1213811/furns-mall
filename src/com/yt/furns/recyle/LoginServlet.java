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

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    MemberService memberService = new MemberServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收表单提交的信息，并创建Member
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Member member = new Member(null, username, password, null);

        // 判断是否存在
        if(memberService.login(member) == null){
            // 将登录错误放入req域中，因为这里是请求转发
            req.setAttribute("msg", "用户名或者密码错误");
            req.setAttribute("username", username);
            // 登录失败 页面转发登录界面
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
        } else {
            // 登录成功
            req.getRequestDispatcher("/views/member/login_ok.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
