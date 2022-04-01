package com.wjx.study.service;

/**
 * @Desc:
 * @File name：com.wjx.study.service.RedisService
 * @Create on：2022/4/1 14:51
 * @Author：wjx
 */
public interface RedisService {

    /**
     * @Des redisson锁测试
     * @Date 2022/4/1 14:57
     * @Param
     * @Return
     * @Author wjx
     */
    String redissonLockTest(Long userId);
}
