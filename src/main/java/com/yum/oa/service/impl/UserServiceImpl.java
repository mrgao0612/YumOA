package com.yum.oa.service.impl;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.base.BaseService;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.mapper.UserEntityMapper;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.model.vo.UserInfoOutVo;
import com.yum.oa.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * 用户业务实现类
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
@Service
public class UserServiceImpl extends BaseService<UserEntityMapper, UserEntity> implements UserService {

    @Resource
    private UserEntityMapper userEntityMapper;

    protected UserServiceImpl(UserEntityMapper userEntityMapper) {
        super(userEntityMapper);
    }

    @Override
    public ResultBean<UserInfoOutVo> getUserInfoById(Long id) {
        UserInfoOutVo result = new UserInfoOutVo();
        UserEntity userEntity = super.getOne(id);
        if (userEntity == null) {
            return ResultGenerator.failed("用户不存在");
        }
        BeanUtils.copyProperties(userEntity, result);
        return ResultGenerator.success(result);
    }

    @Transactional
    @Override
    public ResultBean<UserEntity> saveUser(@RequestBody UserEntity entity) {
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
        return userEntityMapper.getUserInfoByMobile(mobile);
    }

    @Override
    public ResultBean<String> delete(Long id) {
        if (super.deleteById(id) > 0) {
            return ResultGenerator.success("删除成功");
        }
        return ResultGenerator.failed("删除失败");
    }

}
