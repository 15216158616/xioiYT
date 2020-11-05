package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 19:38
 **/
@Repository
public class MenuDaoImpl extends StringPojoAppBaseDaoImpl<Menu> implements MenuDao{

    @Override
    protected Class<Menu> getPojoClass() {
        return Menu.class;
    }

    /**
     * 获取用户选中菜单树
     * @param menuIds
     * @return
     */
    @Override
    public List<Menu> getUserMenu(List<String> menuIds) {
        return selectList(getPrefix() + "getUserMenu", menuIds);
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return selectList(getPrefix() + "getAllMenus");
    }
}
