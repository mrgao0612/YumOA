package com.yum.oa.service.impl;

import com.yum.oa.common.base.BaseService;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.entity.UserEntity;
import com.yum.oa.mapper.UserEntityMapper;
import com.yum.oa.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: v2.0
 **/
@Service
public class UserServiceImpl extends BaseService<UserEntityMapper, UserEntity> implements UserService  {

    @Transactional
    @Override
    public ResultBean<?> saveUser(UserEntity entity) {
        if (super.save(entity) > 0) {
            return ResultGenerator.success(entity);
        }
        return ResultGenerator.failed("操作失败");
    }

    @Override
    public ResultBean<?> findPageList(Object param) {
        return ResultGenerator.success(super.findPageList());
    }

}
