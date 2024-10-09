package com.yt.furns.filter;

import com.google.gson.Gson;
import com.yt.furns.javaBean.Member;
import com.yt.furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
// 用于权限验证的过滤器，如果登录过就放行，没有登录就回到登录界面

/**
 * 将要验证的url分为三种情况
 * （1）不去拦截的不去配置，里面包括图片等
 * （2）对某些被拦截目录中要放行的资源，要在配置中指定
 */
@WebFilter(
        urlPatterns = {
                "/views/manage/*",
                "/views/cart/*",
                "/views/member/*",
                "/views/order/*",
                "/cartServlet",
                "/manage/furnServlet",
                "/orderServlet"
        },
        initParams = {
                @WebInitParam(name = "excludedUrls", value =
                        "/views/manage/manage_login.jsp," +  // 没有空格
                                "/views/member/login.jsp"
                )
        }
)
public class AuthFilter implements Filter {
    // 将要放行的资源设置为一个属性
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) {
        // 获取到配置的excludedUrls
        String strExcludedUrls = filterConfig.getInitParameter("excludedUrls");
        String[] spiltUrls = strExcludedUrls.split(",");
        // 将字符床数组转成List
        excludedUrls = Arrays.asList(spiltUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 得到请求的url
        HttpServletRequest req = (HttpServletRequest) servletRequest; // 将接口类型向下装换一下，以便于使用httpServlet的方法
        String url = req.getServletPath();

        // 判断是否要验证
        if (!excludedUrls.contains(url)) {

            // 得到session的member对象
            Member member = (Member) req.getSession().getAttribute("member");
            if (member == null) {
                // 判断是不是一个Ajax请求
                // 表示未登录，请求转发可以不用通过过滤器
                if (!WebUtils.isAjax(req)){
                    req.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest, servletResponse);
                } else {
                    // 返回一个url，按照json格式返回
                    HashMap<String, Object> resMap = new HashMap<>();
                    // 注意这个地址是浏览器解析的，所以前面不能带 /
                    resMap.put("url", "views/member/login.jsp");
                    servletResponse.getWriter().print(new Gson().toJson(resMap));
                }

                // 返回
                return;
            }
        }

        // 登录之后就继续执行
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
