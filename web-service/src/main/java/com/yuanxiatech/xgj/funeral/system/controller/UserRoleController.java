package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.funeral.system.model.UserRole;
import com.yuanxiatech.xgj.funeral.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRoleController extends SystemBaseController {

    private UserRoleService userRoleService;

    @Autowired(required = false)
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * @description 角色用户新增
     * @date 2020/6/11 15:06
     */
    @PostMapping(value = "/roleUser")
    public Result addRoleUser(String roleId, String userIds) {

        userRoleService.saveRoleUserList(roleId, userIds, getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * @description 用户角色新增
     * @date 2020/6/11 15:06
     */
    @PostMapping(value = "/userRole")
    public Result addUserRole(String userId, String roleIds) {

        userRoleService.saveUserRoleList(userId, roleIds, getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 初始化角色绑定的用户
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/roleList")
    public Result getByRoleId(String roleId) {

        List<UserRole> userRoleList = userRoleService.getByRoleId(roleId);
        return jsonWithRecord(userRoleList);
    }

    /**
     * 初始化用户绑定的角色
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/userList")
    public Result getByUserId(String userId) {

        List<UserRole> userRoleList = userRoleService.getByUserId(userId);
        return jsonWithRecord(userRoleList);
    }

}
