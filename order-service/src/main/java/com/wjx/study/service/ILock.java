package com.wjx.study.service;

/**
 * @Desc: redis分布式锁接口
 * @File name：com.wjx.study.service.ILock
 * @Create on：2022/4/1 9:50
 * @Author：wjx
 */
public interface ILock {

    /**
     * @Des
     * @Date 2022/4/1 9:52
     * @Param timeout锁持有的超时时间，过期后自动释放
     * @Return true表示获取锁成功，false表示获取锁失败
     * @Author wjx
     */
     boolean tryLock(long timeout);

     /**
      * @Des 释放锁
      * @Date 2022/4/1 9:52
      * @Param
      * @Return
      * @Author wjx
      */
     void unLock();
}
