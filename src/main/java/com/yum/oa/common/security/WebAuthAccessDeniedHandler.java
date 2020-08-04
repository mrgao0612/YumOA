package com.yum.oa.common.security;

import com.yum.oa.common.result.ResultCode;
import com.yum.oa.common.result.ResultGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限不足处理组件，返回403
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
@Component
public class WebAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = ResultGenerator.failed(ResultCode.FORBIDDEN, e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
