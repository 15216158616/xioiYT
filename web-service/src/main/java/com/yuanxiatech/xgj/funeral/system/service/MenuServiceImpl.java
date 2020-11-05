package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.service.UserService;
import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import com.yuanxiatech.xgj.funeral.system.dao.MenuDao;
import com.yuanxiatech.xgj.funeral.system.model.*;
import com.yuanxiatech.xgj.funeral.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date 2020/6/9 19:37
 **/
@Service
public class MenuServiceImpl extends StringPojoAppBaseServiceImpl<Menu> implements MenuService {

    private MenuDao menuDao;

    private UserRoleService userRoleService;

    private RoleService roleService;

    private RoleMenuService roleMenuService;

    private UserService userService;

    private MenuResourceService menuResourceService;

    @Autowired(required = false)
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Autowired(required = false)
    public void setUserRoleService(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }

    @Autowired(required = false)
    private void setRoleService(RoleService roleService){
        this.roleService = roleService;
    }

    @Autowired(required = false)
    public void setRoleMenuService(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Autowired(required = false)
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired(required = false)
    public void setMenuResourceService(MenuResourceService menuResourceService) {
        this.menuResourceService = menuResourceService;
    }

    @Override
    protected AppBaseDao<Menu, String> getDao() {
        return menuDao;
    }

    /**
     * 新增菜单
     * @param menu
     * @param ids
     */
    @Override
    public void addMenu(Menu menu, String ids) {
        String id = super.save(menu);
        menu.setId(id);
        if (StringUtils.isNotBlank(ids)) {
            List<MenuResource> menuResources = new ArrayList<>();
            List<String> resourceIds = StringUtil.convertList(ids);
            resourceIds.stream().forEach(resourceId -> {
                MenuResource menuResource = new MenuResource();
                menuResource.setId(UuidUtil.get32UUID());
                menuResource.setMenu(menu);
                menuResource.setResource(new Resource(resourceId));
                menuResource.setCreateTime(new Date());
                menuResource.setCreateUserId(menu.getCreateUserId());
                menuResource.setDataStatus(MenuResource.DATA_STATUS_NORMAL);
                menuResources.add(menuResource);
            });

            //新增菜单资源
            menuResourceService.saveList(menuResources);
        }
    }

    /**
     * 编辑菜单
     * @param menu
     * @param ids
     */
    @Override
    public void editMenu(Menu menu, String ids) {
        menuDao.update(menu);
        //删除所有当前菜单的菜单资源
        menuResourceService.deleteMenuId(menu.getId());
        if (StringUtils.isNotBlank(ids)) {
            List<String> resourceIds = StringUtil.convertList(ids);
            List<MenuResource> menuResources = new ArrayList<>();
            resourceIds.stream().forEach(resourceId -> {
                MenuResource menuResource = new MenuResource();
                menuResource.setResource(new Resource(resourceId));
                menuResource.setId(UuidUtil.get32UUID());
                menuResource.setMenu(menu);
                menuResource.setCreateTime(new Date());
                menuResource.setCreateUserId(menu.getCreateUserId());
                menuResource.setDataStatus(MenuResource.DATA_STATUS_NORMAL);
                menuResources.add(menuResource);
            });

            //新增菜单资源
            menuResourceService.saveList(menuResources);
        }
    }

    /**
     * 初始化菜单
     * @param userId
     * @param partnerId
     * @return
     */
    @Override
    public List<Menu> getMenus(String userId, String partnerId) {
        List<Menu> menuList = new ArrayList<>();
        User user = userService.getById(userId);
        if(user == null){
            return menuList;
        }

        //如果用户是超级管理员
        if(user.getSuperAdmin()){
            //获取所有菜单
            menuList = this.getAllMenus();
        }else {
            //用户id获取角色
            List<UserRole> userRoleList = userRoleService.getByUserId(userId);
            if(CollectionUtils.isNotEmpty(userRoleList)){
                List<Role> roleList = userRoleList.stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(roleList)){
                    List<String> roleIds = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
                    //机构查询角色
                    List<Role> roles = roleService.getByRoleIdsAndPartnerId(roleIds, partnerId);
                    if(CollectionUtils.isNotEmpty(roles)){
                        List<String> idList = roles.stream().map(role -> role.getId()).collect(Collectors.toList());

                        //角色获取菜单
                        List<String> menuIdList = new ArrayList<>();
                        if(!CollectionUtils.isEmpty(idList)){
                            menuIdList = roleMenuService.getMenuTreeByRole(idList);
                        }

                        //获取菜单树
                        if(!CollectionUtils.isEmpty(menuIdList)){
                            //获取用户选中的菜单
                            List<Menu> menus = menuDao.getUserMenu(menuIdList);
                            menuList = this.getMenuList(menus);
                        }
                    }
                }
            }
            if(!CollectionUtils.isEmpty(menuList)){
                //对顶级菜单进行排序
                menuList = menuList.stream().sorted(Comparator.comparing(Menu::getOrderNum)).collect(Collectors.toList());
            }
        }
        return menuList;
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        //获取所有菜单
        List<Menu> menus = menuDao.getAllMenus();
        List<Menu> menuList = getMenuList(menus);
        return menuList;
    }

    /**
     * 获取用户选中菜单
     * @param menuIdList
     * @return
     */
    @Override
    public List<Menu> getUserMenu(List<String> menuIdList) {
        List<Menu> menus = menuDao.getUserMenu(menuIdList);
        return menus;
    }

    /**
     * 获取菜单列表
     * @param menuQueryParam
     * @param logonUserId
     * @return
     */
    @Override
    public List<Menu> queryMenu(MenuQueryParam menuQueryParam, String logonUserId) {

        List<Menu> menuList = new ArrayList<>();
        User user = userService.getById(logonUserId);
        menuQueryParam.setPaging(null);
        if(user == null){
            return menuList;
        }

        //如果用户是超级管理员
        if(user.getSuperAdmin()){
            //获取所有菜单
            DataSet<Menu> menuDataSet = menuDao.queryByParam(menuQueryParam);
            menuList = getMenuList(menuDataSet.getData());
        }else {
            //用户id获取角色
            List<UserRole> userRoleList = userRoleService.getByUserId(logonUserId);
            List<Role> roleList = userRoleList.stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(roleList)){
                List<String> roleIds = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
                //机构查询角色
                List<Role> roles = roleService.getByRoleIdsAndPartnerId(roleIds, menuQueryParam.getPartnerId());
                List<String> idList = roles.stream().map(role -> role.getId()).collect(Collectors.toList());

                //角色获取菜单
                List<String> menuIdList = new ArrayList<>();
                if(!CollectionUtils.isEmpty(idList)){
                    menuIdList = roleMenuService.getMenuTreeByRole(idList);
                }
                //获取菜单
                if(!CollectionUtils.isEmpty(menuIdList)){
                    menuQueryParam.setMenuIds(menuIdList);

                    DataSet<Menu> menuDataSet = menuDao.queryByParam(menuQueryParam);
                    menuList = this.getMenuList(menuDataSet.getData());
                }
            }
            if(!CollectionUtils.isEmpty(menuList)){
                //对顶级菜单进行排序
                menuList = menuList.stream().sorted(Comparator.comparing(Menu::getOrderNum)).collect(Collectors.toList());
            }
        }
        return menuList;
    }

    /**
     * 对菜单处理形成菜单树
     * @param menuList
     * @return
     */
    @Override
    public List<Menu> getMenuList(List<Menu> menuList) {

        //返回的菜单树
        List<Menu> parentList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(menuList)){
            //功能菜单的父级菜单id
            List<String> buttonMenuIds = menuList.stream().filter(menu -> !StringUtils.isEmpty(menu.getParentId()) && menu.getMenuType() == MenuTypeEnum.BUTTON.getValue()).map(menu -> menu.getParentId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(buttonMenuIds)){
                List<Menu> buttonMenuList = super.getByIds(buttonMenuIds);
                //将类型是功能菜单添加到用户选中的菜单中
                for (Menu menu : buttonMenuList) {
                    if(menu != null){
                        menuList.add(menu);
                    }
                }
            }

            //获取类型是菜单的菜单类型父级id
            List<String> parentIds = menuList.stream().filter(menu -> !StringUtils.isEmpty(menu.getParentId()) && menu.getMenuType() == MenuTypeEnum.MENU.getValue()).map(menu -> menu.getParentId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(parentIds)){
                List<Menu> menus = super.getByIds(parentIds);
                //将类型是菜单的菜单添加到用户选中的菜单中
                for (Menu menu : menus) {
                    if(menu != null){
                        menuList.add(menu);
                    }
                }

                //获取非顶级菜单的菜单id
                List<String> ids = menus.stream().filter(menu -> !StringUtils.isEmpty(menu.getParentId()) && menu.getMenuType() == MenuTypeEnum.MENU.getValue()).map(menu -> menu.getParentId()).collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(ids)){
                    //递归将所有的非选中顶级的菜单添加到用户选中的菜单中
                    this.getParentMenu(menuList,menus);
                }
            }

            //去除重复的菜单
            menuList = menuList.stream().distinct().collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(menuList)){
                //获取顶级菜单
                parentList = menuList.stream().filter(menu -> StringUtils.isEmpty(menu.getParentId())).collect(Collectors.toList());
                //获取自己菜单
                List<Menu> childrenList = menuList.stream().filter(menu -> !StringUtils.isEmpty(menu.getParentId())).collect(Collectors.toList());

                if(CollectionUtils.isNotEmpty(childrenList)){
                    //对子级菜单排序
                    childrenList = childrenList.stream().sorted(Comparator.comparing(Menu::getOrderNum)).collect(Collectors.toList());
                }
                List<Menu> finalChildrenList = childrenList;
                //递归形成菜单树
                parentList.stream().forEach(menu -> menu.setChildrenList(menu.getId(), finalChildrenList));
            }
        }

        return parentList;
    }

    //递归将所有的非选中顶级的菜单添加到用户选中的菜单中
    private void getParentMenu(List<Menu> menuList,List<Menu> menus) {

        List<String> parentIds = menus.stream().filter(menu -> menu.getParentId() != null && menu.getMenuType() == MenuTypeEnum.MENU.getValue()).map(menu -> menu.getParentId()).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(parentIds)){
            List<Menu> menuArray = super.getByIds(parentIds);
            //将类型是菜单的菜单添加到用户选中的菜单中
            for (Menu menu : menuArray) {
                if(menu != null){
                    menuList.add(menu);
                }
            }

            List<String> ids = menuArray.stream().filter(data -> data.getParentId() != null && data.getMenuType() == MenuTypeEnum.MENU.getValue()).map(data -> data.getParentId()).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(ids)){
                this.getParentMenu(menus,menuArray);
            }
        }
    }

}
