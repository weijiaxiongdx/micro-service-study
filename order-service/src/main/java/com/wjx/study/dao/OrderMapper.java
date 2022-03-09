package com.wjx.study.dao;

import com.wjx.common.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    OrderVO selectOrderById(@Param("id") Long id);
}
