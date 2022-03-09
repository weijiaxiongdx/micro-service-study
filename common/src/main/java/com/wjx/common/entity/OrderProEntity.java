package com.wjx.common.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Desc:
 * @File name：com.wjx.common.entity.OrderProEntity
 * @Create on：2022/3/8 13:55
 * @Author：wjx
 */
@TableName("pp_order_pro")
@Alias("orderPro")
@Data
public class OrderProEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @Id
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "订单号")
    @Column(name = "order_number")
    private String orderNumber;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "订单状态")
    @Column(name = "status")
    private String status;
}