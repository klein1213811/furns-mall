package com.yt.furns.service.impl;

import com.yt.furns.javaBean.Furn;
import com.yt.furns.javaBean.Page;

import java.awt.*;
import java.util.List;

public interface FurnService {
    // 返回家居信息
    public List<Furn> queryAllFurn();

    public int addFurn(Furn furn);

    public int deleteFurnById(int furnId);

    public Furn queryFurnById(int furnId);

    public int updateFurn(Furn furn);

    /** 根据传入的begin和pageSize,返回对应的Page对象
     * @param pageNo 要显示的第几页
     * @param pageSize 显示的页数
     * @return com.yt.furns.javaBean.Page<com.yt.furns.javaBean.Furn>
     **/
    public Page<Furn> page(int pageNo, int pageSize);

    /** 根据传入的begin和pageSize, name 返回对应的Page对象
     * @param pageNo
     * @param pageSize
     * @param name
     * @return com.yt.furns.javaBean.Page<com.yt.furns.javaBean.Furn>
     **/
    public Page<Furn> pageByName(int pageNo, int pageSize, String name);
}
