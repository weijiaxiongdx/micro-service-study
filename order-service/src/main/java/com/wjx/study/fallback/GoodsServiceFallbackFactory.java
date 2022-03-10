package com.wjx.study.fallback;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.feign.GoodsServiceFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * @Desc: 订单服务远程调用商品服务容错类，泛型参数表示要为哪个接口产生容错类
 *        这种方式可以拿到远程调用产生的异常信息
 * @File name：com.wjx.study.fallback.GoodsServiceFallbackFactory
 * @Create on：2022/3/10 9:28
 * @Author：wjx
 */
@Component
public class GoodsServiceFallbackFactory implements FallbackFactory<GoodsServiceFeign>{

    @Override
    public GoodsServiceFeign create(Throwable throwable) {
        return id -> {
            GoodsVO goods = new GoodsVO();
            goods.setId(-100L);
            goods.setName("不存在商品-能拿到异常信息");
            goods.setStock(0);
            goods.setSalePrice(BigDecimal.ZERO);
            return Result.ok(goods);
        };
    }
}
