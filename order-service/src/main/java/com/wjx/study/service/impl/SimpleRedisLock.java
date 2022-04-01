package com.wjx.study.service.impl;

import com.wjx.study.service.ILock;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: redis分布式锁版本一，简单的分布式锁
 *        存在问题：释放锁的时候会误删别人的锁(业务执行时间超过锁的过期时间，导致锁提前释放)，即使在释放锁的时候使用存入的线程id判断，也会有这样的问题(因为集群环境下不同的JVM线程id可能相同)
 * @File name：com.wjx.study.service.impl.SimpleRedisLock
 * @Create on：2022/4/1 9:55
 * @Author：wjx
 */
public class SimpleRedisLock implements ILock {

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

    public SimpleRedisLock(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }


    /**
     * @Des 获取锁，setIfAbsent对应redis的set k1000 v1000 ex 60 nx或set k1000 v1000 nx ex 60命令
     * @Date 2022/4/1 10:10
     * @Param
     * @Return
     * @Author wjx
     */
    @Override
    public boolean tryLock(long timeout) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX+name,Thread.currentThread().getId()+"", timeout, TimeUnit.SECONDS);
        //return result;//如果result为null,则拆箱时报空指针错误，所以使用下面的方式返回
        return Boolean.TRUE.equals(result);
    }

  /**
   * @Des 释放锁
   * @Date 2022/4/1 10:10
   * @Param
   * @Return
   * @Author wjx
   */
    @Override
    public void unLock() {
        stringRedisTemplate.delete(KEY_PREFIX+name);
    }
}
