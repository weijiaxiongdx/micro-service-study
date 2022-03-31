package com.wjx.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Desc:
 * @File name：com.wjx.common.config.RedisConfig
 * @Create on：2022/3/14 11:42
 * @Author：wjx
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory factory;

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
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //redisTemplate.setKeySerializer(new StringRedisSerializer());//也可以使用下面一行封装好的方法
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
