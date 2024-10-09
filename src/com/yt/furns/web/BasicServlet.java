package com.yt.furns.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

// 这里不用配置
public abstract class BasicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8"); // 解决中文乱码
        String action = req.getParameter("action"); // 这里的action和值要子类中的方法名保持一致
        try {
            // 使用反射获取当前对象的方法, 这个this就是继承BasicServlet的子类Servlet
            // 这里的方法就是子类中action中的对应的方法名的那个函数，这个函数根据this的不同而变化
            Method declaredMethod = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.invoke(this, req, resp); // 调用函数
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
