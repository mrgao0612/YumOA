package com.yum.oa.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登录身份认证
 * @className CustomUserDetailsService
 * @Description
 * @Author mrgao0612@gmail.com
 * @Date 2020/8/4 20:50
 * @Version 0.0.1
 **/
@Component("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public JwtUser loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
