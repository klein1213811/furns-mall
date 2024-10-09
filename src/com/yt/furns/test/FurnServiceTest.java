package com.yt.furns.test;

import com.yt.furns.javaBean.Furn;
import com.yt.furns.javaBean.Page;
import com.yt.furns.service.impl.FurnService;
import com.yt.furns.service.impl.FurnServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnServiceTest {
    private FurnService furnService = new FurnServiceImpl();
    @Test
    public void queryFurns(){
        List<Furn> furns = furnService.queryAllFurn();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void addFurn(){
        Furn furn = new Furn(null, "shafa~", "jiaju", new BigDecimal(999.99), 100, 10, "assets/images/product-image/14.jpg");
        furnService.addFurn(furn);
    }

    @Test
    public void deleteFurn(){
        furnService.deleteFurnById(11);
    }

    @Test
    public void queryFurnById(){
        Furn furn = furnService.queryFurnById(2);
        System.out.println(furn);
    }

    @Test
    public void page(){
        Page<Furn> page = furnService.page(1, 3);
        System.out.println(page);
    }

    @Test
    public void pageByName(){
        System.out.println(furnService.pageByName(1, 3, "Name"));
        System.out.println(1);
    }
}
