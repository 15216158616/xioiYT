package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.funeral.system.model.MenuResource;
import com.yuanxiatech.xgj.funeral.system.service.MenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 20:13
 **/
@RestController
public class MenuResourceController extends SystemBaseController {

    private MenuResourceService menuResourceService;

    @Autowired(required = false)
    public void setMenuResourceService(MenuResourceService menuResourceService) {
        this.menuResourceService = menuResourceService;
    }

    /**
     * 根据菜单id获取菜单资源
     *
     * @param menuId
     * @return
     */
    @GetMapping(value = "/menuResourceList")
    public Result getMenuResources(String menuId) {
        List<MenuResource> menuResources = menuResourceService.getByMenuId(menuId);
        return jsonWithRecord(menuResources);
    }
}
