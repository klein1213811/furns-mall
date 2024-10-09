package com.yt.furns.test;

import com.yt.furns.dao.impl.FurnDAO;
import com.yt.furns.dao.impl.FurnDAOImpl;
import com.yt.furns.javaBean.Furn;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnDAOTest {
    private FurnDAO furnDAO = new FurnDAOImpl();
    @Test
    public void queryFurns(){
        List<Furn> furns = furnDAO.queryAllFurn();
        for(Furn furn : furns){
            System.out.println(furn);
        }
    }

    @Test
    public void add(){
        Furn furn = new Furn(null, "shafa", "jiaju", new BigDecimal(999.99), 100, 10, "assets/images/product-image/14.jpg");
        furnDAO.addFurn(furn);
    }

    @Test
    public void update(){
        Furn furn = new Furn(6, "123", "123", new BigDecimal(123), 123, 123, "123");
        furnDAO.updateFurn(furn);
    }

    @Test
    public void getByName(){
        System.out.println(furnDAO.getTotalFurnByName("Name"));
    }

    @Test
    public void getItemsByName(){
        System.out.println(furnDAO.getPageItemsByName(0, 3, "Name"));
    }
}
