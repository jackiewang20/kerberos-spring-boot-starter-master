package com.example.common.bean;

/**
 * @author jackie
 * @Title: BaseBean
 * @ProjectName sprintboot-junit
 * @Description: 基础的通用bean，例如分页参数，版本号；分页必填参数：
 * 设置pageNow、pageSize，或者offset、pageSize。
 * @date 2018/11/19 15:29
 */
public class BaseBean {
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 从多少条开始分页查询
     */
    private Integer offset;
    /**
     * 当前页，第几页
     */
    private Integer pageNow;
    /**
     * 每页记录条数
     */
    private Integer pageSize;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
        initOffset();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        initOffset();
    }

    public Integer getOffset() {
        return offset;
    }

    private void initOffset() {
        if (pageNow == null || pageNow <= 0) {
            pageNow = 1;
        }

        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        offset = pageSize * (pageNow - 1);
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
