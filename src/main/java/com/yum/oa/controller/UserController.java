package com.yum.oa.controller;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.model.vo.UserInfoOutVo;
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
 * @version: 0.0.1
 **/
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("根据用户ID获取用户详情")
    @GetMapping("/getUserInfoById/{id}")
    public ResultBean<UserInfoOutVo> getUserInfoById(@Valid @PathVariable("id") Long id) {
        return userService.getUserInfoById(id);
    }

    @PostMapping("insert")
    @ApiOperation("新增用户")
    public ResultBean<UserEntity> insert(@Valid @RequestBody UserEntity userEntity) {
        return userService.saveUser(userEntity);
    }

    @GetMapping("list")
    @ApiOperation("用户列表")
    public ResultBean<PageInfo<UserEntity>> list(Object param) {
        return userService.findPageList(param);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("delete/{id}")
    public ResultBean<String> delete(@Valid @PathVariable("id") Long id) {
        return userService.delete(id);
    }
}
