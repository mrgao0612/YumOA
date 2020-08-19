package com.yum.oa.common.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 用户权限
 * @description
 * @author: gaoyu
 * @create: 2020-08-15
 * @version: 0.0.1
 **/
public final class Authority implements GrantedAuthority {
    private String authority;

    public Authority() {}

    private Authority(String authority) {
        this.authority = authority;
    }

    public static Authority getInstance(String authority) {
        return new Authority(authority);
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
