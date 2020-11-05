package com.yuanxiatech.xgj.funeral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 */
@SpringBootApplication
@ImportResource(value = {"classpath:spring-transaction.xml","classpath:spring-bean.xml"})
@ComponentScan(basePackages = {"com.yuanxiatech.xgj.funeral", "com.yuanxiatech.xgj.core", "com.yuanxiatech.xgj.common"})
@EnableCaching
public class FuneralSpringBoot extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FuneralSpringBoot.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FuneralSpringBoot.class, args);
    }

}
