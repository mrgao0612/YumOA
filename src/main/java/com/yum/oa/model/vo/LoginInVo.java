package com.yum.oa.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录入参
 * @description
 * @author: gaoyu
 * @create: 2020-08-12
 * @version: 0.0.1
 **/
@ApiModel(description = "用户登录入参")
public class LoginInVo {
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;
    @ApiModelProperty(name = "密码", required = true)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
