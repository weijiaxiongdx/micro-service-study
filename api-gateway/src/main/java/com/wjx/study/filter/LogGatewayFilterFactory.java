package com.wjx.study.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Desc: 自定义局部过滤器 类名中的Log要与配置文件配置的过滤器名称一致，然后加上固定的GatewayFilterFactory
 *        属于pre过滤器，路由被转发之前执行
 * @File name：com.wjx.study.filter.LogGatewayFilterFactory
 * @Create on：2022/3/13 16:21
 * @Author：wjx
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    // 构造器
    public LogGatewayFilterFactory(){
        super(LogGatewayFilterFactory.Config.class);
    }

    // 读取配置文件中的参数，赋值到配置类中
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog","cacheLog");
    }


    // 过滤器逻辑
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isCacheLog()){
                System.out.println("cacheLog日志已经开启了");
            }

            if (config.isConsoleLog()){
                System.out.println("consoleLog日志已经开启了");
            }

            return chain.filter(exchange);
        };
    }

    // 配置类，接收配置参数
    @Data
    @NoArgsConstructor
    public static class Config{
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
