package com.wjx.study.controller;

import com.wjx.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc: 配置中心测试
 * @File name：com.wjx.study.controller.ConfigController
 * @Create on：2022/3/21 14:17
 * @Author：wjx
 */
@Api(tags = {"配置中心测试"})
@RefreshScope
@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Value("${wjx.config.name}")
    private String name;

    @ApiOperation(value = "从配置中心获取配置内容-硬编码方式")
    @GetMapping(value = "/get1")
    public Result<String> getConfigFromNacos1(){
        return Result.ok(applicationContext.getEnvironment().getProperty("wjx.config.name"));
    }

    @ApiOperation(value = "从配置中心获取配置内容-注解方式")
    @GetMapping(value = "/get2")
    public Result<String> getConfigFromNacos2(){
        return Result.ok(name);
    }
}
