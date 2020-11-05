package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;

import java.util.List;

public interface UserRoleService extends StringPojoAppBaseService<UserRole> {

    /**
     * @description 角色用户新增
     * @date 2020/6/11 12:48
     */
    void saveRoleUserList(String roleId, String userIds, User logonUser);

    /**
     * @description 用户角色新增
     * @date 2020/6/11 15:08
     */
    void saveUserRoleList(String userId, String roleIds, User logonUser);

    //角色id查询用户
    List<UserRole> getByRoleId(String roleId);

    //用户id查询角色
    List<UserRole> getByUserId(String userId);

}
