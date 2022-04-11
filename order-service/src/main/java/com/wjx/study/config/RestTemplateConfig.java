package com.wjx.study.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @Desc:
 * @File name：com.wjx.study.config.RestTemplateConfig
 * @Create on：2022/3/8 16:16
 * @Author：wjx
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 一、自定义负载均衡策略，@LoadBalanced默认的负载均衡策略为轮询
     *     方式一：在任何一个配置类型中定义IRule类型的bean，如下方的randomRule，这种方式是全局的，针对所有的微服务，订单服务调商品服务、订单服务调用户服务等都是此策略
     *     方式二：在配置文件中配置规则，只针对某个微服务，goods-service:
     *                                              ribbon:
     *                                                NFLoadBalancerRuleClassName:com.netflix.loadbalancer.RandomRule
     * 二、修改加载策略，Ribbon默认采用懒加载，即第一次访问时才会创建LoadBalancerClient(同时会缓存服务提供方信息到本地)，请求时间比较长，而懒加载方式会在项目启动时创建LoadBalancerClient，可通过以下配置开启懒加载
     *    ribbon:
     *      eager-load:
     *        enabled: true #开启懒加载
     *        clients: goods-service #指定对哪个服务进行懒加载，可配置多个
     * @param factory
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(10000);
        return factory;
    }



    /**
     * @Des 将@LoadBalanced默认的轮询策略改为随机策略
     * @Date 2022/4/8 15:23
     * @Param 
     * @Return 
     * @Author wjx
     */
   /* @Bean
    public IRule randomRule(){
        return new RandomRule();
    }*/
    
}
