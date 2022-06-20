package com.wjx.study.fallback;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.study.feign.GoodsServiceFeign;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Desc: 订单服务远程调用商品服务容错类，需要实现加@FeignClient注解的接口中的所有方法，远程调用失败就进入该类同名方法，执行容错逻辑
 *        这种方式拿不到远程调用产生的异常信息
 * @File name：com.wjx.study.fallback.GoodsServiceFallback
 * @Create on：2022/3/9 17:04
 * @Author：wjx
 */
@Component
public class GoodsServiceFallback implements GoodsServiceFeign {

    @Override
    public Result<GoodsVO> selectGoodsById(Long id){
        // 容错逻辑
        GoodsVO goods = new GoodsVO();
        goods.setId(-100L);
        goods.setName("不存在商品");
        goods.setStock(0);
        goods.setSalePrice(BigDecimal.ZERO);
        return Result.ok(goods);
    }

    @Override
    public Result<GoodsVO> selectGoodsById2(Long id) {
        // 容错逻辑
        GoodsVO goods = new GoodsVO();
        goods.setId(-111L);
        goods.setName("不存在商品2");
        goods.setStock(0);
        goods.setSalePrice(BigDecimal.ZERO);
        return Result.ok(goods);
    }

    @Override
    public int updateGoodsById(Long id) {
        System.out.println("商品更新失败");
        return 0;
    }
}
