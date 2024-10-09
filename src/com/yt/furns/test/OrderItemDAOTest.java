package com.yt.furns.test;

import com.yt.furns.dao.impl.OrderItemDAO;
import com.yt.furns.dao.impl.OrderItemDAOImpl;
import com.yt.furns.javaBean.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderItemDAOTest {
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    @Test
    public void saveOrderItem() {
        orderItemDAO.saveOrderItem(new OrderItem(null, "name", new BigDecimal("100.00"), 2, new BigDecimal(400), "sn00002"));
    }
}
