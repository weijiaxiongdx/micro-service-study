package com.wjx.study.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.service.GoodsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
/**
 * @Desc: redis缓存预热，一般把热点商品提前放入缓存
 * @File name：com.wjx.study.config.RedisHandler
 * @Create on：2022/4/6 10:51
 * @Author：wjx
 */
@Component
public class RedisHandler implements InitializingBean {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsService goodsService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void afterPropertiesSet() throws Exception {

        //查询商品并放入缓存
        Long goodsId = 1L;//热点商品
        GoodsVO goods = goodsService.selectGoodsById(goodsId);
        String goodsJson = MAPPER.writeValueAsString(goods);
        redisTemplate.opsForValue().set("goods:id" + goodsId,goodsJson);


        //查询库存并放入缓存，缓存key前缀为goods:stock:id todo
    }
}
