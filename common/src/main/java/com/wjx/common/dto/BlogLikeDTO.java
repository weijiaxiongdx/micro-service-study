package com.wjx.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @Desc: 博客点赞入参
 * @File name：com.wjx.common.dto.BlogLikeDTO
 * @Create on：2022/4/2 11:05
 * @Author：wjx
 */
@ApiModel(value = "博客点赞入参")
@Data
public class BlogLikeDTO {

    @NotNull(message = "博客id不能为空")
    @ApiModelProperty("博客id")
    private Long id;

    @ApiModelProperty(value = "当前登录用户id",hidden = true)
    private Long userId;
}
