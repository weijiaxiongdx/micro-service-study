package com.wjx.study.service.impl;

import com.wjx.study.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @File name：com.wjx.study.service.impl.RedisServiceImpl
 * @Create on：2022/4/1 14:51
 * @Author：wjx
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String redissonLockTest(Long userId){
        //可重入
        RLock lock = redissonClient.getLock("lock:order:" + userId);
        boolean isLock = lock.tryLock();
        if(!isLock){
            log.info("获取锁失败");
            return "获取锁失败";
        }

        try {
            log.info("获取锁成功-处理业务逻辑");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("释放了锁");
        }

        return "获取锁成功";
    }
}
