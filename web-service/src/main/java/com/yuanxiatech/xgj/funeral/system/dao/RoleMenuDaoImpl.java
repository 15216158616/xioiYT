package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleMenuDaoImpl extends StringPojoAppBaseDaoImpl<RoleMenu> implements RoleMenuDao{

    @Override
    protected Class<RoleMenu> getPojoClass() {
        return RoleMenu.class;
    }

    /**
     * 角色新增菜单
     * @param roleMenuList
     */
    @Override
    public void addRoleMenuList(List<RoleMenu> roleMenuList) {
        insert(getPrefix() + "addRoleMenuList",roleMenuList);
    }

    /**
     * 删除角色已有的菜单
     * @param roleId
     * @param deleteTime
     * @param deleteUserId
     */
    @Override
    public void deleteRoleMenuList(String roleId, Date deleteTime, String deleteUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId",roleId);
        map.put("deleteTime",deleteTime);
        map.put("deleteUserId",deleteUserId);

        delete(getPrefix() + "deleteRoleMenuList", map);
    }

    /**
     * 角色已有的菜单以及功能
     * @param roleIdList
     * @return
     */
    @Override
    public List<RoleMenu> getRoleMenuByRoleIds(List<String> roleIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleIdList",roleIdList);
        return selectList(getPrefix() + "getRoleMenuByRoleIds", map);
    }

    /**
     * 角色获取选中菜单
     * @param roleId
     * @return
     */
    @Override
    public List<RoleMenu> getSelectByRole(String roleId) {
        return selectList(getPrefix() + "getSelectByRole",roleId);
    }
}
