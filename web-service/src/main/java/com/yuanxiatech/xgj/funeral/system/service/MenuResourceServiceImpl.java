package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.funeral.system.dao.MenuResourceDao;
import com.yuanxiatech.xgj.funeral.system.model.MenuResource;
import com.yuanxiatech.xgj.funeral.system.service.MenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 20:22
 **/
@Service
public class MenuResourceServiceImpl extends StringPojoAppBaseServiceImpl<MenuResource> implements MenuResourceService {

    private MenuResourceDao menuResourceDao;

    @Autowired(required = false)
    public void setMenuResourceDao(MenuResourceDao menuResourceDao) {
        this.menuResourceDao = menuResourceDao;
    }

    @Override
    protected AppBaseDao<MenuResource, String> getDao() {
        return menuResourceDao;
    }

    /**
     * 新增菜单资源
     * @param menuResources
     */
    @Override
    public void saveList(List<MenuResource> menuResources) {
        menuResourceDao.saveList(menuResources);
    }

    /**
     * 删除所有当前菜单的菜单资源
     * @param menuId
     */
    @Override
    public void deleteMenuId(String menuId) {
        menuResourceDao.deleteMenuId(menuId);
    }

    /**
     * 根据多个菜单id删除已有的菜单资源
     * @param menuIds
     */
    @Override
    public void deleteMenuIds(List<String> menuIds) {
        menuResourceDao.deleteMenuIds(menuIds);
    }

    /**
     * 根据菜单id获取菜单资源
     * @param menuId
     * @return
     */
    @Override
    public List<MenuResource> getByMenuId(String menuId) {
        return menuResourceDao.getByMenuId(menuId);
    }
}
