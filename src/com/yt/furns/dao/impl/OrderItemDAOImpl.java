package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.OrderItem;

public class OrderItemDAOImpl extends BasicDAO<OrderItem> implements OrderItemDAO {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into order_item(id, name, price, `count`, total_price, order_id) values(?, ?, ?, ?, ?, ?)";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getPrice(), orderItem.getCount(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
