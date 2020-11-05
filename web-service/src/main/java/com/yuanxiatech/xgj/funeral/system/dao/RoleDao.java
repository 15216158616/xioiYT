package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.Role;

import java.util.List;

public interface RoleDao extends StringPojoAppBaseDao<Role> {

    //机构的角色
    List<Role> getByPartnerId(String partnerId);

    //角色id和机构查询
    List<Role> getByRoleIdsAndPartnerId(List<String> roleIds, String partnerId);
}
