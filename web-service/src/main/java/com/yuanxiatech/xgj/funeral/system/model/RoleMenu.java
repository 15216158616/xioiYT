package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * @description: 角色菜单
 * @date 2020/6/9 20:03
 **/
public class RoleMenu extends StringPojo {

    private Role role;//角色

    private Menu menu;//菜单

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
