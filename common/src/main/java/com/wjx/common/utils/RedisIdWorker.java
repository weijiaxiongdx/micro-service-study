package com.wjx.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Desc: redis全局唯一id实现
 *        1.全局id生成器，需要满足的特性
 *          唯一性(全局唯一)、高可用(任何时候都能生成id)、高性能(生成速度快)、递增性(单调递增)、安全性(不要让别人轻易猜到id的规律)
 *        2.为了增加id的安全性，可以在自增的数值上拼接一些其它信息，最终结构如下
 *          第一个bit为符号位，永远为0；2-32总共31个bit为时间戳(当前时间到一个固定时间的时间戳差值，以秒为单位，可以使用69年)；剩下的32个bit为redis自增序列(理论支持每秒产生2^32个不同的id)
 *          00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000
 *
 * @File name：com.wjx.common.utils.RedisIdWorker
 * @Create on：2022/3/31 18:25
 * @Author：wjx
 */
@Component
public class RedisIdWorker {

    @Autowired
    private RedisUtils redisUtils;

    // 固定的时间戳，2022年1月1号 0时0分0秒对应的时间戳
    private static final long BEGIN_TIMESTAMP = 1640995200L;

    // 自增序列号位数
    private static final int BIT = 32;

    /**
     * @Des
     * @Date 2022/3/31 18:27
     * @Param keyPrefix表示业务类型，比如order、goods
     * @Return
     * @Author wjx
     */
    public long nextId(String keyPrefix){

        // 生成时间戳
        LocalDateTime now =LocalDateTime.now();
        long timestamp = now.toEpochSecond(ZoneOffset.UTC) - BEGIN_TIMESTAMP;

        // 生成自增序列，key以年月日区分，不至于超出范围、也方便统计
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        long count = redisUtils.incr("icr:" + keyPrefix + ":" + date,1);

        // 拼接并返回，左移是为了空出低32位来存自增序列，或上自增序列表示自增序列是什么就存的什么
        return timestamp << BIT | count;
    }


    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2022,1,1,0,0,0);
        long second = time.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);//1640995200
    }

}
