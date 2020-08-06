package com.yum.oa.common.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 用户权限
 * @description
 * @author: gaoyu
 * @create: 2020-08-06
 * @version: v2.0
 **/
public class Authority implements GrantedAuthority {
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String getAuthority() {
        return auth;
    }
}
