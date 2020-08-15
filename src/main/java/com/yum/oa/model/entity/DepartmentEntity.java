package com.yum.oa.model.entity;

import com.yum.oa.common.base.BaseEntity;

/**
 * @author: gaoyu
 * 对应数据库表： yum_department
 */
public class DepartmentEntity extends BaseEntity {
    /** 部门编码 */
    private Integer deptCode;
    /** 部门名称 */
    private String deptName;
    /** 父部门id */
    private Long parentId;
    /** 部门人数 */
    private Integer deptNum;

    public Integer getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Integer deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(Integer deptNum) {
        this.deptNum = deptNum;
    }

}
