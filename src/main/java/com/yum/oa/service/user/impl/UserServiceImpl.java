package com.yum.oa.service.user.impl;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.base.BaseService;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.mapper.UserEntityMapper;
import com.yum.oa.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: v2.0
 **/
@Service
public class UserServiceImpl extends BaseService<UserEntityMapper, UserEntity> implements UserService {

    @Transactional
    @Override
    public ResultBean<?> saveUser(@RequestBody UserEntity entity) {
        if (super.save(entity) > 0) {
            return ResultGenerator.success(entity);
        }
        return ResultGenerator.failed("操作失败");
    }

    @Override
    public ResultBean<PageInfo<UserEntity>> findPageList(Object param) {
        return ResultGenerator.success(super.findPageList());
    }

    @Override
    public UserEntity getUserInfoByMobile(String mobile) {
        return t.getUserInfoByMobile(mobile);
    }

}
