package com.yuanxiatech.xgj.funeral.base.controller;

import com.yuanxiatech.xgj.common.rbac.holder.UserContextHolder;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.model.UserContext;
import com.yuanxiatech.xgj.core.web.spring.JsonBasedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class CommonBaseController extends JsonBasedController {

    public static final String COMMON_MODULE_NAME = "/common";

    public static final String DEFAULT_SCREEN_WIDTH = "960";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 6357869213649815391L;

    private HttpServletRequest request;

    @Autowired(required = false)
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 保存已登录用户在当前线程中
     *
     * @param userContext
     */
    protected void setUserContext(UserContext userContext) {
        UserContextHolder.setUserContext(userContext);
    }

    /**
     * 获取已登录用户信息
     */
    protected UserContext getUserContext() {
        return UserContextHolder.getUserContext();
    }

    /**
     * 移除线程中的用户信息
     */
    protected void removeUserContext() {
        UserContextHolder.clear();
    }

    /**
     * 获取登陆用户信息
     *
     * @return
     */
    protected User getLogonUser() {
        UserContext userContext = getUserContext();
        return userContext != null ? userContext.getUser() : null;
    }

    /**
     * 获取登陆用户id
     *
     * @return
     */
    protected String getLogonUserId() {
        User user = getLogonUser();
        return user != null ? user.getId() : null;
    }

    /**
     * 获得请求地址ip
     *
     * @return
     */
    protected String getClientIP() {
        if (request == null)
            return null;
        String s = request.getHeader("X-Forwarded-For");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = request.getHeader("Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = request.getHeader("WL-Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = request.getHeader("HTTP_CLIENT_IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = request.getRemoteAddr();
        return s;
    }
}
