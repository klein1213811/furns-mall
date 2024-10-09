package com.yt.furns.javaBean;

import java.math.BigDecimal;
import java.util.Date;

// 表示一个订单
public class Order {
    private String id;
    // 这里引入的Date的包要是util下的
    private Date createTime;
    private BigDecimal price;
    private Integer status;
    private Integer memberId;

    public Order() {
    }

    public Order(String id, Date createTime, BigDecimal price, Integer status, Integer memberId) {
        this.id = id;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", memberId=" + memberId +
                '}';
    }
}
