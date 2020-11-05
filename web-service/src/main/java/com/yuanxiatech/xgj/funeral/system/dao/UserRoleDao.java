package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleDao extends StringPojoAppBaseDao<UserRole> {

    /**
     * @description 批量新增
     * @date 2020/6/11 12:51
     */
    void saveList(List<UserRole> userRoles);

    //角色id删除用户
    void deleteByRoleId(Map<String, Object> map);

    //用户id删除角色
    void deleteByUserId(Map<String, Object> map);

    //角色id查询用户
    List<UserRole> getByRoleId(String roleId);

    //用户id查询角色
    List<UserRole> getByUserId(String userId);
}
