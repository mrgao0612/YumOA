package com.yum.oa.service;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.entity.UserEntity;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: v2.0
 **/
public interface UserService {

    ResultBean<?> saveUser(UserEntity userEntity);

    ResultBean<?> findPageList(Object param);
}
