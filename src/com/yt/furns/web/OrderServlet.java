package com.yt.furns.web;

import com.yt.furns.javaBean.Cart;
import com.yt.furns.javaBean.Member;
import com.yt.furns.service.impl.OrderService;
import com.yt.furns.service.impl.OrderServiceImpl;
import com.yt.furns.utils.JDBC_Druid;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/orderServlet"})
public class OrderServlet extends BasicServlet {
    private OrderService orderService = new OrderServiceImpl();

    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取购物车,
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        // 由于HashMap的clear是将size置为空
        // 补充逻辑，购物车为空，或者里面的值为空
        if (cart == null || cart.getItems().isEmpty()) {
            // 说明没有购买，所以就转发到首页
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        // 获取当前member的登录id
        Member member = (Member) req.getSession().getAttribute("member");
        if(member == null) {
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            return;
        }

        // 生成订单
        // 如果只是希望对orderService.saveOrder进行事务控制，那么在这个位置直接提交和回滚即可
        String orderId = orderService.saveOrder(cart, member.getId());

        // 将订单号放入session
        req.getSession().setAttribute("orderId", orderId);

        //使用重定向到checkout.jsp
        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");
    }
}
