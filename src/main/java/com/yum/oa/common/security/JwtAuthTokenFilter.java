package com.yum.oa.common.security;

import com.yum.oa.common.exception.ExpirationTokenException;
import com.yum.oa.common.exception.InvalidTokenException;
import com.yum.oa.common.redis.RedisKeyGenerator;
import com.yum.oa.common.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器，验证token是否有效
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.tokenStart}")
    private String tokenStart;

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(header);
        JwtUser user;
        if (!StringUtils.isEmpty(authToken) && authToken.startsWith(tokenStart)) {
            authToken = authToken.substring(tokenStart.length());
            user = jwtUtil.getUserFromToken(authToken);

            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String cacheToken = redisUtil.get(RedisKeyGenerator.getUserToken(user.getUserId()));
                if (StringUtils.isEmpty(cacheToken) || !cacheToken.equals((tokenStart + authToken))) {
                    throw new InvalidTokenException("无效的token");
                }
                if (jwtUtil.isTokenExpired(authToken)) {
                    throw new ExpirationTokenException("token已过期");
                }
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, authToken, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
