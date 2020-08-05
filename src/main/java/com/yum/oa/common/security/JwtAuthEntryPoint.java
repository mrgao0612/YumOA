package com.yum.oa.common.security;

import com.yum.oa.common.result.ResultCode;
import com.yum.oa.common.result.ResultGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证失败处理组件，返回401
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
@Component("JwtAuthEntryPoint")
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = ResultGenerator.failed(ResultCode.UNAUTHORIZED, e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
