

package com.wjx.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Desc: 登录入参
 * @File name：com.wjx.common.form.LoginForm
 * @Create on：2022/3/14 9:33
 * @Author：wjx
 */
@Data
@ApiModel(value = "登录入参")
public class LoginForm {

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = "(\\+\\d+)?1[123456789]\\d{9}$",message = "手机号码非法")
    private String phone;

    @ApiModelProperty(value = "验证码",required = true)
    @NotBlank(message="验证码不能为空")
    private String code;

}
