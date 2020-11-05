package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * @description: 角色用户
 * @date 2020/6/11 10:25
 **/
public class UserRole extends StringPojo {

    private Role role;//角色

    private User user;//用户

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
