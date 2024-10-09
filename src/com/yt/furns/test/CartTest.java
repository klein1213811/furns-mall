package com.yt.furns.test;

import com.yt.furns.javaBean.Cart;
import com.yt.furns.javaBean.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

public class CartTest {
    private Cart cart = new Cart();
    @Test
    public void addItem(){
        cart.addItem(new CartItem(1, "沙发", new BigDecimal(10), 2, new BigDecimal(20)));
        cart.addItem(new CartItem(2, "黑沙发", new BigDecimal(20), 2, new BigDecimal(40)));
        System.out.println(cart);
    }
}
