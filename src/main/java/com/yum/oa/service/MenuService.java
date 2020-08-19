package com.yum.oa.service;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.MenuEntity;
import com.yum.oa.model.vo.NavOutVo;

import java.util.List;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-15
 * @version: 0.0.1
 **/
public interface MenuService {
    ResultBean<MenuEntity> saveMenu(MenuEntity entity);

    ResultBean<List<NavOutVo>> findNavList();
}
