package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.service.UserService;
import com.yuanxiatech.xgj.funeral.system.dao.RoleDao;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import com.yuanxiatech.xgj.funeral.system.model.RoleQueryParam;
import com.yuanxiatech.xgj.funeral.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 19:41
 **/
@Service
public class RoleServiceImpl extends StringPojoAppBaseServiceImpl<Role> implements RoleService {

    private RoleDao roleDao;

    private UserService userService;

    @Autowired(required = false)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = false)
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    protected AppBaseDao<Role, String> getDao() {
        return roleDao;
    }

    /**
     * 删除角色
     * @param ids
     * @param logonUser
     */
    @Override
    public void deleteRole(String ids,User logonUser) {

        List<String> idList = StringUtil.convertList(ids);
        List<Role> roleList = new ArrayList<>();
        idList.stream().forEach(id ->{
            Role role = new Role();
            role.setId(id);
            role.setDataStatus(Role.DATA_STATUS_DELETE);
            role.setDeleteTime(new Date());
            role.setDeleteUserId(logonUser.getId());
            roleList.add(role);
        });

        super.logicDelete(roleList);
    }

    /**
     * 查询角色并分页
     * @param queryParam
     * @return
     */
    @Override
    public DataSet<Role> queryByParam(RoleQueryParam queryParam,User logonUser) {

        DataSet<Role> roleDataSet = super.queryByParam(queryParam);

        return roleDataSet;
    }

    /**
     * 初始化机构的用户
     * @param partnerId
     * @return
     */
    @Override
    public List<User> initUserList(String partnerId) {
        return userService.getByPartnerId(partnerId);
    }

    /**
     * 机构的角色
     * @param partnerId
     * @return
     */
    @Override
    public List<Role> initRoleList(String partnerId) {
        return roleDao.getByPartnerId(partnerId);
    }

    /**
     * 角色id和机构查询
     * @param roleIds
     * @param partnerId
     * @return
     */
    @Override
    public List<Role> getByRoleIdsAndPartnerId(List<String> roleIds, String partnerId) {
        return roleDao.getByRoleIdsAndPartnerId(roleIds,partnerId);
    }
}
