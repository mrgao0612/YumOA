package com.yum.oa.model.dto;

/**
 * 登录业务参数
 * @description
 * @author: gaoyu
 * @create: 2020-08-12
 * @version: 0.0.1
 **/
public class LoginDto {
    private String mobile;
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
