package com.yum.oa.model.entity;

import com.yum.oa.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author: gaoyu
 * 对应数据库表： yum_user
 */
@ApiModel
public class UserEntity extends BaseEntity {

    /** 用户名 */
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    @Length(max = 10, message = "用户名长度应介于0~10之间")
    private String username;

    /** 密码 */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}