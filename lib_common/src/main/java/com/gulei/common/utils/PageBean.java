package com.gulei.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gl152 on 2018/6/15.
 * 用于分页操作的实体类
 */

public class PageBean<T> {
    public static final int FirstPage = 1;
    private int currentPage = FirstPage;
    private boolean hasNextPage = true;
    private int pageSize = 10;//默认加载10个
    private List<T> data;

    public PageBean(int pageSize) {
        this.pageSize = pageSize;
        this.data = new ArrayList<>();
    }

    public void autoIncrementCurrentPage() {
        currentPage++;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public void noNextPage() {
        hasNextPage = false;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void clear() {
        hasNextPage = true;
        currentPage = FirstPage;
        data.clear();
    }

}
