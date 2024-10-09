package com.yt.furns.service.impl;

import com.yt.furns.dao.impl.FurnDAO;
import com.yt.furns.dao.impl.FurnDAOImpl;
import com.yt.furns.javaBean.Furn;
import com.yt.furns.javaBean.Page;

import java.util.List;

public class FurnServiceImpl implements FurnService {
    private FurnDAO furnDAO = new FurnDAOImpl();

    /**
     * 定义属性 FurnDAO对象
     *
     * @return java.util.List<com.yt.furns.javaBean.Furn>
     **/
    @Override
    public List<Furn> queryAllFurn() {
        return furnDAO.queryAllFurn();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurnById(int furnId) {
        return furnDAO.deleteFurnById(furnId);
    }

    @Override
    public Furn queryFurnById(int Id) {
        return furnDAO.queryFurnById(Id);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        // 先创建一个Page对象，然后根据实际情况填充属性
        Page<Furn> furnPage = new Page<>();
        furnPage.setPageNo(pageNo);
        furnPage.setPageSize(pageSize);
        int totalFurn = furnDAO.getTotalFurn();
        furnPage.setTotalCount(totalFurn);
        // 计算总页数
        int totalPage = totalFurn / pageSize;
        if (totalFurn % pageSize > 0) {
            totalPage++;
        }
        furnPage.setTotalPages(totalPage);

        // items
        int begin = (pageNo - 1) * pageSize;
        furnPage.setItems(furnDAO.getPageItems(begin, pageSize));

        return furnPage;
    }

    @Override
    public Page<Furn> pageByName(int pageNo, int pageSize, String name) {
        // 先创建一个Page对象，然后根据实际情况填充属性
        Page<Furn> furnPage = new Page<>();
        furnPage.setPageNo(pageNo);
        furnPage.setPageSize(pageSize);
        int totalFurn = furnDAO.getTotalFurnByName(name); // 根据名字获取总的记录数
        furnPage.setTotalCount(totalFurn);
        // 计算总页数
        int totalPage = totalFurn / pageSize;
        if (totalFurn % pageSize > 0) {
            totalPage++;
        }
        furnPage.setTotalPages(totalPage);

        // items
        int begin = (pageNo - 1) * pageSize;
        furnPage.setItems(furnDAO.getPageItemsByName(begin, pageSize, name));

        return furnPage;
    }
}
