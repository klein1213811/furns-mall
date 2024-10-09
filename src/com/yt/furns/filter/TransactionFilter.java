package com.yt.furns.filter;

import com.yt.furns.utils.JDBC_Druid;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
/** 用于管理事务的过滤器
 * 使用/*表示对所有的请求进行事务的管理
 */
@WebFilter(urlPatterns = {"/*"})
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 前置代码
        try {
            // 这部分正常提交运行，上来直接放行，
            filterChain.doFilter(servletRequest, servletResponse);
            // 后置代码
            JDBC_Druid.commit(); // 运行完回到这里，在这里统一提交
        } catch (IOException e) {
            // 只有在try中发生异常就在这里回滚，所以这里必须保证异常是在这里捕获，所以之前的代码中需要将异常向上抛
            // 异常机制会参与业务逻辑
            JDBC_Druid.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
