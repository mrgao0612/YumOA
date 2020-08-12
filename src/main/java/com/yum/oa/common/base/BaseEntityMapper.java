package com.yum.oa.common.base;

import java.util.List;

/**
 * 基础Mapper接口
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
public interface BaseEntityMapper<E extends BaseEntity> {

    E selectByPrimaryKey(Long id);

    int insertSelective(E entity);

    int updateByPrimaryKeySelective(E entity);

    List<E> findList();
}
