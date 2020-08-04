package com.yum.oa.common.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器，验证token是否有效
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.tokenStart}")
    private String tokenStart;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(header);
        if (!StringUtils.isEmpty(authToken) && authToken.startsWith(tokenStart))
            authToken = authToken.substring(tokenStart.length());
        else authToken = null;

        JwtUser user = JwtUtil.getUserFromToken(authToken);
        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (JwtUtil.validateToken(authToken, user)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
