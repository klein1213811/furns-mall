package com.yt.furns.test;

import com.yt.furns.javaBean.Cart;
import com.yt.furns.javaBean.CartItem;
import com.yt.furns.service.impl.OrderService;
import com.yt.furns.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderServiceTest {
    OrderService orderService = new OrderServiceImpl();
    private Cart cart = new Cart();
    @Test
    public void testSaveOrder() {
        cart.addItem(new CartItem(17, "Name", new BigDecimal(60), 2, new BigDecimal(120)));
        cart.addItem(new CartItem(18, "Name1", new BigDecimal(60), 2, new BigDecimal(120)));
        String orderId = orderService.saveOrder(cart, 2);
        System.out.println(orderId);
    }
}
