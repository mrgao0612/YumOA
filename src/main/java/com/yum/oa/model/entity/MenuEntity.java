package com.yum.oa.model.entity;

import com.yum.oa.common.base.BaseEntity;

/**
 * @author: gaoyu
 * 对应数据库表： yum_menu
 */
public class MenuEntity extends BaseEntity {
    /** 菜单编码 */
    private Integer menuCode;
    /** 目录名称 */
    private String menuName;
    /** 菜单路径 */
    private String path;
    /** 父级菜单 */
    private Long parentId;
    /** 菜单图标 */
    private String icon;
    /** 是否禁用0:否,1:是 */
    private Boolean disabled;

    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

}
