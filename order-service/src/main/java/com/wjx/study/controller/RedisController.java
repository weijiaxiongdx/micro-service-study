package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.utils.RedisUtils;
import com.wjx.study.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = {"redis测试2"})
@RestController
@RequestMapping(value = "/redis2")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "redis设置中文值测试2")
    @GetMapping(value = "set2")
    public void set(){

        redisUtils.set("k102","中文测试2");
        System.out.println(redisUtils.get("k102"));

        redisUtils.set("k103","汉语测试2");
        System.out.println(redisUtils.get("k103"));
    }

    @ApiOperation(value = "redisson分布式锁测试")
    @GetMapping(value = "redissonLockTest")
    public Result<String> redissonLockTest(@RequestParam(value = "userId") @Valid @NotNull(message = "用户id不能为空") Long userId){
         return Result.ok(redisService.redissonLockTest(userId));
    }
}
