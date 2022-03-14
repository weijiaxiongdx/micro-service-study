package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.constant.Constant;
import com.wjx.common.except.RRException;
import com.wjx.common.form.LoginForm;
import com.wjx.common.utils.RedisUtils;
import com.wjx.common.vo.LoginResponseVO;
import com.wjx.common.vo.UserVO;
import com.wjx.study.feign.UserServiceFeign;
import com.wjx.study.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Desc: 登录服务
 * @File name：com.wjx.study.controller.LonginController
 * @Create on：2022/3/13 19:06
 * @Author：wjx
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class LonginController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/loginByCode")
    @ApiOperation("手机号+验证码登录")
    public Result<LoginResponseVO> loginByCode(@RequestBody @Valid LoginForm form) {
        String phone = form.getPhone();
        String key = String.format(Constant.LOGIN_SMS_CODE_KEY,phone);

        if (!redisUtils.hasKey(key)) {
            throw new RRException(Constant.RESULT.FI1012,"验证码无效");
        }

        String code = redisUtils.get(key, String.class);
        if (!code.equals(form.getCode())) {
            throw new RRException(Constant.RESULT.FI1011,"验证码有误");
        }

        String token = jwtUtils.generateToken(phone);
        Result<UserVO> user = userServiceFeign.selectByPhone(phone);
        if(user != null){
            //缓存用户信息
            cacheUserInfo(token,user.getData());
        }

        LoginResponseVO responseVo = new LoginResponseVO();
        responseVo.setToken(token);
        responseVo.setExpire(jwtUtils.getExpire());
        log.info("生成的token为: {}",token);
        return Result.ok(responseVo);
    }

    /**
     * 缓存用户信息
     * @param user
     */
    protected void cacheUserInfo(String token,UserVO user){
        String key = String.format(Constant.USER_INFO_CACHE,token);

        //有效时间7天
        redisUtils.set(key,user,jwtUtils.getExpire()*1000);
    }
}
