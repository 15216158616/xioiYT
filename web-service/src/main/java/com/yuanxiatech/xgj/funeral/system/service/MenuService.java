package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import com.yuanxiatech.xgj.funeral.system.model.MenuQueryParam;

import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 19:35
 **/
public interface MenuService extends StringPojoAppBaseService<Menu> {

    //新增菜单
    void addMenu(Menu menu, String ids);

    //编辑菜单
    void editMenu(Menu menu, String ids);

    //初始化菜单
    List<Menu> getMenus(String userId, String partnerId);

    //获取菜单树
    List<Menu> getMenuList(List<Menu> menus);

    //获取所有菜单
    List<Menu> getAllMenus();

    //获取菜单列表
    List<Menu> queryMenu(MenuQueryParam menuQueryParam, String logonUserId);

    //获取用户选中菜单
    List<Menu> getUserMenu(List<String> menuIdList);

}
