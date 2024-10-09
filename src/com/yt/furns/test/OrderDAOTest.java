package com.yt.furns.test;

import com.yt.furns.dao.impl.OrderDAO;
import com.yt.furns.dao.impl.OrderDAOImpl;
import com.yt.furns.javaBean.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDAOTest {
    private OrderDAO orderDAO = new OrderDAOImpl();
    @Test
    public void saveOrder() {
        Order order = new Order("sn00002", new Date(), new BigDecimal(100), 0, 2);
        orderDAO.saveOrder(order);
    }
}
