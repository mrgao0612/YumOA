package com.yum.oa.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
public abstract class BaseService<T extends BaseEntityMapper<E>, E extends BaseEntity> {
    private final T mapper;

    protected BaseService(T mapper) {
        this.mapper = mapper;
    }

    protected E getOne(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    protected List<E> findList() {
        return mapper.findList();
    }

    protected PageInfo<E> findPageList() {
        PageHelper.startPage(1, 10);
        return new PageInfo<>(this.findList());
    }

    protected int save(E entity) {
        if (entity == null) {
            return 0;
        }
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
            return mapper.insertSelective(entity);
        } else {
            entity.setModifiedDate(new Date());
            return mapper.updateByPrimaryKeySelective(entity);
        }
    }

    protected int deleteById(Long id) {
        return mapper.deleteById(id);
    }

}
