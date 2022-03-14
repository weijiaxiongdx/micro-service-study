package com.wjx.study.feign;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.fallback.GoodsServiceFallback;
import com.wjx.study.fallback.GoodsServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Desc:远程调用客户端
 * @File name：com.wjx.study.feign.GoodsServiceFeign
 * @Create on：2022/3/8 17:51
 * @Author：wjx
 *
 * fallback可以指定容错类，拿不到远程调用异常信息
 * fallbackFactory也可以指定容错类，可以拿到远程调用异常信息
 * fallback的优先级高于fallbackFactory的优先级，两者同时配置，则会进入fallback的容错类
 */
@FeignClient(value = "goods-service",
            fallback = GoodsServiceFallback.class,
            fallbackFactory = GoodsServiceFallbackFactory.class
             )
public interface GoodsServiceFeign {

    @GetMapping(value = "/goods/detail")
    Result<GoodsVO> selectGoodsById(@RequestParam("goodsId") Long id);

    @GetMapping(value = "/goods/detail2")
    Result<GoodsVO> selectGoodsById2(@RequestParam("goodsId") Long id);
}
