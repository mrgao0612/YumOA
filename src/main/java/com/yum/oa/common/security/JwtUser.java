package com.yum.oa.common.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Jwt认证用户
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUser implements UserDetails {
    private long userId;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(long userId, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
