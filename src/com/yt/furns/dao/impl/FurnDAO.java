package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Furn;

import java.util.List;

public interface FurnDAO {
    // 返回所有家居，后面再考虑分页
    public List<Furn> queryAllFurn();

    // 将传入的Furn对象保存到DB
    public int addFurn(Furn furn);

    // 删除DB中对应的家居
    public int deleteFurnById(int id);

    public Furn queryFurnById(int id);

    //
    public int updateFurn(Furn furn);

    // page的哪些属性是可以从数据库中获取的就放到DAO层
    public int getTotalFurn();

    // 获取当前页要显示的furn数据 start 和 end表示从第几条数据开始获取，默认从0开始
    public List<Furn> getPageItems(int begin, int pageSize);

    // 根据名字获取总记录数
    public int getTotalFurnByName(String furnName);

    // 根据begin, pageSize，和Name来获取要显示的家居信息
    public List<Furn> getPageItemsByName(int begin, int pageSize,  String furnName);
}
