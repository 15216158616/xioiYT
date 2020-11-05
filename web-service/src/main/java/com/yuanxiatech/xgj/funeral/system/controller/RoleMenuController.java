package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import com.yuanxiatech.xgj.funeral.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @date 2020/6/9 20:12
 **/
@RestController
public class RoleMenuController extends SystemBaseController {

    private RoleMenuService roleMenuService;

    @Autowired(required = false)
    public void setRoleMenuService(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * 获取角色菜单
     *
     * @param userId
     * @param partnerId
     * @return
     */
    @GetMapping(value = "/getMenuTree")
    public Result getRoleMenu(String userId, String partnerId, String roleId) {

        //获取角色菜单
        List<Menu> menuTrees = roleMenuService.getRoleMenu(userId, partnerId, roleId);
        Map<String, Object> map = new HashMap<>();

        //角色获取选中菜单
        List<String> menuIds = roleMenuService.getSelectByRole(roleId);

        map.put("menuTrees", menuTrees);
        map.put("menuIds", menuIds);//此菜单id用于回显前端选中的数据

        return jsonWithRecord(map);
    }

    /**
     * 角色添加菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping(value = "/addRoleMenu")
    public Result addRoleMenu(String roleId, String menuIds) {

        roleMenuService.addRoleMenu(roleId, menuIds, getLogonUser());
        return jsonWithStandardStatus();
    }
}
