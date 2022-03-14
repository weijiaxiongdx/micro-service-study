package com.wjx.study.feign;

import com.wjx.common.Result;
import com.wjx.common.vo.UserVO;
import com.wjx.study.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Desc:
 * @File name：com.wjx.study.controller.feign.UserServiceFeign
 * @Create on：2022/3/14 10:29
 * @Author：wjx
 */
@FeignClient(value = "user-service",fallback = UserServiceFallback.class)
public interface UserServiceFeign {

    @GetMapping(value = "/user/selectByPhone")
    Result<UserVO> selectByPhone(@RequestParam("phone") String phone);

}
