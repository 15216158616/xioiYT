package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.Menu;

import java.util.List;

public interface MenuDao extends StringPojoAppBaseDao<Menu> {

    //获取用户选中菜单树
    List<Menu> getUserMenu(List<String> menuIds);

    //获取所有菜单
    List<Menu> getAllMenus();
}
