package com.yum.oa.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户信息Vo类
 * @description
 * @author: gaoyu
 * @create: 2020-08-17
 * @version: 0.0.1
 **/
@ApiModel(description = "用户信息")
public class UserInfoOutVo {
    @ApiModelProperty(value = "用户主键")
    private Long id;
    /** 员工编号 */
    @ApiModelProperty(value = "用户编号")
    private Integer userCode;
    /** 用户名称 */
    @ApiModelProperty(value = "用户名称")
    private String username;
    /** 手机号码 */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /** 所在部门主键 */
    @ApiModelProperty(value = "所在部门主键")
    private Long deptId;
    @ApiModelProperty(value = "所在部门名称")
    private String deptName;
    /** 所属角色主键 */
    @ApiModelProperty(value = "所属角色主键")
    private Long roleId;
    @ApiModelProperty(value = "所属角色名称")
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
