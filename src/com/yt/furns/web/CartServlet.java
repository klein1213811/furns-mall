package com.yt.furns.web;

import com.google.gson.Gson;
import com.yt.furns.javaBean.Cart;
import com.yt.furns.javaBean.CartItem;
import com.yt.furns.javaBean.Furn;
import com.yt.furns.service.impl.FurnService;
import com.yt.furns.service.impl.FurnServiceImpl;
import com.yt.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cartServlet"})
public class CartServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    /**
     * 添加一个家居数据到购物车
     *
     * @param req
     * @param resp
     **/
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 得到添加家居的id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        // 获取对应id的Furn
        Furn furn = furnService.queryFurnById(id);

        // 根据Furn构建cartItem 点一次表示添加一个
        CartItem item = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        // 从session获取Cart
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        cart.addItem(item);

        // 添加完毕后返回添加家居的界面，使用请求投中的Referer
        resp.sendRedirect(req.getHeader("referer"));
    }

    /**
     * 优化addItem。使用Ajax的方式进行局部的刷新
     *
     * @param req
     * @param resp
     **/
    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 得到添加家居的id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        // 获取对应id的Furn
        Furn furn = furnService.queryFurnById(id);

        // 根据Furn构建cartItem 点一次表示添加一个
        CartItem item = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        // 从session获取Cart
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        cart.addItem(item);

        // 添加完毕后返回json数据返回给前端局部刷新
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("cartTotalCount", cart.getTotalCount());
        resp.getWriter().print(new Gson().toJson(resMap));
    }

    /**
     * 更新某个CartItem的数量，即更新购物车
     *
     * @param req
     * @param resp
     **/
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        int count = DataUtils.parseInt(req.getParameter("count"), 1);

        // 获取session的公务车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id, count);
        }

        // 回到更新后的页面
        resp.sendRedirect(req.getHeader("referer"));
    }

    /**
     * 根据id删除指定的item
     *
     * @param req
     * @param resp
     **/
    protected void delItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.delItem(id);
        }

        resp.sendRedirect(req.getHeader("referer"));
    }

    /**
     * 清空购物车
     *
     * @param req
     * @param resp
     **/
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
