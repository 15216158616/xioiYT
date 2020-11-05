package com.yuanxiatech.xgj.funeral.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cookie")
public class FuneralCookieConfig {

    private String name;//名称

    private String domain;//域名

    private String path;//路径

    private Integer expHour;//过期时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getExpHour() {
        return expHour;
    }

    public void setExpHour(Integer expHour) {
        this.expHour = expHour;
    }

}
