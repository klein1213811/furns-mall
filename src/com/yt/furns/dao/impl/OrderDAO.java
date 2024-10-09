package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Order;

public interface OrderDAO {
    // 将传入的Order对象保存到数据库
    public int saveOrder(Order order);

}
