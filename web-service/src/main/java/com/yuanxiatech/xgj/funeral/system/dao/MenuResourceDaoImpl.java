package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.MenuResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuResourceDaoImpl extends StringPojoAppBaseDaoImpl<MenuResource> implements MenuResourceDao{

    @Override
    protected Class<MenuResource> getPojoClass() {
        return MenuResource.class;
    }

    /**
     * 新增菜单资源
     * @param menuResources
     */
    @Override
    public void saveList(List<MenuResource> menuResources) {
        insert(getPrefix() + "saveList",menuResources);
    }

    /**
     * 删除所有当前菜单的菜单资源
     * @param menuId
     */
    @Override
    public void deleteMenuId(String menuId) {
        delete(getPrefix() + "deleteMenuId",menuId);
    }

    /**
     * 根据多个菜单id删除已有的菜单资源
     * @param menuIds
     */
    @Override
    public void deleteMenuIds(List<String> menuIds) {
        delete(getPrefix() + "deleteMenuIds",menuIds);
    }

    /**
     * 根据菜单id获取菜单资源
     * @param menuId
     * @return
     */
    @Override
    public List<MenuResource> getByMenuId(String menuId) {
        return selectList(getPrefix() + "getByMenuId",menuId);
    }
}
