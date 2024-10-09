package com.yt.furns.javaBean;

import java.util.List;

/** Page是一个分页的数据模型，里面包含了分页的各种信息
 * 将来分页的模型对应的数据类型是不确定的，所以这里使用泛型
 * */
public class Page<T> {
    // 因为每页显示多少条记录，在其他地方可能也要使用
    public static final Integer PAGE_SIZE = 3;

    private Integer pageNo; // 显示第几页
    private Integer pageSize = PAGE_SIZE;// 表示美业显示几条记录
    private Integer totalCount; // 表示共有多少记录
    private Integer totalPages; // 共有多少页
    private List<T> items; // 表示当前页要显示的数据
    private String url; // 分页导航

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
