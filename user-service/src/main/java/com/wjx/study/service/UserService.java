package com.wjx.study.service;

import com.wjx.common.vo.UserVO;

/**
 * @Desc: 用户服务
 * @File name：com.wjx.study.service.UserService
 * @Create on：2022/3/14 10:05
 * @Author：wjx
 */
public interface UserService {

    UserVO selectByPhone(String phone);
}
