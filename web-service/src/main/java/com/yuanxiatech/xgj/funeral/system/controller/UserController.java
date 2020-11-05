package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.model.UserQueryParam;
import com.yuanxiatech.xgj.common.rbac.service.UserService;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.security.Md5DigestPasswordManager;
import com.yuanxiatech.xgj.core.security.PasswordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class UserController extends SystemBaseController {

    public static final String MODULE_NAME = "/user";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    private PasswordManager passwordManager = new Md5DigestPasswordManager();

    @Autowired(required = false)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping(MODULE_NAME + "/add")
    public Result addUser(User user) {
        User logonUser = getLogonUser();
        user.setCreateUserId(logonUser.getId());
        user.setCreateTime(new Date());
        user.setPassword(passwordManager.encrypt(user.getPassword()));
        userService.save(user);
        return jsonWithStandardStatus();
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @PostMapping(MODULE_NAME + "/delete")
    public Result deleteUser(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        User logonUser = getLogonUser();
        List<User> userList = new ArrayList<User>();
        idList.stream().forEach(id -> {
            User user = new User();
            user.setId(id);
            user.setDeleteUserId(logonUser.getId());
            user.setDeleteTime(new Date());
            user.setDataStatus(User.DATA_STATUS_DELETE);
            userList.add(user);
        });
        userService.logicDelete(userList);
        return jsonWithStandardStatus();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping(MODULE_NAME + "/password")
    public Result editPassword(User user) {
        userService.editPassword(user.getPassword(), user.getId());
        return jsonWithStandardStatus();
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @PostMapping(MODULE_NAME + "/edit")
    public Result editUser(User user) {
        User logonUser = getLogonUser();
        user.setLastUpdateUserId(logonUser.getId());
        user.setLastUpdateTime(new Date());
        userService.update(user);
        return jsonWithStandardStatus();
    }

    /**
     * 获取用户列表
     *
     * @param userQueryParam
     * @return
     */
    @GetMapping(MODULE_NAME + "/list")
    public Result queryUserParam(UserQueryParam userQueryParam) {
        DataSet<User> userDataSet = userService.queryByParam(userQueryParam);
        return jsonWithRecord(userDataSet);
    }
}
