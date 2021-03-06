package com.wjx.study.service;

import com.wjx.common.dto.OrderCreateDTO;
import com.wjx.common.vo.GoodsVO;
import com.wjx.common.vo.OrderVO;

/**
 * 订单服务
 */
public interface OrderService {

    OrderVO selectOrderById(Long id);

    GoodsVO selectGoodsById1(Long id);

    GoodsVO selectGoodsById2(Long id);

    GoodsVO selectGoodsById3(Long id);

    GoodsVO selectGoodsById4(Long id);

    String selectGoodsById5(Long id);

    GoodsVO selectGoodsById6(Long id);

    void createOrder(OrderCreateDTO dto);

    void seataTest(Long id) throws Exception;
}
