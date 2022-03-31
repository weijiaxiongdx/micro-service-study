package com.wjx.study.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"redis测试"})
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "redis设置中文值测试")
    @GetMapping(value = "set")
    public void set(){
        /**
         * spring-boot整合redis时默认使用jDK序列化器来进行序列化和反序列化，即底层使用ObjectOutputStream来写入数据到redis中的，存在中文显示问题，所以需要自己设置序列化器，以解决中文显示问题
         * 一般key使用StringRedisSerializer序列化器、value或hashValue使用GenericJackson2JsonRedisSerializer序列化器(可解决在客户端-如redis-cli中的中文显示问题，
         * 但实际测试时，即使使用了此序列化器，显示的依旧不是中文，为什么呢？？？)
         *
         * 但是使用GenericJackson2JsonRedisSerializer序列化器时，会在redis中存入额外的信息(每个对象会有类型信息"@class":"com.wjx.common.vo.UserVO")，以便反序列化，这个内存占用开锁比较大，所以
         * 一般情况下程序员需要手动进行序列化(set时，将java对象转成json字符串)和反序列化(get时，将json字符串转成java对象)
         *
         * 最终value还是配置使用StringRedisSerializer序列化器(也可直接使用StringRedisTemplate进行操作，不需要配置RedisTemplate的bean)，多作了手动序列化和反序列化操作
         * 所以在RedisUtils中，set的时候都会把value转成json字符串，就这么用
         *
         * 注意注意：其它客户端(比如RedisInsight-v2-win-installer)正常显示中文
         */
        redisTemplate.opsForValue().set("k100","中文测试");
        System.out.println(redisTemplate.opsForValue().get("k100"));//获取的是“中文测试”，但在redis-cli中查看的不是中文呢？？？

        stringRedisTemplate.opsForValue().set("k101","汉语测试");
        System.out.println(stringRedisTemplate.opsForValue().get("k101"));//获取的是“中文测试”，但在redis-cli中查看的不是中文呢？？？
    }
}
