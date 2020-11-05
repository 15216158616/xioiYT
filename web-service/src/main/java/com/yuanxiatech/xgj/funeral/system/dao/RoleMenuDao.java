package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.RoleMenu;

import java.util.Date;
import java.util.List;

public interface RoleMenuDao extends StringPojoAppBaseDao<RoleMenu> {

    /**
     * @description 批量新增
     * @date 2020/6/10 19:57
     */
    void addRoleMenuList(List<RoleMenu> roleMenuList);

    /**
     * @description 删除当前角色所有菜单
     * @date 2020/6/10 20:04
     */
    void deleteRoleMenuList(String roleId, Date deleteTime, String deleteUserId);

    /**
     * @description 角色id获取相关数据
     * @date 2020/6/10 17:27
     * @param roleIdList
     */
    List<RoleMenu> getRoleMenuByRoleIds(List<String> roleIdList);

    //角色获取选中菜单
    List<RoleMenu> getSelectByRole(String roleId);
}
