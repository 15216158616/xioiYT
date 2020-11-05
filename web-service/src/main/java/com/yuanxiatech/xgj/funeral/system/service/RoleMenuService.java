package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import com.yuanxiatech.xgj.funeral.system.model.RoleMenu;

import java.util.List;

public interface RoleMenuService extends StringPojoAppBaseService<RoleMenu> {

    /**
     * @description 新增
     * @date 2020/6/10 19:53
     */
    void addRoleMenu(String roleId, String menuIds, User logonUser);

    /**
     * @description 获取当前角色对应的功能
     * @date 2020/6/10 17:15
     * @param roleIdList
     */
    List<String> getMenuTreeByRole(List<String> roleIdList);

    //角色获取选中菜单
    List<String> getSelectByRole(String roleId);

    //获取角色菜单
    List<Menu> getRoleMenu(String userId, String partnerId, String roleId);
}
