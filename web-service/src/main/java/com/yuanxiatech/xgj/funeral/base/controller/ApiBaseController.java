package com.yuanxiatech.xgj.funeral.base.controller;

import com.yuanxiatech.xgj.common.rbac.holder.UserContextHolder;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.model.UserContext;
import com.yuanxiatech.xgj.core.web.spring.JsonBasedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class ApiBaseController extends JsonBasedController {

    public static final String BASE_SYSTEM_NAME = "/funeral-api";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 6357869213649815392L;

    private HttpSession httpSession;

    @Autowired(required = false)
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    protected void setSessionAttribute(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    protected <T> T getSessionAttribute(String key) {
        return (T) httpSession.getAttribute(key);
    }

    protected void removeSessionAttribute(String key) {
        httpSession.removeAttribute(key);
    }

    /**
     * 保存已登录用户在Session中
     *
     * @param userContext
     */
    protected void setUserContext(UserContext userContext) {
        UserContextHolder.setUserContext(userContext);
    }

    /**
     * 获取已登录用户
     */
    protected UserContext getUserContext() {
        return UserContextHolder.getUserContext();
    }

    /**
     * 移除Session中的用户信息
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
}
