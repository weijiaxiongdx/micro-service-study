package com.wjx.study.service.impl;

import cn.hutool.core.lang.UUID;
import com.wjx.study.service.ILock;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: redis分布式锁版本二，简单的分布式锁，解决误删问题，在获取锁的时候，value加上UUID，释放锁的时候进行判断，防止误删
 *        但是这样就没有误删问题了吗？？？答案是依旧有误删问题，因为释放锁操作分为判断和删除两个操作，这两个操作不是原子性的，可能在释放锁时，t1线程判断成功了，在删除锁的时候
 *        进入阻塞状态了(正好垃圾回收器在执行垃圾回收操作)，导致锁超时自动释放了，此时t2线程获取到锁执行业务逻辑中，t1线程执行了锁删除操作，也就删除了别的线程获取到的锁
 * @File name：com.wjx.study.service.impl.SimpleRedisLock2
 * @Create on：2022/4/1 11:05
 * @Author：wjx
 */
public class SimpleRedisLock2 implements ILock {

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

    public SimpleRedisLock2(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }


    /**
     * @Des 获取锁，setIfAbsent对应redis的set k1000 v1000 ex 60 nx或set k1000 v1000 nx ex 60命令
     * @Date 2022/4/1 11:05
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
   * @Date 2022/4/1 11:05
   * @Param
   * @Return
   * @Author wjx
   */
    @Override
    public void unLock() {
        String value = stringRedisTemplate.opsForValue().get(KEY_PREFIX+name);
        String value2 = VALUE_PREFIX + Thread.currentThread().getId();
        if(StringUtils.equals(value,value2)){
            stringRedisTemplate.delete(KEY_PREFIX+name);
        }
    }
}
