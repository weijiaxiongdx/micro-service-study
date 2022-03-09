package com.wjx.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "订单详情响应")
@Data
public class OrderVO {

    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
