package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.QueryParamAdapter;

/**
 * @description: 资源信息表
 * @date 2020/6/9 20:05
 **/
public class ResourceQueryParam extends QueryParamAdapter {

    private String name;//名称

    private String path;//路径

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
}
