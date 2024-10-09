package com.yt.furns.dao.impl;

import com.yt.furns.javaBean.Furn;

import java.util.List;

public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {
    /**
     * 查询所有的家居
     *
     * @return java.util.List<com.yt.furns.javaBean.Furn>
     **/
    @Override
    public List<Furn> queryAllFurn() {
        // 这里不要用 select * from 原因就是可能需要别名，比如下面的img_path和imgPath
        String sql = "select id, name, maker, price, sales, stock, img_path imgPath from furn"; // 这里不要用 select * from

        return queryMultiply(sql, Furn.class);
    }

    /** 将传入的Furn对象保存到DB
     * @param furn 传入的furn对象
     * @return int 受影响的函数
     **/
    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn(`id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path`) VALUES(NULL , ? , ? , ? , ? , ? , ?)";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurnById(int id) {
        String sql = "DELETE FROM furn WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(int id) {
        String sql = "select id, name, maker, price, sales, stock, img_path imgPath from furn where id = ?";
        return querySingle(sql, Furn.class, id);
    }

    /** 将在浏览器中的进行修改的对象更新到数据库, 根据id来更新
     * @param furn
     * @return int
     **/
    @Override
    public int updateFurn(Furn furn) {
        String sql = "update furn set name = ?, maker = ?, price = ?, sales = ?, stock = ?, img_path = ? where id = ?";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgPath(), furn.getId());
    }

    @Override
    public int getTotalFurn() {
        String sql = "select count(*) from furn";
//        return (Integer) queryScalar(sql);
        // 这里不能这么写，会有隐患
        return ((Number) queryScalar(sql)).intValue();
    }

    /**
     * @param begin 表示从第几条数据开始取
     * @param pageSize 表示取出多少条记录
     * @return java.util.List<com.yt.furns.javaBean.Furn>
     **/
    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {
        String sql = "select id, name, maker, price, sales, stock, img_path imgPath from furn Limit ?, ?";
        return queryMultiply(sql, Furn.class, begin, pageSize);
    }

    @Override
    public int getTotalFurnByName(String furnName) {
        // 模糊查询：like %沙发%  这样检索就会将名字中带有沙发的名字全部检索出来
        String sql = "select count(*) from furn where name like ?";
        return ((Number)queryScalar(sql, "%" + furnName + "%")).intValue();
    }

    @Override
    public List<Furn> getPageItemsByName(int begin, int pageSize, String furnName) {
        String sql = "select id, name, maker, price, sales, stock, img_path imgPath from furn where name like ? Limit ?, ?";
        return queryMultiply(sql, Furn.class, "%" + furnName + "%", begin, pageSize);
    }
}
