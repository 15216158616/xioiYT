package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 菜单表
 * @date 2020/6/9 19:26
 **/
public class Menu extends StringPojo {

    private String parentId;//上级菜单

    private String menuName;//菜单名

    private Integer menuType;//菜单类型菜单类型(1菜单 2功能按钮）

    private String icon;//图标

    private String url;//菜单链接

    private String routeName;//路由名称

    private String routePath;//路由路径

    private String routeComponent;//路由组件

    private Integer routeCache;//路由缓存（1缓存 2不缓存）

    private String perms;//权限标示

    private Integer visible;//是否可见（1可见 2不可见）

    private Integer orderNum;//排序

    private String remark;//备注

    private List<Menu> childrenList;//子类集合

    public String getMenuTypeDesc(){
        String desc = "";
        if(this.menuType != null){
            MenuTypeEnum menuTypeEnum = MenuTypeEnum.parse(this.menuType);
            desc = menuTypeEnum != null ?  menuTypeEnum.getLabel() : "";
        }
        return desc;
    }

    public String getRouteCacheDesc(){
        String desc = "";
        if(this.routeCache != null){
            RouteCacheEnum routeCacheEnum = RouteCacheEnum.parse(this.routeCache);
            desc = routeCacheEnum != null ?  routeCacheEnum.getLabel() : "";
        }
        return desc;
    }

    public String getVisibleDesc(){
        String desc = "";
        if(this.visible != null){
            VisibleEnum visibleEnum = VisibleEnum.parse(this.visible);
            desc = visibleEnum != null ?  visibleEnum.getLabel() : "";
        }
        return desc;
    }

    public Menu(){}

    public Menu(String id){super(id);}

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getRouteComponent() {
        return routeComponent;
    }

    public void setRouteComponent(String routeComponent) {
        this.routeComponent = routeComponent;
    }

    public Integer getRouteCache() {
        return routeCache;
    }

    public void setRouteCache(Integer routeCache) {
        this.routeCache = routeCache;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Menu> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(String id,List<Menu> menuList) {

        List<Menu> childrenList = menuList.stream().filter(menu -> StringUtils.equals(id, menu.getParentId())).collect(Collectors.toList());
        this.childrenList = childrenList;

        if(!childrenList.isEmpty()){
            childrenList.stream().forEach(children -> children.setChildrenList(children.getId(),menuList));
        }
    }

}
