package com.yum.oa.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
@Service
public abstract class BaseService<T extends BaseEntityMapper<E>, E extends BaseEntity> {
    @Resource
    protected T t;

    protected E getOne(Long id) {
        return t.selectByPrimaryKey(id);
    }

    protected List<E> findList() {
        return t.findList();
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
            return t.insertSelective(entity);
        } else {
            return t.updateByPrimaryKeySelective(entity);
        }
    }

}
