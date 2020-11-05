package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import com.yuanxiatech.xgj.funeral.system.model.MenuQueryParam;
import com.yuanxiatech.xgj.funeral.system.service.MenuResourceService;
import com.yuanxiatech.xgj.funeral.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MenuController extends SystemBaseController {

    private MenuService menuService;

    private MenuResourceService menuResourceService;

    @Autowired(required = false)
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired(required = false)
    public void setMenuResourceService(MenuResourceService menuResourceService) {
        this.menuResourceService = menuResourceService;
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @param ids
     * @return
     */
    @PostMapping(value = "/newMenu")
    public Result addMenu(Menu menu, String ids) {
        menu.setCreateTime(new Date());
        menu.setCreateUserId(getLogonUserId());
        menuService.addMenu(menu, ids);
        return jsonWithStandardStatus();
    }

    /**
     * 删除菜单
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/cancelMenu")
    public Result deleteMenu(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        List<Menu> menus = new ArrayList<>();
        idList.stream().forEach(id -> {
            Menu menu = new Menu();
            menu.setId(id);
            menu.setDeleteTime(new Date());
            menu.setDeleteUserId(getLogonUserId());
            menu.setDataStatus(Menu.DATA_STATUS_DELETE);
            menus.add(menu);
        });
        menuService.logicDelete(menus);
        menuResourceService.deleteMenuIds(idList);
        return jsonWithStandardStatus();
    }

    /**
     * 编辑菜单
     *
     * @param menu
     * @param ids
     * @return
     */
    @PostMapping(value = "/oldMenu")
    public Result editMenu(Menu menu, String ids) {
        menu.setLastUpdateTime(new Date());
        menu.setLastUpdateUserId(getLogonUserId());
        menuService.editMenu(menu, ids);
        return jsonWithStandardStatus();
    }

    /**
     * 初始化菜单
     *
     * @param userId
     * @param partnerId
     * @return
     */
    @GetMapping(value = "/getMenus")
    public Result getMenus(String userId, String partnerId) {
        List<Menu> menuList = menuService.getMenus(userId, partnerId);
        return jsonWithRecord(menuList);
    }

    /**
     * 获取菜单列表
     *
     * @param menuQueryParam
     * @return
     */
    @GetMapping(value = "/menuList")
    public Result queryMenu(MenuQueryParam menuQueryParam) {
        List<Menu> menuList = menuService.queryMenu(menuQueryParam, getLogonUserId());
        return jsonWithRecord(menuList);
    }

    /**
     * 根据菜单id获取菜单
     *
     * @param menuId
     * @return
     */
    @GetMapping(value = "/menu")
    public Result getMenuNameByMenuId(String menuId) {
        Menu menu = menuService.getById(menuId);
        return jsonWithRecord(menu);
    }
}
