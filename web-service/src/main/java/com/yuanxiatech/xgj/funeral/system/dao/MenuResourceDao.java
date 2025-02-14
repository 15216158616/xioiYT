package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.MenuResource;

import java.util.List;

public interface MenuResourceDao extends StringPojoAppBaseDao<MenuResource> {

    //新增菜单资源
    void saveList(List<MenuResource> menuResources);

    //删除所有当前菜单的菜单资源
    void deleteMenuId(String menuId);

    //根据多个菜单id删除已有的菜单资源
    void deleteMenuIds(List<String> menuIds);

    //根据菜单id获取菜单资源
    List<MenuResource> getByMenuId(String menuId);

}
