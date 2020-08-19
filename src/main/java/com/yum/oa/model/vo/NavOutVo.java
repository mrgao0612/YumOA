package com.yum.oa.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导航栏Vo类
 * @description
 * @author: gaoyu
 * @create: 2020-08-17
 * @version: 0.0.1
 **/
@ApiModel
public class NavOutVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "目录名称")
    private String menuName;
    @ApiModelProperty(value = "父级菜单ID")
    private Long parentId;
    @ApiModelProperty(value = "菜单路径")
    private String path;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @ApiModelProperty(value = "是否禁用0:否,1:是")
    private Boolean disabled;
    @ApiModelProperty(value = "子菜单列表")
    private List<NavOutVo> childNavList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<NavOutVo> getChildNavList() {
        return childNavList;
    }

    public void setChildNavList(List<NavOutVo> childNavList) {
        this.childNavList = childNavList;
    }
}
