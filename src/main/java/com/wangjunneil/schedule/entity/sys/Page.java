package com.wangjunneil.schedule.entity.sys;

import java.util.List;

/**
 *
 * Created by wangjun on 8/4/16.
 */
public class Page<T> {
    private int currentPage;
    private int pageSize = 20;
    private long totalNum;
    private int totalPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private List<T> pageDataList;

    public Page() { }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
        // 设置总记录数时即把总页数进行计算
        this.totalPage = (int)(totalNum % pageSize > 0 ? totalNum / pageSize + 1 : totalNum / pageSize);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean isHasNextPage() {
        this.hasNextPage = currentPage < totalPage;
        return hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public List<T> getPageDataList() {
        return pageDataList;
    }

    public void setPageDataList(List<T> pageDataList) {
        this.pageDataList = pageDataList;
    }
}
