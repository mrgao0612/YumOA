package com.yum.oa.model.entity;

import com.yum.oa.common.base.BaseEntity;

/**
 * @author: gaoyu
 * 对应数据库表： yum_user
 */
public class UserEntity extends BaseEntity {
    /** 员工编号 */
    private Integer userCode;
    /** 用户名称 */
    private String username;
    /** 手机号码 */
    private String mobile;
    /** 用户头像 */
    private String avatar;
    /** 用户性别 0:男,1:女 */
    private String gender;
    /** 年龄 */
    private Byte age;
    /** 登录密码 */
    private String password;
    /** 所在部门主键 */
    private Long deptId;
    /** 所属角色主键 */
    private Long roleId;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
