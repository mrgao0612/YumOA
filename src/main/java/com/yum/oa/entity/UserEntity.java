package com.yum.oa.entity;

import com.yum.oa.common.base.BaseEntity;

/**
 * @author: gaoyu
 * 对应数据库表： yum_user
 */
public class UserEntity extends BaseEntity {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}