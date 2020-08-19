package com.yum.oa.service.impl;

import com.yum.oa.common.redis.RedisKeyGenerator;
import com.yum.oa.common.redis.RedisUtil;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.common.security.Authority;
import com.yum.oa.common.security.JwtUser;
import com.yum.oa.common.security.JwtUtil;
import com.yum.oa.model.dto.LoginDto;
import com.yum.oa.model.entity.UserEntity;
import com.yum.oa.model.vo.LoginOutVo;
import com.yum.oa.service.LoginService;
import com.yum.oa.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-12
 * @version: v2.0
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Value(value = "${jwt.tokenStart}")
    private String tokenStart;

    @Override
    public ResultBean<?> login(LoginDto param) {
        UserEntity userInfo = userService.getUserInfoByMobile(param.getMobile());
        if (userInfo == null || !userInfo.getPassword().equals(param.getPassword())) {
            return ResultGenerator.failed("账号或密码不正确");
        }
        JwtUser jwtUser = new JwtUser(userInfo.getId(), userInfo.getUsername(),
                userInfo.getPassword(), Collections.singleton(Authority.getInstance("All")));
        String token = tokenStart + jwtUtil.generateAccessToken(jwtUser);
        LoginOutVo result = new LoginOutVo();
        BeanUtils.copyProperties(userInfo, result);
        result.setAuthorities(jwtUser.getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet()));
        result.setToken(token);
        redisUtil.set(RedisKeyGenerator.getUserToken(userInfo.getId()), token);
        return ResultGenerator.success(result);
    }
}
