package com.wjx.study.fallback;

import com.wjx.common.Result;
import com.wjx.common.vo.UserVO;
import com.wjx.study.feign.UserServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @File name：com.wjx.study.fallback.UserServiceFallback
 * @Create on：2022/3/14 10:32
 * @Author：wjx
 */
@Component
public class UserServiceFallback implements UserServiceFeign {

    @Override
    public Result<UserVO> selectByPhone(String phone){
        // 容错逻辑
        UserVO user = new UserVO(-100L,"没有查到用户","183XXXXXXX");
        return Result.ok(user);
    }
}
