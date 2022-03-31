package com.wjx.study.controller;

import com.wjx.common.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"redis测试2"})
@RestController
@RequestMapping(value = "/redis2")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "redis设置中文值测试2")
    @GetMapping(value = "set2")
    public void set(){

        redisUtils.set("k102","中文测试2");
        System.out.println(redisUtils.get("k102"));

        redisUtils.set("k103","汉语测试2");
        System.out.println(redisUtils.get("k103"));
    }
}
