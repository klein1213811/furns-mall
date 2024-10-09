package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Order;

public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO{
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into `order`(id, create_time, price, status, member_id) values(?, ?, ?, ?, ?);";
        return update(sql, order.getId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getMemberId());
    }
}
