package com.yuanxiatech.xgj.funeral.system.filter;

import com.yuanxiatech.xgj.common.rbac.model.CookieConfig;
import com.yuanxiatech.xgj.core.json.JsonUtils;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.web.spring.SpringContextHolder;
import com.yuanxiatech.xgj.common.rbac.holder.UserContextHolder;
import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.common.rbac.model.UserContext;
import com.yuanxiatech.xgj.funeral.base.cache.InternetplusCacheManage;
import com.yuanxiatech.xgj.funeral.system.config.FuneralCookieConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class LogonFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isRequestUrlExcluded(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            //更新cookie的过期时间
            String ticket = getRequestTicket(request, response);

            //通过userId去获取用户信息,设置到请求线程中，提供给后续使用
            InternetplusCacheManage cacheManage = SpringContextHolder.getBean(InternetplusCacheManage.class);
            Object cacheObject = cacheManage.get(ticket);

            if (cacheObject == null) {
                response.setContentType("application/json;charset=utf-8");
                Result result = new Result();
                result.setMsg("会话过期");
                result.setStatus(401);
                PrintWriter out = response.getWriter();
                out.println(JsonUtils.toJsonStr(result));
            } else {
                User user = (User) cacheObject;
                UserContext userContext = new UserContext();
                userContext.setUser(user);
                UserContextHolder.setUserContext(userContext);

                filterChain.doFilter(request, response);
                return;
            }
        } finally {
            UserContextHolder.clear();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取请求的票据
     * <p>
     * 分别按顺序从cookie、parameter、header中获取
     * </p>
     *
     * @param request
     * @param response
     * @return
     */
    private String getRequestTicket(HttpServletRequest request, HttpServletResponse response) {
        FuneralCookieConfig funeralCookieConfig = SpringContextHolder.getBean(FuneralCookieConfig.class);
        String ticket = "";
        String key = "yxappname";

        String yxappname = getRequestHeader(request, key);
        logger.info("yxappname: ", yxappname);
        if(StringUtils.isNotEmpty(yxappname)){
            InternetplusCacheManage cacheManage = SpringContextHolder.getBean(InternetplusCacheManage.class);
            Object cacheObject = cacheManage.get(yxappname);
            CookieConfig cookieConfig = new CookieConfig();
            if (cacheObject != null) {
                cookieConfig = (CookieConfig) cacheObject;
            }

            if (request.getCookies() != null) {
                List<Cookie> cookieList = Arrays.asList(request.getCookies());
                for (Cookie cookie : cookieList) {
                    if (cookie.getName().equals(cookieConfig.getName())) {
                        ticket = cookie.getValue();
                        //更新cookie过期时间
                        cookie.setMaxAge(Integer.valueOf(cookieConfig.getExpHour()) * 60 * 60);
                        cookie.setPath(cookieConfig.getPath());
                        cookie.setDomain(cookieConfig.getDomain());
                        response.addCookie(cookie);
                    }
                }
            }
            if (StringUtils.isBlank(ticket)) {
                ticket = request.getParameter(cookieConfig.getName());
            }

            if (StringUtils.isBlank(ticket)) {
                ticket = getRequestHeader(request, cookieConfig.getName());
            }
        }else {
            if (request.getCookies() != null) {
                List<Cookie> cookieList = Arrays.asList(request.getCookies());
                for (Cookie cookie : cookieList) {
                    if (cookie.getName().equals(funeralCookieConfig.getName())) {
                        ticket = cookie.getValue();
                        //更新cookie过期时间
                        cookie.setMaxAge(Integer.valueOf(funeralCookieConfig.getExpHour()) * 60 * 60);
                        cookie.setDomain(funeralCookieConfig.getDomain());
                        cookie.setPath(funeralCookieConfig.getPath());
                        response.addCookie(cookie);
                    }
                }
            }

            if (StringUtils.isBlank(ticket)) {
                ticket = request.getParameter(funeralCookieConfig.getName());
            }

            if (StringUtils.isBlank(ticket)) {
                ticket = getRequestHeader(request, funeralCookieConfig.getName());
            }
        }

        return ticket;
    }

    private String getRequestHeader(HttpServletRequest request, String headerName) {
        String headerVal = "";
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if (key.equals(headerName)) {
                headerVal = request.getHeader(headerName);
            }
        }
        return headerVal;
    }

    private boolean isRequestUrlExcluded(HttpServletRequest request) {
        if (this.logger.isInfoEnabled()) {
            this.logger.debug("request url: " + request.getRequestURL());
        }


        StringBuffer uriBuffer = new StringBuffer(request.getRequestURI());
        if (request.getQueryString() != null) {
            uriBuffer.append("?").append(request.getQueryString());
        }

        String requestUri = uriBuffer.toString();
        return matches(requestUri);
    }

    private boolean matches(String uri) {
        List<String> unLimitUries = Arrays.asList(
                "(.*)/login",
                "(.*)/status-query"
        );

        uri = uri.substring(uri.indexOf("/"));
        if (uri.indexOf("?") != -1) {
            uri = uri.substring(0, uri.indexOf("?"));
        }

        boolean match = false;
        for (String unLimitUri : unLimitUries) {
            if (uri.matches(unLimitUri)) {
                match = true;
                break;
            }
        }

        return match;
    }
}
