package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import com.yuanxiatech.xgj.funeral.system.model.RoleQueryParam;
import com.yuanxiatech.xgj.funeral.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @date 2020/6/9 19:44
 **/
@RestController
public class RoleController extends SystemBaseController {

    private RoleService roleService;

    @Autowired(required = false)
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @PostMapping(value = "/addRole")
    public Result addRole(Role role) {

        User logonUser = getLogonUser();
        role.setCreateTime(new Date());
        role.setCreateUserId(logonUser.getId());
        roleService.save(role);
        return jsonWithStandardStatus();
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteRole")
    public Result deleteRole(String ids) {

        roleService.deleteRole(ids, getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @PostMapping(value = "/editRole")
    public Result editRole(Role role) {

        User logonUser = getLogonUser();
        role.setLastUpdateTime(new Date());
        role.setLastUpdateUserId(logonUser.getId());
        roleService.update(role);
        return jsonWithStandardStatus();
    }

    /**
     * 角色分页查询
     *
     * @param roleQueryParam
     * @return
     */
    @GetMapping(value = "/getRoleByParam")
    public Result getRoleByParam(RoleQueryParam roleQueryParam) {

        User logonUser = getLogonUser();
        DataSet<Role> roleDataSet = roleService.queryByParam(roleQueryParam, logonUser);
        return jsonWithRecord(roleDataSet);
    }


    /**
     * 初始化机构的用户
     *
     * @param partnerId
     * @return
     */
    @GetMapping(value = "/partnerUserList")
    public Result initUserList(String partnerId) {

        List<User> userList = roleService.initUserList(partnerId);
        return jsonWithRecord(userList);
    }

    /**
     * 初始化机构的角色
     *
     * @param partnerId
     * @return
     */
    @GetMapping(value = "/partnerRoleList")
    public Result initRoleList(String partnerId) {

        List<Role> roleList = roleService.initRoleList(partnerId);
        return jsonWithRecord(roleList);
    }
}
