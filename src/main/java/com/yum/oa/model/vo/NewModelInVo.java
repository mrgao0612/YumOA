package com.yum.oa.model.vo;

/**
 * 工作流保存模型
 * @description
 * @author: gaoyu
 * @create: 2020-08-18
 * @version:
 **/
public class NewModelInVo {
    private String id;
    private String name;
    private String key;
    private String desc;
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
