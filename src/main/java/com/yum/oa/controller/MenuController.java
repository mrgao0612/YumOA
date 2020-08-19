package com.yum.oa.controller;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.MenuEntity;
import com.yum.oa.model.vo.NavOutVo;
import com.yum.oa.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 菜单控制器
 * @description
 * @author: gaoyu
 * @create: 2020-08-15
 * @version: 0.0.1
 **/
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "保存菜单")
    @PostMapping("/save")
    public ResultBean<MenuEntity> saveMenu(@Valid @RequestBody MenuEntity entity) {
        return menuService.saveMenu(entity);
    }

    @ApiOperation(value = "查询导航栏菜单")
    @GetMapping("/findNavList")
    public ResultBean<List<NavOutVo>> findNavList() {
        return menuService.findNavList();
    }
}
