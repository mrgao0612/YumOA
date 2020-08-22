package com.yum.oa.common.utils;

import com.yum.oa.common.security.JwtUser;
import com.yum.oa.common.security.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/20
 **/
@Component
public class UserUtil {
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.tokenStart}")
    private String tokenStart;
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 获取当前登录用户信息
     * description:
     * @param
     * @return:
     * @author: gaoyu
     * @date: 2020/8/20
     * @version
     */
    public JwtUser getCurrentUser() {
        String token = request.getHeader(header);
        if (StringUtils.isNotBlank(token)) {
            return jwtUtil.getUserFromToken(token.substring(tokenStart.length()));
        }
        return null;
    }
}
