package com.yt.furns.web;

import com.yt.furns.javaBean.Admin;
import com.yt.furns.javaBean.Member;
import com.yt.furns.service.impl.AdminService;
import com.yt.furns.service.impl.AdminServiceImpl;
import com.yt.furns.service.impl.MemberService;
import com.yt.furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends BasicServlet {
    AdminService adminService = new AdminServiceImpl();
    // 验证登录
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Admin admin = new Admin(null, username, password);

        // 判断是否存在
        if (adminService.login(admin) == null) {
            // 将登录错误放入req域中，因为这里是请求转发
            req.setAttribute("msg", "用户名或者密码错误");
            req.setAttribute("username", username);
            // 登录失败 页面转发登录界面
            req.getRequestDispatcher("/views/manage/manage_login.jsp").forward(req, resp);
            System.out.println(123);
        } else {
            // 登录成功
            req.getRequestDispatcher("/views/manage/manage_menu.jsp").forward(req, resp);
        }
    }
}
