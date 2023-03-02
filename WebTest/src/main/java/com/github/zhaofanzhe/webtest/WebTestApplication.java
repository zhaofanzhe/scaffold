package com.github.zhaofanzhe.webtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@MapperScan("com.github.zhaofanzhe.webtest")
@ConfigurationPropertiesScan
@EnableCaching
@EnableAsync
@SpringBootApplication
public class WebTestApplication {

    public static void main(String[] args) {
        // 设置当前时区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(WebTestApplication.class, args);
    }

}
