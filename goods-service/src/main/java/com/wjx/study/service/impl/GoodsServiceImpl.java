package com.wjx.study.service.impl;

import com.wjx.common.vo.GoodsVO;
import com.wjx.study.dao.GoodsMapper;
import com.wjx.study.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc: 商品服务
 * @File name：com.wjx.study.service.impl.GoodsServiceImpl
 * @Create on：2022/3/8 11:04
 * @Author：wjx
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @Des 查询商品详情
     * @Date 2022/3/8 11:04
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsById(Long id) {
        return goodsMapper.selectGoodsById(id);
    }
}
