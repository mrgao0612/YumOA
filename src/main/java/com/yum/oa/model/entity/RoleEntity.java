package com.yum.oa.model.entity;

import com.yum.oa.common.base.BaseEntity;

/**
 * @author: gaoyu
 * 对应数据库表： yum_role
 */
public class RoleEntity extends BaseEntity {
    /** 角色编号 */
    private Integer roleCode;
    /** 角色名称 */
    private String roleName;
    /** 角色人数 */
    private Integer roleNum;

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(Integer roleNum) {
        this.roleNum = roleNum;
    }

}
