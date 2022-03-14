

package com.wjx.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @Desc:
 * @File name：com.wjx.common.entity.UserEntity
 * @Create on：2022/3/14 9:51
 * @Author：wjx
 */
@Data
@ApiModel(value = "用户信息")
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
	private Long id;

    @ApiModelProperty(value = "昵称")
	private String name;

    @ApiModelProperty(value = "手机号")
	private String phone;
}
