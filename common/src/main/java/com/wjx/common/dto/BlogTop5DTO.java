package com.wjx.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @Desc: 查询博客最早点赞的用户的信息入参
 * @File name：com.wjx.common.dto.BlogTop5DTO
 * @Create on：2022/4/2 13:28
 * @Author：wjx
 */
@ApiModel(value = "博客点赞入参")
@Data
public class BlogTop5DTO {

    @NotNull(message = "博客id不能为空")
    @ApiModelProperty("博客id")
    private Long id;
}
