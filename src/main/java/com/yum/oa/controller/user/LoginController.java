package com.yum.oa.controller.user;

import com.yum.oa.common.result.ResultBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * @description
 * @author: gaoyu
 * @create: 2020-08-08
 * @version: 0.0.1
 **/
@Api(tags = "登录管理")
@RestController
@RequestMapping("/api")
public class LoginController {

    @RequestMapping("login")
    public ResultBean<?> login() {
        return null;
    }
}
