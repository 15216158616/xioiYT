package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 20:03
 **/
public class RoleMenuQueryParam extends StringPojo {

    private String roleName;//角色

    private List<String> roleIds;

    private String menuName;//菜单

    private List<String> menuIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
