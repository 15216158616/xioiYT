package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.common.rbac.service.UserService;
import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.funeral.system.dao.RoleMenuDao;
import com.yuanxiatech.xgj.funeral.system.model.Menu;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import com.yuanxiatech.xgj.funeral.system.model.RoleMenu;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;
import com.yuanxiatech.xgj.funeral.system.service.MenuService;
import com.yuanxiatech.xgj.funeral.system.service.RoleMenuService;
import com.yuanxiatech.xgj.funeral.system.service.RoleService;
import com.yuanxiatech.xgj.funeral.system.service.UserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date 2020/6/9 20:15
 **/
@Service
public class RoleMenuServiceImpl extends StringPojoAppBaseServiceImpl<RoleMenu> implements RoleMenuService {

    private RoleMenuDao roleMenuDao;

    private MenuService menuService;

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleService roleService;

    @Autowired(required = false)
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired(required = false)
    public void setRoleMenuDao(RoleMenuDao roleMenuDao) {
        this.roleMenuDao = roleMenuDao;
    }

    @Autowired(required = false)
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired(required = false)
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired(required = false)
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected AppBaseDao<RoleMenu, String> getDao() {
        return roleMenuDao;
    }

    /**
     * 角色新增菜单
     * @param roleId
     * @param menuIds
     * @param logonUser
     */
    @Override
    public void addRoleMenu(String roleId, String menuIds, User logonUser) {

        List<RoleMenu> roleMenuList = new ArrayList<>();
        List<String> idList = StringUtil.convertList(menuIds);

        idList.stream().forEach(id ->{

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setId(UuidUtil.get32UUID());
            roleMenu.setMenu(new Menu(id));
            roleMenu.setRole(new Role(roleId));
            roleMenu.setCreateTime(new Date());
            roleMenu.setCreateUserId(logonUser.getId());
            roleMenuList.add(roleMenu);
        });

        //先删除当前角色所有菜单
        roleMenuDao.deleteRoleMenuList(roleId,new Date(),logonUser.getId());
        //再为角色新增菜单
        roleMenuDao.addRoleMenuList(roleMenuList);
    }

    /**
     * 角色已有的菜单以及功能
     * @param roleIdList
     * @return
     */
    @Override
    public List<String> getMenuTreeByRole(List<String> roleIdList) {

        //根据当前角色id获取对应的菜单功能
        List<RoleMenu> roleMenus = roleMenuDao.getRoleMenuByRoleIds(roleIdList);
        List<String> menuIds = roleMenus.stream().filter(roleMenu -> roleMenu.getMenu() != null).map(menu -> menu.getMenu().getId()).collect(Collectors.toList());

        return menuIds;
    }

    /**
     * 角色获取选中菜单
     * @param roleId
     * @return
     */
    @Override
    public List<String> getSelectByRole(String roleId) {

        List<RoleMenu> roleMenus = roleMenuDao.getSelectByRole(roleId);
        List<String> menuIds = roleMenus.stream().filter(data -> data.getMenu() != null).map(data -> data.getMenu().getId()).collect(Collectors.toList());

        return menuIds;
    }

    /**
     * 获取角色菜单
     * @param userId
     * @param partnerId
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> getRoleMenu(String userId, String partnerId, String roleId) {

        List<Menu> menuTrees = new ArrayList<>();

        //用户为空
        User user = userService.getById(userId);
        if(user == null){
            return menuTrees;
        }

        //用户是超级管理员
        if(user.getSuperAdmin()){
            //获取所有菜单
            menuTrees = menuService.getAllMenus();

        }else {

            //用户非超级管理员
            List<UserRole> userRoleList = userRoleService.getByUserId(userId);

            //用户id查询角色
            List<String> roleIds = new ArrayList<>();
            if(!CollectionUtils.isEmpty(userRoleList)){
                userRoleList.stream().forEach(userRole -> {
                    if(userRole != null && userRole.getRole() != null){
                        roleIds.add(userRole.getRole().getId());
                    }
                });
            }

            //从角色中筛选出该机构的角色
            List<String> roleIdList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(roleIds)){
                List<Role> roleList = roleService.getByRoleIdsAndPartnerId(roleIds,partnerId);
                roleList.stream().forEach(role -> {
                    if(role != null){
                        roleIdList.add(role.getId());
                    }
                });
            }

            //根据角色id获取菜单id
            List<String> menuIdList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(roleIdList)){
                menuIdList = this.getMenuTreeByRole(roleIdList);
            }

            //获取菜单树
            if(!CollectionUtils.isEmpty(menuIdList)){
                List<Menu> menus = menuService.getUserMenu(menuIdList);
                menuTrees = menuService.getMenuList(menus);
            }
        }

        return menuTrees;
    }
}
