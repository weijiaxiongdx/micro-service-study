package com.wjx.study.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjx.common.Result;
import com.wjx.common.vo.GoodsVO;
import com.wjx.common.vo.OrderVO;
import com.wjx.study.dao.OrderMapper;
import com.wjx.study.feign.GoodsServiceFeign;
import com.wjx.study.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private GoodsServiceFeign goodsServiceFeign;

    /**
     * @Des 查询订单详情
     * @Date 2022/3/8 10:49
     * @Param id 订单id
     * @Return
     * @Author wjx
     */
    @Override
    public OrderVO selectOrderById(Long id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * @Des 远程调用商品服务1 写死商品服务接口地址
     * @Date 2022/3/8 16:07
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsById1(Long id) {
        String goodsStr = restTemplate.getForObject("http://127.0.0.1:9111/goods/detail?goodsId=" + id,String.class);
        JSONObject jsonObject = JSON.parseObject(goodsStr);
        JSONObject jsonDataObject = jsonObject.getJSONObject("data");
        GoodsVO goodsEntity = jsonDataObject.toJavaObject(GoodsVO.class);
        log.info("查询到的商品数据：{}",goodsEntity);
        return goodsEntity;
    }

    /**
     * @Des 远程调用商品服务2 从nacos注册中心拿商品服务地址，如果有多个实例，如何确定请求哪一个呢
     * @Date 2022/3/8 16:58
     * @Param id 商品id
     * @Return 
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsById2(Long id) {
        List<ServiceInstance> instanceList = discoveryClient.getInstances("goods-service");
        ServiceInstance instance = instanceList.get(0);
        String host = instance.getHost();
        int port = instance.getPort();

        String goodsStr = restTemplate.getForObject("http://" + host + ":" + port +"/goods/detail?goodsId=" + id,String.class);
        JSONObject jsonObject = JSON.parseObject(goodsStr);
        JSONObject jsonDataObject = jsonObject.getJSONObject("data");
        GoodsVO goodsEntity = jsonDataObject.toJavaObject(GoodsVO.class);
        log.info("查询到的商品数据：{}",goodsEntity);
        return goodsEntity;
    }


    /**
     * @Des 远程调用商品服务3 基于ribbon实现负载均衡(加@LoadBalanced注解)，默认轮询
     *      这种方式可读性不好、编程风格不统一，所以使用Feign(内部是对ribbon的封装)进行远程调用
     * @Date 2022/3/8 17:17
     * @Param id 商品id
     * @Return 
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsById3(Long id) {
        String url = "goods-service";
        String goodsStr = restTemplate.getForObject("http://" + url +"/goods/detail?goodsId=" + id,String.class);
        JSONObject jsonObject = JSON.parseObject(goodsStr);
        JSONObject jsonDataObject = jsonObject.getJSONObject("data");
        GoodsVO goodsEntity = jsonDataObject.toJavaObject(GoodsVO.class);
        log.info("查询到的商品数据：{}",goodsEntity);
        return goodsEntity;
    }

    /**
     * @Des 远程调用商品服务4 基于Feign进行远程调用
     * @Date 2022/3/8 17:56
     * @Param id 商品id
     * @Return
     * @Author wjx
     */
    @Override
    public GoodsVO selectGoodsById4(Long id) {
        Result<GoodsVO> goods = goodsServiceFeign.selectGoodsById(id);
        log.info("查询到的商品数据：{}",goods.getData());
        return goods.getData();
    }

    /**
     * blockHandler捕获的是Sentinel定义的BlockException异常、fallback捕获的是Throwable异常
     * blockHandlerMethod方法的返回值和参数必须要和原方法一致，允许在参数列表最后加一个BlockException，用来接收原方法中的异常
     * message为资源名称，在Sentinel控制台可针对此资源进行各种限流规则配置
     * ExceptionHandlePage异常处理优先级高于@SentinelResource注解配置的降级逻辑
     */
    @SentinelResource(value = "message",blockHandler = "blockHandlerMethod", fallback = "fallbackMethod")
    @Override
    public String selectGoodsById5(Long id) {
        //int i = 1/0;
        return "hello";
    }

    public String blockHandlerMethod(Long id, BlockException e){
        log.info("Sentinel异常：{}",e);
        return "处理Sentinel异常";
    }

    public String fallbackMethod(Long id){
        log.info("Throwable异常");
        return "处理Throwable异常";
    }
}
