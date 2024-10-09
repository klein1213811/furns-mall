package com.yt.furns.javaBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

// 就是一个购物车，包含多个cartItem
public class Cart {
    // 包含多个CartItem对象，所以使用HashMap来保存
    private HashMap<Integer, CartItem> items = new HashMap<>();
    /** 修改指定的cartItem的数量和总价
     * @param id
     * @param count
     **/
    public void updateCount(int id, int count){
        CartItem item = items.get(id);
        if(item != null){
            // 先更新数量在更新总价
            item.setCount(count);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount()))); // 注意这里不能直接把count放进去
        }
    }
    public Integer getTotalCount() {
        // 这里的清空为0非常重要，因为这个函数是获取你加了几个
        int totalCount = 0;
        // 遍历Items，统计totalCount。遍历HashMap
        Iterator<Integer> iterator = items.keySet().iterator();
        while (iterator.hasNext()) {
            int itemId = iterator.next();
            CartItem item = items.get(itemId);
            totalCount += item.getCount();
        }

        return totalCount;
    }

    /**
     * 返回购物车的总价
     *
     * @return java.math.BigDecimal
     **/
    public BigDecimal getCartTotalPrice() {
        BigDecimal cartTotalPrice = new BigDecimal(0);
        // 遍历Items，统计cartTotalPrice。遍历HashMap
        for (int itemId : items.keySet()) {
            CartItem item = items.get(itemId);
            cartTotalPrice = cartTotalPrice.add(item.getTotalPrice());
        }
        return cartTotalPrice;
    }

    // 添加家居到Cart
    public void addItem(CartItem cartItem) {
        // 先判断是否存在
        CartItem item = items.get(cartItem.getId());
        if (item == null) { // 说明此时购物车中没有这个cartItem
            items.put(cartItem.getId(), cartItem);
        } else {
            item.setCount(item.getCount() + 1); // 如果有这个item。就在数量上 + 1
            // 修改总价
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }
    /** 根据传入的id，删除指定的购物车项
     * @param Id
     **/
    public void delItem(int Id) {
        items.remove(Id);
    }
    /** 清空
     **/
    public void clear(){
        // HashMap的clear不是将里面的内容置空，而是将hashMap的size置为0
        /**
         *     public void clear() {
         *         Node<K,V>[] tab;
         *         modCount++;
         *         if ((tab = table) != null && size > 0) {
         *             size = 0;⭐⭐⭐⭐
         *             for (int i = 0; i < tab.length; ++i)
         *                 tab[i] = null;
         *         }
         *     }
         * */
        items.clear();
    }
    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }

    public HashMap<Integer, CartItem> getItems() {
        return items;
    }
}
