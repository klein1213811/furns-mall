package com.yt.furns.web;

import com.google.gson.Gson;
import com.yt.furns.javaBean.Member;
import com.yt.furns.service.impl.MemberService;
import com.yt.furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(urlPatterns = {"/memberServlet"})
public class MemberServlet extends BasicServlet {
    MemberService memberService = new MemberServiceImpl();
    /** 处理登录
     * @param req
     * @param resp
     **/
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收表单提交的信息，并创建Member
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String code1 = req.getParameter("code1");
        Member member = memberService.login(new Member(null, username, password, null));

        // 验证验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY); // 拿到之后立即删除防止重复使用
        if (token != null && token.equals(code1)) {
            // 判断是否存在
            if (member == null) {
                // 将登录错误放入req域中，因为这里是请求转发
                req.setAttribute("msg", "用户名或者密码错误");
                req.setAttribute("username", username);
                // 登录失败 页面转发登录界面
                req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            } else {
                // 登录成功
                // 将得到的Member对象放入session
                req.getSession().setAttribute("member", member);
                req.getRequestDispatcher("/views/member/login_ok.jsp").forward(req, resp);
            }
        } else {
            // 验证码不通过
            req.setAttribute("msg", "验证码不正确");
            // 回显数据
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
        }
    }

    /** 注销登录
     * @param req
     * @param resp
     **/
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 销毁当前用户的session，然后重定向到index.jsp首页
        req.getSession().invalidate(); // 只是销毁当前的用户
        resp.sendRedirect(req.getContextPath());
    }

    /** 处理注册
     * @param req
     * @param resp
     **/
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户的注册信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code2 = req.getParameter("code2");

        // 验证验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY); // 拿到之后立即删除防止重复使用
        if (token != null && token.equals(code2)) {
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
        } else {
            // 验证码不通过
            req.setAttribute("msg2", "验证码不正确");
            // 回显数据
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            req.setAttribute("active", "register");
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
        }
    }

    /** 使用ajax是否在DB中
     * @param req
     * @param resp
     **/
    protected void isExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        boolean isExist = memberService.isExistsUsername(username);
        // 按json格式返回 {"isExist":true}
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("isExist", isExist);
        resp.getWriter().print(new Gson().toJson(resMap));
    }
}
