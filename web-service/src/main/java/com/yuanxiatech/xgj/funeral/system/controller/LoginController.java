package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.common.partner.model.Partner;
import com.yuanxiatech.xgj.common.partner.service.PartnerService;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.model.UserQueryParam;
import com.yuanxiatech.xgj.common.rbac.service.UserService;
import com.yuanxiatech.xgj.core.exception.BusinessException;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.security.Md5DigestPasswordManager;
import com.yuanxiatech.xgj.core.security.PasswordManager;
import com.yuanxiatech.xgj.core.utils.RandomsUtil;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import com.yuanxiatech.xgj.funeral.base.cache.InternetplusCacheManage;
import com.yuanxiatech.xgj.funeral.system.config.FuneralCookieConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class LoginController extends SystemBaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    private InternetplusCacheManage cacheManage;

    private FuneralCookieConfig cookieConfig;

    private PartnerService partnerService;

    @Autowired(required = false)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = false)
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Autowired(required = false)
    public void setCacheManage(InternetplusCacheManage cacheManage) {
        this.cacheManage = cacheManage;
    }

    @Autowired(required = false)
    public void setCookieConfig(FuneralCookieConfig cookieConfig) {
        this.cookieConfig = cookieConfig;
    }

    @Autowired(required = false)
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * 登录账号密码匹配
     *
     * @param username
     * @param password
     * @param response
     * @return
     */
    @PostMapping(value = "/login")
    public Result loginByUserNameAndPwd(String username, String password, HttpServletResponse response) {
        User user = userService.login(username, password);
        user.setPassword("");

        if (user.getPartner() == null) {
            throw new BusinessException("用户没有配置机构");
        }

        if (StringUtils.isEmpty(user.getPartner().getId())) {
            throw new BusinessException("用户没有配置机构");
        }

        Partner partner = partnerService.getById(user.getPartner().getId());
        user.setPartner(partner);

        /**
         * ticket设置
         */
        int expiresMillis = 8 * 3600;
        String ticket = "TICKET_" + UuidUtil.get32UUID() + "_" + RandomsUtil.getAuthCodeNumber(6);
        cacheManage.set(ticket, user, expiresMillis);

        Cookie cookie = new Cookie(cookieConfig.getName(), ticket);
        cookie.setMaxAge(expiresMillis);
        cookie.setDomain(cookieConfig.getDomain());
        cookie.setPath(cookieConfig.getPath());
        response.addCookie(cookie);

        return jsonWithRecord(user);
    }
}
