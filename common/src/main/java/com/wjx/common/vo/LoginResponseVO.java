package com.wjx.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Desc: 登录响应VO
 * @File name：com.wjx.common.vo.LoginResponseVO
 * @Create on：2022/3/14 9:29
 * @Author：wjx
 */
@Data
@ApiModel("登录返回信息")
public class LoginResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "token过期时间，单位秒")
    private Long expire;
}
