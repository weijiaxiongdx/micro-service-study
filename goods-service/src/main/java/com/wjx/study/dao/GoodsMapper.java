package com.wjx.study.dao;

import com.wjx.common.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {

    GoodsVO selectGoodsById(@Param("id") Long id);
}
