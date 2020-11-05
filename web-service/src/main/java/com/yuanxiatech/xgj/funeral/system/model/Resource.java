package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * @description: 资源信息表
 * @date 2020/6/9 20:05
 **/
public class Resource extends StringPojo {

    private String name;//名称

    private String path;//路径

    private String remark;//描述

    public Resource(){}

    public Resource(String id){super(id);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
