package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @date 2020/6/11 10:29
 **/
@Repository
public class UserRoleDaoImpl extends StringPojoAppBaseDaoImpl<UserRole> implements UserRoleDao{

    @Override
    protected Class<UserRole> getPojoClass() {
        return UserRole.class;
    }

    /**
     * 批量新增
     * @param userRoles
     */
    @Override
    public void saveList(List<UserRole> userRoles) {
        insert(getPrefix() + "saveList",userRoles);
    }

    /**
     * 角色id删除用户
     * @param map
     */
    @Override
    public void deleteByRoleId(Map<String, Object> map) {
        delete(getPrefix() + "deleteByRoleId",map);
    }

    /**
     * 用户id删除角色
     * @param map
     */
    @Override
    public void deleteByUserId(Map<String, Object> map) {
        delete(getPrefix() + "deleteByUserId",map);
    }

    /**
     * 角色id查询用户
     * @param roleId
     * @return
     */
    @Override
    public List<UserRole> getByRoleId(String roleId) {

        Map<String, Object> map = new HashMap<>();
        map.put("roleId",roleId);
        return selectList(getPrefix() + "getByRoleId",map);
    }

    /**
     * 用户id查询角色
     * @param userId
     * @return
     */
    @Override
    public List<UserRole> getByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        return selectList(getPrefix() + "getByUserId",map);
    }
}
