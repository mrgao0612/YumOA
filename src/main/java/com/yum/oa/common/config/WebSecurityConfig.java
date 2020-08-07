package com.yum.oa.common.config;

import com.yum.oa.common.security.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * web请求鉴权配置组件
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthTokenFilter jwtAuthTokenFilter;
    @Qualifier(value = "JwtAuthEntryPoint")
    @Resource
    private AuthenticationEntryPoint authEntryPoint;
    @Qualifier(value = "WebAuthAccessDeniedHandler")
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Qualifier(value = "CustomUserDetailsService")
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    public void configureAuth(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 使用Jwt，禁用csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                // 基于token,不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许匿名访问的接口
                .antMatchers("/api/login", "/api/signout", "/error/**").permitAll()
                // 阻止POST预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 除上面的接口都需要验证
                .anyRequest().authenticated();
        // 禁用缓存
        security.headers().cacheControl();
        // 添加Jwt过滤组件
        security.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/v2/api-docs",//swagger api json
                "/webjars/**",
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
