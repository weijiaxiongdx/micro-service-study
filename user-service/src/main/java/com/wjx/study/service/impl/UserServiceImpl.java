package com.wjx.study.service.impl;

import com.wjx.common.vo.UserVO;
import com.wjx.study.dao.UserMapper;
import com.wjx.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc: 用户服务
 * @File name：com.wjx.study.service.impl.UserServiceImpl
 * @Create on：2022/3/14 10:05
 * @Author：wjx
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO selectByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }
}
