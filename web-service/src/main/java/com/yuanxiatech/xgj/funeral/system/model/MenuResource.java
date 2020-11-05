package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * @description: 菜单资源表
 * @date 2020/6/9 20:07
 **/
public class MenuResource extends StringPojo {

    private Menu menu;//菜单

    private Resource resource;//资源

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
