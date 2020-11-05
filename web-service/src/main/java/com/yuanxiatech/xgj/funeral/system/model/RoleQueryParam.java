package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.QueryParamAdapter;

import java.util.List;

/**
 * @description 用户角色表
 * @date 2020/6/9 18:57
 */
public class RoleQueryParam extends QueryParamAdapter {

    private String name;//名称

    private String code;//标示

    private List<String> roleIds;

    private String partnerId;//机构

    public RoleQueryParam(){}

    public RoleQueryParam(String name){this.name = name;}

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
