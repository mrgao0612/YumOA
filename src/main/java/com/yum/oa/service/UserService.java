package com.yum.oa.service;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.model.vo.UserInfoOutVo;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
public interface UserService {

    ResultBean<UserInfoOutVo> getUserInfoById(Long id);

    ResultBean<UserEntity> saveUser(UserEntity userEntity);

    ResultBean<PageInfo<UserEntity>> findPageList(Object param);

    UserEntity getUserInfoByMobile(String mobile);

    ResultBean<String> delete(Long id);
}
