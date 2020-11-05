package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.QueryParamAdapter;

import java.util.List;

/**
 * @description: 菜单表
 * @date 2020/6/9 19:26
 **/
public class MenuQueryParam extends QueryParamAdapter {

    private String menuName;//菜单名

    private Integer menuType;//菜单类型(1菜单 2功能按钮）

    private Integer visible;//是否可见（1可见 2不可见）

    private String partnerId;

    private List<String> menuIds;

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

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
