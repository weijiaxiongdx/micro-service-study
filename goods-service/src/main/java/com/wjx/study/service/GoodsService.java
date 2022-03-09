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

}
