package com.anrong.boot.util;

import java.util.List;

/**
 * 分页封装类
 *
 * @param <T>
 * @author liuxun
 */
public class Page<T> {
    /**
     * 总记录数
     */
    private Integer totalSize;
    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 当前页码
     */
    private Integer pageNo;

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * 每页显示多少条数据
     */
    private Integer pageSize;
    /**
     * 当前页的数据
     */
    private List<T> items;

    /**
     * 获取当前页起始行数
     */
    private Integer start;

    /**
     * @param pageNo    当前页码
     * @param pageSize  每页显示的数据量
     * @param totalSize 总记录数
     */
    public Page(Integer pageNo, Integer pageSize, Integer totalSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalSize = totalSize;

        // 计算总页数
        totalPages = totalSize / pageSize;
        if (totalSize % pageSize != 0) {
            totalPages++;
        }

        if (pageNo > totalPages) {
            this.pageNo = totalPages;
        }
        if (this.pageNo <= 0) {
            this.pageNo = 1;
        }

        start = (this.pageNo - 1) * pageSize;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getStart() {
        return start;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
