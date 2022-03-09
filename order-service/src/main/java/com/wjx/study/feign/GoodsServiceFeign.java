package com.wjx.study.feign;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.fallback.GoodsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Desc:远程调用客户端
 * @File name：com.wjx.study.feign.GoodsServiceFeign
 * @Create on：2022/3/8 17:51
 * @Author：wjx
 *
 * fallback可以指定容错类
 */
@FeignClient(value = "goods-service",fallback = GoodsServiceFallback.class)
public interface GoodsServiceFeign {

    @GetMapping(value = "/goods/detail")
    Result<GoodsVO> selectGoodsById(@RequestParam("goodsId") Long id);
}
