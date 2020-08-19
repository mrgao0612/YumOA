package com.yum.oa.controller;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.dto.LoginDto;
import com.yum.oa.model.vo.LoginInVo;
import com.yum.oa.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public ResultBean<?> login(@Valid @RequestBody LoginInVo param) {
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(param, loginDto);
        return loginService.login(loginDto);
    }
}
