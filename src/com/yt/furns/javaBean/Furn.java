package com.yt.furns.javaBean;

import java.math.BigDecimal;

// furn的javaBean与表映射
public class Furn {
    private Integer id; // 用Integer是防止空指针，自动装箱，还有很多的方法
    private String name;
    private String maker;
    private BigDecimal price;
    private Integer sales;
    private Integer stock;

    // 路径使用驼峰表示法，在表中我们的字段是img_path, 但是在这里我们使用的是imgPath，这时就需要额外的映射
    private String imgPath = "assets/images/product-image/default.jpg"; // 使用默认值

    public Furn() {
    }

    public Furn(Integer id, String name, String maker, BigDecimal price, Integer sales, Integer stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        if(!(imgPath == null || imgPath.isEmpty())){
            this.imgPath = imgPath; // 这里就是和BeanUtils配合使用
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
