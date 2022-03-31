package com.wjx.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Desc: 创建订单入参
 * @File name：com.wjx.common.dto.CreateOrderDTO
 * @Create on：2022/3/31 10:21
 * @Author：wjx
 */
@ApiModel(value = "订单入参DTO")
@Data
public class OrderCreateDTO {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @NotNull(message = "商品id不能为空")
    @ApiModelProperty("商品id")
    private Long goodsId;

    @NotNull(message = "商品数量不能为空")
    @ApiModelProperty("商品数量")
    private Integer num;
}
