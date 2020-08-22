package com.yum.oa.common.base;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/21
 **/
public class BasePageVars {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
