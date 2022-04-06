package com.wjx.study.service;

import com.wjx.common.vo.GoodsVO;

public interface GoodsService {

    /**
     * @Des 查询商品详情
     * @Date 2022/3/8 11:03
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    GoodsVO selectGoodsById(Long id);

    /**
     * @Des 查询商品详情3，增加caffeine缓存，缓存查不到再查数据库
     * @Date 2022/4/4 15:18
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    GoodsVO selectGoodsFromCaffeine(Long id);

}
