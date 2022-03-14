package com.wjx.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Desc:
 * @File name：com.wjx.study.AuthController
 * @Create on：2022/3/14 8:46
 * @Author：wjx
 */
@EnableFeignClients
@ComponentScan(basePackages = "com.wjx.*")
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
