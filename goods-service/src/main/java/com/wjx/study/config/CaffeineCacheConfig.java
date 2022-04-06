package com.wjx.study.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wjx.common.vo.GoodsVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: caffeine本地缓存配置
 * @File name：com.wjx.study.config.CacheConfig
 * @Create on：2022/4/4 12:32
 * @Author：wjx
 */
@Configuration
public class CaffeineCacheConfig {

    /**
     * @Des caffeine缓存配置
     *      三种缓存驱逐策略
     *        基于容量：设置缓存的数量上限，超过容量时，先清除时间上更早的数据？
     *        基于时间：设置缓存的有效时间
     *        基于引用：设置缓存为软引入或弱引用，利用GC来回收数据，性能较差，不建议使用
     * @Date 2022/4/4 15:04
     * @Param
     * @Return
     * @Author wjx
     */
    @Bean
    public Cache<Long, GoodsVO> goodsCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)//最后一次写入或访问后经过多久过期，基于时间的缓存驱逐策略
                .initialCapacity(1000)//初始缓存空间大小
                .maximumSize(1000)//缓存的最大条数，基于容量的缓存驱逐策略
                .build();
    }

    /**
     * @Des 一般会按照业务定义不同的缓存，例如商品缓存、库存缓存
     * @Date 2022/4/4 15:12
     * @Param
     * @Return
     * @Author wjx
     */
    @Bean
    public Cache<String, Object> stockCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .initialCapacity(1000)
                .maximumSize(1000)
                .build();
    }
}
