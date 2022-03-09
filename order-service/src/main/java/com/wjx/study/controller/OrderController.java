package com.wjx.study.controller;

import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.common.vo.OrderVO;
import com.wjx.study.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc: 订单服务类
 * @File name：com.wjx.study.controller.OrderController
 * @Create on：2022/3/8 9:54
 * @Author：wjx
 */
@Api(tags = {"订单服务"})
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

     /**
      * @Des 查看订单详情
      * @Date 2022/3/8 10:46
      * @Param id 订单id
      * @Return
      * @Author wjx
      */
    @ApiOperation(value = "查看订单详情")
    @GetMapping("/detail")
    public Result<OrderVO> selectOrderById(@RequestParam("orderId") Long id){
        return Result.ok(orderService.selectOrderById(id));
    }

    /**
     * @Des 远程调用商品服务1 写死商品服务接口地址
     * @Date 2022/3/8 16:06
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @ApiOperation(value = " 远程查看商品详情1")
    @GetMapping("/goods/detail1")
    public Result<GoodsVO> selectGoodsById1(@RequestParam("goodsId") Long id){
        return Result.ok(orderService.selectGoodsById1(id));
    }


    /**
     * @Des 远程调用商品服务2 从nacos注册中心拿商品服务地址，如果有多个实例，如何确定请求哪一个呢
     * @Date 2022/3/8 16:57
     * @Param 商品id
     * @Return
     * @Author wjx
     */
    @ApiOperation(value = " 远程查看商品详情2")
    @GetMapping("/goods/detail2")
    public Result<GoodsVO> selectGoodsById2(@RequestParam("goodsId") Long id){
        return Result.ok(orderService.selectGoodsById2(id));
    }

    /**
     * @Des 远程调用商品服务3 基于ribbon实现负载均衡(加@LoadBalanced注解)，默认轮询
     * @Date 2022/3/8 17:11
     * @Param 商品id
     * @Return
     * @Author wjx
     */
    @ApiOperation(value = " 远程查看商品详情3")
    @GetMapping("/goods/detail3")
    public Result<GoodsVO> selectGoodsById3(@RequestParam("goodsId") Long id){
        return Result.ok(orderService.selectGoodsById3(id));
    }

    /**
     * @Des 远程调用商品服务4 基于Feign进行远程调用
     * @Date 2022/3/8 17:55
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @ApiOperation(value = " 远程查看商品详情4")
    @GetMapping("/goods/detail4")
    public Result<GoodsVO> selectGoodsById4(@RequestParam("goodsId") Long id){
        return Result.ok(orderService.selectGoodsById4(id));
    }

    /**
     * @Des @SentinelResource注解测试
     * @Date 2022/3/9 13:56
     * @Param 
     * @Return 
     * @Author wjx
     */
    @ApiOperation(value = "SentinelResource注解测试")
    @GetMapping("/goods/detail5")
    public Result<String> selectGoodsById5(@RequestParam(value = "goodsId",required = false) Long id){
        return Result.ok(orderService.selectGoodsById5(id));
    }
}
