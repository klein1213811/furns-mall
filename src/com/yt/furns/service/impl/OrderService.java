package com.yt.furns.service.impl;

import com.yt.furns.javaBean.Cart;
import com.yt.furns.javaBean.Order;

public interface OrderService {
    // 订单是由购物车来生成的，所以形参是cart cart对象在session,通过web层传入
    public String saveOrder(Cart cart, int memberId);
}
