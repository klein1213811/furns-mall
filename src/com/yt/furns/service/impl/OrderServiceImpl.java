package com.yt.furns.service.impl;

import com.yt.furns.dao.impl.*;
import com.yt.furns.javaBean.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private FurnDAO furnDAO = new FurnDAOImpl();
    @Override
    public String saveOrder(Cart cart, int memberId) {
        // 加cart购物车中的数据以order和orderItem的形式保存到DB中
        // （1）根据cart对象，构建对应的Order对象, 先生成一个UUID表示订单号，这个订单号是唯一的
        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order = new Order(orderId, new Date(), cart.getCartTotalPrice(), 0, memberId);
        orderDAO.saveOrder(order); // 保存order到数据表

        // 因为生成订单会操作多表，因此会涉及到多表事务的问题，此时需要ThreadLocal + mysql事务机制 + 过滤器

        // （2）通过cart对像，遍历出cartItem，构建orderItem对象，并保存到响应的OrderItem表中
        HashMap<Integer, CartItem> items = cart.getItems();
        Set<Integer> integers = items.keySet();
        for (Integer itemId : integers) {
            CartItem item = items.get(itemId);
            OrderItem orderItem = new OrderItem(null, item.getName(), item.getPrice(), item.getCount(), item.getTotalPrice(), orderId);
            orderItemDAO.saveOrderItem(orderItem);

            // （3）更新furn的sales和stock这两个字段，销量增加，存量减少
            // 获取furn对象
            Furn furn = furnDAO.queryFurnById(itemId);
            // 更新sales和stock
            furn.setSales(furn.getSales() + item.getCount());
            furn.setStock(furn.getStock() - item.getCount());
            // 更新furn到数据表

            furnDAO.updateFurn(furn);
        }

        // 清空购物车,注意这里只是将购物车中的内容清空，但是购物车这个cart对象还存在在session中，此时如果再生成订单还是会成功，但是里面没有东西
        cart.clear();
        return orderId;
    }
}
