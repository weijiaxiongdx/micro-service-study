package com.wjx.study.dao;

import com.wjx.common.vo.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserVO selectByPhone(@Param("phone") String phone);
}
