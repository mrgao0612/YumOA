package com.yum.oa.service.impl;

import com.yum.oa.common.base.BaseService;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.mapper.MenuEntityMapper;
import com.yum.oa.model.entity.MenuEntity;
import com.yum.oa.model.vo.NavOutVo;
import com.yum.oa.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-15
 * @version: 0.0.1
 **/
@Service
public class MenuServiceImpl extends BaseService<MenuEntityMapper, MenuEntity> implements MenuService {

    @Resource
    private MenuEntityMapper menuEntityMapper;

    protected MenuServiceImpl(MenuEntityMapper menuEntityMapper) {
        super(menuEntityMapper);
    }

    @Override
    public ResultBean<MenuEntity> saveMenu(MenuEntity entity) {
        if (super.save(entity) > 0) {
            return ResultGenerator.success(entity);
        }
        return ResultGenerator.failed("保存菜单失败");
    }

    @Override
    public ResultBean<List<NavOutVo>> findNavList() {
        return ResultGenerator.success(menuEntityMapper.findNavList());
    }
}
