package com.wjx.study.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
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

    @Autowired
    private Cache<Long,GoodsVO> goodsCache;

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

    /**
     * @Des 查询商品详情3，增加caffeine缓存，缓存查不到再查数据库
     * @Date 2022/4/4 15:19
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsFromCaffeine(Long id){
        return goodsCache.get(id, goodsId->selectGoodsById(goodsId));
    }

    @Override
    public int updateGoodsById(Long id){
         int count = goodsMapper.updateGoodsById(id);
         return count;
    }

}
