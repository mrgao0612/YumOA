package com.yum.oa.mapper;

import com.yum.oa.common.base.BaseEntityMapper;
import com.yum.oa.model.entity.MenuEntity;
import com.yum.oa.model.vo.NavOutVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuEntityMapper extends BaseEntityMapper<MenuEntity> {
    List<NavOutVo> findNavList();
}
