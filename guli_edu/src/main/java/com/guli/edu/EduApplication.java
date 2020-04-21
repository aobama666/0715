package com.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 当启动类加载的时候，它会把此包及其子包下所有的注解类加载
 * 建议：启动工程的时候，用Debug模式启动
 * 在企业中项目很大、启动也需要30m - 60m或者更长，方便检测
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}
