package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.vo.UserVO;
import com.wjx.study.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @Desc: 用户服务
 * @File name：com.wjx.study.controller.UserController
 * @Create on：2022/3/14 10:04
 * @Author：wjx
 */
@Api(tags = {"用户服务"})
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "通过手机号查询用户信息")
    @GetMapping(value = "/selectByPhone")
    public Result<UserVO> selectByPhone(@RequestParam(value = "phone") @NotBlank(message = "手机号不能为空") String phone){
        return Result.ok(userService.selectByPhone(phone));
    }
}
