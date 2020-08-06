package com.yum.oa.common.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * Jwt认证用户
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUser implements UserDetails {
    private final long userId;
    private final String userName;
    private final String password;
    private final Set<Authority> authorities;

    public JwtUser(long userId, String userName, String password, Set<Authority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
