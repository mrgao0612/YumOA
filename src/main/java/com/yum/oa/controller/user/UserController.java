package com.yum.oa.controller.user;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public ResultBean<?> insert(@Valid @RequestBody UserEntity userEntity) {
        return userService.saveUser(userEntity);
    }

    @GetMapping("list")
    @ApiOperation("用户列表")
    public ResultBean<PageInfo<UserEntity>> list(Object param) {
        return userService.findPageList(param);
    }
}
