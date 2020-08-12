package com.yum.oa.service.user;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.UserEntity;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: v2.0
 **/
public interface UserService {

    ResultBean<?> saveUser(UserEntity userEntity);

    ResultBean<PageInfo<UserEntity>> findPageList(Object param);

    UserEntity getUserInfoByMobile(String mobile);
}
