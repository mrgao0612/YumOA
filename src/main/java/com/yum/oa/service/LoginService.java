package com.yum.oa.service;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.dto.LoginDto;

/**
 * 登录业务接口
 * @description
 * @author: gaoyu
 * @create: 2020-08-12
 * @version: 0.0.1
 **/
public interface LoginService {
    ResultBean<?> login(LoginDto param);
}
