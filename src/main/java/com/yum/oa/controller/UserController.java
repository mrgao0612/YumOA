package com.yum.oa.controller;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.entity.UserEntity;
import com.yum.oa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: v2.0
 **/
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("insert")
    @ApiOperation("新增用户")
    public ResultBean<?> insert(@RequestBody UserEntity userEntity) {
        return userService.saveUser(userEntity);
    }

    @GetMapping("list")
    @ApiOperation("用户列表")
    public ResultBean<?> list(Object param) {
        return userService.findPageList(param);
    }
}
