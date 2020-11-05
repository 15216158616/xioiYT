package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import com.yuanxiatech.xgj.funeral.system.model.RoleQueryParam;

import java.util.List;

public interface RoleService extends StringPojoAppBaseService<Role> {

    /**
     * @description 删除
     * @date 2020/6/10 15:57
     */
    void deleteRole(String ids, User logonUser);

    /**
     * @description 分页
     * @date 2020/6/10 15:46
     */
    DataSet<Role> queryByParam(RoleQueryParam queryParam, User logonUser);

    //初始化机构的用户
    List<User> initUserList(String partnerId);

    //机构的角色
    List<Role> initRoleList(String partnerId);

    //角色id和机构查询
    List<Role> getByRoleIdsAndPartnerId(List<String> roleIds, String partnerId);
}
