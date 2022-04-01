package com.wjx.study.service.impl;

import cn.hutool.core.lang.UUID;
import com.wjx.study.service.ILock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Desc: redis分布式锁版本三，使用lua脚本保证判断和删除操作的原子性，防止误删。基本可放到生产环境使用
 *        但是还存在以下问题
 *          不可重入、不可重试、超时释放(业务执行耗时较长，超过锁的过期时间)、主从一致性
 *          Redisson解决了这些问题
 * @File name：com.wjx.study.service.impl.SimpleRedisLock3
 * @Create on：2022/4/1 12:18
 * @Author：wjx
 */
public class SimpleRedisLock3 implements ILock {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 锁业务类型，如order、goods
     * 调用方加锁时，除了传order外，还可以拼接其它的，比如秒杀下单，一个人只能抢一单，则可以拼接上用户id，锁的粒度小了，性能更高，即order+userId
     */
    private String name;

    /**
     * 锁前缀
     */
    private static final String KEY_PREFIX = "lock:";

    /**
     * 值前缀，参数true表示去掉原生UUID中的“-”
     */
    private static final String VALUE_PREFIX = UUID.randomUUID().toString(true) + "_";

    private static final DefaultRedisScript<Long> redisScript;

    public SimpleRedisLock3(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }

    static {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis_release_lock.lua")));
    }


    /**
     * @Des 获取锁，setIfAbsent对应redis的set k1000 v1000 ex 60 nx或set k1000 v1000 nx ex 60命令
     * @Date 2022/4/1 12:18
     * @Param
     * @Return
     * @Author wjx
     */
    @Override
    public boolean tryLock(long timeout) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX+name,VALUE_PREFIX + Thread.currentThread().getId()+"", timeout, TimeUnit.SECONDS);
        //return result;//如果result为null,则拆箱时报空指针错误，所以使用下面的方式返回
        return Boolean.TRUE.equals(result);

    }

  /**
   * @Des 释放锁
   * @Date 2022/4/1 12:18
   * @Param
   * @Return
   * @Author wjx
   */
    @Override
    public void unLock() {
        stringRedisTemplate.execute(redisScript, Arrays.asList(KEY_PREFIX+name),VALUE_PREFIX + Thread.currentThread().getId());
    }
}
