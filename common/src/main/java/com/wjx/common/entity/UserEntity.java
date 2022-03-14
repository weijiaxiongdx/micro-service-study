

package com.wjx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("pp_user")
@ApiModel(value = "用户实体")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
    @ApiModelProperty(value = "用户id")
	private Long id;

    @ApiModelProperty(value = "昵称")
	private String name;

    @ApiModelProperty(value = "手机号")
	private String phone;
}
