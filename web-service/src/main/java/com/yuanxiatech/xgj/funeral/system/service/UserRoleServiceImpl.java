package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.funeral.system.dao.UserRoleDao;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;
import com.yuanxiatech.xgj.funeral.system.service.UserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: TODO
 * @date 2020/6/11 10:30
 **/
@Service
public class UserRoleServiceImpl extends StringPojoAppBaseServiceImpl<UserRole> implements UserRoleService {

    private UserRoleDao userRoleDao;

    @Autowired(required = false)
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    protected AppBaseDao<UserRole, String> getDao() {
        return userRoleDao;
    }

    /**
     * 角色用户新增
     * @param roleId
     * @param userIds
     * @param logonUser
     */
    public void saveRoleUserList(String roleId, String userIds, User logonUser) {

        List<UserRole> userRoles = new ArrayList<>();
        List<String> userList = StringUtil.convertList(userIds);

        //角色id删除用户
        Map<String, Object> map = new HashMap<>();
        map.put("deleteTime",new Date());
        map.put("deleteUserId",logonUser.getId());
        map.put("roleId",roleId);
        userRoleDao.deleteByRoleId(map);

        if(CollectionUtils.isNotEmpty(userList)){
            userList.stream().forEach(userId ->{

                UserRole userRole = new UserRole();
                userRole.setId(UuidUtil.get32UUID());
                userRole.setRole(new Role(roleId));
                userRole.setUser(new User(userId));
                userRole.setCreateTime(new Date());
                userRole.setCreateUserId(logonUser.getId());
                userRoles.add(userRole);
            });
        }

        if(CollectionUtils.isNotEmpty(userRoles)){
            userRoleDao.saveList(userRoles);
        }
    }

    /**
     * 用户角色新增
     * @param userId
     * @param roleIds
     * @param logonUser
     */
    public void saveUserRoleList(String userId, String roleIds, User logonUser) {

        List<UserRole> userRoles = new ArrayList<>();
        List<String> roleList = StringUtil.convertList(roleIds);

        //用户id删除角色
        Map<String, Object> map = new HashMap<>();
        map.put("deleteTime",new Date());
        map.put("deleteUserId",logonUser.getId());
        map.put("userId",userId);
        userRoleDao.deleteByUserId(map);

        if(CollectionUtils.isNotEmpty(roleList)){
            roleList.stream().forEach(roleId ->{

                UserRole userRole = new UserRole();
                userRole.setId(UuidUtil.get32UUID());
                userRole.setRole(new Role(roleId));
                userRole.setUser(new User(userId));
                userRole.setCreateTime(new Date());
                userRole.setCreateUserId(logonUser.getId());

                userRoles.add(userRole);
            });
        }

        if(CollectionUtils.isNotEmpty(userRoles)){
            userRoleDao.saveList(userRoles);
        }
    }

    /**
     * 角色id查询用户
     * @param roleId
     * @return
     */
    @Override
    public List<UserRole> getByRoleId(String roleId) {
        return userRoleDao.getByRoleId(roleId);
    }

    /**
     * 用户id查询角色
     * @param userId
     * @return
     */
    @Override
    public List<UserRole> getByUserId(String userId) {
        return userRoleDao.getByUserId(userId);
    }
}
